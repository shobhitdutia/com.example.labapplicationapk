package com.example.labapplicationapk;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ViewChangePassword extends JFrame {
	static JFrame frame1;
	ControllerServer cs;
	ControllerServerBackListener csb;
	JTextField userIDText;
	JTextField oldPassText;
	JTextField newPassText;
	private Vector<JTextField> textFieldVector=new Vector<JTextField>();
    
	
	public ViewChangePassword(ControllerServer cs, ControllerServerBackListener csb) {
		this.cs=cs;
		this.csb=csb;
	}
	public void showGUI() {
		frame1 = new JFrame("Change password");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentsToChangePassword(frame1.getContentPane());
        frame1.setLocationRelativeTo(null);
        frame1.setResizable(false);
        frame1.pack();
        frame1.setVisible(true);
	}
	private void addComponentsToChangePassword(final Container contentPane) {
        //Input panel
	    final JPanel inputPanel = new JPanel();
	    inputPanel.setLayout(new GridLayout(3,2));
	    
	    userIDText=new JTextField();
	    oldPassText=new JTextField();
	    newPassText=new JTextField();
	    textFieldVector.add(userIDText);
	    textFieldVector.add(oldPassText);
	    textFieldVector.add(newPassText);
	    
	    inputPanel.add(new JLabel("Enter your old username"));
	    inputPanel.add(userIDText);
	    inputPanel.add(new JLabel("Enter your old password"));
	    inputPanel.add(oldPassText);
	    inputPanel.add(new JLabel("Enter your new password"));
	    inputPanel.add(newPassText);
	    
		//Button panel
        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3,1));
        
        //Buttons
        JButton changeButton=new JButton("Change!");
        changeButton.addActionListener(cs);
        JButton backButton=new JButton("Back");
        backButton.addActionListener(csb);
        
        buttonPanel.add(changeButton);
        buttonPanel.add(backButton);
        
        contentPane.add(inputPanel, BorderLayout.NORTH);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
	}
	public Vector<JTextField> getInputVector() {
		return this.textFieldVector;
	}
}