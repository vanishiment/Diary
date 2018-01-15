package com.plant.diary.data.local;

import android.content.Context;
import com.plant.diary.data.datasource.DiaryDataSource;
import com.plant.diary.data.datasource.MonthCoverDataSource;
import com.plant.diary.data.local.db.DiaryDao;
import com.plant.diary.data.local.db.DiaryDatabase;
import com.plant.diary.data.local.db.MonthCoverDao;
import com.plant.diary.data.model.Diary;
import com.plant.diary.data.model.MonthCover;
import io.reactivex.Flowable;
import java.util.List;

public class LocalMonthCoverAndDiaryDataSource implements MonthCoverDataSource, DiaryDataSource {

  private static LocalMonthCoverAndDiaryDataSource mDataSource;

  private DiaryDao mDiaryDao;
  private MonthCoverDao mMonthCoverDao;

  private LocalMonthCoverAndDiaryDataSource(DiaryDao diaryDao, MonthCoverDao monthCoverDao) {
    mDiaryDao = diaryDao;
    mMonthCoverDao = monthCoverDao;
  }

  public static LocalMonthCoverAndDiaryDataSource get(Context context) {
    if (mDataSource == null) {
      synchronized (LocalMonthCoverAndDiaryDataSource.class) {
        if (mDataSource == null) {
          mDataSource = new LocalMonthCoverAndDiaryDataSource(DiaryDatabase.get(context).diaryDao(),
              DiaryDatabase.get(context).monthCoverDao());
        }
      }
    }
    return mDataSource;
  }

  /************************************* DiaryDataSource ***************************************/

  @Override public Flowable<List<Diary>> getMonthDiaryList(int year, int month) {
    return mDiaryDao.getDiaryListByMonth(year, month);
  }

  @Override public Flowable<Diary> getDayDiary(int year, int month, int day) {
    return mDiaryDao.getDiaryListByDay(year, month, day);
  }

  @Override public void insertDiary(Diary diary) {
    mDiaryDao.insertDiary(diary);
  }

  @Override public void updateDiary(Diary diary) {
    mDiaryDao.updateDiary(diary);
  }

  @Override public void deleteDiary(Diary diary) {
    mDiaryDao.deleteDiary(diary);
  }

  /************************************* MonthCoverDataSource ***************************************/

  @Override public Flowable<List<MonthCover>> getYearMonthCoverList(int year) {
    return mMonthCoverDao.getMonthCoverListByYear(year);
  }

  @Override public Flowable<MonthCover> getMonthCover(int year, int month) {
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
