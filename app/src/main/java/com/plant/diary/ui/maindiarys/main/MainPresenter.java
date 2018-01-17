package com.plant.diary.ui.maindiarys.main;

import android.support.annotation.NonNull;
import com.plant.diary.data.model.MonthCover;
import com.plant.diary.data.repo.MonthCoverRepo;
import com.plant.diary.ui.compat.BaseSchedulerProvider;
import com.plant.diary.utils.DateUtil;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.List;

public class MainPresenter implements MainContract.Presenter {

  @NonNull
  private final MonthCoverRepo mRepo;

  @NonNull
  private final MainContract.View mView;

  @NonNull
  private final BaseSchedulerProvider mSchedulerProvider;

  private CompositeDisposable mCompositeDisposable;

  MainPresenter(@NonNull MonthCoverRepo repo, @NonNull MainContract.View view,
      @NonNull BaseSchedulerProvider schedulerProvider) {
    mRepo = repo;
    mView = view;
    mSchedulerProvider = schedulerProvider;

    mCompositeDisposable = new CompositeDisposable();
    mView.setPresenter(this);
  }

  @Override public void subscribe() {
    int curYear = DateUtil.getCurYear();
    queryMonthCovers(curYear);
  }

  @Override public void unSubscribe() {
    mCompositeDisposable.clear();
  }

  @Override public void queryMonthCovers(int year) {

    mCompositeDisposable.clear();
    Disposable disposable = mRepo.getYearMonthCoverList(year)
        .subscribeOn(mSchedulerProvider.io())
        .observeOn(mSchedulerProvider.ui())
        .subscribe(
            // onNext
            this::processResult,
            // onError
            throwable -> {
              mView.showLoadingCardsError();
            });
    mCompositeDisposable.add(disposable);
  }

  private void processResult(List<MonthCover> monthCovers){
    if (monthCovers.isEmpty()){
      mView.showNoCards();
    }else {
      mView.showCards(monthCovers);
    }
  }
}
