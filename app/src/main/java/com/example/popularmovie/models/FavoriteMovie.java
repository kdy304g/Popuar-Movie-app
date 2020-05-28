package com.example.popularmovie.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "favorites")
public class FavoriteMovie {
    @PrimaryKey
    public int id;
    public String image;
    public FavoriteMovie(int id, String image){
        this.id = id;
        this.image = image;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getImage(){
        return image;
    }
    public void setImage(String image){
        this.image = image;
    }
}
