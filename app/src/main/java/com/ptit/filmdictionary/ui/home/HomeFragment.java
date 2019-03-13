package com.ptit.filmdictionary.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.ptit.filmdictionary.BR;
import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.base.BaseFragment;
import com.ptit.filmdictionary.data.model.CategoryKey;
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
import com.ptit.filmdictionary.utils.Constants;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> implements HomeNavigator,
        HomeCategoryAdapter.CategoryListener, View.OnClickListener, SlideAdapter.SlideListener,
        ViewPager.OnPageChangeListener {
    private static final CharSequence TITTLE_SPACE = " ";
    private static final int DEFAULT_SCROLL_RANGE = -1;
    private static HomeFragment sInstance;
    private HomeViewModel mHomeViewModel;
    private FragmentHomeBinding mFragmentHomeBinding;

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
        mFragmentHomeBinding.viewPager.setAdapter(new SlideAdapter(this));
        mFragmentHomeBinding.viewPager.addOnPageChangeListener(this);
        mFragmentHomeBinding.tabLayout.setupWithViewPager(mFragmentHomeBinding.viewPager, true);
        mFragmentHomeBinding.recyclerCategory.setAdapter(new HomeCategoryAdapter(this));
        mFragmentHomeBinding.recyclerCategory.setNestedScrollingEnabled(false);
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

    }

    @Override
    public void startSearchActivity() {

    }

    @Override
    public void onCategoryClick(String category) {
        switch (category){
            case Constants.TITLE_UP_COMING:
                startCategoryActivity(CategoryKey.CATEGORY_UP_COMING, Constants.TITLE_UP_COMING);
                break;
            case Constants.TITLE_POPULAR:
                startCategoryActivity(CategoryKey.CATEGORY_POPULAR, Constants.TITLE_POPULAR);
                break;
            case Constants.TITLE_TOP_RATE:
                startCategoryActivity(CategoryKey.CATEGORY_TOP_RATE, Constants.TITLE_TOP_RATE);
                break;
            case Constants.TITLE_NOW_PLAYING:
                startCategoryActivity(CategoryKey.CATEGORY_NOW_PLAYING, Constants.TITLE_NOW_PLAYING);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
