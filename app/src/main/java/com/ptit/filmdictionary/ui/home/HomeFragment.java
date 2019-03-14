package com.ptit.filmdictionary.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ptit.filmdictionary.BR;
import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.base.BaseFragment;
import com.ptit.filmdictionary.data.model.CategoryKey;
import com.ptit.filmdictionary.data.model.CategoryName;
import com.ptit.filmdictionary.data.model.Genre;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.data.source.MovieRepository;
import com.ptit.filmdictionary.data.source.local.MovieLocalDataSource;
import com.ptit.filmdictionary.data.source.remote.MovieRemoteDataSource;
import com.ptit.filmdictionary.databinding.FragmentHomeBinding;
import com.ptit.filmdictionary.ui.category.CategoryActivity;
import com.ptit.filmdictionary.ui.genre.GenreActivity;
import com.ptit.filmdictionary.ui.home.adapter.HomeCategoryAdapter;
import com.ptit.filmdictionary.ui.home.adapter.SlideAdapter;
import com.ptit.filmdictionary.ui.movie_detail.MovieDetailActivity;
import com.ptit.filmdictionary.ui.movie_detail.info.GenreRecylerAdapter;
import com.ptit.filmdictionary.ui.search.SearchActivity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> implements HomeNavigator,
        HomeCategoryAdapter.CategoryListener, View.OnClickListener, SlideAdapter.SlideListener,
        ViewPager.OnPageChangeListener, GenreRecylerAdapter.ItemClickListener {
    private static final CharSequence TITTLE_SPACE = " ";
    private static final int DEFAULT_SCROLL_RANGE = -1;
    private static final int NUM_SLIDE = 5;
    private static final long PERIOD_TIME_SLIDE = 3000;
    private static final long DELAY_TIME_SLIDE = 100;
    private static HomeFragment sInstance;
    private HomeViewModel mHomeViewModel;
    private FragmentHomeBinding mFragmentHomeBinding;
    private SlideAdapter mSlideAdapter;
    private int mCurrentSlide;

    @Override
    protected HomeViewModel getViewModel() {
        if (mHomeViewModel == null) {
            mHomeViewModel = new HomeViewModel(MovieRepository.getInstance(
                    MovieRemoteDataSource.getInstance(getActivity()),
                    MovieLocalDataSource.getInstance(getActivity())));
        }
        return mHomeViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomeViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentHomeBinding = getViewDataBinding();
        hideExpandedTittle();
        initAdapter();
        registerEvents();
    }

    private void registerEvents() {
        mFragmentHomeBinding.textGenres.setOnClickListener(this);
    }

    private void initAdapter() {
        mSlideAdapter = new SlideAdapter(this);
        mFragmentHomeBinding.viewPager.setAdapter(mSlideAdapter);
        mFragmentHomeBinding.viewPager.addOnPageChangeListener(this);
        mCurrentSlide = mSlideAdapter.getCurrentSlide();
        initTimerChangeSlide();
        mFragmentHomeBinding.tabLayout.setupWithViewPager(mFragmentHomeBinding.viewPager, true);
        mFragmentHomeBinding.recyclerCategory.setAdapter(new HomeCategoryAdapter(this));
        mFragmentHomeBinding.recyclerCategory.setNestedScrollingEnabled(false);
        mFragmentHomeBinding.recyclerGenre.setAdapter(new GenreRecylerAdapter(new ArrayList<>(), this));
    }

    private void initTimerChangeSlide() {
        Handler handler = new Handler();
        Runnable update = () -> {
            if (mCurrentSlide == NUM_SLIDE) {
                mCurrentSlide = 0;
            }
            mFragmentHomeBinding.viewPager.setCurrentItem(mCurrentSlide++, true);
        };
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, DELAY_TIME_SLIDE, PERIOD_TIME_SLIDE);
    }

    @Override
    protected int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    private void hideExpandedTittle() {
        mFragmentHomeBinding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = DEFAULT_SCROLL_RANGE;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == DEFAULT_SCROLL_RANGE) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    mFragmentHomeBinding.collapsingToolbar.setTitle(getString(R.string.app_name));
                    mFragmentHomeBinding.imageSearch.setVisibility(View.VISIBLE);
                    mFragmentHomeBinding.imageSearch.setColorFilter(ContextCompat.getColor(getActivity(),
                            R.color.color_black),
                            android.graphics.PorterDuff.Mode.SRC_IN);
                    isShow = true;
                } else if (isShow) {
                    mFragmentHomeBinding.collapsingToolbar.setTitle(TITTLE_SPACE);
                    mFragmentHomeBinding.imageSearch.setVisibility(View.GONE);
                    isShow = false;
                }
            }
        });
    }

    public static HomeFragment getInstance() {
        if (sInstance == null) {
            sInstance = new HomeFragment();
        }
        return sInstance;
    }

    @Override
    public void startCategoryActivity(String categoryKey, String categoryTitle) {
        startActivity(CategoryActivity.getIntent(getActivity(), categoryKey, categoryTitle));
    }

    @Override
    public void startGenreActivity(String genreKey, String genreTitle) {
        startActivity(GenreActivity.getIntent(getActivity(), genreKey, genreTitle));
    }

    @Override
    public void startMovieDetailActivity(Movie movie) {
        startActivity(MovieDetailActivity.getIntent(getActivity(), movie.getId(), movie.getTitle()));
    }

    @Override
    public void startSearchActivity() {
        startActivity(SearchActivity.getIntent(getActivity()));
    }

    @Override
    public void onCategoryClick(String category) {
        switch (category) {
            case CategoryName.TITLE_UP_COMING:
                startCategoryActivity(CategoryKey.CATEGORY_UP_COMING, CategoryName.TITLE_UP_COMING);
                break;
            case CategoryName.TITLE_POPULAR:
                startCategoryActivity(CategoryKey.CATEGORY_POPULAR, CategoryName.TITLE_POPULAR);
                break;
            case CategoryName.TITLE_TOP_RATE:
                startCategoryActivity(CategoryKey.CATEGORY_TOP_RATE, CategoryName.TITLE_TOP_RATE);
                break;
            case CategoryName.TITLE_NOW_PLAYING:
                startCategoryActivity(CategoryKey.CATEGORY_NOW_PLAYING, CategoryName.TITLE_NOW_PLAYING);
                break;
            default:
                break;
        }
    }

    @Override
    public void onMovieClick(Movie movie) {
        startMovieDetailActivity(movie);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_genres:
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        mHomeViewModel.dispose();
        super.onDestroy();
    }

    @Override
    public void onSlideClickListener(Movie movie) {
        startMovieDetailActivity(movie);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        mCurrentSlide = i;
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onItemClick(Genre genre) {
        startGenreActivity(genre.getId(), genre.getName());
    }
}
