package com.example.labapplicationapk;
import java.net.ServerSocket;
import java.net.Socket;

public class ConfigruationListner implements Runnable{

	@Override
	public void run() {
		try{
			ServerSocket welcomeSocket = new ServerSocket(6789);

			while(true)
			{
				Socket connectionSocket = welcomeSocket.accept();
				System.out.println("In Config listener");
				ConfigruationReceiver reciver = new ConfigruationReceiver(connectionSocket);
				Thread th = new Thread(reciver);
				th.start();
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}
}