package com.andre3.smartshopperslist.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.andre3.smartshopperslist.R;
import com.andre3.smartshopperslist.model.Category;
import com.andre3.smartshopperslist.model.Item;
import com.andre3.smartshopperslist.model.Lists;
import com.andre3.smartshopperslist.model.Store;
import com.andre3.smartshopperslist.services.CategoryImpl;
import com.andre3.smartshopperslist.services.ItemImpl;
import com.andre3.smartshopperslist.services.StoreImpl;

import java.util.List;

/**
 * Created by ODBBROW on 10/19/2016.
 */

public class CategoryAdpr extends BaseAdapter {

    Context context;
    List<Category> cat;

    public CategoryAdpr(Context context, List<Category> cat) {
        this.cat = cat;
        this.context = context;
    }

    @Override
    public int getCount() {
        int arrSize = cat.size();
        return arrSize;
    }

    @Override
    public Object getItem(int position) {
        return cat.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context, R.layout.cat_adpr_view, null);

        TextView cat_edit_tv = (TextView)v.findViewById(R.id.cat_tv);
        final ImageButton cat_del_btn = (ImageButton)v.findViewById(R.id.cat_del_btn);
        cat_del_btn.setFocusable(false);


        cat_edit_tv.setText(cat.get(position).getName());

        cat_del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Pass category ID for deletion
               dialogBox(cat.get(position).getId());

            }
        });


        return v;
    }

    public void dialogBox(final int catId) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Are you sure you want to delete this Category? all data associated with it will be removed as well.");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        // Grab database layer to perform delete operation
                        CategoryImpl dao = new CategoryImpl(context, new Category());
                        dao.delete(catId);


                        // Soft delete items
                        Item item = new Item();
                        item.setCatId(catId);
                        ItemImpl item_dao = new ItemImpl(item, context);
                        item_dao.softDeleteCat();


                        Toast.makeText(context, "Your category and all associated data were deleted." , Toast.LENGTH_LONG).show();
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
