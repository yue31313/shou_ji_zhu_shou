package com.example.playtabtest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;

import com.example.phone.CallRecords;
import com.example.phone.CharacterParser;
import com.example.phone.ClearEditText;
import com.example.phone.PhoneActivity;
import com.example.phone.PinyinComparator;
import com.example.phone.SideBar;
import com.example.phone.Sms;
import com.example.phone.SortAdpter;
import com.example.phone.SortModel;
import com.example.phone.SideBar.OnTouchingLetterChangedListener;
import com.example.playtabtest.fragment.CommonUIFragment;
import com.example.playtabtest.fragment.LaunchUIFragment;
import com.example.playtabtest.fragment.LianxirenFragment;
import com.example.playtabtest.fragment.MeissageFragment;
import com.example.playtabtest.view.SyncHorizontalScrollView;
import com.example.tiantian.R;

public class MainActivityss extends FragmentActivity {

	public static final String ARGUMENTS_NAME = "arg";
	private RelativeLayout rl_nav;
	private SyncHorizontalScrollView mHsv;
	private RadioGroup rg_nav_content;
	private ImageView iv_nav_indicator;
	private ImageView iv_nav_left;
	private ImageView iv_nav_right;
	private ViewPager mViewPager;
	private int indicatorWidth;
	public static String[] tabTitle = { "通话记录", "联系人", "短信息", "用户分组" }; // 标题
	private LayoutInflater mInflater;
	private TabFragmentPagerAdapter mAdapter;
	private int currentIndicatorLeft = 0;
	public static List<Sms> smsss;
	public static List<CallRecords> callRecords;
	
	
	//通讯录
		private ListView sortListView;
		private SideBar sideBar;
		private TextView dialog;
		
		private SortAdpter adapter1;
		private ClearEditText mClearEditText;
		public static Map<String,String> callRecord1s;

		private CharacterParser characterParser;
		private List<SortModel> SourceDateList;
		
		private PinyinComparator pinyinComparator;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainsssss);
		callRecords = getAllRecords();
		callRecord1s=getAllCallRecords();
		smsss=getAllsms();
		findViewById();
		initView();
		setListener();
		}
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
			System.out.println(date[i]+"))))))))))))))))))))))))))))))");
			String pinyin = characterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();
			
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
	
	
	
	
	
	private void setListener() {

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {

				if (rg_nav_content != null
						&& rg_nav_content.getChildCount() > position) {
					((RadioButton) rg_nav_content.getChildAt(position))
							.performClick();
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		rg_nav_content
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {

						if (rg_nav_content.getChildAt(checkedId) != null) {

							TranslateAnimation animation = new TranslateAnimation(
									currentIndicatorLeft,
									((RadioButton) rg_nav_content
											.getChildAt(checkedId)).getLeft(),
									0f, 0f);
							animation.setInterpolator(new LinearInterpolator());
							animation.setDuration(100);
							animation.setFillAfter(true);

							// 执行位移动画
							iv_nav_indicator.startAnimation(animation);

							mViewPager.setCurrentItem(checkedId); // ViewPager
																	// 跟随一起 切换

							// 记录当前 下标的距最左侧的 距离
							currentIndicatorLeft = ((RadioButton) rg_nav_content
									.getChildAt(checkedId)).getLeft();

							mHsv.smoothScrollTo(
									(checkedId > 1 ? ((RadioButton) rg_nav_content
											.getChildAt(checkedId)).getLeft()
											: 0)
											- ((RadioButton) rg_nav_content
													.getChildAt(2)).getLeft(),
									0);
						}
					}
				});
	}

	private void initView() {

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		indicatorWidth = dm.widthPixels / 4;

		LayoutParams cursor_Params = iv_nav_indicator.getLayoutParams();
		cursor_Params.width = indicatorWidth;// 初始化滑动下标的宽
		iv_nav_indicator.setLayoutParams(cursor_Params);

		mHsv.setSomeParam(rl_nav, iv_nav_left, iv_nav_right, this);

		// 获取布局填充器
		mInflater = (LayoutInflater) this
				.getSystemService(LAYOUT_INFLATER_SERVICE);

		// 另一种方式获取
		// LayoutInflater mInflater = LayoutInflater.from(this);

		initNavigationHSV();

		mAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mAdapter);
	}

	private void initNavigationHSV() {

		rg_nav_content.removeAllViews();

		for (int i = 0; i < tabTitle.length; i++) {

			RadioButton rb = (RadioButton) mInflater.inflate(
					R.layout.nav_radiogroup_item, null);
			rb.setId(i);
			rb.setText(tabTitle[i]);
			rb.setLayoutParams(new LayoutParams(indicatorWidth,
					LayoutParams.MATCH_PARENT));

			rg_nav_content.addView(rb);
		}

	}

	//获取短信列表
	private List<Sms> getAllsms(){
		final String SMS_URI_INBOX = "content://sms/";
		List<Sms> smss=new ArrayList<Sms>();
		List<String> phoneNumbers=new ArrayList<String>();
        try {  
            ContentResolver cr =getContentResolver();  
            String[] projection = new String[] { "_id", "address", "person","body", "date", "type" };  
            Uri uri = Uri.parse(SMS_URI_INBOX);  
            Cursor cursor = cr.query(uri, projection, null, null, "date desc");  
            while (cursor.moveToNext()) {  
                // -----------------------信息----------------  
                //int nameColumn = cursor.getColumnIndex("person");        // 联系人姓名列表序号  
                int phoneNumberColumn = cursor.getColumnIndex("address");// 手机号  
                int smsbodyColumn = cursor.getColumnIndex("body");       // 短信内容  
                int dateColumn = cursor.getColumnIndex("date");          // 日期  
                int typeColumn = cursor.getColumnIndex("type");          // 收发类型 1表示接受 2表示发送  
                
               // String nameId = cursor.getString(nameColumn);  
                String phoneNumber = cursor.getString(phoneNumberColumn);  
                String smscontent = cursor.getString(smsbodyColumn);  
                Date date = new Date(Long.parseLong(cursor.getString(dateColumn)));  
                int type=cursor.getInt(typeColumn);
  
                // --------------------------匹配联系人名字--------------------------  
                Uri personUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,phoneNumber);  
                Cursor localCursor = cr.query(personUri, new String[] {PhoneLookup.DISPLAY_NAME, PhoneLookup.PHOTO_ID,PhoneLookup._ID }, null, null, null); 
                String name=null;
                if (localCursor.getCount()!=0) {  
                    localCursor.moveToFirst();  
                    name = localCursor.getString(localCursor.getColumnIndex(PhoneLookup.DISPLAY_NAME));
                    localCursor.close();
                  } 
                //System.out.println(name+"--"+phoneNumber+"--"+smsbody+"--"+date+"--"+(type==1?"收":"发"));
                if(!phoneNumbers.contains(phoneNumber)){
                	phoneNumbers.add(phoneNumber);
                	Sms sms=new Sms(name, phoneNumber, smscontent, type, date);
                	smss.add(sms);
                } 
            }
            cursor.close();
            return smss;
        } catch (SQLiteException e) { 
        	Toast.makeText(MainActivityss.this, "获取联系人失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();  
        }
        return null;
	}
	private void findViewById() {

		rl_nav = (RelativeLayout) findViewById(R.id.rl_nav);

		mHsv = (SyncHorizontalScrollView) findViewById(R.id.mHsv);

		rg_nav_content = (RadioGroup) findViewById(R.id.rg_nav_content);

		iv_nav_indicator = (ImageView) findViewById(R.id.iv_nav_indicator);
		iv_nav_left = (ImageView) findViewById(R.id.iv_nav_left);
		iv_nav_right = (ImageView) findViewById(R.id.iv_nav_right);

		mViewPager = (ViewPager) findViewById(R.id.mViewPager);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public static class TabFragmentPagerAdapter extends FragmentPagerAdapter {

		

		public TabFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			Fragment ft = null;
			switch (arg0) {
			case 0:
				ft = new LaunchUIFragment();
				break;
			case 1:
				ft = new LianxirenFragment();
				break;
			case 2:
				ft = new MeissageFragment();
				break;
			default:
				ft = new CommonUIFragment();
				Bundle args = new Bundle();
				args.putString(ARGUMENTS_NAME, tabTitle[arg0]);
				ft.setArguments(args);
				break;
			}
			return ft;
		}

		@Override
		public int getCount() {

			return tabTitle.length;
		}
		
			
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		mAdapter.notifyDataSetChanged();
		mViewPager.setAdapter(mAdapter);
		callRecords = getAllRecords();
		callRecord1s=getAllCallRecords();
		System.out.println("+++++++++++++++++++++++++++++++++");
//		setListener();
		
	}

}
