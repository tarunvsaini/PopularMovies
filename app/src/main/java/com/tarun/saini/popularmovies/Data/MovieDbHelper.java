package com.tarun.saini.popularmovies.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.tarun.saini.popularmovies.Data.MovieContract.MovieEntry.KEY_BACKDROP;
import static com.tarun.saini.popularmovies.Data.MovieContract.MovieEntry.KEY_DATE;
import static com.tarun.saini.popularmovies.Data.MovieContract.MovieEntry.KEY_ID;
import static com.tarun.saini.popularmovies.Data.MovieContract.MovieEntry.KEY_LANGUAGE;
import static com.tarun.saini.popularmovies.Data.MovieContract.MovieEntry.KEY_OVERVIEW;
import static com.tarun.saini.popularmovies.Data.MovieContract.MovieEntry.KEY_POPULARITY;
import static com.tarun.saini.popularmovies.Data.MovieContract.MovieEntry.KEY_POSTER;
import static com.tarun.saini.popularmovies.Data.MovieContract.MovieEntry.KEY_RATING;
import static com.tarun.saini.popularmovies.Data.MovieContract.MovieEntry.KEY_TITLE;
import static com.tarun.saini.popularmovies.Data.MovieContract.MovieEntry.KEY_VOTE_COUNT;
import static com.tarun.saini.popularmovies.Data.MovieContract.MovieEntry.TABLE_NAME;

public class MovieDbHelper extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "favouriteMovie.db";

    public static final String LOGTAG = "FAVORITE";
    private SQLiteOpenHelper dbhandler;
    private SQLiteDatabase db;



    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void open(){
        Log.i(LOGTAG, "Database Opened");
        db = dbhandler.getWritableDatabase();
    }

    public void close(){
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MOVIES_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_RATING + " TEXT,"
                + KEY_DATE + " TEXT,"
                + KEY_OVERVIEW + " TEXT,"
                + KEY_BACKDROP + " TEXT,"
                + KEY_VOTE_COUNT + " TEXT,"
                + KEY_LANGUAGE + " TEXT,"
                + KEY_POPULARITY + " TEXT,"
                + KEY_POSTER + " TEXT" + ")";
        db.execSQL(CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


}
