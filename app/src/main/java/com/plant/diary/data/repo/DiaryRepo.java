package com.plant.diary.data.repo;

import android.content.Context;
import com.plant.diary.data.datasource.DiaryDataSource;
import com.plant.diary.data.local.LocalDiaryDataSource;
import com.plant.diary.data.model.Diary;
import io.reactivex.Flowable;
import java.util.List;

public class DiaryRepo implements DiaryDataSource {

  private static DiaryRepo mRepo;
  private LocalDiaryDataSource mLocal;

  private DiaryRepo(LocalDiaryDataSource local) {
    mLocal = local;
  }

  public static DiaryRepo getRepo(Context context){
    if (mRepo == null){
      synchronized (DiaryRepo.class){
        if (mRepo == null){
          mRepo = new DiaryRepo(LocalDiaryDataSource.getSource(context));
        }
      }
    }
    return mRepo;
  }

  @Override public Flowable<List<Diary>> getMonthDiaryList(int year, int month) {
    return mLocal.getMonthDiaryList(year,month);
  }

  @Override public Flowable<List<Diary>> getDayDiary(int year, int month, int day) {
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
}
