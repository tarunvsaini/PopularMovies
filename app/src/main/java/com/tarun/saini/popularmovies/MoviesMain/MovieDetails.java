package com.tarun.saini.popularmovies.MoviesMain;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.tarun.saini.popularmovies.Model.MovieModel;
import com.tarun.saini.popularmovies.R;

import static com.tarun.saini.popularmovies.MoviesMain.PopularMovieList.PANES;

public class MovieDetails extends AppCompatActivity {

    public static final String DETAILS ="details" ;
    private static final String SAVE_STATE = "DetailFragment";
    private MovieModel mMovieModel;
    private Fragment movieDetailsFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);


        mMovieModel=getIntent().getParcelableExtra(DETAILS);
        boolean mTwoPane=getIntent().getBooleanExtra(PANES,false);

        if (savedInstanceState==null)
        {

            movieDetailsFragment =new Fragment_MovieDetails();
            Bundle bundle = new Bundle();
            bundle.putBoolean(PANES, mTwoPane);
            movieDetailsFragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.movie_detail_container, movieDetailsFragment);
            transaction.commit();

        }else
            {

                if(movieDetailsFragment!=null && movieDetailsFragment.isAdded())
                {
                    movieDetailsFragment =  getSupportFragmentManager().getFragment(savedInstanceState,SAVE_STATE);


                }


            }



}

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (movieDetailsFragment!=null && movieDetailsFragment.isAdded())
        {
            getSupportFragmentManager().putFragment(outState,SAVE_STATE,movieDetailsFragment);

        }

    }




}









