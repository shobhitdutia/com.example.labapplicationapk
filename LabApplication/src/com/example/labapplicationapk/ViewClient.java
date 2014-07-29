package com.example.labapplicationapk;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ViewClient extends JFrame {
	JTextField username;
	JPasswordField password;
	ControllerClient controllerClient;
	JFrame frame1,frame2;

	Dimension d=new Dimension(100,50);
	
	public ViewClient(String title) {
		super(title);
	}

	public ViewClient(ControllerClient contoller) {
		controllerClient=contoller;
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
        buttonPanel.setLayout(new GridLayout(1,0));
        JButton loginButton=new JButton("Login");
        loginButton.addActionListener(controllerClient);
        JButton cancelButton=new JButton("Clear");
        cancelButton.addActionListener(controllerClient);
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);
        contentPane.add(new JLabel("Please enter your username and password"), BorderLayout.PAGE_START);
        contentPane.add(inputPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.PAGE_END);
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
		GridLayout mainFrameLayout = new GridLayout(3,2);
		mainFrameLayout.setHgap(10);
		mainFrameLayout.setVgap(10);
        //Button panel
        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(mainFrameLayout);
        JButton downloadConfigruationButton=new JButton("Download Configruation");
        downloadConfigruationButton.addActionListener(controllerClient);
        JButton downloadMalwareButton=new JButton("Download Malware");
        downloadMalwareButton.addActionListener(controllerClient);
        JButton backupEmulatorButton=new JButton("Backup Emulator");
        backupEmulatorButton.addActionListener(controllerClient);
        JButton restoreEmulatorButton=new JButton("Restore Emulator");
        restoreEmulatorButton.addActionListener(controllerClient);
//        JButton terminalButton=new JButton("Open Terminal");
//        terminalButton.addActionListener(controllerClient);
        JButton logoutButton=new JButton("Logout");
        logoutButton.addActionListener(controllerClient);

        buttonPanel.add(downloadConfigruationButton);
        buttonPanel.add(downloadMalwareButton);
        buttonPanel.add(backupEmulatorButton);
        buttonPanel.add(restoreEmulatorButton);
      //  buttonPanel.add(terminalButton);
        buttonPanel.add(logoutButton);
        contentPane.add(buttonPanel, BorderLayout.CENTER);
	}	
}
