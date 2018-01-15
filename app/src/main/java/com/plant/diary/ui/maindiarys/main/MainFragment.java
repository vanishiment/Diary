package com.plant.diary.ui.maindiarys.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListAdapter;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListItem;
import com.plant.diary.R;
import com.plant.diary.data.model.MonthCover;
import com.plant.diary.ui.base.BaseFragment;
import com.plant.diary.ui.maindiarys.card.CardFragment;
import com.plant.diary.ui.maindiarys.card.CardFragmentPagerAdapter;
import java.util.List;

import static com.plant.diary.utils.Util.dpToPixels;

public class MainFragment extends BaseFragment implements MainContract.View{

  MainContract.Presenter mPresenter;
  private CardFragmentPagerAdapter mAdapter;

  public MainFragment() {
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    setHasOptionsMenu(true);
    View root = inflater.inflate(R.layout.fragment_main, container, false);
    setupViews(root);
    return root;
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.menu_main_fragment,menu);
    //super.onCreateOptionsMenu(menu, inflater);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_filter:
        showYearFilter(item.getActionView());
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void showYearFilter(View view){
    //PopupMenu popupMenu = new PopupMenu(getContext(),view);
    //popupMenu.getMenuInflater().inflate(R.menu.menu_pop_fragment_main,popupMenu.getMenu());
    //popupMenu.setOnMenuItemClickListener(item -> {
    //  switch (item.getItemId()){
    //    case R.id.pop_menu_2017:
    //      setYear(2017);
    //      popupMenu.dismiss();
    //      break;
    //    case R.id.pop_menu_2018:
    //      setYear(2018);
    //      popupMenu.dismiss();
    //      break;
    //    case R.id.pop_menu_2019:
    //      setYear(2019);
    //      popupMenu.dismiss();
    //      break;
    //  }
    //  return false;
    //});
    //popupMenu.show();
  }

  @Override public void onResume() {
    super.onResume();
    mPresenter.subscribe();
  }

  @Override public void onPause() {
    super.onPause();
    mPresenter.unSubscribe();
  }

  @Override public void setPresenter(MainContract.Presenter presenter) {
    mPresenter = presenter;
  }

  @Override public void setupViews(View root) {
    ViewPager vp = root.findViewById(R.id.fragment_main_vp);
    float e = dpToPixels(2, getActivity());
    mAdapter = new CardFragmentPagerAdapter(getActivity(),getChildFragmentManager(), e);
    vp.setAdapter(mAdapter);
    vp.setOffscreenPageLimit(3);
  }

  @Override public void setYear(int year) {
    mPresenter.queryMonthCovers(year);
  }

  @Override public void showCards(List<MonthCover> monthCovers) {
    if (monthCovers == null || monthCovers.isEmpty()) return;
    for (MonthCover monthCover : monthCovers) {
      int month = monthCover.getMonth();
      if (month <= 0 || month > 13) continue;
      Fragment fragment = mAdapter.getItem(month - 1);
      if (fragment instanceof CardFragment){
        CardFragment cardFragment = (CardFragment) fragment;
        cardFragment.updateMonthCover(monthCover);
      }
    }
  }

  @Override public void setLoadingIndicator(boolean show) {
    // no need
  }

  @Override public void showLoadingCardsError() {
    // no need
  }

  @Override public void showNoCards() {
    // no need
  }
}
