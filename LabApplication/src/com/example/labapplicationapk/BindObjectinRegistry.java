package com.example.labapplicationapk;
/* 
 * BootStrap.java 
 * 
 * Version: 
 *     $Id$ 
 * 
 * Revisions: 
 *     $Log$ 
 */
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Enumeration;

/**
 * This program initializes the server
 *
 * @author      Shobhit Dutia
 */

public class BindObjectinRegistry {

	/**
	 * The main program.
	 * 
	 * @param    args   command line arguments (ignored) 
	 * @exception UnknownHostException 
	 * @throws SocketException 
	 */

	public static void main(String[] args) throws UnknownHostException, SocketException {
		try {
			String hostname = InetAddress.getLocalHost().getHostAddress();
			System.out.println("this host IP is " + hostname);
			JoinInterface obj=new JoinImpl();
			Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
	        NetworkInterface ni;
	        InetAddress address = null;
	        while (nis.hasMoreElements()) {
	            ni = nis.nextElement();
	            if (!ni.isLoopback() && ni.isUp()) {
	                for (InterfaceAddress ia : ni.getInterfaceAddresses()) {
	                    if (ia.getAddress().getAddress().length == 4) {
	                        address=ia.getAddress();
	                    }
	                }
	            }
	        }
	        System.setProperty("java.rmi.server.hostname", address.toString()); 
	        LocateRegistry.createRegistry(1099);
			Registry reg1 = LocateRegistry.getRegistry();
			System.setProperty("java.rmi.server.hostname",address.toString());
			// Binding the object in the rmi registry				
			reg1.rebind("Shobhit_bootstrapObject", obj);
			System.out.println("Bootstrap server bound in registry on port 1099!");
			System.out.println("Please provide bootstrap IP "+address+" to peers");
			ConfigruationListner lr = new ConfigruationListner();
			Thread th = new Thread(lr);
			th.start();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}