package com.example.jack.team09adandroidapp;

/**
 * Created by CHEN ZIQING on 2018/1/30.
 */
import android.app.Activity;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class BreakdownByDepartmentAdapter extends ArrayAdapter<BreakdownByDepartment> {
    private Activity context;
    private static List<BreakdownByDepartment> list;
    private int resource;

    public BreakdownByDepartmentAdapter(Activity context, List<BreakdownByDepartment> list) {
        super(context, R.layout.retrieval_form_by_dept_item, list);
        this.resource = resource;
        this.list = list;
        this.context=context;
    }
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        LayoutInflater inflater= context.getLayoutInflater();
        View v= inflater.inflate(R.layout.retrieval_form_by_dept_item,null,true);
        TextView deptID=(TextView)v.findViewById(R.id.deptID);
        TextView needed=(TextView)v.findViewById(R.id.needed);

        deptID.setText(list.get(position).getDeptID().toString());
        needed.setText(String.valueOf(list.get(position).getNeeded()));
        return v;
    }

    public static List<BreakdownByDepartment> getList(){
        return list;
    }
}
