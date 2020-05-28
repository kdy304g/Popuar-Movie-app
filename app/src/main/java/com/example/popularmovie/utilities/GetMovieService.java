package com.example.popularmovie.utilities;

import com.example.popularmovie.models.MovieResult;
import com.example.popularmovie.models.ReviewResult;
import com.example.popularmovie.models.VideoResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

@SuppressWarnings("ALL")
public interface GetMovieService {
    @GET("popular")
    Call<MovieResult> getPopularMovieResult(@Query("api_key") String api_key);
    @GET("top_rated")
    Call<MovieResult> getTopRatedMovieResult(@Query("api_key") String api_key);
    @GET("{id}/reviews")
    Call<ReviewResult> getReviewResult(@Path("id") int id, @Query("api_key") String api_key);
    @GET("{id}/videos")
    Call<VideoResult> getVideoResult(@Path("id") int id, @Query("api_key") String api_key);
}
