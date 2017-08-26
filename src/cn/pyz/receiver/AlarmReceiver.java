package cn.pyz.receiver;

import com.example.naoling.ShowAlarmActivity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Toast.makeText(context, "AlarmReceiver", 1).show();
		

		if ("cn.pyz.alarm".equals(intent.getAction())) {
			
			Intent intent2 = new Intent(context, ShowAlarmActivity.class);
			intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent2);
		}
	}

}
