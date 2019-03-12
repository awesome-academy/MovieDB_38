package com.ptit.filmdictionary.ui.actor.movies;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.data.source.MovieRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MoviesViewModel {
    private static final int DEFAULT_PAGE = 1;
    public final ObservableList<Movie> mMovies = new ObservableArrayList<>();
    private int mPage;
    private String mActorKey;
    private MovieRepository mMovieRepository;
    private CompositeDisposable mCompositeDisposable;
    MoviesNavigator mNavigator;

    public MoviesViewModel(MovieRepository movieRepository, MoviesNavigator navigator) {
        mMovieRepository = movieRepository;
        mNavigator = navigator;
        mCompositeDisposable = new CompositeDisposable();
        mPage = DEFAULT_PAGE;
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int page) {
        mPage = page;
    }

    public void setActorKey(String actorKey) {
        mActorKey = actorKey;
    }

    public void loadMoviesByActor(int page) {
        mPage = page;
        Disposable disposable = mMovieRepository.getMoviesByActor(mActorKey, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse -> {
                    mMovies.clear();
                    mMovies.addAll(movieResponse.getResults());
                    mNavigator.hideLoadData(true);
                    mNavigator.hideLoadMore(true);
                });
        mCompositeDisposable.add(disposable);
    }

    public void destroy() {
        mCompositeDisposable.dispose();
    }
}
