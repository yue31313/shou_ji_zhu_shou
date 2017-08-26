//package com.example.naoling;
//
//import java.text.DateFormat;  
//import java.text.SimpleDateFormat;  
//import java.util.Calendar;  
//import java.util.Date;  
//import android.app.Activity;  
//import android.app.AlarmManager;  
//import android.app.PendingIntent;  
//import android.content.BroadcastReceiver;  
//import android.content.Context;  
//import android.content.Intent;  
//import android.database.Cursor;  
///** 
// * 开机重新设置闹铃广播 
// * @author wangyubin 
// * 
// */  
//public class AlarmInitReceiver extends BroadcastReceiver {  
//  
//    Cursor cursor;  
//    private Calendar calendar;  
//  
//    @Override  
//    public void onReceive(Context context, Intent intent1) {  
//        // TODO Auto-generated method stub  
//        String action = intent1.getAction();  
//        //如果是开机广播的话就重新设计闹铃  
//        if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {  
//            setAlarmTime(context);  
//        }  
//    }  
//  
//    /** 
//     * 重新设置内容 
//     * @param context 上下文 
//     */  
//    private void setAlarmTime(Context context) {  
//        calendar = Calendar.getInstance();  
//        // 得到当前时间并设置到日历里面  
//        calendar.setTimeInMillis(System.currentTimeMillis());  
//        DbHelper dbHelper = new DbHelper(context);  
//        Date date = new Date();  
//        //查询数据库存储的闹铃  
//        cursor = dbHelper.query();  
//        while (cursor.moveToNext()) {  
//            String timer = cursor.getString(0);  
//            System.out.println(timer);  
//            try {  
//                if (ConverToDate(timer).getTime() > date.getTime()) {  
//                    System.out.println(timer.split(" ")[0].split("-")[0] + "/"  
//                            + timer.split(" ")[0].split("-")[1] + "/"  
//                            + timer.split(" ")[0].split("-")[2] + " "  
//                            + timer.split(" ")[1].split(":")[0] + ":"  
//                            + timer.split(" ")[1].split(":")[1]);  
//                    //这边读取年月日时分,也可以读取存进来的long值  
//                    calendar.set(  
//                            Integer.valueOf(timer.split(" ")[0].split("-")[0]),  
//                            Integer.valueOf(timer.split(" ")[0].split("-")[1])-1,  
//                            Integer.valueOf(timer.split(" ")[0].split("-")[2]),  
//                            Integer.valueOf(timer.split(" ")[1].split(":")[0]),  
//                            Integer.valueOf(timer.split(" ")[1].split(":")[1]),  
//                            0);  
//                    Intent intent = new Intent(context, AlamrReceiver.class);  
//                    // 设置intent的动作,识别当前设置的是哪一个闹铃,有利于管理闹铃的关闭  
//                    intent.setAction(timer);  
//                    // 用广播管理闹铃  
//                    PendingIntent pi = PendingIntent.getBroadcast(context, 0,  
//                            intent, 0);  
//                    // 获取闹铃管理  
//                    AlarmManager am = (AlarmManager) context  
//                            .getSystemService(Activity.ALARM_SERVICE);  
//                    // 设置闹钟  
//                    am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),  
//                            pi);  
//                    // 设置闹钟重复时间  
//                    am.setRepeating(AlarmManager.RTC_WAKEUP,  
//                            calendar.getTimeInMillis(), 10 * 1000, pi);  
//  
//                } else {  
//                    //过期的闹铃将不进行设置(可以重数据库中删除)  
//                    dbHelper.delete(timer);  
//                    System.out.println("当前这个闹铃已过期");  
//                }  
//            } catch (Exception e) {  
//                // TODO Auto-generated catch block  
//                e.printStackTrace();  
//            }  
//        }  
//        if(null!=cursor){  
//            cursor.close();  
//        }  
//        if(null!=dbHelper){  
//            dbHelper.close();  
//        }  
//    }  
//  
//    public static Date ConverToDate(String strDate) throws Exception {  
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");  
//        return df.parse(strDate);  
//    }  
//  
//}  

