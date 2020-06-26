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
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixter.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;

public class MovieDetailsActivity extends AppCompatActivity {

    Movie movie; // the movie to display

    public static final String MOVIE_ID = "movie_id";

    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    TextView tvVotes;
    ImageView ivBackdrop;
    String movieID;
    Integer id;


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
        id = movie.getId();

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
                String MOVIE_URL = "https://api.themoviedb.org/3/movie/" + id.toString() + "/videos?api_key=7f3946a3c8e821d8c229525297c5adff";
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(MOVIE_URL, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.d("MovieDetailsActivity", "onSuccess");
                        JSONObject jsonObject = json.jsonObject;

                        try {
                            JSONArray results = jsonObject.getJSONArray("results");

                            if (results.length() > 0) {
                                JSONObject firstVideo = results.getJSONObject(0);
                                movieID = firstVideo.getString("key");
                            }
                            else { movieID = "";}
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // use intent to go to new activity if a video for it exists
                        if (!movieID.equals("")) {
                            Intent intent = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);
                            intent.putExtra(MOVIE_ID, movieID);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "No Trailer Available", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.d("MovieDetailsActivity", "onFailure");
                    }
                });



            }
        });






    }
}