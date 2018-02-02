package com.example.jack.team09adandroidapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by doujohner on 28/1/2018.
 */

public class Disbursement {
    public int getDisID() {
        return disID;
    }

    public void setDisID(int disID) {
        this.disID = disID;
    }

    public String getDeptID() {
        return deptID;
    }

    public void setDeptID(String deptID) {
        this.deptID = deptID;
    }

    public String getStoreStaffID() {
        return storeStaffID;
    }

    public void setStoreStaffID(String storeStaffID) {
        this.storeStaffID = storeStaffID;
    }

    public String getDisDate()  {//generate formatted string

        return disDate;
    }

    public void setDisDate(String disDate) {
        this.disDate = disDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public Disbursement(int disID, String deptID, String storeStaffID, String disDate, String status) {

        this.disID = disID;
        this.deptID = deptID;
        this.storeStaffID = storeStaffID;
        this.disDate = disDate;
        this.status = status;
    }
    public Disbursement(){

    }
    private int disID;
    private String deptID;
    private String storeStaffID;
    private String disDate;
    private String status;
    final String baseUrl = URL.baseURL+"/AndroidServices/DisbursementListService.svc";
    public List<Disbursement> getDisbursementList(String deptID){
        String url = "/Disbursement?deptID="+deptID;
        List<Disbursement> list = new ArrayList<Disbursement>();
        JSONArray a = JSONParser.getJSONArrayFromUrl((baseUrl+url));
        try{

            for(int i=0;i<a.length();i++){
                JSONObject b = a.getJSONObject(i);
                Disbursement d =new Disbursement( b.getInt("DisID"),b.getString("DeptID"),b.getString("StoreStaffID"),b.getString("DisDate"),b.getString("Status"));
                list.add(d);
            }
        } catch (JSONException e) {
            Log.e("object", e.toString());
            e.printStackTrace();
        }
        return list;

    }
    public List<Disbursement> getDisbursementListByrepID(String repID){
        String url = "/DisbursementByrepID?repID="+repID;
        List<Disbursement> list = new ArrayList<Disbursement>();
        JSONArray a = JSONParser.getJSONArrayFromUrl((baseUrl+url));
        try{

            for(int i=0;i<a.length();i++){
                JSONObject b = a.getJSONObject(i);
                Disbursement d =new Disbursement( b.getInt("DisID"),b.getString("DeptID"),b.getString("StoreStaffID"),b.getString("DisDate"),b.getString("Status"));
                list.add(d);
            }
        } catch (JSONException e) {
            Log.e("object", e.toString());
            e.printStackTrace();
        }
        return list;
    }

    public String qrcode_rep(String loginID,String url){

//        Calendar today = Calendar.getInstance();
//        today.set(Calendar.HOUR_OF_DAY,0);
//        Date d = today.getTime();
        String d="testdate";
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("loginID",loginID);
            jsonObject.put("scan_date",d);
        }catch (Exception e){
            Log.e("jsonobject transfer",e.toString());
        }
        String result = JSONParser.postStream(url,jsonObject.toString());
        return result;

    }

}
