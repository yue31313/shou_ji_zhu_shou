package com.example.jiasu;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.example.ruanjianguanjia.AppInfo;
import com.example.tiantian.R;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

public class TabdemoActivity extends TabActivity implements TabContentFactory {
	TabHost host;
	TabWidget widget;
	AppInfoManager manager1;
	private TextView neicunShow;
	private TextView yiyongbili;
	private TextView jinchengNum;
	private View neicuntiao;
	private ListView liebiao;
	private Button jiasuqi;
	List<AppInfo> lists;
	List<AppInfo> listsjilu;
	private View vv;
	ViewHolder holder;
	private ProgressBar progressbar;
	ArrayList<String> Listdian = new ArrayList<String>();
	MylistAdapter mylistAdapter;
String mangermassage[] ={"基本信息","CPU","内存","分辨率","像素","WIFI"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		host = getTabHost();
		widget = getTabWidget();
		View view = getLayoutInflater().inflate(R.layout.activity_tabtoubiao,
				null);
		TabSpec tabSpec = host.newTabSpec("tab1").setIndicator(view)
				.setContent(this);
		View view1 = getLayoutInflater().inflate(R.layout.activity_touwenjian,
				null);
		TabSpec tabSpec1 = host.newTabSpec("tab2").setIndicator(view1)
				.setContent(new Intent(TabdemoActivity.this,JianCe.class));

		host.addTab(tabSpec);
		host.addTab(tabSpec1);
		manager1 = new AppInfoManager(TabdemoActivity.this);
		listsjilu = new ArrayList<AppInfo>();
		lists = manager1.getRunningAppInfo();
		for (int i = 0; i < lists.size(); i++) {
			if (lists.get(i).getBaoName().equals("com.example.tiantian")) {

				Collections.swap(lists, 0, i);
				break;
			}
		}

		neicunShow.setText(manager1.getEmployMemory() + "/"
				+ manager1.getSystemAvaialbeMemory());
		jinchengNum
				.setText("应用程序：" + manager1.getRunningAppInfo().size() + "个");
		yiyongbili.setText(manager1.getMemoryPrecent() + "%");

		progressbar.setMax(100);
		progressbar.setProgress(manager1.getMemoryPrecent());
		mylistAdapter = new MylistAdapter();
		liebiao.setAdapter(mylistAdapter);
		jiasuqi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				manager1.killProcess(Listdian);
				Iterator<String> it = Listdian.iterator();
				
				while(it.hasNext()){
					String s = it.next();
					for(int x=0;x<lists.size();x++){
						for(int y=0;y<Listdian.size();y++){
							if(lists.get(x).getBaoName().equals(Listdian.get(y))){
								listsjilu.add(lists.get(x));
							}
						}
					}
					
				}
				lists.removeAll(listsjilu);
				 mylistAdapter.notifyDataSetChanged();
				System.out.println(	manager1.killProcess(Listdian));
			}
		});
		liebiao.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				CheckBox checkBox = (CheckBox) arg1
						.findViewById(R.id.checkBox1);
				if(arg2!=0){
					if (checkBox.isChecked()) {
						checkBox.setChecked(false);
						Listdian.remove(lists.get(arg2).getBaoName());
						

					} else {
						
						checkBox.setChecked(true);
						Listdian.add(lists.get(arg2).getBaoName() );
						System.out.println(Listdian.get(0));
						
					}
				}
//				Toast.makeText(TabdemoActivity.this, arg2 + "",
//						Toast.LENGTH_SHORT).show();
				
			}
		});
		// 换标签的背景
		host.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String arg0) {
				// TODO Auto-generated method stub
				int id = host.getCurrentTab();
				switch (id) {
				case 0:

					View view = widget.getChildAt(id);
					view.setBackgroundResource(R.drawable.btn_a);
					View view1 = widget.getChildAt(1);
					view1.setBackgroundResource(R.drawable.btn_bg);
					break;

				case 1:
					View view2 = widget.getChildAt(id);
					view2.setBackgroundResource(R.drawable.btn_a);
					View view3 = widget.getChildAt(0);
					view3.setBackgroundResource(R.drawable.btn_bg);
					break;
				}

			}
		});
	}

	@Override
	public View createTabContent(String arg0) {
		// TODO Auto-generated method stub
		View view = null;
		if ("tab1".equals(arg0)) {
			view = getLayoutInflater()
					.inflate(R.layout.activity_tabhoust, null);
			neicunShow = (TextView) view.findViewById(R.id.neicunde);
			yiyongbili = (TextView) view.findViewById(R.id.yiyongneicunbili);
			jinchengNum = (TextView) view.findViewById(R.id.jinchenggeshu);
			neicuntiao = view.findViewById(R.id.neicuntiaozi);
			liebiao = (ListView) view.findViewById(R.id.chexuliebiao);
			jiasuqi = (Button) view.findViewById(R.id.jiasuqi);
			progressbar = (ProgressBar) view.findViewById(R.id.neicuntiaozi);

		}
		else if ("tab2".equals(arg0)) {
			String[][] erzi = {{"是否Root:"+manager1.isRoot()}};
			view = getLayoutInflater().inflate(R.layout.xitongjiance, null);
			ExpandableListView expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView1);
			expandableListView.setBackgroundResource(R.drawable.background);
			expandableListView.setAdapter(new MyExpandLisviewAdapter(erzi));
			expandableListView.setCacheColorHint(Color.TRANSPARENT);
			expandableListView.setGroupIndicator(null);
			
		}
		return view;
	}
class MyExpandLisviewAdapter extends BaseExpandableListAdapter {
	String[][] erzi = null;
	MyExpandLisviewAdapter(String[][] erzi){
		this.erzi = erzi;
	}
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return groupPosition*childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		
				TextView view = new TextView(TabdemoActivity.this);
				view.setTextColor(Color.BLACK);
				view.setTextSize(25);
				view.setText(erzi[groupPosition][childPosition]);
				return view;
		
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return erzi[groupPosition].length;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return mangermassage[groupPosition];
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return mangermassage.length;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView view = new TextView(TabdemoActivity.this);
		view.setTextColor(Color.BLACK);
		view.setTextSize(30);
		view.setText(mangermassage[groupPosition]);
		return view;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
	class MylistAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return lists.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return lists.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			holder = null;
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(
						R.layout.yunxingjinchengbuju, parent, false);
				holder = new ViewHolder();
				holder.jinchengName = (TextView) convertView
						.findViewById(R.id.jinchengming);
				holder.jinchengtishi = (TextView) convertView
						.findViewById(R.id.neirongtishi);
				holder.tubiao = (ImageView) convertView
						.findViewById(R.id.jinchengtubiao);
				holder.checkBox = (CheckBox) convertView
						.findViewById(R.id.checkBox1);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.checkBox.setChecked(false);
			
			if (Listdian.contains(position + "")) {
				holder.checkBox.setChecked(true);
			}
			
			holder.jinchengName.setText(lists.get(position).getName());
			holder.jinchengtishi.setText("服务"
					+ lists.get(position).getServicen() + "内存"
					+ lists.get(position).getNum());
			holder.tubiao.setImageDrawable(lists.get(position).getIcon());
			if(lists.get(position).getBaoName().equals("com.example.tiantian")){
				holder.checkBox.setVisibility(View.GONE);
			}
			return convertView;
		}

	}

	class ViewHolder {
		CheckBox checkBox;
		TextView jinchengName;
		TextView jinchengtishi;
		ImageView tubiao;
	}

}
