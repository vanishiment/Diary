package com.plant.diary.data.local;

import android.content.Context;
import com.plant.diary.data.datasource.DiaryDataSource;
import com.plant.diary.data.local.db.DiaryDao;
import com.plant.diary.data.local.db.DiaryDatabase;
import com.plant.diary.data.model.Diary;
import io.reactivex.Flowable;
import java.util.List;

public class LocalDiaryDataSource implements DiaryDataSource{

  private static LocalDiaryDataSource sSource;
  private DiaryDao mDiaryDao;

  private LocalDiaryDataSource(Context context){
    mDiaryDao = DiaryDatabase.get(context).diaryDao();
  }

  public static LocalDiaryDataSource getSource(Context context){
    if (sSource == null){
      synchronized (LocalDiaryDataSource.class){
        if (sSource == null){
          sSource = new LocalDiaryDataSource(context.getApplicationContext());
        }
      }
    }
    return sSource;
  }

  @Override public Flowable<List<Diary>> getMonthDiaryList(int year, int month) {
    return mDiaryDao.getDiaryListByMonth(year, month);
  }

  @Override public Flowable<List<Diary>> getDayDiary(int year, int month, int day) {
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
}
