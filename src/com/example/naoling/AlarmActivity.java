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
 * �������(��ʾ:���ù�ȥ��ʱ���һֱ��ʾ:����ڰ�ʱ�����ó������ʱ��ȡ������) 
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
//        // �õ���ǰʱ�䲢���õ���������  
//        calendar.setTimeInMillis(System.currentTimeMillis());  
//        // ��ȡ����ǰ��ʱ��  
//        year = calendar.get(Calendar.YEAR);  
//        month = calendar.get(Calendar.MONTH);  
//        day = calendar.get(Calendar.DATE);  
//        hour = calendar.get(Calendar.HOUR_OF_DAY);  
//        minute = calendar.get(Calendar.MINUTE);  
//        // ����date�ؼ��ĳ�ʼ���Լ�����  
//        mDatePicker.init(year, month, day, new OnDateChangedListener() {  
//  
//            public void onDateChanged(DatePicker view, int year,  
//                    int monthOfYear, int dayOfMonth) {  
//                mDatePickerTv.setText("��ѡ��������ǣ�" + year + "��"  
//                        + (monthOfYear + 1) + "��" + dayOfMonth + "��");  
//                AlarmActivity.this.year = year;  
//                AlarmActivity.this.month = monthOfYear;  
//                AlarmActivity.this.day = dayOfMonth;  
//            }  
//  
//        });  
//        // ����24Сʱ��  
//        mTimePicker.setIs24HourView(true);  
//        mTimePicker.setOnTimeChangedListener(new OnTimeChangedListener() {  
//  
//            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {  
//                mTimePickerTv.setText("��ѡ���ʱ���ǣ�" + hourOfDay + "ʱ" + minute  
//                        + "��");  
//                AlarmActivity.this.hour = hourOfDay;  
//                AlarmActivity.this.minute = minute;  
//            }  
//        });  
//        // �������尴ť����  
//        mInstallBtn.setOnClickListener(this);  
//        mCancelBtn.setOnClickListener(this);  
//    }  
//  
//    @Override  
//    public void onClick(View v) {  
//        // TODO Auto-generated method stub  
//        switch (v.getId()) {  
//        // ���ｲһ����������������������:��ϸ��case���涼��һ��������,���������switch����ľֲ������ͱ���˵�����case����ľֲ�����,��ֹ������ͻ  
//        case R.id.btn_install: {  
//            // ���������ʱ��(��,��,��,ʱ,��,��)  
//            calendar.set(year, month, day, hour, minute, 0);  
//            int newMonth = month + 1;  
//            Intent intent = new Intent(this, AlamrReceiver.class);  
//            String time = year + "-" + newMonth + "-" + day + " " + hour + ":"  
//                    + minute;  
//            // ����intent�Ķ���,ʶ��ǰ���õ�����һ������,�����ڹ�������Ĺر�  
//            intent.setAction(time);  
//            dbHelper.update(time, calendar.getTimeInMillis()+"");  
//            // �ù㲥��������  
//            PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);  
//            // ��ȡ�������  
//            AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);  
//            // ��������  
//            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);  
//            // ���������ظ�ʱ��  
//            am.setRepeating(AlarmManager.RTC_WAKEUP,  
//                    calendar.getTimeInMillis(), 10 * 1000, pi);  
//            // ��ȡ�����·���0~11,����Ҫ��1  
//            mTitleTv.setText("��ѡ�������Ϊ:" + year + "��" + newMonth + "��" + day  
//                    + "��" + hour + "ʱ" + minute + "��");  
//            break;  
//        }  
//        case R.id.btn_cancel: {  
//            int newMonth = month + 1;  
//            Intent intent = new Intent(this, AlamrReceiver.class);  
//            // �ҳ���ǰ�ؼ�ѡ�������ʱ��,���رյ�ǰѡ�������  
//            String time = year + "-" + newMonth + "-" + day + " " + hour + ":"  
//                    + minute;  
//            intent.setAction(time);  
//            dbHelper.delete(time);  
//            PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);  
//            AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);  
//            am.cancel(pi);  
//            mTitleTv.setText("��ȡ����" + year + "��" + newMonth + "��" + day + "��"  
//                    + hour + "ʱ" + minute + "��" + "������!!!");  
//            break;  
//        }  
//        }  
//    }  
//  
//}  