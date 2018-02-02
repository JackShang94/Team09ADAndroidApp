package com.example.jack.team09adandroidapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Yuanxushu on 29/1/2018.
 */

public class AdjustmentVoucherItem extends HashMap<String, String> {
    final static String baseURL = "http://172.17.251.72:80/logicU/AndroidServices/AdjustmentVoucherService.svc/";

    public AdjustmentVoucherItem(String adjVItemID, String storeStaffID, String authorisedBy, String adjDate, String status) {
        put("AdjVID", adjVItemID);
        put("StoreStaffID", storeStaffID);
        put("AuthorisedBy", authorisedBy);
        put("AdjDate", adjDate);
        put("Status", status);
    }
//    int adjVItemID;
//    int adjVID;
//    int qty;
//    string itemID;
//    string record;

//    public void addNewAdjV(List<AdjustmentVoucherCartItem> adjVCart){
//
//        String result = JSONParser.postStream(baseURL+"/Update", adjVCart.toString());
//    }
}

