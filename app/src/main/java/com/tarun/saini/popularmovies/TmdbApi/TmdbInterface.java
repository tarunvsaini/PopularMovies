package com.tarun.saini.popularmovies.TmdbApi;

import com.tarun.saini.popularmovies.Model.MovieModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Tarun on 17-07-2016.
 */
public interface TmdbInterface {

    @GET("movie/popular")
    Call<MovieModel> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieModel> getTopRatedMovies(@Query("api_key") String apiKey);


}
