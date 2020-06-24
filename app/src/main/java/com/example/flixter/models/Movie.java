package com.example.flixter.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// plain old java object
public class Movie {
    String posterPath;
    String title;
    String overview;

    public Movie(JSONObject jsonObject) throws JSONException { // constructor(type)
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
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

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
}
