package com.example.popularmovie.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovie.R;
import com.example.popularmovie.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {

    private List<Movie> movies;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private String posterBASEURL = "https://image.tmdb.org/t/p/w500/";

    public MovieRecyclerViewAdapter(Context context, List<Movie> movies, ItemClickListener itemClickListener){
        this.mInflater = LayoutInflater.from(context);
        this.movies = movies;
        this.mClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MovieRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(view, mClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieRecyclerViewAdapter.ViewHolder holder, int position) {
        Picasso.get().load(posterBASEURL+movies.get(position).getImage()).into(holder.movieImageView);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView movieImageView;
        ItemClickListener itemClickListener;

        public ViewHolder(View itemView, ItemClickListener itemClickListener){
            super(itemView);
            movieImageView = itemView.findViewById(R.id.movie_image);
            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickListener.onMovieItemClick(getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onMovieItemClick(int position);
    }
}
