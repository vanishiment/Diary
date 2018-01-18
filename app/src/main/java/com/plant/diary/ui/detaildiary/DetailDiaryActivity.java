package com.plant.diary.ui.detaildiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plant.diary.R;
import com.plant.diary.data.repo.DiaryRepo;
import com.plant.diary.ui.base.BaseActivity;
import com.plant.diary.ui.compat.SchedulerProvider;
import com.plant.diary.ui.constants.Constant;
import com.plant.diary.utils.ActivityUtils;

public class DetailDiaryActivity extends BaseActivity {

  private int mYear;
  private int mMonth;
  private int mDay;

  @BindView(R.id.detail_diary_toolbar) Toolbar mToolbar;
  @BindView(R.id.detail_diary_toolbar_iv) ImageView mIV;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail_diary);
    ButterKnife.bind(this);
    setSupportActionBar(mToolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null){
      actionBar.setHomeButtonEnabled(true);
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setTitle("1/Sun.");
    }

    Intent intent = getIntent();
    if (intent != null) {
      mYear = intent.getIntExtra(Constant.MONTH_COVER_YEAR, -1);
      mMonth = intent.getIntExtra(Constant.MONTH_COVER_MONTH, -1);
      mDay = intent.getIntExtra(Constant.MONTH_COVER_DAY, -1);
    }
    if (mYear == -1 || mMonth == -1 || mDay == -1) {
      return;
    }

    DetailDiaryFragment detailDiaryFragment =
        (DetailDiaryFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_detail_diary);
    if (detailDiaryFragment == null){
      detailDiaryFragment = new DetailDiaryFragment();
      detailDiaryFragment.setDate(mYear,mMonth,mDay);
      new DetailDiaryPresenter(DiaryRepo.getRepo(this),detailDiaryFragment, SchedulerProvider.get());
      ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),detailDiaryFragment,R.id.fragment_detail_diary);
    }
  }
}
