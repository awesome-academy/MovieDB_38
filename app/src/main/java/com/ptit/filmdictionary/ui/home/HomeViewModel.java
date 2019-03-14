package com.ptit.filmdictionary.ui.home;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.util.Log;
import android.widget.Toast;

import com.ptit.filmdictionary.base.BaseRepository;
import com.ptit.filmdictionary.base.BaseViewModel;
import com.ptit.filmdictionary.data.model.CategoryKey;
import com.ptit.filmdictionary.data.model.Genre;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.data.source.MovieRepository;
import com.ptit.filmdictionary.data.source.remote.response.MovieResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.ptit.filmdictionary.utils.Constants.TITLE_POPULAR;
import static com.ptit.filmdictionary.utils.Constants.TITLE_TOP_RATE;
import static com.ptit.filmdictionary.utils.Constants.TITLE_UP_COMING;
import static com.ptit.filmdictionary.utils.Constants.TITLE_NOW_PLAYING;

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
    public final ObservableList<ObservableList<Movie>> categoryMoviesObservable;
    public final ObservableList<String> categoryTitleObservable;

    public HomeViewModel(BaseRepository repository) {
        super(repository);
        mRepository = (MovieRepository) repository;
        nowPlayingMoviesObservable = new ObservableArrayList<>();
        upComingMoviesObservable = new ObservableArrayList<>();
        popularMoviesObservable = new ObservableArrayList<>();
        topRateMoviesObservable = new ObservableArrayList<>();
        topTrendingMoviesObservable = new ObservableArrayList<>();
        categoryMoviesObservable = new ObservableArrayList<>();
        categoryTitleObservable = new ObservableArrayList<>();
        mDisposable = new CompositeDisposable();
        loadData();
    }

    private void loadData() {
        loadTopRateMovies();
        loadNowPlayingMovies();
        loadPopularMovies();
        loadUpComingMovies();
        loadTopTrendingMovies();
    }

    private void loadTopTrendingMovies() {
        Disposable disposable = mRepository.getMoviesTrendingByDay()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse -> {
                    topTrendingMoviesObservable.addAll(movieResponse.getResults().subList(FROM_INDEX, TO_INDEX));
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
                    categoryTitleObservable.add(TITLE_NOW_PLAYING);
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
                    categoryTitleObservable.add(TITLE_UP_COMING);
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
                    categoryTitleObservable.add(TITLE_TOP_RATE);
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
                    categoryTitleObservable.add(TITLE_POPULAR);
                }, throwable -> handleError(throwable.getMessage()));
        mDisposable.add(disposable);
    }

    public ObservableList<Movie> getNowPlayingMoviesObservable() {
        return nowPlayingMoviesObservable;
    }

    public ObservableList<ObservableList<Movie>> getCategoryMoviesObservable() {
        return categoryMoviesObservable;
    }

    public ObservableList<String> getCategoryTitleObservable() {
        return categoryTitleObservable;
    }

    private void handleError(String message) {
    }

    public void onGenreClick(Genre genre){
        mNavigator.startGenreActivity(genre.getId(), genre.getName());
    }

    public void onSearchClick(){

    }

    public void dispose() {
        mDisposable.dispose();
    }
}
