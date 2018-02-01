package com.example.jack.team09adandroidapp;

/**
 * Created by CHEN ZIQING on 2018/1/28.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import android.widget.Toast;


public class RetrievalFormItemActivity extends AppCompatActivity {
    List<RetrievalFormItem> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_retrievalform);
        final ListView lv = (ListView) findViewById(R.id.listview_retrievalFormItem);

        list = RetrievalFormItem.getRetrievalFormItems();

        RetrievalFormItemAdapter adapter = new RetrievalFormItemAdapter(this, list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                onMyItemClick(adapterView, view, i, l);
            }
        });


    }

    public void onMyItemClick(AdapterView<?> adapterView, View v, int i, long l) {
        RetrievalFormItem retrievalFormItem = (RetrievalFormItem) adapterView.getAdapter().getItem(i);
        String itemID = retrievalFormItem.getItemID();
        Intent it = new Intent(RetrievalFormItemActivity.this,RetrievalFormItemByDeptActivity.class);

        List<BreakdownByDepartment> list = new ArrayList<BreakdownByDepartment>();
        list = retrievalFormItem.getBreakdownByDepartmentList();

        it.putExtra("breakdownByDepartmentList", (Serializable)list);
        startActivity(it);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_adjustment) {

            return true;
        }
        if(id ==R.id.action_toDisbursement){
            if(this.getClass().equals(DisbursementListActivity.class)){
                return false;
            }else{
                Intent i = new Intent(this,DisbursementListActivity.class);
                startActivity(i);
            }
        }
        if(id==R.id.action_toRetrieval){
            if(this.getClass().equals(RetrievalFormItemActivity.class)){
                return false;
            }else{
                Intent i = new Intent(this,RetrievalFormItemActivity.class);
                startActivity(i);
            }
            return true;
        }
        if(id==R.id.action_logout){
            AccountSession as = new AccountSession(this);
            as.logoutUser();

            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}


