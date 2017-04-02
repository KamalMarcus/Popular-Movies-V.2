package com.kamal.marcus.popularmoviesv2.Controller;

import java.util.HashMap;

/**
 * Created by KiMoo on 02/03/2017.
 */

public class Urls {
    final public static String MOVIE_DB_API_BASE_URL = "https://api.themoviedb.org/3/discover/movie?";
    final public static String SORTING_PARAM = "sort_by";
    final public static String SORT_BY_POPULARITY = "popularity.desc";
    final public static String SORT_BY_HIGHEST_RATING = "vote_average.desc";
    final public static String SORT_BY_FAVOURITES="favourites";
    final public static String API_KEY_PARAM = "api_key";
    final public static String NORMAL_POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185";
    final public static String LARGE_POSTER_URL = "http://image.tmdb.org/t/p/w342";
    final public static String TRAILERS_AND_REVIEWS_BASE_URL = "http://api.themoviedb.org/3/movie/";
    public static HashMap<String, String> genres;

    static {
        genres = new HashMap<String, String>();
        genres.put("28", "Action");
        genres.put("12", "Adventure");
        genres.put("16", "Animation");
        genres.put("35", "Comedy");
        genres.put("80", "Crime");
        genres.put("99", "Documentary");
        genres.put("18", "Drama");
        genres.put("10751", "Family");
        genres.put("14", "Fantasy");
        genres.put("36", "History");
        genres.put("27", "Horror");
        genres.put("10402", "Music");
        genres.put("9648", "Mystery");
        genres.put("10749", "Romance");
        genres.put("878", "Science Fiction");
        genres.put("10770", "TV Movie");
        genres.put("53", "Thriller");
        genres.put("10752", "War");
        genres.put("37", "Western");
    }
}
