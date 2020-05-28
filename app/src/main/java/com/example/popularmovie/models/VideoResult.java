package com.example.popularmovie.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class VideoResult implements Serializable {
    @SerializedName("results")
    private List<Video> videoResult;
    public VideoResult(List<Video> videoResult){
        this.videoResult = videoResult;
    }
    public List<Video> getVideoResult(){
        return videoResult;
    }
}
