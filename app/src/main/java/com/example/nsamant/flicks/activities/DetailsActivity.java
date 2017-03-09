package com.example.nsamant.flicks.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.nsamant.activity.R;

public class DetailsActivity extends AppCompatActivity {

    private TextView tvMovieTitle;
    private TextView tvMovieReleaseDate;
    private TextView tvMovieOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tvMovieTitle = (TextView) findViewById(R.id.tvMovieTitle);
        tvMovieOverview = (TextView) findViewById(R.id.tvMovieOverview);
        tvMovieReleaseDate = (TextView) findViewById(R.id.tvReleaseDate);

        tvMovieTitle.setText(getIntent().getStringExtra("movie_title").toString());
        tvMovieOverview.setText(getIntent().getStringExtra("movie_overview").toString());
        tvMovieReleaseDate.setText(getIntent().getStringExtra("release_date").toString());
    }

    public void onSubmit(View v) {
        // closes the activity and returns to first screen
        this.finish();
    }
}
