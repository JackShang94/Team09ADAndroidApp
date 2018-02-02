package com.example.jack.team09adandroidapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuanxushu on 2018/1/28.
 */

public class SubmitAdjustmentVoucherActivity extends Activity {

    Context mContext;
    ListDataSave dataSave;
    private ArrayList<AdjustmentVoucherCartItem> adjVCart;
    private AdjustmentVoucherCartAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_adjv);
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);

        //get the cart list
        mContext = getApplicationContext();
        dataSave = new ListDataSave(mContext, "adjVCart");
        adjVCart = (ArrayList) dataSave.getDataList("adjVCart");

        // Make sure to clear the selections
        if (adjVCart != null) {
            for (int i = 0; i < adjVCart.size(); i++) {
                adjVCart.get(i).selected = false;
            }
        }

        // Create the list
        final ListView listViewCart = (ListView) findViewById(R.id.listview_adjvList);
        myAdapter = new AdjustmentVoucherCartAdapter(adjVCart, this, true);
        listViewCart.setAdapter(myAdapter);

        listViewCart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                AdjustmentVoucherCartItem selectedItem = adjVCart.get(position);
                if (selectedItem.selected == true) {
                    selectedItem.selected = false;
                } else {
                    selectedItem.selected = true;
                }
                myAdapter.notifyDataSetInvalidated();
            }
        });

        //remove selected items
        Button removeButton = (Button) findViewById(R.id.Button_Remove);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Loop through and remove all the products that are selected
                // Loop backwards so that the remove works correctly
                for (int i = adjVCart.size() - 1; i >= 0; i--) {

                    if (adjVCart.get(i).selected) {
                        adjVCart.remove(i);
                    }
                }
                dataSave.setDataList("adjVCart", adjVCart);
                myAdapter.notifyDataSetChanged();
                listViewCart.setAdapter(myAdapter);
                ArrayList<AdjustmentVoucherCartItem> testList = adjVCart;
            }
        });
        //submit this adjV and clear cart
        Button button_submit = (Button) findViewById(R.id.Button_submit);
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //submit the list to db
                AdjustmentVoucherCartItem adjV = new AdjustmentVoucherCartItem();
                int result =adjV.addNewAdjV(adjVCart);
                if(result==0){
                    Toast.makeText(SubmitAdjustmentVoucherActivity.this,
                            "Submit Failed!", Toast.LENGTH_SHORT)
                            .show();
                }else if(result==1){
                    dataSave.clearAll("adjVCart");
                    Toast.makeText(SubmitAdjustmentVoucherActivity.this,
                            "Submit Successfully!", Toast.LENGTH_SHORT)
                            .show();
                }
                //clear the list

            }
        });

        //clear all items from the adjlist cart
        Button button_clear = (Button) findViewById(R.id.Button_clear);
        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dataSave.clearAll("adjVCart");
                Toast.makeText(SubmitAdjustmentVoucherActivity.this,
                        "Clear Successfully!", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_submit_adjv);
//        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
//        Button button_submit = (Button) findViewById(R.id.Button_submit);
//        Button button_remove = (Button) findViewById(R.id.Button_Remove);
//        Button button_clear = (Button) findViewById(R.id.Button_clear);
//
//        //show adj list info
//        final ListView list = (ListView) findViewById(R.id.listview_adjvList);
//        mContext = getApplicationContext();
//        dataSave = new ListDataSave(mContext, "adjVCart");
//        adjVCart = (ArrayList) dataSave.getDataList("adjVCart");
//        String s = adjVCart.get(0).get("ItemID");
//
//        new AsyncTask<Void, Void, List<AdjustmentVoucherCartItem>>() {
//            @Override
//            protected List<AdjustmentVoucherCartItem> doInBackground(Void... params) {
//                return adjVCart;
//            }
//
//            @Override
//            protected void onPostExecute(List<AdjustmentVoucherCartItem> result) {
//
//                SimpleAdapter adapter = new SimpleAdapter(SubmitAdjustmentVoucherActivity.this,
//                        result, R.layout.simple_adjvlist_item,
//                        new String[]{"ItemID", "Description", "AdjustedQty", "Record"},
//                        new int[]{R.id.textView_itemCode, R.id.textView_itemDescription,
//                                R.id.textView_adjQty, R.id.textView_adjRecord});
//                list.setAdapter(adapter);
//
////                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////                    @Override
////                    public void onItemClick(AdapterView<?> av, View v, int position, long id) {
////                        Item item = (Item) av.getAdapter().getItem(position);
////                        Intent intent = new Intent(SubmitAdjustmentVoucherActivity.this, AddAdjustmentVoucherActivity.class);
////                        intent.putExtra("ItemID", item.get("ItemID"));
////                        startActivity(intent);
////
////                    }
////                });
//            }
//        }.execute();
//
//        //remove item
//        button_remove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//        //submit this adjV and clear cart
//        button_submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//        //clear all items from the adjlist cart
//        button_clear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            dataSave.clearAll("adjVCart");
//                Toast.makeText(SubmitAdjustmentVoucherActivity.this ,
//                        "Clear Successfully!" , Toast.LENGTH_SHORT)
//                        .show();
//            }
//        });
//    }
}
