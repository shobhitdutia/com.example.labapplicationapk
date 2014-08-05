package com.example.labapplicationapk;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JTextField;

public class ControllerServerBackListener implements ActionListener {
	ControllerServer cs;
	/*ViewServer v;
	ViewAddUser viewAddUser;
	//ViewClassList viewClassList;
	ViewLog v1;*/
	static String backButtoncallingFrom;
	public ControllerServerBackListener(ControllerServer cs) {
		this.cs=cs;
		/*this.v=v;
		this.viewAddUser=viewAddUser;
	//	this.viewClassList=viewClassList;
		this.v1=v1;*/
	}

	public ControllerServerBackListener() {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton button =(JButton) e.getSource();
			if(button.getText().equals("Back")) {
				if(backButtoncallingFrom.equals("Add users_1")) {
					//ViewClassList.frame1.setVisible(false);
					ViewAddUser.frame1.setVisible(false);
					ViewServer.frame2.setVisible(true);
				}
				else if(backButtoncallingFrom.equals("Select existing class")) {
					ViewClassList.frame1.setVisible(false);
					ViewAddUser.frame1.setVisible(true);
					backButtoncallingFrom="Add users_1";
				}
				else if(backButtoncallingFrom.equals("Add class")) {
					ViewAddUser.frame2.setVisible(false);
					ViewAddUser.frame1.setVisible(true);
					backButtoncallingFrom="Add users_1";
				}
				else if(backButtoncallingFrom.equals("Adding users to textbox from new class")) {
					ViewAddUser.frame3.setVisible(false);
					ViewAddUser.frame2.setVisible(true);
					backButtoncallingFrom="Add class";
					Vector<JTextField> userListVector=ViewAddUser.getUserList();
					userListVector.removeAllElements();
					//ViewAddUser.removeAllTextboxes("");
				}
				else if(backButtoncallingFrom.equals("Adding users to textbox from existing class")) {
					ViewAddUser.frame3.setVisible(false);
					ViewClassList.frame1.setVisible(true);
					backButtoncallingFrom="Select existing class";
					Vector<JTextField> userListVector=ViewAddUser.getUserList();
					userListVector.removeAllElements();
					//ViewAddUser.removeAllTextboxes("");
				}
				else if(backButtoncallingFrom.equals("Send emulator config_1")) {
					ViewClassList.frame1.setVisible(false);
					ViewServer.frame2.setVisible(true);
				}
				else if(backButtoncallingFrom.equals("Send emulator config_2")) {
					ViewSendEmuConfig.frame1.setVisible(false);
					ViewClassList.frame1.setVisible(true);
					backButtoncallingFrom="Send emulator config_1";
				}
				else if(backButtoncallingFrom.equals("Add malware_1")) {
					//ViewAddRemoveMalware.frame1.setVisible(false)
					ViewClassList.frame1.setVisible(false);
					ViewServer.frame2.setVisible(true);
				}
				else if(backButtoncallingFrom.equals("Add malware_2")) {
					backButtoncallingFrom="Add malware_1";
					ViewAddRemoveMalware.frame1.setVisible(false);
					ViewClassList.frame1.setVisible(true);
					/*ViewClassList.frame1.setVisible(false);
					ViewServer.frame2.setVisible(true);*/
				}
				else if(backButtoncallingFrom.equals("Remove users_1")) {
					
				}
				
				else if(backButtoncallingFrom.equals("View log_1")) {
					ViewServer.frame2.setVisible(true);
					ViewClassList.frame1.setVisible(false);
				}
				else if(backButtoncallingFrom.equals("View log_2")) {
					ControllerServerBackListener.backButtoncallingFrom="View log_1";
					ViewClassList.frame1.setVisible(true);
					ViewLog.frame1.setVisible(false);
				}
				else if(backButtoncallingFrom.equals("Change password")) {
					ViewServer.frame2.setVisible(true);
					ViewChangePassword.frame1.setVisible(false);
				}
				else if(backButtoncallingFrom.equals("Add instructor")) {
					ViewServer.frame2.setVisible(true);
					ViewAddInstructor.frame1.setVisible(false);
				}
			}
		}
	}
}