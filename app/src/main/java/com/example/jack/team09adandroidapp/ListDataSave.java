package com.example.jack.team09adandroidapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Yuanxushu on 30/1/2018.
 */

public class ListDataSave implements Serializable {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public ListDataSave(Context mContext, String preferenceName) {
        preferences = mContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /**
     * get List
     */
    public <T> List<AdjustmentVoucherCartItem> getDataList(String tag) {
        List<AdjustmentVoucherCartItem> datalist = new ArrayList<AdjustmentVoucherCartItem>();
        String strJson = preferences.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<AdjustmentVoucherCartItem>>() {
        }.getType());
        return datalist;
    }

    /**
     * save list
     */
    public <T> void setDataList(String tag, List<AdjustmentVoucherCartItem> datalist) {
        if (null == datalist || datalist.size() <= 0) {
            return;
        }
        Gson gson = new Gson();
        String strJson = gson.toJson(datalist);
        editor.putString(tag, strJson);
        editor.commit();
    }

    /**
     * remove one item from list
     */
    public <T>  void remove(String tag, AdjustmentVoucherCartItem item) {
        List<AdjustmentVoucherCartItem> datalist = getDataList(tag);
        if (null == datalist || datalist.size() <= 0) {
            return;
        }
        datalist.remove(item); //remove selected item

        Gson gson = new Gson();
        String jsonStr = gson.toJson(datalist); //transfer the updated list to json
        editor.putString(tag, jsonStr);
        editor.commit();
    }

    /**
     * clear the cart list
     */
    public  void clearAll(String tag) {
        editor.clear();
        editor.commit();
    }
}

