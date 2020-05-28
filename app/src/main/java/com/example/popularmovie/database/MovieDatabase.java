package com.example.popularmovie.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.popularmovie.models.FavoriteMovie;
import com.example.popularmovie.models.Movie;

@Database(entities = { Movie.class, FavoriteMovie.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    public abstract MovieDao getMovieDao();
    public abstract FavoriteMovieDao getFavoriteMovieDao();

    private static MovieDatabase movieDB;

    public static MovieDatabase getInstance(Context context) {
        if (null == movieDB) {
            movieDB = buildDatabaseInstance(context);
        }
        return movieDB;
    }
    public static MovieDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context, MovieDatabase.class, "movies").allowMainThreadQueries().build();
    }
    public void cleanUp(){
        movieDB = null;
    }
}
