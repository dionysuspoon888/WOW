package com.example.wow;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.wow.ShoppingCartContract.*;

public class ShoppingCartDBHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME= "shoppingCart.db";
    public static final int DATABASE_VERSION = 1;


    public ShoppingCartDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_SHOPPINGCART_TABLE="CREATE TABLE "+
                ShoppingCartEntry.TABLE_NAME+ " ("+
                ShoppingCartEntry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                ShoppingCartEntry.COLUMN_IMAGE +" INTEGER NOT NULL, "+
                ShoppingCartEntry.COLUMN_PRODUCT +" TEXT NOT NULL, "+
                ShoppingCartEntry.COLUMN_PRICE +" DOUBLE NOT NULL, "+
                ShoppingCartEntry.COLUMN_QUANTITY +" INTEGER NOT NULL"+
                ");";

        db.execSQL(SQL_CREATE_SHOPPINGCART_TABLE); //Create TABLE in DB when 1st time
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ShoppingCartEntry.TABLE_NAME);
        onCreate(db);


    }
}
