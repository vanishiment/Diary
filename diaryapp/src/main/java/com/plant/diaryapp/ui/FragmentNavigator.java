package com.plant.diaryapp.ui;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class FragmentNavigator {

    private static volatile FragmentNavigator sNavigator;

    private int mLayoutId;
    private FragmentManager mManager;
    private Map<String, Fragment> mFragmentMap;

    private FragmentNavigator(AppCompatActivity activity, int layoutId) {
        mLayoutId = layoutId;
        mManager = activity.getSupportFragmentManager();
        init();
    }

    public static FragmentNavigator get(AppCompatActivity activity, int layoutId) {
        if (sNavigator == null) {
            synchronized (FragmentNavigator.class) {
                if (sNavigator == null) {
                    sNavigator = new FragmentNavigator(activity, layoutId);
                }
            }
        }
        return sNavigator;
    }

    private void init(){
        addFragment(TimeLineFragment.newInstance(),TimeLineFragment.class.getSimpleName());
        addFragment(DiaryFragment.getInstance(),DiaryFragment.class.getSimpleName());
        addFragment(MediaFragment.getInstance(MediaFragment.TYPE_TEXT),MediaFragment.class.getSimpleName() + "-" + MediaFragment.TYPE_TEXT);
        addFragment(MediaFragment.getInstance(MediaFragment.TYPE_PHOTO),MediaFragment.class.getSimpleName() + "-" + MediaFragment.TYPE_PHOTO);
        addFragment(MediaFragment.getInstance(MediaFragment.TYPE_VIDEO),MediaFragment.class.getSimpleName() + "-" + MediaFragment.TYPE_VIDEO);
    }

    public void addFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = mManager.beginTransaction();
        transaction.add(mLayoutId, fragment, tag);
        transaction.commit();
        if (mFragmentMap == null) {
            mFragmentMap = new HashMap<>();
        }
        mFragmentMap.put(tag, fragment);
    }

    public void hideFragment() {
        if (mFragmentMap == null || mFragmentMap.isEmpty()) return;
        FragmentTransaction transaction = mManager.beginTransaction();
        for (Map.Entry<String, Fragment> entry : mFragmentMap.entrySet()) {
            Fragment fragment = entry.getValue();
            transaction.hide(fragment);
        }
        transaction.commit();
    }

    public void showFragment(String tag){
        hideFragment();

        FragmentTransaction transaction = mManager.beginTransaction();
        Fragment fragment = mManager.findFragmentByTag(tag);
        if (fragment != null){
            transaction.show(fragment).commit();
        }
    }

}
