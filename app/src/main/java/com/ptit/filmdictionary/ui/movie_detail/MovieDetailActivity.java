package com.ptit.filmdictionary.ui.movie_detail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.source.MovieRepository;
import com.ptit.filmdictionary.data.source.local.MovieLocalDataSource;
import com.ptit.filmdictionary.data.source.remote.MovieRemoteDataSource;
import com.ptit.filmdictionary.databinding.ActivityMovieDetailBinding;
import com.ptit.filmdictionary.ui.movie_detail.info.MovieInfoFragment;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String BUNDLE_MOVIE_ID = "BUNDLE_MOVIE_ID";
    private static final String EXTRA_MOVIE_DETAIL = "com.ptit.filmdictionary.extras.EXTRA_MOVIE_DETAIL";
    private static final String BUNDLE_MOVIE_NAME = "BUNDLE_MOVIE_NAME";
    private int mMovieId;
    private String mMovieName;
    private MovieDetailViewModel mViewModel;
    private ActivityMovieDetailBinding mBinding;
    private MoviePageAdapter mPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        recieveData();
        initActionBar();
        initViewModel();
        initViewPager();
    }

    private void initViewPager() {
        mPageAdapter = new MoviePageAdapter(getSupportFragmentManager());
        MovieInfoFragment infoFragment = MovieInfoFragment.newInstance();
        infoFragment.setViewModel(mViewModel);
        mPageAdapter.addFragment(infoFragment);
        mBinding.viewPager.setAdapter(mPageAdapter);
    }

    private void initViewModel() {
        mViewModel = new MovieDetailViewModel(MovieRepository.getInstance(MovieRemoteDataSource.getInstance(this),
                MovieLocalDataSource.getInstance(this)));
        mViewModel.loadMovieDetail(mMovieId);
    }

    private void initActionBar() {
        setSupportActionBar(mBinding.toolbar);
        mBinding.toolbar.setTitle(mMovieName);
        mBinding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        mBinding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                //todo: start searchactivity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void recieveData() {
        Bundle bundle = getIntent().getBundleExtra(EXTRA_MOVIE_DETAIL);
        mMovieId = bundle.getInt(BUNDLE_MOVIE_ID);
        mMovieName = bundle.getString(BUNDLE_MOVIE_NAME);
    }

    public static Intent getIntent(Context context, int movieId, String movieName) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_MOVIE_ID, movieId);
        bundle.putString(BUNDLE_MOVIE_NAME, movieName);
        intent.putExtra(EXTRA_MOVIE_DETAIL, bundle);
        return intent;
    }

    @Override
    protected void onDestroy() {
        mViewModel.destroy();
        super.onDestroy();
    }
}
