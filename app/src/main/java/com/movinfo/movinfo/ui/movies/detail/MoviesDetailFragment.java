package com.movinfo.movinfo.ui.movies.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.movinfo.movinfo.BuildConfig;
import com.movinfo.movinfo.R;
import com.movinfo.movinfo.data.network.models.Movie;
import com.movinfo.movinfo.utils.Constants;
import com.squareup.picasso.Picasso;

/**
 * @author eddy.
 */

public class MoviesDetailFragment extends Fragment {
    private static final String TAG = MoviesDetailFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = requireActivity().getIntent();

        Movie movie = null;
        if (intent != null) {
            movie = (Movie) intent.getSerializableExtra(MoviesDetailActivity.MOVIE_INTENT_EXTRA);
        }

        View v = inflater.inflate(R.layout.movies_detail_fragment, container, false);

        //Bind UI

        TextView movieTitleTextView = v.findViewById(R.id.movieDetailsTitleTextView);
        TextView movieSynopsisTextView = v.findViewById(R.id.movieSynopsisTextView);
        TextView releaseDateTextView = v.findViewById(R.id.releaseDateTextView);
        TextView voteAverageTextView = v.findViewById(R.id.voteAverageTextView);

        ImageView moviePosterImageView = v.findViewById(R.id.movieDetailsPosterImageView);
        RatingBar voteAverageBar = v.findViewById(R.id.movieDetailsRatingBar);

        if (movie != null) {
            Log.d(TAG, "Showing movie: " + movie);
            // Display poster
            String posterPath =
                    Constants.MOVIE_DB_POSTER_URL + Constants.MOVIES_DETAILS_POSTER_RESOLUTION
                            + movie.getPosterPath()
                            + "?api_key=" + BuildConfig.TheMovieDbApiToken;
            Picasso.get().load(posterPath)
                    .placeholder(R.drawable.ic_image_black_24dp)
                    .error(R.drawable.ic_broken_image_black_24dp)
                    .into(moviePosterImageView);

            // Title
            movieSynopsisTextView.setText(movie.getOverview());

            // Rating
            float rating = 5 * (movie.getVoteAverage() / 10);
            voteAverageBar.setRating(rating);

            // Vote average
            voteAverageTextView.setText(String.valueOf(movie.getVoteAverage()));


            // Release date
            releaseDateTextView.setText(movie.getFormattedDate());

            if (actionBar != null) {
                actionBar.setTitle(movie.getTitle());
                movieTitleTextView.setText(movie.getTitle());
            }
        } else {
            Log.w(TAG, "Movie in Details screen is null");
        }

        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) {
            // Return to the movies list
            NavUtils.navigateUpFromSameTask(requireActivity());
        }

        return super.onOptionsItemSelected(item);
    }
}
