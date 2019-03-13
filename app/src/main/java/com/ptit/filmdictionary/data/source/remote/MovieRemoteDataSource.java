package com.ptit.filmdictionary.data.source.remote;

import android.content.Context;

import com.ptit.filmdictionary.data.model.Actor;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.data.source.MovieDataSource;
import com.ptit.filmdictionary.data.source.remote.response.GenreResponse;
import com.ptit.filmdictionary.data.source.remote.response.MovieResponse;

import io.reactivex.Observable;

public class MovieRemoteDataSource implements MovieDataSource.Remote {
    private static MovieRemoteDataSource sMovieRemoteDataSource;
    private ApiRequest mApiRequest;

    private MovieRemoteDataSource(ApiRequest apiRequest) {
        mApiRequest = apiRequest;
    }

    public static MovieRemoteDataSource getInstance(Context context) {
        if (sMovieRemoteDataSource == null) {
            sMovieRemoteDataSource = new MovieRemoteDataSource(NetworkService.getInstance(context));
        }
        return sMovieRemoteDataSource;
    }

    @Override
    public Observable<GenreResponse> getGenres() {
        return mApiRequest.getGenres();
    }

    @Override
    public Observable<MovieResponse> getMoviesTrendingByDay() {
        return mApiRequest.getMoviesTrendingByDay();
    }

    @Override
    public Observable<MovieResponse> getMoviesByCategory(String categoryType, int page) {
        return mApiRequest.getMoviesByCategory(categoryType, page);
    }

    @Override
    public Observable<MovieResponse> getMoviesByGenre(String idGenre, int page) {
        return mApiRequest.getMoviesByGenre(idGenre, page);
    }

    @Override
    public Observable<MovieResponse> getMoviesByActor(String idActor, int page) {
        return mApiRequest.getMoviesByActor(idActor, page);
    }

    @Override
    public Observable<MovieResponse> getMoviesByCompany(int idCompany, int page) {
        return mApiRequest.getMoviesByCompany(idCompany, page);
    }

    @Override
    public Observable<Movie> getMovieDetail(int idMovie) {
        return mApiRequest.getMovieDetail(idMovie);
    }

    @Override
    public Observable<MovieResponse> searchMovieByName(String key, int page) {
        return mApiRequest.searchMovieByName(key, page);
    }

    @Override
    public Observable<Actor> getProfile(String actorId) {
        return mApiRequest.getProfile(actorId);
    }
}
