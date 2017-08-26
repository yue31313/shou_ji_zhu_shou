package com.example.phone;

import java.util.Date;

public class CallRecords {
	private String name;
	private String number;
	private int type;
	private Date date;
	public CallRecords(String name, String number, int type, Date date,
			long talktime) {
		super();
		this.name = name;
		this.number = number;
		this.type = type;
		this.date = date;
		this.talktime = talktime;
	}
	private long talktime;
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getTalktime() {
		return talktime;
	}
	public void setTalktime(long talktime) {
		this.talktime = talktime;
	}
	
}
