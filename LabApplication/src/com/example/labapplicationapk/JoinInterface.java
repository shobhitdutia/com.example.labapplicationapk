package com.example.labapplicationapk;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

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

	public int addUsersToDatabase(String username,
			String password, Vector<JTextField> userListVector) throws java.rmi.RemoteException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	
	public int deleteUsersFromDatabase(String username,
			String password, Vector<JTextField> userListVector) throws java.rmi.RemoteException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	public Vector getUsersFromDatabase(String username,
			String password) throws java.rmi.RemoteException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	public int addMalware(String fileName, byte[] buffer) throws java.rmi.RemoteException;
	public int sendEmulatorConfiguration(Vector<File> v)throws java.rmi.RemoteException, IOException;
	public Vector getEmulatorConfiguration() throws RemoteException,IOException;
	public Vector getMalware() throws java.rmi.RemoteException, IOException;
	public List<String> files() throws RemoteException;
	public byte[] downloadFile(String fileName) throws RemoteException;
	public List<String> classes() throws RemoteException;
}