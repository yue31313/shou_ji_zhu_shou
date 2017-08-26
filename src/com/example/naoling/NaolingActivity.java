package com.example.naoling;

import java.util.Calendar;

import cn.pyz.service.AlarmService;

import com.example.naoling.MainActivityqq.LooperThread;
import com.example.tiantian.R;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker.OnDateChangedListener;

public class NaolingActivity extends Activity implements
		OnCheckedChangeListener, OnTouchListener,
		android.widget.RadioGroup.OnCheckedChangeListener {

	private ToggleButton tb;
	private EditText etTime;
	private RadioGroup radioGroup;
	private Calendar calendar1;
	private int flag = 0;// 频率标志
	private long timeInMillis;
	private boolean isEnabled = false;// 闹钟开启标志
	TextView timeshow;
	private Calendar calendar;
	private int year, month, day, hour, minute, miss, miao;
	Handler mHandler;
	private LooperThread mClockThread;
	/* 声明一常数作为判别信息用 */

	protected static final int GUINOTIFIER = 0x1234;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initView();
		setListener();
		init();
	}

	private void setListener() {
		tb.setOnCheckedChangeListener(this);
		etTime.setOnTouchListener(this);
		radioGroup.setOnCheckedChangeListener(this);
	}

	private void initView() {
		tb = (ToggleButton) this.findViewById(R.id.toggleButton);
		etTime = (EditText) this.findViewById(R.id.et_time);
		radioGroup = (RadioGroup) this.findViewById(R.id.radioGroup);
	}

	// radiogroup监听事件
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.radio0:
			Toast.makeText(this, "一次", 1).show();
			flag = 0;
			break;
		case R.id.radio1:
			Toast.makeText(this, "工作日", 1).show();
			flag = 1;
			break;
		case R.id.radio2:
			Toast.makeText(this, "每天", 1).show();
			flag = 2;
			break;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			showTimeDialog();
		}
		return false;
	}

	private void showTimeDialog() {
		calendar1 = Calendar.getInstance();
		calendar1.get(Calendar.HOUR_OF_DAY);
		calendar1.get(Calendar.MINUTE);
		new TimePickerDialog(this, timeListener,
				calendar1.get(Calendar.HOUR_OF_DAY),
				calendar1.get(Calendar.MINUTE), true).show();
	}

	OnTimeSetListener timeListener = new OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			calendar1.setTimeInMillis(System.currentTimeMillis());
			calendar1.set(Calendar.HOUR_OF_DAY, hourOfDay);
			calendar1.set(Calendar.MINUTE, minute);
			calendar1.set(Calendar.SECOND, 0);
			calendar1.set(Calendar.MILLISECOND, 0);
			etTime.setText(format(hourOfDay) + ":" + format(minute));
			timeInMillis = calendar1.getTimeInMillis();
		}
	};

	// ToggleButtong事件监听
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (etTime.getText().toString().length() != 0) {
			isEnabled = isChecked;
			if (isChecked) {
				etTime.setEnabled(false);
				AlarmService.enabledAlarm(timeInMillis, flag, this, isEnabled);
			} else {
				etTime.setEnabled(true);
				AlarmService.cancelAlarm(this);
			}
		} else {
			Toast.makeText(this, "提醒时间不能空", 1).show();
			tb.setChecked(false);
		}
	}

	private String format(int x) {
		String s = "" + x;
		if (s.length() == 1)
			s = "0" + s;
		return s;
	}

	public void init() {
		timeshow = (TextView) findViewById(R.id.timeshow);
		calendar = Calendar.getInstance();
		// 得到当前时间并设置到日历里面
		calendar.setTimeInMillis(System.currentTimeMillis());
		// 获取到当前的时间
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH) + 1;
		day = calendar.get(Calendar.DATE);
		hour = calendar.get(Calendar.HOUR_OF_DAY);
		minute = calendar.get(Calendar.MINUTE);
		miss = calendar.get(Calendar.SECOND);
		// 设置date控件的初始化以及监听
		mHandler = new Handler() {
			public void handleMessage(Message msg) {

				/* 处理信息的方法 */

				switch (msg.what) {

				case NaolingActivity.GUINOTIFIER:

					/* 在这处理要TextView对象Show时间的事件 */

					timeshow.setText(hour + " : " + minute + " : " + " : "
							+ miao);
					break;

				}
				super.handleMessage(msg);

			}

		};

		/* 通过进程来持续取得系统时间 */

		mClockThread = new LooperThread();

		mClockThread.start();

	}

	/* 改写一个Thread Class用来持续取得系统时间 */

	class LooperThread extends Thread {

		public void run() {

			super.run();

			try {

				do

				{

					/* 取得系统时间 */

					long time = System.currentTimeMillis();

					/* 通过Calendar对象来取得小时与分钟 */

					final Calendar mCalendar = Calendar.getInstance();

					mCalendar.setTimeInMillis(time);

					hour = mCalendar.get(Calendar.HOUR_OF_DAY);

					minute = mCalendar.get(Calendar.MINUTE);
					miao = mCalendar.get(Calendar.SECOND);
					/* 让进程休息一秒 */

					Thread.sleep(1000);

					/* 重要关键程序:取得时间后发出信息给Handler */

					Message m = new Message();

					m.what = NaolingActivity.GUINOTIFIER;

					NaolingActivity.this.mHandler.sendMessage(m);

				} while (NaolingActivity.LooperThread.interrupted() == false);

				/* 当系统发出中断信息时停止本循环 */

			} catch (Exception e) {

				e.printStackTrace();

			}
		}
	}
}
