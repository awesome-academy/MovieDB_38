package com.ptit.filmdictionary.ui.movie_detail;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.data.source.MovieRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailViewModel {
    public final ObservableField<Movie> mMovie = new ObservableField<>();
    public final ObservableBoolean mShowProgress = new ObservableBoolean(true);
    private MovieRepository mRepository;
    private CompositeDisposable mCompositeDisposable;

    public MovieDetailViewModel(MovieRepository repository) {
        mRepository = repository;
        mCompositeDisposable = new CompositeDisposable();
    }

    public void loadMovieDetail(int movieId) {
        Disposable disposable = mRepository.getMovieDetail(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movie -> {
                    mMovie.set(movie);
                    mShowProgress.set(false);
                });
        mCompositeDisposable.add(disposable);
    }

    public void destroy() {
        mCompositeDisposable.dispose();
    }
}
