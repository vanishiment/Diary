package com.plant.diaryapp.fragment;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
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

import com.bumptech.glide.Glide;
import com.plant.diaryapp.R;
import com.plant.diaryapp.activity.DiaryListAct;
import com.plant.diaryapp.app.DiaryApp;
import com.plant.diaryapp.data.datasource.DiaryBookDataSource;
import com.plant.diaryapp.data.local.LocalDiaryBookDataSource;
import com.plant.diaryapp.data.model.DiaryBook;
import com.plant.diaryapp.data.remote.RemoteDiaryDataSource;
import com.plant.diaryapp.data.repo.DiaryBookRepo;
import com.plant.diaryapp.support.GlideEngineOverride;
import com.plant.diaryapp.support.permission.MPermission;
import com.plant.diaryapp.support.permission.callback.PermissionCallback;
import com.plant.diaryapp.utils.AppExecutors;
import com.plant.diaryapp.utils.DateUtil;
import com.plant.diaryapp.utils.Utils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class CardFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "CardFragment";

    private static final int REQUEST_CODE_CHOOSE = 1000;

    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private int mYear, mMonth;

    private CardView mCardView;
    private ImageView mBg;
    private TextView mMonthNum, mMonthText, mPbText;
    private ProgressBar mPb;
    private ImageButton mMore;
    private BottomSheetDialog mDialog;

    private DiaryBook mBook = new DiaryBook();

    public CardFragment() {
    }

    public static CardFragment newIns(int year, int month) {
        CardFragment cardFragment = new CardFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(YEAR, year);
        bundle.putInt(MONTH, month);
        cardFragment.setArguments(bundle);
        return cardFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mYear = bundle.getInt(YEAR, -1);
            mMonth = bundle.getInt(MONTH, -1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_card, container, false);
        initViews(view);
        Log.e(TAG, "year: " + mYear + " month: " + mMonth);
        setupView(mYear, mMonth);
        loadData(mYear, mMonth);
        return view;
    }

    private void initViews(View view) {
        mCardView = view.findViewById(R.id.card_front_card_view);
        mCardView.setOnClickListener(this);
        mBg = view.findViewById(R.id.card_front_bg_iv);
        mMonthNum = view.findViewById(R.id.card_front_month_number_tv);
        mMonthText = view.findViewById(R.id.card_front_month_text_tv);
        mPbText = view.findViewById(R.id.card_front_pb_tv);
        mPb = view.findViewById(R.id.card_front_pb);
        mMore = view.findViewById(R.id.card_front_more_ibtn);
        mMore.setOnClickListener(this);
    }

    private void setupView(int year, int month) {
        mMonthNum.setText(String.valueOf(month));
        mMonthText.setText(Utils.getMonthText(month));
        mPbText.setText(String.format(Locale.getDefault(), "0/%d", DateUtil.getMonthDays(year, month-1, 1)));
        mPb.setProgress(0);

        mBook.setYear(mYear);
        mBook.setMonth(mMonth);
    }

    private void loadData(int year, int month) {
        LocalDiaryBookDataSource local = LocalDiaryBookDataSource.getSource(DiaryApp.getDiaryDb().mDiaryBookDao(), AppExecutors.get());
        DiaryBookRepo.getRepo(local, new RemoteDiaryDataSource()).getMonthDiaryBook(year, month, new DiaryBookDataSource.GetDiaryBookCallback() {
            @Override
            public void onDiaryLoaded(DiaryBook diaryBook) {
                setupViewWithDiaryBook(diaryBook);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void updateOrInsertDiaryBook(DiaryBook diaryBook) {
        LocalDiaryBookDataSource local = LocalDiaryBookDataSource.getSource(DiaryApp.getDiaryDb().mDiaryBookDao(), AppExecutors.get());
        DiaryBookRepo repo = DiaryBookRepo.getRepo(local, new RemoteDiaryDataSource());
        repo.getMonthDiaryBook(diaryBook.getYear(), diaryBook.getMonth(), new DiaryBookDataSource.GetDiaryBookCallback() {
            @Override
            public void onDiaryLoaded(DiaryBook diaryBook1) {
                repo.updateDiaryBook(diaryBook);
            }

            @Override
            public void onDataNotAvailable() {
                repo.insertDiaryBook(diaryBook);
            }
        });
    }

    private void setupViewWithDiaryBook(DiaryBook diaryBook) {
        if (diaryBook == null) return;
        if (mYear != diaryBook.getYear()) return;
        if (mMonth != diaryBook.getMonth()) return;

        mMonthNum.setText(String.valueOf(mYear));
        mMonthText.setText(Utils.getMonthText(mMonth));

        mBook.setYear(mYear);
        mBook.setMonth(mMonth);

        boolean getColor = !TextUtils.isEmpty(diaryBook.getColor());
        boolean getPicUrl = !TextUtils.isEmpty(diaryBook.getCoverPicUrl());
        if (getPicUrl) {
            Glide.with(CardFragment.this).load(diaryBook.getCoverPicUrl()).into(mBg);

            mBook.setColor("");
            mBook.setCoverPicUrl(diaryBook.getCoverPicUrl());
        } else if (getColor) {
            mBg.setImageDrawable(new ColorDrawable(Color.parseColor(diaryBook.getColor())));

            mBook.setColor(diaryBook.getColor());
            mBook.setCoverPicUrl("");
        }
        int diaryCount = diaryBook.getDiaryCount();
        if (diaryCount != 0) {

            mBook.setDiaryCount(diaryCount);

            int days = DateUtil.getMonthDays(mYear, mMonth, 1);
            mPb.setMax(days);
            mPbText.setText(String.format(Locale.getDefault(), "%d/%d", diaryCount, days));
            mPb.setProgress(diaryCount);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.card_front_more_ibtn) {
            showColorOrImgPicker();
        }else if (v.getId() == R.id.card_front_card_view){
            launchAct(DiaryListAct.class);
        }
    }

    public void launchAct(Class<? extends Activity> clz){
        Intent intent = new Intent(getActivity(),clz);
        intent.putExtra(DiaryListAct.YEAR,mYear);
        intent.putExtra(DiaryListAct.MONTH,mMonth);
        getActivity().startActivity(intent);
    }

    private void showColorOrImgPicker() {
        mDialog = new BottomSheetDialog(getActivity());
        View dialogView =
                getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_layout_card, null);
        Button cancelBtn = dialogView.findViewById(R.id.bottom_sheet_cancel);
        Button photosBtn = dialogView.findViewById(R.id.bottom_sheet_photos);
        cancelBtn.setOnClickListener(v -> {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
        });
        photosBtn.setOnClickListener(v -> showImagePicker());
        RecyclerView recyclerView = dialogView.findViewById(R.id.bottom_sheet_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 7));
        String[] colors = getResources().getStringArray(R.array.color_array);
        recyclerView.setAdapter(
                new BottomSheetRVAdapter(colors, (view, color) -> {
                    mBg.setImageDrawable(new ColorDrawable(Color.parseColor(color)));
                    mBook.setColor(String.valueOf(color));
                    mBook.setCoverPicUrl("");
                    updateOrInsertDiaryBook(mBook);
                    mDialog.dismiss();
                }));
        mDialog.setContentView(dialogView);
        mDialog.show();
    }

    private void showImagePicker() {
        MPermission.getInstance().request(this, new PermissionCallback() {

            @Override
            public void onGranted() {
                Matisse.from(CardFragment.this)
                        .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
                        .countable(true)
                        .capture(true)
                        .captureStrategy(new CaptureStrategy(true, "com.plant.diaryapp.fileprovider"))
                        .maxSelectable(1)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                        .thumbnailScale(0.85F)
                        .imageEngine(new GlideEngineOverride())
                        .forResult(REQUEST_CODE_CHOOSE);
            }

            @Override
            public void onDenied(List<String> perms) {

            }
        }, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"});
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<Uri> uris = Matisse.obtainResult(data);
            if (uris != null && uris.size() > 0) {
                Uri uri = uris.get(0);
                String url = uri.toString();
                Glide.with(CardFragment.this).load(uri).into(mBg);
                mBook.setCoverPicUrl(url);
                mBook.setColor("");
                updateOrInsertDiaryBook(mBook);
                if (mDialog != null && mDialog.isShowing()) mDialog.dismiss();
            }
        }
    }

    private static class BottomSheetRVAdapter extends RecyclerView.Adapter<BottomSheetRVAdapter.VH> {

        String[] mColorList;
        OnItemClickListener onItemClickListener;

        BottomSheetRVAdapter(String[] colors, OnItemClickListener onItemClickListener) {
            mColorList = colors;
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.bottom_sheet_dialog_color_item_card, parent, false);
            return new VH(view);
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            final String color = mColorList[position];
            holder.ivBtn.setImageDrawable(new ColorDrawable(Color.parseColor(color)));
            holder.fl.setOnClickListener(v -> {
                if (BottomSheetRVAdapter.this.onItemClickListener != null) {
                    BottomSheetRVAdapter.this.onItemClickListener.onItemClick(v, color);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mColorList.length;
        }

        interface OnItemClickListener {
            void onItemClick(View view, String color);
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
}
