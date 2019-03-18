package com.ptit.filmdictionary.ui.favorite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.ptit.filmdictionary.BR;
import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.base.BaseFragment;
import com.ptit.filmdictionary.base.BaseRecyclerViewAdapter;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.data.source.MovieRepository;
import com.ptit.filmdictionary.data.source.local.MovieLocalDataSource;
import com.ptit.filmdictionary.data.source.remote.MovieRemoteDataSource;
import com.ptit.filmdictionary.databinding.FragmentFavoriteBinding;
import com.ptit.filmdictionary.ui.favorite.adapter.FavoriteAdapter;
import com.ptit.filmdictionary.ui.movie_detail.MovieDetailActivity;
import com.ptit.filmdictionary.ui.search.SearchActivity;

public class FavoriteFragment extends BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>
        implements FavoriteNavigator, BaseRecyclerViewAdapter.ItemListener<Movie>, View.OnClickListener {
    private FavoriteViewModel mFavoriteViewModel;
    private FragmentFavoriteBinding mBinding;
    private FavoriteAdapter mAdapter;
    private static FavoriteFragment sInstance;

    public static FavoriteFragment getInstance() {
        if (sInstance == null) {
            sInstance = new FavoriteFragment();
        }
        return sInstance;
    }

    @Override
    public void onResume() {
        super.onResume();
        mFavoriteViewModel.loadAllFavorites();
    }

    @Override
    protected FavoriteViewModel getViewModel() {
        if (mFavoriteViewModel == null) {
            mFavoriteViewModel = new FavoriteViewModel(MovieRepository.getInstance(
                    MovieRemoteDataSource.getInstance(getActivity()),
                    MovieLocalDataSource.getInstance(getActivity())
            ));
        }
        return mFavoriteViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFavoriteViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding = getViewDataBinding();
        initRecycler();
        registerEvents();
    }

    private void initRecycler() {
        mAdapter = new FavoriteAdapter();
        mAdapter.setItemListener(this);
        mBinding.recyclerFavorite.setAdapter(mAdapter);
        mFavoriteViewModel.loadAllFavorites();
    }

    private void registerEvents() {
        mBinding.imageSearch.setOnClickListener(this);
    }

    @Override
    protected int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_favorite;
    }

    @Override
    public void startSearchActivity() {
        startActivity(SearchActivity.getIntent(getActivity()));
    }

    @Override
    public void startMovieDetailActivity(Movie movie) {
        startActivity(MovieDetailActivity.getIntent(getActivity(), movie.getId(), movie.getTitle()));
    }

    @Override
    public void onItemClicked(Movie movie, int position) {
        startMovieDetailActivity(movie);
    }

    @Override
    public void onElementClicked(Movie movie, int position) {
        mFavoriteViewModel.deleteFavorite(movie);
        mAdapter.removeItem(position);
    }

    @Override
    public void onClick(View v) {
        startSearchActivity();
    }

    @Override
    public void onDestroy() {
        mFavoriteViewModel.dispose();
        sInstance = null;
        super.onDestroy();
    }
}
