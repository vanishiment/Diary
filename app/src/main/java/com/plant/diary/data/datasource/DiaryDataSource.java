package com.plant.diary.data.datasource;

import android.support.annotation.IntRange;
import com.plant.diary.data.model.Diary;
import io.reactivex.Flowable;
import java.util.List;

public interface DiaryDataSource {

  Flowable<List<Diary>> getMonthDiaryList(@IntRange(from = 1978, to = 2048) int year,
      @IntRange(from = 1, to = 12) int month);

  Flowable<List<Diary>> getDayDiary(@IntRange(from = 1978, to = 2048) int year,
      @IntRange(from = 1, to = 12) int month, @IntRange(from = 1, to = 31) int day);

  void insertDiary(Diary diary);

  void updateDiary(Diary diary);

  void deleteDiary(Diary diary);

}
