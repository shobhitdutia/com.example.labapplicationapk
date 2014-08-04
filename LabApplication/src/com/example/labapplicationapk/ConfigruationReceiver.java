package com.example.labapplicationapk;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ConfigruationReceiver implements Runnable{

	Socket connectionSocket;
	
	public ConfigruationReceiver(Socket in) {
		connectionSocket=in;
	}
	
	@Override
	public void run() {
		String username,userid;
		ObjectInputStream inFromClient;
		EmulatorState state;
		
		try {
			inFromClient = new ObjectInputStream(connectionSocket.getInputStream());	
			state = (EmulatorState) inFromClient.readObject();
			connectionSocket.close();
			System.out.println("Writing to file");
            
			userid=state.user_id;
			System.out.println(userid);
			String className=null;
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection con;
				con = DriverManager.getConnection(
						"jdbc:mysql://localhost/radb",
						"root",
						"mysql");
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("Select class_name from userlist where uid = "+userid);
				while(rs.next()){
					className=rs.getString("class_name");
				}
				stmt.close();
				con.close();
				System.out.println("Class Name returned");
			} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			String userHome = System.getProperty("user.home");
			char sep=File.pathSeparatorChar;
			String path = userHome+sep+"ISSP";
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            Date date = new Date();
            String dateString = dateFormat.format(date).toString();
            System.out.println(dateString);
			File f=new File(path+userid+" "+dateString+".txt");
			f.createNewFile();
	//		PrintWriter writer = new PrintWriter("//path//"+username+userid+"config.txt", "UTF-8");
			PrintWriter writer = new PrintWriter(f);		
			writer.println("Bluetooth "+state.bluetooth);
			writer.println("wifi "+state.wifi);
			writer.println("storage "+state.storage);
			writer.println("location "+state.location);
			writer.println("airplaneMode "+state.airplaneMode);
			writer.println("NFC "+state.NFC);
			writer.println("operatorName "+state.operatorName);
			writer.println("musicVolumeLevel "+state.musicVolumeLevel);
			writer.println("ringVolumeLevel "+state.ringVolumeLevel);
			writer.println("batteryLevel "+state.batteryLevel);
			writer.println("version "+state.version);
			writer.println("model "+state.model);
			writer.println("INSTALLED APPLICATION LIST:");
			List<String> installedApplicationList=state.installedApplications;
			for(String appName:installedApplicationList) {
				writer.println(appName);
			}
			/*bluetooth, wifi, storage, location, airplaneMode, NFC, operatorName,
			musicVolumeLevel,ringVolumeLevel, batteryLevel, version, model;*/
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
