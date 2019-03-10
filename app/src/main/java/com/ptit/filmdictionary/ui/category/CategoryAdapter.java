package com.ptit.filmdictionary.ui.category;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ObservableFloat;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.databinding.ItemRecyclerCategoryLayoutBinding;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<Movie> mMovies;

    public CategoryAdapter(List<Movie> movies) {
        mMovies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemRecyclerCategoryLayoutBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_recycler_category_layout, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(mMovies.get(i));
    }

    @Override
    public int getItemCount() {
        return mMovies == null ? 0 : mMovies.size();
    }

    public void addMovies(List<Movie> movies) {
        mMovies.addAll(movies);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ObservableField<String> mTitle = new ObservableField<>();
        public final ObservableFloat mVoteAverage = new ObservableFloat();
        public final ObservableField<String> mReleaseDate = new ObservableField<>();
        public final ObservableField<String> mOverview = new ObservableField<>();
        public final ObservableField<String> mPosterPath = new ObservableField<>();
        private ItemRecyclerCategoryLayoutBinding mBinding;

        public ViewHolder(ItemRecyclerCategoryLayoutBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }

        public void bindData(Movie movie) {
            if (mBinding.getViewHolder() == null) mBinding.setViewHolder(this);
            mTitle.set(movie.getTitle());
            mVoteAverage.set((float) movie.getVoteAverage());
            mReleaseDate.set(movie.getReleaseDate());
            mOverview.set(movie.getOverview());
            mPosterPath.set(movie.getPosterPath());
        }
    }
}
