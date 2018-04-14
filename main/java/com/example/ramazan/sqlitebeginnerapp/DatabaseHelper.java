package com.example.ramazan.sqlitebeginnerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ramazan on 14.04.2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "StudentDatabase.db";
    private static final int DATANABASE_VERSION = 1;

    private static final String CREATE_TABLE_NOTE = "create table "
            + Constants.TABLE_NAME
            + "("
            + Constants.COLUMN_ID + " integer primary key autoincrement, "
            + Constants.COLUMN_FIRSTNAME + " text not null, "
            + Constants.COLUMN_EMAIL + " integer not null" + ")";

    private static final String DROP_TABLE_NOTE = "DROP TABLE IF EXISTS students";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATANABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("some sql statement to do something");
        db.execSQL(DROP_TABLE_NOTE);
        onCreate(db);

    }
}
