package com.plant.diary.data.local.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.plant.diary.data.model.Diary;
import com.plant.diary.data.model.MonthCover;

@Database(entities = { Diary.class, MonthCover.class }, version = 1, exportSchema = false)
public abstract class DiaryDatabase extends RoomDatabase {

  private static final String DB_NAME = "diary.db";
  private static DiaryDatabase sIns;

  public abstract DiaryDao diaryDao();

  public abstract MonthCoverDao monthCoverDao();

  public static DiaryDatabase get(Context context) {
    if (sIns == null) {
      synchronized (DiaryDatabase.class) {
        if (sIns == null) {
          sIns = Room.databaseBuilder(context.getApplicationContext(), DiaryDatabase.class, DB_NAME)
              .build();
        }
      }
    }
    return sIns;
  }
}
