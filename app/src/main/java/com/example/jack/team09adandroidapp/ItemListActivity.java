package com.example.jack.team09adandroidapp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuanxushu on 2018/1/28.
 */

public class ItemListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_itemlist);
//        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
        /***********************check login******************************/
        AccountSession as = new AccountSession(this);
        as.checkLogin(this);
        /*************************************************************/
        Button button_search = (Button) findViewById(R.id.button_search);
        FloatingActionButton floatingActionButton=(FloatingActionButton)findViewById(R.id.fltbtn_add);

        //show item catelog when this activity starts
        final ListView list = (ListView) findViewById(R.id.listview_itemlist);
        new AsyncTask<Void, Void, List<Item>>() {
            @Override
            protected List<Item> doInBackground(Void... params) {
                return Item.getItemList();
            }

            @Override
            protected void onPostExecute(List<Item> result) {

//                if (result.isEmpty()) {
//                    TextView txtMsg = (TextView) findViewById(R.id.txtMsg);
//                    txtMsg.setVisibility(View.VISIBLE);
//                }
                SimpleAdapter adapter = new SimpleAdapter(ItemListActivity.this,
                        result, R.layout.simple_item_listview_item,
                        new String[]{"ItemID", "CategoryID", "Description"},
                        new int[]{R.id.textView_iID, R.id.textView_iCAT, R.id.textView_iDescription});
                list.setAdapter(adapter);

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> av, View v, int position, long id) {
                        Item item = (Item) av.getAdapter().getItem(position);
                        Intent intent = new Intent(ItemListActivity.this, AddAdjustmentVoucherActivity.class);
                        intent.putExtra("ItemID", item.get("ItemID"));
                        startActivity(intent);

                    }
                });
            }
        }.execute();

        //show itemlist based on search result
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ListView listView = (ListView) findViewById(R.id.listview_itemlist);
                new AsyncTask<Void, Void, List<Item>>() {
                    @Override
                    protected List<Item> doInBackground(Void... params) {
                        EditText editText = (EditText) findViewById(R.id.edtiText_search);
                        return Item.getItemListBySearch(editText.getText().toString());
                    }

                    @Override
                    protected void onPostExecute(List<Item> result) {
                        if(result.size()==0){
                            result = Item.getItemList();
                        }

                        SimpleAdapter adapter = new SimpleAdapter(ItemListActivity.this,
                                result, R.layout.simple_item_listview_item,
                                new String[]{"ItemID", "CategoryID", "Description"},
                                new int[]{R.id.textView_iID, R.id.textView_iCAT, R.id.textView_iDescription});
                        list.setAdapter(adapter);

                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> av, View v, int position, long id) {
                                Item item = (Item) av.getAdapter().getItem(position);
                                Intent intent = new Intent(ItemListActivity.this, AddAdjustmentVoucherActivity.class);
                                intent.putExtra("ItemID", item.get("ItemID"));
                                startActivity(intent);
                            }
                        });
                    }
                }.execute();
            }
        });

        //view adjcart
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemListActivity.this, SubmitAdjustmentVoucherActivity.class);
                startActivity(intent);
            }
        });
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
            if(this.getClass().equals(ItemListActivity.class)){
                return false;
            }else{
                Intent i =new Intent(this,ItemListActivity.class);
                startActivity(i);
            }

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


