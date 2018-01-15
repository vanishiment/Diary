package com.plant.diary.data.local.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.IntRange;
import com.plant.diary.data.model.MonthCover;
import io.reactivex.Flowable;
import java.util.List;

@Dao
public interface MonthCoverDao {

  @Query("select * from month_cover_table where year = :year")
  Flowable<List<MonthCover>> getMonthCoverListByYear(@IntRange(from = 1978, to = 2048) int year);

  @Query("select * from month_cover_table where year = :year and month = :month limit 1")
  Flowable<MonthCover> getMonthCover(@IntRange(from = 1978, to = 2048) int year,
      @IntRange(from = 1, to = 12) int month);

  @Insert(onConflict = OnConflictStrategy.REPLACE) void insertMonthCover(MonthCover monthCover);

  @Update(onConflict = OnConflictStrategy.REPLACE) void updateMonthCover(MonthCover monthCover);

  @Delete void deleteMonthCover(MonthCover monthCover);
}
