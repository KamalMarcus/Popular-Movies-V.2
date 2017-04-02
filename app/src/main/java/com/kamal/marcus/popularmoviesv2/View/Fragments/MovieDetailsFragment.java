package com.kamal.marcus.popularmoviesv2.View.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kamal.marcus.popularmoviesv2.Controller.Adapters.ReviewsAdapter;
import com.kamal.marcus.popularmoviesv2.Controller.Adapters.TrailersAdapter;
import com.kamal.marcus.popularmoviesv2.Controller.Constants;
import com.kamal.marcus.popularmoviesv2.Models.Movie;
import com.kamal.marcus.popularmoviesv2.Models.Review;
import com.kamal.marcus.popularmoviesv2.Models.Trailer;
import com.kamal.marcus.popularmoviesv2.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.kamal.marcus.popularmoviesv2.Controller.Urls.API_KEY_PARAM;
import static com.kamal.marcus.popularmoviesv2.Controller.Urls.TRAILERS_AND_REVIEWS_BASE_URL;
import static com.kamal.marcus.popularmoviesv2.Controller.Utils.addMovieToFavourites;
import static com.kamal.marcus.popularmoviesv2.Controller.Utils.isLargeScreen;
import static com.kamal.marcus.popularmoviesv2.Controller.Utils.isMovieFavourite;
import static com.kamal.marcus.popularmoviesv2.Controller.Utils.removeMovieFromFavourites;


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
    private RecyclerView trailersRecyclerView;
    private ArrayList<Trailer> trailersList;
    private TrailersAdapter trailersAdapter;
    private TextView trailerTextView;
    private TextView reviewsTextView;
    private RecyclerView reviewsRecyclerView;
    private ArrayList<Review> reviewsList;
    private ReviewsAdapter reviewsAdapter;
    private ImageView imageFavourite;

    public static final String TAG = MovieDetailsFragment.class.getSimpleName();
    private ImageButton trailerShareButton;

    public MovieDetailsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trailersList = new ArrayList<Trailer>();
        reviewsList = new ArrayList<Review>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);

        Bundle data;
        if (isLargeScreen(getActivity())) {
            data = getArguments();
        } else {
            data = getActivity().getIntent().getExtras();
        }


        selectedMovie = data.getParcelable("selectedMovie");

        coverImageView = (ImageView) rootView.findViewById(R.id.cover_image_view);
        Picasso.with(getActivity()).load(selectedMovie.getCoverPhotoUrl()).placeholder(R.drawable.poster_profile_temp).into(coverImageView);

        titleTextView = (TextView) rootView.findViewById(R.id.movie_title);
        titleTextView.setText(selectedMovie.getTitle());

        genreTextView = (TextView) rootView.findViewById(R.id.genre_text_view);

        String genreText = selectedMovie.getGenreIds().toString().replace(",", " |").replace("[", "").replace("]", "");
        genreTextView.setText(genreText);

        imageFavourite = (ImageView) rootView.findViewById(R.id.favourite_image_view);
        if (isMovieFavourite(getActivity(), selectedMovie.getId())) {
            imageFavourite.setBackground(getResources().getDrawable(R.drawable.heart_favourite));
        }
        imageFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isMovieFavourite(getActivity(), selectedMovie.getId())) {
                    imageFavourite.setBackground(getResources().getDrawable(R.drawable.heart_favourite));
                    addMovieToFavourites(getActivity(), selectedMovie);
                } else {
                    imageFavourite.setBackground(getResources().getDrawable(R.drawable.heart_not_favourite));
                    removeMovieFromFavourites(getActivity(), selectedMovie.getId());
                }
            }
        });

        overViewTextView = (TextView) rootView.findViewById(R.id.overview_text_view);
        overViewTextView.setText(selectedMovie.getOverview());

        posterImageView = (ImageView) rootView.findViewById(R.id.movie_poster);
        Picasso.with(getActivity()).load(selectedMovie.getProfilePhotoUrl()).placeholder(R.drawable.poster_profile_temp).into(posterImageView);

        releaseDateTextView = (TextView) rootView.findViewById(R.id.release_date_text_view);
        releaseDateTextView.setText(selectedMovie.getReleaseDate());

        ratingTextView = (TextView) rootView.findViewById(R.id.rating_text_view);
        ratingTextView.setText(selectedMovie.getRating() + "/10");

        voteCountTextView = (TextView) rootView.findViewById(R.id.vote_count_text_view);
        voteCountTextView.setText(selectedMovie.getVoteCount());

        trailerShareButton = (ImageButton) rootView.findViewById(R.id.button_share_trailer);
        trailerTextView = (TextView) rootView.findViewById(R.id.trailers_text_view);
        trailersRecyclerView = (RecyclerView) rootView.findViewById(R.id.trailers_recycler_view);
        trailersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        trailersAdapter = new TrailersAdapter(trailersList);
        trailersRecyclerView.setAdapter(trailersAdapter);
        getTrailers(selectedMovie.getId());

        reviewsTextView = (TextView) rootView.findViewById(R.id.reviews_text_view);
        reviewsRecyclerView = (RecyclerView) rootView.findViewById(R.id.reviews_recycler_view);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        reviewsAdapter = new ReviewsAdapter(reviewsList);
        reviewsRecyclerView.setAdapter(reviewsAdapter);
        getReviews(selectedMovie.getId());


        return rootView;
    }

    private void getTrailers(String movieId) {
        Uri builtUri = Uri.parse(TRAILERS_AND_REVIEWS_BASE_URL + movieId + "/videos").buildUpon()
                .appendQueryParameter(API_KEY_PARAM, Constants.MY_API_KEY)
                .build();
        String trailerUrl = builtUri.toString();
        StringRequest trailersRequest = new StringRequest(Request.Method.GET, trailerUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject trailerJson = new JSONObject(response);
                    JSONArray trailerArray = trailerJson.getJSONArray("results");

                    for (int i = 0; i < trailerArray.length(); i++) {
                        JSONObject trailer = trailerArray.getJSONObject(i);
                        if (trailer.getString("site").contentEquals("YouTube")) {
                            trailersList.add(new Trailer(trailer.getString("id"), trailer.getString("key"), trailer.getString("name"), trailer.getString("site"), trailer.getString("type")));
                        }
                    }
                    if (trailersList.isEmpty()) {
                        trailerTextView.setText("No available trailers.");
                        trailerShareButton.setVisibility(View.GONE);
                    } else {
                        trailerShareButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_TEXT, "http://www.youtube.com/watch?v=" +trailersList.get(0).getKey());
                                sendIntent.setType("text/plain");
                                startActivity(sendIntent);
                            }
                        });
                    }
                    trailersAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(getActivity()).add(trailersRequest);
    }

    private void getReviews(String movieId) {
        Uri builtUri = Uri.parse(TRAILERS_AND_REVIEWS_BASE_URL + movieId + "/reviews").buildUpon()
                .appendQueryParameter(API_KEY_PARAM, Constants.MY_API_KEY)
                .build();
        String reviewUrl = builtUri.toString();
        StringRequest trailersRequest = new StringRequest(Request.Method.GET, reviewUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject reviewJson = new JSONObject(response);
                    JSONArray reviewArray = reviewJson.getJSONArray("results");

                    for (int i = 0; i < reviewArray.length(); i++) {
                        JSONObject review = reviewArray.getJSONObject(i);
                        reviewsList.add(new Review(review.getString("id"), review.getString("author"), review.getString("content")));
                    }

                    if (trailersList.isEmpty()) {
                        reviewsTextView.setText("No available reviews.");
                    }
                    reviewsAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(getActivity()).add(trailersRequest);
    }

}
