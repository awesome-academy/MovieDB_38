package com.ptit.filmdictionary.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.TextView;

import com.ptit.filmdictionary.BR;
import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.base.BaseActivity;
import com.ptit.filmdictionary.base.BaseRecyclerViewAdapter;
import com.ptit.filmdictionary.data.model.History;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.data.source.HistoryRepository;
import com.ptit.filmdictionary.data.source.MovieRepository;
import com.ptit.filmdictionary.data.source.local.MovieLocalDataSource;
import com.ptit.filmdictionary.data.source.remote.MovieRemoteDataSource;
import com.ptit.filmdictionary.databinding.ActivitySearchBinding;
import com.ptit.filmdictionary.ui.history.HistoryDialogFragment;
import com.ptit.filmdictionary.ui.movie_detail.MovieDetailActivity;
import com.ptit.filmdictionary.ui.search.adapter.SearchAdapter;

public class SearchActivity extends BaseActivity<ActivitySearchBinding, SearchViewModel> implements
        SearchNavigator, TextWatcher, BaseRecyclerViewAdapter.ItemListener<Movie>, View.OnClickListener,
        TextView.OnEditorActionListener, HistoryDialogFragment.HistoryClickListener {
    private SearchViewModel mSearchViewModel;
    private ActivitySearchBinding mBinding;
    private boolean mIsScrolling;

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
        mBinding.recyclerSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    mIsScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mBinding
                        .recyclerSearch.getLayoutManager();
                if (mIsScrolling && linearLayoutManager.findLastVisibleItemPosition() == adapter.getItemCount() - 1) {
                    loadMore();
                }
            }
        });
    }

    private void loadMore() {
        mIsScrolling = false;
        mSearchViewModel.loadMore();
    }

    private void initEvents() {
        mBinding.textSearch.addTextChangedListener(this);
        mBinding.textSearch.setOnEditorActionListener(this);
        mBinding.imageHistory.setOnClickListener(this);
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

    public void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE
        );
        imm.hideSoftInputFromWindow(mBinding.textSearch.getWindowToken(), 0);
    }

    @Override
    protected SearchViewModel getViewModel() {
        if (mSearchViewModel == null) {
            mSearchViewModel = new SearchViewModel(MovieRepository.getInstance(
                    MovieRemoteDataSource.getInstance(this),
                    MovieLocalDataSource.getInstance(this)),
                    HistoryRepository.getInstance()
            );
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
    public void onElementClicked(Movie movie, int position) {

    }

    @Override
    protected void onDestroy() {
        mSearchViewModel.dispose();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        openHistoryDialog();
    }

    private void openHistoryDialog() {
        HistoryDialogFragment fragment = HistoryDialogFragment.getInstance();
        fragment.setListener(this);
        fragment.show(getSupportFragmentManager(), fragment.getTag());
    }

    private void hideHistoryDialog() {
        HistoryDialogFragment fragment = HistoryDialogFragment.getInstance();
        fragment.dismiss();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            String query = v.getText().toString();
            if (query.isEmpty()) return false;
            mSearchViewModel.saveHistory(query);
            hideSoftKeyboard();
            return true;
        }
        return false;
    }

    @Override
    public void onHistoryClickListener(History history) {
        mBinding.textSearch.setText(history.getTitle());
        hideHistoryDialog();
        mSearchViewModel.loadResultByKeyword(history.getTitle());
    }
}
