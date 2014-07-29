package com.example.labapplicationapk;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EmulatorState implements Serializable{
	String user_name, user_id, bluetooth, wifi, storage, location, airplaneMode, NFC, operatorName,
	musicVolumeLevel,ringVolumeLevel, batteryLevel, version, model;
	List<String> installedApplications;
	public EmulatorState() {
		installedApplications=new ArrayList<String>();
	}
}