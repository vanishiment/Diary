package com.plant.diary.ui.monthdiary;

import android.support.annotation.NonNull;
import com.plant.diary.data.repo.DiaryRepo;
import com.plant.diary.data.repo.MonthCoverRepo;
import com.plant.diary.ui.compat.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MonthDiaryPresenter implements MonthDiaryContact.Presenter {

  @NonNull
  private final DiaryRepo mRepo;

  @NonNull
  private final MonthDiaryContact.View mView;

  @NonNull
  private final SchedulerProvider mSchedulerProvider;

  @NonNull
  private CompositeDisposable mCompositeDisposable;

  public MonthDiaryPresenter(@NonNull DiaryRepo repo, @NonNull MonthDiaryContact.View view,
      @NonNull SchedulerProvider schedulerProvider) {
    mRepo = repo;
    mView = view;
    mSchedulerProvider = schedulerProvider;
    mCompositeDisposable = new CompositeDisposable();
    mView.setPresenter(this);
  }

  @Override public void subscribe() {

  }

  @Override public void unSubscribe() {
    mCompositeDisposable.clear();
  }

  @Override public void loadMonthDiary(boolean showLoading,int year, int month) {
    mCompositeDisposable.clear();
    if (showLoading){
      mView.setLoadingIndicator(true);
    }
    Disposable disposable = mRepo.getMonthDiaryList(year, month)
        .subscribeOn(mSchedulerProvider.io())
        .observeOn(mSchedulerProvider.ui())
        .subscribe(
            diaryList -> {
              if (diaryList.isEmpty()){
                mView.showNoMonthDiary();
                if (showLoading){
                  mView.setLoadingIndicator(false);
                }
              }else {
                mView.showMonthDiary(diaryList);
                if (showLoading){
                  mView.setLoadingIndicator(false);
                }
              }
            },
            throwable -> {
              mView.showMonthDiaryError();
              if (showLoading){
                mView.setLoadingIndicator(false);
              }
            }
        );
    mCompositeDisposable.add(disposable);
  }
}
