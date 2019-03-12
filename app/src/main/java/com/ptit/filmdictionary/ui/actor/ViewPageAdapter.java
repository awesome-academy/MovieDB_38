package com.ptit.filmdictionary.ui.actor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ptit.filmdictionary.ui.actor.info.ActorInfoFragment;
import com.ptit.filmdictionary.ui.actor.movies.MoviesFragment;

public class ViewPageAdapter extends FragmentStatePagerAdapter {

    private static final int NUMBER_FRAG = 2;
    private static final int ACTOR_INFO_FRAG = 0;
    private static final int MOVIES_FRAG = 1;

    public ViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case ACTOR_INFO_FRAG:
                return new ActorInfoFragment();
            case MOVIES_FRAG:
                return new MoviesFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUMBER_FRAG;
    }
}
