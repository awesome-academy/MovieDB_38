package com.ptit.filmdictionary.ui.movie_detail.producer;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.model.Company;
import com.ptit.filmdictionary.databinding.FragmentProducerBinding;
import com.ptit.filmdictionary.ui.movie_detail.MovieDetailViewModel;
import com.ptit.filmdictionary.ui.producer.ProducerActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProducerFragment extends Fragment implements ProducerRecyclerAdapter.OnProducerListener {
    private MovieDetailViewModel mViewModel;
    private FragmentProducerBinding mBinding;

    public ProducerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_producer, container, false);
        mBinding.setViewModel(mViewModel);
        initRecyclerView();
        return mBinding.getRoot();
    }

    public void setViewModel(MovieDetailViewModel viewModel) {
        mViewModel = viewModel;
    }

    private void initRecyclerView() {
        mBinding.recyclerProducer.setAdapter(new ProducerRecyclerAdapter(new ArrayList<>(), this));
    }

    public static ProducerFragment getInstance() {
        return new ProducerFragment();
    }

    @Override
    public void onProducerClick(Company company) {
        startActivity(ProducerActivity.getIntent(getContext(), company.getId(), company.getName()));
    }
}
