package com.example.popularmovie.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.popularmovie.models.Movie;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> getMovies();

    @Query("SELECT * FROM movies WHERE id = :id")
    LiveData<Movie> getMovie(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(List<Movie> movies);

    @Query("DELETE FROM movies")
    void deleteMovies();

    @Update
    void updateMovie(Movie movie);
}
