package com.example.popularmovie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovie.adapters.MovieRecyclerViewAdapter;
import com.example.popularmovie.models.Movie;
import com.example.popularmovie.models.MovieResult;
import com.example.popularmovie.viewModels.MovieViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieRecyclerViewAdapter.ItemClickListener{

    MovieRecyclerViewAdapter mMovieAdapter;
    private ArrayList<Movie> mMovies;
    private RecyclerView mRecyclerView;
    MovieViewModel movieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.rvMovies);
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
//        Log.d("viewmodel", String.valueOf(movieViewModel));
        movieViewModel.getMoviesLiveData().observe(this, new Observer<MovieResult>() {
            @Override
            public void onChanged(MovieResult movieResult) {
                Log.d("movieResult", String.valueOf(movieResult));
            }
        });
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        startActivity(intent);
    }
}
