package com.plant.diary.ui.maindiarys.card;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;
import com.plant.diary.data.repo.MonthCoverAndDiaryRepo;
import com.plant.diary.ui.compat.SchedulerProvider;
import com.plant.diary.ui.widget.viewpagercard.CardAdapter;
import java.util.ArrayList;
import java.util.List;

public class CardFragmentPagerAdapter extends FragmentStatePagerAdapter implements CardAdapter {

  private List<CardFragment> mCardFragmentList;
  private float mBaseElevation;

  public CardFragmentPagerAdapter(Context context, FragmentManager fm, float baseElevation) {
    super(fm);
    mCardFragmentList = new ArrayList<>();
    mBaseElevation = baseElevation;
    for (int i = 1; i <= 12; i++) {
      CardFragment cardFragment = CardFragment.newInstance(i);
      new CardPresenter(MonthCoverAndDiaryRepo.get(context), cardFragment,
          SchedulerProvider.get());
      mCardFragmentList.add(cardFragment);
    }
  }

  @Override public Fragment getItem(int position) {
    return mCardFragmentList.get(position);
  }

  @Override public int getCount() {
    return mCardFragmentList.size();
  }

  @Override public float getBaseElevation() {
    return mBaseElevation;
  }

  @Override public CardView getCardViewAt(int position) {
    return mCardFragmentList.get(position).mCardView;
  }

  @NonNull @Override public Object instantiateItem(ViewGroup container, int position) {
    Object fragment = super.instantiateItem(container, position);
    mCardFragmentList.set(position, (CardFragment) fragment);
    return fragment;
  }

}
