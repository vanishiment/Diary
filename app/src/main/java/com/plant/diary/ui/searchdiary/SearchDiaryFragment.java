package com.plant.diary.ui.searchdiary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.plant.diary.R;
import com.plant.diary.ui.base.BaseFragment;

public class SearchDiaryFragment extends BaseFragment {

  public SearchDiaryFragment() {
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_search_diary, container, false);
    ButterKnife.bind(this, root);
    return root;
  }
}
