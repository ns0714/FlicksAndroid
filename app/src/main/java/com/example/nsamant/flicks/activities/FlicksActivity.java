package com.example.nsamant.flicks.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.nsamant.activity.R;
import com.example.nsamant.flicks.adapters.MovieAdapter;
import com.example.nsamant.flicks.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class FlicksActivity extends AppCompatActivity {

    private ListView lvMovies;
    private ArrayList<Movie> moviesList;
    private ArrayAdapter<Movie> movieArrayAdapter;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flicks);

        lvMovies = (ListView) findViewById(R.id.lvMovies);
        moviesList = new ArrayList<Movie>();
        movieArrayAdapter = new MovieAdapter(this, moviesList);
        lvMovies.setAdapter(movieArrayAdapter);
        sendNetworkRequest();
    }

    public void sendNetworkRequest() {
        url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        //params.put("key", "1232");
        client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieResults = null;

                try {
                    movieResults = response.getJSONArray("results");
                    moviesList.addAll(Movie.fromJsonArray(movieResults));
                    movieArrayAdapter.notifyDataSetChanged();
                    Log.d("DEBUG", moviesList.toString());
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
