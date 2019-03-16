package com.ptit.filmdictionary.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.ptit.filmdictionary.BR;
import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.base.BaseActivity;
import com.ptit.filmdictionary.base.BaseRecyclerViewAdapter;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.data.source.MovieRepository;
import com.ptit.filmdictionary.data.source.local.MovieLocalDataSource;
import com.ptit.filmdictionary.data.source.remote.MovieRemoteDataSource;
import com.ptit.filmdictionary.databinding.ActivitySearchBinding;
import com.ptit.filmdictionary.ui.movie_detail.MovieDetailActivity;
import com.ptit.filmdictionary.ui.search.adapter.SearchAdapter;

public class SearchActivity extends BaseActivity<ActivitySearchBinding, SearchViewModel> implements
        SearchNavigator, TextWatcher, BaseRecyclerViewAdapter.ItemListener<Movie> {
    private SearchViewModel mSearchViewModel;
    private ActivitySearchBinding mBinding;

    @Override
    protected int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            setTextStatusBarColor();
        }
        mBinding = getViewDataBinding();
        setUpActionBar();
        initEvents();
        initRecycler();
    }

    private void initRecycler() {
        SearchAdapter adapter = new SearchAdapter();
        adapter.setItemListener(this);
        mBinding.recyclerSearch.setAdapter(adapter);
    }

    private void initEvents() {
        mBinding.textSearch.addTextChangedListener(this);
    }

    private void setUpActionBar() {
        setSupportActionBar(mBinding.toolbarSearch);
        mBinding.toolbarSearch.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        mBinding.toolbarSearch.setNavigationOnClickListener(a -> onBackPressed());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setTextStatusBarColor() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @Override
    protected SearchViewModel getViewModel() {
        if (mSearchViewModel == null) {
            mSearchViewModel = new SearchViewModel(MovieRepository.getInstance(
                    MovieRemoteDataSource.getInstance(this),
                    MovieLocalDataSource.getInstance(this)
            ));
        }
        return mSearchViewModel;
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_search;
    }

    @Override
    public void startMovieDetailActivity(Movie movie) {
        startActivity(MovieDetailActivity.getIntent(this, movie.getId(), movie.getTitle()));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().isEmpty()) return;
        mSearchViewModel.loadResultByKeyword(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onItemClicked(Movie movie, int position) {
        startMovieDetailActivity(movie);
    }

    @Override
    protected void onDestroy() {
        mSearchViewModel.dispose();
        super.onDestroy();
    }
}
