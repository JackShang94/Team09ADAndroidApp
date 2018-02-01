package com.example.jack.team09adandroidapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DisbursementItem_rep_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rep_disbursement_item);
        final ListView disItemlv = findViewById(R.id.disitemlistview_rep);

        Intent i = getIntent();
        final String disID = i.getStringExtra("disID");
        loadDisItem loadDisItem = new loadDisItem();
        loadDisItem.execute(disID);
    }

    public class loadDisItem extends AsyncTask<String,Void,List<DisbursementItem>> {

        @Override
        protected List<DisbursementItem> doInBackground(String... strings) {
            DisbursementItem disi = new DisbursementItem();
            List<DisbursementItem> ldisi = new ArrayList<>();
            ldisi = disi.getDisbursementItemByDisID(strings[0]);
            return ldisi;
        }
        @Override
        protected void onPostExecute(List<DisbursementItem> ldisi) {
            ListView disItemlv = findViewById(R.id.disitemlistview_rep);
            MyDisItemAdapter_rep myDisItemAdapter = new MyDisItemAdapter_rep(DisbursementItem_rep_Activity.this, ldisi);
            disItemlv.setAdapter(myDisItemAdapter);
        }
    }


}
