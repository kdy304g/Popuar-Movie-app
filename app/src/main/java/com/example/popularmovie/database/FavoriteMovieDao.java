package com.example.popularmovie.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.popularmovie.models.FavoriteMovie;

import java.util.List;

@Dao
public interface FavoriteMovieDao {
    @Query("SELECT * FROM favorites")
    LiveData<List<FavoriteMovie>> getFavoriteMovies();
    @Query("SELECT * FROM favorites WHERE id = :id")
    LiveData<FavoriteMovie> getFavoriteMovie(int id);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addFavoriteMovie(FavoriteMovie favoriteMovie);
    @Query("DELETE From favorites WHERE id = :id")
    void deleteFavoriteMovie(int id);
}
