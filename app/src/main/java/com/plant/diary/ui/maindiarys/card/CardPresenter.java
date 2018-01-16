package com.plant.diary.ui.maindiarys.card;

import android.support.annotation.NonNull;
import android.util.Log;
import com.plant.diary.data.model.MonthCover;
import com.plant.diary.data.repo.MonthCoverAndDiaryRepo;
import com.plant.diary.ui.compat.SchedulerProvider;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class CardPresenter implements CardContract.Presenter {

  @NonNull
  private final MonthCoverAndDiaryRepo mRepo;

  @NonNull
  private final CardContract.View mView;

  @NonNull
  private final SchedulerProvider mSchedulerProvider;

  @NonNull
  private CompositeDisposable mCompositeDisposable;

  private Disposable mDisposableInsert,mDisposableUpdate,mDisposableQuery,mDisposableInsertOrUpdate;

  CardPresenter(@NonNull MonthCoverAndDiaryRepo repo, @NonNull CardContract.View view,
      @NonNull SchedulerProvider schedulerProvider) {
    mRepo = repo;
    mView = view;
    mSchedulerProvider = schedulerProvider;
    mView.setPresenter(this);
    mCompositeDisposable = new CompositeDisposable();
  }

  @Override public void subscribe() {

  }

  @Override public void unSubscribe() {
    mCompositeDisposable.clear();
  }

  @Override public void queryMonthCovers(int year) {

  }

  @Override public void queryMonthCover(int year, int month) {
    if (mDisposableQuery != null) mCompositeDisposable.delete(mDisposableQuery);
    mDisposableQuery = Flowable.create((FlowableOnSubscribe<MonthCover>) emitter -> {
      MonthCover monthCover =  mRepo.getMonthCover(year, month);
      if (monthCover != null && monthCover.getYear() > 0){
        emitter.onNext(monthCover);
      }else {
        emitter.onComplete();
      }
    },BackpressureStrategy.LATEST)
        .subscribeOn(mSchedulerProvider.io())
        .observeOn(mSchedulerProvider.ui())
        .subscribe(new Consumer<MonthCover>() {
                     @Override public void accept(MonthCover monthCover) throws Exception {
                       mView.updateMonthCover(monthCover);
                     }
                   }, new Consumer<Throwable>() {
                     @Override public void accept(Throwable throwable) throws Exception {

                     }
                   }, new Action() {
                     @Override public void run() throws Exception {
                      mView.resetMonthCover();
                     }
                   }

        );

    mCompositeDisposable.add(mDisposableQuery);
  }

  @Override public void insertMonthCover(MonthCover monthCover) {
    if (mDisposableInsert != null) mCompositeDisposable.delete(mDisposableInsert);
    mDisposableInsert = Flowable.just("1")
        .subscribeOn(mSchedulerProvider.io())
        .observeOn(mSchedulerProvider.ui())
        .subscribe(
            s -> mRepo.insertMonthCover(monthCover)
        );
    mCompositeDisposable.add(mDisposableInsert);
  }

  @Override public void updateMonthCover(MonthCover monthCover) {
    if (mDisposableUpdate != null) mCompositeDisposable.delete(mDisposableUpdate);
    mDisposableUpdate = Flowable.just("1")
        .subscribeOn(mSchedulerProvider.io())
        .observeOn(mSchedulerProvider.ui())
        .subscribe(
            s -> mRepo.updateMonthCover(monthCover)
        );
    mCompositeDisposable.add(mDisposableUpdate);
  }

  @Override public void insertOrUpdateMonthCover(int year, int month,MonthCover monthCover) {
    if (mDisposableInsertOrUpdate != null) mCompositeDisposable.delete(mDisposableInsertOrUpdate);

    mDisposableInsertOrUpdate = Flowable.create((FlowableOnSubscribe<MonthCover>) emitter -> {
      MonthCover monthCover1 = mRepo.getMonthCover(year, month);
      if (monthCover1 != null && monthCover1.getYear() > 0){
        mRepo.updateMonthCover(monthCover);
      }else {
        mRepo.insertMonthCover(monthCover);
      }
      emitter.onNext(monthCover);
    }, BackpressureStrategy.LATEST)
        .subscribeOn(mSchedulerProvider.io())
        .observeOn(mSchedulerProvider.ui())
        .subscribe(monthCover12 -> {

        }, throwable -> {

        }, () -> {

        });
    mCompositeDisposable.add(mDisposableInsertOrUpdate);
  }
}
