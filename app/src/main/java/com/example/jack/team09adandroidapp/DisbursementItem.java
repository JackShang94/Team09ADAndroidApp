package com.example.jack.team09adandroidapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by doujohner on 30/1/2018.
 */

public class DisbursementItem {
    public DisbursementItem(){

    }

    public DisbursementItem(String itemID, String itemDesc, int expected, int actual) {
        this.itemID = itemID;
        this.itemDesc = itemDesc;
        this.expected = expected;
        this.actual = actual;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public int getExpected() {
        return expected;
    }

    public void setExpected(int expected) {
        this.expected = expected;
    }

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    private String itemID;
    private String itemDesc;
    private int expected;
    private int actual;
    final String baseUrl =URL.baseURL+"/AndroidServices/DisbursementListService.svc";
    public List<DisbursementItem> getDisbursementItemByDisID(String disID){
        String url = "/Disbursement/"+disID;
        List<DisbursementItem> ldisi = new ArrayList<>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseUrl+url);
        try{
            for(int i=0;i<a.length();i++){
                JSONObject b = a.getJSONObject(i);
                DisbursementItem disi = new DisbursementItem(b.getString("itemID"),b.getString("itemDescription"),b.getInt("expected"),b.getInt("actual"));
                ldisi.add(disi);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ldisi;
    }

    public  int updateDisbursementItem(List<DisbursementItem> ldisItem,String disID){
        String url = "/Disbursement/"+disID+"/update";
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try{

            for(DisbursementItem disItem:ldisItem){
                JSONObject disItemJSON= new JSONObject();
                disItemJSON.put("itemID",disItem.getItemID());
                disItemJSON.put("itemDescription",disItem.getItemDesc());
                disItemJSON.put("expected",disItem.getExpected());
                disItemJSON.put("actual",disItem.getActual());
                jsonArray.put(disItemJSON);
            }
            jsonObject.put("discartList",jsonArray);
        }catch (Exception e){
            Log.e("jsonWrong",e.toString());
        }

        String result =JSONParser.postStream(baseUrl+url,jsonObject.toString());
        int success=0;
        try{
            success = Integer.valueOf(result);
        }catch (Exception e){
            Log.e("disitemInteger",e.toString());
        }
        return success;

    }

}
