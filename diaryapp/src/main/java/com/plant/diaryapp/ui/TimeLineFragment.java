package com.plant.diaryapp.ui;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.plant.diaryapp.R;

import java.util.ArrayList;
import java.util.List;

public class TimeLineFragment extends BaseLazyFragment {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private StateRecyclerView mRecyclerView;
    private QuickAdapter<String> mDiaryBookQuickAdapter = new QuickAdapter<String>(new ArrayList<>()) {
        @Override
        public int getLayoutId() {
            return R.layout.time_line_item_layout;
        }

        @Override
        public void bindViewHolder(VH holder, List<String> list, int position) {

        }

        @Override
        public boolean areItemsTheSame(String oldT, String newT) {
            return oldT == newT;
        }

        @Override
        public boolean areContentsTheSame(String oldT, String newT) {
            return oldT == newT;
        }
    };

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

    SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = () -> {
        loadData();
    };

    @Override
    public void initView(View view) {
        TextView textView = view.findViewById(R.id.frag_time_line_tv);
        textView.setText(R.string.nav_time_line);
        mSwipeRefreshLayout = view.findViewById(R.id.common_refresh_layout_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark,R.color.colorPrimary,R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        StateLayout stateLayout = view.findViewById(R.id.common_refresh_layout_state_layout);
        mRecyclerView = view.findViewById(R.id.common_refresh_layout_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(),linearLayoutManager.getOrientation()));
        mRecyclerView.setStateLayout(stateLayout);
        mRecyclerView.setAdapter(mDiaryBookQuickAdapter);
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
        });
    }

    @Override
    public void onFragmentVisible() {
        updateActivityTitle(getString(R.string.nav_time_line));
    }

    @Override
    public void loadData() {
        new Handler().postDelayed(()->{
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                list.add("1");
            }
            mDiaryBookQuickAdapter.replaceData(list);
            mSwipeRefreshLayout.setRefreshing(false);
        },3000);
    }


}
