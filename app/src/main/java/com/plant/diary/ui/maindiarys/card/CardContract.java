package com.plant.diary.ui.maindiarys.card;

import com.plant.diary.data.model.MonthCover;
import com.plant.diary.ui.base.BasePresenter;
import com.plant.diary.ui.base.BaseView;

public interface CardContract {

  interface View extends BaseView<Presenter> {

    void updateMonthCover(MonthCover monthCover);

    void setMonthTv(String month);

    void setCardBackground(boolean pic, String background);

    void setMonthProgress(int progress);

    void setMonthProgressTv(String progressText);

    void showMoreBottomSheetDialog();

    void pickColor(String color);

    void pickPic();

    void onPickPicResult();
  }

  interface Presenter extends BasePresenter {

    void queryMonthCovers(int year);

    void queryMonthCover(int year, int month);

    void insertMonthCover(MonthCover monthCover);

    void updateMonthCover(MonthCover monthCover);

    void insertOrUpdateMonthCover(int year, int month,MonthCover monthCover);
  }
}
