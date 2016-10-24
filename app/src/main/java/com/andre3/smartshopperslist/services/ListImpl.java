package com.andre3.smartshopperslist.services;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.andre3.smartshopperslist.dao.DBManager;
import com.andre3.smartshopperslist.enums.DBClmns;
import com.andre3.smartshopperslist.enums.DBTables;
import com.andre3.smartshopperslist.model.Item;
import com.andre3.smartshopperslist.model.Lists;

import java.util.ArrayList;

/**
 * Created by andre3 on 10/16/16.
 */

public class ListImpl {

    private  Lists lists;
    private Context context;
    private DBManager db;

    public ListImpl(Context context , Lists lists) {
        this.lists = lists;
        this.context = context;
        this.db = new DBManager(context);
    }

    public long save(){
        SQLiteDatabase sql = db.getReadableDatabase();

        // Package values
        ContentValues values = new ContentValues();

        values.put(DBClmns.name.toString(), lists.getName());
        values.put(DBClmns.reminder.toString(), lists.getReminder());
        values.put(DBClmns.storeId.toString(), lists.getStoreId());

        long id = sql.insert(DBTables.lists.toString(), null, values);
        sql.close();

        return id;
    }

    public long update(){

        SQLiteDatabase sql = db.getReadableDatabase();

        // Package values
        ContentValues values = new ContentValues();

        values.put(DBClmns.name.toString(), lists.getName());
        values.put(DBClmns.reminder.toString(), lists.getReminder());

        long id = sql.update(DBTables.lists.toString(), values, " id ="+ lists.getId(), null);
        sql.close();

        return id;
    }

    public long delete(int listId){

        SQLiteDatabase sql = db.getReadableDatabase();

        long id = sql.delete(DBTables.lists.toString(), " id ="+ listId, null);
        sql.close();

        return id;
    }

    public ArrayList<Lists> readAll(){

        String query = "SELECT * FROM "+ DBTables.lists.toString();
        SQLiteDatabase sql = db.getReadableDatabase();

        ArrayList<Lists> arr = new ArrayList<Lists>();
        Cursor c = sql.rawQuery(query, null);

        if(c.moveToFirst()){

            do{
                Lists lists = new Lists();

                lists.setId(c.getInt(c.getColumnIndex(DBClmns.id.toString())));
                lists.setName(c.getString(c.getColumnIndex(DBClmns.name.toString())));
                lists.setReminder(c.getString(c.getColumnIndex(DBClmns.reminder.toString())));
                lists.setStoreId(c.getInt(c.getColumnIndex(DBClmns.storeId.toString())));

                arr.add(lists);

            }while (c.moveToNext());

        }

        return arr;
    }
    public ArrayList<Lists> readById(int id){

        String query = "SELECT * FROM "+ DBTables.lists.toString()+ " WHERE id ="+id;

        SQLiteDatabase sql = db.getReadableDatabase();

        ArrayList<Lists>  arr = new ArrayList<Lists>();
        Cursor c = sql.rawQuery(query, null);

        if(c.moveToFirst()){

            do{
                Lists lists = new Lists();

                lists.setId(c.getInt(c.getColumnIndex(DBClmns.id.toString())));
                lists.setName(c.getString(c.getColumnIndex(DBClmns.name.toString())));
                lists.setReminder(c.getString(c.getColumnIndex(DBClmns.reminder.toString())));
                lists.setStoreId(c.getInt(c.getColumnIndex(DBClmns.storeId.toString())));

                arr.add(lists);
            }while (c.moveToNext());

        }


        return arr;
    }

    public ArrayList<Lists> readByStoreId(int id){

        String query = "SELECT * FROM "+ DBTables.lists.toString()+ " WHERE storeId ="+id;

        SQLiteDatabase sql = db.getReadableDatabase();

        ArrayList<Lists>  arr = new ArrayList<Lists>();
        Cursor c = sql.rawQuery(query, null);

        if(c.moveToFirst()){

            do{
                Lists lists = new Lists();

                lists.setId(c.getInt(c.getColumnIndex(DBClmns.id.toString())));
                lists.setName(c.getString(c.getColumnIndex(DBClmns.name.toString())));
                lists.setReminder(c.getString(c.getColumnIndex(DBClmns.reminder.toString())));
                lists.setStoreId(c.getInt(c.getColumnIndex(DBClmns.storeId.toString())));

                arr.add(lists);
            }while (c.moveToNext());

        }


        return arr;
    }

    public void dialogBox(final int listId) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Are you sure you want to delete this list item? all data associated with it will be removed as well.");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        // Hard delete
                        delete(listId);

                        // Soft delete items
                        Item item = new Item();
                        item.setListId(listId);
                        final ItemImpl itemDao = new ItemImpl(item, context);
                        itemDao.softDelete();

                        Toast.makeText(context, "Your list and all associated data were deleted." , Toast.LENGTH_LONG).show();
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
