package com.example.ramazan.sqlitebeginnerapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

/**
 * Created by ramazan on 14.04.2018.
 */

public class TableControllerStudent extends DatabaseHelper {

    private static final String SELECT_TABLE_NOTE = "SELECT * FROM " + Constants.TABLE_NAME;
    private static final String SELECT_ORDER_TABLE_NOTE = "SELECT * FROM " + Constants.TABLE_NAME
            + " ORDER BY " + Constants.COLUMN_ID + " DESC";

    public TableControllerStudent(Context context)
    {
        super(context);
    }

    public boolean create(ObjectStudent student)
    {
        ContentValues values = new ContentValues();

        values.put(Constants.COLUMN_FIRSTNAME, student.firstName);
        values.put(Constants.COLUMN_EMAIL, student.email);

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = (db.insert(Constants.TABLE_NAME, null, values) > 0);
        db.close(); //need to close db after operation..

        return createSuccessful;
    }

    public List<ObjectStudent> read(){
        List<ObjectStudent> recordsList = new ArrayList<ObjectStudent>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECT_ORDER_TABLE_NOTE, null);

        if(cursor.moveToFirst()){

            do{
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_ID)));
                String studentFirstName = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_FIRSTNAME));
                String studentEmail = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_EMAIL));

                ObjectStudent student = new ObjectStudent();
                student.id = id;
                student.firstName = studentFirstName;
                student.email = studentEmail;

                recordsList.add(student);
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return  recordsList;
    }

    public int count()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        int recordCount = db.rawQuery(SELECT_TABLE_NOTE, null).getCount();
        db.close();

        return recordCount;
    }
}
