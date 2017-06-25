package com.forever.kimoo.popularmovies.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.widget.Toast;

import com.forever.kimoo.popularmovies.Controller.ContentProvider.MovieContract;
import com.forever.kimoo.popularmovies.Models.Movie;

import static com.forever.kimoo.popularmovies.Controller.ContentProvider.MovieContract.MovieEntry.*;


/**
 * Created by KiMoo on 01/04/2017.
 */

public class Utils {
    public static boolean isMovieFavourite(Context context, String movieId) {
        Cursor cursor = context.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, new String[]{MOVIE_NAME}, MOVIE_ID + "=?", new String[]{movieId}, null, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;

        } else
            return false;
    }

    public static void addMovieToFavourites(Context context,Movie movie){
        ContentValues values = new ContentValues();
        values.put(MOVIE_ID, movie.getId());
        values.put(MOVIE_NAME, movie.getTitle());
        values.put(MOVIE_POSTER,movie.getProfilePhotoUrl());
        values.put(MOVIE_IMAGE,movie.getCoverPhotoUrl());
        values.put(MOVIE_DATE,movie.getReleaseDate());
        values.put(MOVIE_OVERVIEW,movie.getOverview());
        values.put(MOVIE_RATING,movie.getRating());
        values.put(MOVIE_VOTE_AVERAGE,movie.getVoteCount());
        values.put(GENRE_IDS,movie.getGenreIds()+"");

        context.getContentResolver().insert(CONTENT_URI, values);
        Toast.makeText(context.getApplicationContext(), "Added to Favourites", Toast.LENGTH_SHORT).show();
    }

    public static void removeMovieFromFavourites(Context context,String movieId){
        context.getContentResolver().delete(CONTENT_URI,MOVIE_ID+"=?",new String[]{movieId});
        Toast.makeText(context.getApplicationContext(), "Removed from Favourites", Toast.LENGTH_SHORT).show();
    }

    public static boolean isLargeScreen(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }
}
