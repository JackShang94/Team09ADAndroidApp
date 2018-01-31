package com.example.jack.team09adandroidapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by CHEN ZIQING on 2018/1/28.
 */

public class DisbursementListActivity extends AppCompatActivity {
//    class deptBindTasks extends AsyncTask<Void,Void,List<String>>{
//        private Exception exception;
//        protected List<String> doInBackground(Void... Void){
//            try{
//
//            }
//        }
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        final Spinner spinner = findViewById(R.id.spinner);
        final ListView disburseList = findViewById(R.id.listview);
        Department d =new Department();
        List<String> ls = new ArrayList<>();
        final List<Department> ld = d.getAllDept();
        for (Department i:ld) {
            ls.add(i.getDeptName());
        }
        final Disbursement dis = new Disbursement();
        ArrayAdapter deptadapter = new ArrayAdapter(this,R.layout.deptspinner,ls);
        spinner.setAdapter(deptadapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item_string =adapterView.getItemAtPosition(i).toString();
                String deptID =null;
                for (Department d:ld) {
                    if(d.getDeptName()==item_string){
                        deptID=d.getDeptID();
                        break;
                    }
                }

                List<Disbursement> ldis = dis.getDisbursementList(deptID);
                MyDisAdapter myAdapter = new MyDisAdapter(DisbursementListActivity.this,ldis);
                disburseList.setAdapter(myAdapter);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        disburseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView c = view.findViewById(R.id.disIDtextView);
                String disID = c.getText().toString();

                Intent intent = new Intent(DisbursementListActivity.this,DisbursementListItemActivity.class);
                intent.putExtra("disID",disID);
                startActivity(intent);
            }
        });
//
    }



}
