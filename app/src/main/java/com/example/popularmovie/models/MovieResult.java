package com.example.popularmovie.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MovieResult implements Serializable {
    @SerializedName("results")
    @Expose
    private List<Movie> movieResult;

    public MovieResult(List<Movie> movieResult) {
        this.movieResult = movieResult;
    }

    public List<Movie> getMovieResult() {
        return movieResult;
    }
}
