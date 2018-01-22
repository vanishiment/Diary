package com.plant.diaryapp.data.datasource;


import com.plant.diaryapp.data.model.Diary;

import java.util.List;

public interface DiaryDataSource {

    interface LoadDiaryListCallback {
        void onDiaryListLoaded(List<Diary> diaryList);

        void onDataNotAvailable();
    }

    interface GetDiaryCallback {
        void onDiaryLoaded(Diary diary);

        void onDataNotAvailable();
    }

    void getMonthDiary(int year, int month,LoadDiaryListCallback callback);

    void getDiary(int year, int month, int day,GetDiaryCallback callback);

    void insertDiary(Diary diary);

    void updateDiary(Diary diary);

    void updateDiaryTitle(int year, int month, int day, String title);

    void updateDiaryPic(int year, int month, int day, String pic);

    void updateDiaryContent(int year, int month, int day, String content);

    void updateDiaryWeather(int year, int month, int day, String weather);

    void deleteDiary(int year, int month, int day);
}
