package com.tarun.saini.popularmovies.MoviesMain;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.florent37.glidepalette.GlidePalette;
import com.tarun.saini.popularmovies.Data.MovieContract;
import com.tarun.saini.popularmovies.Model.ExtrasModel;
import com.tarun.saini.popularmovies.Model.ExtrasResults;
import com.tarun.saini.popularmovies.Model.MovieModel;
import com.tarun.saini.popularmovies.MovieAdapter.ReviewAdapter;
import com.tarun.saini.popularmovies.R;
import com.tarun.saini.popularmovies.TmdbApi.TmdbClient;
import com.tarun.saini.popularmovies.TmdbApi.TmdbInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
import static com.tarun.saini.popularmovies.MoviesMain.PopularMovieList.MOVIE_MODEL;
import static com.tarun.saini.popularmovies.MoviesMain.PopularMovieList.PANES;
import static com.tarun.saini.popularmovies.MoviesMain.PopularMovieList.POSITION;

public class Fragment_MovieDetails extends Fragment {

    private static final String SAVE_STATE = "saveState";
    private static final String SAVE_EXTRAS = "saveExtras";
    private static final String SAVE_URL = "trailerUrl";
    private ArrayList<MovieModel> movieList = new ArrayList<>();
    public static final String DETAILS = "details";
    private MovieModel mMovieModel;
    private CheckBox favBox;
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private ImageButton youtube;
    ArrayList<ExtrasModel> listItemReview;
    private ArrayList<ExtrasModel> trailer;
    private CollapsingToolbarLayout collapsingToolbar;
    private String TRAILER_URL;
    private final static String API_KEY = "de89bc307e69043208119480f9f2a159";
    private ImageView backdrop, poster;
    private TextView title, release, language, desc, ratings, votes, empty_review, popularity;
    private LinearLayout linearLayout;
    int movieId;
    private Boolean mTwoPane;
    private ArrayList<MovieModel> modelList;
    public static final String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185/";
    public static final String BASE_BACKDROP_URL = "http://image.tmdb.org/t/p/w500/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);




        backdrop = (ImageView) rootView.findViewById(R.id.backdrop);
        poster = (ImageView) rootView.findViewById(R.id.poster);
        title = (TextView) rootView.findViewById(R.id.name);
        release = (TextView) rootView.findViewById(R.id.release);
        language = (TextView) rootView.findViewById(R.id.language);
        desc = (TextView) rootView.findViewById(R.id.desc);
        ratings = (TextView) rootView.findViewById(R.id.ratings);
        votes = (TextView) rootView.findViewById(R.id.votes);
        empty_review = (TextView) rootView.findViewById(R.id.empty_review);
        youtube = (ImageButton) rootView.findViewById(R.id.youTube);
        youtube.setVisibility(View.INVISIBLE);
        favBox = (CheckBox) rootView.findViewById(R.id.fav_box);
        coordinatorLayout = (CoordinatorLayout) rootView.findViewById(R.id.coordinatorLayout);
        popularity = (TextView) rootView.findViewById(R.id.popularity);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.linearLayout);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.review_recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String Message = getString(R.string.shareMessage);

                shareIntent(Message);

                return true;
            }
        });


        favBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((favBox.isChecked())) //check for condition if movie in database
                {

                    addMovie(mMovieModel);
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Added to Favourite Movies", Snackbar.LENGTH_LONG);
                    // favBox.setButtonDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_favorite_red_500_18dp));

                    snackbar.show();

                } else {
                    deleteMovie(mMovieModel.getId());
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Removed From Favourite Movies", Snackbar.LENGTH_LONG);
                    // favBox.setButtonDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_favorite_border_red_500_18dp));

                    snackbar.show();

                }

            }
        });




        //Collapsing Toolbar

        collapsingToolbar =
                (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) rootView.findViewById(R.id.appBar);
        appBarLayout.setExpanded(true);


        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (!mTwoPane) {
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

            }
        });

        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent youtube = new Intent(Intent.ACTION_VIEW, Uri.parse(TRAILER_URL));
                startActivity(youtube);

            }
        });


        //Show different layouts for tablet and phone

        mTwoPane = getArguments().getBoolean(PANES);

        if (mTwoPane) {
            tabView();
            toolbar.setVisibility(View.GONE);


        } else {
            phoneView();

        }

        if ((checkMovieId(mMovieModel.getId()))) {

            favBox.setChecked(true);
        } else {
            favBox.setChecked(false);

        }





        if (savedInstanceState == null)
        {

            //Retrofit Call for Movie Reviews and youtubeVideo
            getExtras();

        }
        else
        {
            movieList = savedInstanceState.getParcelableArrayList(SAVE_STATE);
            listItemReview = savedInstanceState.getParcelableArrayList(SAVE_EXTRAS);
            trailer = savedInstanceState.getParcelableArrayList(SAVE_URL);
            TRAILER_URL=savedInstanceState.getString(SAVE_URL);
            if (listItemReview!=null)
            {
                recyclerView.setAdapter(new ReviewAdapter(listItemReview, getContext()));
                if (listItemReview.size() == 0) {
                    recyclerView.setVisibility(View.INVISIBLE);
                    empty_review.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    empty_review.setVisibility(View.INVISIBLE);
                }

            }
            if(trailer!=null)
            {
                TRAILER_URL = trailer.get(0).getKeyUrl();
                youtube.setVisibility(View.VISIBLE);

            }


        }

        return rootView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }




    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVE_EXTRAS, listItemReview);
        outState.putParcelableArrayList(SAVE_STATE, movieList);
        outState.putParcelableArrayList(SAVE_URL,trailer);


    }


    //Share Movie URL

    public Intent shareIntent(String Message) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        if (TRAILER_URL != null) {
            share.putExtra(android.content.Intent.EXTRA_TEXT, Message + " \n" + TRAILER_URL);
            startActivity(share);
        } else {
            Toast.makeText(getActivity(), R.string.no_trailer, Toast.LENGTH_SHORT).show();
        }
        return share;


    }



    public void getExtras()
    {
        TmdbInterface apiService =
                TmdbClient.getClient().create(TmdbInterface.class);

        Call<ExtrasResults> call = apiService.getReviews(movieId, API_KEY);

        call.enqueue(new Callback<ExtrasResults>() {
            @Override
            public void onResponse(Call<ExtrasResults> call, Response<ExtrasResults> response) {
                listItemReview = response.body().getResults();
                recyclerView.setAdapter(new ReviewAdapter(listItemReview, getContext()));


                if (listItemReview.size() == 0) {
                    recyclerView.setVisibility(View.INVISIBLE);
                    empty_review.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    empty_review.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onFailure(Call<ExtrasResults> call, Throwable t) {

                Log.d("REVIEW CALL", t + " ");


            }
        });


        //Retrofit Call for Movie Trailer

        Call<ExtrasResults> call2 = apiService.getTrailerKey(movieId, API_KEY);
        call2.enqueue(new Callback<ExtrasResults>() {
            @Override
            public void onResponse(Call<ExtrasResults> call, Response<ExtrasResults> response) {
                trailer = response.body().getResults();

                if (trailer.size() == 0) {
                    Toast.makeText(getActivity(), "Not Trailer Available for this Movie", Toast.LENGTH_SHORT).show();

                } else {
                    TRAILER_URL = trailer.get(0).getKeyUrl();
                    youtube.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onFailure(Call<ExtrasResults> call, Throwable t) {

            }
        });
    }

    public void phoneView() {
        mMovieModel = getActivity().getIntent().getParcelableExtra(DETAILS);
        getMovieDetails();
    }

    public void tabView() {

        modelList = getArguments().getParcelableArrayList(MOVIE_MODEL);
        int position = getArguments().getInt(POSITION);
        mMovieModel=modelList.get(position);
        getMovieDetails();
    }


    private void getMovieDetails()
    {
        title.setText(mMovieModel.getTitle());
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(mMovieModel.getReleaseDate());
            String formattedDate = new SimpleDateFormat("dd MMM yyyy").format(date);
            release.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        language.setText( mMovieModel.getOriginalLanguage());
        desc.setText(mMovieModel.getOverview());
        ratings.setText(String.valueOf(mMovieModel.getVoteAverage()));
        votes.setText(String.valueOf(mMovieModel.getVoteCount()));
        popularity.setText(String.format("%.1f", mMovieModel.getPopularity()));

        Glide.with(this)
                .load(BASE_POSTER_URL + mMovieModel.getPoster())
                .listener(GlidePalette.with(mMovieModel.getPoster())
                        .use(GlidePalette.Profile.VIBRANT_DARK)
                        .intoBackground(linearLayout))
                .into(poster);
        Glide.with(this).load(BASE_BACKDROP_URL + mMovieModel.getBackdropPath()).into(backdrop);
        movieId = mMovieModel.getId();
    }


    //add favorite movie to database

    public void addMovie(MovieModel movie) {


        ContentValues values = new ContentValues();
        values.put(KEY_ID, movie.getId());
        values.put(KEY_TITLE, movie.getTitle());
        values.put(KEY_RATING, movie.getVoteAverage());
        values.put(KEY_DATE, movie.getReleaseDate());
        values.put(KEY_OVERVIEW, movie.getOverview());
        values.put(KEY_BACKDROP, movie.getBackdropPath());
        values.put(KEY_VOTE_COUNT, movie.getVoteCount());
        values.put(KEY_LANGUAGE, movie.getOriginalLanguage());
        values.put(KEY_POPULARITY, movie.getPopularity());
        values.put(KEY_POSTER, movie.getPoster());

        getActivity().getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, values);


    }
    //deleting movie from database

    public void deleteMovie(int id) {

        Uri mCurrentUri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, id);
        getActivity().getContentResolver().delete(mCurrentUri, null, null);
    }


    //check if movie is already in database

    public boolean checkMovieId(int id) {

        String[] projection = {KEY_ID};
        String selection = KEY_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        Cursor cursor = getActivity().getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, projection, selection, selectionArgs, null);
        assert cursor != null;
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;

    }


}
