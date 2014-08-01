package com.example.labapplicationapk;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


public class ViewAddRemoveUsers extends JFrame {
	static ViewAddRemoveUsers frame1;
	int textBoxNumber=0;
    private Box vBox;
    private Box hBox;
    private JButton btnAdd;
    private static Vector <JCheckBox>checkBoxVector;
    private static Vector<JTextField> textFieldVector=new Vector<JTextField>();
    private static Vector vectorOfStudents;
    
    /*    private static Vector vectorOfStudents;
    private static Vector<JTextField> userListVector=new Vector<JTextField>();
*/	public ViewAddRemoveUsers(String title) {
		super(title);
	}
	public ViewAddRemoveUsers() {
	}
	/*public void showAddUserList() {
		frame1 = new ViewAddRemoveUsers("ADD Users");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.addComponentsToAddFrame(frame1.getContentPane());
        frame1.setLocationRelativeTo(null);
        frame1.setResizable(true);
        frame1.pack();
        frame1.setVisible(true);
	}*/
	public void showRemoveUserList() {
		frame1 = new ViewAddRemoveUsers("REMOVE Users");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.addComponentsToRemoveFrame(frame1.getContentPane());
        frame1.setLocationRelativeTo(null);
        frame1.setResizable(true);
        frame1.pack();
        frame1.setVisible(true);
	}
	
	
	private void addComponentsToRemoveFrame(Container contentPane) {
		System.out.println("Vector of students is "+vectorOfStudents);
		checkBoxVector=new Vector<JCheckBox>();
		int length=vectorOfStudents.size();
	 	
    /*	//Get student name and ID in a vector
		for (int i = 0; i <=length-2; i+=2) {	
			vectorOfStudents.add("SampleName");
			vectorOfStudents.add(i+1);
		}
	*/
		GridLayout labelGrid=new GridLayout();
		labelGrid.setColumns(3);
		labelGrid.setRows(length/2+1);
		final JPanel labelPanel = new JPanel();
		labelPanel.setLayout(labelGrid);
		labelPanel.add(new JLabel(""));
		labelPanel.add(new JLabel("User name"));
		labelPanel.add(new JLabel("UserID"));
		
		//Add checkboxes and label names with student name and ID
		int countOfCheckBoxes=0;
		for (int i = 0; i <= length-2; i+=2) {
			checkBoxVector.add(new JCheckBox());
			labelPanel.add(checkBoxVector.get(countOfCheckBoxes++));
			labelPanel.add(new JLabel((String)vectorOfStudents.get(i)));
			labelPanel.add(new JLabel(String.valueOf(vectorOfStudents.get(i+1))));
		}
		JScrollPane js=new JScrollPane(labelPanel);
		js.setPreferredSize(new Dimension(200,200));
		//add(js);
		//textBoxNumber=length;
		System.out.println(textFieldVector.size());
		
		vBox = Box.createVerticalBox(); 
		btnAdd = new JButton("Add user to delete");
		JButton buttonBack = new JButton("Back");
		buttonBack.addActionListener(new ControllerServer());
		JButton buttonDone = new JButton("Done deleting");
		buttonDone.addActionListener(new ControllerServer());
        btnAdd.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e)
            {
            	System.out.println("Called");
            	textFieldVector.add(new JTextField());
        		textFieldVector.add(new JTextField());
        		hBox=Box.createHorizontalBox();
        		hBox.add(textFieldVector.get(textBoxNumber));
        		hBox.add(textFieldVector.get(textBoxNumber+1));
        		textBoxNumber+=2;
        		vBox.add(hBox);
        		pack();
            }

        });
       
		
        //ButtonPanel
        final JPanel buttonPanel=new JPanel();
        buttonPanel.add(btnAdd);
        buttonPanel.add(buttonDone);
        buttonPanel.add(buttonBack);
       
        //adding everything to container
        contentPane.add(js, BorderLayout.NORTH);
        contentPane.add(vBox, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

	}
	/*private void addComponentsToAddFrame(Container contentPane) {
		vectorOfStudents=new Vector();
		checkBoxVector=new Vector<JCheckBox>();
		//Remove this for loop later	
		int length=4;
//		System.out.println("adasaaaaaaaaaaaaa "+(arrayOfStudents-2));
		
		//Get student name and ID in a vector
		for (int i = 0; i <=length-2; i+=2) {	
			vectorOfStudents.add("SampleName");
			vectorOfStudents.add(i+1);
		}
		
		for (int i = 0; i <= length-2; i+=2) {
			System.out.println(i+" "+(i+1));
        	textFieldVector.add(new JTextField((String)arrayOfStudents.get(i)));
        	textFieldVector.add(new JTextField(String.valueOf(arrayOfStudents.get(i+1))));
        	System.out.println(i);
		}
		//final GridLayout labelGrid=new GridLayout(3, 2);
		final JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(3,3));
		labelPanel.add(new JLabel(""));
		labelPanel.add(new JLabel("User name"));
		labelPanel.add(new JLabel("UserID"));
		
		//Add checkboxes and label names with student name and ID
		int countOfCheckBoxes=0;
		for (int i = 0; i <= length-2; i+=2) {
			checkBoxVector.add(new JCheckBox());
			labelPanel.add(checkBoxVector.get(countOfCheckBoxes++));
			labelPanel.add(new JLabel((String)vectorOfStudents.get(i)));
			labelPanel.add(new JLabel(String.valueOf(vectorOfStudents.get(i+1))));
		}
	 
		//textBoxNumber=length;
		System.out.println(textFieldVector.size());
		vBox = Box.createVerticalBox(); 
		btnAdd = new JButton("Add new user");
		JButton buttonBack = new JButton("Back");
		buttonBack.addActionListener(new ControllerServer());
		JButton buttonDone = new JButton("Done adding");
		buttonDone.addActionListener(new ControllerServer());
        btnAdd.addActionListener(new ActionListener(){
       
            @Override
            public void actionPerformed(ActionEvent e)
            {
            	System.out.println("Called");
            	textFieldVector.add(new JTextField());
        		textFieldVector.add(new JTextField());
        		hBox=Box.createHorizontalBox();
        		hBox.add(textFieldVector.get(textBoxNumber));
        		hBox.add(textFieldVector.get(textBoxNumber+1));
        		textBoxNumber+=2;
        		vBox.add(hBox);
        		pack();
            }

        });
       
		
        //ButtonPanel
        final JPanel buttonPanel=new JPanel();
        buttonPanel.add(btnAdd);
        buttonPanel.add(buttonDone);
        buttonPanel.add(buttonBack);
       
        //adding everything to container
        contentPane.add(labelPanel, BorderLayout.NORTH);
        
        contentPane.add(vBox, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

	}*/
	public static Vector<JTextField> getUserList() {
		for (int i = 0; i < checkBoxVector.size(); i++) {
			JCheckBox jc=checkBoxVector.get(i);
			if(jc.isSelected()) {
				textFieldVector.add(new JTextField((String)vectorOfStudents.get(i*2)));
	        	textFieldVector.add(new JTextField(String.valueOf(vectorOfStudents.get((i*2)+1))));
			}
		}
		System.out.println("Size of vector is "+textFieldVector.size());
		System.out.println(vectorOfStudents);
		
		//textFieldVector.add(e)
		checkBoxVector.removeAllElements();
		vectorOfStudents.removeAllElements();
		return textFieldVector;
	}
	public static void setVectorofStudents(Vector v) {
		vectorOfStudents=v;
	}
	public static void removeAllTextboxes(String frameName) {	
		frame1.setVisible(false);
		frame1.dispose();
		/*if(frameName=="Add")
			new ViewAddRemoveUsers().showAddUserList();
		else*/ if(frameName=="Remove")
			new ViewAddRemoveUsers().showRemoveUserList();
	}
}
