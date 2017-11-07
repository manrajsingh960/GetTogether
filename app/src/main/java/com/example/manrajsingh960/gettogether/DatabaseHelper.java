package com.example.manrajsingh960.gettogether;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Dennis on 10/27/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.FeedEntry.COL1 + " TEXT," +
                    FeedReaderContract.FeedEntry.COL2 + " TEXT," +
                    FeedReaderContract.FeedEntry.COL3 + " TEXT," +
                    FeedReaderContract.FeedEntry.COL4 + " TEXT," +
                    FeedReaderContract.FeedEntry.COL5 + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_NAME;

    public boolean addData(String entry1, String entry2, String entry3, String entry4, String entry5) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COL1, entry1);
        values.put(FeedReaderContract.FeedEntry.COL2, entry2);
        values.put(FeedReaderContract.FeedEntry.COL3, entry3);
        values.put(FeedReaderContract.FeedEntry.COL4, entry4);
        values.put(FeedReaderContract.FeedEntry.COL5, entry5);
        long result = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        if(result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + FeedReaderContract.FeedEntry.TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }


}