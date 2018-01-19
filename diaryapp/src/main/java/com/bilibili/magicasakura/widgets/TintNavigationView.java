package com.bilibili.magicasakura.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.util.AttributeSet;

import com.bilibili.magicasakura.utils.TintManager;


public class TintNavigationView extends NavigationView implements Tintable, AppCompatBackgroundHelper.BackgroundExtensible{

    private AppCompatBackgroundHelper mBackgroundHelper;

    public TintNavigationView(Context context) {
        this(context,null);
    }

    public TintNavigationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TintNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (isInEditMode()) {
            return;
        }
        TintManager tintManager = TintManager.get(context);

        mBackgroundHelper = new AppCompatBackgroundHelper(this, tintManager);
        mBackgroundHelper.loadFromAttribute(attrs, defStyleAttr);
    }

    @Override
    public void setBackgroundResource(int resId) {
            super.setBackgroundResource(resId);
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
    }

    @Override
    public void setBackgroundTintList(int resId) {
    }

    @Override
    public void setBackgroundTintList(int resId, PorterDuff.Mode mode) {
    }

    @Override
    public void tint() {
        if (mBackgroundHelper != null) {
            mBackgroundHelper.tint();
        }
    }

    @Override
    public void setItemTextColor(@Nullable ColorStateList textColor) {
        super.setItemTextColor(textColor);
    }

    @Override
    public void setItemIconTintList(@Nullable ColorStateList tint) {
        super.setItemIconTintList(tint);
    }
}
