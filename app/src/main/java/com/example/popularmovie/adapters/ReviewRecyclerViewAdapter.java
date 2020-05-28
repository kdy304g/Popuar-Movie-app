package com.example.popularmovie.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovie.R;
import com.example.popularmovie.models.Review;

import java.util.List;

public class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewRecyclerViewAdapter.ViewHolder> {
    private ItemClickListener mClickListener;
    private LayoutInflater mInflater;
    private List<Review> reviews;

    public ReviewRecyclerViewAdapter(Context context, List<Review> mReviewList, ItemClickListener itemClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.reviews = mReviewList;
        this.mClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ReviewRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.review_item, parent, false);
        return new ViewHolder(view, mClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.reviewTextView.setText(reviews.get(position).getUrl());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView reviewTextView;
        ItemClickListener itemClickListener;

        public ViewHolder(View itemView, ItemClickListener itemClickListener){
            super(itemView);
            reviewTextView = itemView.findViewById(R.id.review_text);
            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickListener.onReviewItemClick(getAdapterPosition());
        }
    }
    public interface ItemClickListener {
        void onReviewItemClick(int position);
    }
}
