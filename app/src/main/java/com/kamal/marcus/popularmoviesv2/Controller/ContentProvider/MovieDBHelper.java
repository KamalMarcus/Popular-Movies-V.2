package com.kamal.marcus.popularmoviesv2.Controller.ContentProvider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.os.Build.ID;

/**
 * Created by KiMoo on 30/03/2017.
 */

public class MovieDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "movieList.db";

    public MovieDBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        addMovieTable(db);
    }

    private void addMovieTable(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY,"
                        + MovieContract.MovieEntry.MOVIE_ID + " TEXT,"
                        + MovieContract.MovieEntry.MOVIE_NAME + " TEXT,"
                        + MovieContract.MovieEntry.MOVIE_POSTER + " TEXT,"
                        + MovieContract.MovieEntry.MOVIE_IMAGE + " TEXT,"
                        + MovieContract.MovieEntry.MOVIE_OVERVIEW + " TEXT,"
                        + MovieContract.MovieEntry.MOVIE_RATING + " TEXT,"
                        + MovieContract.MovieEntry.MOVIE_DATE + " TEXT,"
                        + MovieContract.MovieEntry.MOVIE_VOTE_AVERAGE + " TEXT,"
                        + MovieContract.MovieEntry.GENRE_IDS + " TEXT)"

        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
