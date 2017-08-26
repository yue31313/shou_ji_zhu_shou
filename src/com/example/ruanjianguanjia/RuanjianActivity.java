package com.example.ruanjianguanjia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.tiantian.R;
import com.example.tiantian.R.drawable;
import com.example.tiantian.R.layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RuanjianActivity extends Activity implements OnItemClickListener{
	ImageView huanshitu;
	int num = 0;
	GridView gridView;
	ListView listview;
	TextView appNum;
	ImageView xitong;
	ImageView yonghu;
	ImageView tupianxunzhaun;
	int jilu;
	ArrayList<String> list;
	boolean iskai = false;
	List<AppInfo> sysList;
	AlertDialog.Builder builder;
	String[] items = { "详细信息", "卸载程序" };
	private Uri UripackageUri;
	private Animation myAnimation;
	private TextView AppMessage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ruanjian);
		gridView = (GridView) findViewById(R.id.gridView1);
		listview = (ListView) findViewById(R.id.listView1);
		huanshitu = (ImageView) findViewById(R.id.jiugongge);
		appNum = (TextView) findViewById(R.id.appgeshuxinxi);
		xitong = (ImageView) findViewById(R.id.xitongruanjian);
		yonghu = (ImageView) findViewById(R.id.yonghuaruanjian);
		View view = getLayoutInflater().inflate(R.layout.activity_appxinxi, null);
		AppMessage = (TextView) view.findViewById(R.id.wenzizhuanhuan);
		tupianxunzhaun = (ImageView)  view.findViewById(R.id.appView1);
		sysList = getSystemList();
		gridView.setAdapter(new Myadp());
		listview.setAdapter(new Myadp());
		appNum.setText("系统应用" + sysList.size() + "个");
		xitong.setBackgroundResource(R.drawable.comm_btn_bg2_n);
		yonghu.setBackgroundResource(R.drawable.comm_btn_bg2_s);
		listview.setOnItemClickListener(this);
		gridView.setOnItemClickListener(this);
		xitong.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				sysList.clear();
				xitong.setBackgroundResource(R.drawable.comm_btn_bg2_n);
				yonghu.setBackgroundResource(R.drawable.comm_btn_bg2_s);
				sysList = getSystemList();
				gridView.setAdapter(new Myadp());
				listview.setAdapter(new Myadp());
				Toast toast = new Toast(RuanjianActivity.this);
				appNum.setText("系统应用" + sysList.size() + "个");

				toast.makeText(RuanjianActivity.this, "系统的", Toast.LENGTH_LONG)
						.show();
			}
		});
		yonghu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sysList.clear();
				sysList = getYonghu();
				xitong.setBackgroundResource(R.drawable.comm_btn_bg2_s);
				yonghu.setBackgroundResource(R.drawable.comm_btn_bg2_n);
				gridView.setAdapter(new Myadp());
				listview.setAdapter(new Myadp());
				appNum.setText("用户应用" + sysList.size() + "个");
				Toast toast = new Toast(RuanjianActivity.this);

				toast.makeText(RuanjianActivity.this, "用户的", Toast.LENGTH_LONG)
						.show();
			}
		});
		huanshitu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (iskai == true) {
					gridView.setVisibility(View.VISIBLE);
					listview.setVisibility(View.GONE);
					huanshitu.setImageResource(R.drawable.jiugongge);
					iskai = false;
				} else if (iskai == false) {
					gridView.setVisibility(View.GONE);
					listview.setVisibility(View.VISIBLE);
					huanshitu.setImageResource(R.drawable.list);
					iskai = true;
				}
				num++;
			}
		});
	}

	class Myadp extends BaseAdapter {

	

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return sysList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return sysList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = getLayoutInflater().inflate(R.layout.activity_appxinxi,
					null);
			ImageView AppIcon = (ImageView) view.findViewById(R.id.appView1);
			AppMessage = (TextView) view
					.findViewById(R.id.wenzizhuanhuan);
			AppIcon.setImageDrawable(sysList.get(position).getIcon());
			AppMessage.setText(sysList.get(position).getName());

			return view;
		}
	}

	public List<AppInfo> getSystemList() {
		List<AppInfo> list = new ArrayList<AppInfo>();

		// 获得手机上所有已安装的软件
		/*
		 * getPackageManager() 获取系统的包管理者 getInstalledPackages获取已安装的所有包
		 * PackageInfo 系统类，存储该安装软件的所有包信息，即该软件AndroidManifest.xml文件中的所有信息
		 */
		List<PackageInfo> allList = getPackageManager().getInstalledPackages(
				PackageManager.GET_UNINSTALLED_PACKAGES
						| PackageManager.GET_ACTIVITIES);

		for (int i = 0; i < allList.size(); i++) {
			PackageInfo packageInfo = allList.get(i);
			/* ApplicationInfo 系统类，用于存储AndroidManifest.xml文件中application标签内的信息 */
			ApplicationInfo applicationInfo = packageInfo.applicationInfo;
			// 判断该软件是系统软件还是用户软件
			/* 满足该if条件的为系统软件，不满足的为用户软件 */
			if ((applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0
					|| (applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
				AppInfo info = new AppInfo();
				/* applicationInfo.loadIcon(getPackageManager()) 获取软件的图标 */
				info.setIcon(applicationInfo.loadIcon(getPackageManager()));
				info.setName(applicationInfo.loadLabel(getPackageManager())
						.toString()); // 软件名
				info.setVersionName(packageInfo.versionName); // 版本名称
				info.setVersionNumber(packageInfo.versionCode);// 版本号
				info.setBaoName(packageInfo.packageName);
				list.add(info);
			}
		}

		return list;

	}

	public List<AppInfo> getYonghu() {
		List<AppInfo> Ylist = new ArrayList<AppInfo>();
		List<PackageInfo> allList = getPackageManager().getInstalledPackages(
				PackageManager.GET_UNINSTALLED_PACKAGES
						| PackageManager.GET_ACTIVITIES);
		for (int i = 0; i < allList.size(); i++) {
			PackageInfo packageInfo = allList.get(i);
			ApplicationInfo applicationInfo = packageInfo.applicationInfo;
			if (!((applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0 || (applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0)) {
				AppInfo info1 = new AppInfo();
				info1.setIcon(applicationInfo.loadIcon(getPackageManager()));
				info1.setName(applicationInfo.loadLabel(getPackageManager())
						.toString());
				info1.setVersionName(packageInfo.versionName);
				info1.setVersionNumber(packageInfo.versionCode);
				info1.setBaoName(packageInfo.packageName);
				Ylist.add(info1);
			}

		}
		return Ylist;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==0){
			sysList.remove(jilu);
			gridView.setAdapter(new Myadp());
			listview.setAdapter(new Myadp());
		}
		
	
		
	}
	public void xianshi(final int arg2){
		jilu = arg2;
		builder = new AlertDialog.Builder(RuanjianActivity.this);
		builder.setTitle("选项");
		builder.setItems(items, new DialogInterface.OnClickListener() {

			

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				switch (which) {
				case 0:
					AlertDialog.Builder builder = new AlertDialog.Builder(
							RuanjianActivity.this);
					builder.setIcon(sysList.get(arg2).getIcon());
					builder.setTitle("详细信息");
					builder.setMessage("程序名称:"
							+ sysList.get(arg2).getName() + "\n" + "包名:"
							+ sysList.get(arg2).getBaoName() + "\n"
							+ "版本名:"
							+ sysList.get(arg2).getVersionName() + "\n"
							+ "版本号："
							+ sysList.get(arg2).getVersionNumber());
					builder.setNegativeButton("确定", null);
					builder.show();
					break;
				case 1:
					if( sysList.get(arg2).getBaoName().equals("com.example.tiantian")){
						AlertDialog.Builder builder1 = new AlertDialog.Builder(
								RuanjianActivity.this);
						builder1.setMessage("您确定要删除本程序吗？");
						builder1.setNegativeButton("确定", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								Uri uri = Uri.fromParts("package", sysList.get(arg2).getBaoName().toString(), null);
								Intent it = new Intent(Intent.ACTION_DELETE, uri);
								startActivityForResult(it, 0);
							}
						});
						builder1.setPositiveButton("取消", null);
						builder1.show();
					}
					else{
						Uri uri = Uri.fromParts("package", sysList.get(arg2).getBaoName().toString(), null);
						Intent it = new Intent(Intent.ACTION_DELETE, uri);
						startActivityForResult(it, 0);
					}
				
				}

			}
		});
		builder.show();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		xianshi(arg2);
	}
}
