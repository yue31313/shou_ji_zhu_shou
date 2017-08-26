package com.example.tiantian;

import com.example.dianchi.DianchiManagerAcyivity;
import com.example.jiasu.RuanjianJiasuActivity;
import com.example.jiasu.TabdemoActivity;
import com.example.naoling.MainActivityqq;
import com.example.naoling.NaolingActivity;
import com.example.phone.PhoneActivity;
import com.example.playtabtest.BaseActivity;
import com.example.playtabtest.MainActivityss;
import com.example.ruanjianguanjia.RuanjianActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends BaseActivity implements OnClickListener {
	ImageView ruanjianImageView;
	ImageView dianChiImageView;
	ImageView yingjianImageView;
	ImageView clockImageView;
	ImageView tongXunLuImageView;
	ImageView cameraImageView;
	TextView xianshi;
	ImageView selView; 
	int oldId; 

	int unSelImages[] = { R.drawable.menu_icon_0_0, R.drawable.menu_icon_1_0,
			R.drawable.menu_icon_2_0, R.drawable.menu_icon_3_0,
			R.drawable.menu_icon_4_0, R.drawable.menu_icon_5_0 };

	int selImages[] = { R.drawable.menu_icon_0_1, R.drawable.menu_icon_1_1,
			R.drawable.menu_icon_2_1, R.drawable.menu_icon_3_1,
			R.drawable.menu_icon_4_1, R.drawable.menu_icon_5_1 };
	String[] str = { "通讯录", "软件管家", "硬件信息", "电量", "闹钟", "照相机" };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main1);

		ruanjianImageView = (ImageView) findViewById(R.id.menu_icon1);
		dianChiImageView = (ImageView) findViewById(R.id.menu_icon3);
		yingjianImageView = (ImageView) findViewById(R.id.menu_icon2);
		clockImageView = (ImageView) findViewById(R.id.menu_icon4);
		tongXunLuImageView = (ImageView) findViewById(R.id.menu_icon0);
		cameraImageView = (ImageView) findViewById(R.id.menu_icon5);
		xianshi = (TextView) findViewById(R.id.xianshi);
		xianshi.setOnClickListener(this);
		ruanjianImageView.setOnClickListener(this);
		dianChiImageView.setOnClickListener(this);
		yingjianImageView.setOnClickListener(this);
		clockImageView.setOnClickListener(this);
		tongXunLuImageView.setOnClickListener(this);
		cameraImageView.setOnClickListener(this);
		tongXunLuImageView.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				mpDialogShow("正在加载请稍候...");
				
				new Thread() {
					@Override
					public void run() {
						super.run();
						try {
							Thread.sleep(3000);
						} catch (Exception e) {
							e.printStackTrace();
						}
						startActivity(new Intent(MainActivity.this,MainActivityss.class));
						mpDialog.cancel();
					}
				}.start();
				
				
				
				
				return false;
			}
		});
		clockImageView.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this,MainActivityqq.class));
				return false;
			}
		});
		yingjianImageView.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this,TabdemoActivity.class));
				return false;
			}
		});
		dianChiImageView.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this,DianchiManagerAcyivity.class));
//				overridePendingTransition(R.anim.hyperspace_in,
//						R.anim.hyperspace_out);
				return false;
			}
		});
		ruanjianImageView.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(MainActivity.this,RuanjianActivity.class));
//				overridePendingTransition(
//						R.anim.alpha_scale_translate_rotate,
//						R.anim.my_alpha_action);
				return false;
			}
		});
		cameraImageView.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this,
						CameraActivity.class));

				return false;
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.menu_icon0:
			quan(tongXunLuImageView, 0);
			break;
		case R.id.menu_icon1:
			quan(ruanjianImageView, 1);
			break;
		case R.id.menu_icon2:
			quan(yingjianImageView, 2);
			break;
		case R.id.menu_icon3:
			quan(dianChiImageView, 3);
			break;
		case R.id.menu_icon4:
			quan(clockImageView, 4);
			break;
		case R.id.menu_icon5:
			quan(cameraImageView, 5);
			break;
		}
	}

	public void quan(ImageView view, int newId) {
		if (selView != null) {
			selView.setImageResource(unSelImages[oldId]);
		}
		selView = view;
		oldId = newId;
		xianshi.setText(str[newId]);
		selView.setImageResource(selImages[newId]);

	}
	

}
