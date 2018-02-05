package com.example.jack.team09adandroidapp;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by doujohner on 30/1/2018.
 */

public class MyDisItemAdapter extends BaseAdapter{
    private Context mContext;
    private List<DisbursementItem> mData;
    public MyDisItemAdapter(){

    }

    public MyDisItemAdapter(Context mContext, List<DisbursementItem> mData){
        this.mContext = mContext;
        this.mData = mData;
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyDisItemAdapter.ViewHolder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.disbursementitem,null);

            holder = new MyDisItemAdapter.ViewHolder();
            holder.itemID=convertView.findViewById(R.id.itemIDtextview);
            holder.itemDesc=convertView.findViewById(R.id.itemDesctextview);
            holder.expected=convertView.findViewById(R.id.expected);
            holder.actual= convertView.findViewById(R.id.actual);

            convertView.setTag(holder);
        }else{
            holder = (MyDisItemAdapter.ViewHolder) convertView.getTag();
        }

        holder.itemID.setText(mData.get(position).getItemID());
        holder.itemDesc.setText(mData.get(position).getItemDesc());
        holder.expected.setText(String.valueOf(mData.get(position).getExpected()));
        holder.actual.setText(String.valueOf(mData.get(position).getActual()));
        return convertView;
    }

    static class ViewHolder{
        TextView itemID;
        TextView itemDesc;
        TextView expected;
        EditText actual;
    }
}
