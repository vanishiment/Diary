package com.plant.diaryapp.ui;


import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.plant.diaryapp.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class MediaFragment extends BaseLazyFragment {

    public static final int TYPE_TEXT = 0;
    public static final int TYPE_PHOTO = 1;
    public static final int TYPE_VIDEO = 2;

    @IntDef({TYPE_TEXT,TYPE_PHOTO,TYPE_VIDEO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MediaType{}

    private static final String MEDIA_TYPE = "media_type";
    private @MediaType int mMediaType;

    public MediaFragment() {
    }

    public static MediaFragment getInstance(@MediaType int mediaType){
        MediaFragment mediaFragment = new MediaFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(MEDIA_TYPE,mediaType);
        mediaFragment.setArguments(bundle);
        return mediaFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            mMediaType = getArguments().getInt(MEDIA_TYPE,TYPE_TEXT);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_media;
    }

    @Override
    public void initView(View view) {
        TextView textView = view.findViewById(R.id.frag_media_tv);
        switch (mMediaType){
            case TYPE_TEXT:
                textView.setText(R.string.nav_text);
                break;
            case TYPE_PHOTO:
                textView.setText(R.string.nav_photo);
                break;
            case TYPE_VIDEO:
                textView.setText(R.string.nav_video);
                break;
        }
    }

    @Override
    public void onFragmentVisible() {
        switch (mMediaType){
            case TYPE_TEXT:
                updateActivityTitle(getString(R.string.nav_text));
                break;
            case TYPE_PHOTO:
                updateActivityTitle(getString(R.string.nav_photo));
                break;
            case TYPE_VIDEO:
                updateActivityTitle(getString(R.string.nav_video));
                break;
        }
    }

    @Override
    public void loadData() {

    }
}
