package com.tarun.saini.popularmovies.MoviesMain;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tarun.saini.popularmovies.Data.MovieContract;
import com.tarun.saini.popularmovies.Model.MovieModel;
import com.tarun.saini.popularmovies.MovieAdapter.FavoriteMovieAdapter;
import com.tarun.saini.popularmovies.R;

import java.util.ArrayList;
import java.util.List;

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

public class Fragment_FavoriteMovie extends Fragment {


    List<MovieModel> movieList;
    FavoriteMovieAdapter favoriteMovieAdapter;
    Context mContext;
    RelativeLayout empty_relative_layout;




    public Fragment_FavoriteMovie() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment__favorite_movie, container, false);
        empty_relative_layout= (RelativeLayout) rootView.findViewById(R.id.empty_view_favorite);


        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.favorite_recyclerView);

        if (getResources().getConfiguration().orientation == 1) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }
        else if (getResources().getConfiguration().orientation == 2 ) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));}

        movieList=getAllMovie();
        if(movieList.size()!=0)
        {
            favoriteMovieAdapter=new FavoriteMovieAdapter(movieList,getActivity());
            recyclerView.setAdapter(favoriteMovieAdapter);
            empty_relative_layout.setVisibility(View.INVISIBLE);

        }
        else
            {
                recyclerView.setVisibility(View.INVISIBLE);
                empty_relative_layout.setVisibility(View.VISIBLE);

            }



        return rootView;
    }



    public List<MovieModel> getAllMovie(){
        List<MovieModel> movieList = new ArrayList<>();
        String[] projection = {
                KEY_ID,
                KEY_TITLE,
                KEY_RATING,
                KEY_DATE,
                KEY_OVERVIEW,
                KEY_BACKDROP,
                KEY_VOTE_COUNT,
                KEY_LANGUAGE,
                KEY_POPULARITY,
                KEY_POSTER

        };
        Cursor cursor=getActivity().getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,projection,null,null,null);
        assert cursor != null;
        if (cursor.moveToFirst()){
            do {
                MovieModel movie = new MovieModel(cursor.getString(cursor.getColumnIndex(KEY_POSTER)),
                        cursor.getString(cursor.getColumnIndex(KEY_OVERVIEW)),
                        cursor.getString(cursor.getColumnIndex(KEY_DATE)),
                        Integer.valueOf(cursor.getString(cursor.getColumnIndex(KEY_ID))),
                        cursor.getString(cursor.getColumnIndex(KEY_LANGUAGE)),
                        cursor.getString(cursor.getColumnIndex(KEY_TITLE)),
                        Double.valueOf(cursor.getString(cursor.getColumnIndex(KEY_POPULARITY))),
                        cursor.getString(cursor.getColumnIndex(KEY_BACKDROP)),
                        Integer.valueOf(cursor.getString(cursor.getColumnIndex(KEY_VOTE_COUNT))),
                        Double.valueOf(cursor.getString(cursor.getColumnIndex(KEY_RATING))));
                movieList.add(movie);

            }while(cursor.moveToNext());
        }
        cursor.close();
        return movieList;
    }

}
