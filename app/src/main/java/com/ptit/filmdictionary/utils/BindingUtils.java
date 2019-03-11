package com.ptit.filmdictionary.utils;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.ui.category.CategoryAdapter;

public class BindingUtils {
    @BindingAdapter("bindMovies")
    public static void bindMovies(RecyclerView recyclerView,
                                  ObservableList<Movie> movies) {
        CategoryAdapter adapter = (CategoryAdapter) recyclerView.getAdapter();
        if (adapter != null) adapter.addMovies(movies);
    }

    @BindingAdapter("bindImage")
    public static void bindPoster(ImageView imageView, ObservableField<String> image_path) {
        GlideApp.with(imageView)
                .load(StringUtils.getSmallImage(image_path.get()))
                .thumbnail(GlideApp.with(imageView).load(R.drawable.preloader))
                .error(R.drawable.no_image)
                .into(imageView);
    }
}
