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
// * ����������������㲥 
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
//        //����ǿ����㲥�Ļ��������������  
//        if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {  
//            setAlarmTime(context);  
//        }  
//    }  
//  
//    /** 
//     * ������������ 
//     * @param context ������ 
//     */  
//    private void setAlarmTime(Context context) {  
//        calendar = Calendar.getInstance();  
//        // �õ���ǰʱ�䲢���õ���������  
//        calendar.setTimeInMillis(System.currentTimeMillis());  
//        DbHelper dbHelper = new DbHelper(context);  
//        Date date = new Date();  
//        //��ѯ���ݿ�洢������  
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
//                    //��߶�ȡ������ʱ��,Ҳ���Զ�ȡ�������longֵ  
//                    calendar.set(  
//                            Integer.valueOf(timer.split(" ")[0].split("-")[0]),  
//                            Integer.valueOf(timer.split(" ")[0].split("-")[1])-1,  
//                            Integer.valueOf(timer.split(" ")[0].split("-")[2]),  
//                            Integer.valueOf(timer.split(" ")[1].split(":")[0]),  
//                            Integer.valueOf(timer.split(" ")[1].split(":")[1]),  
//                            0);  
//                    Intent intent = new Intent(context, AlamrReceiver.class);  
//                    // ����intent�Ķ���,ʶ��ǰ���õ�����һ������,�����ڹ�������Ĺر�  
//                    intent.setAction(timer);  
//                    // �ù㲥��������  
//                    PendingIntent pi = PendingIntent.getBroadcast(context, 0,  
//                            intent, 0);  
//                    // ��ȡ�������  
//                    AlarmManager am = (AlarmManager) context  
//                            .getSystemService(Activity.ALARM_SERVICE);  
//                    // ��������  
//                    am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),  
//                            pi);  
//                    // ���������ظ�ʱ��  
//                    am.setRepeating(AlarmManager.RTC_WAKEUP,  
//                            calendar.getTimeInMillis(), 10 * 1000, pi);  
//  
//                } else {  
//                    //���ڵ����彫����������(���������ݿ���ɾ��)  
//                    dbHelper.delete(timer);  
//                    System.out.println("��ǰ��������ѹ���");  
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

