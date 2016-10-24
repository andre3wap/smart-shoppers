package com.andre3.smartshopperslist.views;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.andre3.smartshopperslist.MainActivity;
import com.andre3.smartshopperslist.R;
import com.andre3.smartshopperslist.adapters.ListsAdpr;
import com.andre3.smartshopperslist.model.Lists;
import com.andre3.smartshopperslist.services.ListImpl;

import java.lang.reflect.Field;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by andre3 on 10/14/16.
 */

public class MainFragment extends Fragment {

    ListView lv;
    PopupBuilder dialog;
    ListImpl dao;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        forceShowActionBarOverflowMenu();

        //Get user's default storeId
        SharedPreferences prefs = getContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor = getContext().getSharedPreferences("myPrefs", MODE_PRIVATE).edit();

         final int userStoreId = prefs.getInt("default_storeId", 0);

        //Load fragment and display shopping lists
        View view = inflater.inflate(R.layout.content_main, container, false);
        lv = (ListView)view.findViewById(R.id.list_name_lv);

        dao  = new ListImpl(getContext(), new Lists() );

        ListsAdpr adapter = new ListsAdpr(getContext(), dao.readByStoreId(userStoreId));
        adapter.setLv(lv);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bd = new Bundle();
                CategoryFragment fr = new CategoryFragment();

                bd.putInt("listId", dao.readByStoreId(userStoreId).get(position).getId());
                fr.setArguments(bd);

                editor.putInt("active_listId", dao.readByStoreId(userStoreId).get(position).getId() );
                editor.commit();

                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fr).addToBackStack(null).commit();

            }
        });



        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences prefs = getContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
                int userStoreId = prefs.getInt("default_storeId", 0);

                ///Snackbar.make(view, "Replace with your own ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                dialog = new PopupBuilder(getContext());
                dialog.setLv(lv);
                dialog.setDialogTitle("Create new list");
                dialog.newList(false, 0, userStoreId).show();
            }
        });

        return view;
    }
    private void forceShowActionBarOverflowMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(getContext());
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
