package com.plant.diary.ui.monthdiary;

import com.plant.diary.data.model.Diary;
import com.plant.diary.ui.base.BasePresenter;
import com.plant.diary.ui.base.BaseView;
import java.util.List;

public interface MonthDiaryContact {

  interface View extends BaseView<Presenter>{

    void setLoadingIndicator(boolean show);

    void showNoMonthDiary();

    void showMonthDiaryError();

    void showMonthDiary(List<Diary> diaryList);

  }

  interface Presenter extends BasePresenter{

    void loadMonthDiary(boolean showLoading, int year,int month);

  }
}
