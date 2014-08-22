package com.example.labapplicationapk;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
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
    private static Vector <JCheckBox>checkBoxVector;
    //int textBoxNumber=0;
    private Box vBox;
    private Box hBox;
    private Box userVBox;
    private JButton btnAdd;
    private static Vector<JTextField> textFieldVector=new Vector<JTextField>();
    static String newClassName;
	private static JFileChooser openFile;
    
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
        JButton selectButton=new JButton("Select existing class to add");
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
		frame3 = new JFrame("ADD Users");
        frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentsToAddUser_3(frame3.getContentPane());
        frame3.setLocationRelativeTo(null);
        frame3.setResizable(true);
        frame3.pack();
        frame3.setVisible(true);
	}
	
	private void addComponentsToAddUser_3(final Container contentPane) {
		//vBox = Box.createVerticalBox(); 
		userVBox= Box.createVerticalBox(); 
		JButton btnBrowse=new JButton("Browse");
		checkBoxVector=new Vector<JCheckBox>();	    
		btnBrowse.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
            	System.out.println(e.getSource());
        		openFile = new JFileChooser();	
         		openFile.showOpenDialog(null);   		
        		parseFile();
        		for (int i = 0; i <checkBoxVector.size(); i++) {
        			userVBox.add(checkBoxVector.get(i));     			
        		}
        		frame3.pack();
            }

			private void parseFile() {
				BufferedReader br=null;
    			try {
    				File selectStudents=new File(openFile.getSelectedFile().getAbsolutePath());
    				System.out.println(selectStudents.getAbsolutePath());
					String studentName;
    				br=new BufferedReader(new FileReader(selectStudents));
					while((studentName = br.readLine()) != null) { 
						JCheckBox studentNameJCheckBox=new JCheckBox(studentName);
						checkBoxVector.add(studentNameJCheckBox);
						System.out.println(studentName); 
					} 
				} catch (IOException |NullPointerException e1) {
					e1.printStackTrace();
				}
    			finally {
    				if(br!=null)
						try {
							br.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
    			}
			}

        });
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
            	JTextField jt=new JTextField();
            	textFieldVector.add(jt);
        		hBox=Box.createHorizontalBox();
        		hBox.add(jt);
        		userVBox.add(hBox);
        		frame3.pack();
            }

        });
       
		
        //ButtonPanel
        final JPanel buttonPanel=new JPanel();
        buttonPanel.add(btnBrowse);
        buttonPanel.add(btnAdd);
        buttonPanel.add(buttonDone);
        buttonPanel.add(buttonBack);
        contentPane.add(userVBox, BorderLayout.NORTH);
        //contentPane.add(vBox, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	public void setNewClassName() {
		newClassName=classnameTextField.getText().toString();
	}
	public void setExistingClassName(String newClassName){
		this.newClassName=newClassName;
	}
	/*public static void setVectorofStudents(Vector v) {
		vectorOfStudents=v;
	}*/
	/*public void removeAllTextboxes() {	
		frame3.setVisible(false);
		frame3.dispose();
		this.showGUI3();
	}*/
	public static Vector<String> getUserList() {
	    Vector<String> studentJCheckBoxVector=new Vector<String>();
		for (int i = 0; i < checkBoxVector.size(); i++) {
			
			JCheckBox jc=checkBoxVector.get(i);
			if(jc.isSelected()) {
				studentJCheckBoxVector.add(jc.getText().toString());
	//        	textFieldVector.add(new JTextField(String.valueOf(vectorOfStudents.get((i*2)+1))));
			}
		}
		for(JTextField jTextField:textFieldVector) {
			studentJCheckBoxVector.add(jTextField.getText().toString());
		}
		System.out.println("Size of vector is "+studentJCheckBoxVector.size());
		//System.out.println(vectorOfStudents);
		
		//textFieldVector.add(e)
		checkBoxVector.removeAllElements();
		textFieldVector.removeAllElements();
		return studentJCheckBoxVector;
	}
	/*public static void removeAllTextboxes(String frameName) {	
		frame1.setVisible(false);
		frame1.dispose();
		if(frameName=="Add")
			new ViewAddRemoveUsers().showAddUserList();
		else if(frameName=="Remove")
			new ViewAddRemoveUsers().showRemoveUserList();
	}*/
	public static void removeUsersFromUI() {
		textFieldVector.removeAllElements();
		checkBoxVector.removeAllElements();
	}
}