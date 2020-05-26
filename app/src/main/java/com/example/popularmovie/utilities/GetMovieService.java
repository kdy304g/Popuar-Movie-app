package com.example.popularmovie.utilities;

import com.example.popularmovie.models.MovieResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

@SuppressWarnings("ALL")
public interface GetMovieService {
    @GET("movie/popular")
    Call<MovieResult> getMovieResult(@Query("api_key") String apiKey);
}
