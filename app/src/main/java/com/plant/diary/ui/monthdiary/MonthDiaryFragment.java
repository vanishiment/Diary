package com.plant.diary.ui.monthdiary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plant.diary.R;
import com.plant.diary.ui.base.BaseFragment;
import java.util.ArrayList;
import java.util.List;

public class MonthDiaryFragment extends BaseFragment {

  @BindView(R.id.fragment_month_diary_rv) RecyclerView mRV;

  private List<String> mDays;

  public MonthDiaryFragment() {
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initDays();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    setHasOptionsMenu(true);
    View root = inflater.inflate(R.layout.fragment_month_diary, container, false);
    ButterKnife.bind(this, root);
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    mRV.setLayoutManager(layoutManager);
    mRV.setItemAnimator(new DefaultItemAnimator());
    mRV.setAdapter(new MonthDiaryAdapter(getActivity(), mDays));
    return root;
  }

  private void initDays() {
    if (mDays == null) mDays = new ArrayList<>();
    if (!mDays.isEmpty()) mDays.clear();
    for (int i = 0; i < 30; i++) {
      mDays.add(String.valueOf(i));
    }
  }
}
