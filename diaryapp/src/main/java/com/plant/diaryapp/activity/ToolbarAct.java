package com.plant.diaryapp.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.plant.diaryapp.R;


public class ToolbarAct extends AppCompatActivity {

    Toolbar mToolbar;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mToolbar = findViewById(R.id.toolbar);

        if (mToolbar != null){
            setSupportActionBar(mToolbar);
            mToolbar.setNavigationOnClickListener(v -> onBackPressed());

            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null){
                actionBar.setDisplayShowHomeEnabled(true);
                if (canBack()){
                    actionBar.setDisplayHomeAsUpEnabled(true);
                }
            }
        }
    }

    public boolean canBack(){
        return false;
    }

    public void setDisplayShowTitleEnabled(boolean show){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayShowTitleEnabled(show);
        }
    }
}
