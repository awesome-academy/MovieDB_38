package com.ptit.filmdictionary.utils;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.ui.category.CategoryAdapter;

public class BindingUtils {
    private static final int ROUNDING_RADIUS = 20;

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
                .placeholder(R.drawable.preloader)
                .error(R.drawable.no_image)
                .into(imageView);
    }

    @BindingAdapter("bindImage")
    public static void bindPoster(ImageView imageView, String image_path) {
        GlideApp.with(imageView)
                .load(StringUtils.getImage(image_path))
                .placeholder(R.drawable.preloader)
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
}
