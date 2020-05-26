package com.example.popularmovie.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class MovieResult implements Serializable {
    @SerializedName("results")
    @Expose
    private ArrayList<Movie> movieResult;

    public MovieResult(ArrayList<Movie> movieResult) {
        this.movieResult = movieResult;
    }

    public ArrayList<Movie> getMovieResult() {
        return movieResult;
    }
}
