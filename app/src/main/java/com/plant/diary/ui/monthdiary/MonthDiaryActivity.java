package com.plant.diary.ui.monthdiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plant.diary.R;
import com.plant.diary.data.repo.DiaryRepo;
import com.plant.diary.ui.base.BaseActivity;
import com.plant.diary.ui.compat.SchedulerProvider;
import com.plant.diary.ui.constants.Constant;
import com.plant.diary.utils.ActivityUtils;

public class MonthDiaryActivity extends BaseActivity {

  private int mYear;
  private int mMonth;

  @BindView(R.id.month_diary_toolbar) Toolbar mToolbar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_month_diary);
    ButterKnife.bind(this);
    setSupportActionBar(mToolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setHomeButtonEnabled(true);
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    Intent intent = getIntent();
    if (intent != null) {
      mYear = intent.getIntExtra(Constant.MONTH_COVER_YEAR, -1);
      mMonth = intent.getIntExtra(Constant.MONTH_COVER_MONTH, -1);
    }
    if (mYear == -1 || mMonth == -1) {
      return;
    }
    if (actionBar != null) actionBar.setTitle(mMonth + "/" + mYear);

    MonthDiaryFragment monthDiaryFragment =
        (MonthDiaryFragment) getSupportFragmentManager().findFragmentById(
            R.id.month_diary_fragment);
    if (monthDiaryFragment == null) {
      monthDiaryFragment = new MonthDiaryFragment();
      MonthDiaryPresenter presenter =
          new MonthDiaryPresenter(DiaryRepo.getRepo(this), monthDiaryFragment,
              SchedulerProvider.get());
      ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), monthDiaryFragment,
          R.id.month_diary_fragment);
    }
    monthDiaryFragment.setDate(mYear, mMonth);
  }
}
