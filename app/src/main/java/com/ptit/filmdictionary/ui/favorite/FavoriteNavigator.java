package com.ptit.filmdictionary.ui.favorite;

import com.ptit.filmdictionary.data.model.Movie;

public interface FavoriteNavigator {
    void startSearchActivity();

    void startMovieDetailActivity(Movie movie);
}
