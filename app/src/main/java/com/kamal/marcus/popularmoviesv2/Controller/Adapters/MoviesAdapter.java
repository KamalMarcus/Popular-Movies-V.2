package com.kamal.marcus.popularmoviesv2.Controller.Adapters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kamal.marcus.popularmoviesv2.Models.Movie;
import com.kamal.marcus.popularmoviesv2.View.Activities.MovieDetailsActivity;
import com.kamal.marcus.popularmoviesv2.R;
import com.kamal.marcus.popularmoviesv2.Controller.Urls;
import com.kamal.marcus.popularmoviesv2.View.Fragments.MovieDetailsFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.kamal.marcus.popularmoviesv2.Controller.Utils.isLargeScreen;

/**
 * Created by KiMoo on 04/03/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesHolder> {
    ArrayList<Movie> moviesList;

    public MoviesAdapter(ArrayList<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MoviesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        MoviesHolder holder = new MoviesHolder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MoviesHolder holder, final int position) {
        final Movie movie = moviesList.get(position);
        Picasso.with(holder.moviePoster.getContext()).load(movie.getProfilePhotoUrl()).placeholder(R.drawable.poster_profile_temp).into(holder.moviePoster);
        holder.movieTitle.setText(movie.getTitle());
        holder.movieTitle.setSelected(true);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Replace genre IDs with their corresponding values
                ArrayList<String> genreValues=new ArrayList<String>();
                for(String id:movie.getGenreIds()){
                    id= Urls.genres.get(id);
                    genreValues.add(id);
                }
                movie.setGenreIds(genreValues);

                if(isLargeScreen(holder.itemView.getContext())){
                    Bundle arguments = new Bundle();
                    arguments.putParcelable("selectedMovie", movie);

                    MovieDetailsFragment fragment = new MovieDetailsFragment();
                    fragment.setArguments(arguments);

                    ((FragmentActivity)holder.itemView.getContext()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.movie_details_container, fragment, MovieDetailsFragment.TAG)
                            .commit();
                }

                else {
                    Intent intent=new Intent(holder.itemView.getContext(),MovieDetailsActivity.class);
                    intent.putExtra("selectedMovie",movie);
                    holder.itemView.getContext().startActivity(intent);
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    class MoviesHolder extends RecyclerView.ViewHolder {
        TextView movieTitle;
        ImageView moviePoster;

        public MoviesHolder(View itemView) {
            super(itemView);
            moviePoster = (ImageView) itemView.findViewById(R.id.movie_poster);
            movieTitle = (TextView) itemView.findViewById(R.id.movie_title);
        }
    }
}
