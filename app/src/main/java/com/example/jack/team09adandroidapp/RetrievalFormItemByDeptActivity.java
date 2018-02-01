package com.example.jack.team09adandroidapp;

/**
 * Created by CHEN ZIQING on 2018/1/28.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class RetrievalFormItemByDeptActivity extends Activity {


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_retrievalfrombydept);
        final ListView lv = (ListView) findViewById(R.id.listview_retrievalFormItemByDept);

        Intent it = getIntent();
        List<BreakdownByDepartment> list = new ArrayList<>();
        list = (List<BreakdownByDepartment>) it.getSerializableExtra("breakdownByDepartmentList");

        BreakdownByDepartmentAdapter adapter = new BreakdownByDepartmentAdapter(this, list);
        lv.setAdapter(adapter);

    }

    public void backClickHandler(View v) {

        Intent it = new Intent(getApplicationContext(),RetrievalFormItemActivity.class);
        startActivity(it);
    }

}
