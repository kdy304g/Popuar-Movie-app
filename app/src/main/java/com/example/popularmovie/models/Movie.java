package com.example.popularmovie.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity (tableName = "movies")
public class Movie implements Serializable {

    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    public int id;
    @SerializedName("title")
    public String title;
    @SerializedName("poster_path")
    public String image;
    @SerializedName("release_date")
    public String date;
    @SerializedName("vote_count")
    public int vote;
    @SerializedName("overview")
    public String plot;

    public Movie(int id, String title, String image, String date, int vote, String plot){
        this.id = id;
        this.title = title;
        this.image = image;
        this.date = date;
        this.vote = vote;
        this.plot = plot;
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
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getVote() {
        return vote;
    }
    public void setVote(int vote) {
        this.vote = vote;
    }
    public String getPlot(){
        return plot;
    }
    public void setPlot(String plot){
        this.plot = plot;
    }

    @NonNull
    @Override
    public String toString() {
        return title;
    }

}
