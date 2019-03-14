package com.ptit.filmdictionary.ui.movie_detail;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.data.source.MovieRepository;
import com.ptit.filmdictionary.ui.main.OnInternetListener;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailViewModel {
    public final ObservableField<Movie> mMovie = new ObservableField<>();
    public final ObservableBoolean mShowProgress = new ObservableBoolean(true);
    public final ObservableBoolean mIsFavorite = new ObservableBoolean(false);
    private MovieRepository mRepository;
    private CompositeDisposable mCompositeDisposable;
    private OnTrailerListener mListener;
    private OnInternetListener mInternetListener;
    private OnFavoriteListener mFavoriteListener;

    public MovieDetailViewModel(MovieRepository repository, OnTrailerListener listener) {
        mRepository = repository;
        mListener = listener;
        mCompositeDisposable = new CompositeDisposable();
    }

    public void setInternetListener(OnInternetListener internetListener) {
        mInternetListener = internetListener;
    }

    public void setFavoriteListener(OnFavoriteListener favoriteListener) {
        mFavoriteListener = favoriteListener;
    }

    public void loadMovieDetail(int movieId) {
        Disposable disposable = mRepository.getMovieDetail(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movie -> {
                    mMovie.set(movie);
                    mShowProgress.set(false);
                    if (!movie.getVideoResult().getVideos().isEmpty()) {
                        mListener.onCreateTrailer(movie.getVideoResult().getVideos().get(0).getKey());
                    }
                }, throwable -> {
                    if (mInternetListener != null) mInternetListener.onNoInternet();
                });
        mCompositeDisposable.add(disposable);

        checkFavorite(movieId);
    }

    public void checkFavorite(int movieId) {
        Disposable disposable = Observable.defer(() -> Observable.just(mRepository.isFavorite(movieId)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    mIsFavorite.set(aBoolean);
                });
        mCompositeDisposable.add(disposable);
    }

    public void destroy() {
        mCompositeDisposable.dispose();
    }

    public void changeFavorite() {
        mIsFavorite.set(!mIsFavorite.get());
        if (mIsFavorite.get()) {
            mRepository.addFavorite(mMovie.get());
        } else {
            mRepository.deleteFavorite(mMovie.get());
        }
        mFavoriteListener.onFavoriteClick(mIsFavorite.get());
    }

    public interface OnFavoriteListener {
        void onFavoriteClick(boolean isFavorite);
    }
}
