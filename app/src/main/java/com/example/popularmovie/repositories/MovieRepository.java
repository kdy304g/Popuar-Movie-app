package com.example.popularmovie.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.popularmovie.models.MovieResult;
import com.example.popularmovie.utilities.GetMovieService;
import com.example.popularmovie.utilities.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    private static MovieRepository movieRepository;

    public static MovieRepository getInstance(){
        if (movieRepository == null){
            movieRepository = new MovieRepository();
        }
        return movieRepository;
    }

    private GetMovieService service;

    public MovieRepository(){
        service = RetrofitInstance.getRetrofitInstance().create(GetMovieService.class);
    }

    public MutableLiveData<MovieResult> getMovies(String api){
        Log.d("getmovies","called");
        final MutableLiveData<MovieResult> movieData = new MutableLiveData<>();
        service.getMovieResult(api).enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                if (response.isSuccessful()){
                    movieData.setValue(response.body());
                    Log.d("debug", String.valueOf(movieData));
                }
            }
            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                movieData.setValue(null);
                Log.d("onfailure","why");
            }
        });
        return movieData;
    }
}
