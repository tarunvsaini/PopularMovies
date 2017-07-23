package com.tarun.saini.popularmovies.MoviesMain;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.tarun.saini.popularmovies.Data.MovieDbHelper;
import com.tarun.saini.popularmovies.Model.MovieModel;
import com.tarun.saini.popularmovies.MovieAdapter.PopularMoviesAdapter;
import com.tarun.saini.popularmovies.R;

import java.util.ArrayList;
import java.util.List;


public class PopularMovieList extends AppCompatActivity implements PopularMoviesAdapter.OnMovieClickListener {


    public final static String API_KEY = "de89bc307e69043208119480f9f2a159";
    public static final String MOVIE_MODEL = "movieModel";
    private static final String SAVE_STATE = "save_state";
    public boolean mTwoPane;
    public static final String POSITION = "position";
    public static final String PANES = "panes";

    Fragment fragmentPopularMovies;


    FloatingActionButton fab1, fab2, fab3;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movie_list);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setFitsSystemWindows(true);

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(Color.parseColor("#20000000"));



        if (savedInstanceState==null)
        {
        fragmentPopularMovies=new Fragment_PopularList();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.movie_list_container,fragmentPopularMovies);
        fragmentTransaction.commit();
        }else
        {
            if(fragmentPopularMovies!=null && fragmentPopularMovies.isAdded())
            {
                fragmentPopularMovies =  getSupportFragmentManager().getFragment(savedInstanceState,SAVE_STATE);

            }

        }

        FloatingActionMenu floatingActionMenu = (FloatingActionMenu) findViewById(R.id.floating_action_menu);
        fab1 = (FloatingActionButton) findViewById(R.id.floating_action_menu_popular);
        fab2 = (FloatingActionButton) findViewById(R.id.floating_action_menu_favorite);
        fab3 = (FloatingActionButton) findViewById(R.id.floating_action_menu_top_rated);

        fab1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PopularMovieList.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), FavoriteMovieList.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TopMovieList.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });


        if (mTwoPane = findViewById(R.id.movie_linear_layout) != null) {
            mTwoPane = true;

        } else {
            mTwoPane = false;
        }

    }


    @Override
    public void onMovieSelected(int position, ArrayList<MovieModel> movie) {


        Bundle bundle = new Bundle();
        if (mTwoPane) {

            Fragment_MovieDetails movieDetails = new Fragment_MovieDetails();


            bundle.putInt(POSITION, position);
            bundle.putBoolean(PANES, mTwoPane);
            bundle.putParcelableArrayList(MOVIE_MODEL, movie);
            movieDetails.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.movie_detail_container, movieDetails);
            transaction.commit();
        } else {
            Intent intent = new Intent(this, MovieDetails.class);
            bundle.putInt(POSITION, position);
            bundle.putBoolean(PANES, mTwoPane);
            intent.putExtra(MovieDetails.DETAILS, movie.get(position));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtras(bundle);
            startActivity(intent);

        }


    }@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragmentPopularMovies!=null && fragmentPopularMovies.isAdded())
        {
            getSupportFragmentManager().putFragment(outState,SAVE_STATE,fragmentPopularMovies);

        }

    }






}