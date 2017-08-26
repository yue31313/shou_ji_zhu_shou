package com.xiaomimanager.sms;

public class MessageModel {
    private String message = "";//信息内容
    private String date = "";//发送时间
    private boolean isA = true;//是否是A发送的消息,如果不是则是B发送的

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isA() {
        return isA;
    }

    public void setA(boolean isA) {
        this.isA = isA;
    }
}
