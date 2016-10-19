package com.andre3.smartshopperslist.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.andre3.smartshopperslist.R;
import com.andre3.smartshopperslist.adapters.CategoryAdpr;
import com.andre3.smartshopperslist.adapters.StoreAdpr;
import com.andre3.smartshopperslist.model.Category;
import com.andre3.smartshopperslist.services.CategoryImpl;
import com.andre3.smartshopperslist.services.StoreImpl;

/**
 * Created by ODBBROW on 10/19/2016.
 */

public class CatEditFragment extends Fragment {

    CategoryImpl dao;
    ///CategoryAdpr adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Load fragment and display shopping lists
        View view = inflater.inflate(R.layout.cat_edit_frg, container, false);

        ListView cat_lv = (ListView)view.findViewById(R.id.cat_lv);

        CategoryImpl dao = new CategoryImpl(getContext(), new Category());
        CategoryAdpr adapter = new CategoryAdpr(getContext(), dao.readAll());

        cat_lv.setAdapter(adapter);




        return view;
    }

}
