package com.ptit.filmdictionary.ui.history.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.base.BaseRecyclerViewAdapter;
import com.ptit.filmdictionary.data.model.History;
import com.ptit.filmdictionary.databinding.ItemHistoryBinding;
import com.ptit.filmdictionary.ui.history.ItemHistoryViewModel;

public class HistoryAdapter extends BaseRecyclerViewAdapter<History, HistoryAdapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemHistoryBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_history,
                viewGroup, false);
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
        private ItemHistoryBinding mBinding;
        private ItemListener<History> mListener;

        public ViewHolder(ItemHistoryBinding binding, ItemListener<History> listener) {
            super(binding.getRoot());
            mBinding = binding;
            mListener = listener;
            mBinding.setViewModel(new ItemHistoryViewModel());
        }

        private void bindData(History history) {
            mBinding.getViewModel().historyObservableField.set(history);
            mBinding.imageDelete.setOnClickListener(this);
            mBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener == null) return;
            switch (v.getId()) {
                case R.id.image_delete:
                    mListener.onElementClicked(mBinding.getViewModel().historyObservableField.get(),
                            getAdapterPosition());
                    break;
                default:
                    mListener.onItemClicked(mBinding.getViewModel().historyObservableField.get(),
                            getAdapterPosition());
            }

        }
    }
}
