package com.forever.kimoo.popularmovies.Controller.ContentProvider;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by KiMoo on 30/03/2017.
 */

public class MovieContract {

    public static final String CONTENT_AUTHORITY = "com.kamal.marcus.popularmoviesv2";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIE = "movie";

    public static final class MovieEntry implements BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_URI  + "/" + PATH_MOVIE;

        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_MOVIE;

        public static final String TABLE_NAME = "movieTable";
        public static final String MOVIE_ID = "movie_id";
        public static final String MOVIE_NAME = "name";
        public static final String MOVIE_POSTER = "poster";
        public static final String MOVIE_IMAGE = "image";
        public static final String MOVIE_OVERVIEW = "overview";
        public static final String MOVIE_RATING = "rating";
        public static final String MOVIE_VOTE_AVERAGE="vote_average";
        public static final String MOVIE_DATE = "movie_date";
        public static final String GENRE_IDS="genre_ids";

        public static Uri buildMovieUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

}
