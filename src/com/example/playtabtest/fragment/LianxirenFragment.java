package com.example.playtabtest.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.example.phone.CharacterParser;
import com.example.phone.ClearEditText;
import com.example.phone.PinyinComparator;
import com.example.phone.SideBar;
import com.example.phone.SortAdpter;
import com.example.phone.SortModel;
import com.example.phone.SideBar.OnTouchingLetterChangedListener;
import com.example.playtabtest.AddPeopleActivity;
import com.example.playtabtest.ConstactsEditActivity;
import com.example.playtabtest.MainActivityss;
import com.example.playtabtest.MainActivityss.TabFragmentPagerAdapter;
import com.example.playtabtest.fragment.SortAdpterqq.ViewHolder;
import com.example.tiantian.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class LianxirenFragment extends Fragment {

	//通讯录
	
		private ListView sortListView;
		private SideBar sideBar;
		private TextView dialog;
		private Button addpeople;
		private SortAdpterqq adapter1;
		private ClearEditText mClearEditText;

		/**
		 * 姹夊瓧杞崲鎴愭嫾闊崇殑绫�
		 */
		private CharacterParser characterParser;
		private List<SortModel> SourceDateList;
		
		/**
		 * 鏍规嵁鎷奸煶鏉ユ帓鍒桳istView閲岄潰鐨勬暟鎹被
		 */
		private PinyinComparator pinyinComparator;
		private View rootView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		 rootView = inflater.inflate(R.layout.activity_teltephone, container, false);
			initViews();
		
		
		
		return rootView;
	}
	
	private void initViews() {
		//瀹炰緥鍖栨眽瀛楄浆鎷奸煶绫�
		characterParser = CharacterParser.getInstance();
		
		pinyinComparator = new PinyinComparator();
		sideBar = (SideBar) rootView.findViewById(R.id.sidrbar);
		dialog = (TextView)  rootView.findViewById(R.id.dialog);
		addpeople = (Button) rootView.findViewById(R.id.addpeople);
		sideBar.setTextView(dialog);
		
		//璁剧疆鍙充晶瑙︽懜鐩戝惉
		addpeople.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent intent = new Intent(getActivity(),AddPeopleActivity.class);
			startActivity(intent);
			}
		});
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
			
			@Override
			public void onTouchingLetterChanged(String s) {
				//璇ュ瓧姣嶉娆″嚭鐜扮殑浣嶇疆
				int position = adapter1.getPositionForSection(s.charAt(0));
				if(position != -1){
					sortListView.setSelection(position);
				}
			}
		});
		
		sortListView = (ListView) rootView.findViewById(R.id.country_lvcountry);
		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//杩欓噷瑕佸埄鐢╝dapter.getItem(position)鏉ヨ幏鍙栧綋鍓峱osition鎵�搴旂殑瀵硅薄
				//Toast.makeText(getApplication(), ((SortModel)adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
				String number=MainActivityss.callRecord1s.get(((SortModel)adapter1.getItem(position)).getName());
				Intent intent=new Intent(getActivity(),ConstactsEditActivity.class);
				intent.putExtra("number", number);
				if(((SortModel)adapter1.getItem(position)).getName()==null){
					intent.putExtra("status", false);
				}else{
					intent.putExtra("status", true);
				}
				intent.putExtra("name", ((SortModel)adapter1.getItem(position)).getName());
				startActivity(intent);
				
			}
		});
		
		List<String> constact=new ArrayList<String>();
		for(Iterator<String> keys=MainActivityss.callRecord1s.keySet().iterator();keys.hasNext();){
			String key=keys.next();
			if(key==null){
				key = "未知";
			}
			constact.add(key);
			//在这里加上判断就能够避免key为null时崩溃
			
			
			
			
			
		}
		String []names=new String[]{};
		names=constact.toArray(names);
		for(int i=0;i<names.length;i++){
			System.out.println(names[i]+"*************************************");
		}
	
		SourceDateList = filledData(names);
		
		// 鏍规嵁a-z杩涜鎺掑簭婧愭暟鎹�
		Collections.sort(SourceDateList, pinyinComparator);
		adapter1 = new SortAdpterqq(getActivity(), SourceDateList);
		sortListView.setAdapter(adapter1);
		
		
		mClearEditText = (ClearEditText) rootView.findViewById(R.id.filter_edit);
		
		//鏍规嵁杈撳叆妗嗚緭鍏ュ�鐨勬敼鍙樻潵杩囨护鎼滅储
		mClearEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				//褰撹緭鍏ユ閲岄潰鐨勫�涓虹┖锛屾洿鏂颁负鍘熸潵鐨勫垪琛紝鍚﹀垯涓鸿繃婊ゆ暟鎹垪琛�
				filterData(s.toString());
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
			
			
			class SortAdpterqq extends BaseAdapter implements SectionIndexer{
				private List<SortModel> list = null;
				private Context mContext;
				
				public void SortAdpter(Context mContext, List<SortModel> list) {
					this.mContext = mContext;
					this.list = list;
				}
				public void updateListView(List<SortModel> list){
					this.list = list;
					notifyDataSetChanged();
				}
				@Override
				public int getCount() {
					// TODO Auto-generated method stub
					return list.size();
				}

				@Override
				public Object getItem(int position) {
					// TODO Auto-generated method stub
					return list.get(position);
				}

				@Override
				public long getItemId(int position) {
					// TODO Auto-generated method stub
					return position;
				}

				@Override
				public View getView(final int position, View view, ViewGroup arg2) {
					ViewHolder viewHolder = null;
					final SortModel mContent = list.get(position);
					if (view == null) {
						viewHolder = new ViewHolder();
						view = LayoutInflater.from(mContext).inflate(R.layout.mi_constacts_item, null);
						viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
						viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
						view.setTag(viewHolder);
					} else {
						viewHolder = (ViewHolder) view.getTag();
					}
					
					//鏍规嵁position鑾峰彇鍒嗙被鐨勯瀛楁瘝鐨凜har ascii鍊�
					int section = getSectionForPosition(position);
					
					//濡傛灉褰撳墠浣嶇疆绛変簬璇ュ垎绫婚瀛楁瘝鐨凜har鐨勪綅缃�锛屽垯璁や负鏄涓�鍑虹幇
					if(position == getPositionForSection(section)){
						viewHolder.tvLetter.setVisibility(View.VISIBLE);
						viewHolder.tvLetter.setText(mContent.getSortLetters());
					}else{
						viewHolder.tvLetter.setVisibility(View.GONE);
					}
				
					viewHolder.tvTitle.setText(this.list.get(position).getName());
					
					return view;

				}
				


				final class ViewHolder {
					TextView tvLetter;
					TextView tvTitle;
				}

				@Override
				public int getPositionForSection(int section) {
					// TODO Auto-generated method stub
					for (int i = 0; i < getCount(); i++) {
						String sortStr = list.get(i).getSortLetters();
						char firstChar = sortStr.toUpperCase().charAt(0);
						if (firstChar == section) {
							return i;
						}
					}
					
					return -1;
				}

				@Override
				public int getSectionForPosition(int position) {
					// TODO Auto-generated method stub
					return list.get(position).getSortLetters().charAt(0);
				}
				private String getAlpha(String str) {
					String  sortStr = str.trim().substring(0, 1).toUpperCase();
					// 姝ｅ垯琛ㄨ揪寮忥紝鍒ゆ柇棣栧瓧姣嶆槸鍚︽槸鑻辨枃瀛楁瘝
					if (sortStr.matches("[A-Z]")) {
						return sortStr;
					} else {
						return "#";
					}
				}
				@Override
				public Object[] getSections() {
					// TODO Auto-generated method stub
					return null;
				}

//				@Override
//				public Object[] getSections() {
//					return null;
//				}

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
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
@Override
public void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	adapter1.notifyDataSetChanged();
	System.out.println(")))))))))))))))))))))))))))))))))))))+))))))))))))))************");
}
}
