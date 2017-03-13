package com.example.nsamant.flicks.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.nsamant.activity.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class DetailsActivity extends YouTubeBaseActivity {

    private TextView tvMovieTitle;
    private TextView tvMovieReleaseDate;
    private TextView tvMovieOverview;
    private RatingBar ratingBar;

    private static final String YOUTUBE_API_KEY = "AIzaSyCPwm2m3lKJKwjIDxzivxBcVZZujTXDXgU";
    private String url = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tvMovieTitle = (TextView) findViewById(R.id.tvMovieTitle);
        tvMovieOverview = (TextView) findViewById(R.id.tvMovieOverview);
        tvMovieReleaseDate = (TextView) findViewById(R.id.tvReleaseDate);
        ratingBar = (RatingBar) findViewById(R.id.ivRatingsImage);

        tvMovieTitle.setText(getIntent().getStringExtra("movie_title").toString());
        tvMovieOverview.setText(getIntent().getStringExtra("movie_overview").toString());
        tvMovieReleaseDate.setText(getIntent().getStringExtra("release_date").toString());
        int vote = getIntent().getIntExtra("movie_vote", 0);
        ratingBar.setRating(vote/2);

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.player);
        int movieId = getIntent().getIntExtra("movie_id", 0);
        sendMovieDetailsNetworkRequest(movieId);
    }

    public void onSubmit(View v) {
        // closes the activity and returns to first screen
        this.finish();
    }

    private void sendMovieDetailsNetworkRequest(int movieId) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        url = String.format(url, movieId);
        Log.d("url", url);
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieDetails = null;
                try {
                    final String key = response.getJSONArray("results").getJSONObject(0).getString("key");
                    Log.d("DEBUG", key);

                    youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                            YouTubePlayer youTubePlayer, boolean b) {
                           youTubePlayer.cueVideo(key);
                        }

                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                            YouTubeInitializationResult youTubeInitializationResult) {

                        }
                    });

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
