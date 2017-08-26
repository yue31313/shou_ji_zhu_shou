package com.example.tiantian;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.RelativeLayout;

public class WelcomeActivity extends Activity {

	Handler handler = new Handler();
	Runnable r;
	int[] t = { R.drawable.shanping };
	int num = 0;
	private RelativeLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qiehuan);
		layout = (RelativeLayout) findViewById(R.id.nihao);
		// layout = new ImageView(QieHuanActivity.this);
		r = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (num >0) {
					Intent intent = new Intent(WelcomeActivity.this,
							MainActivity.class);
					startActivity(intent);
					// ¹Ø±Õµ±Ç°Activity
					finish();
				} else {
					layout.setBackgroundResource(t[num]);
					handler.postDelayed(r, 1000);
					num++;
				}

			}
		};

		handler.postDelayed(r, 1000);
	}

}
