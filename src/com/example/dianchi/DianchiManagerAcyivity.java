package com.example.dianchi;

import com.example.tiantian.R;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TextView;

public class DianchiManagerAcyivity extends Activity {
	RelativeLayout title1;
	RelativeLayout title2;
	RelativeLayout buju1;
	RelativeLayout buju2;
	TextView tvBatteryChanged;
	TextView dianchizhuangtai;
	TextView dianchizhuangtai2;
	TextView dianchiwendu;
	TextView kuaisu;
	TextView xunhuan;
	TextView woliu;
	TextView dianchidianya;
	TextView dianchishiyongy;
	TextView chongdianfangshi;
	TextView dianchishiyongzhuangkuang;
	private BatteryReceiver receiver = null;
	private int percent;
	ImageView dianchihuantu;
	ImageView dianchihuantu2;
	ImageView dengpao1;
	ImageView dengpao2;
	ImageView dengpao3;
    private SlidingDrawer sd;  
    Button kaiguan;
    Handler handler = new Handler();
	int num = 0;
	boolean isru = true;
	int [] t = {R.drawable.bt0,R.drawable.bt10,R.drawable.bt20,R.drawable.bt30,R.drawable.bt50,R.drawable.bt70,R.drawable.bt80,R.drawable.bt90,R.drawable.bt100};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dianchi);
		title1 = (RelativeLayout) findViewById(R.id.biaotianniu1);
		title2 = (RelativeLayout) findViewById(R.id.biaotianniu2);
		buju1 = (RelativeLayout) findViewById(R.id.buju1);
		buju2 = (RelativeLayout) findViewById(R.id.buju2);
		tvBatteryChanged = (TextView) findViewById(R.id.xianshidianliangde);
		kuaisu = (TextView) findViewById(R.id.kuaisu);
		xunhuan = (TextView) findViewById(R.id.xunhuan);
		woliu = (TextView) findViewById(R.id.woliu);
		dengpao1 = (ImageView) findViewById(R.id.dengpao1);
		dengpao2 = (ImageView) findViewById(R.id.dengpao2);
		dengpao3 = (ImageView) findViewById(R.id.dengpao3);
		dianchizhuangtai = (TextView) findViewById(R.id.dianchizhuangtai);
		dianchiwendu = (TextView) findViewById(R.id.dianchiwendu);
		dianchidianya = (TextView) findViewById(R.id.dianchidianya);
		dianchishiyongzhuangkuang = (TextView) findViewById(R.id.dianchishiyongzhuangkuang);
		chongdianfangshi = (TextView) findViewById(R.id.chongdianfangshi);
		dianchihuantu2 = (ImageView) findViewById(R.id.dianchihuantu2);
		dianchishiyongy = (TextView) findViewById(R.id.dianchisgiyongzhuangtaide);
		dianchihuantu = (ImageView) findViewById(R.id.dianhuantu);
		dianchizhuangtai2 = (TextView) findViewById(R.id.dianchizhuangtai2);
		sd = (SlidingDrawer) findViewById(R.id.slidingDrawer1);
		kaiguan = (Button) findViewById(R.id.handle);
		receiver = new BatteryReceiver();
		IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

		registerReceiver(receiver, filter);// 注册BroadcastReceiver
		   sd.setOnDrawerOpenListener(new OnDrawerOpenListener() {  
	            public void onDrawerOpened() {  
	                //可以设置图片开启的状态  
	            	kaiguan.setBackgroundResource(R.drawable.milaoshu);
	            }  
	        });  
	        sd.setOnDrawerCloseListener(new OnDrawerCloseListener() {  
	            public void onDrawerClosed() {  
	                //可以设置图片关闭的状态  
	            	kaiguan.setBackgroundResource(R.drawable.wenroudexiaomianyang);
	            }  
	        });  

		title1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				title1.setBackgroundResource(R.drawable.channelgallery_bg);

				title2.setBackgroundResource(R.drawable.btn_bg);
				buju1.setVisibility(RelativeLayout.VISIBLE);
				buju2.setVisibility(RelativeLayout.GONE);

			}
		});
		title2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				title2.setBackgroundResource(R.drawable.channelgallery_bg);
				title1.setBackgroundResource(R.drawable.btn_bg);
				buju2.setVisibility(RelativeLayout.VISIBLE);
				buju1.setVisibility(RelativeLayout.GONE);
			}
		});
	}

	 class BatteryReceiver extends BroadcastReceiver {
		private String BatteryTemp;
		private String BatteryStatus;
		private String BatteryStatus2;
		private boolean flag;

		public void onReceive(Context context, Intent intent) {

			int current = intent.getExtras().getInt("level");// 获得当前电量

			int total = intent.getExtras().getInt("scale");// 获得总电量
			dianchidianya.setText("当前电压为：" + intent.getIntExtra("voltage", 0)
					+ "mV");
			float wendu = intent.getExtras().getInt("temperature") * 0.1f;
			dianchiwendu.setText(intent.getExtras().getInt("temperature")
					* 0.1f + "度");
			if (wendu > 30) {
				dianchiwendu.setTextColor(Color.RED);
			} else {
				dianchiwendu.setTextColor(Color.GREEN);
			}
			percent = current * 100 / total;

			tvBatteryChanged.setText(percent + "%");
			huantu();
			if(percent<10){
				dianchishiyongy.setText("电量不足，请连接充电器!!!");
				}
			if (percent < 80) {
				dengpao1.setImageResource(R.drawable.battery_bulb_active);
				dengpao2.setImageResource(R.drawable.battery_bulb_deactive);
				dengpao1.setImageResource(R.drawable.battery_bulb_deactive);
				kuaisu.setText("1.快速充电中\n");
				xunhuan.setText("2.循环充电等待中\n");
				woliu.setText("3.涡流充电等待中\n");
			} else if (percent >= 80 && percent < 99) {
				dengpao1.setImageResource(R.drawable.battery_bulb_deactive);
				dengpao2.setImageResource(R.drawable.battery_bulb_active);
				dengpao1.setImageResource(R.drawable.battery_bulb_deactive);
				kuaisu.setText("1.快速充电已完成\n");
				xunhuan.setText("2.循环充电中\n");
				woliu.setText("3.涡流充电等待中\n");
			} else {
				dengpao1.setImageResource(R.drawable.battery_bulb_deactive);
				dengpao2.setImageResource(R.drawable.battery_bulb_deactive);
				dengpao3.setImageResource(R.drawable.battery_bulb_active);
				kuaisu.setText("1.快速充电已完成\n");
				xunhuan.setText("2.循环充电已完成\n");
				woliu.setText("3.涡流充电中\n");
			}

			switch (intent.getIntExtra("status",
					BatteryManager.BATTERY_STATUS_UNKNOWN)) {

			case BatteryManager.BATTERY_STATUS_CHARGING:
				isru = true;
//				handler.postDelayed(r, 1000);
				
				dianchizhuangtai.setText("充电状态");
				dianchizhuangtai.setTextColor(Color.GREEN);
				dianchihuantu2.setImageResource(R.drawable.battery_charging);
				dianchizhuangtai2.setText("正在充电");
				
				break;

			case BatteryManager.BATTERY_STATUS_DISCHARGING:
				isru = false;

				BatteryStatus = "放电状态";
				dianchizhuangtai.setText("放电状态");
				dianchizhuangtai2.setText("放电状态");
				dengpao1.setImageResource(R.drawable.battery_bulb_deactive);
				dengpao2.setImageResource(R.drawable.battery_bulb_deactive);
				dengpao3.setImageResource(R.drawable.battery_bulb_deactive);
				kuaisu.setText("1.快速充电等待中\n");
				xunhuan.setText("2.循环充电等待中\n");
				woliu.setText("3.涡流充电等待中\n");
				dianchizhuangtai.setTextColor(Color.YELLOW);
				break;

			case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
				isru = false;
				BatteryStatus = "未充电";
				dianchizhuangtai.setText("未充电");
				dianchizhuangtai2.setText("未充电");
				dianchizhuangtai.setTextColor(Color.GREEN);
				break;

			case BatteryManager.BATTERY_STATUS_FULL:
				isru = false;
				BatteryStatus = "充满电";
				dianchizhuangtai.setText("充满电");
				dianchizhuangtai.setTextColor(Color.GREEN);
				dianchizhuangtai2.setText("充满电");
				break;

			case BatteryManager.BATTERY_STATUS_UNKNOWN:
				isru = false;
				BatteryStatus = "未知道状态";
				dianchizhuangtai.setText("未知道状态");
				dianchizhuangtai2.setText("未知道状态");
				dianchizhuangtai.setTextColor(Color.BLUE);
				break;

			}
			
			
		//电池充电状态	
			
			if(BatteryManager.BATTERY_STATUS_CHARGING==2){
//				handler.notify();
//				isru = true;
				if(!flag){
					handler.postDelayed(r, 500);
					flag = true;
				}
			}else{
				isru = false;
				flag = false;
					handler.removeCallbacks(r);
					huantu();
			}
			
			
			
			
			
			
			
			
			
			
			
			
			switch (intent.getIntExtra("plugged",
					BatteryManager.BATTERY_PLUGGED_AC)) {

			case BatteryManager.BATTERY_PLUGGED_AC:

				BatteryStatus2 = "AC充电";
				chongdianfangshi.setText("AC充电");
				break;

			case BatteryManager.BATTERY_PLUGGED_USB:

				BatteryStatus2 = "USB充电";
				chongdianfangshi.setText("USB充电");
				break;

			}

			switch (intent.getIntExtra("health",
					BatteryManager.BATTERY_HEALTH_UNKNOWN)) {

			case BatteryManager.BATTERY_HEALTH_UNKNOWN:

				BatteryTemp = "未知错误";
				dianchishiyongzhuangkuang.setText("未知错误");
				break;

			case BatteryManager.BATTERY_HEALTH_GOOD:

				BatteryTemp = "状态良好";
				dianchishiyongzhuangkuang.setText("状态良好");
				break;

			case BatteryManager.BATTERY_HEALTH_DEAD:

				BatteryTemp = "电池没有电";
				dianchishiyongzhuangkuang.setText("电池没有电");
				break;

			case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:

				BatteryTemp = "电池电压过高";
				dianchishiyongzhuangkuang.setText("电池电压过高");
				break;

			case BatteryManager.BATTERY_HEALTH_OVERHEAT:
				dianchishiyongzhuangkuang.setText("电池过热");
				BatteryTemp = "电池过热";

				break;

			}
			 
			
			
		}
		public void huantu(){
			if (percent > 90) {
				dianchihuantu.setImageResource(R.drawable.bt100);
				dianchihuantu2.setImageResource(R.drawable.bt100);
			} else if (percent > 80 && percent <= 90) {
				dianchihuantu.setImageResource(R.drawable.bt90);
				dianchihuantu2.setImageResource(R.drawable.bt90);
			} else if (percent > 70 && percent <= 80) {
				dianchihuantu.setImageResource(R.drawable.bt80);
				dianchihuantu2.setImageResource(R.drawable.bt80);
			} else if (percent > 60 && percent <= 70) {
				dianchihuantu.setImageResource(R.drawable.bt70);
				dianchihuantu2.setImageResource(R.drawable.bt70);
			} else if (percent > 50 && percent <= 60) {
				dianchihuantu.setImageResource(R.drawable.bt50);
				dianchihuantu2.setImageResource(R.drawable.bt50);
			} else if (percent > 40 && percent <= 50) {
				dianchihuantu.setImageResource(R.drawable.bt30);
				dianchihuantu2.setImageResource(R.drawable.bt30);
			} else if (percent > 30 && percent <= 40) {
				dianchihuantu.setImageResource(R.drawable.bt20);
				dianchihuantu2.setImageResource(R.drawable.bt20);
			} else if (percent > 20 && percent <= 30) {
				dianchihuantu.setImageResource(R.drawable.bt10);
				dianchihuantu2.setImageResource(R.drawable.bt10);
			} else if (percent > 0 && percent <= 20) {
				dianchihuantu.setImageResource(R.drawable.bt0);
				dianchihuantu2.setImageResource(R.drawable.bt0);
			}
		};
		
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				if(isru==true){
					if (num <t.length ){
						dianchihuantu.setImageResource(t[num]);
						num++;
					}else{
						num=0;
					}
					
				}
				handler.postDelayed(r, 500);
				
				
			}
		};
	}

}
