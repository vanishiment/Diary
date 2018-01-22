package com.plant.diaryapp.data.repo;


import com.plant.diaryapp.data.datasource.DiaryBookDataSource;
import com.plant.diaryapp.data.local.LocalDiaryBookDataSource;
import com.plant.diaryapp.data.model.DiaryBook;
import com.plant.diaryapp.data.remote.RemoteDiaryDataSource;

import java.util.List;

public class DiaryBookRepo implements DiaryBookDataSource{

    private static DiaryBookRepo mRepo;
    private LocalDiaryBookDataSource mLocal;
    private RemoteDiaryDataSource mRemote;

    private DiaryBookRepo(LocalDiaryBookDataSource local,RemoteDiaryDataSource remote) {
        mLocal = local;
        mRemote = remote;
    }

    public static DiaryBookRepo getRepo(LocalDiaryBookDataSource local,RemoteDiaryDataSource remote){
        if (mRepo == null){
            synchronized (DiaryBookRepo.class){
                if (mRepo == null){
                    mRepo = new DiaryBookRepo(local, remote);
                }
            }
        }
        return mRepo;
    }


    @Override
    public void getYearDiaryBook(int year, LoadDiaryBooksCallback callback) {
        mLocal.getYearDiaryBook(year, callback);
    }

    @Override
    public void getMonthDiaryBook(int year, int month, GetDiaryBookCallback callback) {
        mLocal.getMonthDiaryBook(year, month, callback);
    }

    @Override
    public void insertDiaryBook(DiaryBook diaryBook) {
        mLocal.insertDiaryBook(diaryBook);
    }

    @Override
    public void updateDiaryBook(DiaryBook diaryBook) {
        mLocal.updateDiaryBook(diaryBook);
    }

    @Override
    public void updateDiaryBookColor(int year, int month, String color) {
        mLocal.updateDiaryBookColor(year, month, color);
    }

    @Override
    public void updateDiaryBookPic(int year, int month, String pic) {
        mLocal.updateDiaryBookPic(year, month, pic);
    }

    @Override
    public void updateDiaryBookDiaryCount(int year, int month, int diaryCount) {
        mLocal.updateDiaryBookDiaryCount(year, month, diaryCount);
    }

    @Override
    public void deleteDiaryBookById(int year, int month) {
        mLocal.deleteDiaryBookById(year, month);
    }

    public void destroy(){
        mRepo = null;
    }
}
