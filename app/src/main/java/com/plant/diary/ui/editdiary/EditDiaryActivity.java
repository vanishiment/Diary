package com.plant.diary.ui.editdiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plant.diary.R;
import com.plant.diary.data.repo.DiaryRepo;
import com.plant.diary.data.repo.MonthCoverRepo;
import com.plant.diary.ui.base.BaseActivity;
import com.plant.diary.ui.compat.SchedulerProvider;
import com.plant.diary.ui.constants.Constant;
import com.plant.diary.utils.ActivityUtils;

public class EditDiaryActivity extends BaseActivity {

  private int mYear;
  private int mMonth;
  private int mDay;

  @BindView(R.id.edit_diary_toolbar) Toolbar mToolbar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_diary);
    ButterKnife.bind(this);
    setSupportActionBar(mToolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null){
      actionBar.setHomeButtonEnabled(true);
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
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

    if (actionBar != null) actionBar.setTitle(mMonth + "," + mDay + "/" + mYear);

    EditDiaryFragment editDiaryFragment =
        (EditDiaryFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_edit_diary);
    if (editDiaryFragment == null){
      editDiaryFragment = new EditDiaryFragment();
      editDiaryFragment.setDate(mYear,mMonth,mDay);
      new EditDiaryPresenter(DiaryRepo.getRepo(this), MonthCoverRepo.getRepo(this),editDiaryFragment, SchedulerProvider.get());
      ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),editDiaryFragment,R.id.fragment_edit_diary);
    }

  }

}
