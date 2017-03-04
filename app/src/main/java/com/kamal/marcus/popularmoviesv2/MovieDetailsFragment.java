package com.kamal.marcus.popularmoviesv2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailsFragment extends Fragment {


    private ImageView coverImageView;
    private Movie selectedMovie;
    private TextView titleTextView;
    private TextView genreTextView;
    private TextView overViewTextView;
    private ImageView posterImageView;
    private TextView releaseDateTextView;
    private TextView ratingTextView;
    private TextView voteCountTextView;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);

        if(getActivity().getIntent().getParcelableExtra("selectedMovie")!=null){
            selectedMovie=getActivity().getIntent().getParcelableExtra("selectedMovie");
        }

        coverImageView= (ImageView) rootView.findViewById(R.id.cover_image_view);
        Picasso.with(getActivity()).load(selectedMovie.getCoverPhotoUrl()).placeholder(R.drawable.poster_profile_temp).into(coverImageView);

        titleTextView= (TextView) rootView.findViewById(R.id.movie_title);
        titleTextView.setText(selectedMovie.getTitle());

        genreTextView= (TextView) rootView.findViewById(R.id.genre_text_view);
        for(String s:selectedMovie.getGenreIds()){
            System.out.println(s);
            System.out.println(selectedMovie.getGenreIds());
        }
        String genreText=selectedMovie.getGenreIds().toString().replace(","," |").replace("[","").replace("]","");
        genreTextView.setText(genreText);

        overViewTextView= (TextView) rootView.findViewById(R.id.overview_text_view);
        overViewTextView.setText(selectedMovie.getOverview());

        posterImageView= (ImageView) rootView.findViewById(R.id.movie_poster);
        Picasso.with(getActivity()).load(selectedMovie.getProfilePhotoUrl()).placeholder(R.drawable.poster_profile_temp).into(posterImageView);

        releaseDateTextView= (TextView) rootView.findViewById(R.id.release_date_text_view);
        releaseDateTextView.setText(selectedMovie.getReleaseDate());

        ratingTextView= (TextView) rootView.findViewById(R.id.rating_text_view);
        ratingTextView.setText(selectedMovie.getRating()+"/10");

        voteCountTextView= (TextView) rootView.findViewById(R.id.vote_count_text_view);
        voteCountTextView.setText(selectedMovie.getVoteCount());


        return rootView;
    }

}
