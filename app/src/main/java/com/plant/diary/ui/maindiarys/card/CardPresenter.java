package com.plant.diary.ui.maindiarys.card;

import android.support.annotation.NonNull;
import com.plant.diary.data.model.MonthCover;
import com.plant.diary.data.repo.MonthCoverRepo;
import com.plant.diary.ui.compat.SchedulerProvider;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import java.util.List;
import org.reactivestreams.Publisher;

public class CardPresenter implements CardContract.Presenter {

  @NonNull
  private final MonthCoverRepo mRepo;

  @NonNull
  private final CardContract.View mView;

  @NonNull
  private final SchedulerProvider mSchedulerProvider;

  @NonNull
  private CompositeDisposable mCompositeDisposable;

  private Disposable mDisposableInsert,mDisposableUpdate,mDisposableQuery,mDisposableInsertOrUpdate;

  CardPresenter(@NonNull MonthCoverRepo repo, @NonNull CardContract.View view,
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
    mDisposableQuery = mRepo.getMonthCover(year, month)
        .subscribeOn(mSchedulerProvider.io())
        .observeOn(mSchedulerProvider.ui())
        .subscribe(
            // onNext
            monthCovers -> {
            if (!monthCovers.isEmpty()){
              mView.updateMonthCover(monthCovers.get(0));
            }else {
              mView.resetMonthCover();
            }
        },
            // onError
            throwable -> {

        },
            // onComplete
            () -> {

        });

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
    mDisposableInsertOrUpdate = mRepo.getMonthCover(year, month)
        .subscribeOn(mSchedulerProvider.io())
        .observeOn(mSchedulerProvider.io())
        .subscribe(
            monthCovers -> {
              if (monthCovers.isEmpty()){
                mRepo.insertMonthCover(monthCover);
              }else {
                mRepo.updateMonthCover(monthCover);
              }
            }
        );
    mCompositeDisposable.add(mDisposableInsertOrUpdate);
  }
}
