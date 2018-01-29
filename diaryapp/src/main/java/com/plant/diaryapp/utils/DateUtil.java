package com.plant.diaryapp.utils;

import java.util.Calendar;

public class DateUtil {

  public static int getCurYear(){
    Calendar calendar = Calendar.getInstance();
    return calendar.get(Calendar.YEAR);
  }

  public static int getCurMonth(){
    Calendar calendar = Calendar.getInstance();
    return calendar.get(Calendar.MONTH) + 1;
  }

  public static int getCurDay(){
    Calendar calendar = Calendar.getInstance();
    return calendar.get(Calendar.DAY_OF_MONTH);
  }

  public static int getCurMonthDays(){
    Calendar calendar = Calendar.getInstance();
    return calendar.getActualMaximum(Calendar.DATE);
  }

  public static int getMonthDays(int year,int month,int day){
    Calendar calendar = Calendar.getInstance();
    calendar.set(year, month, day);
    return calendar.getActualMaximum(Calendar.DATE);
  }

  public static boolean isSta(int day){
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.DAY_OF_MONTH, day);
    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
    return dayOfWeek == 7;
  }

  public static boolean isSun(int day){
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.DAY_OF_MONTH, day);
    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
    return dayOfWeek == 1;
  }

  public static int firstDayInWeekOfMonth(){
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    //SimpleDateFormat format = new SimpleDateFormat("E", Locale.getDefault());
    return calendar.get(Calendar.DAY_OF_WEEK) - 1;
  }
}
