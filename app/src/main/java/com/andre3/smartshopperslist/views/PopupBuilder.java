package com.andre3.smartshopperslist.views;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.andre3.smartshopperslist.MainActivity;
import com.andre3.smartshopperslist.R;
import com.andre3.smartshopperslist.adapters.ListsAdpr;
import com.andre3.smartshopperslist.adapters.StoreAdpr;
import com.andre3.smartshopperslist.model.Lists;
import com.andre3.smartshopperslist.model.Store;
import com.andre3.smartshopperslist.services.ListImpl;
import com.andre3.smartshopperslist.services.StoreImpl;

/**
 * Created by andre3 on 10/13/16.
 */

public class PopupBuilder extends AppCompatActivity {

    Context context;
    String dialogTitle;
    AutoCompleteTextView store_et;
    EditText store_lcn_et, list_name_et;
    Button store_btn, list_btn, list_del_btn;
    ListView lv;
    BaseAdapter adapter;


    public PopupBuilder(Context context) {
        this.context = context;
    }

    public void setDialogTitle(String dialogTitle) {
        this.dialogTitle = dialogTitle;
    }

    public ListView getLv() {
        return lv;
    }

    public void setLv(ListView lv) {
        this.lv = lv;
    }

    public BaseAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(StoreAdpr adapter) {
        this.adapter = adapter;
    }


    /********** Custom dialogs below ************/

    public Dialog newList( final boolean updateData, final int listId, final int storeId){

        final Dialog dialog = new Dialog(this.context);
        dialog.setTitle(this.dialogTitle);
        dialog.setContentView(R.layout.list_new_dialog);


        list_name_et = (EditText) dialog.findViewById(R.id.list_name_et);
        list_btn = (Button) dialog.findViewById(R.id.list_btn);
        list_del_btn = (Button) dialog.findViewById(R.id.list_del_btn);
        list_del_btn.setVisibility(View.GONE);

        // Read data from database
        final Lists lists = new Lists();
         ListImpl dao = new ListImpl(context, lists);

        // If @updateData set to true
        if(updateData){
            list_del_btn.setVisibility(View.VISIBLE);
            list_name_et.setText(dao.readById(listId).get(0).getName());
            //TODO: setup date pickers
            ////list_name_et.setText(dao.readById(listId).get(0).getName());

        }

        list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(list_name_et.getText().toString().isEmpty()){

                    Toast.makeText(context, "Field should not be left blank", Toast.LENGTH_LONG).show();

                }else {

                   lists.setName(list_name_et.getText().toString());
                    ///lists.setReminder();
                    lists.setStoreId(storeId);
                    lists.setId(listId);
                    ListImpl dao = new ListImpl(context, lists);

                    if(updateData) {
                        long id = dao.update();
                    }else{
                        long id = dao.save();
                    }

                    ListsAdpr adapter = new ListsAdpr(context,  dao.readByStoreId(storeId));
                    lv.setAdapter(adapter);

                      dialog.dismiss();
                }
            }
        });



        return dialog;
    }

    public Dialog newStore(final boolean updateData , Boolean cancelable, final int storeId){


        final Dialog dialog = new Dialog(this.context);
        dialog.setTitle(this.dialogTitle);


        if(cancelable) {
            dialog.setCancelable(false);
        }
        dialog.setContentView(R.layout.store_new_dialog);

        store_lcn_et = (EditText)dialog.findViewById(R.id.store_lcn_et);
        store_et = (AutoCompleteTextView)dialog.findViewById(R.id.store_et);
        store_btn = (Button)dialog.findViewById(R.id.store_btn);

        // Read data from database
        final Store store = new Store();
        StoreImpl dao = new StoreImpl(context, store);

        // Update data if update boolead is true
        if(updateData){
            store_lcn_et.setText(dao.readById(storeId).get(0).getLocation());
            store_et.setText(dao.readById(storeId).get(0).getName());
        }

        store_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(store_et.getText().toString().isEmpty()){

                    Toast.makeText(context, "Field should not be left blank", Toast.LENGTH_LONG).show();

                }else{

                    store.setId(storeId);
                    store.setName(store_et.getText().toString());
                    store.setLocation(store_lcn_et.getText().toString());

                    StoreImpl dao = new StoreImpl(context, store);

                    if(updateData){

                        long id = dao.update();

                        // Call adapter to refresh the data
                        StoreAdpr adapter = new StoreAdpr(context, dao.readAll());
                        lv.setAdapter(adapter);

                    }else {

                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                        long id = dao.save();

                    }
                   dialog.dismiss();

                }
            }
        });


        return dialog;
    }


}