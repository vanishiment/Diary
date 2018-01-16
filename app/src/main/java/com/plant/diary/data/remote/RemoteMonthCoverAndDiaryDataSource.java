package com.plant.diary.data.remote;

import com.plant.diary.data.datasource.DiaryDataSource;
import com.plant.diary.data.datasource.MonthCoverDataSource;
import com.plant.diary.data.model.Diary;
import com.plant.diary.data.model.MonthCover;
import io.reactivex.Flowable;
import java.util.List;

public class RemoteMonthCoverAndDiaryDataSource implements MonthCoverDataSource,DiaryDataSource{

  private static RemoteMonthCoverAndDiaryDataSource sIns;

  public static RemoteMonthCoverAndDiaryDataSource get(){
    if (sIns == null){
      synchronized (RemoteMonthCoverAndDiaryDataSource.class){
        if (sIns == null){
          sIns = new RemoteMonthCoverAndDiaryDataSource();
        }
      }
    }
    return sIns;
  }

  /************************************* DiaryDataSource ***************************************/

  @Override public Flowable<List<Diary>> getMonthDiaryList(int year, int month) {
    return null;
  }

  @Override public Flowable<Diary> getDayDiary(int year, int month, int day) {
    return null;
  }

  @Override public void insertDiary(Diary diary) {

  }

  @Override public void updateDiary(Diary diary) {

  }

  @Override public void deleteDiary(Diary diary) {

  }

  /************************************* MonthCoverDataSource ***************************************/

  @Override public Flowable<List<MonthCover>> getYearMonthCoverList(int year) {
    return null;
  }

  @Override public MonthCover getMonthCover(int year, int month) {
    return null;
  }

  @Override public void insertMonthCover(MonthCover monthCover) {

  }

  @Override public void updateMonthCover(MonthCover monthCover) {

  }

  @Override public void deleteMonthCover(MonthCover monthCover) {

  }
}
