package com.ptit.filmdictionary.ui.home;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;

import com.ptit.filmdictionary.base.BaseRepository;
import com.ptit.filmdictionary.base.BaseViewModel;
import com.ptit.filmdictionary.data.model.CategoryKey;
import com.ptit.filmdictionary.data.model.CategoryName;
import com.ptit.filmdictionary.data.model.Genre;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.data.source.MovieRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends BaseViewModel<HomeNavigator> {
    private static final int FIRST_PAGE = 1;
    private static final int TO_INDEX = 5;
    private static final int FROM_INDEX = 0;
    private HomeNavigator mNavigator;
    private MovieRepository mRepository;
    private CompositeDisposable mDisposable;

    public final ObservableList<Movie> nowPlayingMoviesObservable;
    public final ObservableList<Movie> upComingMoviesObservable;
    public final ObservableList<Movie> popularMoviesObservable;
    public final ObservableList<Movie> topRateMoviesObservable;
    public final ObservableList<Movie> topTrendingMoviesObservable;
    public final ObservableList<Genre> genresObservable;
    public final ObservableList<ObservableList<Movie>> categoryMoviesObservable;
    public final ObservableList<String> categoryTitleObservable;
    
    public final ObservableBoolean isUpComingLoadedObservable;
    public final ObservableBoolean isNowPlayingLoadedObservable;
    public final ObservableBoolean isTopRateLoadedObservable;
    public final ObservableBoolean isPopularLoadedObservable;
    public final ObservableBoolean isGenresLoadedObservable;
    public final ObservableBoolean isTrendingLoadedObservable;
    public final ObservableBoolean isAllLoadedObservable;

    public HomeViewModel(BaseRepository repository) {
        super(repository);
        mRepository = (MovieRepository) repository;
        nowPlayingMoviesObservable = new ObservableArrayList<>();
        upComingMoviesObservable = new ObservableArrayList<>();
        popularMoviesObservable = new ObservableArrayList<>();
        topRateMoviesObservable = new ObservableArrayList<>();
        topTrendingMoviesObservable = new ObservableArrayList<>();
        genresObservable = new ObservableArrayList<>();
        categoryMoviesObservable = new ObservableArrayList<>();
        categoryTitleObservable = new ObservableArrayList<>();
        mDisposable = new CompositeDisposable();
        isUpComingLoadedObservable = new ObservableBoolean(false);
        isNowPlayingLoadedObservable = new ObservableBoolean(false);
        isTopRateLoadedObservable = new ObservableBoolean(false);
        isPopularLoadedObservable = new ObservableBoolean(false);
        isGenresLoadedObservable = new ObservableBoolean(false);
        isTrendingLoadedObservable = new ObservableBoolean(false);
        isAllLoadedObservable = new ObservableBoolean(false);
        loadData();
    }

    private void loadData() {
        loadTopRateMovies();
        loadNowPlayingMovies();
        loadPopularMovies();
        loadUpComingMovies();
        loadTopTrendingMovies();
        loadGenres();
    }

    private boolean isAllLoaded(){
        return isGenresLoadedObservable.get() &&
                isUpComingLoadedObservable.get() &&
                isTopRateLoadedObservable.get() &&
                isTrendingLoadedObservable.get() &&
                isPopularLoadedObservable.get() &&
                isNowPlayingLoadedObservable.get();
    }
    private void loadGenres() {
        Disposable disposable = mRepository.getGenres()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(genreResponse -> {
                    genresObservable.addAll(genreResponse.getGenres());
                    isGenresLoadedObservable.set(true);
                    isAllLoadedObservable.set(isAllLoaded());
                },
                        throwable -> handleError(throwable.getMessage()));
        mDisposable.add(disposable);
    }

    private void loadTopTrendingMovies() {
        Disposable disposable = mRepository.getMoviesTrendingByDay()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse -> {
                    topTrendingMoviesObservable.addAll(movieResponse.getResults().subList(FROM_INDEX, TO_INDEX));
                    isTrendingLoadedObservable.set(true);
                    isAllLoadedObservable.set(isAllLoaded());
                }, throwable -> handleError(throwable.getMessage()));
        mDisposable.add(disposable);
    }

    private void loadNowPlayingMovies() {
        Disposable disposable = mRepository.getMoviesByCategory(CategoryKey.CATEGORY_NOW_PLAYING, FIRST_PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse -> {
                    nowPlayingMoviesObservable.addAll(movieResponse.getResults());
                    categoryMoviesObservable.add(nowPlayingMoviesObservable);
                    categoryTitleObservable.add(CategoryName.TITLE_NOW_PLAYING);
                    isNowPlayingLoadedObservable.set(true);
                    isAllLoadedObservable.set(isAllLoaded());
                }, throwable -> handleError(throwable.getMessage()));
        mDisposable.add(disposable);
    }

    private void loadUpComingMovies() {
        Disposable disposable = mRepository.getMoviesByCategory(CategoryKey.CATEGORY_UP_COMING, FIRST_PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse -> {
                    upComingMoviesObservable.addAll(movieResponse.getResults());
                    categoryMoviesObservable.add(upComingMoviesObservable);
                    categoryTitleObservable.add(CategoryName.TITLE_UP_COMING);
                    isUpComingLoadedObservable.set(true);
                    isAllLoadedObservable.set(isAllLoaded());
                }, throwable -> handleError(throwable.getMessage()));
        mDisposable.add(disposable);
    }

    private void loadTopRateMovies() {
        Disposable disposable = mRepository.getMoviesByCategory(CategoryKey.CATEGORY_TOP_RATE, FIRST_PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse -> {
                    topRateMoviesObservable.addAll(movieResponse.getResults());
                    categoryMoviesObservable.add(topRateMoviesObservable);
                    categoryTitleObservable.add(CategoryName.TITLE_TOP_RATE);
                    isTopRateLoadedObservable.set(true);
                    isAllLoadedObservable.set(isAllLoaded());
                }, throwable -> handleError(throwable.getMessage()));
        mDisposable.add(disposable);
    }

    private void loadPopularMovies() {
        Disposable disposable = mRepository.getMoviesByCategory(CategoryKey.CATEGORY_POPULAR, FIRST_PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse -> {
                    popularMoviesObservable.addAll(movieResponse.getResults());
                    categoryMoviesObservable.add(popularMoviesObservable);
                    categoryTitleObservable.add(CategoryName.TITLE_POPULAR);
                    isPopularLoadedObservable.set(true);
                    isAllLoadedObservable.set(isAllLoaded());
                }, throwable -> handleError(throwable.getMessage()));
        mDisposable.add(disposable);
    }

    private void handleError(String message) {
    }

    public void onGenreClick(Genre genre){
        getNavigator().startGenreActivity(genre.getId(), genre.getName());
    }

    public void onSearchClick(){
        getNavigator().startSearchActivity();
    }

    public void dispose() {
        mDisposable.dispose();
    }
}
