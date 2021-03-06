package com.example.labapplicationapk;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

/* 
 * JoinInterface.java 
 * 
 * Version: 
 *     $Id$ 
 * 
 * Revisions: 
 *     $Log$ 
 */
public interface JoinInterface extends java.rmi.Remote {

	/**
	 * join request method.
	 * 
	 * @param    args   	ip address of new node
	 * @return 	 ip address of bootstrap node	
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */

	public int addUsersToDatabase(Vector<String> userListVector,String className) throws java.rmi.RemoteException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	
	public int deleteUsersFromDatabase(Vector<String> userListVector, String selectedClass) throws java.rmi.RemoteException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	public Vector<String> getUsersFromDatabase(String className) throws java.rmi.RemoteException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	public int addMalware(String fileName, byte[] buffer,String classname) throws java.rmi.RemoteException;
	public int sendEmulatorConfiguration(Vector<byte[]>buffer,String classname)throws java.rmi.RemoteException, IOException;
	public Vector getEmulatorConfiguration(String classname) throws RemoteException,IOException;
	public Vector getMalware(String classname) throws java.rmi.RemoteException, IOException;
	public List<String> getLogs(String classname) throws RemoteException;
	public byte[] downloadFile(String fileName,String classname) throws RemoteException;
	public List<String> getClasses() throws RemoteException;
	public String getMyClassName(String uid) throws RemoteException;
	public void addClass(String classname) throws RemoteException;
	public String changePassword(String uid, String oldPassword, String newPassword, String caller)  throws RemoteException;
	public int addInstructor(String instId)  throws RemoteException;
	public char[] getPassword(String uid, String caller) throws RemoteException;
}