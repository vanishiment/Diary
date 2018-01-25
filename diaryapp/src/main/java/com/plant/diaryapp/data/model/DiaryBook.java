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
    private java.lang.String color;

    @ColumnInfo(name = "cover_pic_url")
    private java.lang.String coverPicUrl;

    @ColumnInfo(name = "diary_count")
    private int diaryCount;

    public DiaryBook(int year, int month, java.lang.String color, java.lang.String coverPicUrl, int diaryCount) {
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

    public java.lang.String getColor() {
        return color;
    }

    public void setColor(java.lang.String color) {
        this.color = color;
    }

    public java.lang.String getCoverPicUrl() {
        return coverPicUrl;
    }

    public void setCoverPicUrl(java.lang.String coverPicUrl) {
        this.coverPicUrl = coverPicUrl;
    }

    public int getDiaryCount() {
        return diaryCount;
    }

    public void setDiaryCount(int diaryCount) {
        this.diaryCount = diaryCount;
    }
}
