package com.example.popularmovie.utilities;

import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;

public class RetrofitInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";

    public static Retrofit getRetrofitInstance(){
        if(retrofit == null){
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
