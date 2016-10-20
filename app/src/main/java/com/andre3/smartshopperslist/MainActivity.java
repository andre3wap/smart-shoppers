package com.andre3.smartshopperslist;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import com.andre3.smartshopperslist.model.Category;
import com.andre3.smartshopperslist.model.Store;
import com.andre3.smartshopperslist.services.CategoryImpl;
import com.andre3.smartshopperslist.services.StoreImpl;
import com.andre3.smartshopperslist.views.CatEditFragment;
import com.andre3.smartshopperslist.views.StoreEditFragment;
import com.andre3.smartshopperslist.views.MainFragment;
import com.andre3.smartshopperslist.views.PopupBuilder;

public class MainActivity extends AppCompatActivity {

    PopupBuilder pb;
    StoreImpl storeDao;
    String MY_PREFS_NAME = "myPrefs";
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainFragment fr = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fr).addToBackStack(null).commit();


        // Call popup builder
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        pb = new PopupBuilder(MainActivity.this);

        // Controls the first prompt for the user to create a store.
        Boolean access = prefs.getBoolean("firstAccess", false);
        int userStoreId = prefs.getInt("default_storeId", 0);

        if(!access){

            // Confirm that the user created the store
            editor.putBoolean("firstAccess", true);
            editor.commit();

            // Launch popup for user to create first store
            pb.setDialogTitle("Create Store");
            pb.newStore(false, true, 0).show();

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        final Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        storeDao = new StoreImpl(getApplicationContext(), new Store());

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, storeDao.readNames());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

                /// Save values for the store that the user last selected
                String[] active_store = storeDao.readNames().get(position).toString().split("-");


                editor.putInt("default_storeId", Integer.parseInt(active_store[0].trim()));
                editor.putString("default_store", active_store[1]);
                editor.commit();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_store) {
            pb.setDialogTitle("Create Store");
            pb.newStore(false, false, 0).show();
            return true;
        }
        if (id == R.id.action_edit_store) {

            StoreEditFragment fr = new StoreEditFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fr, "store_edit").addToBackStack(null).commit();

            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}