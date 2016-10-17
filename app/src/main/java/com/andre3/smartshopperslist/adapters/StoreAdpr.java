package com.andre3.smartshopperslist.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.andre3.smartshopperslist.R;
import com.andre3.smartshopperslist.model.Store;
import com.andre3.smartshopperslist.services.StoreImpl;

import java.util.List;

/**
 * Created by andre3 on 10/15/16.
 */

public class StoreAdpr extends BaseAdapter{

    private List<Store> store;
    Context context;

    public StoreAdpr(Context context, List<Store> store) {
        this.store = store;
        this.context = context;
    }

    @Override
    public int getCount() {
        int arrSize = store.size();
        return arrSize;
    }

    @Override
    public Object getItem(int position) {
        return store.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context, R.layout.store_adpr_view, null);
        TextView store_edit_tv = (TextView)v.findViewById(R.id.store_edit_tv);

        store_edit_tv.setText(store.get(position).getName());

        ImageButton btn = (ImageButton)v.findViewById(R.id.store_delete_btn);
        btn.setFocusable(false);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogBox(store.get(position).getId());
            }
        });


        return v;
    }
    public void dialogBox(final int storeId) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Are you sure you want to delete this store? all data associated with it will be removed as well.");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        // Grab database layer to perform delete operation
                        StoreImpl dao = new StoreImpl(context, new Store());
                        dao.delete(storeId);

                        Toast.makeText(context, "Your store and all associated data were deleted." , Toast.LENGTH_LONG).show();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
