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

public class ViewAddUser extends JFrame {
	static JFrame frame1;
	JTextField classnameTextField;
	ControllerServer cs;
	public ViewAddUser(ControllerServer cs) {
		this.cs=cs;
	}
	public void showGUI() {
		frame1 = new JFrame("Add classname");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentsToAddUser_1(frame1.getContentPane());
        frame1.setLocationRelativeTo(null);
        frame1.setResizable(false);
        frame1.pack();
        frame1.setVisible(true);
	}

	private void addComponentsToAddUser_1(final Container contentPane) {
		//labels and text box panel
		final JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(1,1));
        classnameTextField=new JTextField();
        inputPanel.add(new JLabel(" Class Name: "));
        inputPanel.add(classnameTextField);
   
        //Button panel
        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,1));
        JButton OkButton=new JButton("Ok");
        OkButton.addActionListener(cs);

        JButton backButton=new JButton("Back");
        //cancelButton.addActionListener(cs);
        buttonPanel.add(OkButton);
        buttonPanel.add(backButton);
        contentPane.add(inputPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.PAGE_END);
        JButton b = new JButton("Just fake button");
	}
}