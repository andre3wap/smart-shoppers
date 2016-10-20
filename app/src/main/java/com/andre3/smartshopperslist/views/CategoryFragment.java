package com.andre3.smartshopperslist.views;

import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.andre3.smartshopperslist.R;
import com.andre3.smartshopperslist.adapters.ExpandableListAdapter;
import com.andre3.smartshopperslist.model.Category;
import com.andre3.smartshopperslist.services.CategoryImpl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by andre3 on 10/18/16.
 */

public class CategoryFragment extends Fragment{

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    int listId;
    int userStoreId;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        forceShowActionBarOverflowMenu();

        View view = inflater.inflate(R.layout.cat_main_frg, container, false);
        CategoryImpl header_dao = new CategoryImpl(getContext() , new Category());
        SharedPreferences prefs = getContext().getSharedPreferences("myPrefs", MODE_PRIVATE);


         listId = getArguments().getInt("listId");
         userStoreId = prefs.getInt("default_storeId", 0);

        // Check if the user has any categories assigned to the selected list, if not; display a popup.
        if(header_dao.readById(listId).size() <= 0){

            PopupBuilder dialog  = new PopupBuilder(getContext());
            dialog.newCat(false, true, 0, listId,  userStoreId ).show();
        }

        //TODO: When creating cats, users should be able to select from presets


        // get the listview
        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);

        // preparing list data
        prepareListData(listId);

        listAdapter = new ExpandableListAdapter(getContext(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);


        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Dialog to add new item to Category....
                PopupBuilder dialog  = new PopupBuilder(getContext());
                dialog.newItem(false,listId, 0, userStoreId).show();

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

    private void prepareListData(int listId) {
        int i = 0;
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");




        CategoryImpl header_dao = new CategoryImpl(getContext() , new Category());

        for(Category temp : header_dao.readById(listId)){

            listDataHeader.add(temp.getName());

            listDataChild.put(listDataHeader.get(i), top250); // Header, Child data

            i++;
        }

    }
}

