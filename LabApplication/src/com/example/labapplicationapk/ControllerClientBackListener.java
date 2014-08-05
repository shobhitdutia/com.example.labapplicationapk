package com.example.labapplicationapk;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JTextField;

public class ControllerClientBackListener implements ActionListener {
	ControllerClient cc;
	/*ViewServer v;
	ViewAddUser viewAddUser;
	//ViewClassList viewClassList;
	ViewLog v1;*/
	static String backButtoncallingFrom;
	public ControllerClientBackListener(ControllerClient cc) {
		this.cc=cc;
		/*this.v=v;
		this.viewAddUser=viewAddUser;
	//	this.viewClassList=viewClassList;
		this.v1=v1;*/
	}

	public ControllerClientBackListener() {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton button =(JButton) e.getSource();
			if(button.getText().equals("Back")) {
				if(backButtoncallingFrom.equals("Change password")) {
					ViewClient.frame2.setVisible(true);
					ViewChangePasswordClient.frame1.setVisible(false);
				}
			}
		}
	}
}