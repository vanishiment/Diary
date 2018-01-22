package com.plant.diaryapp.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.plant.diaryapp.data.model.Diary;
import com.plant.diaryapp.data.model.DiaryBook;

@Database(entities = {Diary.class, DiaryBook.class},version = 1,exportSchema = false)
public abstract class DiaryDb extends RoomDatabase{

    private static final String DB_NAME = "diary_app.db";

    private static volatile DiaryDb sDb;

    public abstract DiaryDao mDiaryDao();

    public abstract DiaryBookDao mDiaryBookDao();

    public static DiaryDb get(Context context){
        if (sDb == null){
            synchronized (DiaryDb.class){
                if (sDb == null){
                    sDb = Room.databaseBuilder(context.getApplicationContext(),DiaryDb.class,DB_NAME).build();
                }
            }
        }
        return sDb;
    }

}
