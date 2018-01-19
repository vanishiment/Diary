package com.plant.diaryapp;


import android.app.Application;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;

import com.bilibili.magicasakura.utils.ThemeUtils;

public class DiaryApp extends Application implements ThemeUtils.switchColor {

    @Override
    public void onCreate() {
        super.onCreate();

        // 主题切换
        ThemeUtils.setSwitchColor(this);
    }

    @Override
    public int replaceColorById(Context context, @ColorRes int colorId) {
        if (ThemeHelper.isDefaultTheme(context)) {
            return context.getResources().getColor(colorId);
        }
        String theme = ThemeUtil.getTheme(context);
        if (theme != null) {
            colorId = ThemeUtil.getThemeColorId(context, colorId, theme);
        }
        return context.getResources().getColor(colorId);
    }

    @Override
    public int replaceColor(Context context, @ColorInt int originColor) {
        if (ThemeHelper.isDefaultTheme(context)) {
            return originColor;
        }
        String theme = ThemeUtil.getTheme(context);
        int colorId = -1;

        if (theme != null) {
            colorId = ThemeUtil.getThemeColor(context, originColor, theme);
        }
        return colorId != -1 ? getResources().getColor(colorId) : originColor;
    }
}
