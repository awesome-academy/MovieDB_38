package com.ptit.filmdictionary.data.source;

import android.databinding.ObservableArrayList;

import com.ptit.filmdictionary.data.model.Actor;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.data.source.remote.response.GenreResponse;
import com.ptit.filmdictionary.data.source.remote.response.MovieResponse;

import io.reactivex.Observable;

public interface MovieDataSource {
    interface Local {
        ObservableArrayList<Movie> getAllFavorite();

        boolean addFavorite(Movie movie);

        boolean deleteFavorite(Movie movie);

        boolean isFavorite(int movieId);
    }

    interface Remote {
        Observable<GenreResponse> getGenres();

        Observable<MovieResponse> getMoviesTrendingByDay();

        Observable<MovieResponse> getMoviesByCategory(String categoryType, int page);

        Observable<MovieResponse> getMoviesByGenre(String idGenre, int page);

        Observable<MovieResponse> getMoviesByActor(String idActor, int page);

        Observable<MovieResponse> getMoviesByCompany(int idCompany, int page);

        Observable<Movie> getMovieDetail(int idMovie);

        Observable<MovieResponse> searchMovieByName(String key, int page);

        Observable<Actor> getProfile(String actorId);
    }
}
