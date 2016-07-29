package com.tarun.saini.popularmovies.MoviesMain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.tarun.saini.popularmovies.Model.MovieModel;
import com.tarun.saini.popularmovies.MovieAdapter.PopularMovies;
import com.tarun.saini.popularmovies.R;
import com.tarun.saini.popularmovies.TmdbApi.TmdbClient;
import com.tarun.saini.popularmovies.TmdbApi.TmdbInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PopularMovieList extends AppCompatActivity {

    private static final String SAVE_STATE = "saveState";
    public static final String TAG = TopMovieList.class.getSimpleName();
    private RecyclerView recyclerView;
    private PopularMovies mMovieAdapter;
    private final static String API_KEY = "de89bc307e69043208119480f9f2a159";
    FloatingActionMenu floatingActionMenu;
    ArrayList<MovieModel> movieList;
    FloatingActionButton fab1, fab2, fab3;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_movie_list);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


        recyclerView = (RecyclerView) findViewById(R.id.topMovie_recyclerView);
        if (getResources().getConfiguration().orientation == 1) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else if (getResources().getConfiguration().orientation == 2) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        }
        TmdbInterface tmdbInterface = TmdbClient.getClient().create(TmdbInterface.class);
        Call<MovieModel> call = tmdbInterface.getPopularMovies(API_KEY);
        call.enqueue(new Callback<MovieModel>() {

            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {

                movieList = response.body().getResults();
                mMovieAdapter = new PopularMovies(movieList, getApplicationContext());
                recyclerView.setAdapter(mMovieAdapter);

            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

                Log.e(TAG, t.toString());

            }
        });

        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.floating_action_menu);
        fab1 = (FloatingActionButton) findViewById(R.id.floating_action_menu_popular);
        fab2 = (FloatingActionButton) findViewById(R.id.floating_action_menu_favorite);
        fab3 = (FloatingActionButton) findViewById(R.id.floating_action_menu_top_rated);

        fab1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PopularMovieList.class);
                startActivity(intent);

            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Toast.makeText(PopularMovieList.this, "Favourite Movies Clicked", Toast.LENGTH_SHORT).show();


            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TopMovieList.class);
                startActivity(intent);

            }
        });


        if (savedInstanceState != null) {
            movieList = savedInstanceState.getParcelableArrayList(SAVE_STATE);
            mMovieAdapter = new PopularMovies(movieList, getApplicationContext());
            recyclerView.setAdapter(mMovieAdapter);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVE_STATE, movieList);

    }
}
