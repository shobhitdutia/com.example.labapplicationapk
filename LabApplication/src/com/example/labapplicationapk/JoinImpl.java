package com.example.labapplicationapk;
/* 
 * JoinImpl.java 
 * 
 * Version: 
 *     $Id$ 
 * 
 * Revisions: 
 *     $Log$ 
 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JTextField;

/**
 * This program is the implementation of the interface
 *
 * @author      Shobhit Dutia
 */

public class JoinImpl extends UnicastRemoteObject implements JoinInterface {
	private static final long serialVersionUID = 1L;
	static int countOfPeers=0;
	static Map<Integer, String> ipMap=new HashMap<Integer, String>();
	
	protected JoinImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * join request method.
	 * 
	 * @param    args   	ip address of new node
	 * @return 	 ip address of bootstrap node	
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */


	public int addUsersToDatabase(String username,
			String password, Vector<JTextField> userListVector) {
		int success=1;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con;
			con = DriverManager.getConnection(
			        "jdbc:mysql://localhost/radb",
			        username,
			        password);
			Statement stmt = con.createStatement();
			String pass=null;
			int uid;
			for (int i=0;i<userListVector.size();i++) {//JTextField jt:userListVector) {
				if((i%2)==0)
					 pass=userListVector.get(i).getText();
				else {
					uid=Integer.parseInt(userListVector.get(i).getText());	
					System.out.println(pass+" "+uid);
				    if(stmt.executeUpdate("INSERT INTO userlist (uid,pass) VALUES ("+uid+",\""+pass+"\");")==0) {
				    	success=0;
				    }
				}
			}
			stmt.close();
			con.close();
			System.out.println("Inserted successfully");
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
			success=0;
		}
		return success;		
	}
	
	public int deleteUsersFromDatabase(String username,
			String password, Vector<JTextField> userListVector) {
		int success=1;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con;
			con = DriverManager.getConnection(
			        "jdbc:mysql://localhost/radb",
			        username,
			        password);
			Statement stmt = con.createStatement();
			String uname=null;	
			int uid;
			for (int i=0;i<userListVector.size();i++) {//JTextField jt:userListVector) {
				if((i%2)==0)
					 uname=userListVector.get(i).getText();
				else {
					uid=Integer.parseInt(userListVector.get(i).getText());		    	
				    //if(stmt.executeUpdate("DELETE FROM userList where uid="+uid+"AND uname=\'"+uname+"\';")==0) {
					if(stmt.executeUpdate("DELETE FROM userList where uid="+uid+";")==0) {
				    	success=0;
				    }
				}
			}
			stmt.close();
			con.close();
			System.out.println("Deleted successfully");
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
			success=0;
		}
		return success;
	}
	public Vector getUsersFromDatabase(String username,
			String password) {
		Vector userListVector=new Vector();
		int success=1, id;
		String name;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con;
			con = DriverManager.getConnection(
			        "jdbc:mysql://localhost/radb",
			        username,
			        password);
			Statement stmt = con.createStatement();
			ResultSet rs=stmt.executeQuery("Select * from userList");
			
			while(rs.next()){
				id=rs.getInt("uid");
				name=rs.getString("uname");
				//create 2 text fields with values received from uid and uname
				//JTextField uidTextField=new JTextField();
			//	uidTextField.setText(Integer.toString(rs.getInt("uid")));
				userListVector.add(name);
				//JTextField uNameTextField=new JTextField();
			//	uNameTextField.setText(rs.getString("uname"));
				userListVector.add(id);
			}
			stmt.close();
			con.close();
			System.out.println("GOt users successfully");
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return userListVector;
	}

	@Override
	public int addMalware(String fileName, byte[] fileData) throws RemoteException {
         try {
        	 File dir = new File("Malwares");
 			 if(!dir.exists())
 				dir.mkdir();
        	 System.out.println("Filename is "+fileName);
             File file=new File(dir,fileName);
             BufferedOutputStream outputFile=new BufferedOutputStream(new FileOutputStream(file.getAbsolutePath()));
			outputFile.write(fileData,0,fileData.length);
	         outputFile.flush();
	         outputFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}

	@Override
	public int sendEmulatorConfiguration(Vector<File> v)
			throws IOException {
		FileReader fr=null;
		FileWriter fw=null;
		int index=0;
		try{
			File dir = new File("Emulator_Configurations");
			if(!dir.exists())
				dir.mkdir();
			for (File f:v) {
				fr=new FileReader(f);
				File f1=new File(dir,"config_ini_"+(index++)+".ini");
				fw=new FileWriter(f1);
				System.out.println("Creating file.. ");
				int c;
		        while ((c = fr.read()) != -1) {
		        	fw.write(c);
		        }
			}			
		} finally {
            if (fr != null) {
                fr.close();
            }
            if (fw!= null) {
                fw.close();
            }
        }
		return 1;
	}

	@Override
	public Vector getEmulatorConfiguration() throws RemoteException,
			IOException {
		Vector configVector=sendFilesToClient("configuration");
		//File dir=new File("Emulator_Configurations");
		return configVector;
	}
	
	@Override
	public Vector getMalware() throws IOException {
		Vector malwareVector=sendFilesToClient("malware");
		//File dir=new File("Emulator_Configurations");
		return malwareVector;
	}
	private Vector sendFilesToClient(String queryType) throws IOException {
		Vector configVector=new Vector();
		File[] files;
		if(queryType.equals("configuration")) 
			files= new File("Emulator_Configurations").listFiles();
		else
			files = new File("Malwares").listFiles();
		//adding every odd value as file name and even value as bytes for that file
		for (int i = 0; i < files.length; i++) {
			System.out.println(files[i].getAbsolutePath());
			 byte buffer[]=new byte[(int)files[i].length()];
	         BufferedInputStream inputFileStream=new BufferedInputStream( new FileInputStream(files[i].getAbsolutePath()));
			//Reads the file into buffer
	         System.out.println("Buffer length:"+buffer.length);
	         inputFileStream.read(buffer,0,buffer.length);
	         inputFileStream.close();			
	         configVector.add(files[i].getName());
	         configVector.add(buffer);
		}
		return configVector;
	}
	@Override
	public List<String> files() throws RemoteException {
		String files;
		//String user= System.getProperty("user.name"); 
		//String path = "/home/stu14/s6/"+user+"/logs"; 
		String path = "logs"; 
		File folder;
		//if(new File("/home/stu14/s6/"+user+"/logs").exists()) {
		if(new File("logs").exists()) {
			folder = new File(path);
		}
		else{
	//		new File("/home/stu14/s6/"+user+"/logs").mkdir();
			new File("logs").mkdir();
			//folder = new File("/home/stu14/s6/"+user+"/logs");
			folder = new File("logs");
		}
		File[] listOfFiles = folder.listFiles(); 
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < listOfFiles.length; i++) 
		{
			files = listOfFiles[i].getName();
			list.add(files);
		}
		return list;
	}

	@Override
	public byte[] downloadFile(String fileName) throws RemoteException {
		try {
			//String user=System.getProperty("user.name");
			//File file = new File("/home/stu14/s6/"+user+"/logs/"+fileName);
			File file = new File("logs/"+fileName);
			byte buffer[] = new byte[(int)file.length()];
			BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));
			input.read(buffer,0,buffer.length);
			input.close();
			return(buffer);
		} catch(Exception e){
			System.out.println("FileImpl: "+e.getMessage());
			e.printStackTrace();
			return(null);
		}
	}

	@Override
	public List<String> classes() throws RemoteException {
		String files;
		//String user= System.getProperty("user.name"); 
		//String path = "/home/stu14/s6/"+user+"/logs"; 
		String path = "ISSP"; 
		File folder;
		//if(new File("/home/stu14/s6/"+user+"/logs").exists()) {
		if(new File("ISSP").exists()) {
			folder = new File(path);
		}
		else{
	//		new File("/home/stu14/s6/"+user+"/logs").mkdir();
			new File("ISSP").mkdir();
			//folder = new File("/home/stu14/s6/"+user+"/logs");
			folder = new File("ISSP");
		}
		File[] listOfFiles = folder.listFiles(); 
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < listOfFiles.length; i++) 
		{
			files = listOfFiles[i].getName();
			list.add(files);
		}
		return list;	
	}
}