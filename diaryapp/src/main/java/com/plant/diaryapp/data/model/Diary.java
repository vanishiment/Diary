package com.plant.diaryapp.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "diary_table",primaryKeys = {"year","month","day"})
public class Diary implements Parcelable {

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

    public Diary(int year, int month, int week, int day, String title, String weather, String content, String pic) {
        mYear = year;
        mMonth = month;
        mWeek = week;
        mDay = day;
        mTitle = title;
        mWeather = weather;
        mContent = content;
        mPic = pic;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mYear);
        dest.writeInt(this.mMonth);
        dest.writeInt(this.mWeek);
        dest.writeInt(this.mDay);
        dest.writeString(this.mTitle);
        dest.writeString(this.mWeather);
        dest.writeString(this.mContent);
        dest.writeString(this.mPic);
    }

    protected Diary(Parcel in) {
        this.mYear = in.readInt();
        this.mMonth = in.readInt();
        this.mWeek = in.readInt();
        this.mDay = in.readInt();
        this.mTitle = in.readString();
        this.mWeather = in.readString();
        this.mContent = in.readString();
        this.mPic = in.readString();
    }

    public static final Parcelable.Creator<Diary> CREATOR = new Parcelable.Creator<Diary>() {
        @Override
        public Diary createFromParcel(Parcel source) {
            return new Diary(source);
        }

        @Override
        public Diary[] newArray(int size) {
            return new Diary[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Diary diary = (Diary) o;

        if (mYear != diary.mYear) return false;
        if (mMonth != diary.mMonth) return false;
        if (mWeek != diary.mWeek) return false;
        if (mDay != diary.mDay) return false;
        if (mTitle != null ? !mTitle.equals(diary.mTitle) : diary.mTitle != null) return false;
        if (mWeather != null ? !mWeather.equals(diary.mWeather) : diary.mWeather != null)
            return false;
        if (mContent != null ? !mContent.equals(diary.mContent) : diary.mContent != null)
            return false;
        return mPic != null ? mPic.equals(diary.mPic) : diary.mPic == null;
    }

    @Override
    public int hashCode() {
        int result = mYear;
        result = 31 * result + mMonth;
        result = 31 * result + mWeek;
        result = 31 * result + mDay;
        result = 31 * result + (mTitle != null ? mTitle.hashCode() : 0);
        result = 31 * result + (mWeather != null ? mWeather.hashCode() : 0);
        result = 31 * result + (mContent != null ? mContent.hashCode() : 0);
        result = 31 * result + (mPic != null ? mPic.hashCode() : 0);
        return result;
    }
}
