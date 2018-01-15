package com.plant.diary.ui.maindiarys.main;

import com.plant.diary.data.model.MonthCover;
import com.plant.diary.ui.base.BasePresenter;
import com.plant.diary.ui.base.BaseView;
import java.util.List;

public interface MainContract {

  interface View extends BaseView<Presenter>{

    void setupViews(android.view.View view);

    void setYear(int year);

    void showCards(List<MonthCover> monthCovers);

    void setLoadingIndicator(boolean show);

    void showLoadingCardsError();

    void showNoCards();
  }

  interface Presenter extends BasePresenter{

    void queryMonthCovers(int year);

  }
}
