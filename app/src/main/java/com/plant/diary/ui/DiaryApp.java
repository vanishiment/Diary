package com.plant.diary.ui;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.preference.PreferenceManager;
import com.facebook.stetho.Stetho;
import timber.log.Timber;

public class DiaryApp extends Application {


  @Override public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
    applyNightModel();
    Timber.plant(new Timber.DebugTree());
  }


  private void applyNightModel(){
    if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("night_mode", false)) {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    } else {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
  }
}
