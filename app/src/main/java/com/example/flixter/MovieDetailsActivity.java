package com.example.flixter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.flixter.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {

    Movie movie; // the movie to display

    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    TextView tvVotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //set info
        tvTitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvOverview);
        rbVoteAverage = findViewById(R.id.rbVoteAverage);
        tvVotes = findViewById(R.id.tvVotes);

        // unwrap movie and assign field
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));

        // set title and overview
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());

        // set rating
        float rating = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(rating = rating > 0 ? rating / 2.0f : rating);

        // set the number of votes
        int votes = movie.getVoteCount();
        String votesMessage = String.valueOf(rating) + "/5 stars on " + String.valueOf(votes) + " reviews";
        tvVotes.setText(votesMessage);




    }
}