package com.example.flixter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.flixter.models.Movie;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieDetailsActivity extends AppCompatActivity {

    Movie movie; // the movie to display

    public static final String MOVIE_ID = "movie_id";

    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    TextView tvVotes;
    ImageView ivBackdrop;
    String movieID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //set info
        tvTitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvOverview);
        rbVoteAverage = findViewById(R.id.rbVoteAverage);
        tvVotes = findViewById(R.id.tvVotes);
        ivBackdrop = findViewById(R.id.ivBackdrop);


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

        // display backdrop image
        String imageURL = movie.getBackdropPath();
        Glide.with(this).load(imageURL).into(ivBackdrop);

        // if image tapped, go to movie trailer
        ivBackdrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movieID = movie.getMovieId();
                // use intent to go to new activity if a video for it exists
                if (!movieID.equals("")) {
                    Intent intent = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);
                    intent.putExtra(MOVIE_ID, movieID);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "No Trailer Available", Toast.LENGTH_SHORT).show();
                }

            }
        });






    }
}