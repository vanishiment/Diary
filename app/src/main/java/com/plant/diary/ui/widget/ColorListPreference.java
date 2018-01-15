package com.plant.diary.ui.widget;

import android.content.Context;
import android.support.v7.preference.DialogPreference;
import android.util.AttributeSet;

public class ColorListPreference extends DialogPreference{

  public ColorListPreference(Context context, AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  public ColorListPreference(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public ColorListPreference(Context context, AttributeSet attrs) {
    super(context, attrs);
  }
}
