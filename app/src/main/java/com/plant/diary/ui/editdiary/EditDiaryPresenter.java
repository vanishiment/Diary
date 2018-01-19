package com.plant.diary.ui.editdiary;

import android.text.TextUtils;
import com.plant.diary.data.model.Diary;
import com.plant.diary.data.model.MonthCover;
import com.plant.diary.data.repo.DiaryRepo;
import com.plant.diary.data.repo.MonthCoverRepo;
import com.plant.diary.ui.compat.SchedulerProvider;
import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class EditDiaryPresenter implements EditDiaryContract.Presenter {

  private final DiaryRepo mRepo;
  private final MonthCoverRepo mCoverRepo;
  private final EditDiaryContract.View mView;
  private final SchedulerProvider mProvider;

  private CompositeDisposable mCompositeDisposable;
  private Disposable mQuery,mInsert,mUpdate,mDelete,mDisposable;

  public EditDiaryPresenter(DiaryRepo repo,MonthCoverRepo coverRepo, EditDiaryContract.View view,
      SchedulerProvider provider) {
    mRepo = repo;
    mCoverRepo = coverRepo;
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
    mInsert = Flowable.just("1")
        .subscribeOn(mProvider.io())
        .observeOn(mProvider.io())
        .subscribe(s -> {
          mRepo.insertDiary(diary);
        });
    mCompositeDisposable.add(mInsert);
  }

  @Override public void updateDiary(Diary diary) {
    mUpdate = Flowable.just("1")
        .subscribeOn(mProvider.io())
        .observeOn(mProvider.io())
        .subscribe(s -> {
          mRepo.updateDiary(diary);
        });
    mCompositeDisposable.add(mUpdate);
  }

  @Override public void deleteDiary(Diary diary) {
    mDelete = Flowable.just("1")
        .subscribeOn(mProvider.io())
        .observeOn(mProvider.io())
        .subscribe(s -> {
          mRepo.deleteDiary(diary);
        });
    mCompositeDisposable.add(mDelete);
  }

  public void insertOrUpdateDiary(Diary diary){
    if (!rightDiary(diary)){
      return;
    }
    mDisposable = mRepo.getDayDiary(diary.getYear(),diary.getMonth(),diary.getDay())
        .subscribeOn(mProvider.io())
        .observeOn(mProvider.io())
        .subscribe(diaryList -> {
          if (diaryList.isEmpty()){
            mRepo.insertDiary(diary);
          }else {
            mRepo.updateDiary(diary);
          }
        });
    mCompositeDisposable.add(mDisposable);
  }

  public boolean rightDiary(Diary diary) {
    return diary != null && (!TextUtils.isEmpty(diary.getTitle()) || !TextUtils.isEmpty(
        diary.getContent()) || !TextUtils.isEmpty(diary.getPic()));
  }

}
