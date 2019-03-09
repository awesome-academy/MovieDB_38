package com.ptit.filmdictionary.ui.genre;

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

public class GenreActivity extends BaseMoviesActivity<CategoryViewModel, CategoryAdapter> {
    public static final String BUNDLE_GENRE_KEY = "BUNDLE_GENRE_KEY";

    @Override
    protected void initViewModel() {
        Bundle bundle = getIntent().getBundleExtra(EXTRA_AGRS);
        String genreKey = bundle.getString(BUNDLE_GENRE_KEY);
        mActionBarTitle = bundle.getString(BUNDLE_ACTION_BAR_TITLE);
        mViewModel = new CategoryViewModel(MovieRepository.getInstance(MovieRemoteDataSource.getInstance(this),
                MovieLocalDataSource.getInstance(this)), this);
        mViewModel.setGenreKey(genreKey);
        mBinding.setViewModel(mViewModel);
        mViewModel.loadMoviesByGenre(mViewModel.getPage());
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
        mViewModel.loadMoviesByGenre(nextPage);
    }

    public static Intent getIntent(Context context, String genreKey, String genreTitle) {
        Intent intent = new Intent(context, GenreActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_GENRE_KEY, genreKey);
        bundle.putString(BUNDLE_ACTION_BAR_TITLE, genreTitle);
        intent.putExtra(EXTRA_AGRS, bundle);
        return intent;
    }

    @Override
    protected void onDestroy() {
        mViewModel.destroy();
        super.onDestroy();
    }
}
