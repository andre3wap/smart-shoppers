package com.andre3.smartshopperslist.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andre3.smartshopperslist.R;
import com.andre3.smartshopperslist.model.Item;
import com.andre3.smartshopperslist.model.Lists;
import com.andre3.smartshopperslist.model.Store;
import com.andre3.smartshopperslist.services.ItemImpl;
import com.andre3.smartshopperslist.services.ListImpl;
import com.andre3.smartshopperslist.services.StoreImpl;
import com.andre3.smartshopperslist.views.PopupBuilder;
import com.andre3.smartshopperslist.views.StoreEditFragment;

import org.w3c.dom.Text;

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

        list_optn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setLv(getLv());
                dialog.newList(true, lists.get(position).getId(), 0).show();
            }
        });

        ItemImpl dao = new ItemImpl(new Item(), context);

        TextView lists_tv = (TextView)v.findViewById(R.id.lists_tv);
        TextView list_itm_count = (TextView)v.findViewById(R.id.list_itm_count);

        lists_tv.setText(lists.get(position).getName());
        list_itm_count.setText("( " +dao.readByListId(lists.get(position).getId()).size()+ " / " +dao.readByListIdAll(lists.get(position).getId()).size()+" )");


        return v;
    }
    public void refreshEvents() {
        this.lists.clear();
        this.lists.addAll(lists);
        notifyDataSetChanged();
    }


}
