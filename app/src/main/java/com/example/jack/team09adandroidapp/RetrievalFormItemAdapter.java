package com.example.jack.team09adandroidapp;

import android.app.Activity;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

/**
 * Created by CHEN ZIQING on 2018/1/29.
 */

public class RetrievalFormItemAdapter extends ArrayAdapter<RetrievalFormItem> {
    private Activity context;
    private List<RetrievalFormItem> list;
    private int resource;

    public RetrievalFormItemAdapter(Activity context, List<RetrievalFormItem> list) {
        super(context, R.layout.retrieval_form_item, list);
        this.resource = resource;
        this.list = list;
        this.context=context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= context.getLayoutInflater();
        View v= inflater.inflate(R.layout.retrieval_form_item,null,true);
        TextView itemID=(TextView)v.findViewById(R.id.itemID);
        TextView itemDescription=(TextView)v.findViewById(R.id.itemDescription);
        TextView itemLocation=(TextView)v.findViewById(R.id.itemLocation);
        TextView itemNeeded=(TextView)v.findViewById(R.id.itemNeeded);

        itemID.setText(list.get(position).getItemID().toString());
        itemDescription.setText(list.get(position).getItemDescription().toString());
        itemLocation.setText(list.get(position).getItemLocation().toString());
        itemNeeded.setText(String.valueOf(list.get(position).getItemNeeded()));

        return v;
        //commit
    }
}
