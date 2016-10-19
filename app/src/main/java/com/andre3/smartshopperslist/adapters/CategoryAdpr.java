package com.andre3.smartshopperslist.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.andre3.smartshopperslist.R;
import com.andre3.smartshopperslist.model.Category;
import com.andre3.smartshopperslist.model.Lists;
import com.andre3.smartshopperslist.model.Store;

import java.util.List;

/**
 * Created by ODBBROW on 10/19/2016.
 */

public class CategoryAdpr extends BaseAdapter {

    Context context;
    List<Category> cat;

    public CategoryAdpr(Context context, List<Category> cat) {
        this.cat = cat;
        this.context = context;
    }

    @Override
    public int getCount() {
        int arrSize = cat.size();
        return arrSize;
    }

    @Override
    public Object getItem(int position) {
        return cat.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context, R.layout.cat_adpr_view, null);
        TextView cat_edit_tv = (TextView)v.findViewById(R.id.cat_tv);

        cat_edit_tv.setText(cat.get(position).getName());


        return v;
    }
}
