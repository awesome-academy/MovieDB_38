package com.ptit.filmdictionary.ui.actor.info;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.source.MovieRepository;
import com.ptit.filmdictionary.data.source.local.MovieLocalDataSource;
import com.ptit.filmdictionary.data.source.remote.MovieRemoteDataSource;
import com.ptit.filmdictionary.databinding.FragmentActorInforBinding;
import com.ptit.filmdictionary.ui.actor.ActorActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActorInfoFragment extends Fragment implements ActorInfoNavigator {
    public ActorInfoViewModel mViewModel;
    private FragmentActorInforBinding mBinding;

    public ActorInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_actor_infor, container, false);
        View view = mBinding.getRoot();
        initViewModel();
        return view;
    }

    private void initViewModel() {
        ActorActivity actorActivity = (ActorActivity) getActivity();

        mViewModel = new ActorInfoViewModel(MovieRepository.getInstance(MovieRemoteDataSource.getInstance(getContext()),
                MovieLocalDataSource.getInstance(getContext())), this);
        mBinding.setViewModel(mViewModel);
        mViewModel.loadProfile(actorActivity.getActorId());
    }

    @Override
    public void showContentView() {
        mBinding.viewContent.setVisibility(View.VISIBLE);
        mBinding.progressLoad.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        mViewModel.destroy();
        super.onDestroy();
    }
}
