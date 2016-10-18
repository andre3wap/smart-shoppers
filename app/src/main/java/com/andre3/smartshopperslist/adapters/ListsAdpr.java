package com.andre3.smartshopperslist.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.andre3.smartshopperslist.R;
import com.andre3.smartshopperslist.model.Lists;
import com.andre3.smartshopperslist.views.PopupBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by andre3 on 10/16/16.
 */

public class ListsAdpr extends BaseAdapter {

    Context context;
    List<Lists> lists;
    ImageButton list_optn_btn;
    PopupBuilder dialog;
    ListView lv;

    public ListsAdpr(Context context, List<Lists> lists) {
        this.context = context;
        this.lists = lists;
    }

    public ListView getLv() {
        return lv;
    }

    public void setLv(ListView lv) {
        this.lv = lv;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context, R.layout.lists_adpr_view, null);

        dialog = new PopupBuilder(context);

        list_optn_btn = (ImageButton)v.findViewById(R.id.list_optn_btn);
        list_optn_btn.setFocusable(false);

        dialog.setLv(getLv());

        list_optn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.newList(true, lists.get(position).getId(), 0).show();
                System.out.println("Clicked: " +  lists.get(position).getName());
            }
        });


        TextView lists_tv = (TextView)v.findViewById(R.id.lists_tv);
        lists_tv.setText(lists.get(position).getName());


        return v;
    }
}
