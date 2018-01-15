package com.plant.diary.ui.maindiarys.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.plant.diary.R;
import com.plant.diary.data.repo.MonthCoverAndDiaryRepo;
import com.plant.diary.ui.base.BaseActivity;
import com.plant.diary.ui.compat.BaseSchedulerProvider;
import com.plant.diary.ui.compat.SchedulerProvider;
import com.plant.diary.ui.compat.StatusBarCompat;
import com.plant.diary.ui.editdiary.EditDiaryActivity;
import com.plant.diary.ui.searchdiary.SearchDiaryActivity;
import com.plant.diary.ui.settings.SettingActivity;
import com.plant.diary.utils.ActivityUtils;

public class MainActivity extends BaseActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    StatusBarCompat.compat(this, getResources().getColor(R.color.colorPrimaryDark));
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setHomeButtonEnabled(true);
      actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
      actionBar.setTitle(R.string.app_name);
    }
    FloatingActionButton fab = findViewById(R.id.fab);
    fab.setOnClickListener(
        v -> startActivity(new Intent(MainActivity.this, EditDiaryActivity.class)));

    MainFragment mainFragment =
        (MainFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
    if (mainFragment == null){
      mainFragment = new MainFragment();
      ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),mainFragment,R.id.content_frame);
    }
    new MainPresenter(MonthCoverAndDiaryRepo.get(this),mainFragment, SchedulerProvider.get());
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    switch (id) {
      case android.R.id.home:
        startActivity(new Intent(MainActivity.this, SettingActivity.class));
        break;
      case R.id.action_search:
        startActivity(new Intent(MainActivity.this, SearchDiaryActivity.class));
        break;
      default:
        break;
    }

    return super.onOptionsItemSelected(item);
  }
}
