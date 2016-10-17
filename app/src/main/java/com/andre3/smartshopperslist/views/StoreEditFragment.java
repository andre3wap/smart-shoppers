package com.andre3.smartshopperslist.views;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.andre3.smartshopperslist.MainActivity;
import com.andre3.smartshopperslist.R;
import com.andre3.smartshopperslist.adapters.StoreAdpr;
import com.andre3.smartshopperslist.model.Store;
import com.andre3.smartshopperslist.services.StoreImpl;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
/**
 * Created by andre3 on 10/14/16.
 */

public class StoreEditFragment extends Fragment {

    StoreImpl dao;
    StoreAdpr adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Load fragment and display shopping lists
        View view = inflater.inflate(R.layout.store_edit_frg, container, false);

        dao = new StoreImpl(getContext(), new Store());

         adapter = new StoreAdpr(getContext(), dao.readAll());
        final ListView  store_edit_lv = (ListView) view.findViewById(R.id.store_edit_lv);
        store_edit_lv.setAdapter(adapter);


        store_edit_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PopupBuilder dialog = new PopupBuilder(getContext());
                dialog.setLv(store_edit_lv);
                dialog.setDialogTitle("Update Store");
                dialog.newStore(true, false, dao.readAll().get(position).getId()).show();

            }
        });
        return view;
    }


}
