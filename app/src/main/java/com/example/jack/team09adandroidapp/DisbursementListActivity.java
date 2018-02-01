package com.example.jack.team09adandroidapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by CHEN ZIQING on 2018/1/28.
 */

public class DisbursementListActivity extends AppCompatActivity {

    private View pb;
    private List<Department> ld=new ArrayList();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement);

        /**********Here for checking login state***************/
        AccountSession as =new AccountSession(this);
        as.checkLogin(this);
        /*****************************************************/
        /*****************bind dept to spinner******************/
//      final
        pb = findViewById(R.id.dis_progress);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
//


        final Spinner spinner = findViewById(R.id.spinner);
        final ListView disburseList = findViewById(R.id.listview);




        new AsyncTask<Void,Void,List<String>>() {
            @Override
            protected List<String> doInBackground(Void...params) {
                Department d =new Department();
                ld =d.getAllDept();
                List<String> ls = new ArrayList<>();
                for (Department i:ld) {
                    ls.add(i.getDeptName());
                }
                return ls;
            }
            @Override
            protected void onPostExecute(List<String> ls){
                ArrayAdapter deptadapter = new ArrayAdapter(DisbursementListActivity.this,R.layout.deptspinner,ls);
                spinner.setAdapter(deptadapter);
            }
        }.execute();




        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item_string =adapterView.getItemAtPosition(i).toString();
                String deptID =null;
                for (Department d:ld) {
                    if(d.getDeptName()==item_string){
                        deptID=d.getDeptID();
                        break;
                    }
                }
                loadDis loadAsync = new loadDis(DisbursementListActivity.this,deptID);
                loadAsync.execute((Void) null);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        disburseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView c = view.findViewById(R.id.disIDtextView);
                String disID = c.getText().toString();

                Intent intent = new Intent(DisbursementListActivity.this,DisbursementListItemActivity.class);
                intent.putExtra("disID",disID);
                startActivity(intent);
            }
        });
//
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

            return true;
        }
        if(id ==R.id.action_toDisbursement){
            if(this.getClass()==DisbursementListActivity.class){
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
//    @SuppressWarnings("StatementWithEmptyBody")


    public class loadDis extends AsyncTask<Void,Void,List<Disbursement>>{
        private Context context;
        private String deptID;
        public loadDis(Context context,String deptID){
            this.context=context;
            this.deptID=deptID;
        }

        @Override
        protected List<Disbursement> doInBackground(Void... params) {
            Disbursement dis = new Disbursement();
            List<Disbursement> ldis = dis.getDisbursementList(this.deptID);
            return ldis;

        }
        @Override
        protected void onPostExecute(List<Disbursement> ldis) {
            ListView disburseList = findViewById(R.id.listview);
            MyDisAdapter myAdapter = new MyDisAdapter(DisbursementListActivity.this, ldis);
            disburseList.setAdapter(myAdapter);
        }
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
