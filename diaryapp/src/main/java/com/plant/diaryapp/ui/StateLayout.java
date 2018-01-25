package com.plant.diaryapp.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.plant.diaryapp.R;


public class StateLayout extends FrameLayout implements View.OnClickListener{

    private RelativeLayout mLayout;
    private TextView mTextView;
    private Button mButton;
    private OnRetryButtonClick mClick;

    public StateLayout(@NonNull Context context) {
        this(context,null);
    }

    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.common_state_layout,this);
        mLayout = findViewById(R.id.state_layout);
        mTextView = findViewById(R.id.state_empty_tv);
        mButton = findViewById(R.id.state_empty_btn);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.state_empty_btn){
            if (null != mClick){
                mClick.onRetryButtonClick();
            }
        }
    }

    public void setClick(OnRetryButtonClick click) {
        mClick = click;
    }

    public void showEmpty(String emptyMsg){
        mLayout.setVisibility(VISIBLE);
        mTextView.setVisibility(VISIBLE);
        if (!TextUtils.isEmpty(emptyMsg)) mTextView.setText(emptyMsg);
        mButton.setVisibility(GONE);
    }

    public void showError(String errorMsg){
        mLayout.setVisibility(VISIBLE);
        mTextView.setVisibility(GONE);
        if (!TextUtils.isEmpty(errorMsg)) mButton.setText(errorMsg);
        mButton.setVisibility(VISIBLE);
        mButton.setClickable(false);
    }

    public void showErrorWithRetry(String errorMsg,OnRetryButtonClick click){
        setClick(click);
        mLayout.setVisibility(VISIBLE);
        mTextView.setVisibility(GONE);
        if (!TextUtils.isEmpty(errorMsg)) mButton.setText(errorMsg);
        mButton.setVisibility(VISIBLE);
        mButton.setClickable(true);
    }

    public void hideEveryThing(){
        mLayout.setVisibility(GONE);
    }

    public interface OnRetryButtonClick{
        void onRetryButtonClick();
    }
}
