package cn.pyz.service;

import java.util.Calendar;
import java.util.HashMap;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import cn.pyz.receiver.AlarmReceiver;

public class AlarmService {

	private static int[] requestCodes = new int[] { 2, 3, 4, 5, 6 };// �����������룬��Ӧ��һ������

	/**
	 * ������������
	 * 
	 * @param timeInMillis
	 *            ���Ӵ���ʱ�䣨��������
	 * @param flag
	 *            ����Ƶ�ʱ�־ 0��ʾһ�Σ�1��ʾ�������죬2��ʾÿ�춼��
	 * @param context
	 * @param isEnabled 
	 * @return
	 */
	public static boolean saveAlarmData(long timeInMillis, int flag,
			Context context, boolean isEnabled) {

		SharedPreferences preference = context.getSharedPreferences(
				"alarmData", Context.MODE_PRIVATE);
		Editor edit = preference.edit();
		edit.putLong("timeInMillis", timeInMillis);
		edit.putInt("flag", flag);
		edit.putBoolean("isEnabled", isEnabled);
		return edit.commit();
	}

	/**
	 * �õ���������
	 * 
	 * @param context
	 * @return
	 */
	public static HashMap<String, Object> getAlarmData(Context context) {

		HashMap<String, Object> mapData = new HashMap<String, Object>();
		SharedPreferences preference = context.getSharedPreferences(
				"alarmData", Context.MODE_PRIVATE);
		mapData.put("timeInMillis", preference.getLong("timeInMillis", 0));
		mapData.put("flag", preference.getInt("flag", -1));
		mapData.put("isEnabled", preference.getBoolean("isEnabled", false));
		return mapData;
	}

	/**
	 * ��������
	 * 
	 * @param timeInMillis
	 *            ���Ӵ���ʱ�䣨��������
	 * @param flag
	 *            ����Ƶ�ʱ�־ 0��ʾһ�Σ�1��ʾ�������죬2��ʾÿ�춼��
	 * @param context
	 * @param isEnabled ���ӿ�����־
	 */
	public static void enabledAlarm(long timeInMillis, int flag, Context context, boolean isEnabled) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(context, AlarmReceiver.class);
		intent.setAction("cn.pyz.alarm");
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		// ��ȡ���ӹ����ʵ��
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		long curLongTime = System.currentTimeMillis();
		System.out.println("-----------curLongTime---------" + curLongTime);
		System.out.println("-----------timeInMillis----------" + timeInMillis);
		System.out.println("----------------flag----------------" + flag);
		if (flag == 0) {// ֻ��һ��

			if (timeInMillis >= curLongTime) {
				System.out.println("------------timeInMillis-----------------"
						+ timeInMillis);
				am.set(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
			} else {// �ڶ�����
				System.out.println("===============================");
				am.set(AlarmManager.RTC_WAKEUP,
						(timeInMillis + AlarmManager.INTERVAL_DAY),
						pendingIntent);
			}

		} else if (flag == 1) {// ��������

			Calendar c = Calendar.getInstance();
			int curDayofWeek = c.get(Calendar.DAY_OF_WEEK);
			System.out.println("---------timeInMillis--------"
					+ timeInMillis);
			for (int request : requestCodes) {
				long triggerAtTime = 0;
				pendingIntent = PendingIntent.getBroadcast(context, request,
						intent, PendingIntent.FLAG_UPDATE_CURRENT);
				if (request - curDayofWeek == 0) {

					if (timeInMillis < curLongTime) {// ��������
						triggerAtTime = timeInMillis
								+ AlarmManager.INTERVAL_DAY * 7;
					}else{
						triggerAtTime=timeInMillis;
					}
				} else if (request - curDayofWeek > 0) {
					triggerAtTime = timeInMillis + AlarmManager.INTERVAL_DAY
							* (request - curDayofWeek);
				} else {
					triggerAtTime=timeInMillis + AlarmManager.INTERVAL_DAY
							* (request - curDayofWeek + 7);
				}
				System.out.println("---------triggerAtTime--------"
						+ triggerAtTime);
				am.set(AlarmManager.RTC_WAKEUP, triggerAtTime, pendingIntent);
				am.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtTime,
						AlarmManager.INTERVAL_DAY * 7, pendingIntent);
			}

		} else if (flag == 2) {// ÿ����

			if (timeInMillis >= curLongTime) {
				am.set(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
				am.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis,
						24 * 60 * 60 * 1000, pendingIntent);
			} else {

				am.set(AlarmManager.RTC_WAKEUP,
						(timeInMillis + AlarmManager.INTERVAL_DAY),
						pendingIntent);
				am.setRepeating(AlarmManager.RTC_WAKEUP,
						(timeInMillis + AlarmManager.INTERVAL_DAY),
						24 * 60 * 60 * 1000, pendingIntent);
			}

		}
		saveAlarmData(timeInMillis, flag, context,isEnabled);
	}

	public static void cancelAlarm(Context context) {

		HashMap<String, Object> alarmData = getAlarmData(context);
		int flag = (Integer) alarmData.get("flag");
		Intent intent = new Intent(context, AlarmReceiver.class);
		intent.setAction("cn.pyz.alarm");

		switch (flag) {
		case 1:// ������

			for (int request : requestCodes) {
				PendingIntent pendingIntent = PendingIntent.getBroadcast(
						context, request, intent, 0);
				// ��ȡ���ӹ����ʵ��
				AlarmManager am = (AlarmManager) context
						.getSystemService(Context.ALARM_SERVICE);
				/* ȡ�� */
				am.cancel(pendingIntent);
			}
			break;

		default:// һ�λ�ÿ��

			PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
					0, intent, 0);
			// ��ȡ���ӹ����ʵ��
			AlarmManager am = (AlarmManager) context
					.getSystemService(Context.ALARM_SERVICE);
			/* ȡ�� */
			am.cancel(pendingIntent);
			break;
		}
	}

}
