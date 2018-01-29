package com.example.jack.team09adandroidapp;

/**
 * Created by CHEN ZIQING on 2018/1/28.
 */
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import android.widget.ArrayAdapter;


public class RetrievalFormItemActivity extends android.app.ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrievalform);

//        new AsyncTask<String, Void, List<RetrievalFormItem>>() {
//            @Override
//            protected List<RetrievalFormItem> doInBackground(String... params) {
//                return RetrievalFormItem.getRetrievalFormItems();
//            }
//
//            @Override
//            protected void onPostExecute(List<RetrievalFormItem> result) {
//
//                SimpleAdapter adapter =
//                        new SimpleAdapter(RetrievalFormItem.this, result,
//                                R.layout.retrieval_form_item,
//                                new String[]{"ItemID", "ItemDescription"},
//                                new int[]{R.id.itemID, R.id.itemDescription});
//                setListAdapter(adapter);
//            }
//        }.execute();
//
//        @Override
//        protected void onListItemClick(ListView l, View v,
//        int position, long id) {
//            Book  b= (Book) getListAdapter().getItem(position);
//            Intent intent = new Intent(this, DetailsActivity.class);
//            intent.putExtra("id", b.get("id"));
//            startActivity(intent);
//        }

    }
}

