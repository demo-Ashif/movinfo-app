package com.movinfo.movinfo.ui.movies.list.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.movinfo.movinfo.BuildConfig;
import com.movinfo.movinfo.R;
import com.movinfo.movinfo.data.network.models.Movie;
import com.movinfo.movinfo.ui.movies.detail.MoviesDetailActivity;
import com.movinfo.movinfo.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView adapter to manage the movies list
 */

public class MoviesListAdapter extends
        RecyclerView.Adapter<MoviesListAdapter.MoviesListViewHolder> {
    private List<Movie> mMovies = new ArrayList<>();
    private Context mContext;

    public MoviesListAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public MoviesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
            int viewType) {
        View movieListItemView = LayoutInflater.from(mContext).inflate(R.layout.movie_list_item,
                parent, false);
        return new MoviesListViewHolder(movieListItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesListViewHolder holder,
            int position) {
        holder.bind(mMovies.get(position));
    }

    @Override
    public int getItemCount() {
        int noOfMovies = 0;

        if (mMovies != null) {
            noOfMovies = mMovies.size();
        }

        return noOfMovies;
    }

    public List<Movie> getMovies() {
        return mMovies;
    }

    public void setMovies(List<Movie> movies) {
        mMovies.addAll(movies);
    }

    public void resetMoviesList() {
        mMovies.clear();
        notifyDataSetChanged();
    }

    class MoviesListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        MoviesListViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
        }

        void bind(Movie movie) {
            // Bind UI with data

            // Poster
            ImageView moviePosterImageView = itemView.findViewById(
                    R.id.movieDetailsPosterImageView);
            String posterPath =
                    Constants.MOVIE_DB_POSTER_URL + Constants.MOVIES_LIST_POSTER_RESOLUTION
                            + movie.getPosterPath()
                            + "?api_key=" + BuildConfig.TheMovieDbApiToken;
            Picasso.get().load(posterPath)
                    .placeholder(R.drawable.ic_image_black_24dp)
                    .error(R.drawable.ic_broken_image_black_24dp)
                    .into(moviePosterImageView);

            // Title
            TextView movieTitleTextView = itemView.findViewById(R.id.movieTitleTextView);
            movieTitleTextView.setText(movie.getTitle());

            // Rating
            RatingBar movieVoteAverageBar = itemView.findViewById(R.id.movieRatingBar);
            float rating = 5 * (movie.getVoteAverage() / 10);
            movieVoteAverageBar.setRating(rating);

            // Vote Average
            TextView voteAverageTextView = itemView.findViewById(R.id.movieVoteAverageTextView);
            voteAverageTextView.setText(String.format("%s", movie.getVoteAverage()));
        }

        private void openMovieDetails(Movie movie) {
            Intent intent = new Intent(mContext, MoviesDetailActivity.class);
            intent.putExtra(MoviesDetailActivity.MOVIE_INTENT_EXTRA, movie);

            mContext.startActivity(intent);
        }

        @Override
        public void onClick(View view) {
            int moviePositionClicked = getAdapterPosition();
            openMovieDetails(mMovies.get(moviePositionClicked));
        }
    }
}
