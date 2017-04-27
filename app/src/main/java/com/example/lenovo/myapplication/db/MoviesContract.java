package com.example.lenovo.myapplication.db;

import android.provider.BaseColumns;

/**
 * Created by lenovo on 10/31/2016.
 */

public final class MoviesContract {

    private MoviesContract(){}

    public static class MovieEntry implements BaseColumns{

        public static final String TABLE_NAME = "movies";

        public static final String _id = BaseColumns._ID ;
        public static final String COLUMN_NAME_TITLE = "json_string";




    }



}
