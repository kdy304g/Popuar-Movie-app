package com.example.popularmovie;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private String title;
    private String image;
    private int id;
    private String date;
    private int vote;
    private String plot;

    public Movie(Parcel in){
        title = in.readString();
        image = in.readString();
        id = in.readInt();
        date = in.readString();
        vote = in.readInt();
        plot =in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(image);
        dest.writeInt(id);
        dest.writeString(date);
        dest.writeInt(vote);
        dest.writeString(plot);
    }

    public Movie(String title, String image, int id, String date, int vote, String plot){
        this.title = title;
        this.image = image;
        this.id = id;
        this.date = date;
        this.vote = vote;
        this.plot = plot;
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getTitle(){
        return title;
    }
    public String getImage(){
        return image;
    }
    public int getId(){
        return id;
    }
    public String getDate(){
        return date;
    }
    public String getPlot(){
        return plot;
    }
    public int getVote(){
        return vote;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

}
