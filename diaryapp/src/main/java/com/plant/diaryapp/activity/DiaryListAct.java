package com.plant.diaryapp.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.plant.diaryapp.R;

public class DiaryListAct extends ToolbarAct {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_diary_list);
    }

    @Override
    public boolean canBack() {
        return true;
    }
}
