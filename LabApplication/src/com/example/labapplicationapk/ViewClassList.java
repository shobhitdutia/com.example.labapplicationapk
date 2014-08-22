package com.example.labapplicationapk;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class ViewClassList {
	ControllerServer controllerServer;
	ControllerServerBackListener csb;
	JoinInterface service;
	static JFrame frame1;
	JList<String> listbox;
	String selectedClass;
	String callingFrom;
	
	public ViewClassList(ControllerServer contoller,ControllerServerBackListener csb, JoinInterface service){
		controllerServer=contoller;
		this.csb=csb;
		this.service=service;
	}
	
	public void showGUI() {
		frame1 = new JFrame("View List of Classes");
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addComponentsToListFrame(frame1.getContentPane());
		frame1.setLocationRelativeTo(null);
		frame1.setResizable(false);
		frame1.pack();
		frame1.setVisible(true);
	}
	public void setCallingLocation(String temp){
		callingFrom=temp;
	}
	private void addComponentsToListFrame(final Container contentPane) {
		//labels and text box panel
		final JPanel inputPanel = new JPanel();
		final JPanel buttonPanel = new JPanel();
		final JPanel textPanel = new JPanel();
		List<String> list;
		try {
			list = service.getClasses();
			JButton backButton=new JButton("Back");
			backButton.addActionListener(csb);
			if(!list.isEmpty()){
				Collections.sort(list);
			
				listbox = new JList( list.toArray());
			
				inputPanel.setLayout( new BorderLayout() );
				buttonPanel.setLayout(new GridLayout(1,2));
				inputPanel.add(listbox,BorderLayout.CENTER);

				JButton selectButton=new JButton("Select");
				selectButton.addActionListener(controllerServer);
				buttonPanel.add(selectButton);
				buttonPanel.add(backButton);
				contentPane.add(new JLabel("Please select a class"), BorderLayout.PAGE_START);
				contentPane.add(inputPanel, BorderLayout.CENTER);
				contentPane.add(buttonPanel, BorderLayout.PAGE_END);
			}else{
				textPanel.setLayout(new GridLayout(2,1));
				
				textPanel.add(new JLabel("No files present. Please add a class first!"));
				textPanel.add(backButton);
				contentPane.add(textPanel);
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
