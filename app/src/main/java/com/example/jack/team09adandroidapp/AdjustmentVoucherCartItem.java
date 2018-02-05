package com.example.jack.team09adandroidapp;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.security.spec.ECField;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Yuanxushu on 29/1/2018.
 */

public class AdjustmentVoucherCartItem implements Serializable {

    final static String baseURL = URL.baseURL+"/AndroidServices/AdjustmentCartItemService.svc/";
    private String itemID;
    private String description;
    private int qtyAdjusted;
    private String record;

    public boolean selected;

    public AdjustmentVoucherCartItem(){}

    public String getItemID() {
        return itemID;
    }

    public String getDescription() {
        return description;
    }

    public int getQtyAdjusted() {
        return qtyAdjusted;
    }

    public String getRecord() {
        return record;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQtyAdjusted(int qtyAdjusted) {
        this.qtyAdjusted = qtyAdjusted;
    }

    public void setRecord(String record) {
        this.record = record;
    }


    public int addNewAdjV(List<AdjustmentVoucherCartItem> adjVCart,String staffID){
        JSONArray jsonArray=new JSONArray();
        for (AdjustmentVoucherCartItem cartItem :adjVCart)
        {
            try {
                JSONObject jcartItem = new JSONObject();
                jcartItem.put("ItemID", cartItem.getItemID());
                jcartItem.put("QtyAdjusted", cartItem.getQtyAdjusted());
                jcartItem.put("Record", cartItem.getRecord());
                jsonArray.put(jcartItem);

            } catch (Exception e) {
                Log.e("notice",e.toString());
            }
        }

        JSONObject jsonString = new JSONObject();
        try{
            jsonString.put("cartList",jsonArray);
        }catch (Exception e){
            Log.e("cartlist",e.toString());
        }
        String result= JSONParser.postStream(baseURL+"ADJV/post/addAdj/"+staffID,jsonString.toString());
        int result_int =0;
        try{
            result_int = Integer.parseInt(result);
        }catch (Exception e){
            Log.e("parseInt result",e.toString());
        }
        return result_int;
    }
}
