package com.example.rakesh.starbucks;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rakesh on 20-02-2017.
 */

public class Helper extends SQLiteOpenHelper {
    private static final String DB_NAME = "starbucks"; // the name of our database
    private static final int DB_VERSION = 2; // the version of the database
    public Helper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateDatabase(db,0,DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateDatabase(db,oldVersion,newVersion);
    }
    private static void insertDrink(SQLiteDatabase db,String name,String description, int resourceId) { //this method was created to
        ContentValues drinkValues = new ContentValues();       //... insert several drink rows
        drinkValues.put("NAME", name);
        drinkValues.put("DESCRIPTION", description);
        drinkValues.put("image_id", resourceId);
        db.insert("DRINK", null, drinkValues);
    }
    private void updateDatabase(SQLiteDatabase db,int oldVersion,int newVersion){
        if (oldVersion<1){
            db.execSQL("CREATE TABLE DRINK(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "NAME TEXT,"
                    + "DESCRIPTION TEXT,"
                    + "image_id integer);");
            insertDrink(db, "Latte", "Espresso and steamed milk", R.drawable.latte);    //to statically insert data into table
            insertDrink(db, "Cappuccino", "Espresso, hot milk and steamed-milk foam",
                    R.drawable.cappuccino);
            insertDrink(db, "Filter", "Our best drip coffee", R.drawable.filter);
        }
        if (oldVersion<2){
            db.execSQL("alter table DRINK add column favorite numeric;");
        }
    }
}
