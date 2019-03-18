package com.ptit.filmdictionary.ui.favorite;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.ptit.filmdictionary.base.BaseViewModel;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.data.source.MovieRepository;

import io.reactivex.disposables.CompositeDisposable;

public class FavoriteViewModel extends BaseViewModel<FavoriteNavigator> {
    private MovieRepository mRepository;
    public final ObservableList<Movie> moviesObservable;
    private CompositeDisposable mDisposable;

    public FavoriteViewModel(MovieRepository repository) {
        super(repository);
        mRepository = repository;
        moviesObservable = new ObservableArrayList<>();
        mDisposable = new CompositeDisposable();
    }

    public void loadAllFavorites(){
        moviesObservable.clear();
        moviesObservable.addAll(mRepository.getAllFavorite());
    }

    public boolean deleteFavorite(Movie movie){
        return mRepository.deleteFavorite(movie);
    }

    public void dispose(){
        mDisposable.dispose();
    }
}
