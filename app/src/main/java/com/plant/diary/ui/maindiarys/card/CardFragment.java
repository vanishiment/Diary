package com.plant.diary.ui.maindiarys.card;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.plant.diary.R;
import com.plant.diary.data.model.MonthCover;
import com.plant.diary.support.GlideEngineOverride;
import com.plant.diary.support.permission.MPermission;
import com.plant.diary.support.permission.callback.PermissionCallback;
import com.plant.diary.ui.base.BaseFragment;
import com.plant.diary.ui.constants.Constant;
import com.plant.diary.ui.monthdiary.MonthDiaryActivity;
import com.plant.diary.utils.DateUtil;
import com.plant.diary.utils.Util;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class CardFragment extends BaseFragment implements CardContract.View{

  private static final String MONTH = "month";

  private static final int REQUEST_CODE_CHOOSE = 1000;

  private boolean mIsDestroy = true;

  private int mYear = -1;
  private int mMonth;

  @BindView(R.id.card_front_card_view) CardView mCardView;
  @BindView(R.id.card_front_month_text_tv) TextView mMonthNum;
  @BindView(R.id.card_front_month_number_tv) TextView mMonthText;
  @BindView(R.id.card_front_bg_iv) ImageView mBackground;
  @BindView(R.id.card_front_pb_tv) TextView mPbText;
  @BindView(R.id.card_front_pb) ProgressBar mPb;
  @BindView(R.id.card_front_more_ibtn) ImageView mMore;

  BottomSheetDialog dialog;

  private CardContract.Presenter mPresenter;

  public CardFragment() {
    // Required empty public constructor
  }

  public static CardFragment newInstance(int month) {
    CardFragment fragment = new CardFragment();
    Bundle args = new Bundle();
    args.putInt(MONTH, month);
    fragment.setArguments(args);
    return fragment;
  }

  public void setYear(int year) {
    mYear = year;
    mPresenter.queryMonthCover(mYear,mMonth);
  }

  @Override public void setPresenter(CardContract.Presenter presenter) {
    mPresenter = presenter;
  }

  public void updateMonthCover(MonthCover monthCover){
    if (mIsDestroy) return;
    int year = monthCover.getYear();
    int month = monthCover.getMonth();
    String color = monthCover.getColor();
    String pic = monthCover.getCoverPicUrl();
    int diaryCount = monthCover.getDiaryCount();
    Util.checkYear(year);
    Util.checkMonth(month);

    mYear = year;
    if (month != mMonth) return;

    if (!TextUtils.isEmpty(color) && !color.equals("0")) setCardBackground(false,color);
    if (!TextUtils.isEmpty(pic)) setCardBackground(true,pic);

    int monthOfDays = DateUtil.getMonthDays(year,month-1,1);
    String diaryProgress = diaryCount + "/" + monthOfDays;
    mPb.setMax(monthOfDays);
    setMonthProgress(diaryCount);
    setMonthProgressTv(diaryProgress);

  }

  @Override public void resetMonthCover() {
    if (!mIsDestroy){
      mMonthText.setText(String.valueOf(mMonth));
      mMonthNum.setText(getMonthText(mMonth));
      mBackground.setImageDrawable(
          getActivity().getResources().getDrawable(R.drawable.month_item_background));
      mPbText.setText(String.format(Locale.getDefault(),"0/%d", DateUtil.getMonthDays(mYear, mMonth-1, 1)));
      mPb.setProgress(0);
    }

  }

  @Override public void setMonthTv(String month) {
    if (TextUtils.isEmpty(month)) return;
    mMonthText.setText(month);
    mMonthNum.setText(getMonthText(Integer.parseInt(month)));
  }

  @Override public void setCardBackground(boolean pic, String background) {
    if (pic && !TextUtils.isEmpty(background)){
      Uri uri = Uri.parse(background);
      Glide.with(this).load(uri).into(mBackground);
    }else if (!pic && !TextUtils.isEmpty(background)){
      mBackground.setImageDrawable(new ColorDrawable(Integer.parseInt(background)));
    }
  }

  @Override public void setMonthProgress(int progress) {
    mPb.setProgress(progress);
  }

  @Override public void setMonthProgressTv(String progressText) {
    if (TextUtils.isEmpty(progressText)) return;
    mPbText.setText(progressText);
  }

  @Override public void showMoreBottomSheetDialog() {
     dialog = new BottomSheetDialog(getActivity());
    View dialogView =
        getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_layout_card, null);
    Button cancelBtn = dialogView.findViewById(R.id.bottom_sheet_cancel);
    Button photosBtn = dialogView.findViewById(R.id.bottom_sheet_photos);
    cancelBtn.setOnClickListener(v -> {
      if (dialog.isShowing()) {
        dialog.dismiss();
      }
    });
    photosBtn.setOnClickListener(v -> pickPic());
    RecyclerView recyclerView = dialogView.findViewById(R.id.bottom_sheet_recycler_view);
    recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 7));
    recyclerView.setAdapter(
        new BottomSheetRVAdapter((view, color) -> {
          Log.e("----", "onItemClick: ");
          mBackground.setImageDrawable(new ColorDrawable(color));
          mPresenter.insertOrUpdateMonthCover(mYear,mMonth,new MonthCover(mYear,mMonth,String.valueOf(color),null,0));
          dialog.dismiss();
        }));
    dialog.setContentView(dialogView);
    dialog.show();
  }

  @Override public void pickColor(String color) {

  }

  @Override public void pickPic() {
    MPermission.getInstance().request(this, new PermissionCallback() {
      @Override public void onGranted() {
        Matisse.from(CardFragment.this)
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
    },new String[]{ "android.permission.WRITE_EXTERNAL_STORAGE"});

  }

  @Override public void onPickPicResult() {

  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mMonth = getArguments().getInt(MONTH);
    }
  }

  @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    if (mYear == -1){
      mYear = DateUtil.getCurYear();
    }
    View root = inflater.inflate(R.layout.fragment_card_front, container, false);
    ButterKnife.bind(this, root);
    mMonthText.setText(String.valueOf(mMonth));
    mMonthNum.setText(getMonthText(mMonth));
    mBackground.setImageDrawable(
        getActivity().getResources().getDrawable(R.drawable.month_item_background));
    mPbText.setText(String.format(Locale.getDefault(),"0/%d", DateUtil.getMonthDays(mYear, mMonth-1, 1)));
    mPb.setProgress(0);
    return root;
  }

  @Override public void onResume() {
    super.onResume();
    mPresenter.subscribe();
    mPresenter.queryMonthCover(mYear,mMonth);
    mIsDestroy = false;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    mPresenter.unSubscribe();
    mIsDestroy = true;
  }

  @OnClick(R.id.card_front_card_view) public void onCardViewClick(View view) {
    Intent intent = new Intent(getActivity(), MonthDiaryActivity.class);
    intent.putExtra(Constant.MONTH_COVER_YEAR,mYear);
    intent.putExtra(Constant.MONTH_COVER_MONTH,mMonth);
    getActivity().startActivity(intent);
  }

  @OnClick(R.id.card_front_more_ibtn) public void onClick(View view) {
    showMoreBottomSheetDialog();
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
      List<Uri> uris = Matisse.obtainResult(data);
      if (uris != null && uris.size() > 0) {
        Uri uri = uris.get(0);
        String url = uri.toString();
        Glide.with(CardFragment.this).load(uri).into(mBackground);
        mPresenter.insertOrUpdateMonthCover(mYear,mMonth,new MonthCover(mYear,mMonth,null,url,0));
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
      }
    }
  }

  private static class BottomSheetRVAdapter extends RecyclerView.Adapter<BottomSheetRVAdapter.VH> {

    List<String> mColorList = new ArrayList<>();
    OnItemClickListener onItemClickListener;

    BottomSheetRVAdapter(OnItemClickListener onItemClickListener) {
      initData();
      this.onItemClickListener = onItemClickListener;
    }

    @Override public VH onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.bottom_sheet_dialog_color_item_card, parent, false);
      return new VH(view);
    }

    @Override public void onBindViewHolder(VH holder, int position) {
      final int color = Integer.parseInt(mColorList.get(position));
      //holder.ivBtn.setImageDrawable(new ColorDrawable(color));
      holder.fl.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (BottomSheetRVAdapter.this.onItemClickListener != null) {
            BottomSheetRVAdapter.this.onItemClickListener.onItemClick(v, color);
          }
        }
      });
    }

    @Override public int getItemCount() {
      return mColorList.size();
    }

    private void initData() {
      for (int i = 0; i < 20; i++) {
        mColorList.add(String.valueOf(Color.BLUE));
      }
    }

    interface OnItemClickListener {
      void onItemClick(View view, int color);
    }

    static class VH extends RecyclerView.ViewHolder {

      FrameLayout fl;
      ImageButton ivBtn;

      VH(View itemView) {
        super(itemView);
        fl = itemView.findViewById(R.id.bottom_sheet_dialog_color_item_fl);
        ivBtn = itemView.findViewById(R.id.bottom_sheet_dialog_color_item_iv_btn);
      }
    }
  }

  private String getMonthText(int month) {
    switch (month) {
      case 1:
        return "JAN";
      case 2:
        return "FEB";
      case 3:
        return "MAR";
      case 4:
        return "APR";
      case 5:
        return "MAY";
      case 6:
        return "JUNE";
      case 7:
        return "JULY";
      case 8:
        return "AUG";
      case 9:
        return "SEPT";
      case 10:
        return "OCT";
      case 11:
        return "NOV";
      case 12:
        return "DEC";
      default:
        return "";
    }
  }
}
