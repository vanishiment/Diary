package com.plant.diary.ui.maindiarys.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.afollestad.materialdialogs.MaterialDialog;
import com.plant.diary.R;
import com.plant.diary.data.repo.MonthCoverRepo;
import com.plant.diary.ui.base.BaseActivity;
import com.plant.diary.ui.compat.SchedulerProvider;
import com.plant.diary.ui.compat.StatusBarCompat;
import com.plant.diary.ui.constants.Constant;
import com.plant.diary.ui.editdiary.EditDiaryActivity;
import com.plant.diary.ui.searchdiary.SearchDiaryActivity;
import com.plant.diary.ui.settings.SettingActivity;
import com.plant.diary.utils.ActivityUtils;
import timber.log.Timber;

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
        v -> {
          showGoToDialog();
        });

    MainFragment mainFragment =
        (MainFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
    if (mainFragment == null){
      mainFragment = new MainFragment();
      ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),mainFragment,R.id.content_frame);
    }
    new MainPresenter(MonthCoverRepo.getRepo(this),mainFragment, SchedulerProvider.get());
  }
  int year = 0;
   int month = 0;
   int day = 0;
  private void showGoToDialog(){

    new MaterialDialog.Builder(this)
        .title("选择要编辑的日期")
        .items(R.array.list_date)
        .itemsCallbackSingleChoice(1, new MaterialDialog.ListCallbackSingleChoice() {
          @Override public boolean onSelection(MaterialDialog dialog, View itemView, int which,
              CharSequence text) {
            String[] strings = text.toString().split("/");
            year = Integer.parseInt(strings[0]);
            month = Integer.parseInt(strings[1]);
            day = Integer.parseInt(strings[2]);
            Timber.tag("main_act").e("date %s %s %s",year,month,day);
            Log.e("main_act","year " + year + " month " + month + " day " + day);
            return false;
          }
        })
        .negativeText("取消")
        .positiveText("确定")
        .onNegative((dialog1, which) -> dialog1.dismiss())
        .onPositive((dialog12, which) -> {
          Intent intent = new Intent(MainActivity.this, EditDiaryActivity.class);
          intent.putExtra(Constant.MONTH_COVER_YEAR,2018);
          intent.putExtra(Constant.MONTH_COVER_MONTH,1);
          intent.putExtra(Constant.MONTH_COVER_DAY,18);
          MainActivity.this.startActivity(intent);
        }).build().show();

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
