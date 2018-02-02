package com.example.jack.team09adandroidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Yuanxushu on 31/1/2018.
 */

public class AdjustmentVoucherCartAdapter extends BaseAdapter {
    private List<AdjustmentVoucherCartItem> myCartList;
//    private LayoutInflater myInflater;
    private boolean myCheckbox;
    private Context context;

    public AdjustmentVoucherCartAdapter(List<AdjustmentVoucherCartItem> list,Context context, boolean Checkbox) {
        this.myCartList = list;
        this.context=context;
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
        final ViewItem item;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.simple_adjvlist_item,
                    null);
            item = new ViewItem();
            item.itemCode = (TextView) convertView.findViewById(R.id.textView_itemCode);
            item.itemDescription = (TextView) convertView.findViewById(R.id.textView_itemDescription);
            item.qtyAdjusted = (TextView) convertView.findViewById(R.id.textView_adjQty);
            item.record = (TextView) convertView.findViewById(R.id.textView_adjRecord);
            item.itemCheckbox = (CheckBox) convertView.findViewById(R.id.CheckBoxSelected);

            convertView.setTag(item);
        } else {
            item = (ViewItem) convertView.getTag();
        }

        AdjustmentVoucherCartItem cartItem = myCartList.get(position);
        item.itemCode.setText(cartItem.getItemID());
        item.itemDescription.setText(cartItem.getDescription());
        item.qtyAdjusted.setText(Integer.toString(cartItem.getQtyAdjusted()));
        item.record.setText(cartItem.getRecord());

        if (!myCheckbox) {
            item.itemCheckbox.setVisibility(View.GONE);
        } else {
            if (cartItem.selected == true)
                item.itemCheckbox.setChecked(true);
            else
                item.itemCheckbox.setChecked(false);
        }
        return convertView;
    }


    private class ViewItem {
        TextView itemCode;
        TextView itemDescription;
        TextView qtyAdjusted;
        TextView record;
        CheckBox itemCheckbox;
    }
}


