package com.plant.diary.ui.detaildiary;

import com.plant.diary.data.model.Diary;
import com.plant.diary.ui.base.BasePresenter;
import com.plant.diary.ui.base.BaseView;

public interface DetailDiaryContract {

  interface View extends BaseView<Presenter> {

    void showDiary(Diary diary);

    void goToEditDiary();
  }

  interface Presenter extends BasePresenter {

    void getDiary(int year, int month, int day);
  }
}
