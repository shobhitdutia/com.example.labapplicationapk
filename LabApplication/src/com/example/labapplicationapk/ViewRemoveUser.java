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


public class ViewRemoveUser extends JFrame {
	static JFrame frame1, frame2;
	int textBoxNumber=0;
    private static Vector <JCheckBox>checkBoxVector;
    private static Vector<String> textFieldVector=new Vector<String>();
    private static Vector<String> vectorOfStudents;
    ControllerServer cs;
    ControllerServerBackListener csb;
	private String className;
	public ViewRemoveUser(ControllerServer cs, ControllerServerBackListener csb) {
		this.cs=cs;
		this.csb=csb;
	}
    public void showGUI1() {
		frame1 = new JFrame("Remove user");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentsToRemoveUser_1(frame1.getContentPane());
        frame1.setLocationRelativeTo(null);
        frame1.setResizable(false);
        frame1.pack();
        frame1.setVisible(true);
	}
	private void addComponentsToRemoveUser_1(final Container contentPane) {
        //Button panel
        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2,1));
        //Buttons
        JButton selectButton=new JButton("Select existing class to remove");
        selectButton.addActionListener(cs);
        JButton backButton=new JButton("Back");
        backButton.addActionListener(csb);
        buttonPanel.add(selectButton);
        buttonPanel.add(backButton);
        contentPane.add(buttonPanel);
	}
    
	
	public void showRemoveUserList() {
		frame2 = new JFrame("Remove Users");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentsToRemoveFrame(frame2.getContentPane());
        frame2.setLocationRelativeTo(null);
        frame2.setResizable(true);
        frame2.pack();
        frame2.setVisible(true);
	}	
	private void addComponentsToRemoveFrame(Container contentPane) {
		System.out.println("Vector of students is "+vectorOfStudents);
		checkBoxVector=new Vector<JCheckBox>();
		int length=vectorOfStudents.size();
		GridLayout labelGrid=new GridLayout();
		labelGrid.setColumns(2);
		labelGrid.setRows(length);
		final JPanel labelPanel = new JPanel();
		labelPanel.setLayout(labelGrid);
		labelPanel.add(new JLabel(""));
		labelPanel.add(new JLabel("User name"));
		
		//Add checkboxes and label names with student name and ID
		for (int i = 0; i <length; i++) {
			JCheckBox jc=new JCheckBox(vectorOfStudents.get(i));
			checkBoxVector.add(jc);
			labelPanel.add(jc);
		}
		JScrollPane js=new JScrollPane(labelPanel);
		js.setPreferredSize(new Dimension(200,200));
		System.out.println(textFieldVector.size());
		JButton buttonBack = new JButton("Back");
		buttonBack.addActionListener(csb);
		JButton buttonDone = new JButton("Done deleting");
		buttonDone.addActionListener(cs);
        //ButtonPanel
        final JPanel buttonPanel=new JPanel();
        buttonPanel.add(buttonDone);
        buttonPanel.add(buttonBack);       
        contentPane.add(labelPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

	}
	public static Vector<String> getUserList() {
		for (JCheckBox jc:checkBoxVector) {
			if(jc.isSelected()) {
				textFieldVector.add(jc.getText().toString());
	        }
		}
		System.out.println("Size of vector is "+textFieldVector.size());
		checkBoxVector.removeAllElements();
		vectorOfStudents.removeAllElements();
		return textFieldVector;
	}
	public static void setVectorofStudents(Vector<String> v) {
		vectorOfStudents=v;
	}
	public void removeAllTextboxes() {	
		frame2.setVisible(false);
		frame2.dispose();
	}
	public void setExistingClassName(String className){
		this.className=className;
	}
	public String getExistingClassName(){
		return className;
	}
	public static void removeUsersFromUI() {
		textFieldVector.removeAllElements();
		checkBoxVector.removeAllElements();
	}
}