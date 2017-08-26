package com.xiaomimanager.sms;

import java.util.List;



import com.example.tiantian.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * 鑷畾涔変俊鎭樉绀篴dapter
 *@Copyright Copyright (c) 2012 - 2100
  *@author    Administrator
 *@create at 2013-5-9
 *@version 1.1.0
 */
public class MessageAdapter extends BaseAdapter {
    private List<MessageModel> listModel;
    private Context context;
    private ViewHolder holder;

    class ViewHolder {
        TextView messageA;
        TextView dateA;
        TextView messageB;
        TextView dateB;
        RelativeLayout layoutB;
        RelativeLayout layoutA;

    }

    public MessageAdapter(Context context, List<MessageModel> listModel) {
        this.context = context;
        this.listModel = listModel;
        holder = new ViewHolder();
    }

    @Override
    public int getCount() {
        return listModel.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.mi_sms_detail_item,
            null);
        holder.messageA = (TextView) convertView.findViewById(R.id.messageA);
        holder.dateA = (TextView) convertView.findViewById(R.id.dateA);
        holder.messageB = (TextView) convertView.findViewById(R.id.messageB);
        holder.dateB = (TextView) convertView.findViewById(R.id.dateB);
        holder.layoutB = (RelativeLayout) convertView.findViewById(R.id.layoutB);
        holder.layoutA = (RelativeLayout) convertView.findViewById(R.id.layoutA);

        if (listModel.get(position).isA()) {//濡傛灉鏄疉鍙戦�鐨勬秷鎭�鍒欓殣钘廈鐨勫竷灞�
            holder.layoutB.setVisibility(View.GONE);
            holder.messageA.setText(listModel.get(position).getMessage());
            holder.dateA.setText(listModel.get(position).getDate());
        } else {//涓嶆槸A鍙戦�鐨勬秷鎭氨闅愯棌A甯冨眬
            holder.layoutA.setVisibility(View.GONE);
            holder.messageB.setText(listModel.get(position).getMessage());
            holder.dateB.setText(listModel.get(position).getDate());
        }
        return convertView;
    }

}
