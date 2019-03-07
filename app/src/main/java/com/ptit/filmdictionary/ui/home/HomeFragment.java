package com.ptit.filmdictionary.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.ptit.filmdictionary.BR;
import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.base.BaseFragment;
import com.ptit.filmdictionary.data.model.Genre;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.databinding.FragmentHomeBinding;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> implements HomeNavigator {
    private static final CharSequence TITTLE_SPACE = " ";
    private static final int DEFAULT_SCROLL_RANGE = -1;
    private static HomeFragment sInstance;
    private HomeViewModel mHomeViewModel;
    private FragmentHomeBinding mFragmentHomeBinding;

    @Override
    protected HomeViewModel getViewModel() {
        if (mHomeViewModel == null) {
            mHomeViewModel = new HomeViewModel(null);
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
    public void showMovies(Genre genre) {

    }

    @Override
    public void showMovieDetail(Movie movie) {

    }
}
