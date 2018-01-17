package com.plant.diary.data.datasource;

import android.support.annotation.IntRange;
import com.plant.diary.data.model.MonthCover;
import io.reactivex.Flowable;
import java.util.List;

public interface MonthCoverDataSource {

  Flowable<List<MonthCover>> getYearMonthCoverList(@IntRange(from = 1978, to = 2048) int year);

  Flowable<List<MonthCover>> getMonthCover(@IntRange(from = 1978, to = 2048) int year,
      @IntRange(from = 1, to = 12) int month);

  void insertMonthCover(MonthCover monthCover);

  void updateMonthCover(MonthCover monthCover);

  void deleteMonthCover(MonthCover monthCover);

}
