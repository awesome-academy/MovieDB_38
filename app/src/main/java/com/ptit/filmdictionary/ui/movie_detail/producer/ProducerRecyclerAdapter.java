package com.ptit.filmdictionary.ui.movie_detail.producer;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.model.Company;
import com.ptit.filmdictionary.databinding.ItemRecyclerProducerBinding;

import java.util.List;

public class ProducerRecyclerAdapter extends RecyclerView.Adapter<ProducerRecyclerAdapter.ViewHolder> {
    private List<Company> mCompanies;
    private OnProducerListener mListener;

    public ProducerRecyclerAdapter(List<Company> companies, OnProducerListener listener) {
        mCompanies = companies;
        mListener = listener;
    }

    public void setCompanies(List<Company> companies) {
        mCompanies = companies;
        notifyItemRangeChanged(0, mCompanies.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemRecyclerProducerBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_recycler_producer, viewGroup, false);
        return new ViewHolder(binding, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(mCompanies.get(i));
    }

    @Override
    public int getItemCount() {
        return mCompanies == null ? 0 : mCompanies.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemProducerViewModel mViewModel;
        private OnProducerListener mListener;
        private ItemRecyclerProducerBinding mBinding;

        public ViewHolder(ItemRecyclerProducerBinding binding, OnProducerListener listener) {
            super(binding.getRoot());
            mBinding = binding;
            mViewModel = new ItemProducerViewModel();
            mBinding.setViewModel(mViewModel);
            mListener = listener;
            itemView.setOnClickListener(v -> mListener
                    .onProducerClick(mBinding.getViewModel().mCompany.get()));
        }

        public void bindData(Company company) {
            mViewModel.mCompany.set(company);
        }
    }

    interface OnProducerListener {
        void onProducerClick(Company company);
    }
}
