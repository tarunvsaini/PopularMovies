package com.tarun.saini.popularmovies.MoviesMain;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.tarun.saini.popularmovies.Data.MovieDbHelper;
import com.tarun.saini.popularmovies.Model.MovieModel;
import com.tarun.saini.popularmovies.R;

public class FavoriteMovieList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movie_list);


        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            mToolbar.setTitle(R.string.favoriteMovies);
        }
        setSupportActionBar(mToolbar);
        mToolbar.setFitsSystemWindows(true);

        SystemBarTintManager tintManager = new SystemBarTintManager(this);

        tintManager.setStatusBarTintEnabled(true);

        tintManager.setNavigationBarTintEnabled(true);

        tintManager.setTintColor(Color.parseColor("#20000000"));


        FloatingActionMenu floatingActionMenu = (FloatingActionMenu) findViewById(R.id.floating_action_menu);
        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.floating_action_menu_popular);
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.floating_action_menu_favorite);
        FloatingActionButton fab3 = (FloatingActionButton) findViewById(R.id.floating_action_menu_top_rated);

        fab1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),PopularMovieList.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(intent);

            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent =new Intent(getApplicationContext(),FavoriteMovieList.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(intent);



            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),TopMovieList.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(intent);

            }
        });



    }
}
