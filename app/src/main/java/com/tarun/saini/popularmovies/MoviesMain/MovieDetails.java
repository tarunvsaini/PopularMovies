package com.tarun.saini.popularmovies.MoviesMain;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.florent37.glidepalette.GlidePalette;
import com.tarun.saini.popularmovies.Model.ExtrasModel;
import com.tarun.saini.popularmovies.Model.MovieModel;
import com.tarun.saini.popularmovies.R;

import java.util.ArrayList;
import java.util.List;

import static com.tarun.saini.popularmovies.MoviesMain.PopularMovieList.PANES;

public class MovieDetails extends AppCompatActivity {

    public static final String DETAILS ="details" ;
    private MovieModel mMovieModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);


        mMovieModel=getIntent().getParcelableExtra(DETAILS);
        boolean mTwoPane=getIntent().getBooleanExtra(PANES,false);
        Fragment_MovieDetails movieDetails;
        if (savedInstanceState==null)
        {
            Bundle bundle = new Bundle();
            movieDetails=new Fragment_MovieDetails();
            bundle.putBoolean(PANES, mTwoPane);
            movieDetails.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.movie_detail_container, movieDetails);
            transaction.commit();
        }else
            {
                movieDetails= (Fragment_MovieDetails) getSupportFragmentManager().findFragmentById(R.id.movie_list_container);

            }



}}









