package com.example.popularmovie.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Movie implements Serializable {

    @SerializedName("title")
    public String title;
    @SerializedName("id")
    public int id;

    public Movie(int id, String title){
        this.id = id;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
