package com.plant.diaryapp.ui;


import android.view.View;
import android.widget.TextView;

import com.plant.diaryapp.R;

public class DiaryFragment extends BaseLazyFragment {

    public DiaryFragment() {
    }

    public static DiaryFragment getInstance(){
        return new DiaryFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_diary;
    }

    @Override
    public void initView(View view) {
        TextView textView = view.findViewById(R.id.frag_diary_tv);
        textView.setText(R.string.nav_diary);
    }

    @Override
    public void onFragmentVisible() {
        updateActivityTitle(getString(R.string.nav_diary));
    }

    @Override
    public void loadData() {

    }
}
