package com.example.flixter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.flixter.models.Movie;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {

    TextView tvTitle;
    TextView tvOverview;
    RatingBar ratingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = findViewById(R.id.tvTitleDetail);
        tvOverview = findViewById(R.id.tvOverViewDetail);
        ratingBar = findViewById(R.id.ratingBar);

        // passed title separately to at least show the title in case of slow internet
        String title = getIntent().getStringExtra("title");
        // passed all the movie class
        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));

        tvTitle.setText(title);
        tvOverview.setText(movie.getOverview());
        ratingBar.setRating((float) movie.getRating()); //setRating needs a float. double is more precise, so we need to downcast it to float.ğ
    }
}