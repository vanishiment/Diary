package com.plant.diaryapp.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.plant.diaryapp.data.model.DiaryBook;

import java.util.List;

@Dao
public interface DiaryBookDao {

    @Query("select * from diary_book_table where year = :year")
    List<DiaryBook> getYearDiaryBook(int year);

    @Query("select * from diary_book_table where year = :year and month = :month")
    DiaryBook getMonthDiaryBook(int year, int month);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDiaryBook(DiaryBook diaryBook);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateDiaryBook(DiaryBook diaryBook);

    @Query("update diary_book_table set color = :color where year = :year and month = :month")
    void updateDiaryBookColor(int year, int month, java.lang.String color);

    @Query("update diary_book_table set cover_pic_url = :pic where year = :year and month = :month")
    void updateDiaryBookPic(int year, int month, java.lang.String pic);

    @Query("update diary_book_table set diary_count = :diaryCount where year = :year and month = :month")
    void updateDiaryBookDiaryCount(int year,int month,int diaryCount);

    @Query("delete from diary_book_table where year = :year and month = :month")
    void deleteDiaryBookById(int year,int month);

}
