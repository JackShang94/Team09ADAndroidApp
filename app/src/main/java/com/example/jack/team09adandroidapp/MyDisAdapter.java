package com.example.jack.team09adandroidapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by doujohner on 29/1/2018.
 */

public class MyDisAdapter extends BaseAdapter {
    private Context mContext;
    private List<Disbursement> mData;
    public MyDisAdapter(){

    }

    public MyDisAdapter(Context mContext, List<Disbursement> mData){
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.disburselist,null);
            holder = new ViewHolder();

            holder.disID =  convertView.findViewById(R.id.disIDtextView);
            holder.disDate = convertView.findViewById(R.id.disDatetextView);
            convertView.setTag(holder);   //save Holder to convertView
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        String date =mData.get(position).getDisDate();
        try {
            String results = date.replaceAll("^/Date\\(","");
            results = results.substring(0, results.indexOf('+'));
            long time = Long.parseLong(results);
            Date myDate = new Date(time);

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String date_string = sdf.format(time);
            holder.disDate.setText(date_string);
        }catch (Exception e){
            Log.e("time",e.toString());
        }
        holder.disID.setText(String.valueOf(mData.get(position).getDisID()));

        return convertView;
    }

    static class ViewHolder{
        TextView disID;
        TextView disDate;
    }
}
