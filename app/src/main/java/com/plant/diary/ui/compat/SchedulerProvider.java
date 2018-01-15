package com.plant.diary.ui.compat;

import android.support.annotation.NonNull;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SchedulerProvider implements BaseSchedulerProvider {

  private static class Holder {
    static final SchedulerProvider s = new SchedulerProvider();
  }

  public static SchedulerProvider get() {
    return Holder.s;
  }

  @NonNull @Override public Scheduler computation() {
    return Schedulers.computation();
  }

  @NonNull @Override public Scheduler io() {
    return Schedulers.io();
  }

  @NonNull @Override public Scheduler ui() {
    return AndroidSchedulers.mainThread();
  }
}
