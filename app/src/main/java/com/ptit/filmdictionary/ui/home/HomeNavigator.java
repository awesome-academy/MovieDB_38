package com.ptit.filmdictionary.ui.home;

import com.ptit.filmdictionary.data.model.Genre;
import com.ptit.filmdictionary.data.model.Movie;

public interface HomeNavigator {
    void showMovies(Genre genre);

    void showMovieDetail(Movie movie);
}
