package com.plant.diaryapp.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.plant.diaryapp.data.model.Diary;

import java.util.List;

@Dao
public interface DiaryDao {

    @Query("select * from diary_table where year = :year and month = :month")
    List<Diary> getMonthDiary(int year,int month);

    @Query("select * from diary_table where year = :year and month = :month and day = :day")
    Diary getDiary(int year,int month,int day);

    @Query("select * from diary_table where title like :text or content like :text")
    List<Diary> queryDiary(String text);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDiary(Diary diary);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateDiary(Diary diary);

    @Query("update diary_table set title = :title where year = :year and month = :month and day = :day")
    void updateDiaryTitle(int year,int month,int day,String title);

    @Query("update diary_table set pic = :pic where year = :year and month = :month and day = :day")
    void updateDiaryPic(int year,int month,int day,String pic);

    @Query("update diary_table set content = :content where year = :year and month = :month and day = :day")
    void updateDiaryContent(int year,int month,int day,String content);

    @Query("update diary_table set weather = :weather where year = :year and month = :month and day = :day")
    void updateDiaryWeather(int year,int month,int day,String weather);

    @Query("delete from diary_table where year = :year and month = :month and day = :day")
    void deleteDiary(int year,int month,int day);

}
