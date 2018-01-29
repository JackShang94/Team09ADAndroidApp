package com.example.jack.team09adandroidapp;

/**
 * Created by CHEN ZIQING on 2018/1/28.
 */

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

public class BreakdownByDepartment extends HashMap {

    public BreakdownByDepartment(String deptID,int needed,int actual ) {
        put("DeptID", deptID);
        put("Needed", needed);
        put("Actual", actual);
    }



}
