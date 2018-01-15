package com.plant.diary.utils;

import android.content.Context;

public class Util {

  public static float dpToPixels(int dp, Context context) {
    return dp * (context.getResources().getDisplayMetrics().density);
  }

  public static boolean checkYear(int year) {
    if (year < 1978 || year > 2048) {
      throw new IllegalArgumentException(year + " is not 1978 < && < 2048");
    }

    return true;
  }

  public static boolean checkMonth(int month){
    switch (month) {
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
        return true;
      default:
        throw new IllegalArgumentException(" must be a Month number.");
    }
  }
}
