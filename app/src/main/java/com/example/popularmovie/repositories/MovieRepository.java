package com.example.popularmovie.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.popularmovie.database.FavoriteMovieDao;
import com.example.popularmovie.database.MovieDao;
import com.example.popularmovie.database.MovieDatabase;
import com.example.popularmovie.models.FavoriteMovie;
import com.example.popularmovie.models.MovieResult;
import com.example.popularmovie.utilities.GetMovieService;
import com.example.popularmovie.utilities.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    private static MovieRepository movieRepository;
    @NonNull
    private MutableLiveData<MovieResult> movieData = new MutableLiveData<>();
    private MovieDao mMovieDao;
    private FavoriteMovieDao mFavoriteMovieDao;

    public static MovieRepository getInstance(Application application){
        if (movieRepository == null){
            movieRepository = new MovieRepository(application);
        }
        return movieRepository;
    }

    private GetMovieService service;

    public MovieRepository(Application application){
        MovieDatabase db = MovieDatabase.getInstance(application);
        mMovieDao = db.getMovieDao();
        mFavoriteMovieDao = db.getFavoriteMovieDao();
        service = RetrofitInstance.getRetrofitInstance().create(GetMovieService.class);
    }

    @NonNull
    public LiveData<MovieResult> getPopularMovies(String api){
        service.getPopularMovieResult(api).enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                if (response.isSuccessful()){
                    movieData.setValue(response.body());
                    new insertAsyncTask(mMovieDao).execute(response.body());
                }
            }
            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                movieData.setValue(null);
            }
        });
        return movieData;
    }
    @NonNull
    public LiveData<MovieResult> getTopRatedMovies(String api){
        service.getTopRatedMovieResult(api).enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                if (response.isSuccessful()){
                    movieData.setValue(response.body());
                    new insertAsyncTask(mMovieDao).execute(response.body());
                }
            }
            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                movieData.setValue(null);
            }
        });
        return movieData;
    }

    public LiveData<List<FavoriteMovie>> getFavoriteMovies(){
        return mFavoriteMovieDao.getFavoriteMovies();
    }

    private static class insertAsyncTask extends AsyncTask<MovieResult, Void, Void>{
        private MovieDao mAsyncTaskDao;
        insertAsyncTask(MovieDao dao){
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(MovieResult... movieResults) {
            mAsyncTaskDao.insertMovies(movieResults[0].getMovieResult());
            return null;
        }
    }
}
