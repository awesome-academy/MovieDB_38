package com.ptit.filmdictionary.ui.actor.movies;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.data.source.MovieRepository;
import com.ptit.filmdictionary.data.source.local.MovieLocalDataSource;
import com.ptit.filmdictionary.data.source.remote.MovieRemoteDataSource;
import com.ptit.filmdictionary.databinding.FragmentActorMoviesBinding;
import com.ptit.filmdictionary.ui.actor.ActorActivity;
import com.ptit.filmdictionary.ui.category.CategoryAdapter;
import com.ptit.filmdictionary.ui.movie_detail.MovieDetailActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment implements MoviesNavigator,
        CategoryAdapter.ItemClickListener {
    private FragmentActorMoviesBinding mBinding;
    private MoviesViewModel mViewModel;
    private CategoryAdapter mAdapter;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_actor_movies, container, false);
        initViewModel();
        mBinding.setViewModel(mViewModel);
        setUpAdapter();
        return mBinding.getRoot();
    }

    private void setUpAdapter() {
        mAdapter = new CategoryAdapter(new ArrayList<>(), this);

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

    private void loadMoreMovies() {
        hideLoadMore(false);
        int nextPage = mViewModel.getPage();
        ++nextPage;
        mViewModel.loadMoviesByActor(nextPage);
    }

    private void initViewModel() {
        ActorActivity activity = (ActorActivity) getActivity();
        String actorKey = activity.getActorId();
        mViewModel = new MoviesViewModel(MovieRepository.getInstance(MovieRemoteDataSource.getInstance(getContext()),
                MovieLocalDataSource.getInstance(getContext())), this);
        mViewModel.setActorKey(actorKey);
        mViewModel.loadMoviesByActor(mViewModel.getPage());
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
        startActivity(MovieDetailActivity.getIntent(getContext(), movie.getId(), movie.getTitle()));
    }

    @Override
    public void onDestroy() {
        mViewModel.destroy();
        super.onDestroy();
    }
}
