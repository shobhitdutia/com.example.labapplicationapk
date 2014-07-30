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
import javax.swing.JList;
import javax.swing.JPanel;

public class ViewClassList {
	ControllerServer controllerServer;
	JoinInterface service;
	JFrame frame1;
	JList<String> listbox;
	String selectedClass;
	String callingFrom;
	
	public ViewClassList(ControllerServer contoller,JoinInterface service){
		controllerServer=contoller;
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
		List<String> list;
		try {
			list = service.classes();
			if(!list.isEmpty()){
				Collections.sort(list);
			
				listbox = new JList( list.toArray());
			
				inputPanel.setLayout( new BorderLayout() );
				buttonPanel.setLayout(new GridLayout(1,2));
				inputPanel.add(listbox,BorderLayout.CENTER);

				JButton openButton=new JButton("Select");
				JButton backButton=new JButton("Back to Main");
				openButton.addActionListener(controllerServer);
				backButton.addActionListener(controllerServer);
				buttonPanel.add(openButton);
				buttonPanel.add(backButton);
				contentPane.add(inputPanel, BorderLayout.NORTH);
				contentPane.add(buttonPanel, BorderLayout.SOUTH);
			}else{
				TextArea t = new TextArea("No logs present");
				t.setBackground(Color.WHITE);
				t.setEditable(false);
				contentPane.add(t);
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
