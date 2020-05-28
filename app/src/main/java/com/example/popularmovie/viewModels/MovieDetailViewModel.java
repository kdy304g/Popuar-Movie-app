package com.example.popularmovie.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.popularmovie.models.FavoriteMovie;
import com.example.popularmovie.models.Movie;
import com.example.popularmovie.models.ReviewResult;
import com.example.popularmovie.models.VideoResult;
import com.example.popularmovie.repositories.MovieDetailRepository;
import com.example.popularmovie.utilities.Constants;

public class MovieDetailViewModel extends AndroidViewModel {
    private MovieDetailRepository movieDetailRepository;
    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
        movieDetailRepository = MovieDetailRepository.getInstance(application);
    }
    public LiveData<Movie> getMovieLiveData(int id) {
        return movieDetailRepository.getMovie(id);
    }
    public LiveData<ReviewResult> getReviewsLiveData(int id){
        return movieDetailRepository.getReviews(id, Constants.api_key);
    }
    public LiveData<VideoResult> getVideosLiveData(int id){
        return movieDetailRepository.getVideos(id, Constants.api_key);
    }
    public void addFavoriteMovie(int id, String image){
        movieDetailRepository.addFavoirte(id, image);
    }
    public void deleteFavoriteMovie(int id){
        movieDetailRepository.deleteFavorite(id);
    }
    public LiveData<FavoriteMovie> getFavoriteMovie(int id){
        return movieDetailRepository.getFavorite(id);
    }
}
