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
	private int flag = 0;// Ƶ�ʱ�־
	private long timeInMillis;
	private boolean isEnabled = false;// ���ӿ�����־
	TextView timeshow;
	private Calendar calendar;
	private int year, month, day, hour, minute, miss, miao;
	Handler mHandler;
	private LooperThread mClockThread;
	/* ����һ������Ϊ�б���Ϣ�� */

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

	// radiogroup�����¼�
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.radio0:
			Toast.makeText(this, "һ��", 1).show();
			flag = 0;
			break;
		case R.id.radio1:
			Toast.makeText(this, "������", 1).show();
			flag = 1;
			break;
		case R.id.radio2:
			Toast.makeText(this, "ÿ��", 1).show();
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

	// ToggleButtong�¼�����
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
			Toast.makeText(this, "����ʱ�䲻�ܿ�", 1).show();
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
		// �õ���ǰʱ�䲢���õ���������
		calendar.setTimeInMillis(System.currentTimeMillis());
		// ��ȡ����ǰ��ʱ��
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH) + 1;
		day = calendar.get(Calendar.DATE);
		hour = calendar.get(Calendar.HOUR_OF_DAY);
		minute = calendar.get(Calendar.MINUTE);
		miss = calendar.get(Calendar.SECOND);
		// ����date�ؼ��ĳ�ʼ���Լ�����
		mHandler = new Handler() {
			public void handleMessage(Message msg) {

				/* ������Ϣ�ķ��� */

				switch (msg.what) {

				case NaolingActivity.GUINOTIFIER:

					/* ���⴦��ҪTextView����Showʱ����¼� */

					timeshow.setText(hour + " : " + minute + " : " + " : "
							+ miao);
					break;

				}
				super.handleMessage(msg);

			}

		};

		/* ͨ������������ȡ��ϵͳʱ�� */

		mClockThread = new LooperThread();

		mClockThread.start();

	}

	/* ��дһ��Thread Class��������ȡ��ϵͳʱ�� */

	class LooperThread extends Thread {

		public void run() {

			super.run();

			try {

				do

				{

					/* ȡ��ϵͳʱ�� */

					long time = System.currentTimeMillis();

					/* ͨ��Calendar������ȡ��Сʱ����� */

					final Calendar mCalendar = Calendar.getInstance();

					mCalendar.setTimeInMillis(time);

					hour = mCalendar.get(Calendar.HOUR_OF_DAY);

					minute = mCalendar.get(Calendar.MINUTE);
					miao = mCalendar.get(Calendar.SECOND);
					/* �ý�����Ϣһ�� */

					Thread.sleep(1000);

					/* ��Ҫ�ؼ�����:ȡ��ʱ��󷢳���Ϣ��Handler */

					Message m = new Message();

					m.what = NaolingActivity.GUINOTIFIER;

					NaolingActivity.this.mHandler.sendMessage(m);

				} while (NaolingActivity.LooperThread.interrupted() == false);

				/* ��ϵͳ�����ж���Ϣʱֹͣ��ѭ�� */

			} catch (Exception e) {

				e.printStackTrace();

			}
		}
	}
}
