package com.plant.diaryapp.data.local;

import com.plant.diaryapp.data.datasource.DiaryBookDataSource;
import com.plant.diaryapp.data.model.DiaryBook;
import com.plant.diaryapp.utils.AppExecutors;

import java.util.List;


public class LocalDiaryBookDataSource implements DiaryBookDataSource {

    private static LocalDiaryBookDataSource sSource;
    private DiaryBookDao mDiaryBookDao;
    private AppExecutors mAppExecutors;

    private LocalDiaryBookDataSource(DiaryBookDao diaryBookDao, AppExecutors appExecutors) {
        mDiaryBookDao = diaryBookDao;
        mAppExecutors = appExecutors;
    }

    public static LocalDiaryBookDataSource getSource(DiaryBookDao diaryBookDao, AppExecutors appExecutors) {
        if (sSource == null) {
            synchronized (LocalDiaryBookDataSource.class) {
                if (sSource == null) {
                    sSource = new LocalDiaryBookDataSource(diaryBookDao, appExecutors);
                }
            }
        }
        return sSource;
    }

    @Override
    public void getYearDiaryBook(int year, LoadDiaryBooksCallback callback) {
        Runnable runnable = () -> {
            List<DiaryBook> diaryBooks = mDiaryBookDao.getYearDiaryBook(year);
            mAppExecutors.mainThread().execute(() -> {
                if (diaryBooks.isEmpty()) {
                    callback.onDataNotAvailable();
                } else {
                    callback.onDiaryListLoaded(diaryBooks);
                }
            });
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getMonthDiaryBook(int year, int month, GetDiaryBookCallback callback) {
        mDiaryBookDao.getMonthDiaryBook(year, month);
        Runnable runnable = () -> {
            DiaryBook diaryBook = mDiaryBookDao.getMonthDiaryBook(year, month);
            mAppExecutors.mainThread().execute(() -> {
                if (diaryBook == null) {
                    callback.onDataNotAvailable();
                } else {
                    callback.onDiaryLoaded(diaryBook);
                }
            });
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void insertDiaryBook(DiaryBook diaryBook) {
        mAppExecutors.diskIO().execute(() -> {
            mDiaryBookDao.insertDiaryBook(diaryBook);
        });
    }

    @Override
    public void updateDiaryBook(DiaryBook diaryBook) {
        mAppExecutors.diskIO().execute(() -> {
            mDiaryBookDao.updateDiaryBook(diaryBook);
        });

    }

    @Override
    public void updateDiaryBookColor(int year, int month, String color) {
        mAppExecutors.diskIO().execute(() -> {
            mDiaryBookDao.updateDiaryBookColor(year, month, color);
        });

    }

    @Override
    public void updateDiaryBookPic(int year, int month, String pic) {
        mAppExecutors.diskIO().execute(() -> {
            mDiaryBookDao.updateDiaryBookPic(year, month, pic);
        });

    }

    @Override
    public void updateDiaryBookDiaryCount(int year, int month, int diaryCount) {
        mAppExecutors.diskIO().execute(() -> {
            mDiaryBookDao.updateDiaryBookDiaryCount(year, month, diaryCount);
        });

    }

    @Override
    public void deleteDiaryBookById(int year, int month) {
        mAppExecutors.diskIO().execute(() -> {
            mDiaryBookDao.deleteDiaryBookById(year, month);
        });

    }
}
