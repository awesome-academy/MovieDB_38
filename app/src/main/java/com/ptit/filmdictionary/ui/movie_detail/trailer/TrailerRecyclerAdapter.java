package com.ptit.filmdictionary.ui.movie_detail.trailer;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.model.Video;
import com.ptit.filmdictionary.databinding.ItemRecyclerTrailerBinding;
import com.ptit.filmdictionary.ui.movie_detail.OnTrailerListener;

import java.util.List;

public class TrailerRecyclerAdapter extends RecyclerView.Adapter<TrailerRecyclerAdapter.ViewHolder> {
    private List<Video> mVideos;
    private OnTrailerListener mListener;

    public TrailerRecyclerAdapter(List<Video> videos, OnTrailerListener listener) {
        mVideos = videos;
        mListener = listener;
    }

    public void setVideos(List<Video> videos) {
        mVideos = videos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemRecyclerTrailerBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_recycler_trailer, viewGroup, false);
        return new ViewHolder(binding, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindDate(mVideos.get(i));
    }

    @Override
    public int getItemCount() {
        return mVideos == null ? 0 : mVideos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemTrailerViewModel mViewModel;
        private ItemRecyclerTrailerBinding mBinding;
        private OnTrailerListener mListener;

        public ViewHolder(ItemRecyclerTrailerBinding binding, OnTrailerListener listener) {
            super(binding.getRoot());
            mBinding = binding;
            mViewModel = new ItemTrailerViewModel();
            mBinding.setViewModel(mViewModel);
            mListener = listener;
            mBinding.getRoot().setOnClickListener(v -> mListener
                    .onPlayTrailer(mBinding.getViewModel().mVideo.get().getKey()));
        }

        public void bindDate(Video video) {
            mViewModel.mVideo.set(video);
        }
    }
}
