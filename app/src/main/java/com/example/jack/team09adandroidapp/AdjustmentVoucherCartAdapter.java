package com.example.jack.team09adandroidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuanxushu on 31/1/2018.
 */

public class AdjustmentVoucherCartAdapter extends BaseAdapter {
    private List<AdjustmentVoucherCartItem> myCartList;
    private boolean myCheckbox;
    private Context context;

    public AdjustmentVoucherCartAdapter(List<AdjustmentVoucherCartItem> list, Context context, boolean Checkbox) {
        this.myCartList = list;
        this.context = context;
        boolean myCheckbox = Checkbox;
    }

    @Override
    public int getCount() {
        if (myCartList == null) {
            return 0;
        }
        return myCartList.size();
    }

    @Override
    public Object getItem(int position) {
        return myCartList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.simple_adjvlist_item,
                    null);
            holder = new ViewHolder();
            holder.itemCode = (TextView) convertView.findViewById(R.id.textView_itemCode);
            holder.itemDescription = (TextView) convertView.findViewById(R.id.textView_itemDescription);
            holder.qtyAdjusted = (TextView) convertView.findViewById(R.id.textView_adjQty);
            holder.record = (TextView) convertView.findViewById(R.id.textView_adjRecord);
            holder.itemCheckbox = (CheckBox) convertView.findViewById(R.id.CheckBoxSelected);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final AdjustmentVoucherCartItem cartItem = myCartList.get(position);
        holder.itemCode.setText(cartItem.getItemID());
        holder.itemDescription.setText(cartItem.getDescription());
        holder.qtyAdjusted.setText(Integer.toString(cartItem.getQtyAdjusted()));
        holder.record.setText(cartItem.getRecord());

        holder.itemCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    cartItem.selected = true;
                } else {
                    cartItem.selected = false;
                }
            }
        });
        holder.itemCheckbox.setChecked(cartItem.selected);
        return convertView;
    }

    private class ViewHolder {
        TextView itemCode;
        TextView itemDescription;
        TextView qtyAdjusted;
        TextView record;
        CheckBox itemCheckbox;
    }

    public void deleteAll() {
        if (myCartList == null) {
            myCartList = new ArrayList<AdjustmentVoucherCartItem>();
        }
        myCartList.clear();
        notifyDataSetChanged();
    }
}


