package com.plant.diary.ui.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plant.diary.R;
import com.plant.diary.ui.base.BaseFragment;

public class SettingsFragment extends BaseFragment {

  @BindView(R.id.fragment_setting_frame_layout) FrameLayout mFL;
  public SettingsFragment() {
  }

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_setting, container, false);
    ButterKnife.bind(this,root);
    SettingFragmentPreference preference = new SettingFragmentPreference();
    getChildFragmentManager().beginTransaction().add(R.id.fragment_setting_frame_layout,preference).commit();
    return root;
  }
}
