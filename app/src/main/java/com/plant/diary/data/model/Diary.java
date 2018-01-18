package com.plant.diary.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

@Entity(tableName = "diary_table",primaryKeys = {"year","month","day"})
public class Diary {

  @ColumnInfo(name = "year")
  private int mYear;

  @ColumnInfo(name = "month")
  private int mMonth;

  @ColumnInfo(name = "week")
  private int mWeek;

  @ColumnInfo(name = "day")
  private int mDay;

  @ColumnInfo(name = "title")
  private String mTitle;

  @ColumnInfo(name = "weather")
  private String mWeather;

  @ColumnInfo(name = "content")
  private String mContent;

  @ColumnInfo(name = "pic")
  private String mPic;

  @Ignore
  public Diary() {
  }

  public Diary(int year, int month,int week, int day, String title, String weather,
      String content, String pic) {
    mYear = year;
    mMonth = month;
    mWeek = week;
    mDay = day;
    mTitle = title;
    mWeather = weather;
    mContent = content;
    mPic = pic;
  }

  public static final class Builder{

    private final Diary diary = new Diary();

    public Builder year(int year){
      diary.setYear(year);
      return this;
    }

    public Builder month(int month){
      diary.setMonth(month);
      return this;
    }

    public Builder day(int day){
      diary.setDay(day);
      return this;
    }

    public Builder title(String title){
      diary.setTitle(title);
      return this;
    }

    public Builder weather(String weather){
      diary.setWeather(weather);
      return this;
    }

    public Builder content(String content){
      diary.setContent(content);
      return this;
    }

    public Builder pic(String pic){
      diary.setPic(pic);
      return this;
    }

    public Diary build(){
      return diary;
    }
  }

  public int getYear() {
    return mYear;
  }

  public void setYear(int year) {
    mYear = year;
  }

  public int getMonth() {
    return mMonth;
  }

  public void setMonth(int month) {
    mMonth = month;
  }

  public int getWeek() {
    return mWeek;
  }

  public void setWeek(int week) {
    mWeek = week;
  }

  public int getDay() {
    return mDay;
  }

  public void setDay(int day) {
    mDay = day;
  }

  public String getTitle() {
    return mTitle;
  }

  public void setTitle(String title) {
    mTitle = title;
  }

  public String getWeather() {
    return mWeather;
  }

  public void setWeather(String weather) {
    mWeather = weather;
  }

  public String getContent() {
    return mContent;
  }

  public void setContent(String content) {
    mContent = content;
  }

  public String getPic() {
    return mPic;
  }

  public void setPic(String pic) {
    mPic = pic;
  }
}
