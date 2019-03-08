package com.ptit.filmdictionary.ui.category;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.data.source.MovieRepository;
import com.ptit.filmdictionary.data.source.local.MovieLocalDataSource;
import com.ptit.filmdictionary.data.source.remote.MovieRemoteDataSource;
import com.ptit.filmdictionary.databinding.ActivityCategoryBinding;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity implements CategoryNavigator,
        CategoryAdapter.ItemClickListener {
    public static final String BUNDLE_CATEGORY_KEY = "BUNDLE_CATEGORY_KEY";
    public static final String EXTRA_AGRS = "com.ptit.filmdictionary.extras.EXTRA_ARGS";
    private static final String BUNDLE_CATEGORY_TITLE = "BUNDLE_CATEGORY_TITLE";
    protected String mActionBarTitle;
    protected CategoryViewModel mViewModel;
    private ActivityCategoryBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_category);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            setTextStatusBarColor();
        }
        initViewModel();
        initActionBar();
        setUpRecycler();
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
                //Todo: start activity Search
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void initActionBar() {
        setSupportActionBar(mBinding.toolbarCategory);
        getSupportActionBar().setTitle(mActionBarTitle);
        mBinding.toolbarCategory.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        mBinding.toolbarCategory.setNavigationOnClickListener(v -> onBackPressed());
    }

    protected void setUpRecycler() {
        final CategoryAdapter adapter = new CategoryAdapter(new ArrayList<Movie>(), this);
        mBinding.recyclerMovies.setAdapter(adapter);

        mBinding.recyclerMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager manager
                        = (LinearLayoutManager) mBinding.recyclerMovies.getLayoutManager();
                if (manager.findLastCompletelyVisibleItemPosition() == adapter.getItemCount() - 1) {
                    //bottom of list!
                    hideLoadMore(false);
                    int nextPage = mViewModel.getPage();
                    ++nextPage;
                    mViewModel.loadMoviesByCategory(nextPage);
                }
            }
        });
    }

    protected void initViewModel() {
        Bundle bundle = getIntent().getBundleExtra(EXTRA_AGRS);
        String categoryKey = bundle.getString(BUNDLE_CATEGORY_KEY);
        mActionBarTitle = bundle.getString(BUNDLE_CATEGORY_TITLE);
        mViewModel = new CategoryViewModel(categoryKey,
                MovieRepository.getInstance(MovieRemoteDataSource.getInstance(this),
                        MovieLocalDataSource.getInstance(this)), this);

        mBinding.setViewModel(mViewModel);
        mViewModel.loadMoviesByCategory(mViewModel.getPage());
    }

    public static Intent getInstance(Context context, String categoryKey, String categoryTitle) {
        Intent intent = new Intent(context, CategoryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_CATEGORY_KEY, categoryKey);
        bundle.putString(BUNDLE_CATEGORY_TITLE, categoryTitle);
        intent.putExtra(EXTRA_AGRS, bundle);
        return intent;
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
    protected void onDestroy() {
        mViewModel.destroy();
        super.onDestroy();
    }

    @Override
    public void onItemMovieClick(Movie movie) {
        //TODO : start activity MovieDetail
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setTextStatusBarColor() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
}
