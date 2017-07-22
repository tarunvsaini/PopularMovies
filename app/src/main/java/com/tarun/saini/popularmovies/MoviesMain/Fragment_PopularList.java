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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tarun.saini.popularmovies.Model.MovieModel;
import com.tarun.saini.popularmovies.MovieAdapter.PopularMoviesAdapter;
import com.tarun.saini.popularmovies.R;
import com.tarun.saini.popularmovies.TmdbApi.TmdbClient;
import com.tarun.saini.popularmovies.TmdbApi.TmdbInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_PopularList extends Fragment {

    public final static String API_KEY = "de89bc307e69043208119480f9f2a159";
    public static final String TAG = PopularMovieList.class.getSimpleName();
    private RecyclerView recyclerView;
    private static final String SAVE_STATE = "saveState";
    private PopularMoviesAdapter mMovieAdapter;
    private boolean mTwoPane;
    RelativeLayout empty_layout;

    ArrayList<MovieModel> movieList;

    private PopularMoviesAdapter.OnMovieClickListener mCallBack;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallBack = (PopularMoviesAdapter.OnMovieClickListener) context;
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
        Call<MovieModel> call = tmdbInterface.getPopularMovies(API_KEY);
        call.enqueue(new Callback<MovieModel>() {

            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {

                movieList = response.body().getResults();


                    mMovieAdapter = new PopularMoviesAdapter(movieList, getContext(), mCallBack);
                    recyclerView.setAdapter(mMovieAdapter);











            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {



                Log.e(TAG, t.toString());

            }
        });


        if (savedInstanceState != null) {
            movieList = savedInstanceState.getParcelableArrayList(SAVE_STATE);
            if (movieList != null)
            {
                mMovieAdapter = new PopularMoviesAdapter(movieList, getActivity(), mCallBack);
                recyclerView.setAdapter(mMovieAdapter);

            }

        }

        return rootView;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVE_STATE, movieList);

    }
}