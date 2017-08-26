package com.example.naoling;

import java.util.Calendar;  

import com.example.tiantian.R;
  

import android.app.Activity;  
import android.app.AlarmManager;  
import android.app.PendingIntent;  
import android.content.Intent;  
import android.os.Bundle;  
import android.view.View;  
import android.view.View.OnClickListener;  
import android.widget.Button;  
import android.widget.DatePicker;  
import android.widget.DatePicker.OnDateChangedListener;  
import android.widget.TextView;  
import android.widget.TimePicker;  
import android.widget.TimePicker.OnTimeChangedListener;  
  
/*** 
 * 闹铃管理(提示:设置过去的时间会一直提示:大家在把时间设置成闹铃的时间取消即可) 
 *  
 * @author wangyubin 
 *  
 */  
//public class AlarmActivity extends Activity implements OnClickListener {  
//  
//    private TextView mTitleTv, mDatePickerTv, mTimePickerTv;  
//    private DatePicker mDatePicker;  
//    private TimePicker mTimePicker;  
//    private Button mInstallBtn, mCancelBtn;  
//    private Calendar calendar;  
//  
//    private int year, month, day, hour, minute;  
//    private DbHelper dbHelper;  
//  
//    @Override  
//    public void onCreate(Bundle savedInstanceState) {  
//        super.onCreate(savedInstanceState);  
//        setContentView(R.layout.activity_alarm);  
//        dbHelper = new DbHelper(this);  
//        init();  
//    }  
//  
//    private void init() {  
//  
//        mTitleTv = (TextView) findViewById(R.id.tv_title);  
//        mDatePicker = (DatePicker) findViewById(R.id.datePicker);  
//        mTimePicker = (TimePicker) findViewById(R.id.timePicker);  
//        mDatePickerTv = (TextView) findViewById(R.id.tv_datepicker);  
//        mTimePickerTv = (TextView) findViewById(R.id.tv_timepicker);  
//        mInstallBtn = (Button) findViewById(R.id.btn_install);  
//        mCancelBtn = (Button) findViewById(R.id.btn_cancel);  
//        calendar = Calendar.getInstance();  
//        // 得到当前时间并设置到日历里面  
//        calendar.setTimeInMillis(System.currentTimeMillis());  
//        // 获取到当前的时间  
//        year = calendar.get(Calendar.YEAR);  
//        month = calendar.get(Calendar.MONTH);  
//        day = calendar.get(Calendar.DATE);  
//        hour = calendar.get(Calendar.HOUR_OF_DAY);  
//        minute = calendar.get(Calendar.MINUTE);  
//        // 设置date控件的初始化以及监听  
//        mDatePicker.init(year, month, day, new OnDateChangedListener() {  
//  
//            public void onDateChanged(DatePicker view, int year,  
//                    int monthOfYear, int dayOfMonth) {  
//                mDatePickerTv.setText("您选择的日期是：" + year + "年"  
//                        + (monthOfYear + 1) + "月" + dayOfMonth + "日");  
//                AlarmActivity.this.year = year;  
//                AlarmActivity.this.month = monthOfYear;  
//                AlarmActivity.this.day = dayOfMonth;  
//            }  
//  
//        });  
//        // 设置24小时制  
//        mTimePicker.setIs24HourView(true);  
//        mTimePicker.setOnTimeChangedListener(new OnTimeChangedListener() {  
//  
//            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {  
//                mTimePickerTv.setText("您选择的时间是：" + hourOfDay + "时" + minute  
//                        + "分");  
//                AlarmActivity.this.hour = hourOfDay;  
//                AlarmActivity.this.minute = minute;  
//            }  
//        });  
//        // 设置闹铃按钮监听  
//        mInstallBtn.setOnClickListener(this);  
//        mCancelBtn.setOnClickListener(this);  
//    }  
//  
//    @Override  
//    public void onClick(View v) {  
//        // TODO Auto-generated method stub  
//        switch (v.getId()) {  
//        // 这里讲一个我们遇到的命名的问题:仔细看case后面都带一个大括号,这样里面的switch里面的局部变量就变成了单独的case里面的局部变量,防止命名冲突  
//        case R.id.btn_install: {  
//            // 设置闹铃的时间(年,月,日,时,分,秒)  
//            calendar.set(year, month, day, hour, minute, 0);  
//            int newMonth = month + 1;  
//            Intent intent = new Intent(this, AlamrReceiver.class);  
//            String time = year + "-" + newMonth + "-" + day + " " + hour + ":"  
//                    + minute;  
//            // 设置intent的动作,识别当前设置的是哪一个闹铃,有利于管理闹铃的关闭  
//            intent.setAction(time);  
//            dbHelper.update(time, calendar.getTimeInMillis()+"");  
//            // 用广播管理闹铃  
//            PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);  
//            // 获取闹铃管理  
//            AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);  
//            // 设置闹钟  
//            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);  
//            // 设置闹钟重复时间  
//            am.setRepeating(AlarmManager.RTC_WAKEUP,  
//                    calendar.getTimeInMillis(), 10 * 1000, pi);  
//            // 获取到的月份是0~11,所以要加1  
//            mTitleTv.setText("您选择的闹铃为:" + year + "年" + newMonth + "月" + day  
//                    + "日" + hour + "时" + minute + "分");  
//            break;  
//        }  
//        case R.id.btn_cancel: {  
//            int newMonth = month + 1;  
//            Intent intent = new Intent(this, AlamrReceiver.class);  
//            // 找出当前控件选择的闹铃时间,并关闭当前选择的闹铃  
//            String time = year + "-" + newMonth + "-" + day + " " + hour + ":"  
//                    + minute;  
//            intent.setAction(time);  
//            dbHelper.delete(time);  
//            PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);  
//            AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);  
//            am.cancel(pi);  
//            mTitleTv.setText("您取消了" + year + "年" + newMonth + "月" + day + "日"  
//                    + hour + "时" + minute + "分" + "的闹铃!!!");  
//            break;  
//        }  
//        }  
//    }  
//  
//}  