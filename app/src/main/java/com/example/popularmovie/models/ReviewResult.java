package com.example.popularmovie.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ReviewResult implements Serializable {
    @SerializedName("results")
    @Expose
    private List<Review> reviewResult;

    public ReviewResult(List<Review> reviewResult){
        this.reviewResult = reviewResult;
    }

    public List<Review> getReviewResult(){
        return reviewResult;
    }
}
