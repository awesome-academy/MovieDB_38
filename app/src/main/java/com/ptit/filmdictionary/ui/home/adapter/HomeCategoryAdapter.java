package com.ptit.filmdictionary.ui.home.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.databinding.ItemCategoryBinding;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder> {
    private ObservableList<ObservableList<Movie>> mMovies;
    private ObservableList<String> mCategories;
    private CategoryListener mListener;

    public HomeCategoryAdapter(CategoryListener listener) {
        mListener = listener;
        mMovies = new ObservableArrayList<>();
        mCategories = new ObservableArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemCategoryBinding categoryBinding = DataBindingUtil.inflate(inflater, R.layout.item_category,
                viewGroup, false);
        return new ViewHolder(categoryBinding, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(mMovies.get(i), mCategories.get(i));
    }

    @Override
    public int getItemCount() {
        return mMovies == null ? 0 : mMovies.size();
    }

    public void update(ObservableList<ObservableList<Movie>> movies,
                       ObservableList<String> categories){
        mMovies = movies;
        mCategories = categories;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            MovieAdapter.MovieListener {
        private ItemCategoryBinding mItemCategoryBinding;
        private CategoryListener mListener;

        public ViewHolder(ItemCategoryBinding binding, CategoryListener listener) {
            super(binding.getRoot());
            mItemCategoryBinding = binding;
            mListener = listener;
            mItemCategoryBinding.recyclerMovie.setAdapter(new MovieAdapter(this));
            mItemCategoryBinding.recyclerMovie.setNestedScrollingEnabled(false);
            mItemCategoryBinding.textCategory.setOnClickListener(this);
        }

        public void bindData(ObservableList<Movie> movies, String category) {
            mItemCategoryBinding.textCategory.setText(category);
            mItemCategoryBinding.setCategoryMovies(movies);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onCategoryClick(mItemCategoryBinding.textCategory.getText().toString());
            }
        }

        @Override
        public void onMovieClickListener(Movie movie) {
            mListener.onMovieClick(movie);
        }
    }

    public interface CategoryListener {
        void onCategoryClick(String category);

        void onMovieClick(Movie movie);
    }
}
