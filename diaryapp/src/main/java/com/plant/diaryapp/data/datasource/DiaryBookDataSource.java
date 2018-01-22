package com.plant.diaryapp.data.datasource;


import com.plant.diaryapp.data.model.DiaryBook;

import java.util.List;

public interface DiaryBookDataSource {

    interface LoadDiaryBooksCallback {
        void onDiaryListLoaded(List<DiaryBook> diaryBooks);

        void onDataNotAvailable();
    }

    interface GetDiaryBookCallback {
        void onDiaryLoaded(DiaryBook diaryBook);

        void onDataNotAvailable();
    }

    void getYearDiaryBook(int year,LoadDiaryBooksCallback callback);

    void getMonthDiaryBook(int year, int month,GetDiaryBookCallback callback);

    void insertDiaryBook(DiaryBook diaryBook);

    void updateDiaryBook(DiaryBook diaryBook);

    void updateDiaryBookColor(int year, int month, String color);

    void updateDiaryBookPic(int year, int month, String pic);

    void updateDiaryBookDiaryCount(int year, int month, int diaryCount);

    void deleteDiaryBookById(int year, int month);
}
