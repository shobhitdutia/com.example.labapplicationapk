package com.example.labapplicationapk;
import java.awt.Component;
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


public class ControllerServer implements ActionListener, ItemListener {
	//String username;;
	String middlewareIP="localhost";
	JoinInterface obj;
	JTextField username;
	JPasswordField password;
	ViewServer v= new ViewServer(this);
	ViewLog v1;
	public ControllerServer() {
		try {
			obj= (JoinInterface)Naming.lookup("//"+middlewareIP+":12459/Shobhit_bootstrapObject");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
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
		                    "Invalid username or password",
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
				ViewServer.frame2.setVisible(false);
				v.showAddRemovePage("Add");
			}
			else if(button.getText().equals("Remove users")) {
				refreshUserListAndUpdateGUI(e, "delete user");
			}
			else if(button.getText().equals("Back")) {
				Vector<JTextField> userListVector=ViewAddRemoveUsers.getUserList();
				userListVector.removeAllElements();
				ViewAddRemoveUsers.removeAllTextboxes("");
				ViewAddRemoveUsers.frame1.setVisible(false);
				ViewServer.frame2.setVisible(true);
			}
			else if(button.getText(	).equals("Send emulator config")) {
				ViewServer.frame2.setVisible(false);
				//Code to send emulator config
				//Path of avd directory
				String path="C:\\Users\\shobhitdutia\\.android\\avd";
				System.out.println(path);
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
				}
				for(File f2:fileDir) {
					System.out.println(f2.getName());
				}
				ViewSendEmuConfig.fileDir=fileDir;
				v.showSendEmuConfig();
			}
			else if(button.getText().equals("Send selected configuration")) {
				Vector<JCheckBox> selectedConfig=ViewSendEmuConfig.getCheckedConfig();
				Vector<File> fileDir=ViewSendEmuConfig.getFileDirOfConfig();
				//check if list is empty
				System.out.println("Size of selected config is :"+selectedConfig.size());
				boolean atleastOneSelected=false;
				for(JCheckBox jc:selectedConfig) {
					if(jc.isSelected())
						atleastOneSelected=true;
				}
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
					System.out.println("Selected is ");
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
					/*System.out.println("Config pahts:");
					for(File f:configFiles) {
						System.out.println(f.getAbsolutePath());
					}*/
					//send config files
					
					try {
						
						if(obj.sendEmulatorConfiguration(configFiles)==1) {
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
					System.out.println("Size of vector "+userListVector.size());
					for(int i=0;i<userListVector.size();i++) {	
						JTextField jt=userListVector.get(i);
						if(i%2!=0) {
							try {
								Integer.parseInt(jt.getText());
							} catch(NumberFormatException e1) {
								System.out.println("Not a number");
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
						
						try {
			
							if(obj.addUsersToDatabase("root", "mysql", userListVector)==1) {
								JOptionPane.showMessageDialog((Component) e.getSource(),
					                    "Query Successful!",
					                    "INSERTED",
					                    JOptionPane.INFORMATION_MESSAGE);
								userListVector.removeAllElements();
								ViewAddRemoveUsers.removeAllTextboxes("Add");
							}
							else {
								JOptionPane.showMessageDialog((Component) e.getSource(),
					                    "Query Failed! Check your input",
					                    "EMPTY",
					                    JOptionPane.INFORMATION_MESSAGE);
								userListVector.removeAllElements();
								ViewAddRemoveUsers.removeAllTextboxes("Add");
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
				v.showMalwarePage("Add");
			}
			else if(button.getText().equals("Back to main")) {
				ViewAddRemoveMalware.frame1.setVisible(false);
				ViewServer.frame2.setVisible(true);
			}
			else if(button.getText().equals("Done adding malware")) {
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
					
				
						if(obj.addMalware(file.getName(),buffer)==1) {
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
				ViewServer.frame2.setVisible(false);
				v1=new ViewLog(this, obj);
				v1.showGUI();
			}
			else if(button.getText().equals("Open")) {
				v1.selectedfile=(String) v1.listbox.getSelectedValue();
				v1.frame1.setVisible(false);
				v1.showFile();
			}
			else if(button.getText().equals("Back to Main")) {
				//v1.frame1.setVisible(true);
				//v1.showFile();
				v1.frame1.setVisible(false);
				ViewServer.frame2.setVisible(true);
			}
			else if(button.getText().equals("Back to list")) {
				
				v1.frame1.setVisible(true);
				v1.frame2.setVisible(false);
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
	private String getUsername() {
		return username.getText();
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
