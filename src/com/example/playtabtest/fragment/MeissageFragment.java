package com.example.playtabtest.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.phone.Sms;
import com.example.playtabtest.MainActivityss;
import com.example.tiantian.R;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MeissageFragment extends Fragment {
	private View rootView;
	
	private ListView lv_sms;
	private String year;
	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = inflater.inflate(R.layout.activity_phonemessage, container, false);
		Date nowDate=new Date();
		year=(new SimpleDateFormat("yyyy").format(nowDate));
		
		init();
		return rootView;
		
		
		
	}
	
	
	
	/**
	 * 初始化数据
	 */
	private void init() {
		lv_sms=(ListView) rootView.findViewById(R.id.xinximulu);
		
		MyAdapter adapter=new MyAdapter();
		lv_sms.setAdapter(adapter);
		
		lv_sms.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				Sms sms=MainActivityss.smsss.get(position);
//				Intent intent=new Intent(getActivity(),SmsDetailActivity.class);
//				intent.putExtra("phoneNumber", sms.getPhoneNumber());
//				intent.putExtra("constactname", sms.getName());
//				startActivity(intent);
				
			}
			
		});
	}
	
	/**
	 * 短信适配器
	 * @author Administrator
	 *
	 */
	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return MainActivityss.smsss.size();
		}

		@Override
		public Object getItem(int position) {
			return MainActivityss.smsss.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder=null;
			if(convertView==null){
				
				convertView = getLayoutInflater(getArguments()).inflate(
						R.layout.mi_sms_item, null);
				
				holder=new Holder();
				
				holder.tv_sms_number=(TextView) convertView.findViewById(R.id.tv_sms_number);
				holder.tv_sms_content=(TextView) convertView.findViewById(R.id.tv_sms_content);
				holder.tv_sms_date=(TextView) convertView.findViewById(R.id.tv_sms_date);
				convertView.setTag(holder);
			}
			holder=(Holder) convertView.getTag();
			Sms sms=MainActivityss.smsss.get(position);
			if(sms.getName()!=null){
				holder.tv_sms_number.setText(sms.getName());
			}else{
				holder.tv_sms_number.setText(sms.getPhoneNumber());
			}
			holder.tv_sms_content.setText(sms.getSmscontent());
			
			Date date=sms.getDate();
	        String datetime=format.format(date);
	        //判断时间是否为今年
	        String time=datetime.substring(0, 4);
			if(year.equals(time)){
				holder.tv_sms_date.setText(new SimpleDateFormat(" MM月dd号").format(date));
			}else{
				holder.tv_sms_date.setText(datetime);
			}
			
			return convertView;
		}
		
		class Holder{
			TextView tv_sms_number;
			TextView tv_sms_content;
			TextView tv_sms_date;
		}
		
	}
	
	



	/**
	 * 写短信
	 * @param v
	 */
	public void writer(View v){
		
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
}
