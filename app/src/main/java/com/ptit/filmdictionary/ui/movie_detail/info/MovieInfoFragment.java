package com.ptit.filmdictionary.ui.movie_detail.info;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.model.Genre;
import com.ptit.filmdictionary.databinding.FragmentMovieInfoBinding;
import com.ptit.filmdictionary.ui.genre.GenreActivity;
import com.ptit.filmdictionary.ui.movie_detail.MovieDetailViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieInfoFragment extends Fragment implements GenreRecylerAdapter.ItemClickListener {
    private MovieDetailViewModel mViewModel;
    private FragmentMovieInfoBinding mBinding;

    public MovieInfoFragment() {
        // Required empty public constructor
    }

    public static MovieInfoFragment newInstance() {
        return new MovieInfoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_info, container, false);
        mBinding.setViewModel(mViewModel);
        initRecyclerView();
        return mBinding.getRoot();
    }

    private void initRecyclerView() {
        mBinding.recyclerGenre.setAdapter(new GenreRecylerAdapter(new ArrayList<>(), this));
    }

    public MovieDetailViewModel getViewModel() {
        return mViewModel;
    }

    public void setViewModel(MovieDetailViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void onItemClick(Genre genre) {
        Intent intent = GenreActivity.getIntent(getContext(), genre.getId(), genre.getName());
        getActivity().startActivity(intent);
    }
}
