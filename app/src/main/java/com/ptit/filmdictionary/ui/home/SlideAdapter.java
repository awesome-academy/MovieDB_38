package com.ptit.filmdictionary.ui.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SlideAdapter extends FragmentPagerAdapter {
    private static final int SUM_FRAGMENT = 1;
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_FAVORITE = 1;
    private static final int FRAGMENT_SETTING = 2;

    public SlideAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case FRAGMENT_HOME:
                return HomeFragment.getInstance();
            case FRAGMENT_FAVORITE:
                return null;
            case FRAGMENT_SETTING:
                return null;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return SUM_FRAGMENT;
    }
}
