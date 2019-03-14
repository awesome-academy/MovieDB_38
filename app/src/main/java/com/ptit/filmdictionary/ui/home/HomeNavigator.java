package com.ptit.filmdictionary.ui.home;

import com.ptit.filmdictionary.data.model.Genre;
import com.ptit.filmdictionary.data.model.Movie;

public interface HomeNavigator {
    void startCategoryActivity(String categoryKey, String categoryTitle);

    void startGenreActivity(String genreKey, String genreTitle);

    void startMovieDetailActivity(Movie movie);

    void startSearchActivity();
}
