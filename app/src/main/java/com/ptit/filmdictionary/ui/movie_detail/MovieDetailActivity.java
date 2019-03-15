package com.ptit.filmdictionary.ui.movie_detail;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.source.MovieRepository;
import com.ptit.filmdictionary.data.source.local.MovieLocalDataSource;
import com.ptit.filmdictionary.data.source.remote.MovieRemoteDataSource;
import com.ptit.filmdictionary.databinding.ActivityMovieDetailBinding;
import com.ptit.filmdictionary.ui.movie_detail.casts.CastFragment;
import com.ptit.filmdictionary.ui.movie_detail.info.MovieInfoFragment;
import com.ptit.filmdictionary.ui.movie_detail.trailer.TrailerFragment;

public class MovieDetailActivity extends AppCompatActivity
        implements OnTrailerListener {

    private static final String BUNDLE_MOVIE_ID = "BUNDLE_MOVIE_ID";
    private static final String EXTRA_MOVIE_DETAIL = "com.ptit.filmdictionary.extras.EXTRA_MOVIE_DETAIL";
    private static final String BUNDLE_MOVIE_NAME = "BUNDLE_MOVIE_NAME";
    private int mMovieId;
    private String mMovieName;
    private MovieDetailViewModel mViewModel;
    private ActivityMovieDetailBinding mBinding;
    private MoviePageAdapter mPageAdapter;
    private YoutubeFragment mYoutubeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            setTextStatusBarColor();
        }
        recieveData();
        initActionBar();
        initViewModel();
        initViewPager();
        mYoutubeFragment = (YoutubeFragment) getFragmentManager().findFragmentById(R.id.frag_youtube);
    }

    private void initViewPager() {
        mPageAdapter = new MoviePageAdapter(getSupportFragmentManager());

        MovieInfoFragment infoFragment = MovieInfoFragment.newInstance();
        infoFragment.setViewModel(mViewModel);
        TrailerFragment trailerFragment = TrailerFragment.getInstance();
        trailerFragment.setViewModel(mViewModel);
        trailerFragment.setListener(this);
        CastFragment castFragment = CastFragment.getInstance();
        castFragment.setViewModel(mViewModel);

        mPageAdapter.addFragment(infoFragment);
        mPageAdapter.addFragment(trailerFragment);
        mPageAdapter.addFragment(castFragment);
        mBinding.viewPager.setAdapter(mPageAdapter);
        mBinding.tabsMovieDetail.setupWithViewPager(mBinding.viewPager);
    }

    private void initViewModel() {
        mViewModel = new MovieDetailViewModel(MovieRepository.getInstance(MovieRemoteDataSource.getInstance(this),
                MovieLocalDataSource.getInstance(this)), this);
        mViewModel.loadMovieDetail(mMovieId);
    }

    private void initActionBar() {
        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setTitle(mMovieName);
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

    @Override
    public void onCreateTrailer(String mTrailerKey) {
        mYoutubeFragment.setTrailerId(mTrailerKey);
    }

    @Override
    public void onPlayTrailer(String mTrailerKey) {
        mYoutubeFragment.setTrailerId(mTrailerKey);
        mYoutubeFragment.playTrailer();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setTextStatusBarColor() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mYoutubeFragment.setFullScreen(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE);
    }
}
