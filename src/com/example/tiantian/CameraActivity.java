package com.example.tiantian;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.Manifest.permission;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class CameraActivity extends Activity {

	SurfaceHolder holder;
	Camera camera;
	private SurfaceView surfaceView;
	private SeekBar jiaoju;
	private ImageView shangguangdeng;
	private ImageView zhaoxiang;
	private ImageView back;
	int num =0;
	boolean isstart = true;
	private Parameters parameters;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setVolumeControlStream(AudioManager.RINGER_MODE_SILENT);
		setContentView(R.layout.activity_camera);
		surfaceView = (SurfaceView) findViewById(R.id.xiangji);
		jiaoju = (SeekBar) findViewById(R.id.jiaoju);
		shangguangdeng = (ImageView) findViewById(R.id.shanguangdeng);
		zhaoxiang = (ImageView) findViewById(R.id.imageView2);
		back = (ImageView) findViewById(R.id.fanhui);
		holder = surfaceView.getHolder();
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		holder.addCallback(new MyCallback());
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		holder.setFixedSize(metrics.widthPixels, metrics.heightPixels);
		zhaoxiang.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				zhaoxiang();
			}
		});
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		jiaoju.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				 parameters = camera.getParameters();
				parameters.setZoom(progress);
				camera.setParameters(parameters);
				
			}
		});
		shangguangdeng.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Parameters parameters = camera.getParameters();
				parameters.getFlashMode();
				
				if(num%3==0){
					
					parameters.setFlashMode(Parameters.FLASH_MODE_AUTO);
					camera.setParameters(parameters);
					shangguangdeng.setImageResource(R.drawable.light_auto);
					
					
				}else if(num%3==1){
					parameters.setFlashMode(Parameters.FLASH_MODE_ON);
					camera.setParameters(parameters);
					shangguangdeng.setImageResource(R.drawable.light_on);
				}else{

					parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
					camera.setParameters(parameters);
					shangguangdeng.setImageResource(R.drawable.light_off);
				}
				
				// parameters.setZoom(20);
				num++;
			}
			
		});
				
	}
	public void zhaoxiang() {
		
		camera.autoFocus(null);
		camera.takePicture(null, null, new PictureCallback() {

			@Override
			public void onPictureTaken(byte[] data, Camera camera) {
				// TODO Auto-generated method stub
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					Bitmap bitmap = BitmapFactory.decodeByteArray(data,
							0, data.length);
					String lujing = Environment
							.getExternalStorageDirectory()
							.getAbsolutePath();
					BufferedOutputStream stream = null;
//					System.out.println(lujing+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.");
					String Times =  DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA))+"";
					File file = new File("/mnt/sdcard/TT/");
			
					
//					File file = new File(lujing + time + ".jpg");
					if (!file.exists()) {
					
							file.mkdirs();
//							file.createNewFile();
						
					}
					try {
						stream = new BufferedOutputStream(
								new FileOutputStream("/mnt/sdcard/TT/"
										+ Times + ".jpg"));
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					bitmap.compress(CompressFormat.JPEG, 100,
							stream);
					
					try {
						stream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Intent intent = new Intent(CameraActivity.this,XianshiActivity.class);
					intent.putExtra("lujing","/mnt/sdcard/TT/" + Times + ".jpg");
				
					System.out.println("/mnt/sdcard/" + Times + ".jpg"+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
					startActivity(intent);
					finish();

				} else {
					new Toast(CameraActivity.this).setText("没有可读的sd卡");
				}
			}
		});

		
	}

	class MyCallback implements Callback {

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			// TODO Auto-generated method stub

		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			if (camera != null) {
				camera.release();
			}
			camera = camera.open();
			//相机打开之后才能进行下一步的操作   获取相机的焦距
			parameters=camera.getParameters();
			jiaoju.setMax(parameters.getMaxZoom());
			try {
				camera.setPreviewDisplay(holder);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			camera.startPreview();
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {

			// TODO Auto-generated method stub
			if(isstart){
				System.out.println("stoppreview.............");
			camera.stopPreview();
			camera.release();
			}
			

		}

	}

   public boolean onKeyDown( int keyCode, KeyEvent event){
	   System.out.println(keyCode+"????????????????????????????????????/");
	   if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
		   jiaoju.setProgress(jiaoju.getProgress()+parameters.getMaxZoom()/15);
		   
	   }else if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
		   jiaoju.setProgress(jiaoju.getProgress()-parameters.getMaxZoom()/15);
	   }
	   else if(keyCode == KeyEvent.KEYCODE_CAMERA){
		   System.out.println("ASHDHSADKSAKJDHAKSJDH+++++++++++++++++=");
		   zhaoxiang();
	   }else if(keyCode == KeyEvent.KEYCODE_BACK){
		   finish();
	   }
	return true;
	   
   }

}
