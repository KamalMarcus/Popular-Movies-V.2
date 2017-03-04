package com.kamal.marcus.popularmoviesv2;


import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {

    private RecyclerView moviesRecyclerView;
    private ArrayList<Movie> moviesList;
    private String sortOrder = "";
    private MoviesAdapter adapter;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sortOrder = getArguments().getString("sortOrder").equals(Urls.SORT_BY_POPULARITY) ? Urls.SORT_BY_POPULARITY : Urls.SORT_BY_HIGHEST_RATING;
        System.out.println(sortOrder);
        moviesList = new ArrayList<Movie>();

        StringRequest moviesRequest = new StringRequest(Request.Method.POST, Urls.MOVIE_DB_API_BASE_URL, new Response.Listener<String>() {
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
                        JSONArray genreArray=jsonObject.getJSONArray("genre_ids");
                        for(int j=0;j<genreArray.length();j++){
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
                Toast.makeText(getActivity(),"No internet connection!",Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Urls.SORTING_PARAM, sortOrder);
                params.put(Urls.API_KEY_PARAM, Constants.MY_API_KEY);
                return params;
            }
        };
moviesRequest.setShouldCache(false);
        Volley.newRequestQueue(getActivity()).add(moviesRequest);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);
        moviesRecyclerView = (RecyclerView) rootView.findViewById(R.id.movies_recycler_view);
        moviesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new MoviesAdapter(moviesList);
        moviesRecyclerView.setAdapter(adapter);

//        adapter.notifyDataSetChanged();
        return rootView;
    }

}
