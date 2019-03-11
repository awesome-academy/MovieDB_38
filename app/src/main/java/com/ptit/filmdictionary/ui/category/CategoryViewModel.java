package com.ptit.filmdictionary.ui.category;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.data.source.MovieRepository;
import com.ptit.filmdictionary.data.source.remote.response.MovieResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CategoryViewModel {
    private static final int DEFAULT_PAGE = 1;
    public final ObservableList<Movie> mMovies = new ObservableArrayList<>();
    private int mPage;
    private String mCategoryType;
    private MovieRepository mMovieRepository;
    private CompositeDisposable mCompositeDisposable;
    private CategoryNavigator mNavigator;

    public CategoryViewModel(String categoryType, MovieRepository movieRepository, CategoryNavigator navigator) {
        mCategoryType = categoryType;
        mMovieRepository = movieRepository;
        mNavigator = navigator;
        mPage = DEFAULT_PAGE;
        mCompositeDisposable = new CompositeDisposable();
    }

    public void loadMoviesByCategory(int page) {
        mPage = page;
        Disposable disposable = mMovieRepository.getMoviesByCategory(mCategoryType, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieResponse>() {
                    @Override
                    public void accept(MovieResponse movieResponse) throws Exception {
                        mMovies.clear();
                        mMovies.addAll(movieResponse.getResults());
                        mNavigator.hideLoadData(true);
                        mNavigator.hideLoadMore(true);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    public void destroy() {
        mCompositeDisposable.dispose();
    }

    public int getPage() {
        return mPage;
    }

}
