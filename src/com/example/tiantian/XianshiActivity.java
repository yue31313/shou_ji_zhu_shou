package com.example.tiantian;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class XianshiActivity extends Activity {
	String lujing;
	ImageView xianshi;
	ImageView baocun;
	ImageView shanchu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xianshi);
		Intent intent = getIntent();
		lujing = intent.getStringExtra("lujing");
		System.out.println(lujing+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		xianshi = (ImageView) findViewById(R.id.xianshitupian);
		baocun = (ImageView) findViewById(R.id.baocun);
		shanchu = (ImageView) findViewById(R.id.shanchu);
		xianshi.setImageURI(Uri.parse(lujing));
		baocun.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast toast = new Toast(XianshiActivity.this);	
				toast.makeText(XianshiActivity.this, "已保存", Toast.LENGTH_LONG).show();
			}
		});
		shanchu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(XianshiActivity.this);
			builder.setTitle("警告！！");
			builder.setMessage("确定要删除吗？");
			builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
					File file =new File(lujing);
					if(file.exists()){
						file.delete();
					}else{
						new Toast(XianshiActivity.this).makeText(XianshiActivity.this, "没找到文件", Toast.LENGTH_LONG).show();	
					}
					
					new Toast(XianshiActivity.this).makeText(XianshiActivity.this, "已删除", Toast.LENGTH_LONG).show();
				startActivity(new Intent(XianshiActivity.this,CameraActivity.class));
				finish();
				}
			});
			builder.setPositiveButton("取消", null);
			builder.show();
			}
		});
		
	}
	 public boolean onKeyDown( int keyCode, KeyEvent event){
		 if(keyCode == KeyEvent.KEYCODE_BACK){
				AlertDialog.Builder builder = new AlertDialog.Builder(XianshiActivity.this);
				builder.setTitle("警告！！");
				builder.setMessage("确定要删除吗？");
				builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						File file =new File(lujing);
						if(file.exists()){
							file.delete();
						}else{
							new Toast(XianshiActivity.this).makeText(XianshiActivity.this, "没找到文件", Toast.LENGTH_LONG).show();	
						}
						
						new Toast(XianshiActivity.this).makeText(XianshiActivity.this, "已删除", Toast.LENGTH_LONG).show();
					startActivity(new Intent(XianshiActivity.this,CameraActivity.class));
					finish();
					}
				});
				builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						startActivity(new Intent(XianshiActivity.this,CameraActivity.class));
						finish();
					}
				});
				builder.show(); 
		 }
		return true;
	 }

}
