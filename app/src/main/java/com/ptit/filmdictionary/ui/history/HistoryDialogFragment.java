package com.ptit.filmdictionary.ui.history;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.base.BaseRecyclerViewAdapter;
import com.ptit.filmdictionary.data.model.History;
import com.ptit.filmdictionary.data.source.HistoryRepository;
import com.ptit.filmdictionary.databinding.FragmentDialogHistoryBinding;
import com.ptit.filmdictionary.ui.history.adapter.HistoryAdapter;

public class HistoryDialogFragment extends BottomSheetDialogFragment implements
        BaseRecyclerViewAdapter.ItemListener<History> {
    private FragmentDialogHistoryBinding mBinding;
    private static HistoryDialogFragment sInstance;
    private HistoryViewModel mViewModel;
    private HistoryAdapter mAdapter;
    private HistoryClickListener mListener;

    public static HistoryDialogFragment getInstance() {
        if (sInstance == null) {
            sInstance = new HistoryDialogFragment();
        }
        return sInstance;
    }

    public void setListener(HistoryClickListener listener) {
        mListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_history, container,
                false);
        initRecycler();
        initViewModel();
        return mBinding.getRoot();
    }

    private void initViewModel() {
        mViewModel = new HistoryViewModel(HistoryRepository.getInstance());
        mBinding.setViewModel(mViewModel);
    }

    private void initRecycler() {
        mAdapter = new HistoryAdapter();
        mAdapter.setItemListener(this);
        mBinding.recyclerHistory.setAdapter(mAdapter);
    }

    @Override
    public void onItemClicked(History history, int position) {
        mListener.onHistoryClickListener(history);
    }

    @Override
    public void onElementClicked(History history, int position) {
        mViewModel.deleteHistories(history);
        mAdapter.removeItem(position);
    }

    public interface HistoryClickListener {
        void onHistoryClickListener(History history);
    }
}
