package com.example.labapplicationapk;
import java.awt.BorderLayout;
import java.awt.Container;
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
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class ViewSendEmuConfig{
	static JFrame frame1;
	static Vector<File> fileDir;
	static Vector<JCheckBox> checkedConfigVector;
	private JCheckBox jc;
	ControllerServerBackListener csb;
	
	public ViewSendEmuConfig(ControllerServerBackListener csb) {
		this.csb=csb;
	}
/*	public ViewSendEmuConfig(String title) {
		super(title);
	}*/
	public void showConfiguration() {
		frame1 = new JFrame("Select Configuration");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentsToAddFrame(frame1.getContentPane());
        frame1.setLocationRelativeTo(null);
        frame1.setResizable(false);
        frame1.pack();
        frame1.setVisible(true);
	}
	private void addComponentsToAddFrame(Container contentPane) {
		final JPanel checkBoxPanel = new JPanel();
		int length=fileDir.size();
		GridLayout checkBoxGrid=new GridLayout();
		checkBoxGrid.setColumns(1);
		checkBoxGrid.setRows(length);
		checkBoxPanel.setLayout(checkBoxGrid);
		checkedConfigVector=new Vector<JCheckBox>();
		//Add checkboxes with list of AVD's
		for (int i = 0; i < length; i++) {
			checkedConfigVector.add(new JCheckBox(fileDir.get(i).getName()));
			checkBoxPanel.add(checkedConfigVector.get(i));
		}
		final JPanel buttonPanel = new JPanel();
	 	JButton sendConfig= new JButton("Send selected configuration");
		sendConfig.addActionListener(new ControllerServer());
		JButton backButton= new JButton("Back");
		backButton.addActionListener(csb);
		/*backButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewSendEmuConfig.frame1.setVisible(false);
				ViewSendEmuConfig.frame1.dispose();
				ViewSendEmuConfig.fileDir.removeAllElements();
				ViewSendEmuConfig.checkedConfigVector.removeAllElements();
				ViewServer.frame2.setVisible(true);
			}
		});*/
		buttonPanel.add(sendConfig);
		buttonPanel.add(backButton);
        
		//adding everything to container
        contentPane.add(checkBoxPanel, BorderLayout.NORTH);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

	}
	public static Vector<JCheckBox> getCheckedConfig() {
		return checkedConfigVector;
	}
	public static Vector<File> getFileDirOfConfig() {
		return fileDir;
	}
}
