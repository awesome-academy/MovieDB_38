package com.ptit.filmdictionary.ui.movie_detail.trailer;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.databinding.FragmentTrailerBinding;
import com.ptit.filmdictionary.ui.movie_detail.MovieDetailViewModel;
import com.ptit.filmdictionary.ui.movie_detail.OnTrailerListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrailerFragment extends Fragment {
    private MovieDetailViewModel mViewModel;
    private FragmentTrailerBinding mBinding;
    private OnTrailerListener mListener;

    public TrailerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_trailer, container, false);
        mBinding.setViewModel(mViewModel);
        initRecyclerView();
        return mBinding.getRoot();
    }

    public void setViewModel(MovieDetailViewModel viewModel) {
        mViewModel = viewModel;
    }

    public void setListener(OnTrailerListener listener) {
        mListener = listener;
    }

    private void initRecyclerView() {
        mBinding.recyclerTrailer.setAdapter(new TrailerRecyclerAdapter(new ArrayList<>(), mListener));
    }

    public static TrailerFragment getInstance() {
        return new TrailerFragment();
    }
}
