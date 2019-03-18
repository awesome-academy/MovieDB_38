package com.ptit.filmdictionary.ui.search;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.ptit.filmdictionary.data.model.Movie;

public class HorizontalMovieViewModel {
    public final ObservableField<Movie> movieObservableField;
    public final ObservableBoolean isFavoriteObservable;

    public HorizontalMovieViewModel() {
        movieObservableField = new ObservableField<>();
        isFavoriteObservable = new ObservableBoolean(false);
    }
}
