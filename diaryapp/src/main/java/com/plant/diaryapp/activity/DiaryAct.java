package com.plant.diaryapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.plant.diaryapp.R;
import com.plant.diaryapp.data.model.Diary;

import java.util.Locale;


public class DiaryAct extends ToolbarAct {

    public static final String YEAR = "year";
    public static final String MONTH = "month";
    public static final String DAY = "day";

    public static final String DIARY = "diary";

    private int mYear, mMonth, mDay;
    private Diary mDiary;

    private ImageView mIv;
    private TextView mDate, mTitle, mWeather, mContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_diary);
        setDisplayShowTitleEnabled(true);
        handleIntent();
        initView();
        setupViews();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            mYear = intent.getIntExtra(YEAR, -1);
            mMonth = intent.getIntExtra(MONTH, -1);
            mDay = intent.getIntExtra(DAY, -1);
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mDiary = bundle.getParcelable(DIARY);
            }
        }
    }

    private void initView() {
        setDisplayShowTitleEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            if (mYear != -1 && mMonth != -1 && mDay != -1) {
                actionBar.setTitle(mMonth + "·" + mDay + "·" + mYear);
            }
        }
        mIv = findViewById(R.id.diary_iv);
        mDate = findViewById(R.id.diary_date);
        mWeather = findViewById(R.id.diary_weather);
        mTitle = findViewById(R.id.diary_title);
        mContent = findViewById(R.id.diary_content);
    }

    private void setupViews() {
        if (mDiary != null) {
            if (mDiary.getDay() != 0 && mDiary.getMonth() != 0 && mDiary.getYear() != 0){
                mDate.setText(String.format(Locale.getDefault(),"%d/%d/%d", mDiary.getYear(), mDiary.getMonth(), mDiary.getDay()));
            }
            if (!TextUtils.isEmpty(mDiary.getPic())){
            Glide.with(mIv).load(R.drawable.item_bg).into(mIv);
            }
            if (!TextUtils.isEmpty(mDiary.getWeather())){
                mWeather.setText(mDiary.getWeather());
            }
            if (!TextUtils.isEmpty(mDiary.getTitle())){
                mTitle.setText(mDiary.getTitle());
            }
            if (!TextUtils.isEmpty(mDiary.getContent())){
                mContent.setText(mDiary.getContent());
            }
        } else {
            finish();
        }
    }

    @Override
    public boolean canBack() {
        return true;
    }
}
