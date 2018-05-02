package com.cherryzpsoft.nanaldle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ConnectFragmentPagerAdapter extends FragmentPagerAdapter {

    String[] titles = {"내글", "내댓글", "북마크"};

    public ConnectFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = new MyWritingFragment();
                break;

            case 1:
                fragment = new MyCommentsFragment();
                break;

            case 2:
                fragment = new MyBookmarkFragment();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
