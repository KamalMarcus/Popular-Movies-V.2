package com.kamal.marcus.popularmoviesv2;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by KiMoo on 02/03/2017.
 */

public class Movie implements Parcelable {
    private String id;
    private String title;
    private String profilePhotoUrl;
    private String coverPhotoUrl;
    private String rating;
    private String overview;
    private String voteCount;
    private ArrayList<String> genreIds;
    private String releaseDate;

    public Movie(){

    }

    protected Movie(Parcel in) {
        setId(in.readString());
        setTitle(in.readString());
        setProfilePhotoUrl(in.readString());
        setCoverPhotoUrl(in.readString());
        setRating(in.readString());
        setOverview(in.readString());
        setVoteCount(in.readString());
        if (in.readByte() == 0x01) {
            setGenreIds(new ArrayList<String>());
            in.readList(getGenreIds(), String.class.getClassLoader());
        } else {
            setGenreIds(null);
        }
        setReleaseDate(in.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getId());
        dest.writeString(getTitle());
        dest.writeString(getProfilePhotoUrl());
        dest.writeString(getCoverPhotoUrl());
        dest.writeString(getRating());
        dest.writeString(getOverview());
        dest.writeString(getVoteCount());
        if (getGenreIds() == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(getGenreIds());
        }
        dest.writeString(getReleaseDate());
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public String getCoverPhotoUrl() {
        return coverPhotoUrl;
    }

    public void setCoverPhotoUrl(String coverPhotoUrl) {
        this.coverPhotoUrl = coverPhotoUrl;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public ArrayList<String> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(ArrayList<String> genreIds) {
        this.genreIds = genreIds;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}