package com.example.popularmovie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovie.adapters.FavoriteMovieRecyclerViewAdapter;
import com.example.popularmovie.adapters.MovieRecyclerViewAdapter;
import com.example.popularmovie.models.FavoriteMovie;
import com.example.popularmovie.models.Movie;
import com.example.popularmovie.models.MovieResult;
import com.example.popularmovie.viewModels.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieRecyclerViewAdapter.ItemClickListener, FavoriteMovieRecyclerViewAdapter.ItemClickListener{

    MovieRecyclerViewAdapter mMovieAdapter;
    FavoriteMovieRecyclerViewAdapter mFavoriteMovieAdapter;
    private List<Movie> mMovies = new ArrayList<>();
    private List<FavoriteMovie> mFavoriteMovies = new ArrayList<>();
    private RecyclerView mRecyclerView;
    MovieViewModel movieViewModel;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.rvMovies);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        if (isNetworkConnected()){
            populateUI();
        }else{
            Toast.makeText(getApplicationContext(), R.string.need_internet,Toast.LENGTH_SHORT).show();
        }

    }

    public void populateUI(){
        switch(pref.getString(getString(R.string.sort_by),"")){
            case "popular":
                getPopularMovies();
                break;
            case "top_rated":
                getTopRatedMovies();
                break;
            case "favorite":
                getFavoriteMovies();
                break;
            default:
                getPopularMovies();
                break;
        }
    }

    public void getPopularMovies(){
        movieViewModel.getPopularMoviesLiveData().observe(this, new Observer<MovieResult>() {
            @Override
            public void onChanged(MovieResult movieResult) {
                if (mMovieAdapter == null){
                    mMovieAdapter = new MovieRecyclerViewAdapter(MainActivity.this, mMovies, MainActivity.this);
                }
                mRecyclerView.setAdapter(mMovieAdapter);
                mMovies.clear();
                mMovies.addAll(movieResult.getMovieResult());
                mMovieAdapter.notifyDataSetChanged();
            }
        });
    }

    public void getTopRatedMovies(){
            movieViewModel.getTopRatedMoviesLiveData().observe(this, new Observer<MovieResult>() {
                @Override
                public void onChanged(MovieResult movieResult) {
                    if (mMovieAdapter == null){
                        mMovieAdapter = new MovieRecyclerViewAdapter(MainActivity.this, mMovies, MainActivity.this);
                    }
                    mRecyclerView.setAdapter(mMovieAdapter);
                    mMovies.clear();
                    mMovies.addAll(movieResult.getMovieResult());
                    mMovieAdapter.notifyDataSetChanged();
                }
            });
    }

    public void getFavoriteMovies(){
        movieViewModel.getFavoriteMoviesLiveData().observe(this, new Observer<List<FavoriteMovie>>() {
            @Override
            public void onChanged(List<FavoriteMovie> favoriteMovies) {
                if (mFavoriteMovieAdapter == null){
                    mFavoriteMovieAdapter = new FavoriteMovieRecyclerViewAdapter(MainActivity.this, mFavoriteMovies, MainActivity.this);
                }
                mRecyclerView.setAdapter(mFavoriteMovieAdapter);
                mFavoriteMovies.clear();
                mFavoriteMovies.addAll(favoriteMovies);
                mFavoriteMovieAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isNetworkConnected()){
            populateUI();
        }else{
            Toast.makeText(getApplicationContext(),R.string.need_internet,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(isNetworkConnected()){
            switch (item.getItemId()){
                case R.id.action_favorite:
                    getFavoriteMovies();
                    editor.putString(getString(R.string.sort_by), "favorite");
                    editor.commit();
                    return true;
                case R.id.action_popular:
                    getPopularMovies();
                    editor.putString(getString(R.string.sort_by), "popular");
                    editor.commit();
                    return true;
                case R.id.action_top:
                    getTopRatedMovies();
                    editor.putString(getString(R.string.sort_by),"top_rated");
                    editor.commit();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }else{
            Toast.makeText(getApplicationContext(),R.string.need_internet,Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    @Override
    public void onMovieItemClick(int position) {
        if (isNetworkConnected()){
            Movie m = mMovies.get(position);
            int id = m.getId();
            Intent i = new Intent(this, DetailActivity.class);
            i.putExtra("id", id);
            startActivity(i);
        }else{
            Toast.makeText(getApplicationContext(),R.string.need_internet,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFavoriteMovieItemClick(int position) {
        if (isNetworkConnected()){
            FavoriteMovie m = mFavoriteMovies.get(position);
            int id = m.getId();
            Intent i = new Intent(this, DetailActivity.class);
            i.putExtra("id", id);
            startActivity(i);
        }else{
            Toast.makeText(getApplicationContext(),R.string.need_internet,Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
