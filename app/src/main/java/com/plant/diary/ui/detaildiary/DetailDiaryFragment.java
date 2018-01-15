package com.plant.diary.ui.detaildiary;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plant.diary.R;
import com.plant.diary.ui.base.BaseFragment;

public class DetailDiaryFragment extends BaseFragment {

  private static final String DIARY_ID = "diary_id";

  private String mDiaryId;

  @BindView(R.id.fragment_detail_diary_iv) ImageView mIv;
  @BindView(R.id.fragment_detail_diary_title) TextView mTitle;
  @BindView(R.id.fragment_detail_diary_weather) TextView mWeather;
  @BindView(R.id.fragment_detail_diary_content) TextView mContent;

  public DetailDiaryFragment() {
    // Required empty public constructor
  }

  public static DetailDiaryFragment newInstance(String param1) {
    DetailDiaryFragment fragment = new DetailDiaryFragment();
    Bundle args = new Bundle();
    args.putString(DIARY_ID, param1);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mDiaryId = getArguments().getString(DIARY_ID);
    }
  }

  @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_detail_diary, container, false);
    ButterKnife.bind(this, root);

    return root;
  }
}
