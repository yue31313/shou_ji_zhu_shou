package com.example.ruanjianguanjia;

import android.graphics.drawable.Drawable;

public class AppInfo {
	private Drawable icon;
	private int Pid;
	private int Servicen;
	public int getServicen() {
		return Servicen;
	}

	public void setServicen(int servicen) {
		Servicen = servicen;
	}

	public int getPid() {
		return Pid;
	}

	public void setPid(int pid) {
		Pid = pid;
	}

	private String Num;
	private String VersionNumber;
	public void setVersionNumber(String versionNumber) {
		VersionNumber = versionNumber;
	}

	public String getNum() {
		return Num;
	}

	public void setNum(String num) {
		Num = num;
	}

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(int versionNumber) {
		this.versionNumber = versionNumber;
	}

	private String name;

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	private String versionName;
	private int versionNumber;
	private String baoName;

	public String getBaoName() {
		return baoName;
	}

	public void setBaoName(String baoName) {
		this.baoName = baoName;
	}

	public boolean getFlag() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setFlag(boolean b) {
		// TODO Auto-generated method stub
		
	}

	
}