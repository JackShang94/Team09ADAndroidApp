package com.example.jack.team09adandroidapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by doujohner on 2/2/2018.
 */

public class MyDisItemAdapter_rep extends BaseAdapter {
    private Context mContext;
    private List<DisbursementItem> mData;
    public MyDisItemAdapter_rep() {
    }
    public MyDisItemAdapter_rep(Context context,List<DisbursementItem> mData) {
        this.mContext=context;
        this.mData=mData;
    }
    @Override
    public int getCount() {
        return mData.size();
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        MyDisItemAdapter_rep.ViewHolder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.disbursementitem_rep,null);
            holder = new MyDisItemAdapter_rep.ViewHolder();

            holder.itemID =  convertView.findViewById(R.id.itemIDtextview_rep);
            holder.itemDesc = convertView.findViewById(R.id.itemDesctextview_rep);
            holder.expected = convertView.findViewById(R.id.expected_rep);
            holder.actual = convertView.findViewById(R.id.actual_rep);
            convertView.setTag(holder);   //save Holder to convertView
        }else{
            holder = (MyDisItemAdapter_rep.ViewHolder) convertView.getTag();
        }



        holder.itemID.setText(mData.get(position).getItemID());
        holder.itemDesc.setText(mData.get(position).getItemDesc());
        try{
            holder.expected.setText(String.valueOf(mData.get(position).getExpected()));
            holder.actual.setText(String.valueOf(mData.get(position).getActual()));

        }catch (Exception e){
            Log.e("Integer format",e.toString());
        }

        return convertView;
    }

    private class ViewHolder{
        TextView itemID;
        TextView itemDesc;
        TextView expected;
        TextView actual;
    }

}