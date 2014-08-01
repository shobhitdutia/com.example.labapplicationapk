package com.example.labapplicationapk;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;

public class ViewAddUser extends JFrame {
	static JFrame frame2, frame1, frame3;
	JTextField classnameTextField;
	ControllerServer cs;
	ControllerServerBackListener csb;
    private static Vector vectorOfStudents;
    private static Vector <JCheckBox>checkBoxVector;
    int textBoxNumber=0;
    private Box vBox;
    private Box hBox;
    private JButton btnAdd;
    private static Vector<JTextField> textFieldVector=new Vector<JTextField>();
    static String newClassName;
    
	public ViewAddUser(ControllerServer cs, ControllerServerBackListener csb) {
		this.cs=cs;
		this.csb=csb;
	}
	public void showGUI1() {
		frame1 = new JFrame("Add user");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentsToAddUser_1(frame1.getContentPane());
        frame1.setLocationRelativeTo(null);
        frame1.setResizable(false);
        frame1.pack();
        frame1.setVisible(true);
	}
	private void addComponentsToAddUser_1(final Container contentPane) {
        //Button panel
        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3,1));
        
        //Buttons
        JButton addButton=new JButton("Add class");
        addButton.addActionListener(cs);
        JButton selectButton=new JButton("Select existing class");
        selectButton.addActionListener(cs);
        JButton backButton=new JButton("Back");
        backButton.addActionListener(csb);
        
        buttonPanel.add(addButton);
        buttonPanel.add(selectButton);
        buttonPanel.add(backButton);

        contentPane.add(buttonPanel);
	}
	public void showGUI2() {
		frame2 = new JFrame("Add classname");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentsToAddUser_2(frame2.getContentPane());
        frame2.setLocationRelativeTo(null);
        frame2.setResizable(false);
        frame2.pack();
        frame2.setVisible(true);
	}
	private void addComponentsToAddUser_2(final Container contentPane) {
		//labels and text box panel
		final JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(1,1));
        classnameTextField=new JTextField();
        inputPanel.add(new JLabel("Class Name: "));
        inputPanel.add(classnameTextField);
   
        //Button panel
        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,1));
        JButton OkButton=new JButton("Ok");
        OkButton.addActionListener(cs);

        JButton backButton=new JButton("Back");
        backButton.addActionListener(csb);
        buttonPanel.add(OkButton);
        buttonPanel.add(backButton);
        contentPane.add(inputPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.PAGE_END);
        JButton b = new JButton("Just fake button");
	}
	public void showGUI3() {
		frame3 = new ViewAddRemoveUsers("ADD Users");
        frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentsToAddUser_3(frame3.getContentPane());
        frame3.setLocationRelativeTo(null);
        frame3.setResizable(true);
        frame3.pack();
        frame3.setVisible(true);
	}
	
	private void addComponentsToAddUser_3(final Container contentPane) {
		vectorOfStudents=new Vector();
		checkBoxVector=new Vector<JCheckBox>();
		//Remove this for loop later	
		int length=4;
/*		//Get student name and ID in a vector
		for (int i = 0; i <=length-2; i+=2) {	
			vectorOfStudents.add("SampleName");
			vectorOfStudents.add("SampleID");
		}
*/		for (int i = 0; i < 2; i++) {
			vectorOfStudents.add("SampleName"+i);	
		}

		final JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(3,3));
		labelPanel.add(new JLabel(""));
		labelPanel.add(new JLabel("User name"));
//		labelPanel.add(new JLabel("UserID"));
		
		//Add checkboxes and label names with student name and ID
		int countOfCheckBoxes=0;
//		for (int i = 0; i <= length-2; i+=2) {
		for (int i = 0; i <vectorOfStudents.size(); i++) {
			checkBoxVector.add(new JCheckBox());
			labelPanel.add(checkBoxVector.get(countOfCheckBoxes++));
			/*labelPanel.add(new JLabel((String)vectorOfStudents.get(i)));
			labelPanel.add(new JLabel(String.valueOf(vectorOfStudents.get(i+1))));*/
			labelPanel.add(new JLabel((String)vectorOfStudents.get(i)));
		}
	 
		System.out.println(textFieldVector.size());
		vBox = Box.createVerticalBox(); 
		JButton selectFile = new JButton("Select file");
		selectFile.addActionListener(cs);
		
		btnAdd = new JButton("Add new user");
		JButton buttonBack = new JButton("Back");
		buttonBack.addActionListener(csb);
		JButton buttonDone = new JButton("Done adding");
		buttonDone.addActionListener(new ControllerServer());
        btnAdd.addActionListener(new ActionListener(){
       
            @Override
            public void actionPerformed(ActionEvent e)
            {
            	System.out.println("Called");
            	textFieldVector.add(new JTextField());
//        		textFieldVector.add(new JTextField());
        		hBox=Box.createHorizontalBox();
        		hBox.add(textFieldVector.get(textBoxNumber));
//        		hBox.add(textFieldVector.get(textBoxNumber+1));
        		textBoxNumber++;
        		vBox.add(hBox);
        		frame3.pack();
            }

        });
       
		
        //ButtonPanel
        final JPanel buttonPanel=new JPanel();
        buttonPanel.add(selectFile);
        buttonPanel.add(btnAdd);
        buttonPanel.add(buttonDone);
        buttonPanel.add(buttonBack);
       
        //adding everything to container
        contentPane.add(labelPanel, BorderLayout.NORTH);
        
        contentPane.add(vBox, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	public void setNewClassName() {
		newClassName=classnameTextField.getText().toString();
	}
	public void setExistingClassName(String newClassName){
		this.newClassName=newClassName;
	}
	public static void setVectorofStudents(Vector v) {
		vectorOfStudents=v;
	}
	/*public void removeAllTextboxes() {	
		frame3.setVisible(false);
		frame3.dispose();
		this.showGUI3();
	}*/
	public static Vector<JTextField> getUserList() {
		for (int i = 0; i < checkBoxVector.size(); i++) {
			JCheckBox jc=checkBoxVector.get(i);
			if(jc.isSelected()) {
				textFieldVector.add(new JTextField((String)vectorOfStudents.get(i)));
	//        	textFieldVector.add(new JTextField(String.valueOf(vectorOfStudents.get((i*2)+1))));
			}
		}
		System.out.println("Size of vector is "+textFieldVector.size());
		System.out.println(vectorOfStudents);
		
		//textFieldVector.add(e)
		checkBoxVector.removeAllElements();
		vectorOfStudents.removeAllElements();
		return textFieldVector;
	}
	/*public static void removeAllTextboxes(String frameName) {	
		frame1.setVisible(false);
		frame1.dispose();
		if(frameName=="Add")
			new ViewAddRemoveUsers().showAddUserList();
		else if(frameName=="Remove")
			new ViewAddRemoveUsers().showRemoveUserList();
	}*/
}