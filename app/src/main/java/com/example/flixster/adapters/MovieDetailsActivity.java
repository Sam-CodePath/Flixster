package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.flixster.MovieTrailerActivity;
import com.example.flixster.R;
import com.example.flixster.databinding.ActivityMovieDetailsBinding;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieDetailsActivity extends AppCompatActivity {

    // the movie to display
    Movie movie;

    // the view objects
    TextView tvTitle;
    TextView tvOverview;
    TextView tvPopularity;
    RatingBar rbVoteAverage;
    ImageView ivLargeBG;
    ImageView ivPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // Hide the support action bar
        getSupportActionBar().hide();


        ActivityMovieDetailsBinding binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());

        // layout of activity is stored in a special property called root
        View view = binding.getRoot();
        setContentView(view);

        // resolve the view objects
        tvTitle = binding.tvTitle;
        tvOverview = binding.tvOverview;
        rbVoteAverage = binding.rbVoteAverage;
        ivLargeBG = binding.ivLargeBG;
        ivPoster = binding.ivPoster;
        tvPopularity = binding.tvPopularity;


        // unwrap the movie passed in via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        // set the title and overview
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        String popularityText = "Popularity: " + movie.getPopularity();
        tvPopularity.setText(popularityText);

        // vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);

        String imageUrl = movie.getBackdropPath();
        int placeHolderID = R.drawable.flicks_backdrop_placeholder;
        Glide.with(this).load(imageUrl).placeholder(placeHolderID).into(ivLargeBG);

        // ivPoster
        String posterUrl = movie.getPosterPath();
        int posterPlaceholderID = R.drawable.flicks_movie_placeholder;
        Glide.with(this).load(posterUrl).placeholder(posterPlaceholderID).into(ivPoster);

        // Click listener
        ivLargeBG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MovieTrailerActivity.class);
                i.putExtra("movieid", movie.getMovieID());
                startActivity(i);
                finish();
            }
        });


    }
}