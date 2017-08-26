package com.example.playtabtest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.phone.CallRecords;
import com.example.tiantian.R;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class XiangxiActivity extends Activity implements OnClickListener{
	private List<CallRecords> callRecords;
	private ViewPager viewPager;
	private TextView tv_name;
	private TextView tv_namelast;
	private LinearLayout ll_callrecord;
	private LinearLayout ll_calldetail;
	private TextView tv_callrecord;
	private TextView tv_calldetail;
	private ImageView iv_share;
	private ImageView iv_add_edit;
	private ImageView iv_back;
	private ListView lv_list;
	private TextView tv_add_edit;
	private TextView tv_phoneNumber;
	private TextView tv_type_addr;
	private String address="未知";
	
	private List<View> pagerList=new ArrayList<View>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mi_constacts_detail);
		init();
	}

	public void init() {
		viewPager=(ViewPager) findViewById(R.id.viewPager);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_namelast=(TextView) findViewById(R.id.tv_name_last);
		ll_callrecord=(LinearLayout) findViewById(R.id.ll_callrecords);
		ll_calldetail=(LinearLayout) findViewById(R.id.ll_calldetail);
		iv_share=(ImageView) findViewById(R.id.iv_share);
		iv_add_edit=(ImageView) findViewById(R.id.iv_add_edit);
		iv_back=(ImageView) findViewById(R.id.iv_back);
		tv_add_edit=(TextView) findViewById(R.id.tv_add_edit);
		tv_callrecord=(TextView) findViewById(R.id.tv_callrecords);
		tv_calldetail=(TextView) findViewById(R.id.tv_calldetail);
		String name = getIntent().getStringExtra("name");
		if(name==null){
			name =address;
			tv_name.setText(name);
			tv_add_edit.setText("新建");
		}else{
			tv_name.setText(name);
			
		}
		
		
		
		
		iv_back.setOnClickListener(this);
		iv_add_edit.setOnClickListener(this);
		iv_share.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.iv_back:
			Intent intent = new Intent(XiangxiActivity.this,MainActivityss.class);
			startActivity(intent);
			
			break;

		default:
			break;
		}
		
	}
	private List<CallRecords> getAllRecords(){
		String phonenumber = getIntent().getStringExtra("phoneNumber");
		List<CallRecords> callrecords=new ArrayList<CallRecords>();
		 Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI,
                 new String[]{CallLog.Calls.NUMBER,CallLog.Calls.CACHED_NAME,CallLog.Calls.TYPE, CallLog.Calls.DATE,CallLog.Calls.DURATION},
                 CallLog.Calls.NUMBER+"=?", new String[]{phonenumber},CallLog.Calls.DEFAULT_SORT_ORDER);
		while(cursor.moveToNext()){
			String number = cursor.getString(0);    //呼叫号码
    	    String name = cursor.getString(1);  //联系人姓名
    	    int type = cursor.getInt(2);          //来电:1,拨出:2,未接:3 
    	    Date date = new Date(Long.parseLong(cursor.getString(3)));
    	    long talktime = cursor.getLong(4);    //通话时长
    	    //不重复显示同一个来电，在详情里显示同一个来电的具体情况
    	    CallRecords c=new CallRecords(name, number, type, date, talktime);
    	    callrecords.add(c);
        }
	   cursor.close();
		
		
		return callRecords;
		
	}
}
