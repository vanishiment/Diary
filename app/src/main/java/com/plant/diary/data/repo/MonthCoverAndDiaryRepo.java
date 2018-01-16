package com.plant.diary.data.repo;

import android.content.Context;
import com.plant.diary.data.datasource.DiaryDataSource;
import com.plant.diary.data.datasource.MonthCoverDataSource;
import com.plant.diary.data.local.LocalMonthCoverAndDiaryDataSource;
import com.plant.diary.data.model.Diary;
import com.plant.diary.data.model.MonthCover;
import com.plant.diary.data.remote.RemoteMonthCoverAndDiaryDataSource;
import io.reactivex.Flowable;
import java.util.List;

public class MonthCoverAndDiaryRepo implements MonthCoverDataSource, DiaryDataSource {

  private static MonthCoverAndDiaryRepo mRepo;

  private LocalMonthCoverAndDiaryDataSource mLocal;
  private RemoteMonthCoverAndDiaryDataSource mRemote;

  private MonthCoverAndDiaryRepo(LocalMonthCoverAndDiaryDataSource local,
      RemoteMonthCoverAndDiaryDataSource remote) {
    mLocal = local;
    mRemote = remote;
  }

  public static MonthCoverAndDiaryRepo get(Context context) {
    if (mRepo == null) {
      synchronized (MonthCoverAndDiaryRepo.class) {
        if (mRepo == null) {
          mRepo = new MonthCoverAndDiaryRepo(LocalMonthCoverAndDiaryDataSource.get(context),
              RemoteMonthCoverAndDiaryDataSource.get());
        }
      }
    }
    return mRepo;
  }

  /************************************* DiaryDataSource ***************************************/

  @Override public Flowable<List<Diary>> getMonthDiaryList(int year, int month) {
    return mLocal.getMonthDiaryList(year, month);
  }

  @Override public Flowable<Diary> getDayDiary(int year, int month, int day) {
    return mLocal.getDayDiary(year, month, day);
  }

  @Override public void insertDiary(Diary diary) {
    mLocal.insertDiary(diary);
  }

  @Override public void updateDiary(Diary diary) {
    mLocal.updateDiary(diary);
  }

  @Override public void deleteDiary(Diary diary) {
    mLocal.deleteDiary(diary);
  }

  /************************************* MonthCoverDataSource ***************************************/

  @Override public Flowable<List<MonthCover>> getYearMonthCoverList(int year) {
    return mLocal.getYearMonthCoverList(year);
  }

  @Override public MonthCover getMonthCover(int year, int month) {
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
