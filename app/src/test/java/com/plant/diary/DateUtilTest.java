package com.plant.diary;

import com.plant.diary.utils.DateUtil;
import org.junit.Test;

public class DateUtilTest {

  @Test
  public void testDate(){
    int curMonth = DateUtil.getCurMonth();
    int curDay = DateUtil.getCurDay();
    int curMonthDays = DateUtil.getCurMonthDays();
    boolean isSta = DateUtil.isSta(curDay + 4);
    boolean isSun = DateUtil.isSun(curDay + 5);
    int firstDayInWeekOfMonth = DateUtil.firstDayInWeekOfMonth();

    p(curMonth);
    p(curDay);
    p(curMonthDays);
    System.out.println("isSta " + isSta);
    System.out.println("isSun " + isSun);
    p(firstDayInWeekOfMonth);

    int monthOfDays = DateUtil.getMonthDays(2018,0,1);
    p(monthOfDays);
  }

  private void p(int msg){
    pCore(msg,"");
  }

  private void p(String msg){
    pCore(0,msg);
  }

  private void pCore(int msgInt,String msgStr){
    System.out.println("" + msgInt + " " + msgStr);
  }
}
