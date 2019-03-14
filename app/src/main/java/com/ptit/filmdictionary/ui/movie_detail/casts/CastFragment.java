package com.ptit.filmdictionary.ui.movie_detail.casts;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.model.Actor;
import com.ptit.filmdictionary.databinding.FragmentCastBinding;
import com.ptit.filmdictionary.ui.actor.ActorActivity;
import com.ptit.filmdictionary.ui.movie_detail.MovieDetailViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CastFragment extends Fragment implements CastsRecyclerAdapter.OnClickCastListener {
    private MovieDetailViewModel mViewModel;
    private FragmentCastBinding mBinding;

    public CastFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_cast, container, false);
        mBinding.setViewModel(mViewModel);
        initRecyclerView();
        return mBinding.getRoot();
    }

    private void initRecyclerView() {
        mBinding.recyclerCasts.setAdapter(new CastsRecyclerAdapter(new ArrayList<>(), this));
    }

    public static CastFragment getInstance() {
        return new CastFragment();
    }

    public void setViewModel(MovieDetailViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void onCastClick(Actor actor) {
        startActivity(ActorActivity.getIntent(getContext(), actor));
    }
}
