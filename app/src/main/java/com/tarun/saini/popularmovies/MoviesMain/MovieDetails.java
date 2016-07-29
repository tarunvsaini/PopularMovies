package com.tarun.saini.popularmovies.MoviesMain;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.florent37.glidepalette.GlidePalette;
import com.tarun.saini.popularmovies.Model.MovieModel;
import com.tarun.saini.popularmovies.R;

import java.util.ArrayList;

public class MovieDetails extends AppCompatActivity {

    private static final String SAVE_STATE ="saveState" ;
    ArrayList<MovieModel> movies=new ArrayList<>();
    public static final String DETAILS ="details" ;
    private MovieModel mMovieModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ImageView backdrop = (ImageView) findViewById(R.id.backdrop);
        ImageView poster = (ImageView) findViewById(R.id.poster);
        TextView title = (TextView) findViewById(R.id.name);
        TextView release = (TextView) findViewById(R.id.release);
        TextView language = (TextView) findViewById(R.id.language);
        TextView desc = (TextView) findViewById(R.id.desc);
        TextView ratings = (TextView) findViewById(R.id.ratings);
        TextView votes = (TextView) findViewById(R.id.votes);
        TextView popularity = (TextView) findViewById(R.id.popularity);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);


        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        if (savedInstanceState !=null)
        {
            movies=savedInstanceState.getParcelableArrayList(SAVE_STATE);
            mMovieModel = getIntent().getParcelableExtra(DETAILS);

        }

        mMovieModel = getIntent().getParcelableExtra(DETAILS);
        title.setText(mMovieModel.getTitle());
        String releaseDate = "Release Date: ";
        release.setText(releaseDate + mMovieModel.getReleaseDate());
        String language1 = "Language: ";
        language.setText(language1 + mMovieModel.getOriginalLanguage());
        desc.setText(mMovieModel.getOverview());
        ratings.setText(mMovieModel.getVoteAverage().toString());
        votes.setText(mMovieModel.getVoteCount().toString());
        popularity.setText(String.format("%.1f", mMovieModel.getPopularity()));

        Glide.with(this)
                .load(mMovieModel.getPoster())
                .listener(GlidePalette.with(mMovieModel.getPoster())
                .use(GlidePalette.Profile.MUTED_DARK)
                .intoBackground(linearLayout))
                .into(poster);
        Glide.with(this).load(mMovieModel.getBackdropPath()).into(backdrop);

        CollapsingToolbar();


        if (savedInstanceState !=null)
        {
            movies=savedInstanceState.getParcelableArrayList(SAVE_STATE);

        }

    }





    //Show Toolbar and Hide Toolbar and Title on Scroll
    private void CollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBar);
        appBarLayout.setExpanded(true);




        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(mMovieModel.getTitle());
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVE_STATE,movies);
    }
}



