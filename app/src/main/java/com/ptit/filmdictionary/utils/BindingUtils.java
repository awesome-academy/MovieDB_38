package com.ptit.filmdictionary.utils;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.model.Genre;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.ui.category.CategoryAdapter;
import com.ptit.filmdictionary.ui.home.adapter.HomeCategoryAdapter;
import com.ptit.filmdictionary.ui.home.adapter.MovieAdapter;
import com.ptit.filmdictionary.ui.home.adapter.SlideAdapter;
import com.ptit.filmdictionary.ui.movie_detail.info.GenreRecylerAdapter;

import java.util.List;

public class BindingUtils {
    private static final int ROUNDING_RADIUS = 20;
    private static final float PROGRESS_UNIT = 10;

    @BindingAdapter("bindMovies")
    public static void bindMovies(RecyclerView recyclerView,
                                  ObservableList<Movie> movies) {
        CategoryAdapter adapter = (CategoryAdapter) recyclerView.getAdapter();
        if (adapter != null) adapter.addMovies(movies);
    }

    @BindingAdapter("bindViewPager")
    public static void bindViewPager(ViewPager viewPager,
                                     ObservableList<Movie> movies) {
        SlideAdapter slideAdapter = (SlideAdapter) viewPager.getAdapter();
        if (slideAdapter != null) {
            slideAdapter.update(movies);
        }
    }

    @BindingAdapter("bindImage")
    public static void bindPoster(ImageView imageView, ObservableField<String> image_path) {
        GlideApp.with(imageView)
                .load(StringUtils.getSmallImage(image_path.get()))
                .thumbnail(GlideApp.with(imageView).load(R.drawable.preloader))
                .error(R.drawable.no_image)
                .into(imageView);
    }

    @BindingAdapter("bindImage")
    public static void bindPoster(ImageView imageView, String image_path) {
        GlideApp.with(imageView)
                .load(StringUtils.getImage(image_path))
                .thumbnail(GlideApp.with(imageView).load(R.drawable.preloader))
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
}
