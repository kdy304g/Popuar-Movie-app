package com.example.popularmovie.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovie.R;
import com.example.popularmovie.models.Video;

import java.util.List;

public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoRecyclerViewAdapter.ViewHolder> {
    private ItemClickListener mClickListener;
    private LayoutInflater mInflater;
    private List<Video> videos;

    public VideoRecyclerViewAdapter(Context context, List<Video> mVideoList, ItemClickListener itemClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.videos = mVideoList;
        this.mClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public VideoRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.video_item, parent, false);
        return new ViewHolder(view, mClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.videoTextView.setText(videos.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView videoTextView;
        ItemClickListener itemClickListener;

        public ViewHolder(View itemView, ItemClickListener itemClickListener){
            super(itemView);
            videoTextView = itemView.findViewById(R.id.video_text);
            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickListener.onVideoItemClick(getAdapterPosition());
        }
    }
    public interface ItemClickListener {
        void onVideoItemClick(int position);
    }
}
