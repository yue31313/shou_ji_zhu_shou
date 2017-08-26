package com.example.phone;

import java.util.Date;

public class Sms {
	public Sms(String name, String phoneNumber, String smscontent, int type,
			Date date) {
		super();
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.smscontent = smscontent;
		this.type = type;
		this.date = date;
	}
	private String name;
	private String phoneNumber;
	private String smscontent;
	private int type;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getSmscontent() {
		return smscontent;
	}
	public void setSmscontent(String smscontent) {
		this.smscontent = smscontent;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	private Date date;
}
