package com.plant.diary.ui.editdiary;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.plant.diary.R;
import com.plant.diary.data.model.Diary;
import com.plant.diary.support.GlideEngineOverride;
import com.plant.diary.support.permission.MPermission;
import com.plant.diary.support.permission.callback.PermissionCallback;
import com.plant.diary.ui.base.BaseFragment;
import com.plant.diary.ui.widget.EditImageLayout;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import timber.log.Timber;

import static android.app.Activity.RESULT_OK;

public class EditDiaryFragment extends BaseFragment
    implements EditImageLayout.OnEditImageLayoutClickListener,EditDiaryContract.View {

  private static final int REQUEST_CODE_CHOOSE = 1000;

  @BindView(R.id.fragment_edit_edit_image_layout) EditImageLayout mEditImg;
  @BindView(R.id.fragment_edit_date) TextView mDate;
  @BindView(R.id.fragment_edit_diary_title) EditText mTitle;
  @BindView(R.id.fragment_edit_diary_weather) TextView mWeather;
  @BindView(R.id.fragment_edit_diary_content) EditText mContent;

  private EditDiaryContract.Presenter mPresenter;

  private Diary mCurDiary = new Diary();

  public void setDate(int year,int month,int day){
    Timber.tag("edit_fra").e("date %s %s %s",year,month,day);
    mCurDiary.setYear(year);
    mCurDiary.setMonth(month);
    mCurDiary.setDay(day);
  }

  public EditDiaryFragment() {
    // Required empty public constructor
  }

  public static EditDiaryFragment newInstance() {
    return new EditDiaryFragment();
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    setHasOptionsMenu(true);
    View root = inflater.inflate(R.layout.fragment_edit_diary, container, false);
    ButterKnife.bind(this, root);
    setupViews();
    return root;
  }

  private void setupViews() {
    mEditImg.setOnEditImageLayoutClickListener(this);
    mTitle.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override public void afterTextChanged(Editable s) {
        String title = s.toString();
        if (!TextUtils.isEmpty(title)){
          mCurDiary.setTitle(title);
        }
      }
    });
    mContent.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override public void afterTextChanged(Editable s) {
        String content = s.toString();
        if (!TextUtils.isEmpty(content)){
          mCurDiary.setContent(content);
        }
      }
    });
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.menu_edit_diary, menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        showCloseEditDiaryDialog();
        return true;
      case R.id.menu_edit_done:
        showDoneDialog();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override public void onAddClick(View view) {
    MPermission.getInstance().request(this, new PermissionCallback() {
      @Override public void onGranted() {
        Matisse.from(EditDiaryFragment.this)
            .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
            .countable(true)
            .capture(true)
            .captureStrategy(new CaptureStrategy(true, "com.zhihu.matisse.sample.fileprovider"))
            .maxSelectable(1)
            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            .thumbnailScale(0.85F)
            .imageEngine(new GlideEngineOverride())
            .forResult(REQUEST_CODE_CHOOSE);
      }

      @Override public void onDenied(List<String> perms) {

      }
    }, new String[] { "android.permission.WRITE_EXTERNAL_STORAGE" });

  }

  @Override public void onDeleteClick(View view) {
    mEditImg.setImageDrawable(null);
  }

  @OnClick(R.id.fragment_edit_diary_weather) public void onClick(View v) {
    final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
    View dialogView = LayoutInflater.from(getActivity())
        .inflate(R.layout.bottom_sheet_dialog_edit_radio_button, null);

    Button cancelBtn = dialogView.findViewById(R.id.bottom_sheet_radio_weather_cancel);
    //RecyclerView weatherRv = dialogView.findViewById(R.id.bottom_sheet_weather_list);
    cancelBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (dialog.isShowing()) {
          dialog.dismiss();
        }
      }
    });
    //weatherRv.setLayoutManager(new LinearLayoutManager(getActivity()));
    RadioGroup radioGroup = dialogView.findViewById(R.id.bottom_sheet_radio_weather_list);
    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton radioButton = group.findViewById(checkedId);
        Log.e("edit", "onCheckedChanged: " + radioButton.getText());
      }
    });
    dialog.setContentView(dialogView);
    dialog.show();
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
      List<Uri> uris = Matisse.obtainResult(data);
      if (uris != null && uris.size() > 0) {
        Glide.with(EditDiaryFragment.this).load(uris.get(0)).into(new SimpleTarget<Drawable>() {
          @Override
          public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
            mEditImg.setImageDrawable(resource);
            mCurDiary.setPic(uris.get(0).toString());
          }
        });
      }
    }
  }

  @Override public void onResume() {
    super.onResume();
    mPresenter.subscribe();
    mPresenter.getDiary(mCurDiary.getYear(),mCurDiary.getMonth(),mCurDiary.getDay());
  }

  @Override public void onPause() {
    super.onPause();
    mPresenter.unSubscribe();
  }

  @Override public void setPresenter(EditDiaryContract.Presenter presenter) {
    mPresenter = presenter;
  }

  @Override public void showCloseEditDiaryDialog() {
    new MaterialDialog.Builder(getActivity()).title("请注意")
        .content("你有正在编辑的内容，确认丢弃吗？")
        .positiveText("保存")
        .negativeText("丢弃")
        .onNegative((dialog, which) -> {
          dialog.dismiss();
          getActivity().finish();
        })
        .onPositive((dialog, which) -> {
          if (mPresenter != null && mPresenter instanceof EditDiaryPresenter){
            EditDiaryPresenter presenter = (EditDiaryPresenter) mPresenter;
            if (presenter.rightDiary(mCurDiary)){
              presenter.insertOrUpdateDiary(mCurDiary);
              getActivity().finish();
            }else {
              showErrorTipDialog("必须输入内容才能保存");
            }
          }
        })
        .build()
    .show();
  }

  @Override public void showDiary(Diary diary) {
    String topImgUrl = diary.getPic();
    String title = diary.getTitle();
    //String weather = diary.getWeather();
    String content = diary.getContent();
    int year = diary.getYear();
    int month = diary.getMonth();
    int week = diary.getWeek();
    mCurDiary.setYear(year);
    mCurDiary.setMonth(month);
    mCurDiary.setWeek(week);
    mCurDiary.setDay(diary.getDay());

    if (!TextUtils.isEmpty(topImgUrl)) Glide.with(this).load(topImgUrl).into(new SimpleTarget<Drawable>() {
      @Override
      public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
        mEditImg.setImageDrawable(resource);
        mCurDiary.setPic(topImgUrl);
      }
    });

    if (!TextUtils.isEmpty(title)){
      mTitle.setText(title);
      mCurDiary.setTitle(title);
    }
    if (!TextUtils.isEmpty(content)){
      mContent.setText(content);
      mCurDiary.setContent(content);
    }
    mDate.setText(String.format(Locale.getDefault(),"%d %d/%d", week, month, year));
  }

  @Override public void showDoneDialog() {
    if (mPresenter != null && mPresenter instanceof EditDiaryPresenter){
      EditDiaryPresenter presenter = (EditDiaryPresenter) mPresenter;
      if (presenter.rightDiary(mCurDiary)){
        presenter.insertOrUpdateDiary(mCurDiary);
        getActivity().finish();
      }else {
        showErrorTipDialog("必须输入内容才能保存");
      }
    }
  }

  @Override public void showErrorTipDialog(String errorMsg) {
    //Toast.makeText(getActivity(), ""+errorMsg, Toast.LENGTH_SHORT).show();
    Snackbar.make(mTitle, ""+errorMsg, Toast.LENGTH_SHORT).show();
  }

  private static class BottomSheetRVAdapter extends RecyclerView.Adapter<BottomSheetRVAdapter.VH> {

    List<String> mWeatherList =
        Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
    OnItemClickListener onItemClickListener;
    int defaultPosition;

    BottomSheetRVAdapter(@IntRange(from = 1, to = 7) int defaultPosition,
        OnItemClickListener onItemClickListener) {
      this.defaultPosition = defaultPosition;
      this.onItemClickListener = onItemClickListener;
    }

    @Override public VH onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.bottom_sheet_dialog_layout_edit_item, parent, false);
      return new VH(view);
    }

    @Override public void onBindViewHolder(final VH holder, int position) {
      if (defaultPosition == position) {
        holder.select.setChecked(true);
      }
      holder.weather.setText(mWeatherList.get(position));
      holder.rl.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (BottomSheetRVAdapter.this.onItemClickListener != null) {
            BottomSheetRVAdapter.this.onItemClickListener.onItemClick(v);
            holder.select.setChecked(true);
          }
        }
      });
    }

    @Override public int getItemCount() {
      return mWeatherList.size();
    }

    interface OnItemClickListener {
      void onItemClick(View view);
    }

    static class VH extends RecyclerView.ViewHolder {

      RelativeLayout rl;
      TextView weather;
      CheckBox select;

      VH(View itemView) {
        super(itemView);
        rl = itemView.findViewById(R.id.bottom_sheet_dialog_edit_rl);
        weather = itemView.findViewById(R.id.bottom_sheet_dialog_edit_weather);
        select = itemView.findViewById(R.id.bottom_sheet_dialog_edit_check_box);
      }
    }
  }
}
