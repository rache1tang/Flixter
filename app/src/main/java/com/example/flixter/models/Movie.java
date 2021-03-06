package com.example.flixter.models;

import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

// plain old java object
@Parcel
public class Movie {
    String posterPath;
    String title;
    String overview;
    String backdropPath;
    int voteCount;
    Integer id;
    String key;

    double voteAverage;

    public Movie() {}

    public Movie(JSONObject jsonObject) throws JSONException { // constructor(type)
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        backdropPath = jsonObject.getString("backdrop_path");
        voteAverage = jsonObject.getDouble("vote_average");
        voteCount = jsonObject.getInt("vote_count");
        id = jsonObject.getInt("id");
    }

    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException { // method(function)
        List<Movie> movies = new ArrayList<>(); // to store the list of movies
        for (int i = 0; i < movieJsonArray.length(); i++) {
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getPosterPath() { // poster path is just relative, we want full url
        /** what you SHOULD do
         * make a configurations API request to get all available image sizes
         * append all image sizes
         * then append the relative url
         */
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath); // size hardcoded to be w342
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w780/%s", backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public Number getVoteAverage() { return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public Integer getId() { return id; }
}
