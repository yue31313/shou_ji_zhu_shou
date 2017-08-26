package com.example.naoling;

import java.io.IOException;

import com.example.tiantian.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Bundle;

public class ShowAlarmActivity extends Activity {

	private MediaPlayer player;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_appxinxi);
	 player = new MediaPlayer();
		try {
			player.setDataSource(this,RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
			player.setAudioStreamType(AudioManager.STREAM_ALARM);
			player.prepare();
			player.start();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		showAlarmDialog();
	}

	private void showAlarmDialog() {

		AlertDialog.Builder buider = new AlertDialog.Builder(this);
		buider.setTitle("提示");
		buider.setMessage("起床了！");
		buider.setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				player.stop();
				finish();
			}
		});

		buider.create().show();

	}

}
