package com.example.jack.team09adandroidapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by doujohner on 1/2/2018.
 */

public class MyDisAdapter_rep extends BaseAdapter{
    private Context mContext;
    private List<Disbursement> mData;
    public MyDisAdapter_rep() {
    }
    public MyDisAdapter_rep(Context context,List<Disbursement> mData) {
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
        MyDisAdapter_rep.ViewHolder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.disburselist_rep,null);
            holder = new MyDisAdapter_rep.ViewHolder();

            holder.disID =  convertView.findViewById(R.id.disIDtextView_rep);
            holder.disDate = convertView.findViewById(R.id.disDatetextView_rep);
            holder.status = convertView.findViewById(R.id.statustextView_rep);
            convertView.setTag(holder);   //save Holder to convertView
        }else{
            holder = (MyDisAdapter_rep.ViewHolder) convertView.getTag();
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
        holder.status.setText(mData.get(position).getStatus());
        return convertView;
    }

    static class ViewHolder{
        TextView disID;
        TextView disDate;
        TextView status;
    }

}
