package com.example.popularmovie;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.popularmovie.utilities.JsonUtils;
import com.example.popularmovie.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivityFragment extends Fragment {

    private ArrayList<Movie> movies = new ArrayList<Movie>();
    private  GridView gridView;
    private MovieAdapter movieAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        makeMovieSearchQuery(true);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        gridView = (GridView) rootView.findViewById(R.id.movies_grid);

        return rootView;
    }

    public boolean isOnline(){
        Runtime runtime = Runtime.getRuntime();
        try{
            Process ipProcess = runtime.exec(getString(R.string.ping_internet));
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void makeMovieSearchQuery(boolean popular) {
        URL movieSearchUrl = NetworkUtils.buildUrl(popular);
        new MovieQueryTask().execute(movieSearchUrl);
    }

    public class MovieQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String movieSearchResults = null;
            if (isOnline()){
                try {
                    movieSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return movieSearchResults;
        }

        @Override
        protected void onPostExecute(String movieSearchResults) {
            if (movieSearchResults != null && !movieSearchResults.equals("")) {
                try {
                    JSONObject movieObject = new JSONObject(movieSearchResults);
                    JSONArray results = movieObject.getJSONArray(getString(R.string.movie_results));
                    movies.clear();
                    for(int i=0;i<results.length();i++){
                        JSONObject item = results.getJSONObject(i);
                        Movie movie = JsonUtils.parseMovieJson(item);
                        movies.add(movie);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (movieAdapter == null){
                    movieAdapter = new MovieAdapter(getActivity(), movies);
                }else{
                    movieAdapter.notifyDataSetChanged();
                }
                gridView.setAdapter(movieAdapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View v,
                                            int position, long id) {
                        Intent intent = new Intent(MainActivityFragment.this.getActivity(), DetailActivity.class);
                        intent.putExtra(getString(R.string.movie), movies.get(position));
                        startActivity(intent);

                    }
                });
            }else {
                Toast.makeText(MainActivityFragment.this.getActivity(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
