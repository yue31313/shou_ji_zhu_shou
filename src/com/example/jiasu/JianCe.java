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

	String num; // �ֻ�����
	String driver; // ��Ӫ��
	String network_type; // ��������
	String isroot; // �Ƿ�Root
	String cpu_type; // CPU�ͺ�
	String cpu_num; // CPU������
	String max; // ���Ƶ��
	String min; // ���Ƶ��
	String now; // ��ǰƵ��
	String run_count; // ʣ���ڴ�����
	String phone_count; // �ֻ��ڴ�����
	String sd_count; // SD���ڴ�
	String resolution; // ��Ļ�ֱ���
	String density; // �����ܶ�
	String touch; // ��㴥��
	String camera_px; // ����ͷ����
	String camera_size; // ��Ƭ���ߴ�
	String light; // �����
	String wifi_name; // WIFI���ӵ�
	String wifi_ip; // WIFI��ַ
	String wifi_speed; // WIFI�����ٶ�
	String mac_address; // Mac��ַ
	String bluetooth_state; // ����״̬

	private ExpandableListView listView;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.xitongjiance);
		// �ֻ���Ϣ
		phoneInfo();
		// CPU��Ϣ
		cpuInfo();
		// �ڴ���Ϣ
		phoneMemory();
		// ����
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
		String fu[] = { "������Ϣ: ", "CPU: ", "�ڴ�: ", "�ֱ���: ", "����: ", "WIFI: " };
		String zi[][] = {
				{ "�豸�ͺţ�" + android.os.Build.MODEL,
						"\nϵͳ�汾��" + android.os.Build.VERSION.RELEASE,
						"\n�ֻ�����:" + num, "\n��Ӫ�̣�" + driver, "\n�Ƿ�ROOT��" + isroot },
				{ "CPU�ͺţ�" + cpu_type, "\nCPU��������" + cpu_num, "\n���Ƶ�ʣ�" + max,
						"\n���Ƶ�ʣ�" + min, "\n��ǰƵ�ʣ�" + now },

				{ "�����ڴ�������" + run_count, "\n�ֻ��ڴ�������" + phone_count,
						"\nsd���ڴ�������" + sd_count },
				{ "��Ļ�ֱ��ʣ�" + resolution, "\n��Ƭ���߶ȣ�" + camera_size,
						"\n�����:" + light },
				{ "�ֻ����أ�" + camera_px, "\n�����ܶȣ�" + density, "\n��㴥�أ�" + touch },
				{ "WIFI���ӵ���" + wifi_name, "\nWIFI��ַ��" + wifi_ip,
						"\nWIFI�����ٶȣ�" + wifi_speed + "",
						"\nMAC��ַ��" + mac_address, "\n����״̬��" + bluetooth_state } };

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

	// �ֻ���Ϣ
	 void phoneInfo() {
		telephonemanage = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		//
		num = telephonemanage.getDeviceId() + "";
		driver = getProvidersName();
		// ��������
		network_type = getNetworkType();
		// �Ƿ�Root
		isroot = isRoot();
	}

	// cpu��Ϣ
	 void cpuInfo() {
		// CPU�ͺ�
		cpu_type = CpuManager.getCpuName();
		//
		cpu_num = CpuManager.getNumCores() + "";
		max = CpuManager.getMaxCpuFreq();
		min = CpuManager.getMinCpuFreq();
		now = CpuManager.getCurCpuFreq();
	}

	// �ڴ�
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
				touch = "��֧��";
			} else {
				touch = "֧��";
			}

		}

	}

	// ����
	void phonePixel() {
		Camera camera = Camera.open();
		Parameters parameters = camera.getParameters();
		String states = parameters.getFlashMode();
		List<Size> s = parameters.getSupportedPictureSizes();

		Display display = getWindowManager().getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		System.out.println(s.get(0).width + " ����" + s.get(0).height
				+ s.get(0).toString());
		int px = s.get(0).width * s.get(0).height;
		camera_px = px + "";
		camera_size = width + "x" + height;
		if ("".equals(states) || states == null) {
			light = "���豸��֧�֣�����";
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
		 bluetooth_state = "��";
		 } else {
		 bluetooth_state = "��";
		 }
	}

	// ��ʼ���ؼ�
	// private void init() {
	// expandable_lv = (ExpandableListView) this
	// .findViewById(R.id.expandable_lv);
	// }

	/**
	 * ��ȡ�ֻ���Ӫ��
	 * 
	 * @return
	 */
	public String getProvidersName() {
		String ProvidersName = null;

		TelephonyManager telephonyManager = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);// ???sim???????
		String simOperator = telephonyManager.getSimOperator();
		String type = "����";

		if ("46000".equals(simOperator)) {
			type = "�й��ƶ�";
		} else if ("46002".equals(simOperator)) {
			type = "�й��ƶ�";
		} else if ("46001".equals(simOperator)) {// ?��?j???46001
			type = "�й���ͨ";
		} else if ("46003".equals(simOperator)) {// ?��?????46003
			type = "�й�����";
		}
		return type;
	}

	/**
	 * �õ���������
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

	// ����ֻ��Ƿ�ROOt
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

			return "��";
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
		return "��";
	}

	private String longToIP(long longIp) {

		StringBuffer sb = new StringBuffer("");
		// ����24λ��0
		sb.append(String.valueOf((longIp & 0x000000FF)));
		sb.append(".");
		// ����1λ��0��Ȼ������8λ
		sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
		sb.append(".");
		// ����8λ��0��Ȼ������16λ
		sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
		sb.append(".");
		// ֱ������24λ
		sb.append(String.valueOf((longIp >>> 24)));
		return sb.toString();
	}

	private long getSDCardSize() {

		File path = Environment.getExternalStorageDirectory();

		StatFs stat = new StatFs(path.getPath());

		/* ��ȡblock��SIZE */

		long blockSize = stat.getBlockSize();

		/* ������ */

		long availableBlocks = stat.getBlockCount();

		/* ����bit��С */

		return availableBlocks * blockSize;

	}

	 String getResolution() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		return (dm.widthPixels + " * " + dm.heightPixels);
	}

	/**
	 * �����ܶ�
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

	// CPU��СƵ��
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

	// ��ǰƵ��
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

	// CPU����
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
