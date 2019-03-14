package com.ptit.filmdictionary.ui.home;

import android.databinding.BaseObservable;

import com.ptit.filmdictionary.data.model.Movie;

public class ItemMovieViewModel extends BaseObservable {
    private Movie mMovie;

    public ItemMovieViewModel() {
    }

    public Movie getMovie() {
        return mMovie;
    }

    public void setMovie(Movie movie) {
        mMovie = movie;
    }

    public String getBackdropPath() {
        return mMovie.getBackdropPath();
    }

    public String getReleaseDate(){
        return mMovie.getReleaseDate();
    }

    public double getVoteAverage(){
        return mMovie.getVoteAverage();
    }

    public String getPosterPath() {
        return mMovie.getPosterPath();
    }

    public String getTitle() {
        return mMovie.getTitle();
    }
}
