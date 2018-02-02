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

        //                if (result.isEmpty()) {
//                    TextView txtMsg = (TextView) findViewById(R.id.txtMsg);
//                    txtMsg.setVisibility(View.VISIBLE);
//                }

        listViewCart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AdjustmentVoucherCartItem selectedItem = adjVCart.get(position);
                if (selectedItem.selected == true) {
                    selectedItem.selected = false;
                } else {
                    selectedItem.selected = true;
                }
                myAdapter.notifyDataSetInvalidated();
                myAdapter.notifyDataSetChanged();
            }
        });

        //remove selected items
        Button removeButton = (Button) findViewById(R.id.Button_Remove);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Loop through and remove all the products that are selected
                // Loop backwards so that the remove works correctly
                for(int i =adjVCart.size()-1;i>=0;i--){

                    if(adjVCart.get(i).selected){
                        adjVCart.remove(i);
                    }
                }
//                for (AdjustmentVoucherCartItem cartItem : adjVCart) {
//                    if (cartItem.selected) {
//                        adjVCart.remove(cartItem);
//
//                    }
//                }
                dataSave.setDataList("adjVCart", adjVCart);
                myAdapter.notifyDataSetChanged();
                listViewCart.invalidate();
            }
        });


        //submit this adjV and clear cart
        Button button_submit = (Button) findViewById(R.id.Button_submit);
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //submit the list to db
                AdjustmentVoucherCartItem adjV = new AdjustmentVoucherCartItem();
                int result = adjV.addNewAdjV(adjVCart);
                if (result == 0) {
                    Toast.makeText(SubmitAdjustmentVoucherActivity.this,
                            "Submit Failed!", Toast.LENGTH_SHORT)
                            .show();
                } else if (result == 1) {
                    dataSave.clearAll("adjVCart");
                    Toast.makeText(SubmitAdjustmentVoucherActivity.this,
                            "Submit Successfully!", Toast.LENGTH_LONG)
                            .show();

                    Intent intent = new Intent(SubmitAdjustmentVoucherActivity.this, ItemListActivity.class);
                    startActivity(intent);
                }
            }
        });

        //clear all items from the adjlist cart
        Button button_clear = (Button) findViewById(R.id.Button_clear);
        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAdapter.deleteAll();
                dataSave.clearAll("adjVCart");
                myAdapter.notifyDataSetChanged();
                listViewCart.invalidate();

                Toast.makeText(SubmitAdjustmentVoucherActivity.this,
                        "Clear Successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        Button button_add = (Button) findViewById(R.id.Button_ADD);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(SubmitAdjustmentVoucherActivity.this, ItemListActivity.class);
                startActivity(intent);
            }
        });
    }
}
