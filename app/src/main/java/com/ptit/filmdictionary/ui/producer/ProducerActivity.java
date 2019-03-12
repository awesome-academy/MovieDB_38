package com.ptit.filmdictionary.ui.producer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ptit.filmdictionary.base.BaseMoviesActivity;
import com.ptit.filmdictionary.data.source.MovieRepository;
import com.ptit.filmdictionary.data.source.local.MovieLocalDataSource;
import com.ptit.filmdictionary.data.source.remote.MovieRemoteDataSource;
import com.ptit.filmdictionary.ui.category.CategoryAdapter;
import com.ptit.filmdictionary.ui.category.CategoryViewModel;

import java.util.ArrayList;

public class ProducerActivity extends BaseMoviesActivity<CategoryViewModel, CategoryAdapter> {

    private static final String BUNDLE_PRODUCER_KEY = "BUNDLE_PRODUCER_KEY";

    @Override
    protected void initViewModel() {
        Bundle bundle = getIntent().getBundleExtra(EXTRA_AGRS);
        int producerKey = bundle.getInt(BUNDLE_PRODUCER_KEY);
        mActionBarTitle = bundle.getString(BUNDLE_ACTION_BAR_TITLE);
        mViewModel = new CategoryViewModel(MovieRepository.getInstance(MovieRemoteDataSource.getInstance(this),
                MovieLocalDataSource.getInstance(this)), this);
        mViewModel.setProducerKey(producerKey);
        mBinding.setViewModel(mViewModel);
        mViewModel.loadMoviesByProducer(mViewModel.getPage());
    }

    @Override
    protected void initRecyclerAdapter() {
        mAdapter = new CategoryAdapter(new ArrayList<>(), this);
    }

    @Override
    protected void loadMoreMovies() {
        hideLoadMore(false);
        int nextPage = mViewModel.getPage();
        ++nextPage;
        mViewModel.loadMoviesByProducer(nextPage);
    }

    public static Intent getIntent(Context context, int producerKey, String producerTitle) {
        Intent intent = new Intent(context, ProducerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_PRODUCER_KEY, producerKey);
        bundle.putString(BUNDLE_ACTION_BAR_TITLE, producerTitle);
        intent.putExtra(EXTRA_AGRS, bundle);
        return intent;
    }

    @Override
    protected void onDestroy() {
        mViewModel.destroy();
        super.onDestroy();
    }
}
