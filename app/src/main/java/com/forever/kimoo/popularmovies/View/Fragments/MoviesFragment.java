package com.forever.kimoo.popularmovies.View.Fragments;


import android.content.res.Configuration;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.forever.kimoo.popularmovies.Controller.Adapters.MoviesAdapter;
import com.forever.kimoo.popularmovies.Controller.Constants;
import com.forever.kimoo.popularmovies.Controller.ContentProvider.MovieContract;
import com.forever.kimoo.popularmovies.Controller.Urls;
import com.forever.kimoo.popularmovies.Models.Movie;
import com.forever.kimoo.popularmovies.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.forever.kimoo.popularmovies.Controller.ContentProvider.MovieContract.MovieEntry.GENRE_IDS;
import static com.forever.kimoo.popularmovies.Controller.ContentProvider.MovieContract.MovieEntry.MOVIE_DATE;
import static com.forever.kimoo.popularmovies.Controller.ContentProvider.MovieContract.MovieEntry.MOVIE_ID;
import static com.forever.kimoo.popularmovies.Controller.ContentProvider.MovieContract.MovieEntry.MOVIE_IMAGE;
import static com.forever.kimoo.popularmovies.Controller.ContentProvider.MovieContract.MovieEntry.MOVIE_NAME;
import static com.forever.kimoo.popularmovies.Controller.ContentProvider.MovieContract.MovieEntry.MOVIE_OVERVIEW;
import static com.forever.kimoo.popularmovies.Controller.ContentProvider.MovieContract.MovieEntry.MOVIE_POSTER;
import static com.forever.kimoo.popularmovies.Controller.ContentProvider.MovieContract.MovieEntry.MOVIE_RATING;
import static com.forever.kimoo.popularmovies.Controller.ContentProvider.MovieContract.MovieEntry.MOVIE_VOTE_AVERAGE;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {

    private RecyclerView moviesRecyclerView;
    private ArrayList<Movie> moviesList;
    private String sortOrder = "";
    private MoviesAdapter adapter;
    public static int currentVisiblePosition;
    boolean isChanged = true;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        moviesList = new ArrayList<Movie>();
        if (!getArguments().getString("sortOrder").equals(Urls.SORT_BY_FAVOURITES)) {
            getMoviesFromAPI();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                moviesRecyclerView.scrollToPosition(currentVisiblePosition);
                currentVisiblePosition = 0;
            }
        }, 200);

        moviesRecyclerView.getLayoutManager().scrollToPosition(currentVisiblePosition);
        if (getArguments().getString("sortOrder").equals(Urls.SORT_BY_FAVOURITES)) {
            moviesList.clear();
            getMoviesFromDatabase();
        }
    }

    private void getMoviesFromAPI() {
        sortOrder = getArguments().getString("sortOrder").equals(Urls.SORT_BY_POPULARITY) ? Urls.SORT_BY_POPULARITY : Urls.SORT_BY_HIGHEST_RATING;
        StringRequest moviesRequest = new StringRequest(Request.Method.POST, Urls.MOVIE_DB_API_BASE_URL + sortOrder, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray moviesJsonArray = new JSONObject(response).getJSONArray("results");
                    int moviesArrayLength = moviesJsonArray.length();
                    for (int i = 0; i < moviesArrayLength; i++) {
                        Movie movie = new Movie();
                        ArrayList<String> genreIds = new ArrayList<String>();

                        JSONObject jsonObject = moviesJsonArray.getJSONObject(i);
                        movie.setId(jsonObject.getString("id"));
                        movie.setTitle(jsonObject.getString("title"));
                        movie.setProfilePhotoUrl(Urls.NORMAL_POSTER_BASE_URL + jsonObject.getString("poster_path"));
                        movie.setCoverPhotoUrl(Urls.LARGE_POSTER_URL + jsonObject.getString("backdrop_path"));
                        movie.setOverview(jsonObject.getString("overview"));
                        movie.setRating(jsonObject.getString("vote_average"));
                        movie.setVoteCount(jsonObject.getString("vote_count"));
                        movie.setReleaseDate(jsonObject.getString("release_date"));
                        JSONArray genreArray = jsonObject.getJSONArray("genre_ids");
                        for (int j = 0; j < genreArray.length(); j++) {
                            genreIds.add(genreArray.getString(j));
                        }
                        movie.setGenreIds(genreIds);

                        moviesList.add(movie);
                    }

                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "No internet connection!", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Urls.API_KEY_PARAM, Constants.MY_API_KEY);
                return params;
            }
        };
        moviesRequest.setShouldCache(false);
        Volley.newRequestQueue(getActivity()).add(moviesRequest);
    }

    private void getMoviesFromDatabase() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                Cursor cursor = getActivity().getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, new String[]{"*"}, null, null, null);
                while (cursor.moveToNext()) {
                    Movie movie = new Movie();

                    movie.setId(cursor.getString(cursor.getColumnIndex(MOVIE_ID)));
                    movie.setTitle(cursor.getString(cursor.getColumnIndex(MOVIE_NAME)));
                    movie.setProfilePhotoUrl(cursor.getString(cursor.getColumnIndex(MOVIE_POSTER)));
                    movie.setCoverPhotoUrl(cursor.getString(cursor.getColumnIndex(MOVIE_IMAGE)));
                    movie.setOverview(cursor.getString(cursor.getColumnIndex(MOVIE_OVERVIEW)));
                    movie.setRating(cursor.getString(cursor.getColumnIndex(MOVIE_RATING)));
                    movie.setVoteCount(cursor.getString(cursor.getColumnIndex(MOVIE_VOTE_AVERAGE)));
                    movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(MOVIE_DATE)));
                    movie.setGenreIds(new ArrayList<String>(Arrays.asList((cursor.getString(cursor.getColumnIndex(GENRE_IDS))).replace("[", "").replace("]", "").split(","))));

                    moviesList.add(movie);
                }
                cursor.close();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
            }
        }.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);
        moviesRecyclerView = (RecyclerView) rootView.findViewById(R.id.movies_recycler_view);
        moviesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new MoviesAdapter(moviesList);
        moviesRecyclerView.setAdapter(adapter);

        return rootView;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        currentVisiblePosition = ((GridLayoutManager) moviesRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();

    }
}
