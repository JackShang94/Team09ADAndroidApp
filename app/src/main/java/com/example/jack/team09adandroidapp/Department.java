package com.example.jack.team09adandroidapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by doujohner on 28/1/2018.
 */

public class Department {
    public Department(String deptID, String deptName, String collectionPoint) {
        this.deptID = deptID;
        this.deptName = deptName;
        this.collectionPoint = collectionPoint;
    }
    public Department(){

    }


    public String getDeptID() {
        return deptID;
    }

    public void setDeptID(String deptID) {
        this.deptID = deptID;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCollectionPoint() {
        return collectionPoint;
    }

    public void setCollectionPoint(String collectionPoint) {
        this.collectionPoint = collectionPoint;
    }
    private String deptID;
    private String deptName;
    private String collectionPoint;




    public  List<Department> getAllDept(){

        final String baseUrl = "http://172.17.251.237/Team09AD/AndroidServices/DisbursementListService.svc/alldept";
        List<Department> deptList = new ArrayList<>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseUrl);
        try{
            for(int i =0;i<a.length();i++){
                JSONObject b = a.getJSONObject(i);
                String deptID = b.getString("DeptID");
                String deptName = b.getString("DeptName");
                String collectionp = b.getString("CollectionPointID");
                Department d = new Department(deptID,deptName,collectionp);
                deptList.add(d);
                Log.i("name",deptName);
            }
        }catch (Exception e){
            Log.e("Department","JSONArray Error");
        }
        return deptList;

    }
}
