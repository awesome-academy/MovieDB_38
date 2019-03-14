package com.ptit.filmdictionary.ui.search.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.base.BaseRecyclerViewAdapter;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.databinding.ItemHorizontalMovieBinding;
import com.ptit.filmdictionary.ui.search.HorizontalMovieViewModel;

public class SearchAdapter extends BaseRecyclerViewAdapter<Movie, SearchAdapter.ViewHolder> {

    public SearchAdapter() {
        super();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemHorizontalMovieBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.item_horizontal_movie, viewGroup, false);
        return new ViewHolder(binding, mItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(mData.get(i));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemHorizontalMovieBinding mBinding;
        private ItemListener<Movie> mListener;

        public ViewHolder(ItemHorizontalMovieBinding binding, ItemListener<Movie> listener) {
            super(binding.getRoot());
            mBinding = binding;
            mListener = listener;
            mBinding.setViewModel(new HorizontalMovieViewModel());
        }

        public void bindData(Movie movie) {
            mBinding.getViewModel().movieObservableField.set(movie);
            mBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClicked(mBinding.getViewModel().movieObservableField.get(),
                        getAdapterPosition());
            }
        }
    }

}
