package com.example.popularmovie;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView m_Poster = findViewById(R.id.image_iv);
        TextView m_Title = findViewById(R.id.movie_title);
        TextView m_Id = findViewById(R.id.movie_id);
        TextView m_Date = findViewById(R.id.movie_date);
        TextView m_Vote = findViewById(R.id.movie_vote);
        TextView m_Plot = findViewById(R.id.movie_plot);

        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra(getString(R.string.movie));

        Picasso.get().load(getString(R.string.picasso_base_url) + movie.getImage()).into(m_Poster);

        int id = movie.getId();
        m_Id.setText(getString(R.string.detail_id)+String.valueOf(id));

        String title = movie.getTitle();
        m_Title.setText(getString(R.string.detail_title)+title);

        String date = movie.getDate();
        m_Date.setText(getString(R.string.detail_release_date)+date);

        int vote = movie.getVote();
        m_Vote.setText(getString(R.string.detail_average_vote)+String.valueOf(vote));

        String plot = movie.getPlot();
        m_Plot.setText(getString(R.string.detail_plot)+plot);
    }
}
