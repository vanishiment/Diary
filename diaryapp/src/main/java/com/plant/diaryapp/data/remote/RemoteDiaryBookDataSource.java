package com.plant.diaryapp.data.remote;


import com.plant.diaryapp.data.datasource.DiaryBookDataSource;
import com.plant.diaryapp.data.model.DiaryBook;

public class RemoteDiaryBookDataSource implements DiaryBookDataSource {
    @Override
    public void getYearDiaryBook(int year,LoadDiaryBooksCallback callback) {
    }

    @Override
    public void getMonthDiaryBook(int year, int month,GetDiaryBookCallback callback) {
    }

    @Override
    public void insertDiaryBook(DiaryBook diaryBook) {

    }

    @Override
    public void updateDiaryBook(DiaryBook diaryBook) {

    }

    @Override
    public void updateDiaryBookColor(int year, int month, java.lang.String color) {

    }

    @Override
    public void updateDiaryBookPic(int year, int month, java.lang.String pic) {

    }

    @Override
    public void updateDiaryBookDiaryCount(int year, int month, int diaryCount) {

    }

    @Override
    public void deleteDiaryBookById(int year, int month) {

    }
}
