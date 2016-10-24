package com.andre3.smartshopperslist.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.andre3.smartshopperslist.enums.DBTables;
import com.andre3.smartshopperslist.enums.DBClmns;

/**
 * Created by ODBBROW on 10/14/2016.
 */

public class DBManager extends SQLiteOpenHelper {

    // DB info
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ssl.db";

    // Value Types
    private static String TYPE_INT = "int";
    private static String TYPE_STRING = "String";
    private static String TYPE_BOOLEAN = "Boolean";

    // Build Queries
    private static final String SQL_STORE_TBL =
            "CREATE TABLE " + DBTables.store + "(" +
            DBClmns.id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DBClmns.name + " " + TYPE_STRING+", "+
            DBClmns.location +" "+ TYPE_STRING+")";

    private static final String SQL_CAT_TBL =
            "CREATE TABLE " + DBTables.category + "(" +
            DBClmns.id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DBClmns.name + " " + TYPE_STRING+","+
            DBClmns.listId +" "+ TYPE_INT+","+
            DBClmns.storeId +" "+ TYPE_INT+")";

    private static final String SQL_LIST_TBL =
            "CREATE TABLE " + DBTables.lists + "(" +
            DBClmns.id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DBClmns.name + " " + TYPE_STRING+","+
            DBClmns.storeId +" "+ TYPE_INT+","+
            DBClmns.reminder +" "+ TYPE_STRING+")";

    private static final String SQL_ITEMS_TBL =
            "CREATE TABLE " + DBTables.items + "(" +
            DBClmns.id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DBClmns.name + " " + TYPE_STRING+","+
            DBClmns.price + " " + TYPE_STRING+","+
            DBClmns.qty + " " + TYPE_INT+","+
            DBClmns.isle + " " + TYPE_INT+","+
            DBClmns.unit + " " + TYPE_STRING+","+
            DBClmns.catId + " " + TYPE_INT+","+
            DBClmns.storeId +" "+ TYPE_INT+","+
            DBClmns.inCart +" "+ TYPE_INT+","+
            DBClmns.listId +" "+ TYPE_INT+","+
            DBClmns.time +" "+ TYPE_STRING+")";



    private static final String SQL_DELETE_ENTRIES =  "DROP TABLE IF EXISTS " + DBTables.store;
    private static final String SQL_DELETE_ENTRIES1 =  "DROP TABLE IF EXISTS " + DBTables.category;
    private static final String SQL_DELETE_ENTRIES2 =  "DROP TABLE IF EXISTS " + DBTables.lists;
    private static final String SQL_DELETE_ENTRIES3 =  "DROP TABLE IF EXISTS " + DBTables.items;

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_LIST_TBL);
        db.execSQL(SQL_STORE_TBL);
        db.execSQL(SQL_ITEMS_TBL);
        db.execSQL(SQL_CAT_TBL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_ENTRIES1);
        db.execSQL(SQL_DELETE_ENTRIES2);
        db.execSQL(SQL_DELETE_ENTRIES3);
        onCreate(db);
    }
}
