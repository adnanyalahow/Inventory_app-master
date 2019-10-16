package com.example.yalahow.inventoryApp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.yalahow.inventoryApp.data.ProductContract.ProductEntry;

/**
 * Created by Adnan Yalahow on 8/14/2018.
 */

public class ProductDbHelper extends SQLiteOpenHelper {

    /** DATABASE VERSION */
    public static final int DATABASE_VERSION = 1;

    /** Name of the database file */
    public static final String DATABASE_NAME = "products.db";

    /** Create a String that contains the SQL statement to create the products table */
    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
            + ProductEntry.TABLE_NAME + " ("
            + ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ProductEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
            + ProductEntry.COLUMN_PRODUCT_QUANTITY + " INTEGER NOT NULL DEFAULT 0, "
            + ProductEntry.COLUMN_PRODUCT_PRICE + " INTEGER NOT NULL);";

    public ProductDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {


    }
}
