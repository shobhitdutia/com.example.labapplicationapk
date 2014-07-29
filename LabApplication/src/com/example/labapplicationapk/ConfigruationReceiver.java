package com.example.labapplicationapk;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
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
            
			username=state.user_name;
			userid=state.user_id;
			System.out.println(username+" "+userid);

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            Date date = new Date();
            String dateString = dateFormat.format(date).toString();
            System.out.println(dateString);
			File f=new File(username+userid+" "+dateString+".txt");
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
