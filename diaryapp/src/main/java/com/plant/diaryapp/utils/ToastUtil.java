package com.plant.diaryapp.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class ToastUtil {

    private static Toast mToast;

    private static Handler sUI = new Handler(Looper.getMainLooper());

    public static void show(Context context, CharSequence charSequence, int duration) {
        try {
            if (null == mToast) {
                mToast = Toast.makeText(context, charSequence, duration);
            } else {
                mToast.setText(charSequence);
                mToast.setDuration(duration);
            }
            postOnUIThread(mToast);
        } catch (Exception e) {
            if (null == mToast) {
                mToast = Toast.makeText(context, charSequence, duration);
            } else {
                mToast.setText(charSequence);
                mToast.setDuration(duration);
            }
            postOnUIThread(mToast);
        }
    }

    public static void show(Context context, int resId, int duration) {
        try {
            if (null == mToast) {
                mToast = Toast.makeText(context, resId, duration);
            } else {
                mToast.setText(resId);
                mToast.setDuration(duration);
            }
            postOnUIThread(mToast);
        } catch (Exception e) {
            if (null == mToast) {
                mToast = Toast.makeText(context, resId, duration);
            } else {
                mToast.setText(resId);
                mToast.setDuration(duration);
            }
            postOnUIThread(mToast);
        }
    }

    private static void postOnUIThread(final Toast toast){
        sUI.post(toast::show);
    }

}
