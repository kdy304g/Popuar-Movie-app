package com.example.popularmovie.utilities;

import com.example.popularmovie.Movie;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    public static Movie parseMovieJson(JSONObject object) throws JSONException {
        String title = object.getString("original_title");
        String image = object.getString("poster_path");
        int id = object.getInt("id");
        int vote = object.getInt("vote_average");
        String plot = object.getString("overview");
        String date = object.getString("release_date");
        return new Movie(title, image, id, date, vote, plot);

    }
}
