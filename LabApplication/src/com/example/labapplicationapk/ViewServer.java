package com.example.labapplicationapk;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;

public class ViewServer extends JFrame {
	JTextField username;
	JPasswordField password;
	static JFrame frame1;
	static JFrame frame2;
	ViewRemoveUser addRemoveUserObject;
//	ViewAddRemoveMalware addRemoveMalwareObject;
	//ViewSendEmuConfig sendEmuConfig;
	Dimension d=new Dimension(100,50);
	ControllerServer cs;
	public ViewServer(ControllerServer controllerServer) {
		cs=controllerServer;
	}
	public ViewServer() {
	}
	public void showGUI() {
		frame1 = new JFrame("LAB APPLICATION");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentsToLoginFrame(frame1.getContentPane());
        frame1.setLocationRelativeTo(null);
        frame1.setResizable(false);
        frame1.pack();
        frame1.setVisible(true);
	}

	private void addComponentsToLoginFrame(final Container contentPane) {
		//labels and text box panel
		final JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2,2));
        username=new JTextField();
        password=new JPasswordField();
        inputPanel.add(new JLabel(" Username: "));
        inputPanel.add(username);
        inputPanel.add(new JLabel(" Password: "));
        inputPanel.add(password);
       
        //Button panel
        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,2));
        JButton loginButton=new JButton("Login");
        loginButton.addActionListener(cs);

        JButton cancelButton=new JButton("Clear");
        cancelButton.addActionListener(cs);
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);
        contentPane.add(new JLabel("Please enter your username and password"), BorderLayout.PAGE_START);
        //contentPane.add(new JSeparator());
        contentPane.add(inputPanel, BorderLayout.CENTER);
       // contentPane.add(new JSeparator(), BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.PAGE_END);
        //contentPane.setPreferredSize(getMaximumSize());
        JButton b = new JButton("Just fake button");
      //  Dimension buttonSize = b.getPreferredSize();
       // buttonPanel.setPreferredSize(new Dimension((int)(buttonSize.getWidth() * 2.5),
         //x       (int)(buttonSize.getHeight() * 1.5) ));
        
	}

	public void showMainScreen() {
		frame1.setVisible(false);
		frame2 = new JFrame("LAB APPLICATION");
     	frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentsToMainFrame(frame2.getContentPane());
        frame2.setLocationRelativeTo(null);
        frame2.setResizable(false);
        frame2.pack();
        frame2.setVisible(true);		
	}

	private void addComponentsToMainFrame(Container contentPane) {
		GridLayout mainFrameLayout = new GridLayout(4,2);
		mainFrameLayout.setHgap(10);
		mainFrameLayout.setVgap(10);
        //Button panel
        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(mainFrameLayout);
        JButton addUserButton=new JButton("Add users");
        addUserButton.addActionListener(cs);
        JButton removeUserButton=new JButton("Remove users");
        removeUserButton.addActionListener(cs);
        JButton malwareButton=new JButton("Add malware");
        malwareButton.addActionListener(cs);
        JButton logButton=new JButton("View log");
        logButton.addActionListener(cs);
        JButton configButton=new JButton("Send emulator config");
        configButton.addActionListener(cs);
        JButton changePasswordButton=new JButton("Change password");
        changePasswordButton.addActionListener(cs);
        JButton addInstructorButton=new JButton("Add instructor");
        addInstructorButton.addActionListener(cs);        
        
        JButton logoutButton=new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ViewServer.frame2.setVisible(false);
				ViewServer.frame1.setVisible(true);			}
		});
        
        buttonPanel.add(addUserButton);
        buttonPanel.add(removeUserButton);
        buttonPanel.add(malwareButton);
        buttonPanel.add(logButton);
        buttonPanel.add(configButton);
        buttonPanel.add(logoutButton);
        buttonPanel.add(changePasswordButton);
        buttonPanel.add(addInstructorButton);
        contentPane.add(buttonPanel, BorderLayout.CENTER);
	}	
	/*public void showAddRemovePage(String queryType) {
		addRemoveUserObject=new ViewRemoveUser();
		if(queryType=="Add") {}
//			addRemoveUserObject.showAddUserList();
		else 
			addRemoveUserObject.showRemoveUserList();
	}*/
	/*public void showMalwarePage(String queryType) {
		addRemoveMalwareObject=new ViewAddRemoveMalware(new ControllerServerBackListener(cs));
		if(queryType=="Add")
			addRemoveMalwareObject.showAddMalwareList();
		else 
			addRemoveMalwareObject.showRemoveMalwareList();;
	}*/

	/*public void showSendEmuConfig() {
		sendEmuConfig=new ViewSendEmuConfig();
		sendEmuConfig.showConfiguration();
	}*/
}