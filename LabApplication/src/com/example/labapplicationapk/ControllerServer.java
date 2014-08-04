package com.example.labapplicationapk;
import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.View;


public class ControllerServer implements ActionListener, ItemListener {
	//String username;;;;
	String selectedClass;
	ViewClassList viewClassList;
	String middlewareIP="localhost";
	JoinInterface obj;
	JTextField username;
	JPasswordField password;
	ViewServer v;
	ViewLog viewLog;
	ViewAddUser viewAddUser;
	ViewSendEmuConfig viewSendEmuConfig;
	ViewChangePassword viewcp;
	ViewAddInstructor viewAddInstructor;
	ControllerServerBackListener csb;
	public ControllerServer() {
		try {
			obj= (JoinInterface)Naming.lookup("//"+middlewareIP+":12459/Shobhit_bootstrapObject");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};

		csb=new ControllerServerBackListener(this);
		v= new ViewServer(this);
		viewAddUser=new ViewAddUser(this, csb);
		viewLog=new ViewLog(this,csb, obj);
		viewClassList=new ViewClassList(this, csb, obj);
		viewSendEmuConfig=new ViewSendEmuConfig(csb);
		viewcp=new ViewChangePassword(this, csb);
		viewAddInstructor=new ViewAddInstructor(this, csb);
	}
	public void init() {
		v.showGUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// System.out.println(getUsername()+" "+new String(getPassword()));
		if(e.getSource() instanceof JButton) {
			JButton button =(JButton) e.getSource();
			if(button.getText().equals("Login")) {

				if (isPasswordCorrect(v.username.getText(), v.password.getPassword())) {
					username=v.username;
					password=v.password;
					v.showMainScreen();
					username.setText("");
					password.setText("");
				} else {
					JOptionPane.showMessageDialog((Component) e.getSource(),
							"Invalid usernameviewClassList.frame1.setVisible(false); or password",
							"Login",
							JOptionPane.ERROR_MESSAGE);
				}		
			}
			else if(button.getText().equals("Clear")) {
				System.out.println("in clear");
				username=v.username;
				password=v.password;
				username.setText("");
				password.setText("");
			}
			else if(button.getText().equals("Add users")) {
				ControllerServerBackListener.backButtoncallingFrom="Add users_1";
				ViewServer.frame2.setVisible(false);
				//v.showAddRemovePage("Add");
				//viewAddUser.showGUI();
				
				//viewClassList.setCallingLocation("Add users");
				//viewClassList.showGUI();
				viewAddUser.showGUI1();
			}
			else if(button.getText().equals("Remove users")) {
				refreshUserListAndUpdateGUI(e, "delete user");
			}
			/*else if(button.getText().equals("Back")) {
				Vector<JTextField> userListVector=ViewAddRemoveUsers.getUserList();
				userListVector.removeAllElements();
				ViewAddRemoveUsers.removeAllTextboxes("");
				ViewAddRemoveUsers.frame1.setVisible(false);
				ViewServer.frame2.setVisible(true);
			}*/
			else if(button.getText().equals("Send emulator config")) {
				ViewServer.frame2.setVisible(false);
				viewClassList.setCallingLocation("Send emulator config");
				viewClassList.showGUI();
				ControllerServerBackListener.backButtoncallingFrom="Send emulator config_1";
				//Code to send emulator config
				//Path of avd directory
				/**/

			}
			else if(button.getText().equals("Send selected configuration")) {
				selectedClass=ViewAddUser.newClassName;
				System.out.println("Configuration class name is "+selectedClass);
				Vector<JCheckBox> selectedConfig=ViewSendEmuConfig.getCheckedConfig();
				Vector<File> fileDir=ViewSendEmuConfig.getFileDirOfConfig();
				//check if list is empty
				System.out.println("Size of selected config is :"+selectedConfig.size());
				boolean atleastOneSelected=false;
				for(JCheckBox jc:selectedConfig) {
					if(jc.isSelected())
						atleastOneSelected=true;
				}viewClassList.frame1.setVisible(false);
				if(!atleastOneSelected) {
					JOptionPane.showMessageDialog((Component) e.getSource(),
							"Please check atleast 1 value!",
							"ERROR",
							JOptionPane.ERROR_MESSAGE);
				}
				else {
					int index=0;
					for(JCheckBox jc:selectedConfig) {
						if(!jc.isSelected()) {
							fileDir.remove(index);
						}
						index++;
					}
					System.out.println("SelecviewClassList.frame1.setVisible(false);ted is ");
					for (File f:fileDir) {
						System.out.println(f.getAbsolutePath());
					}
					//Get config.ini files from each avd selected
					String list[];
					Vector<File> configFiles=new Vector<File>();
					for(File f:fileDir) {
						list=f.list();
						for (int i = 0; i < list.length; i++) {
							if(list[i].equals("config.ini")) {
								configFiles.add(new File(f.getAbsolutePath()+"\\config.ini"));
							}
						}
					}
					/*System.out.println("ConviewClassList.frame1.setVisible(false);fig pahts:");
					for(File f:configFiles) {
						System.out.println(f.getAbsolutePath());
					}*/
					//send config files

					try {
						if(obj.sendEmulatorConfiguration(configFiles,selectedClass)==1) {
							JOptionPane.showMessageDialog((Component) e.getSource(),
									"Sent successfully",
									"Success",
									JOptionPane.INFORMATION_MESSAGE);
						}
					} catch(Exception e1) {
						JOptionPane.showMessageDialog((Component) e.getSource(),
								"Please check your connection",
								"Connection error",
								JOptionPane.ERROR_MESSAGE);
					}
					ViewSendEmuConfig.frame1.setVisible(false);
					ViewSendEmuConfig.frame1.dispose();
					ViewSendEmuConfig.fileDir.removeAllElements();
					ViewSendEmuConfig.checkedConfigVector.removeAllElements();
					ViewServer.frame2.setVisible(true);
				}
			}
			
			else if(button.getText().equals("Done adding")) {
				selectedClass=ViewAddUser.newClassName;
				System.out.println(selectedClass);
				Vector<JTextField> userListVector=ViewAddUser.getUserList();
				//check if list is empty
				if(userListVector.size()==0) {
					//
					JOptionPane.showMessageDialog((Component) e.getSource(),
							"Please add some values to the text box!",
							"EMPTY",
							JOptionPane.ERROR_MESSAGE);
				}
				else {
					boolean error=false;
					//				for(JTextField jt:userListVector) {
					System.out.println("Size of vector "+userListVector.size());
					for(int i=0;i<userListVector.size();i++) {	
						JTextField jt=userListVector.get(i);
						/*if(i%2!=0) {
							try {
								Integer.parseInt(jt.getText());
							} catch(NumberFormatException e1) {
								System.out.println("Not a number");
								error=true;
								break;
							}
						}*/
						if(jt.getText().equals("")) {
							error=true;
							break;	
						}
					}
					System.out.println(error);
					if(error)
						JOptionPane.showMessageDialog((Component) e.getSource(),
								"Please check your input!",
								"EMPTY",
								JOptionPane.ERROR_MESSAGE);
					else if(!error) {
						try {

							if(obj.addUsersToDatabase("root", "mysql", userListVector, selectedClass)==1) {
								JOptionPane.showMessageDialog((Component) e.getSource(),
										"Query Successful!",
										"INSERTED",
										JOptionPane.INFORMATION_MESSAGE);
								userListVector.removeAllElements();
//								ViewAddRemoveUsers.removeAllTextboxes("Add");
								ViewAddUser.frame3.setVisible(false);
								ViewAddUser.frame3.dispose();
								viewAddUser.showGUI3();
							}
							else {
								JOptionPane.showMessageDialog((Component) e.getSource(),
										"Query Failed! Check your input",
										"EMPTY",
										JOptionPane.INFORMATION_MESSAGE);
								userListVector.removeAllElements();
								ViewAddUser.frame3.setVisible(false);
								ViewAddUser.frame3.dispose();
								viewAddUser.showGUI3();
							}
						} catch (RemoteException
								| SQLException | InstantiationException
								| IllegalAccessException | ClassNotFoundException e1) {
							e1.printStackTrace();
						}	    						
					}
				}
			}

			//
			else if(button.getText().equals("Done deleting")) {
				Vector<JTextField> userListVector=ViewAddRemoveUsers.getUserList();
				//check if list is empty
				if(userListVector.size()==0) {
					//
					JOptionPane.showMessageDialog((Component) e.getSource(),
							"Please add some values to the text box!",
							"EMPTY",
							JOptionPane.ERROR_MESSAGE);
				}
				else {
					boolean error=false;
					//				for(JTextField jt:userListVector) {
					for(int i=0;i<userListVector.size();i++) {	
						JTextField jt=userListVector.get(i);
						if(i%2!=0) {
							try {
								Integer.parseInt(jt.getText());
							} catch(NumberFormatException e1) {
								error=true;
								break;
							}
						}
						if(jt.getText().equals("")) {
							error=true;
							break;	
						}
					}
					System.out.println(error);
					if(error)
						JOptionPane.showMessageDialog((Component) e.getSource(),
								"Please check your input!",
								"EMPTY",
								JOptionPane.ERROR_MESSAGE);
					else if(!error) {
						String middlewareIP="localhost";

						try {

							if(obj.deleteUsersFromDatabase("root", "mysql", userListVector)==1) {
								JOptionPane.showMessageDialog((Component) e.getSource(),
										"Query Successful!",
										"Deleted",
										JOptionPane.INFORMATION_MESSAGE);
								userListVector.removeAllElements();
								ViewAddRemoveUsers.removeAllTextboxes("Remove");

								ViewAddRemoveUsers.frame1.invalidate();
								refreshUserListAndUpdateGUI(e, "refresh user");

								//v.show("Remove");
							}
							else {
								JOptionPane.showMessageDialog((Component) e.getSource(),
										"Query Failed! Check your input",
										"EMPTY",
										JOptionPane.INFORMATION_MESSAGE);
							}
						} catch (RemoteException
								| SQLException | InstantiationException
								| IllegalAccessException | ClassNotFoundException e1) {
							e1.printStackTrace();
						}	    						
					}
				}
			}
			else if(button.getText().equals("Add malware")) {
				ViewServer.frame2.setVisible(false);
				ControllerServerBackListener.backButtoncallingFrom="Add malware_1";
				//v.showMalwarePage("Add");
				viewClassList.setCallingLocation("Add malware");
				viewClassList.showGUI();
			}
		/*	else if(button.getText().equals("Back to main")) {
				ViewAddRemoveMalware.frame1.setVisible(false);
				ViewServer.frame2.setVisible(true);
			}*/
			else if(button.getText().equals("Done adding malware")) {
				selectedClass=ViewAddUser.newClassName;
				System.out.println("Add malware selected class is "+selectedClass);
				//JFileChooser jc=ViewAddRemoveMalware.getFileChooserObject();
				JTextField malwareName=ViewAddRemoveMalware.getMalwareTextField();
				System.out.println(malwareName.getText());
				//check if textBox is empty
				if(malwareName.getText()=="") {
					//
					JOptionPane.showMessageDialog((Component) e.getSource(),
							"Please add some values to the text box!",
							"EMPTY",
							JOptionPane.ERROR_MESSAGE);
				}
				else {

					try {
						File file=new File(malwareName.getText());
						//Defines buffer in which the file will be read
						byte buffer[]=new byte[(int)file.length()];
						BufferedInputStream inputFileStream=new BufferedInputStream( new FileInputStream(malwareName.getText()));
						//Reads the file into buffer
						System.out.println("Buffer length:"+buffer.length);
						inputFileStream.read(buffer,0,buffer.length);
						inputFileStream.close();
						//  return(buffer);


						if(obj.addMalware(file.getName(),buffer,selectedClass)==1) {
							JOptionPane.showMessageDialog((Component) e.getSource(),
									"Malware transferred!",
									"SUCCESS",
									JOptionPane.INFORMATION_MESSAGE);
							malwareName.setText("");
						}
						else {
							JOptionPane.showMessageDialog((Component) e.getSource(),
									"Could not transfer",
									"ERROR",
									JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (IOException e1) {
						JOptionPane.showMessageDialog((Component) e.getSource(),
								"ERROR",
								"File not found",
								JOptionPane.ERROR_MESSAGE);
						//e1.printStackTrace();
					}	    						
				}
			}
			else if(button.getText().equals("View log")) {
				ControllerServerBackListener.backButtoncallingFrom="View log_1";
				ViewServer.frame2.setVisible(false);
				viewClassList.setCallingLocation("View log");
				viewClassList.showGUI();
				//v1=new ViewLog(this, obj);
				//v1.showGUI();
			}
			else if(button.getText().equals("Open")) {
				ViewLog.frame1.setVisible(false);
				viewLog.selectedfile=(String) viewLog.listbox.getSelectedValue();
				ViewLog.frame1.setVisible(false);
				viewLog.showFile();
			}
			
			else if(button.getText().equals("Back to list")) {
				viewLog.frame1.setVisible(true);
				viewLog.frame2.setVisible(false);
			}
			else if(button.getText().equals("Select existing class")) {
				ViewAddUser.frame1.setVisible(false);
				ControllerServerBackListener.backButtoncallingFrom="Select existing class";
				viewClassList.setCallingLocation("Add users");
				viewClassList.showGUI();
			}
			else if(button.getText().equals("Add class")) {
				ViewAddUser.frame1.setVisible(false);
				ControllerServerBackListener.backButtoncallingFrom="Add class";
				viewAddUser.showGUI2();
			}
			else if(button.getText().equals("Ok")) {
				ControllerServerBackListener.backButtoncallingFrom="Adding users "
						+ "to textbox from new class";
				viewAddUser.setNewClassName();
				ViewAddUser.frame2.setVisible(false);
				viewAddUser.showGUI3();
			}
			else if(button.getText().equals("Select")) {
//				selectedClass = viewClassList.selectedClass;
				ViewClassList.frame1.setVisible(false);
				if(viewClassList.callingFrom.equals("Add users")){
					System.out.println("Callin from add users ");
					///////////////
					///////////////
					///////////////
					//viewAddUser.showGUI1();
					ControllerServerBackListener.backButtoncallingFrom="Adding users "
							+ "to textbox from existing class";
					viewAddUser.setExistingClassName(viewClassList.listbox.getSelectedValue().toString());
					//ViewAddUser.frame2.setVisible(false);
					viewAddUser.showGUI3();
				}else if(viewClassList.callingFrom.equals("Add malware")){
					ControllerServerBackListener.backButtoncallingFrom="Add malware_2";
					viewAddUser.setExistingClassName(viewClassList.listbox.getSelectedValue().toString());
					v.showMalwarePage("Add");
				}else if(viewClassList.callingFrom.equals("View log")){
					ControllerServerBackListener.backButtoncallingFrom="View log_2";
					viewLog.setClassName(viewClassList.listbox.getSelectedValue().toString());
					viewLog.showGUI();
					//viewClassList.setCallingLocation("View log");
					//viewClassList.showGUI();
				}else if(viewClassList.callingFrom.equals("Send emulator config")){
					viewAddUser.setExistingClassName(viewClassList.listbox.getSelectedValue().toString());
					ControllerServerBackListener.backButtoncallingFrom="Send emulator config_2";
					String path="C:\\Users\\shobhitdutia\\.android\\avd";
					System.out.println(path);
					ViewClassList.frame1.setVisible(false);
					File f=new File(path);
					String list[]=f.list();
					File f1;
					Vector <File>fileDir=new Vector<File>();
					for (int i = 0; i < list.length; i++) {
						list[i]=path+"\\"+list[i]; 				//Add parent directories to list array
						f1=new File(list[i]);
						if(f1.isDirectory()) {
							fileDir.add(f1);						
						}
						ViewClassList.frame1.setVisible(false);	
					}
					for(File f2:fileDir) {
						System.out.println(f2.getName());
					}
					ViewSendEmuConfig.fileDir=fileDir;
					viewSendEmuConfig.showConfiguration();
				}
			}
			else if(button.getText().equals("Change password")) {
				ControllerServerBackListener.backButtoncallingFrom="Change password";
				ViewServer.frame2.setVisible(false);
				viewcp.showGUI();
			}
			else if(button.getText().equals("Change!")) {
				Vector<JTextField> textFieldVector=viewcp.getInputVector();
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
						result=obj.changePassword(textFieldVector.get(0).getText().toString(),
								textFieldVector.get(1).getText().toString(), 
								textFieldVector.get(2).getText().toString());
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
			else if(button.getText().equals("Add instructor")) {
				ControllerServerBackListener.backButtoncallingFrom="Add instructor";
				ViewServer.frame2.setVisible(false);
				viewAddInstructor.showGUI();
			}
			else if(button.getText().equals("Add")) {
				JTextField instIDText=viewAddInstructor.getInstTextfield();
				String instText=instIDText.getText().toString();
				boolean error=false;
				if(instText=="") {
					error=true;
				}
				if(error) {
					JOptionPane.showMessageDialog((Component) e.getSource(),
							"Please enter instructor ID into textfields",
							"Empty text fields",
							JOptionPane.ERROR_MESSAGE);
				}
				else {
					try {
						if(obj.addInstructor(instText)==1) 
							JOptionPane.showMessageDialog((Component) e.getSource(),
									"Instructor Added!",
									"SUCCESS",
									JOptionPane.INFORMATION_MESSAGE);
						else 
							JOptionPane.showMessageDialog((Component) e.getSource(),
									"Query Failed! Check your input for duplicate instructor ID",
									"ERROR",
									JOptionPane.ERROR_MESSAGE);
						ViewAddInstructor.frame1.setVisible(false);
						ViewAddInstructor.frame1.dispose();
						viewAddInstructor.showGUI();
					} catch (HeadlessException | RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
	}

	private void refreshUserListAndUpdateGUI(ActionEvent e, String queryType) {
		//Implement this method later
		//Get the user list from the database
		Vector userListVector=new Vector();
		String middlewareIP="localhost";

		try {

			userListVector=obj.getUsersFromDatabase("root", "mysql");
			if(userListVector.size()!=0) {
				/*JOptionPane.showMessageDialog((Component) e.getSource(),
	                    "Query Successful!",
	                    "Retreived",
	                    JOptionPane.INFORMATION_MESSAGE);
				 */System.out.println("retreival successful");

				 System.out.println(userListVector);
				 ViewAddRemoveUsers.setVectorofStudents(userListVector);
				 ViewServer.frame2.setVisible(false);
				 v.showAddRemovePage("Remove");
			}
			else if(queryType.equals("delete user")){
				JOptionPane.showMessageDialog((Component) e.getSource(),
						"Check if the database has any users",
						"ERROR",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (RemoteException
				| SQLException | InstantiationException
				| IllegalAccessException |  ClassNotFoundException e1) {
			//e1.printStackTrace();

			JOptionPane.showMessageDialog((Component) e.getSource(),
					"Check your network/database connection",
					"ERROR",
					JOptionPane.INFORMATION_MESSAGE);
		}	
	}
	private boolean isPasswordCorrect(String username, char[] password) {
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
	}
	@Override
	public void itemStateChanged(ItemEvent e) {

	}
}
