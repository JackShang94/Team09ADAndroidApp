package com.example.jack.team09adandroidapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CHEN ZIQING on 2018/1/28.
 */

public class DisbursementListItemActivity extends AppCompatActivity {
    private List<DisbursementItem> ldisi = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursementitem);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        final ListView disItemlv = findViewById(R.id.disitemlistview);
        final Button submitBtn = findViewById(R.id.submitbutton);
        Intent i = getIntent();
        final String disID = i.getStringExtra("disID");
        DisbursementItem disi = new DisbursementItem();
//        List<DisbursementItem> ldisi = new ArrayList<>();
        ldisi = disi.getDisbursementItemByDisID(disID);
        MyDisItemAdapter myDisItemAdapter = new MyDisItemAdapter(DisbursementListItemActivity.this, ldisi);
        disItemlv.setAdapter(myDisItemAdapter);
//        int k =disItemlv.getCount();
//        if(disItemlv.getAdapter().getCount()!=0){
//            submitBtn.setVisibility(View.GONE);
//        }
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisbursementItem disitem = new DisbursementItem();
                int result =disitem.updateDisbursementItem(ldisi, disID);
/****************************generate QRCode***********************************/
                if(result==1){
                    qrcodepopup(disID);
                }else{
                    Toast.makeText(DisbursementListItemActivity.this,"Update!",Toast.LENGTH_LONG).show();
                }

/******************************************************************************/


//                startActivity(new Intent(DisbursementListItemActivity.this, DisbursementListActivity.class));
                Toast.makeText(DisbursementListItemActivity.this,"update!",Toast.LENGTH_LONG).show();
//                finish();
            }
        });


    }
    public void qrcodepopup(String disID){
        Dialog d = new Dialog(this);
        d.setContentView(R.layout.qrcode_dialog);
        d.setTitle("disbursement");
        ImageView iv = (ImageView) d.findViewById(R.id.qrcode_imageView);
        Bitmap bit =QRCodeUtil.createQRImage(URL.baseURL+"/AndroidServices/DisbursementListService.svc/Disbursement/"+disID+"/confirm",200,200);
        iv.setImageBitmap(bit);
        d.show();
    }
    public class MyDisItemAdapter extends BaseAdapter {
        private Context mContext;
        private List<DisbursementItem> mData;
//        private LayoutInflater inflater;
        public MyDisItemAdapter() {

        }

        public MyDisItemAdapter(Context mContext, List<DisbursementItem> mData) {
            this.mContext = mContext;
//            this.inflater=LayoutInflater.from(mContext);
            this.mData = mData;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
//            MyDisItemAdapter.ViewHolder holder = null;
//            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.disbursementitem, null);
                TextView itemID = convertView.findViewById(R.id.itemIDtextview);
                TextView itemDesc = convertView.findViewById(R.id.itemDesctextview);
                TextView expected = convertView.findViewById(R.id.expected);
                EditText actual = convertView.findViewById(R.id.actual);

                itemID.setText(mData.get(position).getItemID());
                itemDesc.setText(mData.get(position).getItemDesc());
                expected.setText(String.valueOf(mData.get(position).getExpected()));
                actual.setText(String.valueOf(mData.get(position).getActual()));

                actual.addTextChangedListener(new ActualChangeListener(position));
//                holder = new MyDisItemAdapter.ViewHolder();
//                holder.itemID =(TextView) convertView.findViewById(R.id.itemIDtextview);
//                holder.itemDesc =(TextView) convertView.findViewById(R.id.itemDesctextview);
//                holder.expected =(TextView) convertView.findViewById(R.id.expected);
//                holder.actual = (EditText) convertView.findViewById(R.id.actual);
//                holder.actual.setTag(position);

//                convertView.setTag(holder);
//            } else {
//                holder = (MyDisItemAdapter.ViewHolder) convertView.getTag();
//                holder.actual.setTag(position);
//            }

//            holder.itemID.setText(mData.get(position).getItemID());
//            holder.itemDesc.setText(mData.get(position).getItemDesc());
//            holder.expected.setText(String.valueOf(mData.get(position).getExpected()));
//            holder.actual.setText(String.valueOf(mData.get(position).getActual()));
//            holder.actual.addTextChangedListener(new ActualChangeListener(holder));
//
                return convertView;
//            }

//        public class ViewHolder {
//            TextView itemID;
//            TextView itemDesc;
//            TextView expected;
//            EditText actual;
//        }
        }
        private class ActualChangeListener implements TextWatcher{
//            private ViewHolder holder;
                private  int position;
//            public ActualChangeListener(ViewHolder holder){
//                this.holder=holder;
//            }
            public ActualChangeListener(int position){
                this.position=position;
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                isOnTextChanged=true;

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(editable.toString().equals("")){

                }else{
                    String id =mData.get(this.position).getItemID();
                   mData.get(this.position).setActual(Integer.parseInt(editable.toString()));
                }

            }
        }
    }

}
