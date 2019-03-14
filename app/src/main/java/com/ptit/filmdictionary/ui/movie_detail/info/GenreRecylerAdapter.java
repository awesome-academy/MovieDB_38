package com.ptit.filmdictionary.ui.movie_detail.info;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.model.Genre;
import com.ptit.filmdictionary.databinding.ItemRecyclerGenreBinding;

import java.util.List;

public class GenreRecylerAdapter extends RecyclerView.Adapter<GenreRecylerAdapter.ViewHolder> {
    private List<Genre> mGenres;
    private ItemClickListener mListener;

    public GenreRecylerAdapter(List<Genre> genres, ItemClickListener listener) {
        mGenres = genres;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemRecyclerGenreBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_recycler_genre, viewGroup, false);
        return new ViewHolder(binding, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(mGenres.get(i));
    }

    @Override
    public int getItemCount() {
        return mGenres == null ? 0 : mGenres.size();
    }

    public void setGenres(List<Genre> genres) {
        mGenres = genres;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemRecyclerGenreBinding mBinding;
        ItemRecyclerGenreViewModel mViewModel;

        public ViewHolder(ItemRecyclerGenreBinding binding, ItemClickListener listener) {
            super(binding.getRoot());
            mBinding = binding;
            mViewModel = new ItemRecyclerGenreViewModel();
            mBinding.setViewModel(mViewModel);
            mBinding.getRoot().setOnClickListener(v ->
                    listener.onItemClick(mBinding.getViewModel().mGenre.get()));
        }

        public void bindData(Genre genre) {
            mViewModel.mGenre.set(genre);
        }
    }

    public interface ItemClickListener {
        void onItemClick(Genre genre);
    }
}
