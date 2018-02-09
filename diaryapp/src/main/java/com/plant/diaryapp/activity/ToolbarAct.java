package com.plant.diaryapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.jaeger.library.StatusBarUtil;
import com.plant.diaryapp.R;
import com.tendcloud.tenddata.TCAgent;


public class ToolbarAct extends AppCompatActivity {

    Toolbar mToolbar;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.blue_primary_dark));
        mToolbar = findViewById(R.id.toolbar);

        if (mToolbar != null){
            setSupportActionBar(mToolbar);
            if (canBackPressed()){
                mToolbar.setNavigationOnClickListener(v -> onBackPressed());
            }

            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null){
                actionBar.setDisplayShowHomeEnabled(true);
                if (canBack()){
                    actionBar.setDisplayHomeAsUpEnabled(true);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        TCAgent.onPageStart(this,"ToolbarAct");
    }

    @Override
    protected void onPause() {
        super.onPause();
        TCAgent.onPageEnd(this,"ToolbarAct");
    }

    public boolean canBack(){
        return false;
    }

    public boolean canBackPressed(){
        return true;
    }

    public void setDisplayShowTitleEnabled(boolean show){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayShowTitleEnabled(show);
        }
    }

    public void launchAct(Class<? extends Activity> clz){
        Intent intent = new Intent(this,clz);
        startActivity(intent);
    }
}
