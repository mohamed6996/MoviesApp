package com.example.lenovo.myapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by lenovo on 10/31/2016.
 */

public class MovieHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favorites.db";
    private static final int DATABASE_VERSION = 5;


    public MovieHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_PETS_TABLE =  "CREATE TABLE " + MoviesContract.MovieEntry.TABLE_NAME + " ("
                + MoviesContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MoviesContract.MovieEntry.COLUMN_NAME_TITLE + " TEXT NOT NULL); ";

        db.execSQL(SQL_CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
