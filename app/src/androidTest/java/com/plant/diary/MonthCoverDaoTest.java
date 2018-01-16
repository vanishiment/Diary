package com.plant.diary;

import android.support.test.runner.AndroidJUnit4;
import com.plant.diary.data.model.MonthCover;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MonthCoverDaoTest extends DbTest {

  private MonthCover mInitMonthCover = new MonthCover(2018,1,"color","pic",10);
  private MonthCover mUpdateMonthCover = new MonthCover(2018,1,"color_update","pic",10);

  @Test
  public void insert(){
    mDb.monthCoverDao().insertMonthCover(mInitMonthCover);
    MonthCover monthCover = mDb.monthCoverDao().getMonthCoverTest(2018,1);
    System.out.println("month cover: " + monthCover.toString());
    mDb.monthCoverDao().updateMonthCover(mUpdateMonthCover);
    MonthCover monthCoverUp = mDb.monthCoverDao().getMonthCoverTest(2018,1);
    System.out.println("month cover: " + monthCoverUp.toString());
    mDb.monthCoverDao().deleteMonthCover(monthCoverUp);
    MonthCover monthCoverDe = mDb.monthCoverDao().getMonthCoverTest(2018,1);
    if (monthCoverDe == null){
      System.out.println("null----");
      return;
    }

    System.out.println("month cover: " + monthCoverDe.toString());
  }

  @Test
  public void rxTest(){
    //mDb.monthCoverDao().insertMonthCover(mInitMonthCover);
    System.out.println("insert");
    mDb.monthCoverDao().getMonthCover(2018,1)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.newThread())
        .subscribe(new Consumer<MonthCover>() {
          @Override public void accept(MonthCover monthCover) throws Exception {
            System.out.println("accept:" + monthCover.toString());
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(Throwable throwable) throws Exception {
            System.out.println("error");
          }
        }, new Action() {
          @Override public void run() throws Exception {
            System.out.println("error 22");
          }
        });
    Flowable.just("1")
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.newThread())
        .subscribe(new Consumer<String>() {
          @Override public void accept(String s) throws Exception {
            System.out.println("accept:" + s);
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(Throwable throwable) throws Exception {
            System.out.println("error");
          }
        }, new Action() {
          @Override public void run() throws Exception {
            System.out.println("error 2");
          }
        });
  }
}
