package com.plant.diary.ui.monthdiary;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plant.diary.R;
import com.plant.diary.data.model.Diary;
import com.plant.diary.ui.base.BaseFragment;
import java.util.ArrayList;
import java.util.List;

public class MonthDiaryFragment extends BaseFragment implements MonthDiaryContact.View{

  @BindView(R.id.fragment_month_diary_rv) RecyclerView mRV;

  private List<Diary> mDays = new ArrayList<>();
  private MonthDiaryAdapter mAdapter;

  private MonthDiaryContact.Presenter mPresenter;

  public MonthDiaryFragment() {
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    setHasOptionsMenu(true);
    View root = inflater.inflate(R.layout.fragment_month_diary, container, false);
    ButterKnife.bind(this, root);
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    mRV.setLayoutManager(layoutManager);
    mRV.setItemAnimator(new DefaultItemAnimator());
    if (mAdapter == null) mAdapter = new MonthDiaryAdapter(getActivity(), mDays);
    mRV.setAdapter(mAdapter);
    return root;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        getActivity().finish();
        break;
      default:
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  public void setDate(int year,int month){
    mPresenter.loadMonthDiary(false,year,month);
  }

  @Override public void setPresenter(MonthDiaryContact.Presenter presenter) {
    mPresenter = presenter;
  }

  @Override public void setLoadingIndicator(boolean show) {

  }

  @Override public void showNoMonthDiary() {

  }

  @Override public void showMonthDiaryError() {

  }

  @Override public void showMonthDiary(List<Diary> diaryList) {
    mAdapter.replaceData(diaryList);
  }
}
