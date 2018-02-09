package com.plant.diaryapp.app;


import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.plant.diaryapp.BuildConfig;
import com.plant.diaryapp.app.theme.ThemeHelper;
import com.plant.diaryapp.app.theme.ThemeUtil;
import com.plant.diaryapp.data.local.DiaryDb;
import com.tencent.bugly.crashreport.CrashReport;
import com.tendcloud.tenddata.TCAgent;

public class DiaryApp extends Application implements ThemeUtils.switchColor {

    private static DiaryDb mDiaryDb;

    @Override
    public void onCreate() {
        super.onCreate();

        //TalkingData 统计
        if (BuildConfig.DEBUG){
            TCAgent.LOG_ON = true;
        }
        TCAgent.init(this);

        // Bugly 异常上报
        CrashReport.initCrashReport(this,"738e341a8d",false);

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
