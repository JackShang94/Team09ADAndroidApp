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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class DisbursementList_rep_Activity extends AppCompatActivity {
    private View pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rep_disbursement);
        /**********Here for checking login state***************/
        AccountSession as =new AccountSession(this);
        as.checkLogin(this);
        /*****************************************************/
        final ListView disburseList = findViewById(R.id.rep_listview);

        pb = findViewById(R.id.dis_progress);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        loadDis_rep loadAsync = new loadDis_rep(this,as.getUserDetails().get("loginID"));
        loadAsync.execute((Void) null);
//        new AsyncTask<String,Void,List<Disbursement>>() {
//            @Override
//            protected List<Disbursement> doInBackground(String... params) {
//                Disbursement dis = new Disbursement();
//                List<Disbursement> ldis = dis.getDisbursementListByrepID(params[0]);
//                return ldis;
//            }
//            @Override
//            protected void onPostExecute(List<Disbursement> ldis) {
//                ListView disburseList = findViewById(R.id.rep_listview);
//                MyDisAdapter_rep myAdapter = new MyDisAdapter_rep(DisbursementList_rep_Activity.this, ldis);
//                disburseList.setAdapter(myAdapter);
//            }
//        }.execute(as.getUserDetails().get("loginID"));

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
