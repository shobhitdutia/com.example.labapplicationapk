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
	String username="root", password="KL@nkng$0unD";
	public ConfigruationReceiver(Socket in) {
		connectionSocket=in;
	}
	
	@Override
	public void run() {
		String userid;
		ObjectInputStream inFromClient;
		EmulatorState state;
		try {
			inFromClient = new ObjectInputStream(connectionSocket.getInputStream());	
			System.out.println("In Config receiver");
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
						username,
						password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("Select class_name from userlist where uid = \""+userid+"\"");
				while(rs.next()){
					className=rs.getString("class_name");
				}
				stmt.close();
				con.close();
				System.out.println("Class Name returned:"+className);
			} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			String userHome = System.getProperty("user.home");
			char sep=File.separatorChar;
			String path = userHome+sep+"ISSP"+sep+className+sep+"logs";
			System.out.println(path);

			File dir=new File(path);
			if(!dir.exists()) {
				dir.mkdirs();
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            Date date = new Date();
            String dateString = dateFormat.format(date).toString();
			System.out.println(path+sep+userid+" "+dateString+".txt");
            System.out.println(dateString);
			File f=new File(path+sep+userid+" "+dateString+".txt");
			if(f.createNewFile())
				System.out.println("true");
			else 
				System.out.println("false");
	//		PrintWriter writer = new PrintWriter("//path//"+username+userid+"config.txt", "UTF-8");
			PrintWriter writer = new PrintWriter(f);		
			writer.println("Bluetooth:"+state.bluetooth);
			writer.println("Wifi:"+state.wifi);
			writer.println("Storage:"+state.storage);
			writer.println("Location services:"+state.location);
			writer.println("Airplane mode:"+state.airplaneMode);
			writer.println("NFC:"+state.NFC);
			writer.println("Operator name:"+state.operatorName);
			writer.println("Music volume level:"+state.musicVolumeLevel);
			writer.println("Ring volume level:"+state.ringVolumeLevel);
			writer.println("Battery level:"+state.batteryLevel);
			writer.println("Version:"+state.version);
			writer.println("Model:"+state.model);
			writer.println("");
			writer.println("INSTALLED APPLICATION LIST:");
			List<String> installedApplicationList=state.installedApplications;
			int index=0;
			for(String appName:installedApplicationList) {
				writer.println((++index)+":"+appName);
			}
			/*bluetooth, wifi, storage, location, airplaneMode, NFC, operatorName,
			musicVolumeLevel,ringVolumeLevel, batteryLevel, version, model;*/
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}