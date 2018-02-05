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
        final String deptID = i.getStringExtra("deptID");
        DisbursementItem disi = new DisbursementItem();
        ldisi = disi.getDisbursementItemByDisID(disID);
        final StringBuilder canlogin = new StringBuilder();
        canlogin.append("yes");
        MyDisItemAdapter myDisItemAdapter = new MyDisItemAdapter(DisbursementListItemActivity.this, ldisi,canlogin);
        disItemlv.setAdapter(myDisItemAdapter);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisbursementItem disitem = new DisbursementItem();
                int result =disitem.updateDisbursementItem(ldisi, disID);

                if(canlogin.toString().equals("no")){
                    Toast.makeText(DisbursementListItemActivity.this,"Something wrong!",Toast.LENGTH_LONG).show();
                    return;
                }
/****************************generate QRCode***********************************/
                if(result==1){
                    qrcodepopup(disID,deptID);
                }else{
                    Toast.makeText(DisbursementListItemActivity.this,"Update failed!",Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    public void qrcodepopup(String disID,String deptID){
        Dialog d = new Dialog(this);
        d.setContentView(R.layout.qrcode_dialog);
        d.setTitle("disbursement");
        ImageView iv = (ImageView) d.findViewById(R.id.qrcode_imageView);

        AccountSession as = new AccountSession(this);

        String qrcode_url = URL.baseURL+"/AndroidServices/DisbursementListService.svc/Disbursement/"+disID+"/confirm&"+deptID;
        Bitmap bit =QRCodeUtil.createQRImage(qrcode_url,800,800);
        iv.setImageBitmap(bit);
        d.show();
    }
    public class MyDisItemAdapter extends BaseAdapter {
        private Context mContext;
        private List<DisbursementItem> mData;
        private StringBuilder canlogin;
        public MyDisItemAdapter() {

        }

        public MyDisItemAdapter(Context mContext, List<DisbursementItem> mData,StringBuilder canlogin) {
            this.mContext = mContext;
            this.mData = mData;
            this.canlogin=canlogin;

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
                convertView = LayoutInflater.from(mContext).inflate(R.layout.disbursementitem, null);
                TextView itemID = convertView.findViewById(R.id.itemIDtextview);
                TextView itemDesc = convertView.findViewById(R.id.itemDesctextview);
                TextView expected = convertView.findViewById(R.id.expected);
                EditText actual = convertView.findViewById(R.id.actual);

                itemID.setText(mData.get(position).getItemID());
                itemDesc.setText(mData.get(position).getItemDesc());
                expected.setText(String.valueOf(mData.get(position).getExpected()));
                actual.setText(String.valueOf(mData.get(position).getActual()));

                actual.addTextChangedListener(new ActualChangeListener(position,expected,actual));
//
//
                return convertView;
//
        }
        private class ActualChangeListener implements TextWatcher{

                private  int position;
                private TextView expected;
                private TextView actual;
            public ActualChangeListener(int position,TextView expected,TextView actual){
                this.position=position;
                this.expected=expected;
                this.actual=actual;
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(editable.toString().equals("")){
                    canlogin.setLength(0);
                    canlogin.append("no");
                    this.actual.setError("Can not be empty");

                }else{
                    int expected=mData.get(this.position).getExpected();
                    int actual=Integer.parseInt(editable.toString());
                    if(actual>expected){
                        this.actual.setError("Must be less than expected!");
                        canlogin.setLength(0);
                        canlogin.append("no");
                        return;
                    }else{
                        canlogin.setLength(0);
                        canlogin.append("yes");
                        this.actual.setError(null);
                    }

                    mData.get(this.position).setActual(actual);
                }

            }
        }
    }

}
