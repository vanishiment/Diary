package com.plant.diaryapp.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.plant.diaryapp.R;
import com.plant.diaryapp.app.DiaryApp;
import com.plant.diaryapp.data.datasource.DiaryDataSource;
import com.plant.diaryapp.data.local.LocalDiaryDataSource;
import com.plant.diaryapp.data.model.Diary;
import com.plant.diaryapp.data.remote.RemoteDiaryDataSource;
import com.plant.diaryapp.data.repo.DiaryRepo;
import com.plant.diaryapp.fragment.CardFragment;
import com.plant.diaryapp.support.GlideEngineOverride;
import com.plant.diaryapp.support.permission.MPermission;
import com.plant.diaryapp.support.permission.callback.PermissionCallback;
import com.plant.diaryapp.utils.AppExecutors;
import com.plant.diaryapp.widget.EditImageLayout;
import com.plant.diaryapp.widget.TipDialog;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;
import java.util.Locale;

public class EditAct extends ToolbarAct implements EditImageLayout.OnEditImageLayoutClickListener, TipDialog.OnButtonClickListener {

    private static final int REQUEST_CODE_CHOOSE = 1001;

    public static final String YEAR = "year";
    public static final String MONTH = "month";
    public static final String DAY = "day";

    private int mYear, mMonth, mDay;

    private Diary mDiary = new Diary();
    private Diary mDiaryCopy = new Diary();

    private EditImageLayout mEditImageLayout;
    private TextView mDate;
    private EditText mTitle;
    private TextView mWeather;
    private EditText mContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_edit);
        handleIntent();
        initView();
        setupViews();
        loadData();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            mYear = intent.getIntExtra(YEAR, -1);
            mMonth = intent.getIntExtra(MONTH, -1);
            mDay = intent.getIntExtra(DAY, -1);
        }
    }

    private void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        }
        setDisplayShowTitleEnabled(true);
        mEditImageLayout = findViewById(R.id.edit_edit_image_layout);
        mDate = findViewById(R.id.edit_date);
        mWeather = findViewById(R.id.edit_diary_weather);
        mTitle = findViewById(R.id.edit_diary_title);
        mContent = findViewById(R.id.edit_diary_content);
    }

    private void setupViews() {
        if (mYear != 0 && mMonth != 0 && mDay != 0) {
            mDiary.setYear(mYear);
            mDiary.setMonth(mMonth);
            mDiary.setDay(mDay);
        } else {
            finish();
        }
        mEditImageLayout.setOnEditImageLayoutClickListener(this);
        mDate.setText(String.format(Locale.getDefault(), "%d/%d/%d", mMonth, mDay, mYear));
        mTitle.setHint(R.string.edit_title_hint);
        mContent.setHint(R.string.edit_content_hint);
    }

    private void loadData() {
        LocalDiaryDataSource local = LocalDiaryDataSource.getSource(DiaryApp.getDiaryDb().mDiaryDao(), AppExecutors.get());
        DiaryRepo repo = DiaryRepo.getRepo(local, new RemoteDiaryDataSource());
        repo.getDiary(mYear, mMonth, mDay, new DiaryDataSource.GetDiaryCallback() {
            @Override
            public void onDiaryLoaded(Diary diary) {
                resetDiary(diary);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void resetDiary(Diary diary) {
        if (diary == null || diary.getDay() != mDay || diary.getYear() != mYear || diary.getMonth() != mMonth) {
            return;
        }
        mDiary = diary;
        mDiaryCopy = diary;
        String pic = diary.getPic();
        if (!TextUtils.isEmpty(pic)) {
            Glide.with(this).load(pic).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                    mEditImageLayout.setImageDrawable(resource);
                }
            });
        }
        String title = diary.getTitle();
        if (!TextUtils.isEmpty(title)) {
            mTitle.setText(title);
        }
        String content = diary.getContent();
        if (!TextUtils.isEmpty(content)) {
            mContent.setText(content);
        }
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    public boolean canBackPressed() {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_diary, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_diary_edit_done) {
            editDone();
        } else if (item.getItemId() == android.R.id.home) {
            editCancel();
        }
        return super.onOptionsItemSelected(item);
    }

    private void editDone() {
        Editable editableTitle = mTitle.getText();
        if (editableTitle != null) {
            mDiary.setTitle(editableTitle.toString());
        } else {
            mDiary.setTitle("");
        }
        Editable editableContent = mContent.getText();
        if (editableContent != null) {
            mDiary.setContent(editableContent.toString());
        } else {
            mDiary.setContent("");
        }

        LocalDiaryDataSource local = LocalDiaryDataSource.getSource(DiaryApp.getDiaryDb().mDiaryDao(), AppExecutors.get());
        DiaryRepo repo = DiaryRepo.getRepo(local, new RemoteDiaryDataSource());
        repo.getDiary(mYear, mMonth, mDay, new DiaryDataSource.GetDiaryCallback() {
            @Override
            public void onDiaryLoaded(Diary diary) {
                repo.updateDiary(mDiary);
                EditAct.this.finish();
            }

            @Override
            public void onDataNotAvailable() {
                repo.insertDiary(mDiary);
                EditAct.this.finish();
            }
        });
    }

    private void editCancel() {
        boolean checkDiary = mDiary.equals(mDiaryCopy);
        if (!checkDiary && !TextUtils.isEmpty(mDiary.getTitle()) && !TextUtils.isEmpty(mDiary.getContent()) && !TextUtils.isEmpty(mDiary.getPic())) {
            showTipDialog(getSupportFragmentManager());
        } else {
            finish();
        }
    }

    private void showTipDialog(FragmentManager fm) {
        TipDialog tipDialog = new TipDialog();
        tipDialog.show(fm, "TipDialog");
    }

    private void cancelTipDialog() {
        TipDialog tipDialog = (TipDialog) getSupportFragmentManager().findFragmentByTag("TipDialog");
        if (tipDialog != null) {
            tipDialog.dismiss();
        }
    }

    @Override
    public void onAddClick(View view) {
        MPermission.getInstance().request(this, new PermissionCallback() {
            @Override
            public void onGranted() {
                showPickPhoto();
            }

            @Override
            public void onDenied(List<String> perms) {

            }
        }, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"});
    }

    private void showPickPhoto() {
        Matisse.from(this)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
                .countable(true)
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, "com.plant.diaryapp.fileprovider"))
                .maxSelectable(1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85F)
                .imageEngine(new GlideEngineOverride())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    @Override
    public void onDeleteClick(View view) {
        mEditImageLayout.setImageDrawable(null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<Uri> uris = Matisse.obtainResult(data);
            if (uris != null && uris.size() > 0) {
                Uri uri = uris.get(0);
                String url = uri.toString();
                Glide.with(EditAct.this).load(uri).into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        mEditImageLayout.setImageDrawable(resource);
                    }
                });
                mDiary.setPic(url);
            }
        }
    }

    @Override
    public void onDone() {
        cancelTipDialog();
        finish();
    }

    @Override
    public void onCancel() {
        cancelTipDialog();
    }
}
