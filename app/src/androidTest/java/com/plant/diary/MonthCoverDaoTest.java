package com.plant.diary;

import android.support.test.runner.AndroidJUnit4;
import com.plant.diary.data.model.MonthCover;
import io.reactivex.schedulers.Schedulers;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MonthCoverDaoTest extends DbTest {

  private MonthCover mInitMonthCover = new MonthCover(2018,1,"color","pic",10);
  private MonthCover mUpdateMonthCover = new MonthCover(2018,1,"color","pic",10);

  @Test
  public void insert(){
    mDb.monthCoverDao().insertMonthCover(mInitMonthCover);
    mDb.monthCoverDao()
        .getMonthCover(2018,1)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.newThread())
    .subscribe(monthCover -> {
      MatcherAssert.assertThat(mInitMonthCover.getColor(), CoreMatchers.is("color"));
    });
  }
}
