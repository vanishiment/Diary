package com.plant.diary.data.local;

import android.content.Context;
import com.plant.diary.data.datasource.MonthCoverDataSource;
import com.plant.diary.data.local.db.DiaryDatabase;
import com.plant.diary.data.local.db.MonthCoverDao;
import com.plant.diary.data.model.MonthCover;
import io.reactivex.Flowable;
import java.util.List;

public class LocalMonthCoverDataSource implements MonthCoverDataSource {

  private static LocalMonthCoverDataSource sSource;
  private MonthCoverDao mMonthCoverDao;

  private LocalMonthCoverDataSource(Context context) {
    mMonthCoverDao = DiaryDatabase.get(context.getApplicationContext()).monthCoverDao();
  }

  public static LocalMonthCoverDataSource getSource(Context context) {
    if (sSource == null) {
      synchronized (LocalMonthCoverDataSource.class) {
        if (sSource == null) {
          sSource = new LocalMonthCoverDataSource(context);
        }
      }
    }
    return sSource;
  }

  @Override public Flowable<List<MonthCover>> getYearMonthCoverList(int year) {
    return mMonthCoverDao.getMonthCoverListByYear(year);
  }

  @Override public Flowable<List<MonthCover>> getMonthCover(int year, int month) {
    return mMonthCoverDao.getMonthCover(year, month);
  }

  @Override public void insertMonthCover(MonthCover monthCover) {
    mMonthCoverDao.insertMonthCover(monthCover);
  }

  @Override public void updateMonthCover(MonthCover monthCover) {
    mMonthCoverDao.updateMonthCover(monthCover);
  }

  @Override public void deleteMonthCover(MonthCover monthCover) {
    mMonthCoverDao.deleteMonthCover(monthCover);
  }
}
