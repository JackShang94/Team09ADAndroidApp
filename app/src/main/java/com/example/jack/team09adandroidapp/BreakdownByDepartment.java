package com.example.jack.team09adandroidapp;

/**
 * Created by CHEN ZIQING on 2018/1/28.
 */

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class BreakdownByDepartment implements Serializable {

    private String deptID;
    private int needed;
    private int actual;



    final static String baseURL =URL.baseURL+"/AndroidServices/RetrievalFormItemService.svc/RetrievalFormItem/get";
    //final static String baseURL = "http://192.168.1.95/AD/AndroidServices/RetrievalFormItemService.svc/RetrievalFormItem/get";


    public BreakdownByDepartment(){
    }

    public BreakdownByDepartment(String deptID, int needed, int actual) {
        this.deptID = deptID;
        this.needed = needed;
        this.actual = actual;
    }

    public String getDeptID() {
        return deptID;
    }

    public void setDeptID(String deptID) {
        this.deptID = deptID;
    }

    public int getNeeded() {
        return needed;
    }

    public void setNeeded(int needed) {
        this.needed = needed;
    }

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }


    //JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL);

    public static List<BreakdownByDepartment> getBreakdownByDepartment(JSONArray a) {

        List<BreakdownByDepartment> list = new ArrayList<BreakdownByDepartment>();

        try {
            for (int i = 0; i < a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                  /* Format Json date */
                BreakdownByDepartment breakdownByDepartment = new BreakdownByDepartment(b.getString("deptID"),b.getInt("needed"),b.getInt("actual"));

                list.add(breakdownByDepartment);
            }
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
        return (list);
    }



}
