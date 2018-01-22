package com.plant.diaryapp.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

@Entity(tableName = "diary_book_table",primaryKeys = {"year","month"})
public class DiaryBook {

    @ColumnInfo(name = "year")
    private int year;

    @ColumnInfo(name = "month")
    private int month;

    @ColumnInfo(name = "color")
    private String color;

    @ColumnInfo(name = "cover_pic_url")
    private String coverPicUrl;

    @ColumnInfo(name = "diary_count")
    private int diaryCount;

    public DiaryBook(int year, int month, String color, String coverPicUrl, int diaryCount) {
        this.year = year;
        this.month = month;
        this.color = color;
        this.coverPicUrl = coverPicUrl;
        this.diaryCount = diaryCount;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCoverPicUrl() {
        return coverPicUrl;
    }

    public void setCoverPicUrl(String coverPicUrl) {
        this.coverPicUrl = coverPicUrl;
    }

    public int getDiaryCount() {
        return diaryCount;
    }

    public void setDiaryCount(int diaryCount) {
        this.diaryCount = diaryCount;
    }
}
