package com.example.jack.team09adandroidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.example.jack.team09adandroidapp.RetrievalFormItem;


import java.util.List;
import java.util.Map;

public class Main2Activity extends android.app.ListActivity {

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main2);
        final AsyncTask<String, Void, List<RetrievalFormItem>> execute =new AsyncTask<String, Void, List<RetrievalFormItem>>() {
            @Override
            protected List<RetrievalFormItem> doInBackground(String... params) {
                return RetrievalFormItem.getRetrievalFormItems();
            }

            @Override
            protected void onPostExecute(List<RetrievalFormItem> result) {

                SimpleAdapter adapter;
                adapter = new SimpleAdapter(getApplicationContext(), result,
                        R.layout.retrieval_form_item,
                        new String[]{"ItemID", "ItemDescription"},
                        new int[]{R.id.itemID, R.id.itemDescription});
                setListAdapter(adapter);
            }
        }.execute();
    }
}
