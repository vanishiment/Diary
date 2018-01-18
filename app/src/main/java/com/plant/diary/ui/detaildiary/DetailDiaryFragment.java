package com.plant.diary.ui.detaildiary;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.plant.diary.R;
import com.plant.diary.data.model.Diary;
import com.plant.diary.ui.base.BaseFragment;

public class DetailDiaryFragment extends BaseFragment implements DetailDiaryContract.View{

  @BindView(R.id.fragment_detail_diary_iv) ImageView mIv;
  @BindView(R.id.fragment_detail_diary_title) TextView mTitle;
  @BindView(R.id.fragment_detail_diary_weather) TextView mWeather;
  @BindView(R.id.fragment_detail_diary_content) TextView mContent;

  private DetailDiaryContract.Presenter mPresenter;

  private Diary mCurDiary = new Diary();

  public void setDate(int year,int month,int day){
    mCurDiary.setYear(year);
    mCurDiary.setMonth(month);
    mCurDiary.setDay(day);
  }

  public DetailDiaryFragment() {
    // Required empty public constructor
  }

  @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_detail_diary, container, false);
    ButterKnife.bind(this, root);
    setHasOptionsMenu(true);
    return root;
  }

  @Override public void onResume() {
    super.onResume();
    mPresenter.subscribe();
    mPresenter.getDiary(mCurDiary.getYear(),mCurDiary.getMonth(),mCurDiary.getDay());
  }

  @Override public void onPause() {
    super.onPause();
    mPresenter.unSubscribe();
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()){
      case android.R.id.home:
        getActivity().finish();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void setPresenter(DetailDiaryContract.Presenter presenter) {
    mPresenter = presenter;
  }

  @Override public void showDiary(Diary diary) {
    String topImgUrl = diary.getPic();
    String title = diary.getTitle();
    //String weather = diary.getWeather();
    String content = diary.getContent();
    int year = diary.getYear();
    int month = diary.getMonth();
    int week = diary.getWeek();
    mCurDiary.setYear(year);
    mCurDiary.setMonth(month);
    mCurDiary.setWeek(week);
    mCurDiary.setDay(diary.getDay());

    if (!TextUtils.isEmpty(topImgUrl)){
      Glide.with(this).load(topImgUrl).into(new SimpleTarget<Drawable>() {
        @Override
        public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
          mIv.setImageDrawable(resource);
          mCurDiary.setPic(topImgUrl);
        }
      });
    } else {
      mIv.setVisibility(View.GONE);
    }

    if (!TextUtils.isEmpty(title)){
      mTitle.setText(title);
      mCurDiary.setTitle(title);
    }
    if (!TextUtils.isEmpty(content)){
      mContent.setText(content);
      mCurDiary.setContent(content);
    }
  }

  @Override public void goToEditDiary() {

  }
}
