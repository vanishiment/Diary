package com.plant.diaryapp.support;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.zhihu.matisse.engine.ImageEngine;

public class GlideEngineOverride implements ImageEngine {
    @Override
    public void loadThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView,
                              Uri uri) {
        RequestOptions options = RequestOptions
                .placeholderOf(placeholder)
                .override(resize, resize)
                .centerCrop();

        Glide.with(context)
                .load(uri)
                .apply(options)
                .into(imageView);

    }

    @Override
    public void loadAnimatedGifThumbnail(Context context, int resize, Drawable placeholder,
                                         ImageView imageView, Uri uri) {
        RequestOptions options = RequestOptions
                .placeholderOf(placeholder)
                .override(resize, resize)
                .centerCrop();

        Glide.with(context)
                .load(uri)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        RequestOptions options = RequestOptions
                .priorityOf(Priority.HIGH)
                .override(resizeX, resizeY)
                .centerCrop();

        Glide.with(context)
                .load(uri)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadAnimatedGifImage(Context context, int resizeX, int resizeY, ImageView imageView,
                                     Uri uri) {
        RequestOptions options = RequestOptions
                .priorityOf(Priority.HIGH)
                .override(resizeX, resizeY)
                .centerCrop();

        Glide.with(context)
                .load(uri)
                .apply(options)
                .into(imageView);
    }

    @Override
    public boolean supportAnimatedGif() {
        return true;
    }
}
