package com.plant.diary.ui.monthdiary;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plant.diary.R;
import com.plant.diary.ui.base.BaseActivity;

public class MonthDiaryActivity extends BaseActivity {

  @BindView(R.id.month_diary_toolbar) Toolbar mToolbar;
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_month_diary);
    ButterKnife.bind(this);
    setSupportActionBar(mToolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null){
      actionBar.setHomeButtonEnabled(true);
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setTitle("January/2018");
    }
  }
}
