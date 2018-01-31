package com.plant.diaryapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.plant.diaryapp.R;
import com.plant.diaryapp.fragment.SettingFrag;

public class SettingsAct extends ToolbarAct {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_settings);
        initView();
    }

    private void initView(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(R.string.app_settings);
        }
        setDisplayShowTitleEnabled(true);
        SettingFrag settingFrag = (SettingFrag) getSupportFragmentManager().findFragmentByTag("SettingFrag");
        if (settingFrag == null){
            settingFrag = new SettingFrag();
            getSupportFragmentManager().beginTransaction().add(R.id.settings_container,settingFrag,"SettingFrag").commit();
        }
    }

    @Override
    public boolean canBack() {
        return true;
    }
}
