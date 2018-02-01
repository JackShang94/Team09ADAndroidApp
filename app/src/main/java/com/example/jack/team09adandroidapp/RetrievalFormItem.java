package com.example.jack.team09adandroidapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
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

public class RetrievalFormItem {

    final static String baseURL = "http://172.17.251.239/AD/AndroidServices/RetrievalFormItemService.svc/RetrievalFormItem/get";
    //final static String baseURL = "http://192.168.1.95/AD/AndroidServices/RetrievalFormItemService.svc/RetrievalFormItem/get";

    final static String updateURL = "http://172.17.251.239/AD/AndroidServices/RetrievalFormItemService.svc/RetrievalFormItem/post/update";

    private String itemID;
    private String itemDescription;
    private String itemLocation;
    private int itemNeeded;
    private int itemActual;
    private List<BreakdownByDepartment> breakdownByDepartmentList;


    public RetrievalFormItem() {
    }

    public RetrievalFormItem(String itemID, String itemDescription, String itemLocation, int itemNeeded, int itemActual, List<BreakdownByDepartment> breakdownByDepartmentList) {
        this.itemID = itemID;
        this.itemDescription = itemDescription;
        this.itemLocation = itemLocation;
        this.itemNeeded = itemNeeded;
        this.itemActual = itemActual;
        this.breakdownByDepartmentList = breakdownByDepartmentList;
    }

    public static String getBaseURL() {
        return baseURL;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemLocation() {
        return itemLocation;
    }

    public void setItemLocation(String itemLocation) {
        this.itemLocation = itemLocation;
    }

    public int getItemNeeded() {
        return itemNeeded;
    }

    public void setItemNeeded(int itemNeeded) {
        this.itemNeeded = itemNeeded;
    }

    public int getItemActual() {
        return itemActual;
    }

    public void setItemActual(int itemActual) {
        this.itemActual = itemActual;
    }

    public List<BreakdownByDepartment> getBreakdownByDepartmentList() {
        return breakdownByDepartmentList;
    }

    public void setBreakdownByDepartmentList(List<BreakdownByDepartment> breakdownByDepartmentList) {
        this.breakdownByDepartmentList = breakdownByDepartmentList;
    }




    public static List<RetrievalFormItem> getRetrievalFormItems() {

        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL);
        List<RetrievalFormItem> list = new ArrayList<RetrievalFormItem>();

        try {
            for (int i = 0; i < a.length(); i++) {
                JSONObject b = a.getJSONObject(i);

                  /* Format Json date */
                RetrievalFormItem retrievalFormItem = new RetrievalFormItem(b.getString("itemID"),b.getString("itemDescription"),b.getString("itemLocation"),b.getInt("itemNeeded"),b.getInt("itemActual"), (List<BreakdownByDepartment>)BreakdownByDepartment.getBreakdownByDepartment((JSONArray) b.get("breakdownByDepartmentListData")));

                list.add(retrievalFormItem);
            }
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
        return (list);
    }

}

