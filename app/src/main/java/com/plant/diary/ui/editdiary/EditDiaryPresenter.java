package com.plant.diary.ui.editdiary;

import com.plant.diary.data.model.Diary;
import com.plant.diary.data.repo.DiaryRepo;
import com.plant.diary.ui.compat.SchedulerProvider;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class EditDiaryPresenter implements EditDiaryContract.Presenter {

  private final DiaryRepo mRepo;
  private final EditDiaryContract.View mView;
  private final SchedulerProvider mProvider;

  private CompositeDisposable mCompositeDisposable;
  private Disposable mQuery,mInsert,mUpdate,mDelete;

  public EditDiaryPresenter(DiaryRepo repo, EditDiaryContract.View view,
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
    mQuery = mRepo.getDayDiary(year, month, day)
        .subscribeOn(mProvider.io())
        .observeOn(mProvider.ui())
        .subscribe(
            diaryList -> {
              if (!diaryList.isEmpty()){
                mView.showDiary(diaryList.get(0));
              }
            }
        );
    mCompositeDisposable.add(mQuery);
  }

  @Override public void insertDiary(Diary diary) {
    Flowable.create((FlowableOnSubscribe<String>) emitter -> {
      mRepo.insertDiary(diary);
      emitter.onComplete();
    }, BackpressureStrategy.LATEST)
        .subscribeOn(mProvider.io())
        .observeOn(mProvider.ui())
        .doOnComplete(() -> {

        });
    mCompositeDisposable.add(mInsert);
  }

  @Override public void updateDiary(Diary diary) {
    Flowable.create((FlowableOnSubscribe<String>) emitter -> {
      mRepo.updateDiary(diary);
      emitter.onComplete();
    }, BackpressureStrategy.LATEST)
        .subscribeOn(mProvider.io())
        .observeOn(mProvider.ui())
        .doOnComplete(() -> {

        });
    mCompositeDisposable.add(mUpdate);
  }

  @Override public void deleteDiary(Diary diary) {
    Flowable.create((FlowableOnSubscribe<String>) emitter -> {
      mRepo.deleteDiary(diary);
      emitter.onComplete();
    }, BackpressureStrategy.LATEST)
        .subscribeOn(mProvider.io())
        .observeOn(mProvider.ui())
        .doOnComplete(() -> {

        });
    mCompositeDisposable.add(mDelete);
  }

  public void insertOrUpdateDiary(Diary diary){

  }

  private boolean rightDiary(Diary diary){
    return false;
  }

}
