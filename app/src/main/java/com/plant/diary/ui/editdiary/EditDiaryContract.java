package com.plant.diary.ui.editdiary;

import com.plant.diary.data.model.Diary;
import com.plant.diary.ui.base.BasePresenter;
import com.plant.diary.ui.base.BaseView;

public interface EditDiaryContract {

  interface View extends BaseView<Presenter>{

    void showCloseEditDiaryDialog();

    void showDiary(Diary diary);

    void showDoneDialog();

  }

  interface Presenter extends BasePresenter{

    void getDiary(int year,int month,int day);

    void insertDiary(Diary diary);

    void updateDiary(Diary diary);

    void deleteDiary(Diary diary);

  }

}
