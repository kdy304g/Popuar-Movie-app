package com.example.popularmovie.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.popularmovie.models.MovieResult;
import com.example.popularmovie.repositories.MovieRepository;

public class MovieViewModel extends AndroidViewModel {

    private MutableLiveData<MovieResult> movieLiveData;
    private MovieRepository movieRepository;

    public MovieViewModel(@NonNull Application application){
        super(application);
    }

    public void init(){
        if (movieLiveData != null){
            return;
        }
        movieRepository = MovieRepository.getInstance();
        movieLiveData = movieRepository.getMovies("api_key_here");
    }

    public LiveData<MovieResult> getMoviesLiveData() {
        init();
        return movieLiveData;
    }
}
