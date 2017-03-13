package com.example.nsamant.flicks.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nsamant.activity.R;
import com.example.nsamant.flicks.activities.DetailsActivity;
import com.example.nsamant.flicks.activities.QuickPlayerActivity;
import com.example.nsamant.flicks.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.example.nsamant.activity.R.id.ivMovieImage;
import static com.example.nsamant.activity.R.id.ivPlayIcon;
import static com.example.nsamant.activity.R.id.tvMovieOverview;
import static com.example.nsamant.activity.R.id.tvMovieTitle;

/**
 * Created by nsamant on 3/6/17.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {

    private int NON_POPULAR_MOVIE = 0;
    private int POPULAR_MOVIE = 1;
    private int height;
    private int width;

    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        super(context, 0, movies);
    }

    @Override
    public int getItemViewType(int position) {
        if(getItem(position).getVotes() > 5) {
            return POPULAR_MOVIE;
        } else {
            return NON_POPULAR_MOVIE;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int type = getItemViewType(position);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        if (type == POPULAR_MOVIE) {
            PopularMovieViewHolder viewHolder;
            if (convertView == null) {
                convertView = getInflatedLayoutForType(type);
                viewHolder = new PopularMovieViewHolder();
                viewHolder.ivMovieImage = (ImageView) convertView.findViewById(ivMovieImage);
                viewHolder.ivPlayIcon = (ImageView) convertView.findViewById(ivPlayIcon);
                bindView(position, viewHolder);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (PopularMovieViewHolder) convertView.getTag();
                bindView(position,viewHolder);
            }
        } else if (type == NON_POPULAR_MOVIE) {
            NonPopularMovieViewHolder viewHolder;
            if (convertView == null) {
                convertView = getInflatedLayoutForType(type);
                viewHolder = new NonPopularMovieViewHolder();
                viewHolder.tvMovieTitle = (TextView) convertView.findViewById(tvMovieTitle);
                viewHolder.tvMovieOverview = (TextView) convertView.findViewById(tvMovieOverview);
                viewHolder.ivMovieImage = (ImageView) convertView.findViewById(ivMovieImage);
                bindView(position, viewHolder);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (NonPopularMovieViewHolder) convertView.getTag();
                bindView(position, viewHolder);
            }
        }

        return convertView;
    }

    private void bindView(int position, ViewHolder viewHolder) {
        final Movie movie = getItem(position);
        int orientation = getContext().getResources().getConfiguration().orientation;
        if(viewHolder instanceof PopularMovieViewHolder) {
            loadImageBasedOnOrientation(movie, orientation, ((PopularMovieViewHolder) viewHolder).ivMovieImage, true);

            ((PopularMovieViewHolder) viewHolder).ivPlayIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), QuickPlayerActivity.class);
                    intent.putExtra("movie_id", movie.getMovieId());
                    getContext().startActivity(intent);
                }
            });

        } else if(viewHolder instanceof NonPopularMovieViewHolder) {
            ((NonPopularMovieViewHolder) viewHolder).tvMovieOverview.setText(movie.getOverview());
            ((NonPopularMovieViewHolder) viewHolder).tvMovieTitle.setText(movie.getMovieTitle());
            loadImageBasedOnOrientation(movie, orientation, ((NonPopularMovieViewHolder) viewHolder).ivMovieImage, false);

            ((NonPopularMovieViewHolder) viewHolder).ivMovieImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), DetailsActivity.class);
                    intent.putExtra("movie_title", movie.getMovieTitle());
                    intent.putExtra("release_date", movie.getReleaseDate());
                    intent.putExtra("movie_overview", movie.getOverview());
                    intent.putExtra("movie_id", movie.getMovieId());
                    getContext().startActivity(intent);
                }
            });
        }
    }

    private void loadImageBasedOnOrientation(Movie movie, int orientation, ImageView imageView, boolean isMoviePopular) {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            if(isMoviePopular) {
                Picasso.with(getContext()).load(movie.getBackdropPath())
                        .transform(new RoundedCornersTransformation(10, 10))
                        .resize(width, height)
                        .centerInside()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder).
                        into(imageView);
            } else {
                Picasso.with(getContext()).load(movie.getPosterPath())
                        .transform(new RoundedCornersTransformation(10, 10))
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder).
                        into(imageView);
            }
        } else if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if(isMoviePopular) {
                Picasso.with(getContext()).load(movie.getBackdropPath())
                        .transform(new RoundedCornersTransformation(10, 10))
                        .resize(width, height)
                        .centerInside()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder).
                        into(imageView);
            } else {
                Picasso.with(getContext()).load(movie.getPosterPath())
                        .transform(new RoundedCornersTransformation(10, 10))
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder).
                        into(imageView);
            }
        }
    }


    private View getInflatedLayoutForType(int type) {
        if (type == POPULAR_MOVIE) {
            return LayoutInflater.from(getContext()).inflate(R.layout.popular_movie, null);
        } else if (type == NON_POPULAR_MOVIE) {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_movie, null);
        } else {
            return null;
        }
    }

    public class ViewHolder {

    }

    public class PopularMovieViewHolder extends ViewHolder{
        ImageView ivMovieImage;
        ImageView ivPlayIcon;
    }

    public class NonPopularMovieViewHolder extends ViewHolder{
        ImageView ivMovieImage;
        TextView tvMovieTitle;
        TextView tvMovieOverview;
    }
}
