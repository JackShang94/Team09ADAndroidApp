package com.example.jack.team09adandroidapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DisbursementList_rep_Activity extends AppCompatActivity {
    private View pb;
    private AccountSession as;

    public class qrcode_async extends AsyncTask<Void,Void,Void>{
        private String loginID;
        private String url;
        private String result;
        public qrcode_async(String loginID,String url){
            this.loginID=loginID;
            this.url=url;
        }
        @Override
        protected Void doInBackground(Void... voids) {

                Disbursement dis = new Disbursement();
                String result_return = dis.qrcode_rep(loginID,url);
                this.result=result_return;
                return null;

        }
        @Override
        protected void onPostExecute(Void res){//something wrong???
            int result_returnInt=0;
            try{
                result_returnInt = Integer.valueOf(result);
            }catch (Exception e){
                Log.e("qrcode int parse",e.toString());
            }

            if(result_returnInt==1){
                Toast.makeText(DisbursementList_rep_Activity.this, "update successfully", Toast.LENGTH_LONG).show();
//                Intent refresh = new Intent(DisbursementList_rep_Activity.this, DisbursementItem_rep_Activity.class);
//                startActivity(refresh);
//                DisbursementList_rep_Activity.this.finish(); //
            }else{
                Toast.makeText(DisbursementList_rep_Activity.this, ":update failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // get result
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "cancel scan", Toast.LENGTH_LONG).show();
                return;
            } else {
                String[] result_info=result.getContents().split("&");
                String url = "";
                String deptID = "";
                if(result_info.length==2){
                    url = result_info[0];
                    deptID = result_info[1];
                }else if(result_info.length==1){
                    url = result_info[0];
                }

                if(as.getUserDetails().get("deptID").equals(deptID)){//check if this is corresponding dept
                    Toast.makeText(this, "You are not authenticated", Toast.LENGTH_LONG).show();
                    return;
                }
                Disbursement dis = new Disbursement();
                qrcode_async qrcodeAsync = new qrcode_async(as.getUserDetails().get("loginID"),url);
                qrcodeAsync.execute((Void) null);
//                String result_return = dis.qrcode_rep(as.getUserDetails().get("loginID"),url);//get data,so is supposed to use asynctask
//                try{
//                    int result_returnInt = Integer.parseInt(result_return);
//                    if(result_returnInt==1){
//                        Toast.makeText(this, "update successfully", Toast.LENGTH_LONG).show();
//                        Intent refresh = new Intent(this, DisbursementItem_rep_Activity.class);
//                        startActivity(refresh);
//                        this.finish(); //
//                    }else{
//                        Toast.makeText(this, ":update failed", Toast.LENGTH_LONG).show();
//                    }
//
//                }catch (Exception e){
//                    Log.e("int parse",e.toString());
//                }


            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rep_disbursement);
        /**********Here for checking login state***************/
        AccountSession as =new AccountSession(this);
        this.as=as;
        as.checkLogin(this);

        /*****************************************************/
        final ListView disburseList = findViewById(R.id.rep_listview);

        pb = findViewById(R.id.dis_progress);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(DisbursementList_rep_Activity.this);

                intentIntegrator.initiateScan();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        loadDis_rep loadAsync = new loadDis_rep(this,as.getUserDetails().get("loginID"));
        loadAsync.execute((Void) null);
//

        disburseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView c = view.findViewById(R.id.disIDtextView_rep);
                String disID = c.getText().toString();

                Intent intent = new Intent(DisbursementList_rep_Activity.this,DisbursementItem_rep_Activity.class);
                intent.putExtra("disID",disID);
                startActivity(intent);
            }
        });
    }



    public class loadDis_rep extends AsyncTask<Void,Void,List<Disbursement>>{
        private Context context;
        private String repID;
        public loadDis_rep(Context context,String repID){
            this.context=context;
            this.repID=repID;
        }
        @Override
        protected List<Disbursement> doInBackground(Void... params) {
            Disbursement dis = new Disbursement();
            List<Disbursement> ldis = dis.getDisbursementListByrepID(this.repID);
            return ldis;
        }
        @Override
        protected void onPostExecute(List<Disbursement> ldis) {
            ListView disburseList = findViewById(R.id.rep_listview);
            MyDisAdapter_rep myAdapter = new MyDisAdapter_rep(DisbursementList_rep_Activity.this, ldis);
            disburseList.setAdapter(myAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_rep, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        if(id==R.id.action_logout){
            AccountSession as = new AccountSession(this);
            as.logoutUser();

            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            pb.setVisibility(show ? View.GONE : View.VISIBLE);
            pb.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    pb.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            pb.setVisibility(show ? View.VISIBLE : View.GONE);
            pb.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    pb.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            pb.setVisibility(show ? View.VISIBLE : View.GONE);
            pb.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
