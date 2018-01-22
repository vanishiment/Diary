package com.plant.diaryapp.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragments = new ArrayList<>(5);
        mFragments.add(TimeLineFragment.newInstance());
        mFragments.add(DiaryFragment.getInstance());
        mFragments.add(MediaFragment.getInstance(MediaFragment.TYPE_TEXT));
        mFragments.add(MediaFragment.getInstance(MediaFragment.TYPE_PHOTO));
        mFragments.add(MediaFragment.getInstance(MediaFragment.TYPE_VIDEO));
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
