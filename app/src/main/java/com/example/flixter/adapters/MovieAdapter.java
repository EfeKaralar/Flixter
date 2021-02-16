package com.example.flixter.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixter.R;
import com.example.flixter.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    String TAG = "MovieAdapter";
    Context context;
    List<Movie> movies;


    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Usually involves inflating a layout from XML and returning the holder.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: " );
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // Involves populating data into the item through the holder.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + position);
        // get movie at passes in position
            Movie movie = movies.get(position);
        // bind movie into the ViewHolder
        holder.bind(movie);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        TextView tvTitleLS;
        TextView tvOverviewLS;
        ImageView ivBackdrop;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverView);
            ivPoster = itemView.findViewById(R.id.ivPoster);

            tvTitleLS = itemView.findViewById(R.id.tvTitleLS);
            tvOverviewLS = itemView.findViewById(R.id.tvOverViewLS);
            ivBackdrop = itemView.findViewById(R.id.ivBackdrop);
        }

        public void bind(Movie movie) {
            // get orientation
            int orientation = context.getResources().getConfiguration().orientation;
            // bind the related views
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                tvTitle.setText(movie.getTitle());
                tvOverview.setText(movie.getOverview());
                Glide.with(context)
                        .load(movie.getPosterPath())
                        .placeholder(R.drawable.ic_baseline_sync_24)
                        .into(ivPoster);
            }
            else if (orientation == Configuration.ORIENTATION_LANDSCAPE){
                tvTitleLS.setText(movie.getTitle());
                tvOverviewLS.setText(movie.getOverview());
                Glide.with(context).load(movie.getBackdropPath()).placeholder(R.drawable.ic_baseline_sync_24).into(ivBackdrop);
            }
        }
    }
}
