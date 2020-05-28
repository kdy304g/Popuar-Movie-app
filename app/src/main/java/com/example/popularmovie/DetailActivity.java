package com.example.popularmovie;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovie.adapters.ReviewRecyclerViewAdapter;
import com.example.popularmovie.adapters.VideoRecyclerViewAdapter;
import com.example.popularmovie.models.FavoriteMovie;
import com.example.popularmovie.models.Movie;
import com.example.popularmovie.models.Review;
import com.example.popularmovie.models.ReviewResult;
import com.example.popularmovie.models.Video;
import com.example.popularmovie.models.VideoResult;
import com.example.popularmovie.viewModels.MovieDetailViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements ReviewRecyclerViewAdapter.ItemClickListener, VideoRecyclerViewAdapter.ItemClickListener{

    ReviewRecyclerViewAdapter mReviewAdapter;
    VideoRecyclerViewAdapter mVideoAdapter;
    private Movie mMovie;
    private List<Review> mReviewList = new ArrayList<>();
    private List<Video> mVideoList = new ArrayList<>();
    private MovieDetailViewModel movieDetailViewModel;
    private ImageView m_Poster;
    private TextView m_Title;
    private TextView m_Date;
    private TextView m_Vote;
    private TextView m_Plot;
    private ToggleButton toggle_button;
    private RecyclerView mReviewRecyclerView;
    private RecyclerView mVideoRecyclerView;
    private String YOUTUBEBASE = "http://www.youtube.com/watch?v=";
    private String YOUTUBEAPPBASE = "vnd.youtube:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mReviewRecyclerView = findViewById(R.id.rvReviews);
        mVideoRecyclerView = findViewById(R.id.rvVideos);
        m_Poster = findViewById(R.id.image_iv);
        m_Title = findViewById(R.id.movie_title);
        m_Date = findViewById(R.id.movie_date);
        m_Vote = findViewById(R.id.movie_vote);
        m_Plot = findViewById(R.id.movie_plot);
        toggle_button = findViewById(R.id.simpleToggleButton);
        toggle_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("toggle","clicked");
                if(toggle_button.isChecked()){
                    movieDetailViewModel.addFavoriteMovie(mMovie.getId(), mMovie.getImage());
                    toggle_button.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_yellow_24dp));
                    Toast.makeText(getApplicationContext(),"Added to favorite!",Toast.LENGTH_SHORT).show();
                }else{
                    movieDetailViewModel.deleteFavoriteMovie(mMovie.getId());
                    toggle_button.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_black_24dp));
                    Toast.makeText(getApplicationContext(),"Deleted from favorite!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        movieDetailViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
        Intent i = getIntent();
        int id = i.getIntExtra("id",0);
        movieDetailViewModel.getFavoriteMovie(id).observe(this, new Observer<FavoriteMovie>() {
            @Override
            public void onChanged(FavoriteMovie favoriteMovie) {
                if(favoriteMovie == null){
                    toggle_button.setChecked(false);
                    toggle_button.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_black_24dp));
                }else{
                    toggle_button.setChecked(true);
                    toggle_button.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_yellow_24dp));
                }
            }
        });
        movieDetailViewModel.getMovieLiveData(id).observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                mMovie = movie;
                populateUI();
            }
        });
        movieDetailViewModel.getReviewsLiveData(id).observe(this, new Observer<ReviewResult>() {
            @Override
            public void onChanged(ReviewResult reviewResult) {
                mReviewList.clear();
                mReviewList.addAll(reviewResult.getReviewResult());
                mReviewAdapter.notifyDataSetChanged();
            }
        });
        movieDetailViewModel.getVideosLiveData(id).observe(this, new Observer<VideoResult>() {
            @Override
            public void onChanged(VideoResult videoResult) {
                mVideoList.clear();
                mVideoList.addAll(videoResult.getVideoResult());
                mVideoAdapter.notifyDataSetChanged();
            }
        });
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        if (mReviewAdapter == null) {
            mReviewAdapter = new ReviewRecyclerViewAdapter(DetailActivity.this, mReviewList, DetailActivity.this);
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
            mReviewRecyclerView.setLayoutManager(mLinearLayoutManager);
            mReviewRecyclerView.setAdapter(mReviewAdapter);
            DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(mReviewRecyclerView.getContext(),
                    mLinearLayoutManager.getOrientation());
            mReviewRecyclerView.addItemDecoration(mDividerItemDecoration);
        } else {
            mReviewAdapter.notifyDataSetChanged();
        }
        if (mVideoAdapter == null) {
            mVideoAdapter = new VideoRecyclerViewAdapter(DetailActivity.this, mVideoList, DetailActivity.this);
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
            mVideoRecyclerView.setLayoutManager(mLinearLayoutManager);
            mVideoRecyclerView.setAdapter(mVideoAdapter);
            DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(mVideoRecyclerView.getContext(),
                    mLinearLayoutManager.getOrientation());
            mVideoRecyclerView.addItemDecoration(mDividerItemDecoration);
        } else {
            mVideoAdapter.notifyDataSetChanged();
        }
    }

    private void populateUI(){
        Picasso.get().load(getString(R.string.picasso_base_url) + mMovie.getImage()).into(m_Poster);

        String title = mMovie.getTitle();
        m_Title.setText(title);

        String date = mMovie.getDate();
        m_Date.setText(date);

        int vote = mMovie.getVote();
        m_Vote.setText(getString(R.string.detail_average_vote)+String.valueOf(vote));

        String plot = mMovie.getPlot();
        m_Plot.setText(plot);
    }

    @Override
    public void onReviewItemClick(int position) {
        Intent reviewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mReviewList.get(position).getUrl()));
        startActivity(reviewIntent);
    }

    @Override
    public void onVideoItemClick(int position) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBEAPPBASE + mVideoList.get(position).getKey()));
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBEBASE + mVideoList.get(position).getKey()));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }
}
