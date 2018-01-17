package com.plant.diary.data.local.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.IntRange;
import com.plant.diary.data.model.Diary;
import io.reactivex.Flowable;
import java.util.List;

@Dao public interface DiaryDao {

  @Query("select * from diary_table where year = :year and month = :month")
  Flowable<List<Diary>> getDiaryListByMonth(@IntRange(from = 1978, to = 2048) int year,
      @IntRange(from = 1, to = 12) int month);

  @Query("select * from diary_table where year = :year and month = :month and day = :day limit 1")
  Flowable<List<Diary>> getDiaryListByDay(@IntRange(from = 1978, to = 2048) int year,
      @IntRange(from = 1, to = 12) int month, @IntRange(from = 1, to = 31) int day);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertDiary(Diary diary);

  @Update(onConflict = OnConflictStrategy.REPLACE)
  void updateDiary(Diary diary);

  @Delete
  void deleteDiary(Diary diary);
}
