package com.plant.diary;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.support.test.runner.AndroidJUnit4;
import com.plant.diary.data.model.MonthCover;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class) public class MonthCoverDaoTest extends DbTest {

  @Rule
  public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

  private MonthCover mInitMonthCover = new MonthCover(2018, 1, "color", "pic", 10);
  private MonthCover mUpdateMonthCover = new MonthCover(2018, 1, "color_update", "pic", 10);

  @Test public void getMonthCoverWhenNoMonthCover() {
    mDb.monthCoverDao().getMonthCover(2018, 1).test().assertNoValues();
  }

  @Test public void insertAndGetMonthCover() {

    mDb.monthCoverDao().insertMonthCover(mInitMonthCover);

    mDb.monthCoverDao().getMonthCover(2018, 1).test().assertValue(monthCovers -> {
        MonthCover monthCover = monthCovers.get(0);
      System.out.println("---" + monthCover.toString());
      return monthCover != null
          && monthCover.getYear() == mInitMonthCover.getYear()
          && monthCover.getMonth() == mInitMonthCover.getMonth();

    });
  }

  @Test
  public void updateAndGetMonthCover(){
    mDb.monthCoverDao().insertMonthCover(mInitMonthCover);

    mDb.monthCoverDao().updateMonthCover(mUpdateMonthCover);

    mDb.monthCoverDao().getMonthCover(2018, 1).test().assertValue(monthCovers -> {
      MonthCover monthCover = monthCovers.get(0);
      System.out.println("---" + monthCover.toString());
      return monthCover != null
          && monthCover.getYear() == mInitMonthCover.getYear()
          && monthCover.getMonth() == mInitMonthCover.getMonth()
          && monthCover.getColor().equals("color_update");

    });
  }

  @Test
  public void deleteAndGetMonthCover(){
    mDb.monthCoverDao().insertMonthCover(mInitMonthCover);
    mDb.monthCoverDao().deleteMonthCover(mInitMonthCover);
    mDb.monthCoverDao().getMonthCover(2018,1)
        .test()
        .assertNoValues();
  }
}
