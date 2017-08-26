package com.example.playtabtest.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.phone.CallRecords;
import com.example.phone.PhoneActivity;
import com.example.playtabtest.AddPeopleActivity;
import com.example.playtabtest.MainActivityss;
import com.example.playtabtest.XiangxiActivity;
import com.example.tiantian.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


public class LaunchUIFragment extends Fragment implements OnClickListener,TextWatcher{
	
	private MyAdapter adapter;
	private ListView lv_call;
	private EditText et;
	private Button bt_del;
	private View rootView;
	private SlidingDrawer sliding;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		rootView = inflater.inflate(R.layout.mi_callrecords, container, false);
		init();
	
		return rootView;
	}
	private void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		
	
	}
	private void init() {
		et = (EditText) rootView.findViewById(R.id.et_search);
		bt_del = (Button) rootView.findViewById(R.id.input_del);
		lv_call = (ListView) rootView.findViewById(R.id.lv_call);
		sliding = (SlidingDrawer) rootView.findViewById(R.id.sd_key_grid);

		et.addTextChangedListener(this);
		et.setInputType(InputType.TYPE_NULL);//屏蔽软键盘
		bt_del.setOnClickListener(this);
		sliding.open();

		rootView.findViewById(R.id.ibtn_key_0).setOnClickListener(this);
		rootView.findViewById(R.id.ibtn_key_1).setOnClickListener(this);
		rootView.findViewById(R.id.ibtn_key_2).setOnClickListener(this);
		rootView.findViewById(R.id.ibtn_key_3).setOnClickListener(this);
		rootView.findViewById(R.id.ibtn_key_4).setOnClickListener(this);
		rootView.findViewById(R.id.ibtn_key_5).setOnClickListener(this);
		rootView.findViewById(R.id.ibtn_key_6).setOnClickListener(this);
		rootView.findViewById(R.id.ibtn_key_7).setOnClickListener(this);
		rootView.findViewById(R.id.ibtn_key_8).setOnClickListener(this);
		rootView.findViewById(R.id.ibtn_key_9).setOnClickListener(this);
		rootView.findViewById(R.id.ibtn_key_l).setOnClickListener(this);
		rootView.findViewById(R.id.ibtn_key_r).setOnClickListener(this);
		rootView.findViewById(R.id.ibtn_key_del).setOnClickListener(this);

		rootView.findViewById(R.id.keybord_down).setOnClickListener(this);
		rootView.findViewById(R.id.call_out).setOnClickListener(this);

		adapter = new MyAdapter();
		lv_call.setAdapter(adapter);
		lv_call.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				CallRecords cr = MainActivityss.callRecords.get(position);
				Intent intent = new Intent(getActivity(),
						XiangxiActivity.class);
				intent.putExtra("phoneNumber", cr.getNumber());
				if(cr.getName()==null){
					intent.putExtra("status", false);
				}else{
					intent.putExtra("status", true);
				}
				intent.putExtra("name", cr.getName());
				startActivity(intent);
			}

		});
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	private class MyAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return MainActivityss.callRecords.size();
		}

		@Override
		public Object getItem(int position) {
			return MainActivityss.callRecords.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressWarnings("null")
		@SuppressLint({ "SimpleDateFormat", "ResourceAsColor" })
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder = null;
			if (convertView == null) {
				convertView = getLayoutInflater(getArguments()).inflate(
						R.layout.activity_tonghuajilu, null);
				holder = new Holder();
				holder.call_number = (TextView) convertView
						.findViewById(R.id.call_number);
				holder.call_time = (TextView) convertView
						.findViewById(R.id.call_time);
				holder.call_miss = (ImageView) convertView
						.findViewById(R.id.call_miss);
				holder.tonghushijian = (TextView) convertView
						.findViewById(R.id.tonghuashijian);
				holder.call_you = (ImageView) convertView
						.findViewById(R.id.call_you);
				holder.tonghucishu = (TextView) convertView
						.findViewById(R.id.tonghuacishu);
				convertView.setTag(holder);
			}
			holder = (Holder) convertView.getTag();

			final CallRecords record = MainActivityss.callRecords.get(position);
			final String name = record.getName();

//			 System.out.println(ti+"))))))))))))))))))))))))))))))))))))))))");

			if (name != null) {
				holder.call_number.setText(name);
			} else {
				holder.call_number.setText(record.getNumber());
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			int type = record.getType();
			String callTime = format.format(record.getDate());
			holder.call_time.setText(callTime);
			long ti = record.getTalktime();
			int mm=(int)(ti/60);
			int ss=(int)(ti%60);
//			holder.tonghushijian.setText(ti + "");
			if(mm==0){
				if(type==1){
					holder.tonghushijian.setText("呼入"+ss+"秒");
				}else if(type==2){
					holder.tonghushijian.setText("呼出"+ss+"秒");
				}else if(type==3){
					holder.tonghushijian.setText("响铃"+ss+"秒");
				}
			}else{
				if(type==1){
					holder.tonghushijian.setText("呼入"+mm+"分"+ss+"秒");
				}else if(type==2){
					holder.tonghushijian.setText("呼出"+mm+"分"+ss+"秒");
				}else if(type==3){
					holder.tonghushijian.setText("响铃"+mm+"分"+ss+"秒");
				}
			}
			
			
			
			// holder.tonghucishu.setText(record.getType());
			// 設置來電是否為未接或者打出還是已結的？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？
			if (type == 3) {
				holder.call_number.setTextColor(getResources().getColor(
						R.color.red));
				holder.call_miss
						.setImageResource(R.drawable.call_log_type_missed);
			} else if (type == 2) {
				holder.call_number.setTextColor(getResources().getColor(
						R.color.black));
				holder.call_miss
						.setImageResource(R.drawable.call_log_type_outgoing);
			} else {
				holder.call_number.setTextColor(getResources().getColor(
						R.color.black));
				holder.call_miss
						.setImageResource(R.drawable.call_log_type_incoming);
			}
			holder.call_you.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent intent = new Intent(Intent.ACTION_CALL, Uri
							.parse("tel:" + record.getNumber()));
					startActivity(intent);
				}
			});
			return convertView;
		}


		class Holder {
			TextView call_number;
			TextView call_time;
			ImageView call_miss;
			ImageView call_num;
			ImageView call_you;
			TextView tonghushijian;
			TextView tonghucishu;
		}
	}
	@Override
	public void afterTextChanged(Editable s) {
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if(TextUtils.isEmpty(s)){
			et.setVisibility(View.GONE);
			bt_del.setVisibility(View.GONE);
		}else{
			et.setVisibility(View.VISIBLE);
			bt_del.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ibtn_key_0:
			et.getText().insert(et.getSelectionEnd(), "0");
			break;
		case R.id.ibtn_key_1:
			et.getText().insert(et.getSelectionEnd(), "1");
			break;
		case R.id.ibtn_key_2:
			et.getText().insert(et.getSelectionEnd(), "2");
			break;
		case R.id.ibtn_key_3:
			et.getText().insert(et.getSelectionEnd(), "3");
			break;
		case R.id.ibtn_key_4:
			et.getText().insert(et.getSelectionEnd(), "4");
			break;
		case R.id.ibtn_key_5:
			et.getText().insert(et.getSelectionEnd(), "5");
			break;
		case R.id.ibtn_key_6:
			et.getText().insert(et.getSelectionEnd(), "6");
			break;
		case R.id.ibtn_key_7:
			et.getText().insert(et.getSelectionEnd(), "7");
			break;
		case R.id.ibtn_key_8:
			et.getText().insert(et.getSelectionEnd(), "8");
			break;
		case R.id.ibtn_key_9:
			et.getText().insert(et.getSelectionEnd(), "9");
			break;
		case R.id.ibtn_key_l:
			et.getText().insert(et.getSelectionEnd(), "*");
			break;
		case R.id.ibtn_key_r:
			et.getText().insert(et.getSelectionEnd(), "#");
			break;
		case R.id.ibtn_key_del:
			int length = et.getSelectionEnd();
			if (length > 0) {
				et.getText().delete(length - 1, length);
			}
			break;
		case R.id.input_del:
			et.getText().clear();
			break;
		case R.id.keybord_down:
			sliding.close();
			break;
		case R.id.call_out:
			String phoneNumber = et.getText().toString().trim();
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
					+ phoneNumber));
			startActivity(intent);
			break;
		}
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (adapter != null) {
			adapter.notifyDataSetChanged();
			System.out.println("**********************************");
			System.out.println( MainActivityss.callRecords.size()+"++++++++++++++++++++++++++++++++++++++++++++");
		} else {
			adapter = new MyAdapter();
			lv_call.setAdapter(adapter);
		
			System.out.println(")))))))))))))");
			
		}
	}
}
