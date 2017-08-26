package com.example.phone;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.example.naoling.NaolingActivity;
import com.example.tiantian.MainActivity;
import com.example.tiantian.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.phone.SideBar.OnTouchingLetterChangedListener;
import com.example.phone.SortAdpter;
import com.example.playtabtest.MainActivityss;
public class PhoneActivity extends Activity implements OnClickListener,
		TextWatcher {
	ArrayList<View> Viewlist = new ArrayList<View>();
	private ViewPager pager;
	private ListView lianxiPeoPle;
	private ListView xinximulu;
	private ImageView IimageView;
	private ImageView lianxiren;
	private ImageView duanxin;
	private ImageView tonghuajilu;
	private ImageView tongxunlu;
	private ImageView duanxinxi;

	private EditText et;
	private Button bt_del;
	private ListView lv_call;
	public static List<CallRecords> callRecords;
	private SlidingDrawer sliding;
	private MyAdapter adapter;
	private PagerAdapter pagerAdapter;

	//通讯录
	
	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	
	private SortAdpter adapter1;
	private ClearEditText mClearEditText;
	private Map<String,String> callRecord1s;

	/**
	 * 姹夊瓧杞崲鎴愭嫾闊崇殑绫�
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;
	
	/**
	 * 鏍规嵁鎷奸煶鏉ユ帓鍒桳istView閲岄潰鐨勬暟鎹被
	 */
	private PinyinComparator pinyinComparator;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone);
		pager = (ViewPager) findViewById(R.id.viewpager4);
		Viewlist.add(getLayoutInflater().inflate(R.layout.mi_callrecords, null));
		Viewlist.add(getLayoutInflater().inflate(R.layout.activity_teltephone,
				null));
		Viewlist.add(getLayoutInflater().inflate(
				R.layout.activity_phonemessage, null));

		xinximulu = (ListView) findViewById(R.id.xinximulu);
		lianxiPeoPle = (ListView) findViewById(R.id.lv_call);
		IimageView = (ImageView) findViewById(R.id.lianjie);
		tonghuajilu = (ImageView) findViewById(R.id.peoplephone);

		lianxiren = (ImageView) findViewById(R.id.lianxiren);
		tongxunlu = (ImageView) findViewById(R.id.tongxunlu);
		duanxinxi = (ImageView) findViewById(R.id.duanxinxi);
		duanxin = (ImageView) findViewById(R.id.duanxin);
		pagerAdapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return Viewlist.size();
			}

			// 当某一子条目被销毁时，一般发生在该条目被移出屏幕时
			/*
			 * 参数一：装了很多个iew对象的容器，就是装了所有viewpager所显示的view对象 参数二：当前被销毁条目的位置
			 * 参数三：当前被销毁的对象
			 */
			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {

				container.removeView(Viewlist.get(position));
				// super.destroyItem(container, position, object);

			}

			@Override
			// 当子条目被创建时，一般发生在该条目被移入屏幕时
			public Object instantiateItem(ViewGroup container, int position) {
				container.addView(Viewlist.get(position));
				callRecords = getAllRecords();
				callRecord1s=getAllCallRecords();
				initViews();
				init();
				if (adapter != null) {
					adapter.notifyDataSetChanged();
					System.out.println(")))))))))))))");
				} else {
					adapter = new MyAdapter();
					lv_call.setAdapter(adapter);
				}
				return Viewlist.get(position);
			}

		};
		pager.setAdapter(pagerAdapter);
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if (arg0 == 0) {
					tonghuajilu.setImageResource(R.drawable.tab_dial_selected);
					tongxunlu.setImageResource(R.drawable.tab_buddy_normal);
					duanxinxi.setImageResource(R.drawable.tab_dialog_normal);

				} else if (arg0 == 1) {
					tonghuajilu.setImageResource(R.drawable.tab_dial_normal);
					tongxunlu.setImageResource(R.drawable.tab_buddy_selected);
					duanxinxi.setImageResource(R.drawable.tab_dialog_normal);
				} else {
					tonghuajilu.setImageResource(R.drawable.tab_dial_normal);
					tongxunlu.setImageResource(R.drawable.tab_buddy_normal);
					duanxinxi.setImageResource(R.drawable.tab_dialog_selected);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		tonghuajilu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tonghuajilu.setImageResource(R.drawable.tab_dial_selected);
				tongxunlu.setImageResource(R.drawable.tab_buddy_normal);
				duanxinxi.setImageResource(R.drawable.tab_dialog_normal);
				pager.setCurrentItem(0);
			}
		});
		tongxunlu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tonghuajilu.setImageResource(R.drawable.tab_dial_normal);
				tongxunlu.setImageResource(R.drawable.tab_buddy_selected);
				duanxinxi.setImageResource(R.drawable.tab_dialog_normal);
				pager.setCurrentItem(1);
			}
		});
		duanxinxi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tonghuajilu.setImageResource(R.drawable.tab_dial_normal);
				tongxunlu.setImageResource(R.drawable.tab_buddy_normal);
				duanxinxi.setImageResource(R.drawable.tab_dialog_selected);
				pager.setCurrentItem(2);
			}
		});

	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
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
	public void afterTextChanged(Editable s) {

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (TextUtils.isEmpty(s)) {
			et.setVisibility(View.GONE);
			bt_del.setVisibility(View.GONE);
		} else {
			et.setVisibility(View.VISIBLE);
			bt_del.setVisibility(View.VISIBLE);
		}
	}

	private void init() {
		et = (EditText) findViewById(R.id.et_search);
		bt_del = (Button) findViewById(R.id.input_del);
		lv_call = (ListView) findViewById(R.id.lv_call);
		sliding = (SlidingDrawer) findViewById(R.id.sd_key_grid);

		et.addTextChangedListener(this);
		et.setInputType(InputType.TYPE_NULL);// 灞忚斀杞敭鐩�
		bt_del.setOnClickListener(this);
		sliding.open();

		findViewById(R.id.ibtn_key_0).setOnClickListener(this);
		findViewById(R.id.ibtn_key_1).setOnClickListener(this);
		findViewById(R.id.ibtn_key_2).setOnClickListener(this);
		findViewById(R.id.ibtn_key_3).setOnClickListener(this);
		findViewById(R.id.ibtn_key_4).setOnClickListener(this);
		findViewById(R.id.ibtn_key_5).setOnClickListener(this);
		findViewById(R.id.ibtn_key_6).setOnClickListener(this);
		findViewById(R.id.ibtn_key_7).setOnClickListener(this);
		findViewById(R.id.ibtn_key_8).setOnClickListener(this);
		findViewById(R.id.ibtn_key_9).setOnClickListener(this);
		findViewById(R.id.ibtn_key_l).setOnClickListener(this);
		findViewById(R.id.ibtn_key_r).setOnClickListener(this);
		findViewById(R.id.ibtn_key_del).setOnClickListener(this);

		findViewById(R.id.keybord_down).setOnClickListener(this);
		findViewById(R.id.call_out).setOnClickListener(this);

		adapter = new MyAdapter();
		lv_call.setAdapter(adapter);
		lv_call.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				CallRecords cr = callRecords.get(position);
				Intent intent = new Intent(PhoneActivity.this,
						MainActivityss.class);
				intent.putExtra("number", cr.getNumber());
				startActivity(intent);
			}

		});
	}

	//""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
	private void initViews() {
		//瀹炰緥鍖栨眽瀛楄浆鎷奸煶绫�
		characterParser = CharacterParser.getInstance();
		
		pinyinComparator = new PinyinComparator();
		View  view = getLayoutInflater().inflate(R.layout.activity_teltephone, null);
		sideBar = (SideBar) view.findViewById(R.id.sidrbar);
		dialog = (TextView)  view.findViewById(R.id.dialog);
		sideBar.setTextView(dialog);
		
		//璁剧疆鍙充晶瑙︽懜鐩戝惉
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
			
//			@SuppressLint("NewApi")
//			@Override
			public void onTouchingLetterChanged(String s) {
				//璇ュ瓧姣嶉娆″嚭鐜扮殑浣嶇疆
				int position = adapter1.getPositionForSection(s.charAt(0));
				if(position != -1){
					sortListView.setSelection(position);
				}
			}
		});
		
		sortListView = (ListView) view.findViewById(R.id.country_lvcountry);
		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//杩欓噷瑕佸埄鐢╝dapter.getItem(position)鏉ヨ幏鍙栧綋鍓峱osition鎵�搴旂殑瀵硅薄
				//Toast.makeText(getApplication(), ((SortModel)adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
				String number=callRecord1s.get(((SortModel)adapter1.getItem(position)).getName());
				Intent intent=new Intent(PhoneActivity.this,PhoneActivity.class);
				intent.putExtra("number", number);
				intent.putExtra("name", ((SortModel)adapter1.getItem(position)).getName());
				startActivity(intent);
				
			}
		});
		
		List<String> constact=new ArrayList<String>();
		for(Iterator<String> keys=callRecord1s.keySet().iterator();keys.hasNext();){
			String key=keys.next();
			constact.add(key);
			//在这里加上判断就能够避免未知的错的
		}
		String []names=new String[]{};
		names=constact.toArray(names);
		for(int i=0;i<names.length;i++){
			System.out.println(names[i]+"*************************************");
		}
	
		SourceDateList = filledData(names);
		
		// 鏍规嵁a-z杩涜鎺掑簭婧愭暟鎹�
		Collections.sort(SourceDateList, pinyinComparator);
		adapter1 = new SortAdpter(this, SourceDateList);
		sortListView.setAdapter(adapter1);
		
		
		mClearEditText = (ClearEditText) view.findViewById(R.id.filter_edit);
		
		//鏍规嵁杈撳叆妗嗚緭鍏ュ�鐨勬敼鍙樻潵杩囨护鎼滅储
		mClearEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				//褰撹緭鍏ユ閲岄潰鐨勫�涓虹┖锛屾洿鏂颁负鍘熸潵鐨勫垪琛紝鍚﹀垯涓鸿繃婊ゆ暟鎹垪琛�
				filterData(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		
	}

    

	/**
	 * 涓篖istView濉厖鏁版嵁
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(String [] date){
		List<SortModel> mSortList = new ArrayList<SortModel>();
		
		for(int i=0; i<date.length; i++){
			SortModel sortModel = new SortModel();
			sortModel.setName(date[i]);
			//姹夊瓧杞崲鎴愭嫾闊�
			System.out.println(date[i]+"))))))))))))))))))))))))))))))");
			String pinyin = characterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();
			
			// 姝ｅ垯琛ㄨ揪寮忥紝鍒ゆ柇棣栧瓧姣嶆槸鍚︽槸鑻辨枃瀛楁瘝
			if(sortString.matches("[A-Z]")){
				sortModel.setSortLetters(sortString.toUpperCase());
			}else{
				sortModel.setSortLetters("#");
			}
			
			mSortList.add(sortModel);
		}
		return mSortList;
		
	}
	
	/**
	 * 鏍规嵁杈撳叆妗嗕腑鐨勫�鏉ヨ繃婊ゆ暟鎹苟鏇存柊ListView
	 * @param filterStr
	 */
	private void filterData(String filterStr){
		List<SortModel> filterDateList = new ArrayList<SortModel>();
		
		if(TextUtils.isEmpty(filterStr)){
			filterDateList = SourceDateList;
		}else{
			filterDateList.clear();
			for(SortModel sortModel : SourceDateList){
				String name = sortModel.getName();
				System.out.println(name+"PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
				if(name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
					filterDateList.add(sortModel);
				}
			}
		}
		
		// 鏍规嵁a-z杩涜鎺掑簭
		Collections.sort(filterDateList, pinyinComparator);
		adapter1.updateListView(filterDateList);
	}
	
	/**
	 * 鑾峰彇鎵�湁鏁版嵁
	 * 
	 * @return
	 */
	private Map<String,String> getAllCallRecords() {
		Map<String,String> temp = new HashMap<String, String>();
		Cursor c = getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI,
				null,
				null,
				null,
				ContactsContract.Contacts.DISPLAY_NAME
						+ " COLLATE LOCALIZED ASC");
		if (c.moveToFirst()) {
			do {
				// 鑾峰緱鑱旂郴浜虹殑ID鍙�
				String contactId = c.getString(c
						.getColumnIndex(ContactsContract.Contacts._ID));
				// 鑾峰緱鑱旂郴浜哄鍚�
				String name = c
						.getString(c
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				// 鏌ョ湅璇ヨ仈绯讳汉鏈夊灏戜釜鐢佃瘽鍙风爜銆傚鏋滄病鏈夎繖杩斿洖鍊间负0
				int phoneCount = c
						.getInt(c
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
				String number=null;
				if (phoneCount > 0) {
					// 鑾峰緱鑱旂郴浜虹殑鐢佃瘽鍙风爜
					Cursor phones = getContentResolver().query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " = " + contactId, null, null);
					if (phones.moveToFirst()) {
						number = phones
								.getString(phones
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						}
					phones.close();
				}
				temp.put(name, number);
			} while (c.moveToNext());
		}
		c.close();
		return temp;
	}
	
	//""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
	@SuppressLint("SimpleDateFormat")
	private List<CallRecords> getAllRecords() {
		List<CallRecords> callrecords = new ArrayList<CallRecords>();
		List<String> phoneNumber = new ArrayList<String>();
		Cursor cursor = getContentResolver().query(
				CallLog.Calls.CONTENT_URI,
				new String[] { CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME,
						CallLog.Calls.TYPE, CallLog.Calls.DATE,
						CallLog.Calls.DURATION }, null, null,
				CallLog.Calls.DEFAULT_SORT_ORDER);
		while (cursor.moveToNext()) {
			String number = cursor.getString(0);
			String name = cursor.getString(1);
			int type = cursor.getInt(2);
			Date date = new Date(Long.parseLong(cursor.getString(3)));
			long talktime = cursor.getLong(4);
			if (!phoneNumber.contains(number)) {
				phoneNumber.add(number);
				CallRecords c = new CallRecords(name, number, type, date,
						talktime);
				callrecords.add(c);

				// 获取电话通话时间的信息
			}
		}
		cursor.close();

		return callrecords;

	}
//**********************************实现联系人的显示方式**********************************************
	
	/**
	 * 閫氳瘽璁板綍閫傞厤鍣�
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return callRecords.size();
		}

		@Override
		public Object getItem(int position) {
			return callRecords.get(position);
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
				convertView = getLayoutInflater().inflate(
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

			final CallRecords record = callRecords.get(position);
			final String name = record.getName();

			// System.out.println(ti+"))))))))))))))))))))))))))))))))))))))))");

			if (name != null) {
				holder.call_number.setText(name);
			} else {
				holder.call_number.setText(record.getNumber());
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String callTime = format.format(record.getDate());
			holder.call_time.setText(callTime);
			long ti = record.getTalktime();
			holder.tonghushijian.setText(ti + "");
			// holder.tonghucishu.setText(record.getType());
			// 設置來電是否為未接或者打出還是已結的？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？
			int type = record.getType();
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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		pager.setAdapter(pagerAdapter);
	}
}
