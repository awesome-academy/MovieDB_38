package com.ptit.filmdictionary.ui.search;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;

import com.ptit.filmdictionary.base.BaseRepository;
import com.ptit.filmdictionary.base.BaseViewModel;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.data.source.MovieRepository;
import com.ptit.filmdictionary.data.source.remote.response.MovieResponse;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchViewModel extends BaseViewModel<SearchNavigator> {
    private static final int FIRST_PAGE = 1;
    public final ObservableList<Movie> searchResultObservable;
    public final CompositeDisposable compositeDisposable;
    public final ObservableInt totalResultObservable;
    public final ObservableBoolean isLoadedResults;
    private MovieRepository mMovieRepository;

    public SearchViewModel(BaseRepository repository) {
        super(repository);
        mMovieRepository = (MovieRepository) repository;
        searchResultObservable = new ObservableArrayList<>();
        totalResultObservable = new ObservableInt();
        isLoadedResults = new ObservableBoolean(false);
        compositeDisposable = new CompositeDisposable();
    }

    public void loadResultByKeyword(String query){
        Disposable disposable = mMovieRepository.searchMovieByName(query, FIRST_PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse -> {
                    searchResultObservable.clear();
                    searchResultObservable.addAll(movieResponse.getResults());
                    isLoadedResults.set(true);
                    totalResultObservable.set(movieResponse.getTotalResult());
                });
        compositeDisposable.add(disposable);
    }

    public void dispose(){
        compositeDisposable.dispose();
    }
}
