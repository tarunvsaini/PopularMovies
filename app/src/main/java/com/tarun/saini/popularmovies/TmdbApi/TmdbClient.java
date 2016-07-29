package com.tarun.saini.popularmovies.TmdbApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Tarun on 14-07-2016.
 */
public class TmdbClient {

    public static final String BASE_URL="http://api.themoviedb.org/3/";

    private static Retrofit retrofit=null;

    public static Retrofit getClient()
    {
       if(retrofit==null)
       {
           retrofit=new Retrofit.Builder()
                   .baseUrl(BASE_URL)
                   .addConverterFactory(GsonConverterFactory.create())
                   .build();
       }
        return retrofit;
    }


}
