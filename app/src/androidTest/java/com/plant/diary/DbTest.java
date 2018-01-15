package com.plant.diary;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import com.plant.diary.data.local.db.DiaryDatabase;
import org.junit.After;
import org.junit.Before;

public class DbTest {

  DiaryDatabase mDb;

  @Before
  public void initDb(){
    mDb = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),DiaryDatabase.class).build();
  }

  @After
  public void closeDb(){
    mDb.close();
  }

}
