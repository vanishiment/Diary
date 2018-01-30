package com.plant.diaryapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.plant.diaryapp.R;


public class DiaryAct extends ToolbarAct {

    public static final String YEAR = "year";
    public static final String MONTH = "month";
    public static final String DAY = "day";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_diary);
    }
}
