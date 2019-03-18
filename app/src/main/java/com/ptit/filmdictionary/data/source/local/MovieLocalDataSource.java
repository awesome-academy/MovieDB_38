package com.ptit.filmdictionary.data.source.local;

import android.content.Context;
import android.databinding.ObservableArrayList;

import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.data.source.MovieDataSource;

public class MovieLocalDataSource implements MovieDataSource.Local {
    private static MovieLocalDataSource sInstance;
    private DataBaseHelper mDbHelper;

    private MovieLocalDataSource(Context context) {
        mDbHelper = new DataBaseHelper(context);
    }

    public static MovieLocalDataSource getInstance(Context context) {
        if (sInstance == null) sInstance = new MovieLocalDataSource(context);
        return sInstance;
    }

    @Override
    public ObservableArrayList<Movie> getAllFavorite() {
        return mDbHelper.getAllFavorite();
    }

    @Override
    public boolean addFavorite(Movie movie) {
        return mDbHelper.addFavorite(movie);
    }

    @Override
    public boolean deleteFavorite(Movie movie) {
        return mDbHelper.deleteFavorite(movie);
    }

    @Override
    public boolean isFavorite(int movieId) {
        return mDbHelper.isFavorite(movieId);
    }
}
