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
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import com.andre3.smartshopperslist.R;
import com.andre3.smartshopperslist.adapters.ExpandableListAdapter;
import com.andre3.smartshopperslist.model.Category;
import com.andre3.smartshopperslist.model.Item;
import com.andre3.smartshopperslist.services.CategoryImpl;
import com.andre3.smartshopperslist.services.ItemImpl;

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


        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {


                System.out.println("Child " + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition) );

                String[] child_arr = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).split("-");

                PopupBuilder dialog  = new PopupBuilder(getContext());
                dialog.newItem(true,listId, 0, userStoreId, Integer.parseInt(child_arr[0].trim())).show();


                return false;
            }
        });

        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Dialog to add new item to Category....
                PopupBuilder dialog  = new PopupBuilder(getContext());
                dialog.newItem(false,listId, 0, userStoreId, 0).show();

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

        List<List<String>> arr_lists = new ArrayList<List<String>>();


        CategoryImpl header_dao = new CategoryImpl(getContext() , new Category());
        ItemImpl child_dao = new ItemImpl(new Item(), getContext());


        System.out.println("Item Size: " + child_dao.readByCatId(1));

        for(Category temp : header_dao.readById(listId)){


            List<String> arr_list2 = new ArrayList<>();
            arr_lists.add(arr_list2);

            for(Item listTemp : child_dao.readByCatId( temp.getId())) {

                    arr_lists.get(i).add(listTemp.getId() + " - " + listTemp.getName() + " - " + listTemp.getPrice()  + " - " + listTemp.getQty() + " - " + listTemp.getIsle());

            }

            listDataHeader.add(temp.getName());
            listDataChild.put(listDataHeader.get(i), arr_lists.get(i)); // Header, Child data

            i++;
        }

    }
}

