package com.tarun.saini.popularmovies.TmdbApi;

import com.tarun.saini.popularmovies.Model.ExtrasResults;
import com.tarun.saini.popularmovies.Model.MovieModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface TmdbInterface
{

    @GET("movie/popular")
    Call<MovieModel> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieModel> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}/reviews")
    Call<ExtrasResults> getReviews(@Path("id")int id, @Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<ExtrasResults> getTrailerKey(@Path("id")int id,@Query("api_key") String apiKey);


}
