package com.ptit.filmdictionary.data.source;

import android.databinding.ObservableArrayList;

import com.ptit.filmdictionary.base.BaseRepository;
import com.ptit.filmdictionary.data.model.Actor;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.data.source.local.MovieLocalDataSource;
import com.ptit.filmdictionary.data.source.remote.MovieRemoteDataSource;
import com.ptit.filmdictionary.data.source.remote.response.GenreResponse;
import com.ptit.filmdictionary.data.source.remote.response.MovieResponse;

import io.reactivex.Observable;

public class MovieRepository extends BaseRepository implements MovieDataSource.Local, MovieDataSource.Remote {
    private static MovieRepository sInstance;
    private MovieRemoteDataSource mRemote;
    private MovieLocalDataSource mLocal;

    private MovieRepository(MovieRemoteDataSource remote,
                            MovieLocalDataSource local) {
        mRemote = remote;
        mLocal = local;
    }

    public static MovieRepository getInstance(MovieRemoteDataSource remote,
                                              MovieLocalDataSource local) {
        if (sInstance == null) {
            sInstance = new MovieRepository(remote, local);
        }
        return sInstance;
    }

    @Override
    public ObservableArrayList<Movie> getAllFavorite() {
        return mLocal.getAllFavorite();
    }

    @Override
    public boolean addFavorite(Movie movie) {
        return mLocal.addFavorite(movie);
    }

    @Override
    public boolean deleteFavorite(Movie movie) {
        return mLocal.deleteFavorite(movie);
    }

    @Override
    public boolean isFavorite(int movieId) {
        return mLocal.isFavorite(movieId);
    }

    @Override
    public Observable<GenreResponse> getGenres() {
        return mRemote.getGenres();
    }

    @Override
    public Observable<MovieResponse> getMoviesTrendingByDay() {
        return mRemote.getMoviesTrendingByDay();
    }

    @Override
    public Observable<MovieResponse> getMoviesByCategory(String categoryType, int page) {
        return mRemote.getMoviesByCategory(categoryType, page);
    }

    @Override
    public Observable<MovieResponse> getMoviesByGenre(String idGenre, int page) {
        return mRemote.getMoviesByGenre(idGenre, page);
    }

    @Override
    public Observable<MovieResponse> getMoviesByActor(String idActor, int page) {
        return mRemote.getMoviesByActor(idActor, page);
    }

    @Override
    public Observable<MovieResponse> getMoviesByCompany(int idCompany, int page) {
        return mRemote.getMoviesByCompany(idCompany, page);
    }

    @Override
    public Observable<Movie> getMovieDetail(int idMovie) {
        return mRemote.getMovieDetail(idMovie);
    }

    @Override
    public Observable<MovieResponse> searchMovieByName(String key, int page) {
        return mRemote.searchMovieByName(key, page);
    }

    @Override
    public Observable<Actor> getProfile(String actorId) {
        return mRemote.getProfile(actorId);
    }
}
