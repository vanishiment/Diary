package com.plant.diaryapp.app;


import android.app.Application;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.plant.diaryapp.app.theme.ThemeHelper;
import com.plant.diaryapp.app.theme.ThemeUtil;
import com.plant.diaryapp.data.local.DiaryDb;

public class DiaryApp extends Application implements ThemeUtils.switchColor {

    private static DiaryDb mDiaryDb;

    @Override
    public void onCreate() {
        super.onCreate();

        // 主题切换
        ThemeUtils.setSwitchColor(this);

        mDiaryDb = DiaryDb.get(this);
    }

    public static DiaryDb getDiaryDb() {
        return mDiaryDb;
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
