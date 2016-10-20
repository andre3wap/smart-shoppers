package com.andre3.smartshopperslist.views;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.andre3.smartshopperslist.R;
import com.andre3.smartshopperslist.adapters.CategoryAdpr;
import com.andre3.smartshopperslist.adapters.StoreAdpr;
import com.andre3.smartshopperslist.model.Category;
import com.andre3.smartshopperslist.services.CategoryImpl;
import com.andre3.smartshopperslist.services.StoreImpl;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ODBBROW on 10/19/2016.
 */

public class CatEditFragment extends Fragment {

    CategoryImpl dao;
    CategoryAdpr adapter;
    int userStoreId, userListId;
    ListView cat_lv;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Load fragment and display shopping lists
        View view = inflater.inflate(R.layout.cat_edit_frg, container, false);

         cat_lv = (ListView)view.findViewById(R.id.cat_lv);


        SharedPreferences prefs = getContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
         userStoreId = prefs.getInt("default_storeId", 0);
         userListId = prefs.getInt("active_listId", 0);

        System.out.println("List ID: "+ userListId);

        dao = new CategoryImpl(getContext(), new Category());
        adapter = new CategoryAdpr(getContext(), dao.readByStoreId(userStoreId));

        cat_lv.setAdapter(adapter);

        cat_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Popup for edit category
                PopupBuilder dialog  = new PopupBuilder(getContext());
                dialog.setLv(cat_lv);
                dialog.newCat(true, false, dao.readByStoreId(userStoreId).get(position).getId() , 0,  userStoreId ).show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupBuilder dialog  = new PopupBuilder(getContext());
                dialog.newCat(false, false, 0 , userListId,  userStoreId ).show();
            }
        });


        return view;
    }

}
