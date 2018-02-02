package com.example.jack.team09adandroidapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by CHEN ZIQING on 2018/1/28.
 */

public class AddAdjustmentVoucherActivity extends Activity implements View.OnClickListener {
    final static int[] view = {R.id.textView_id, R.id.textView_category, R.id.textView_description};
    final static String[] key = {"ItemID", "CategoryID", "Description"};

    Context mContext;
    ListDataSave dataSave;
    private ArrayList<AdjustmentVoucherCartItem> adjVCart;

    TextView textView_id;
    TextView textView_description;
    EditText editText_qty;
    EditText editText_rmk;
    Button button;
    Button button_cart;

    //show item info
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_adjv_item);
        initView();
    }
    void initView() {
        textView_id = (TextView) findViewById(R.id.textView_id);
        textView_description=(TextView)findViewById(R.id.textView_description);
        editText_qty = (EditText) findViewById(R.id.editText_qty);
        editText_rmk = (EditText) findViewById(R.id.editText_remark);
        button = (Button) findViewById(R.id.button_add);

        //show Item Info
        String itemID = getIntent().getExtras().getString("ItemID");
        new AsyncTask<String, Void, Item>() {
            @Override
            protected Item doInBackground(String... params) {
                String s = params[0];
                return Item.getItemByID(params[0]);
            }

            @Override
            protected void onPostExecute(Item result) {
                for (int i = 0; i < view.length; i++) {
                    TextView t = (TextView) findViewById(view[i]);
                    t.setText(result.get(key[i]));
                }
            }
        }.execute(itemID);

        mContext = getApplicationContext();
        dataSave = new ListDataSave(mContext, "adjVCart");
        adjVCart = (ArrayList) dataSave.getDataList("adjVCart");
        button.setOnClickListener(this);
    }
    //add this item to adjList
    @Override
    public void onClick(View v) {
        AdjustmentVoucherCartItem cartItem =new AdjustmentVoucherCartItem();

        cartItem.setItemID(textView_id.getText().toString());
        cartItem.setDescription(textView_description.getText().toString());
        cartItem.setQtyAdjusted(Integer.parseInt(editText_qty.getText().toString()));
        cartItem.setRecord(editText_rmk.getText().toString());
        cartItem.selected=false;
        adjVCart.add(cartItem);
        dataSave.setDataList("adjVCart", adjVCart);

        //success msg
        Toast.makeText(AddAdjustmentVoucherActivity.this ,
                "Add to adjustment list successfully!" , Toast.LENGTH_LONG)
                .show();

        Intent intent = new Intent(AddAdjustmentVoucherActivity.this, SubmitAdjustmentVoucherActivity.class);
        startActivity(intent);
    }

}


