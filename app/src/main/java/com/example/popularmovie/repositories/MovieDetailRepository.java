package com.example.popularmovie.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.popularmovie.database.FavoriteMovieDao;
import com.example.popularmovie.database.MovieDao;
import com.example.popularmovie.database.MovieDatabase;
import com.example.popularmovie.models.FavoriteMovie;
import com.example.popularmovie.models.Movie;
import com.example.popularmovie.models.ReviewResult;
import com.example.popularmovie.models.VideoResult;
import com.example.popularmovie.utilities.GetMovieService;
import com.example.popularmovie.utilities.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailRepository {
    private static MovieDetailRepository movieDetailRepository;
    private MutableLiveData<ReviewResult> reviewResultData = new MutableLiveData<>();
    private MutableLiveData<VideoResult> videoResultData = new MutableLiveData<>();
    private MovieDao mMovieDao;
    private FavoriteMovieDao mFavoriteMovieDao;
    private GetMovieService service;

    public MovieDetailRepository(Application application) {
        MovieDatabase db = MovieDatabase.getInstance(application);
        mMovieDao = db.getMovieDao();
        mFavoriteMovieDao = db.getFavoriteMovieDao();
        service = RetrofitInstance.getRetrofitInstance().create(GetMovieService.class);
    }

    public static MovieDetailRepository getInstance(Application application){
        if (movieDetailRepository == null){
            movieDetailRepository = new MovieDetailRepository(application);
        }
        return movieDetailRepository;
    }

    public LiveData<Movie> getMovie(int id){
        return mMovieDao.getMovie(id);
    }
    public LiveData<ReviewResult> getReviews(int id, String api){
        service.getReviewResult(id, api).enqueue(new Callback<ReviewResult>() {
            @Override
            public void onResponse(Call<ReviewResult> call, Response<ReviewResult> response) {
                if (response.isSuccessful()){
                    reviewResultData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<ReviewResult> call, Throwable t) {
                reviewResultData.setValue(null);
            }
        });
        return reviewResultData;
    }
    public LiveData<VideoResult> getVideos(int id, String api){
        service.getVideoResult(id, api).enqueue(new Callback<VideoResult>() {
            @Override
            public void onResponse(Call<VideoResult> call, Response<VideoResult> response) {
                if (response.isSuccessful()){
                    videoResultData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<VideoResult> call, Throwable t) {
                videoResultData.setValue(null);
            }
        });
        return videoResultData;
    }
    public void addFavoirte(int id, String image){
        new insertAsyncTask(mFavoriteMovieDao).execute(new FavoriteMovie(id, image));
    }
    public void deleteFavorite(int id){
        new deleteAyncTask(mFavoriteMovieDao).execute(id);
    }
    public LiveData<FavoriteMovie> getFavorite(int id){
        return mFavoriteMovieDao.getFavoriteMovie(id);
    }

    private static class insertAsyncTask extends AsyncTask<FavoriteMovie, Void, Void> {
        private FavoriteMovieDao mAsyncTaskDao;
        insertAsyncTask(FavoriteMovieDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(FavoriteMovie... favoriteMovies) {
            mAsyncTaskDao.addFavoriteMovie(favoriteMovies[0]);
            return null;
        }
    }
    private static class deleteAyncTask extends AsyncTask<Integer , Void, Void>{
        private FavoriteMovieDao mAsyncTaskDao;
        deleteAyncTask(FavoriteMovieDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            mAsyncTaskDao.deleteFavoriteMovie(integers[0]);
            return null;
        }
    }
}
