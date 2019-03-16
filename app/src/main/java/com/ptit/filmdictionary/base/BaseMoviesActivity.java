package com.ptit.filmdictionary.base;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.databinding.ActivityCategoryBinding;
import com.ptit.filmdictionary.ui.category.CategoryAdapter;
import com.ptit.filmdictionary.ui.category.CategoryNavigator;
import com.ptit.filmdictionary.ui.category.CategoryViewModel;
import com.ptit.filmdictionary.ui.main.OnInternetListener;
import com.ptit.filmdictionary.ui.movie_detail.MovieDetailActivity;
import com.ptit.filmdictionary.ui.search.SearchActivity;
import com.ptit.filmdictionary.utils.Constants;

public abstract class BaseMoviesActivity<T, V extends RecyclerView.Adapter> extends AppCompatActivity
        implements CategoryNavigator, CategoryAdapter.ItemClickListener, OnInternetListener {
    public static final String EXTRA_AGRS = "com.ptit.filmdictionary.extras.EXTRA_ARGS";
    public static final String BUNDLE_ACTION_BAR_TITLE = "BUNDLE_ACTION_BAR_TITLE";
    protected String mActionBarTitle;
    protected T mViewModel;
    protected ActivityCategoryBinding mBinding;
    protected V mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_category);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            setTextStatusBarColor();
        }
        initViewModel();
        if (mViewModel instanceof CategoryViewModel) {
            ((CategoryViewModel) mViewModel).setOnInternetListener(this);
        }
        initActionBar();
        setUpRecycler();
    }

    protected abstract void initViewModel();

    protected void setUpRecycler() {
        initRecyclerAdapter();
        mBinding.recyclerMovies.setAdapter(mAdapter);

        mBinding.recyclerMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager manager
                        = (LinearLayoutManager) mBinding.recyclerMovies.getLayoutManager();
                if (manager.findLastCompletelyVisibleItemPosition() == mAdapter.getItemCount() - 1) {
                    //bottom of list!
                    loadMoreMovies();
                }
            }
        });
    }

    protected abstract void initRecyclerAdapter();

    protected abstract void loadMoreMovies();

    private void initActionBar() {
        setSupportActionBar(mBinding.toolbarCategory);
        getSupportActionBar().setTitle(mActionBarTitle);
        mBinding.toolbarCategory.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        mBinding.toolbarCategory.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                startActivity(SearchActivity.getIntent(this));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void hideLoadData(boolean hide) {
        if (hide) mBinding.progressLoadData.setVisibility(View.GONE);
        else mBinding.progressLoadData.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadMore(boolean hide) {
        if (hide) mBinding.progressLoadMore.setVisibility(View.GONE);
        else mBinding.progressLoadMore.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemMovieClick(Movie movie) {
        startActivity(MovieDetailActivity.getIntent(this, movie.getId(), movie.getTitle()));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setTextStatusBarColor() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @Override
    public void onNoInternet() {
        Toast.makeText(this, Constants.NO_INTERNET, Toast.LENGTH_SHORT).show();
    }
}
