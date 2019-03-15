package com.ptit.filmdictionary.ui.movie_detail.casts;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.model.Actor;
import com.ptit.filmdictionary.databinding.ItemRecyclerCastBinding;

import java.util.List;

public class CastsRecyclerAdapter extends RecyclerView.Adapter<CastsRecyclerAdapter.ViewHolder> {
    private List<Actor> mActors;
    private OnClickCastListener mListener;

    public CastsRecyclerAdapter(List<Actor> actors, OnClickCastListener listener) {
        mActors = actors;
        mListener = listener;
    }

    public void setActors(List<Actor> actors) {
        mActors = actors;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemRecyclerCastBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_recycler_cast, viewGroup, false);
        return new ViewHolder(binding, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(mActors.get(i));
    }

    @Override
    public int getItemCount() {
        return mActors == null ? 0 : mActors.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemRecyclerCastBinding mBinding;
        private OnClickCastListener mListener;
        private ItemCastViewModel mViewModel;

        public ViewHolder(ItemRecyclerCastBinding binding, OnClickCastListener listener) {
            super(binding.getRoot());
            mBinding = binding;
            mListener = listener;
            mViewModel = new ItemCastViewModel();
            mBinding.setViewModel(mViewModel);
            mBinding.getRoot().setOnClickListener(v -> mListener
                    .onCastClick(mBinding.getViewModel().mActor.get()));
        }

        public void bindData(Actor actor) {
            mViewModel.mActor.set(actor);
        }
    }

    interface OnClickCastListener {
        void onCastClick(Actor actor);
    }
}
