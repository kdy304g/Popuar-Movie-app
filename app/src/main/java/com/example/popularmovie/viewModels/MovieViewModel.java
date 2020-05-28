package com.example.popularmovie.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.popularmovie.models.FavoriteMovie;
import com.example.popularmovie.models.MovieResult;
import com.example.popularmovie.repositories.MovieRepository;
import com.example.popularmovie.utilities.Constants;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository movieRepository;

    public MovieViewModel(@NonNull Application application){
        super(application);
        movieRepository = MovieRepository.getInstance(application);
    }

    public LiveData<MovieResult> getPopularMoviesLiveData() {
        return movieRepository.getPopularMovies(Constants.api_key);
    }
    public LiveData<MovieResult> getTopRatedMoviesLiveData(){
        return movieRepository.getTopRatedMovies(Constants.api_key);
    }
    public LiveData<List<FavoriteMovie>> getFavoriteMoviesLiveData(){
        return movieRepository.getFavoriteMovies();
    }
}
