package com.plant.diaryapp.data.repo;


import com.plant.diaryapp.data.datasource.DiaryDataSource;
import com.plant.diaryapp.data.local.LocalDiaryDataSource;
import com.plant.diaryapp.data.model.Diary;
import com.plant.diaryapp.data.remote.RemoteDiaryDataSource;

public class DiaryRepo implements DiaryDataSource{

    private static DiaryRepo mRepo;
    private LocalDiaryDataSource mLocal;
    private RemoteDiaryDataSource mRemote;

    private DiaryRepo(LocalDiaryDataSource local,RemoteDiaryDataSource remote) {
        mLocal = local;
        mRemote = remote;
    }

    public static DiaryRepo getRepo(LocalDiaryDataSource local,RemoteDiaryDataSource remote){
        if (mRepo == null){
            synchronized (DiaryRepo.class){
                if (mRepo == null){
                    mRepo = new DiaryRepo(local, remote);
                }
            }
        }
        return mRepo;
    }

    @Override
    public void getMonthDiary(int year, int month, LoadDiaryListCallback callback) {
        mLocal.getMonthDiary(year, month, callback);
    }

    @Override
    public void getDiary(int year, int month, int day, GetDiaryCallback callback) {
        mLocal.getDiary(year, month, day, callback);
    }

    @Override
    public void queryDiary(String query, LoadDiaryListCallback callback) {
        mLocal.queryDiary(query, callback);
    }

    @Override
    public void insertDiary(Diary diary) {
        mLocal.insertDiary(diary);
    }

    @Override
    public void updateDiary(Diary diary) {
        mLocal.updateDiary(diary);
    }

    @Override
    public void updateDiaryTitle(int year, int month, int day, String title) {
        mLocal.updateDiaryTitle(year, month, day, title);
    }

    @Override
    public void updateDiaryPic(int year, int month, int day, String pic) {
        mLocal.updateDiaryPic(year, month, day, pic);
    }

    @Override
    public void updateDiaryContent(int year, int month, int day, String content) {
        mLocal.updateDiaryContent(year, month, day, content);
    }

    @Override
    public void updateDiaryWeather(int year, int month, int day, String weather) {
        mLocal.updateDiaryWeather(year, month, day, weather);
    }

    @Override
    public void deleteDiary(int year, int month, int day) {
        mLocal.deleteDiary(year, month, day);
    }

    public void destroy(){
        mRepo = null;
    }
}
