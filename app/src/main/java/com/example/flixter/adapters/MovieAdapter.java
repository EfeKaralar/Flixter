package com.example.flixter.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixter.DetailActivity;
import com.example.flixter.MainActivity;
import com.example.flixter.R;
import com.example.flixter.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

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

        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        ImageView ivPlay;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverView);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            ivPlay = itemView.findViewById(R.id.ivPlay);
            container = itemView.findViewById(R.id.container);

        }

        public void bind(Movie movie) {
            // get orientation
            int orientation = context.getResources().getConfiguration().orientation;

            // check the orientation to
            String ImagePath;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                ImagePath = movie.getPosterPath();
            }
            else{ImagePath = movie.getBackdropPath();}

            // bind the related views
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            // For Glide Transformations
            int radius = 30; // corner radius, higher value = more rounded
            int margin = 10; // crop margin, set to 0 for corners with no crop
            Glide.with(context)
                    .load(ImagePath)
                    .placeholder(R.drawable.ic_baseline_sync_24)
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .into(ivPoster);
            if(movie.getRating()<5.5) { ivPlay.setVisibility(View.INVISIBLE); } // set play button invisible

            // 1. Register click listener on the whole row
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 2. Navigate to a new activity
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("title", movie.getTitle());
                    i.putExtra("movie", Parcels.wrap(movie));
                    // initialize pairs of view and transition name
                    Pair<View, String> p1 = Pair.create((View) tvTitle, "title");
                    Pair<View, String> p2 = Pair.create((View) tvOverview, "overview");
                    // pass the pairs to the makeSceneTransitionAnimation method.
                    // then, add options.toBundle() as the 2nd parameter to context.startActivity
                    ActivityOptionsCompat options = ActivityOptionsCompat
                            .makeSceneTransitionAnimation((Activity) context, p1, p2);
                    context.startActivity(i, options.toBundle());
                }
            });
        }
    }
}
