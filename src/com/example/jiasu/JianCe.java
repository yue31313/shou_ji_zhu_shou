package com.example.jiasu;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Pattern;

import com.example.tiantian.R;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class JianCe extends Activity {

	private AppInfoManager app;

	String num; // 手机串号
	String driver; // 运营商
	String network_type; // 网络类型
	String isroot; // 是否Root
	String cpu_type; // CPU型号
	String cpu_num; // CPU核心数
	String max; // 最高频率
	String min; // 最低频率
	String now; // 当前频率
	String run_count; // 剩余内存总量
	String phone_count; // 手机内存总量
	String sd_count; // SD卡内存
	String resolution; // 屏幕分辨率
	String density; // 像素密度
	String touch; // 多点触控
	String camera_px; // 摄像头像素
	String camera_size; // 相片最大尺寸
	String light; // 闪光灯
	String wifi_name; // WIFI连接到
	String wifi_ip; // WIFI地址
	String wifi_speed; // WIFI连接速度
	String mac_address; // Mac地址
	String bluetooth_state; // 蓝牙状态

	private ExpandableListView listView;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.xitongjiance);
		// 手机信息
		phoneInfo();
		// CPU信息
		cpuInfo();
		// 内存信息
		phoneMemory();
		// 像素
		phonePixel();
		// WIFI
		phoneWifi();
		app = new AppInfoManager(JianCe.this);
		listView = (ExpandableListView) findViewById(R.id.expandableListView1);
		listView.setAdapter(new MyAdapt());
		listView.setGroupIndicator(null);
		listView.setCacheColorHint(Color.TRANSPARENT);

	}

	class MyAdapt extends BaseExpandableListAdapter {
		String fu[] = { "基本信息: ", "CPU: ", "内存: ", "分辨率: ", "像素: ", "WIFI: " };
		String zi[][] = {
				{ "设备型号：" + android.os.Build.MODEL,
						"\n系统版本：" + android.os.Build.VERSION.RELEASE,
						"\n手机串号:" + num, "\n运营商：" + driver, "\n是否ROOT：" + isroot },
				{ "CPU型号：" + cpu_type, "\nCPU核心数：" + cpu_num, "\n最高频率：" + max,
						"\n最低频率：" + min, "\n当前频率：" + now },

				{ "运行内存总量：" + run_count, "\n手机内存总量：" + phone_count,
						"\nsd卡内存总量：" + sd_count },
				{ "屏幕分辨率：" + resolution, "\n照片最大尺度：" + camera_size,
						"\n闪光灯:" + light },
				{ "手机像素：" + camera_px, "\n像素密度：" + density, "\n多点触控：" + touch },
				{ "WIFI连接到：" + wifi_name, "\nWIFI地址：" + wifi_ip,
						"\nWIFI连接速度：" + wifi_speed + "",
						"\nMAC地址：" + mac_address, "\n蓝牙状态：" + bluetooth_state } };

		public Object getChild(int arg0, int arg1) {

			return zi[arg0][arg1];
		}

		public long getChildId(int arg0, int arg1) {

			return fu[arg0].length();
		}

		public View getChildView(int arg0, int arg1, boolean arg2, View arg3,
				ViewGroup arg4) {

			TextView view = new TextView(JianCe.this);
			view.setText(zi[arg0][arg1]);
			view.setTextColor(Color.BLUE);

			return view;
		}

		public int getChildrenCount(int arg0) {

			return zi[arg0].length;
		}

		public Object getGroup(int arg0) {

			return fu[arg0];
		}

		public int getGroupCount() {

			return fu.length;
		}

		public long getGroupId(int arg0) {

			return arg0;
		}

		public View getGroupView(int arg0, boolean arg1, View arg2,
				ViewGroup arg3) {

			TextView view = new TextView(JianCe.this);
			view.setText(fu[arg0]);

			view.setTextSize(30);
			view.setTextColor(Color.GREEN);
			return view;
		}

		public boolean hasStableIds() {

			return false;
		}

		public boolean isChildSelectable(int arg0, int arg1) {

			return true;
		}

	}

	TelephonyManager telephonemanage;

	// 手机信息
	 void phoneInfo() {
		telephonemanage = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		//
		num = telephonemanage.getDeviceId() + "";
		driver = getProvidersName();
		// 网络类型
		network_type = getNetworkType();
		// 是否Root
		isroot = isRoot();
	}

	// cpu信息
	 void cpuInfo() {
		// CPU型号
		cpu_type = CpuManager.getCpuName();
		//
		cpu_num = CpuManager.getNumCores() + "";
		max = CpuManager.getMaxCpuFreq();
		min = CpuManager.getMinCpuFreq();
		now = CpuManager.getCurCpuFreq();
	}

	// 内存
	 void phoneMemory() {
		run_count = AppInfoManager.memSize / 1024 / 1024 + "MB";
		phone_count = AppInfoManager.total_memory / 1024 / 1024 + "MB";
		sd_count = getSDCardSize() / 1024 / 1024 + "MB";
		resolution = getResolution();
		density = getDensity() + "";
		Method methods[] = MotionEvent.class.getDeclaredMethods();
		for (Method m : methods) {

			if (m.getName().contains("getPointerCount")
					&& m.getName().equals("getPointerId")) {
				touch = "不支持";
			} else {
				touch = "支持";
			}

		}

	}

	// 像素
	void phonePixel() {
		Camera camera = Camera.open();
		Parameters parameters = camera.getParameters();
		String states = parameters.getFlashMode();
		List<Size> s = parameters.getSupportedPictureSizes();

		Display display = getWindowManager().getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		System.out.println(s.get(0).width + " 像素" + s.get(0).height
				+ s.get(0).toString());
		int px = s.get(0).width * s.get(0).height;
		camera_px = px + "";
		camera_size = width + "x" + height;
		if ("".equals(states) || states == null) {
			light = "该设备不支持！！！";
		} else {
			light = states;
		}

	}

	// WIFI
	 void phoneWifi() {
		WifiInfo info = ((WifiManager) getSystemService(WIFI_SERVICE))
				.getConnectionInfo();

		wifi_name = info.getSSID() + "";
		wifi_ip = longToIP((int) info.getIpAddress());
		wifi_speed = info.getLinkSpeed() + "";
		mac_address = info.getMacAddress() + "";
		 BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
		 if (ba.isEnabled()) {
		 bluetooth_state = "开";
		 } else {
		 bluetooth_state = "关";
		 }
	}

	// 初始化控件
	// private void init() {
	// expandable_lv = (ExpandableListView) this
	// .findViewById(R.id.expandable_lv);
	// }

	/**
	 * 获取手机运营商
	 * 
	 * @return
	 */
	public String getProvidersName() {
		String ProvidersName = null;

		TelephonyManager telephonyManager = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);// ???sim???????
		String simOperator = telephonyManager.getSimOperator();
		String type = "类型";

		if ("46000".equals(simOperator)) {
			type = "中国移动";
		} else if ("46002".equals(simOperator)) {
			type = "中国移动";
		} else if ("46001".equals(simOperator)) {// ?й?j???46001
			type = "中国联通";
		} else if ("46003".equals(simOperator)) {// ?й?????46003
			type = "中国电信";
		}
		return type;
	}

	/**
	 * 得到网络类型
	 * 
	 * @return
	 */
	private String getNetworkType() {
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkinfo = connManager.getActiveNetworkInfo();
		String networkType = "";
		if (networkinfo != null) {
			networkType = networkinfo.getTypeName();
		}

		return networkType;
	}

	// 检测手机是否ROOt
	 String isRoot() {
		Process process = null;
		DataOutputStream os = null;
		try {
			process = Runtime.getRuntime().exec("su");
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes("ls /data/\n");
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
		} catch (Exception e) {

			return "否";
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				process.destroy();
			} catch (Exception e) {
				// nothing
			}
		}
		return "是";
	}

	private String longToIP(long longIp) {

		StringBuffer sb = new StringBuffer("");
		// 将高24位置0
		sb.append(String.valueOf((longIp & 0x000000FF)));
		sb.append(".");
		// 将高1位置0，然后右移8位
		sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
		sb.append(".");
		// 将高8位置0，然后右移16位
		sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
		sb.append(".");
		// 直接右移24位
		sb.append(String.valueOf((longIp >>> 24)));
		return sb.toString();
	}

	private long getSDCardSize() {

		File path = Environment.getExternalStorageDirectory();

		StatFs stat = new StatFs(path.getPath());

		/* 获取block的SIZE */

		long blockSize = stat.getBlockSize();

		/* 块数量 */

		long availableBlocks = stat.getBlockCount();

		/* 返回bit大小 */

		return availableBlocks * blockSize;

	}

	 String getResolution() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		return (dm.widthPixels + " * " + dm.heightPixels);
	}

	/**
	 * 像素密度
	 * 
	 * @return
	 */
	 float getDensity() {
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric.density;
	}

}

class CpuManager {

	public static String getMaxCpuFreq() {
		String result = "";
		ProcessBuilder cmd;
		try {
			String[] args = { "/system/bin/cat",
					"/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq" };
			cmd = new ProcessBuilder(args);
			Process process = cmd.start();
			InputStream in = process.getInputStream();
			byte[] re = new byte[24];
			while (in.read(re) != -1) {
				result = result + new String(re);
			}
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			result = "N/A";
		}
		return result.trim();
	}

	// CPU最小频率
	public static String getMinCpuFreq() {
		String result = "";
		ProcessBuilder cmd;
		try {
			String[] args = { "/system/bin/cat",
					"/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq" };
			cmd = new ProcessBuilder(args);
			Process process = cmd.start();
			InputStream in = process.getInputStream();
			byte[] re = new byte[24];
			while (in.read(re) != -1) {
				result = result + new String(re);
			}
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			result = "N/A";
		}
		return result.trim();
	}

	// 当前频率
	public static String getCurCpuFreq() {
		String result = "N/A";
		try {
			FileReader fr = new FileReader(
					"/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
			BufferedReader br = new BufferedReader(fr);
			String text = br.readLine();
			result = text.trim();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	// CPU名字
	public static String getCpuName() {
		try {
			FileReader fr = new FileReader("/proc/cpuinfo");
			BufferedReader br = new BufferedReader(fr);
			String text = br.readLine();
			String[] array = text.split(":\\s+", 2);
			for (int i = 0; i < array.length; i++) {
			}
			return array[1];
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int getNumCores() {
		class CpuFilter implements FileFilter {
			@Override
			public boolean accept(File pathname) {
				if (Pattern.matches("cpu[0-9]", pathname.getName())) {
					return true;
				}
				return false;
			}
		}

		try {
			File dir = new File("/sys/devices/system/cpu/");
			File[] files = dir.listFiles(new CpuFilter());
			return files.length;
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}
}
