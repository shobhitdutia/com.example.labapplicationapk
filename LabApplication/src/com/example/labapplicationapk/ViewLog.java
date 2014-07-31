package com.example.labapplicationapk;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;


public class ViewLog {
	ControllerServer controllerServer;
	JoinInterface service;
	JFrame frame1,frame2;
	JList<String> listbox;
	String selectedfile;

	public ViewLog(ControllerServer contoller, JoinInterface service) {
		controllerServer=contoller;
		this.service=service;
	}

	public void showGUI() {
		frame1 = new JFrame("View Logs");
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addComponentsToListFrame(frame1.getContentPane());
		frame1.setLocationRelativeTo(null);
		frame1.setResizable(false);
		frame1.pack();
		frame1.setVisible(true);
	}

	private void addComponentsToListFrame(final Container contentPane) {
		//labels and text box panel
		final JPanel inputPanel = new JPanel();
		final JPanel buttonPanel = new JPanel();
		List<String> list;
		try {
			list = service.files(controllerServer.selectedClass);
			if(!list.isEmpty()){
				Collections.sort(list);
			
				listbox = new JList( list.toArray());
			
				inputPanel.setLayout( new BorderLayout() );
				buttonPanel.setLayout(new GridLayout(1,2));
				inputPanel.add(listbox,BorderLayout.CENTER);

				JButton openButton=new JButton("Open");
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

	public void showFile() {
		frame1.setVisible(false);
		frame2 = new JFrame(selectedfile);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addComponents(frame2.getContentPane());
		frame2.setLocationRelativeTo(null);
		frame2.setResizable(false);
		frame2.pack();
		frame2.setVisible(true);
	}

	private void addComponents(Container contentPane) {

		try {
			final JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridLayout(1,2));
			JButton backButton=new JButton("Back to list");
			backButton.addActionListener(controllerServer);
			
			byte[] buffer = service.downloadFile(selectedfile,controllerServer.selectedClass);
			String data = stringToBytesASCII(buffer);
			TextArea t = new TextArea(data);
			t.setBackground(Color.WHITE);
			t.setEditable(false);
			contentPane.add(t);
			
			buttonPanel.add(backButton);
			contentPane.add(buttonPanel, BorderLayout.SOUTH);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	public static String stringToBytesASCII(byte[] in) {
	
		char[] b = new char[in.length];
		for (int i = 0; i < b.length; i++) {
			b[i] = (char)in[i];
		}
		String toreturn= new String(b);
		return toreturn;
	}
}
