package com.andre3.smartshopperslist.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.andre3.smartshopperslist.dao.DBManager;
import com.andre3.smartshopperslist.enums.DBClmns;
import com.andre3.smartshopperslist.enums.DBTables;
import com.andre3.smartshopperslist.model.Category;
import com.andre3.smartshopperslist.model.Lists;

import java.util.ArrayList;

/**
 * Created by ODBBROW on 10/18/2016.
 */

public class CategoryImpl {


    private Category cat;
    private Context context;
    private DBManager db;

    public CategoryImpl( Context context, Category cat) {
        this.cat = cat;
        this.context = context;
        this.db = new DBManager(context);
    }

    public long save(){

        SQLiteDatabase sql = db.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DBClmns.name.toString(), cat.getName());
        values.put(DBClmns.listId.toString(), cat.getListId());
        values.put(DBClmns.storeId.toString(), cat.getStoreId());

        long id = sql.insert(DBTables.category.toString(), null, values);

        sql.close();
        return id;
    }

    public long update(){

        SQLiteDatabase sql = db.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(DBClmns.name.toString(), cat.getName());

        long id = sql.update(DBTables.category.toString(), values, "id = "+ cat.getId(), null);

        sql.close();
        return id;
    }

    public ArrayList<Category> readById(int listId){
        ArrayList<Category> arr = new ArrayList<Category>();

        String query = "SELECT * FROM "+ DBTables.category + " WHERE listId =" + listId;
        SQLiteDatabase sql = db.getReadableDatabase();


        Cursor c = sql.rawQuery(query, null);

        if(c.moveToFirst()){

            do{
                Category cat = new Category();
                cat.setId(c.getInt(c.getColumnIndex(DBClmns.id.toString())));
                cat.setName(c.getString(c.getColumnIndex(DBClmns.name.toString())));
                cat.setListId(c.getInt(c.getColumnIndex(DBClmns.listId.toString())));
                cat.setStoreId(c.getInt(c.getColumnIndex(DBClmns.storeId.toString())));

                arr.add(cat);
            }while(c.moveToNext());

        }

        sql.close();
        return arr;
    }
    public ArrayList<Category> readAll(){
        ArrayList<Category> arr = new ArrayList<Category>();

        String query = "SELECT * FROM "+ DBTables.category;
        SQLiteDatabase sql = db.getReadableDatabase();


        Cursor c = sql.rawQuery(query, null);

        if(c.moveToFirst()){

            do{
                Category cat = new Category();
                cat.setId(c.getInt(c.getColumnIndex(DBClmns.id.toString())));
                cat.setName(c.getString(c.getColumnIndex(DBClmns.name.toString())));
                cat.setListId(c.getInt(c.getColumnIndex(DBClmns.listId.toString())));
                cat.setStoreId(c.getInt(c.getColumnIndex(DBClmns.storeId.toString())));

                arr.add(cat);
            }while(c.moveToNext());

        }
        sql.close();

        return arr;
    }

    public long delete(int listId){

        SQLiteDatabase sql = db.getReadableDatabase();

        long id = sql.delete(DBTables.category.toString(), DBClmns.id.toString()+ "=" + listId, null    );

        sql.close();
        return id;
    }

}
