package com.example.popularmovie.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    final static String MOVIE_BASE_URL_POPULAR =
            "https://api.themoviedb.org/3/movie/popular";
    final static String MOVIE_BASE_URL_TOP =
            "https://api.themoviedb.org/3/movie/top_rated";

    final static String API_KEY = "api_key";
    final static String PARAM_LANG = "language";
    final static String PARAM_PAGE = "page";
    final static String page = "1";
    final static String lang = "en-US";
    final static String api = "your_api_key_here";

    public static URL buildUrl(boolean popular) {
        Uri builtUri;
        if (popular){
            builtUri = Uri.parse(MOVIE_BASE_URL_POPULAR).buildUpon()
                    .appendQueryParameter(API_KEY, api)
                    .appendQueryParameter(PARAM_LANG, lang)
                    .appendQueryParameter(PARAM_PAGE, page)
                    .build();
        }else{
            builtUri = Uri.parse(MOVIE_BASE_URL_TOP).buildUpon()
                    .appendQueryParameter(API_KEY, api)
                    .appendQueryParameter(PARAM_LANG, lang)
                    .appendQueryParameter(PARAM_PAGE, page)
                    .build();
        }

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
