package com.example.popularmovie.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Video implements Serializable {
    @SerializedName("key")
    public String key;
    @SerializedName("site")
    public String site;
    @SerializedName("name")
    public String name;
    public Video(String key, String site, String name){
        this.key = key;
        this.site = site;
        this.name = name;
    }
    public String getKey(){
        return key;
    }
    public void setKey(String key){
        this.key = key;
    }
    public String getSite(){
        return site;
    }
    public void setSite(String site){
        this.site = site;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
}
