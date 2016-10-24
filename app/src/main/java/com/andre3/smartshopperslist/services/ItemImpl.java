package com.andre3.smartshopperslist.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.andre3.smartshopperslist.dao.DBManager;
import com.andre3.smartshopperslist.enums.DBClmns;
import com.andre3.smartshopperslist.enums.DBTables;
import com.andre3.smartshopperslist.model.Item;

import java.util.ArrayList;

/**
 * Created by ODBBROW on 10/20/2016.
 */

public class ItemImpl {

    Item item;
    Context context;
    DBManager db;

    public ItemImpl(Item item, Context context) {
        this.item = item;
        this.context = context;
        this.db = new DBManager(context);
    }

    public long save(){

        SQLiteDatabase sql = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBClmns.name.toString(), item.getName());
        values.put(DBClmns.price.toString(), item.getPrice());
        values.put(DBClmns.qty.toString(), item.getQty());
        values.put(DBClmns.unit.toString(), item.getUnit());
        values.put(DBClmns.isle.toString(), item.getIsle());
        values.put(DBClmns.catId.toString(), item.getCatId());
        values.put(DBClmns.storeId.toString(), item.getStoreId());
        values.put(DBClmns.listId.toString(), item.getListId());
        values.put(DBClmns.time.toString(), item.getTime());
        values.put(DBClmns.inCart.toString(), item.getInCart());

       long id =  sql.insert(DBTables.items.toString(), null, values);
        sql.close();
        return id;
    }

    public long update(){

        SQLiteDatabase sql = db.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBClmns.name.toString(), item.getName());
        values.put(DBClmns.price.toString(), item.getPrice());
        values.put(DBClmns.qty.toString(), item.getQty());
        values.put(DBClmns.unit.toString(), item.getUnit());
        values.put(DBClmns.isle.toString(), item.getIsle());
        values.put(DBClmns.catId.toString(), item.getCatId());
        //values.put(DBClmns.storeId.toString(), item.getStoreId());
        //values.put(DBClmns.listId.toString(), item.getListId());
        values.put(DBClmns.time.toString(), item.getTime());

        long id =  sql.update(DBTables.items.toString(), values, " id="+item.getId(), null);

        sql.close();
        return id;
    }

    public long updateQty(){

        SQLiteDatabase sql = db.getReadableDatabase();

        ContentValues values = new ContentValues();

        values.put(DBClmns.qty.toString(), item.getQty());
        values.put(DBClmns.inCart.toString(), item.getInCart());

        long id =  sql.update(DBTables.items.toString(), values, " id="+item.getId(), null);

        sql.close();
        return id;
    }

    public ArrayList<Item>  readAll(){

        String query = "SELECT * FROM "+ DBTables.items;
        ArrayList<Item> items = new ArrayList<Item>();
        SQLiteDatabase sql = db.getReadableDatabase();

        Cursor c = sql.rawQuery(query, null);

        if(c.moveToFirst()){

            do{
                Item item = new Item();
                item.setId(c.getInt(c.getColumnIndex(DBClmns.id.toString())));
                item.setName(c.getString(c.getColumnIndex(DBClmns.name.toString())));
                item.setPrice(c.getInt(c.getColumnIndex(DBClmns.price.toString())));
                item.setQty(c.getInt(c.getColumnIndex(DBClmns.qty.toString())));
                item.setUnit(c.getString(c.getColumnIndex(DBClmns.unit.toString())));
                item.setIsle(c.getString(c.getColumnIndex(DBClmns.isle.toString())));
                item.setCatId(c.getInt(c.getColumnIndex(DBClmns.catId.toString())));
                item.setStoreId(c.getInt(c.getColumnIndex(DBClmns.storeId.toString())));
                item.setListId(c.getInt(c.getColumnIndex(DBClmns.listId.toString())));
                item.setTime(c.getInt(c.getColumnIndex(DBClmns.time.toString())));

                items.add(item);

            }while(c.moveToNext());

        }
        sql.close();
        return items;

    }
    public ArrayList<Item> readByCatId(int catId){

        String query = "SELECT * FROM "+ DBTables.items + " WHERE `catId`="+catId; //---+ " AND inCart ="+0;
        ArrayList<Item> items = new ArrayList<Item>();
        SQLiteDatabase sql = db.getReadableDatabase();

        Cursor c = sql.rawQuery(query, null);

        if(c.moveToFirst()){

            do{
                Item item = new Item();
                item.setId(c.getInt(c.getColumnIndex(DBClmns.id.toString())));
                item.setName(c.getString(c.getColumnIndex(DBClmns.name.toString())));
                item.setPrice(c.getInt(c.getColumnIndex(DBClmns.price.toString())));
                item.setQty(c.getInt(c.getColumnIndex(DBClmns.qty.toString())));
                item.setUnit(c.getString(c.getColumnIndex(DBClmns.unit.toString())));
                item.setIsle(c.getString(c.getColumnIndex(DBClmns.isle.toString())));
                item.setCatId(c.getInt(c.getColumnIndex(DBClmns.catId.toString())));
                item.setStoreId(c.getInt(c.getColumnIndex(DBClmns.storeId.toString())));
                item.setListId(c.getInt(c.getColumnIndex(DBClmns.listId.toString())));
                item.setTime(c.getInt(c.getColumnIndex(DBClmns.time.toString())));
                item.setInCart(c.getInt(c.getColumnIndex(DBClmns.inCart.toString())));

                items.add(item);

            }while(c.moveToNext());

        }

        sql.close();
        return items;
    }

    public ArrayList<Item> readById(int Id){

        String query = "SELECT * FROM "+ DBTables.items + " WHERE `id`="+Id;///+ " AND inCart ="+0;
        ArrayList<Item> items = new ArrayList<Item>();
        SQLiteDatabase sql = db.getReadableDatabase();

        Cursor c = sql.rawQuery(query, null);

        if(c.moveToFirst()){

            do{
                Item item = new Item();
                item.setId(c.getInt(c.getColumnIndex(DBClmns.id.toString())));
                item.setName(c.getString(c.getColumnIndex(DBClmns.name.toString())));
                item.setPrice(c.getInt(c.getColumnIndex(DBClmns.price.toString())));
                item.setQty(c.getInt(c.getColumnIndex(DBClmns.qty.toString())));
                item.setUnit(c.getString(c.getColumnIndex(DBClmns.unit.toString())));
                item.setIsle(c.getString(c.getColumnIndex(DBClmns.isle.toString())));
                item.setCatId(c.getInt(c.getColumnIndex(DBClmns.catId.toString())));
                item.setStoreId(c.getInt(c.getColumnIndex(DBClmns.storeId.toString())));
                item.setListId(c.getInt(c.getColumnIndex(DBClmns.listId.toString())));
                item.setTime(c.getInt(c.getColumnIndex(DBClmns.time.toString())));
                item.setInCart(c.getInt(c.getColumnIndex(DBClmns.inCart.toString())));

                items.add(item);

            }while(c.moveToNext());

        }

        sql.close();
        return items;
    }


    public ArrayList<Item> readByListId(int listId){

        String query = "SELECT * FROM "+ DBTables.items + " WHERE `listId`="+listId+ " AND inCart ="+0;
        ArrayList<Item> items = new ArrayList<Item>();
        SQLiteDatabase sql = db.getReadableDatabase();

        Cursor c = sql.rawQuery(query, null);

        if(c.moveToFirst()){

            do{
                Item item = new Item();
                item.setId(c.getInt(c.getColumnIndex(DBClmns.id.toString())));
                item.setName(c.getString(c.getColumnIndex(DBClmns.name.toString())));
                item.setPrice(c.getInt(c.getColumnIndex(DBClmns.price.toString())));
                item.setQty(c.getInt(c.getColumnIndex(DBClmns.qty.toString())));
                item.setUnit(c.getString(c.getColumnIndex(DBClmns.unit.toString())));
                item.setIsle(c.getString(c.getColumnIndex(DBClmns.isle.toString())));
                item.setCatId(c.getInt(c.getColumnIndex(DBClmns.catId.toString())));
                item.setStoreId(c.getInt(c.getColumnIndex(DBClmns.storeId.toString())));
                item.setListId(c.getInt(c.getColumnIndex(DBClmns.listId.toString())));
                item.setTime(c.getInt(c.getColumnIndex(DBClmns.time.toString())));
                item.setInCart(c.getInt(c.getColumnIndex(DBClmns.inCart.toString())));

                items.add(item);

            }while(c.moveToNext());

        }

        sql.close();
        return items;
    }

    public ArrayList<Item> readByListIdAll(int listId){

        String query = "SELECT * FROM "+ DBTables.items + " WHERE `listId`="+listId;
        ArrayList<Item> items = new ArrayList<Item>();
        SQLiteDatabase sql = db.getReadableDatabase();

        Cursor c = sql.rawQuery(query, null);

        if(c.moveToFirst()){

            do{
                Item item = new Item();
                item.setId(c.getInt(c.getColumnIndex(DBClmns.id.toString())));
                item.setName(c.getString(c.getColumnIndex(DBClmns.name.toString())));
                item.setPrice(c.getInt(c.getColumnIndex(DBClmns.price.toString())));
                item.setQty(c.getInt(c.getColumnIndex(DBClmns.qty.toString())));
                item.setUnit(c.getString(c.getColumnIndex(DBClmns.unit.toString())));
                item.setIsle(c.getString(c.getColumnIndex(DBClmns.isle.toString())));
                item.setCatId(c.getInt(c.getColumnIndex(DBClmns.catId.toString())));
                item.setStoreId(c.getInt(c.getColumnIndex(DBClmns.storeId.toString())));
                item.setListId(c.getInt(c.getColumnIndex(DBClmns.listId.toString())));
                item.setTime(c.getInt(c.getColumnIndex(DBClmns.time.toString())));
                item.setInCart(c.getInt(c.getColumnIndex(DBClmns.inCart.toString())));

                items.add(item);

            }while(c.moveToNext());

        }

        sql.close();
        return items;
    }


    public long softDelete(){

        SQLiteDatabase sql = db.getReadableDatabase();

        ContentValues values = new ContentValues();

        values.put(DBClmns.inCart.toString(), 1);

        long id =  sql.update(DBTables.items.toString(), values, " listId="+item.getListId(), null);

        sql.close();
        return id;
    }


    public long softDeleteCat(){

        SQLiteDatabase sql = db.getReadableDatabase();

        ContentValues values = new ContentValues();

        values.put(DBClmns.inCart.toString(), 1);

        long id =  sql.update(DBTables.items.toString(), values, " catId="+item.getCatId(), null);

        sql.close();
        return id;
    }

    public long delete(int itemId){

        SQLiteDatabase sql = db.getReadableDatabase();
        long id = sql.delete(DBTables.items.toString(), "id="+itemId, null );
        sql.close();
        return id;
    }

}
