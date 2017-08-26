package com.example.playtabtest;

import java.util.HashMap;
import java.util.List;

import com.example.jiasu.AppInfoManager;
import com.example.ruanjianguanjia.AppInfo;


public class Temp {
	private byte[] data;
	private static Temp temp;
	private int screenW, screenH;
	private AppInfoManager appInfoManager;
	private HashMap<Integer, Boolean> isSelected;
	private HashMap<Integer, Boolean> isChecked;
//	private TelephoneManager telephoneManager;
//	private TelephoneInfo phoneTemp;
	private List<AppInfo> systemAppInfo;
	private List<AppInfo> customAppInfo;
	private int sdkVersion;
//	private List<TelephoneInfo> allNum;
	private boolean isEdit;

	private Temp() {
	}

	public static Temp getTemp() {
		if (temp == null) {
			temp = new Temp();
		}
		return temp;
	}

	public int getScreenW() {
		return screenW;
	}

	public void setScreenW(int screenW) {
		this.screenW = screenW;
	}

	public int getScreenH() {
		return screenH;
	}

	public void setScreenH(int screenH) {
		this.screenH = screenH;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public AppInfoManager getAppInfoManager() {
		return appInfoManager;
	}

	public void setAppInfoManager(AppInfoManager appInfoManager) {
		this.appInfoManager = appInfoManager;
	}

	public HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		this.isSelected = isSelected;
	}

//	public TelephoneManager getTelephoneManager() {
//		return telephoneManager;
//	}
//
//	public void setTelephoneManager(TelephoneManager telephoneManager) {
//		this.telephoneManager = telephoneManager;
//	}

	public HashMap<Integer, Boolean> getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(HashMap<Integer, Boolean> isChecked) {
		this.isChecked = isChecked;
	}

	public int getSdkVersion() {
		return sdkVersion;
	}

	public void setSdkVersion(int sdkVersion) {
		this.sdkVersion = sdkVersion;
	}

//	public TelephoneInfo getPhoneTemp() {
//		return phoneTemp;
//	}

//	public void setPhoneTemp(TelephoneInfo phoneTemp) {
//		this.phoneTemp = phoneTemp;
//	}

//	public List<TelephoneInfo> getAllNum() {
//		return allNum;
//	}
//
//	public void setAllNum(List<TelephoneInfo> allNum) {
//		this.allNum = allNum;
//	}

	public List<AppInfo> getSystemAppInfo() {
		return systemAppInfo;
	}

	public void setSystemAppInfo(List<AppInfo> systemAppInfo) {
		this.systemAppInfo = systemAppInfo;
	}

	public List<AppInfo> getCustomAppInfo() {
		return customAppInfo;
	}

	public void setCustomAppInfo(List<AppInfo> customAppInfo) {
		this.customAppInfo = customAppInfo;
	}

	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}

}
