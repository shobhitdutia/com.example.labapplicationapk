package com.example.labapplicationapk;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Vector;
import java.util.concurrent.Semaphore;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class ControllerClient implements ActionListener {

	JTextField username;
	String usernameString;
	JPasswordField password;
	String className;
	ViewClient v= new ViewClient(this);
	JoinInterface obj;// = (JoinInterface) Naming.lookup("rmi://"+"bootstrapip"+"port number");
	Process pr;
	Semaphore semaphore = new Semaphore(0);
	PrintWriter writer;
	String middlewareIP="localhost";
	ViewChangePasswordClient viewcpc;
	ControllerClientBackListener ccb;

	public ControllerClient() {
		ccb=new ControllerClientBackListener();
		viewcpc=new ViewChangePasswordClient(this, ccb);
		try {
			obj= (JoinInterface)Naming.lookup("//"+middlewareIP+":12459/Shobhit_bootstrapObject");
		} catch (MalformedURLException | RemoteException | NotBoundException e1) {
			e1.printStackTrace();
		}
	}
	public void init() {
		Runnable input=new IncomingReader();
		Thread name=new Thread(input);
		name.start();
		v.showGUI();
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() instanceof JButton) {

			JButton button =(JButton) e.getSource();
			String button_clicked=button.getText();

			if(button_clicked.equals("Login")) {
				username=v.username;
				usernameString=username.getText().toString();
	
				if(usernameString.equals("")) {
					JOptionPane.showMessageDialog((Component) e.getSource(),
							"Please enter a username",
							"EMPTY",
							JOptionPane.ERROR_MESSAGE);
					
				}
				else {
					if (isPasswordCorrect(usernameString, v.password.getPassword())) {
					username=v.username;
					password=v.password;
					v.showMainScreen();
					username.setText("");
					password.setText("");
					} else {
						JOptionPane.showMessageDialog((Component) e.getSource(),
								"Invalid username or password",
								"Login",
								JOptionPane.ERROR_MESSAGE);
					}
				}		
			}
			else if(button_clicked.equals("Clear")) {
				System.out.println("in clear");
				username.setText("");
				password.setText("");
			}
			else if(button_clicked.equals("Logout")) {
				if(pr!=null){
					//save log
					/*try {
						writer = new PrintWriter("Log.txt", "UTF-8");
						BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
						String line;//= input.readLine();
						while ((line = input.readLine()) != null) {
						        System.out.println(line);  
								writer.println(line);
						}
						System.out.println(line);
						input.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					writer.close();*/
					//send log
					pr.destroy();
				}
				v.frame2.setVisible(false);
				v.frame1.setVisible(true);
			}
			else if(button_clicked.equals("Download Configruation")) {
				//get the config files in a vector
				this.getFiles("configuration", e);
			}
			else if(button_clicked.equals("Download Malware")) {
				this.getFiles("malware", e);
			}
			else if(button_clicked.equals("Backup Emulator")) {

				String user=System.getProperty("user.name");
				File srcFolder = new File("C:/Users/"+user+"/.android/avd");
				File destFolder = new File("C:/Users/"+user+"/.android/avdBackup");

				try{
					copyFolder(srcFolder,destFolder);
				}catch(IOException e1){
					e1.printStackTrace();
				}

			}
			else if(button_clicked.equals("Restore Emulator")) {
				String user=System.getProperty("user.name"); 
				File destFolder = new File("/home/"+user+"/.android/avd");
				File srcFolder = new File("/home/"+user+"/.android/avdBackup");

				try{
					copyFolder(srcFolder,destFolder);
				}catch(IOException e1){
					e1.printStackTrace();
				}

			}else if(button_clicked.equals("Open Terminal")){

				String command= "/usr/bin/xterm"; 
				Runtime rt = Runtime.getRuntime(); 	
				try {
					pr = rt.exec(command);
					semaphore.release();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			else if(button.getText().equals("Change password")) {
				ControllerClientBackListener.backButtoncallingFrom="Change password";
				ViewClient.frame2.setVisible(false);
				viewcpc.showGUI();
			}
			else if(button.getText().equals("Change!")) {
				Vector<JTextField> textFieldVector=viewcpc.getInputVector();
				String result = null;
				boolean error=false;
				for(JTextField inputField:textFieldVector) {
					if(inputField.getText().toString()=="") {
						error=true;
						break;
					}	
				}
				if(error) {
					JOptionPane.showMessageDialog((Component) e.getSource(),
							"Please enter values into all textfields",
							"Empty text fields",
							JOptionPane.ERROR_MESSAGE);
				}
				else {
					try {
						String oldPassword=textFieldVector.get(0).getText().toString();
						String newPassword=textFieldVector.get(1).getText().toString();
						result=obj.changePassword(usernameString,
								oldPassword, 
								newPassword, "client calling");
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				JOptionPane.showMessageDialog((Component) e.getSource(),
						result,
						"Result",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	private void getFiles(String queryType, ActionEvent e) {
		// TODO Auto-generated method stub
		Vector fileVector = null;
		boolean exceptionOccured=false;
		String middlewareIP="localhost";
		try {
			
			className=obj.getMyClassName(usernameString);
			System.out.println("Classname of "+usernameString+"is "+className);
			if(queryType.equals("configuration"))
				fileVector=obj.getEmulatorConfiguration(className); 
			else 
				fileVector=obj.getMalware(className); 
		} catch(Exception e1) {
			exceptionOccured=true;
			JOptionPane.showMessageDialog((Component) e.getSource(),
                    "Please check your connection",
                    "Connection error",
                    JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
		//create files from vector. Ensure there is no connection problem
		if(!exceptionOccured) {
			try {
				int i=0;
				while(i<fileVector.size()) {
					File dir;
					if(queryType.equals("configuration"))
						dir = new File("Client_Emu_Configuration");
					else 
						dir = new File("Client_Malware");
		 			if(!dir.exists())
		 				dir.mkdir();
		 			 String fileName=(String) fileVector.get(i++);
		 			 byte[]fileData=(byte[]) fileVector.get(i++);
		        	 System.out.println("Filename is "+fileName);
		             File file=new File(dir,fileName);
		             BufferedOutputStream outputFile=new BufferedOutputStream(new FileOutputStream(file.getAbsolutePath()));
					 outputFile.write(fileData,0,fileData.length);
			         outputFile.flush();
			         outputFile.close();
			         JOptionPane.showMessageDialog((Component) e.getSource(),
			                    "Download successful",
			                    "Success",
			                    JOptionPane.INFORMATION_MESSAGE);
				}
			} catch(Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	private boolean isPasswordCorrect(String username, char[] password) {
		//char[] correctPassword={'l','a','b'};
		char[] correctPassword= null;
		System.out.println("Username is "+username);
		try {
			correctPassword= obj.getPassword(username, "client calling");
			if(correctPassword==null)
				return false;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		System.out.println("correct pass is "+correctPassword.toString());
		
		//char[] correctPassword=correctPasswordString.toCharArray();
		boolean isCorrect=true;
		int length;
		if (password.length != correctPassword.length) 
			isCorrect = false;
		else
			for (int i = 0; i < (length=correctPassword.length>password.length?password.length:correctPassword.length); i++) {
				//System.out.println("compating "+password[i]+"and"+correctPassword[i]); 
				if (password[i] != correctPassword[i]) 
					isCorrect = false;
			}

		//Zero out the password.
		for (int i = 0; i < correctPassword.length; i++) {
			correctPassword[i]='O';	
		}
		if(isCorrect)
			return true;
		else 
			return false;
	}
	/*private boolean isPasswordCorrect(String username, char[] password) {
		char[] correctPassword={'l','a','b'};
		boolean isCorrect=true;
		int length;
		if (password.length != correctPassword.length) 
			isCorrect = false;
		else
			for (int i = 0; i < (length=correctPassword.length>password.length?password.length:correctPassword.length); i++) {
				System.out.println("compating "+password[i]+"and"+correctPassword[i]); 
				if (password[i] != correctPassword[i]) 
					isCorrect = false;
			}

		//Zero out the password.
		for (int i = 0; i < correctPassword.length; i++) {
			correctPassword[i]='O';	
		}
		if(username.equals("lab") && isCorrect)
			return true;
		else 
			return false;
	}*/
	
	public class IncomingReader implements Runnable{
		public void run(){
			try {
				semaphore.acquire();
				BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
				//while(true){
				//write to file
				//	try {
				System.out.println("started");
				//writer = new PrintWriter("Log.txt", "UTF-8");
				String line;//= input.readLine();
				while ((line = input.readLine()) != null && pr!=null) {
					System.out.println(line);  
					//		writer.println(line);
				}
				System.out.println("complete");
				//System.out.println(line);
				//input.close();
				//	} catch (IOException e1) {
				//		e1.printStackTrace();
				//	}
				//writer.close();
				//}
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void copyFolder(File src, File dest)
			throws IOException{

		if(src.isDirectory()){

			//if directory not exists, create it
			if(!dest.exists()){
				dest.mkdir();
			}

			//list all the directory contents
			String files[] = src.list();

			for (String file : files) {
				//construct the src and dest file structure
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				//recursive copy
				copyFolder(srcFile,destFile);
			}
		}else{
			//if file, then copy it
			//Use bytes stream to support all file types
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest); 

			byte[] buffer = new byte[1024];

			int length;
			//copy the file content in bytes 
			while ((length = in.read(buffer)) > 0){
				out.write(buffer, 0, length);
			}

			in.close();
			out.close();
		}
	}
}