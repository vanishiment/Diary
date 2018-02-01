package com.plant.diaryapp.data.local;

import com.plant.diaryapp.data.datasource.DiaryDataSource;
import com.plant.diaryapp.data.model.Diary;
import com.plant.diaryapp.utils.AppExecutors;

import java.util.List;

public class LocalDiaryDataSource implements DiaryDataSource {

    private static LocalDiaryDataSource sSource;
    private DiaryDao mDiaryDao;
    private AppExecutors mAppExecutors;

    private LocalDiaryDataSource(DiaryDao diaryDao, AppExecutors appExecutors) {
        mDiaryDao = diaryDao;
        mAppExecutors = appExecutors;
    }

    public static LocalDiaryDataSource getSource(DiaryDao diaryDao, AppExecutors appExecutors) {
        if (sSource == null) {
            synchronized (LocalDiaryDataSource.class) {
                if (sSource == null) {
                    sSource = new LocalDiaryDataSource(diaryDao, appExecutors);
                }
            }
        }
        return sSource;
    }

    @Override
    public void getMonthDiary(int year, int month, LoadDiaryListCallback callback) {
        Runnable runnable = () -> {
            List<Diary> diaries = mDiaryDao.getMonthDiary(year, month);
            mAppExecutors.mainThread().execute(() -> {
                if (diaries.isEmpty()) {
                    callback.onDataNotAvailable();
                } else {
                    callback.onDiaryListLoaded(diaries);
                }
            });
        };
        mAppExecutors.diskIO().execute(runnable);

    }

    @Override
    public void getDiary(int year, int month, int day, GetDiaryCallback callback) {
        Runnable runnable = () -> {
            Diary diary = mDiaryDao.getDiary(year, month, day);
            mAppExecutors.mainThread().execute(() -> {
                if (diary != null) {
                    callback.onDiaryLoaded(diary);
                } else {
                    callback.onDataNotAvailable();
                }
            });
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void queryDiary(String query, LoadDiaryListCallback callback) {
        Runnable runnable = () -> {
            List<Diary> diaryList = mDiaryDao.queryDiary("%" + query + "%");
            mAppExecutors.mainThread().execute(() -> {
                if (diaryList.isEmpty()){
                    callback.onDataNotAvailable();
                }else {
                    callback.onDiaryListLoaded(diaryList);
                }
            });
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void insertDiary(Diary diary) {
        Runnable insert = () -> {
            mDiaryDao.insertDiary(diary);
        };
        mAppExecutors.diskIO().execute(insert);
    }

    @Override
    public void updateDiary(Diary diary) {

        Runnable update = () -> {
            mDiaryDao.updateDiary(diary);
        };
        mAppExecutors.diskIO().execute(update);
    }

    @Override
    public void updateDiaryTitle(int year, int month, int day, String title) {

        Runnable update = () -> {
            mDiaryDao.updateDiaryTitle(year, month, day, title);
        };
        mAppExecutors.diskIO().execute(update);
    }

    @Override
    public void updateDiaryPic(int year, int month, int day, String pic) {

        Runnable update = () -> {
            mDiaryDao.updateDiaryPic(year, month, day, pic);
        };
        mAppExecutors.diskIO().execute(update);
    }

    @Override
    public void updateDiaryContent(int year, int month, int day, String content) {

        Runnable update = () -> {
            mDiaryDao.updateDiaryContent(year, month, day, content);
        };
        mAppExecutors.diskIO().execute(update);
    }

    @Override
    public void updateDiaryWeather(int year, int month, int day, String weather) {

        Runnable update = () -> {
            mDiaryDao.updateDiaryWeather(year, month, day, weather);
        };
        mAppExecutors.diskIO().execute(update);
    }

    @Override
    public void deleteDiary(int year, int month, int day) {

        Runnable delete = () -> {
            mDiaryDao.deleteDiary(year, month, day);
        };
        mAppExecutors.diskIO().execute(delete);
    }
}
