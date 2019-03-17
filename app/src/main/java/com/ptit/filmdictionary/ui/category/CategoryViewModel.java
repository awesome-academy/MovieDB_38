package com.ptit.filmdictionary.ui.category;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.data.source.MovieRepository;
import com.ptit.filmdictionary.ui.main.OnInternetListener;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CategoryViewModel {
    private static final int DEFAULT_PAGE = 1;
    public final ObservableList<Movie> mMovies = new ObservableArrayList<>();
    private int mPage;
    private String mCategoryKey;
    private String mGenreKey;
    private int mProducerKey;
    private MovieRepository mMovieRepository;
    private CompositeDisposable mCompositeDisposable;
    private CategoryNavigator mNavigator;
    private OnInternetListener mOnInternetListener;

    public CategoryViewModel(MovieRepository movieRepository, CategoryNavigator navigator) {
        mMovieRepository = movieRepository;
        mNavigator = navigator;
        mPage = DEFAULT_PAGE;
        mCompositeDisposable = new CompositeDisposable();
    }

    public void setOnInternetListener(OnInternetListener onInternetListener) {
        mOnInternetListener = onInternetListener;
    }

    public String getCategoryKey() {
        return mCategoryKey;
    }

    public void setCategoryKey(String categoryKey) {
        mCategoryKey = categoryKey;
    }

    public String getGenreKey() {
        return mGenreKey;
    }

    public void setGenreKey(String genreKey) {
        mGenreKey = genreKey;
    }

    public int getProducerKey() {
        return mProducerKey;
    }

    public void setProducerKey(int producerKey) {
        mProducerKey = producerKey;
    }

    public void loadMoviesByCategory(int page) {
        mPage = page;
        Disposable disposable = mMovieRepository.getMoviesByCategory(mCategoryKey, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse -> {
                    mMovies.clear();
                    mMovies.addAll(movieResponse.getResults());
                    mNavigator.hideLoadData(true);
                    mNavigator.hideLoadMore(true);
                }, throwable -> {
                    mOnInternetListener.onNoInternet();
                });
        mCompositeDisposable.add(disposable);
    }

    public void destroy() {
        mCompositeDisposable.dispose();
    }

    public int getPage() {
        return mPage;
    }

    public void loadMoviesByGenre(int page) {
        mPage = page;
        Disposable disposable = mMovieRepository.getMoviesByGenre(mGenreKey, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse -> {
                    mMovies.clear();
                    mMovies.addAll(movieResponse.getResults());
                    mNavigator.hideLoadData(true);
                    mNavigator.hideLoadMore(true);
                }, throwable -> {
                    mOnInternetListener.onNoInternet();
                });
        mCompositeDisposable.add(disposable);
    }

    public void loadMoviesByProducer(int page) {
        mPage = page;
        Disposable disposable = mMovieRepository.getMoviesByCompany(mProducerKey, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse -> {
                    mMovies.clear();
                    mMovies.addAll(movieResponse.getResults());
                    mNavigator.hideLoadData(true);
                    mNavigator.hideLoadMore(true);
                }, throwable -> {
                    mOnInternetListener.onNoInternet();
                });
        mCompositeDisposable.add(disposable);
    }
}
