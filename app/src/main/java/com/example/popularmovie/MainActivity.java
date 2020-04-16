package com.example.popularmovie;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_popular) {
            MainActivityFragment fragment = (MainActivityFragment) getSupportFragmentManager().getFragments().get(0);
            fragment.makeMovieSearchQuery(true);
            return true;
        }
        if (itemThatWasClickedId == R.id.action_top){
            MainActivityFragment fragment = (MainActivityFragment) getSupportFragmentManager().getFragments().get(0);
            fragment.makeMovieSearchQuery(false);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
