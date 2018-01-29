package com.example.jack.team09adandroidapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
/**
 * Created by CHEN ZIQING on 2018/1/28.
 */

public class RetrievalFormItem extends HashMap {

    final static String baseURL = "http://172.17.251.239/AD/AndroidServices/RetrievalFormItemService.svc/RetrievalFormItem/get";
    public RetrievalFormItem(String itemID,String itemDescription,String itemLocation,int itemNeeded,int itemActual, List<BreakdownByDepartment> breakdownByDeptList ) {
        put("ItemID", itemID);
        put("ItemDescription", itemDescription);
        put("itemLocation", itemLocation);
        put("itemNeeded", itemNeeded);
        put("itemActual", itemActual);
        put("BreakdownByDeptList", breakdownByDeptList);
    }

    public static List<RetrievalFormItem> getRetrievalFormItems() {

        List<RetrievalFormItem> list = new ArrayList<RetrievalFormItem>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL);
        try {
            for (int i = 0; i < a.length(); i++) {
                JSONObject b = a.getJSONObject(i);

                  /* Format Json date */

                list.add(new RetrievalFormItem( b.getString("ItemID"),b.getString("ItemDescription"),
                        b.getString("ItemLocation"), b.getInt("ItemNeeded"), b.getInt("ItemActual"),(List<BreakdownByDepartment>)b.get("BreakList")));
            }
        } catch (Exception e) {
            Log.e("Requisition", "JSONArray error");
        }
        return (list);
    }
}

