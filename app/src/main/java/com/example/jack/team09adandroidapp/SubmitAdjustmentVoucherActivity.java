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
    private AccountSession as;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_adjv);
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);

        AccountSession as = new AccountSession(this);
        this.as=as;
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
        final Button removeButton = (Button) findViewById(R.id.Button_Remove);
        final Button button_submit = (Button) findViewById(R.id.Button_submit);
        final Button button_clear = (Button) findViewById(R.id.Button_clear);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Loop through and remove all the products that are selected
                // Loop backwards so that the remove works correctly
                int initialSize = adjVCart.size();
                for(int i =initialSize-1;i>=0;i--){

                    if(adjVCart.get(i).selected){
                        adjVCart.remove(i);
                    }
                }
                if(initialSize==adjVCart.size()){//havent select the item
                    Toast.makeText(SubmitAdjustmentVoucherActivity.this,
                            "Please select at least an item!", Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                if(adjVCart.size()==0){
                    removeButton.setVisibility(View.GONE);
                    button_submit.setVisibility(View.GONE);

                }else{

                    button_submit.setVisibility(View.VISIBLE);

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

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //submit the list to db
                AdjustmentVoucherCartItem adjV = new AdjustmentVoucherCartItem();
                AccountSession as = new AccountSession(SubmitAdjustmentVoucherActivity.this);
                /**********************need staffID****************************/
                int result = adjV.addNewAdjV(adjVCart,as.getUserDetails().get("loginID"));
                /************************************************************/
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
                    finish();
                }
            }
        });

        //clear all items from the adjlist cart

        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAdapter.deleteAll();
                dataSave.clearAll("adjVCart");
                myAdapter.notifyDataSetChanged();
                listViewCart.invalidate();
                button_submit.setVisibility(View.GONE);
                removeButton.setVisibility(View.GONE);

                Toast.makeText(SubmitAdjustmentVoucherActivity.this,
                        "Clear Successfully!", Toast.LENGTH_SHORT).show();
            }
        });
        /*************************Nothing in cart************************/
        if(adjVCart.size()==0){

            removeButton.setVisibility(View.GONE);
            button_submit.setVisibility(View.GONE);
        }else{

            removeButton.setVisibility(View.VISIBLE);
            button_submit.setVisibility(View.VISIBLE);
        }
        /****************************************************************/
        Button button_add = (Button) findViewById(R.id.Button_ADD);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(SubmitAdjustmentVoucherActivity.this, ItemListActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
