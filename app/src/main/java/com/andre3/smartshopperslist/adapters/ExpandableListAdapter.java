package com.andre3.smartshopperslist.adapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.andre3.smartshopperslist.R;
import com.andre3.smartshopperslist.model.Item;
import com.andre3.smartshopperslist.services.ItemImpl;
import com.andre3.smartshopperslist.views.CatEditFragment;
import com.andre3.smartshopperslist.views.MainFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by andre3 on 10/18/16.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    ItemImpl dao;
    int i;


    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        final String[] splitName = childText.split("-");


        final TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);

        ImageButton item_del_btn = (ImageButton)convertView.findViewById(R.id.item_del_btn);
        ImageButton item_qty_btn = (ImageButton)convertView.findViewById(R.id.item_qty_btn);
        item_qty_btn.setTag(splitName[0]);
        item_del_btn.setFocusable(false);
        item_qty_btn.setFocusable(false);

         final TextView item_price = (TextView)convertView.findViewById(R.id.item_price);
        final TextView item_isle = (TextView)convertView.findViewById(R.id.item_isle);
        final TextView  item_qty = (TextView)convertView.findViewById(R.id.item_qty);


        int upQty1 = Integer.parseInt(splitName[3].trim());
        float pricef = Float.parseFloat(splitName[2].trim()) * upQty1;

        item_price.setText("$"+pricef);
        item_qty.setText("Qty: " + splitName[3]);
        item_isle.setText("Isle:" + splitName[4]);

        txtListChild.setText(splitName[1]);

        final ArrayList item_qtyArr = new ArrayList();

        final TextView item_qty_tv = (TextView)convertView.findViewById(R.id.item_qty);
        item_qty_tv.setTag(splitName[0]);

        i=1;
        item_qty_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Add each tagId to an array for record keeping
                item_qtyArr.add(item_qty_tv.getTag());

                int index = item_qtyArr.size();
                int prevIndex;

                // Get the previous index to find out which row was clicked last.
                if(index >= 2) {
                    prevIndex = index - 2;
                }else {
                    prevIndex = 0;
                }


                // Reset count when user clicks a new row.
                if((Integer.parseInt(item_qty_tv.getTag().toString().trim()) != Integer.parseInt(item_qtyArr.get(prevIndex).toString().trim()))){
                    i = 0;
                }


                // Calculate new values
                int upQty = Integer.parseInt(splitName[3].trim()) + i;
                float price = Float.parseFloat(splitName[2].trim()) * upQty;

                // Update textview with new calculated values.
                item_qty_tv.setText("Qty: " + upQty);
                item_price.setText("$" + price);


                // Update the DB with the new amount.
                Item item = new Item();
                item.setId(Integer.parseInt(splitName[0].trim()));
                item.setQty(upQty);
                ItemImpl dao = new ItemImpl(item , _context );
                long id = dao.updateQty();

                i++;
            }
        });

          dao = new ItemImpl(new Item() , _context );


        item_del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int inCart;

                if( dao.readById(Integer.parseInt(splitName[0].trim())).get(0).getInCart() == 0){

                    inCart = 1;

                    txtListChild.setPaintFlags(txtListChild.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    item_price.setPaintFlags(item_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                }else{

                    inCart = 0;
                    txtListChild.setPaintFlags(txtListChild.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                    item_price.setPaintFlags(item_price.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                }

                // Update the DB with the new amount.
                Item item = new Item();
                item.setId(Integer.parseInt(splitName[0].trim()));
                item.setInCart(inCart);
                item.setQty(dao.readById(Integer.parseInt(splitName[0].trim())).get(0).getQty());
                ItemImpl dao = new ItemImpl(item , _context );
                long id = dao.updateQty();


                System.out.println("Name "+ dao.readById(Integer.parseInt(splitName[0].trim())).size());
            }
        });

        if( dao.readById(Integer.parseInt(splitName[0].trim())).get(0).getInCart() == 1){

            txtListChild.setPaintFlags(txtListChild.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            item_price.setPaintFlags(item_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String headerTitle = (String) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        ImageButton list_head_btn = (ImageButton)convertView.findViewById(R.id.list_head_btn);
        list_head_btn.setFocusable(false);

        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        list_head_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CatEditFragment fr = new CatEditFragment();
                ((FragmentActivity) _context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fr).addToBackStack("test_back").commit();

            }
        });

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
