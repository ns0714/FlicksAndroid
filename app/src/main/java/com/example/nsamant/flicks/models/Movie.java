package com.example.nsamant.flicks.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nsamant on 3/6/17.
 */

public class Movie {

    private String movieTitle;
    private String posterPath;
    private String overview;
    private String backdropPath;
    private String releaseDate;
    private int votes;

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public void setBackdropPath(String backdrop) {
        this.backdropPath = backdrop;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getVotes() {
        return votes;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getOverview() {
        return overview;
    }

    public Movie(JSONObject jsonObject) throws JSONException {
        this.movieTitle = jsonObject.getString("title");
        this.posterPath =   jsonObject.getString("poster_path");
        this.overview = jsonObject.getString("overview");
        this.votes = jsonObject.getInt("vote_average");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.releaseDate = jsonObject.getString("release_date");
    }

    public static ArrayList<Movie> fromJsonArray(JSONArray array) {
        ArrayList<Movie> results = new ArrayList<>();

        for(int i=0; i< array.length(); i++) {
            try {
                results.add(new Movie(array.getJSONObject(i)));
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        return results;
    }
}
