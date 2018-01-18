package com.plant.diary.ui.detaildiary;

import com.plant.diary.data.repo.DiaryRepo;
import com.plant.diary.ui.compat.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class DetailDiaryPresenter implements DetailDiaryContract.Presenter {

  private final DiaryRepo mRepo;
  private final DetailDiaryContract.View mView;
  private final SchedulerProvider mProvider;

  private CompositeDisposable mCompositeDisposable;
  private Disposable mQuery;

  public DetailDiaryPresenter(DiaryRepo repo, DetailDiaryContract.View view,
      SchedulerProvider provider) {
    mRepo = repo;
    mView = view;
    mProvider = provider;
    mCompositeDisposable = new CompositeDisposable();
    mView.setPresenter(this);
  }

  @Override public void subscribe() {

  }

  @Override public void unSubscribe() {
    mCompositeDisposable.clear();
  }

  @Override public void getDiary(int year, int month, int day) {
    Disposable disposable = mRepo.getDayDiary(year, month, day)
        .subscribeOn(mProvider.io())
        .observeOn(mProvider.ui())
        .subscribe(diaryList -> {
          if (diaryList.isEmpty()){

          }else {
            mView.showDiary(diaryList.get(0));
          }
        });
    mCompositeDisposable.add(disposable);
  }
}
