package com.example.flixter;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixter.adapters.MovieAdapter;
import com.example.flixter.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String NOW_PLAYING_URL =  "https://api.themoviedb.org/3/movie/now_playing?api_key=7f3946a3c8e821d8c229525297c5adff";
    public static final String TAG = "MainActivity"; // use to easily log data

    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvMovies = findViewById(R.id.rvMovies); // get reference to rv of movies
        movies = new ArrayList<>();

        // create an adapter
        final MovieAdapter movieAdapter = new MovieAdapter(this, movies);

        // set adapter on recycler view
        rvMovies.setAdapter(movieAdapter);

        // set layout manager on recycler view
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        // create new instance of http client
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() { // use json because that is the language the api uses
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) { // responses are json objects!
                Log.d(TAG, "onSuccess"); // log statements can help with debugging (alternatively add bp)
                JSONObject jsonObject = json.jsonObject; // get the json object from response
                try {
                    JSONArray results = jsonObject.getJSONArray("results"); // results are stored in a json array
                    Log.i(TAG, "Results: " + results.toString());
                    movies.addAll(Movie.fromJsonArray(results)); // modify movies NOT change them
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Movies: " + movies.size());
                } catch (JSONException e) {
                    Log.e(TAG, "Json hit exception", e);
                }

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }
}