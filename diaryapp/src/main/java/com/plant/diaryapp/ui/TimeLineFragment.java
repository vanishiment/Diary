package com.plant.diaryapp.ui;

import android.view.View;
import android.widget.TextView;

import com.plant.diaryapp.R;

public class TimeLineFragment extends BaseLazyFragment {

    public TimeLineFragment() {
        // Required empty public constructor
    }

    public static TimeLineFragment newInstance() {
        return new TimeLineFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_time_line;
    }

    @Override
    public void initView(View view) {
        TextView textView = view.findViewById(R.id.frag_time_line_tv);
        textView.setText(R.string.nav_time_line);
    }

    @Override
    public void onFragmentVisible() {
        updateActivityTitle(getString(R.string.nav_time_line));
    }

    @Override
    public void loadData() {

    }


}
