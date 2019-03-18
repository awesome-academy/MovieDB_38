package com.ptit.filmdictionary.utils;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.model.Actor;
import com.ptit.filmdictionary.data.model.CategoryName;
import com.ptit.filmdictionary.data.model.Company;
import com.ptit.filmdictionary.data.model.Genre;
import com.ptit.filmdictionary.data.model.History;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.data.model.Video;
import com.ptit.filmdictionary.ui.category.CategoryAdapter;
import com.ptit.filmdictionary.ui.history.adapter.HistoryAdapter;
import com.ptit.filmdictionary.ui.home.adapter.HomeCategoryAdapter;
import com.ptit.filmdictionary.ui.home.adapter.MovieAdapter;
import com.ptit.filmdictionary.ui.home.adapter.SlideAdapter;
import com.ptit.filmdictionary.ui.movie_detail.casts.CastsRecyclerAdapter;
import com.ptit.filmdictionary.ui.movie_detail.info.GenreRecylerAdapter;
import com.ptit.filmdictionary.ui.search.adapter.SearchAdapter;
import com.ptit.filmdictionary.ui.movie_detail.producer.ProducerRecyclerAdapter;
import com.ptit.filmdictionary.ui.movie_detail.trailer.TrailerRecyclerAdapter;
import com.ptit.filmdictionary.ui.search.adapter.SearchAdapter;

import java.util.ArrayList;
import java.util.List;

public class BindingUtils {
    private static final int ROUNDING_RADIUS = 20;
    private static final float PROGRESS_UNIT = 10;
    private static final String SUFFIX_RESULTS = " results";
    private static final String PREFIX_FOUND = "Found ";
    private static final String DEFAULT_SPINNER = "All";

    @BindingAdapter("bindHistory")
    public static void bindHistory(RecyclerView recyclerView, List<History> histories) {
        HistoryAdapter adapter = (HistoryAdapter) recyclerView.getAdapter();
        if (adapter == null) return;
        adapter.setData(histories);
    }

    @BindingAdapter("spinnerAdapter")
    public static void bindSpinner(Spinner spinner, List<Genre> genres) {
        List<String> strings = new ArrayList<>();
        strings.add(DEFAULT_SPINNER);
        strings.add(CategoryName.TITLE_TOP_RATE);
        strings.add(CategoryName.TITLE_NOW_PLAYING);
        strings.add(CategoryName.TITLE_POPULAR);
        strings.add(CategoryName.TITLE_UP_COMING);
        for (Genre genre : genres) {
            strings.add(genre.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(spinner.getContext(),
                android.R.layout.simple_spinner_item, strings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @BindingAdapter("totalResult")
    public static void totalResult(TextView textView, String totalResult) {
        textView.setText(StringUtils.concateString(PREFIX_FOUND, totalResult, SUFFIX_RESULTS));
    }

    @BindingAdapter("bindMovies")
    public static void bindMovies(RecyclerView recyclerView,
                                  ObservableList<Movie> movies) {
        CategoryAdapter adapter = (CategoryAdapter) recyclerView.getAdapter();
        if (adapter != null) adapter.addMovies(movies);
    }

    @BindingAdapter(value = {"bindSearchMovies", "isLoadMore"})
    public static void bindSearchMovies(RecyclerView recyclerView,
                                        List<Movie> movies, boolean isLoadMore) {
        SearchAdapter adapter = (SearchAdapter) recyclerView.getAdapter();
        if (adapter == null) return;
        if (isLoadMore) {
            adapter.addData(movies);
        } else {
            adapter.setData(movies);
        }
    }

    @BindingAdapter("bindViewPager")
    public static void bindViewPager(ViewPager viewPager,
                                     ObservableList<Movie> movies) {
        SlideAdapter slideAdapter = (SlideAdapter) viewPager.getAdapter();
        if (slideAdapter != null) {
            slideAdapter.update(movies);
        }
    }

    @BindingAdapter("bindSmallImage")
    public static void bindSmallImage(ImageView imageView, String image_path) {
        GlideApp.with(imageView)
                .load(StringUtils.getSmallImage(image_path))
                .thumbnail(GlideApp.with(imageView).load(R.drawable.preloader))
                .error(R.drawable.no_image)
                .into(imageView);
    }

    @BindingAdapter("bindImage")
    public static void bindPoster(ImageView imageView, String image_path) {
        GlideApp.with(imageView)
                .load(StringUtils.getImage(image_path))
                .error(R.drawable.no_image)
                .into(imageView);
    }

    @BindingAdapter("bindAvatar")
    public static void bindAvatar(ImageView imageView, String image_path) {
        GlideApp.with(imageView)
                .load(StringUtils.getSmallImage(image_path))
                .transforms(new CenterCrop(), new RoundedCorners(ROUNDING_RADIUS))
                .into(imageView);
    }

    @BindingAdapter("bindCategoryMovies")
    public static void bindCategoryMovies(RecyclerView recycler, ObservableList<Movie> movies) {
        MovieAdapter adapter = (MovieAdapter) recycler.getAdapter();
        if (adapter != null) {
            adapter.update(movies);
        }
    }

    @BindingAdapter(value = {"bindCategoryMovie", "bindCategoryTitle"}, requireAll = false)
    public static void bindRecyclerCategories(RecyclerView recycler,
                                              ObservableList<ObservableList<Movie>> movies,
                                              ObservableList<String> categories) {
        HomeCategoryAdapter adapter = (HomeCategoryAdapter) recycler.getAdapter();
        if (adapter != null) {
            adapter.update(movies, categories);
        }
    }

    @BindingAdapter("progressValue")
    public static void bindProgress(ProgressBar progressBar, double value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress((int) (value * PROGRESS_UNIT), true);
        } else {
            progressBar.setProgress((int) (value * PROGRESS_UNIT));
        }
    }

    @BindingAdapter("bindGenres")
    public static void bindGenres(RecyclerView recyclerView, List<Genre> genres) {
        GenreRecylerAdapter adapter = (GenreRecylerAdapter) recyclerView.getAdapter();
        if (adapter != null) adapter.setGenres(genres);
    }

    @BindingAdapter("app:bindTrailers")
    public static void bindTrailers(RecyclerView recyclerView, List<Video> videos) {
        TrailerRecyclerAdapter adapter = (TrailerRecyclerAdapter) recyclerView.getAdapter();
        if (adapter != null) adapter.setVideos(videos);
    }

    @BindingAdapter("app:youTubeThumbnailView")
    public static void setYouTubeThumbnailViewForTrailer(ImageView thumbnailView,
                                                         String trailerKey) {
        GlideApp.with(thumbnailView)
                .load(StringUtils.getThumbnail(trailerKey))
                .thumbnail(GlideApp.with(thumbnailView).load(R.drawable.preloader))
                .error(R.drawable.no_image)
                .into(thumbnailView);
    }

    @BindingAdapter("app:bindCasts")
    public static void setAdapterRecyclerCasts(RecyclerView recyclerView, List<Actor> actors) {
        CastsRecyclerAdapter adapter = (CastsRecyclerAdapter) recyclerView.getAdapter();
        if (adapter != null) adapter.setActors(actors);
    }

    @BindingAdapter("app:bindProducer")
    public static void setAdapterRecyclerProducer(RecyclerView recyclerView, List<Company> companies) {
        ProducerRecyclerAdapter adapter = (ProducerRecyclerAdapter) recyclerView.getAdapter();
        if (adapter != null) adapter.setCompanies(companies);
    }
}
