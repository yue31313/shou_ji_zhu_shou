package com.example.jiasu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.example.ruanjianguanjia.AppInfo;
import com.example.tiantian.R;

import android.R.integer;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RuanjianJiasuActivity extends Activity {
	TextView neicunxianshi;
	TextView yiyongneicun;
	ListView chengxuxianshi;
	TextView yunxinggeshu;
	String[] str = {"基本信息","CPU","内存","分辨率","像素","WIFI"};
	String [][] ss = {};
	AppInfoManager manager;
	List<AppInfo> jianchenglist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ruanjianjiasu);
		neicunxianshi = (TextView) findViewById(R.id.neicunxianshi);
		yiyongneicun = (TextView) findViewById(R.id.yiyongneicun);
		chengxuxianshi = (ListView) findViewById(R.id.meigechengxuxianshi);
		yunxinggeshu = (TextView) findViewById(R.id.zhengzaiyunxingde);
		
		manager = new AppInfoManager(RuanjianJiasuActivity.this);
		System.out.println("manager.getRunningAppInfo()="+manager.getRunningAppInfo().size());
		jianchenglist = manager.getRunningAppInfo();
		chengxuxianshi.setAdapter(new MylistAdapter());
		neicunxianshi.setText(manager.getEmployMemory()+"/"+manager.getSystemAvaialbeMemory());
		yiyongneicun.setText("已用内存："+manager.getMemoryPrecent()+"%" );
		
	}

	/**
	 * 获取手机CPU信息
	 */

	private void getCpuInfo() {
		String str1 = "/proc/cpuinfo";
		String str2 = "";
		String[] cpuInfo = { "", "" }; // 1-cpu型号 //2-cpu频率
		String[] arrayOfString;
		try {
			FileReader fr = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			for (int i = 2; i < arrayOfString.length; i++) {
				cpuInfo[0] = cpuInfo[0] + arrayOfString + " ";
			}
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			cpuInfo[1] += arrayOfString[2];
			localBufferedReader.close();
		} catch (IOException e) {
		}
		Toast.makeText(RuanjianJiasuActivity.this,
				"cpu型号:" + cpuInfo[0] + "\n" + "cpu频率:" + cpuInfo[1],
				Toast.LENGTH_LONG).show();
	}

	/**
	 * 手机IMEI
	 */
	private void getImei() {
		TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String imei = mTelephonyMgr.getDeviceId();
		Toast.makeText(RuanjianJiasuActivity.this, "imei:" + imei,
				Toast.LENGTH_LONG).show();
	}

	class MylistAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return jianchenglist.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return jianchenglist.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = getLayoutInflater().inflate(
					R.layout.yunxingjinchengbuju, null);
			TextView textView = (TextView) view.findViewById(R.id.jinchengming);
			TextView textView1 = (TextView) view.findViewById(R.id.neirongtishi);
			ImageView tubiao = (ImageView) view.findViewById(R.id.jinchengtubiao);
			textView.setText(jianchenglist.get(position).getName());
			textView1.setText("服务"+jianchenglist.get(position).getServicen()+"内存"+jianchenglist.get(position).getNum());
			tubiao.setImageDrawable(jianchenglist.get(position).getIcon());
			return view;
		}

	}

}
