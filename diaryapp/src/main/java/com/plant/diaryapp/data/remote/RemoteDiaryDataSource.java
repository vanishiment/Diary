package com.plant.diaryapp.data.remote;


import com.plant.diaryapp.data.datasource.DiaryDataSource;
import com.plant.diaryapp.data.model.Diary;

import java.util.List;

public class RemoteDiaryDataSource implements DiaryDataSource{
    @Override
    public void getMonthDiary(int year, int month,LoadDiaryListCallback callback) {
    }

    @Override
    public void getDiary(int year, int month, int day,GetDiaryCallback callback) {
    }

    @Override
    public void queryDiary(String query, LoadDiaryListCallback callback) {

    }

    @Override
    public void queryAllDiary(LoadDiaryListCallback callback) {

    }

    @Override
    public void insertDiary(Diary diary) {

    }

    @Override
    public void updateDiary(Diary diary) {

    }

    @Override
    public void updateDiaryTitle(int year, int month, int day, String title) {

    }

    @Override
    public void updateDiaryPic(int year, int month, int day, String pic) {

    }

    @Override
    public void updateDiaryContent(int year, int month, int day, String content) {

    }

    @Override
    public void updateDiaryWeather(int year, int month, int day, String weather) {

    }

    @Override
    public void deleteDiary(int year, int month, int day) {

    }
}
