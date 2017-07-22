package com.tarun.saini.popularmovies.MoviesMain;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tarun.saini.popularmovies.Model.MovieModel;
import com.tarun.saini.popularmovies.MovieAdapter.TopRatedMoviesAdapter;
import com.tarun.saini.popularmovies.R;
import com.tarun.saini.popularmovies.TmdbApi.TmdbClient;
import com.tarun.saini.popularmovies.TmdbApi.TmdbInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_TopList extends Fragment {

    private static final String SAVE_STATE = "saveState";
    public static final String TAG = TopMovieList.class.getSimpleName();
    private RecyclerView recyclerView;
    private TopRatedMoviesAdapter mMovieAdapter;
    private boolean mTwoPane;
    ArrayList<MovieModel> movieList;
    private TopRatedMoviesAdapter.OnTopMovieClickListener mCallBack;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallBack = (TopRatedMoviesAdapter.OnTopMovieClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnMovieClickListener");
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.Movie_recyclerView);
        if (getResources().getConfiguration().orientation == 1) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } else if (getResources().getConfiguration().orientation == 2) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        }
        TmdbInterface tmdbInterface = TmdbClient.getClient().create(TmdbInterface.class);
        Call<MovieModel> call = tmdbInterface.getTopRatedMovies(PopularMovieList.API_KEY);
        call.enqueue(new Callback<MovieModel>() {

            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {

                movieList = response.body().getResults();
                mMovieAdapter = new TopRatedMoviesAdapter(movieList, getContext(), mCallBack);
                recyclerView.setAdapter(mMovieAdapter);

            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

                Log.e(TAG, t.toString());

            }
        });


        if (savedInstanceState != null) {
            movieList = savedInstanceState.getParcelableArrayList(SAVE_STATE);
            mMovieAdapter = new TopRatedMoviesAdapter(movieList, getContext(), mCallBack);
            recyclerView.setAdapter(mMovieAdapter);
        }
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVE_STATE, movieList);

    }
}
