package com.tarun.saini.popularmovies.Data;

import android.net.Uri;
import android.provider.BaseColumns;

public final class MovieContract {


    private MovieContract(){}


    public static final String CONTENT_AUTHORITY = "com.tarun.saini.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIES = "MoviesTable";



    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MOVIES);


        public static final String TABLE_NAME = "MoviesTable";

        public static final String KEY_ID = "id";
        public static final String KEY_TITLE = "title";
        public static final String KEY_RATING = "rating";
        public static final String KEY_DATE = "date";
        public static final String KEY_OVERVIEW = "overview";
        public static final String KEY_BACKDROP = "backdrop";
        public static final String KEY_VOTE_COUNT = "vote_count";
        public static final String KEY_LANGUAGE = "language";
        public static final String KEY_POPULARITY = "popularity";
        public static final String KEY_POSTER = "poster";
    }
}
