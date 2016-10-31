package com.andre3.smartshopperslist.views;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andre3.smartshopperslist.MainActivity;
import com.andre3.smartshopperslist.R;
import com.andre3.smartshopperslist.adapters.CategoryAdpr;
import com.andre3.smartshopperslist.adapters.ListsAdpr;
import com.andre3.smartshopperslist.adapters.StoreAdpr;
import com.andre3.smartshopperslist.model.Category;
import com.andre3.smartshopperslist.model.Item;
import com.andre3.smartshopperslist.model.Lists;
import com.andre3.smartshopperslist.model.Store;
import com.andre3.smartshopperslist.services.AutoCompleteImpl;
import com.andre3.smartshopperslist.services.CategoryImpl;
import com.andre3.smartshopperslist.services.ItemImpl;
import com.andre3.smartshopperslist.services.ListImpl;
import com.andre3.smartshopperslist.services.StoreImpl;
import com.andre3.smartshopperslist.tools.DatePickerFrg;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by andre3 on 10/13/16.
 */

public class PopupBuilder extends AppCompatActivity {

    Context context;
    String dialogTitle;
    AutoCompleteTextView store_et, name_et;
    EditText store_lcn_et, list_name_et, cat_name_et;
    EditText price_et, isle_et, qty_et, unit_et, list_rem_et;
    Spinner spinner2, unit_sp;
    Button store_btn, list_btn, list_del_btn, cat_btn, item_save_btn, list_rem_btn;
    ListView lv;
    TextView list_rem_tv;
    BaseAdapter adapter;
    AutoCompleteImpl autoComp;
    int inCart = 0;


    public PopupBuilder(Context context) {
        this.context = context;
        this.autoComp = new AutoCompleteImpl();
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

    public Dialog newItem(final boolean updateData, final int listId, final int catId,  final int storeId, final int id){
        final Dialog dialog = new Dialog(this.context,  R.style.DialogTheme);
        dialog.setContentView(R.layout.item_new_dialog);

        CategoryImpl dao = new CategoryImpl( context , new Category());

        name_et = (AutoCompleteTextView)dialog.findViewById(R.id.name_et);
        price_et = (EditText)dialog.findViewById(R.id.price_et);
        isle_et = (EditText)dialog.findViewById(R.id.isle_et);
        qty_et = (EditText)dialog.findViewById(R.id.qty_et);
        unit_sp = (Spinner)dialog.findViewById(R.id.spinner4);

        spinner2 = (Spinner)dialog.findViewById(R.id.spinner2);

        if(updateData){


            ItemImpl item_dao = new ItemImpl(new Item(), context);

            String price = String.valueOf(item_dao.readById(id).get(0).getPrice());
            String qty = String.valueOf(item_dao.readById(id).get(0).getQty());

            name_et.setText(item_dao.readById(id).get(0).getName());
            price_et.setText(price);
            isle_et.setText(item_dao.readById(id).get(0).getIsle());
            qty_et.setText(qty);
        }

        // Setup autocomplete EditText
        ArrayAdapter autocompletetextAdapter = new ArrayAdapter<String>( context,  android.R.layout.simple_dropdown_item_1line, autoComp.itemsAuto());
        name_et.setAdapter(autocompletetextAdapter);


        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                context, android.R.layout.simple_spinner_item, autoComp.unitsAuto());

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unit_sp.setAdapter(adapter2);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                context, android.R.layout.simple_spinner_item, dao.readNames(listId));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);



        item_save_btn = (Button)dialog.findViewById(R.id.item_save_btn);

        item_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selected_cat = spinner2.getSelectedItem().toString();
                String selected_unit = unit_sp.getSelectedItem().toString();
                final String[] s_cat = selected_cat.split("-");

                if(name_et.getText().toString().isEmpty() || Float.parseFloat( price_et.getText().toString())< 0 || Integer.parseInt(qty_et.getText().toString()) <=0 || isle_et.getText().toString().isEmpty()){

                    Toast.makeText(context, "All fields are required", Toast.LENGTH_LONG).show();

                }else {
                    Item items = new Item();
                    items.setId(id);
                    items.setName(name_et.getText().toString());
                    items.setPrice(Float.parseFloat(price_et.getText().toString()));
                    items.setIsle(isle_et.getText().toString());
                    items.setQty(Integer.parseInt(qty_et.getText().toString()));
                    items.setUnit(selected_unit);
                    items.setListId(listId);
                    items.setCatId(Integer.parseInt(s_cat[0]));
                    items.setStoreId(storeId);
                    items.setInCart(inCart);

                    ItemImpl dao = new ItemImpl(items, context);
                    if(updateData)
                    {
                        dao.update();
                    }else{
                        dao.save();
                    }


                    dialog.dismiss();

                }


                CategoryFragment fr = new CategoryFragment();
                Bundle bd = new Bundle();

                bd.putInt("listId", listId);
                fr.setArguments(bd);

                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fr).addToBackStack(null).commit();
            }
        });





        return dialog;
    }
    public Dialog newCat(final boolean updateData, boolean cancelAble, final int catId, final int listId, final int storeId){
        final Dialog dialog = new Dialog(this.context, R.style.DialogTheme);
        dialog.setTitle(this.dialogTitle);
        dialog.setContentView(R.layout.cat_new_dialog);

        if(cancelAble) {
            dialog.setCancelable(false);
        }

        cat_name_et = (EditText) dialog.findViewById(R.id.cat_name_et);
        cat_btn = (Button) dialog.findViewById(R.id.cat_btn);

        /// Get clicked cat name from DB
        if(updateData){
            CategoryImpl dao = new CategoryImpl(context, new Category());
            cat_name_et.setText(dao.readByCatId(catId).get(0).getName());
        }

        cat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Category cat = new Category();
                cat.setId(catId);
                cat.setName(cat_name_et.getText().toString());
                cat.setListId(listId);
                cat.setStoreId(storeId);

                CategoryImpl dao = new CategoryImpl(context, cat);
                if(updateData){
                    dao.update();
                    adapter = new CategoryAdpr(context, dao.readByStoreId(storeId));
                    getLv().setAdapter(adapter);
                }else {
                    dao.save();
                    CategoryFragment fr = new CategoryFragment();

                    Bundle bd = new Bundle();

                    bd.putInt("listId", listId);
                    fr.setArguments(bd);

                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fr).addToBackStack(null).commit();

                }



                dialog.dismiss();
            }
        });



        return dialog;
    }
    public Dialog newList( final boolean updateData, final int listId, final int storeId){
//TODO: consider moving the delete list button from the popup and put it in the Adapter / Fragment
        final Dialog dialog = new Dialog(this.context, R.style.DialogTheme);
        dialog.setTitle(this.dialogTitle);
        dialog.setContentView(R.layout.list_new_dialog);


        list_name_et = (EditText) dialog.findViewById(R.id.list_name_et);
        list_rem_btn = (Button)dialog.findViewById(R.id.list_rem_btn);
        list_rem_tv = (TextView)dialog.findViewById(R.id.list_rem_tv);
        list_btn = (Button) dialog.findViewById(R.id.list_btn);
        list_del_btn = (Button) dialog.findViewById(R.id.list_del_btn);
        list_del_btn.setVisibility(View.GONE);

        // Read data from database
        final Lists lists = new Lists();
        final ListImpl dao = new ListImpl(context, lists);


        // If @updateData set to true
        if(updateData){

            // Show delete button on popup
            list_del_btn.setVisibility(View.VISIBLE);

            // Pre populate fields
            list_name_et.setText(dao.readById(listId).get(0).getName());
            list_rem_tv.setText(dao.readById(listId).get(0).getReminder());

            // Delete selected list
            list_del_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Delete List
                    dao.dialogBox(listId);
                    dialog.dismiss();
                }
            });

        }
        list_rem_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    DialogFragment newFragment = new DatePickerFrg(dialog);
                    newFragment.show( ((FragmentActivity) context).getSupportFragmentManager(), "datePicker");
            }
        });

        list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ///COMMENT
                if(list_name_et.getText().toString().isEmpty()){

                    Toast.makeText(context, "Field should not be left blank", Toast.LENGTH_LONG).show();

                }else {

                   lists.setName(list_name_et.getText().toString());
                    lists.setReminder(list_rem_tv.getText().toString());
                    lists.setStoreId(storeId);
                    lists.setId(listId);
                    ListImpl dao = new ListImpl(context, lists);

                    if(updateData) {
                        long id = dao.update();
                    }else{
                        long id = dao.save();
                    }

                    // Passing reminder date to AlarmManager
                    if(list_rem_tv.getText().toString()!= "None"){

                        String myTime = "07:00";
                        String myDate = list_rem_tv.getText().toString();

                        String toParse = myDate + " " + myTime; // Results in "2-5-2012 07:00"
                        SimpleDateFormat formatter = new SimpleDateFormat("M-d-yyyy hh:mm"); // I assume d-M, you may refer to M-d for month-day instead.
                        Date date = null; // You will need try/catch around this
                        try {
                            date = formatter.parse(toParse);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        long millis = date.getTime();

                        System.out.println("Milli" + millis);
                    }
                    MainFragment fr = new MainFragment();
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fr).addToBackStack(null).commit();


                    dialog.dismiss();
                }
            }
        });



        return dialog;
    }

    public Dialog newStore(final boolean updateData , Boolean cancelable, final int storeId){


        final Dialog dialog = new Dialog(this.context, R.style.DialogTheme);
        dialog.setTitle(this.dialogTitle);


        if(cancelable) {
            dialog.setCancelable(false);
        }
        dialog.setContentView(R.layout.store_new_dialog);

        store_lcn_et = (EditText)dialog.findViewById(R.id.store_lcn_et);
        store_et = (AutoCompleteTextView)dialog.findViewById(R.id.store_et);
        store_btn = (Button)dialog.findViewById(R.id.store_btn);


        ArrayAdapter autocompletetextAdapter = new ArrayAdapter<String>( context,  android.R.layout.simple_dropdown_item_1line, autoComp.storeAuto());
        store_et.setAdapter(autocompletetextAdapter);


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

                    Toast.makeText(context, "Store name should not be left blank", Toast.LENGTH_LONG).show();

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

                        long id = dao.save();
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);

                    }
                   dialog.dismiss();

                }
            }
        });


        return dialog;
    }


}