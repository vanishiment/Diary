package com.plant.diary.ui.detaildiary;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plant.diary.R;
import com.plant.diary.ui.base.BaseActivity;

public class DetailDiaryActivity extends BaseActivity {

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

  }
}
