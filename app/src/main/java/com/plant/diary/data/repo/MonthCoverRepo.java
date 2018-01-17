package com.plant.diary.data.repo;

import android.content.Context;
import com.plant.diary.data.datasource.MonthCoverDataSource;
import com.plant.diary.data.local.LocalMonthCoverDataSource;
import com.plant.diary.data.model.MonthCover;
import io.reactivex.Flowable;
import java.util.List;

public class MonthCoverRepo implements MonthCoverDataSource {

  private static MonthCoverRepo mRepo;
  private LocalMonthCoverDataSource mLocal;

  private MonthCoverRepo(LocalMonthCoverDataSource local) {
    mLocal = local;
  }

  public static MonthCoverRepo getRepo(Context context){
    if (mRepo == null){
      synchronized (MonthCoverRepo.class){
        if (mRepo == null){
          mRepo = new MonthCoverRepo(LocalMonthCoverDataSource.getSource(context));
        }
      }
    }
    return mRepo;
  }

  @Override public Flowable<List<MonthCover>> getYearMonthCoverList(int year) {
    return mLocal.getYearMonthCoverList(year);
  }

  @Override public Flowable<List<MonthCover>> getMonthCover(int year, int month) {
    return mLocal.getMonthCover(year, month);
  }

  @Override public void insertMonthCover(MonthCover monthCover) {
    mLocal.insertMonthCover(monthCover);
  }

  @Override public void updateMonthCover(MonthCover monthCover) {
    mLocal.updateMonthCover(monthCover);
  }

  @Override public void deleteMonthCover(MonthCover monthCover) {
    mLocal.deleteMonthCover(monthCover);
  }
}
