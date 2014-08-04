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

public class ViewAddInstructor extends JFrame {
	static JFrame frame1;
	ControllerServer cs;
	ControllerServerBackListener csb;
	JTextField instIDText;
	
	public ViewAddInstructor(ControllerServer cs, ControllerServerBackListener csb) {
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
	    inputPanel.setLayout(new GridLayout(1,2));
	    
	    instIDText=new JTextField();	    
	    inputPanel.add(new JLabel("Enter instructor ID"));
	    inputPanel.add(instIDText);
	    
	    //Button panel
        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,2));
        
        //Buttons
        JButton addInstButton=new JButton("Add");
        addInstButton.addActionListener(cs);
        JButton backButton=new JButton("Back");
        backButton.addActionListener(csb);
        
        buttonPanel.add(addInstButton);
        buttonPanel.add(backButton);
        
        contentPane.add(inputPanel, BorderLayout.NORTH);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
	}
	public JTextField getInstTextfield() {
		return this.instIDText;
	}
}