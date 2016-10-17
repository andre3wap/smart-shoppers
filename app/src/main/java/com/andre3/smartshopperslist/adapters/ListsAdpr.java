package com.andre3.smartshopperslist.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.andre3.smartshopperslist.R;
import com.andre3.smartshopperslist.model.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by andre3 on 10/16/16.
 */

public class ListsAdpr extends BaseAdapter {

    Context context;
    List<Lists> lists;

    public ListsAdpr(Context context, List<Lists> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context, R.layout.lists_adpr_view, null);

        TextView lists_tv = (TextView)v.findViewById(R.id.lists_tv);
        lists_tv.setText(lists.get(position).getName());


        return v;
    }
}
