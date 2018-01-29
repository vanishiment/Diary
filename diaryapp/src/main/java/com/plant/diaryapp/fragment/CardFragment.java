package com.plant.diaryapp.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.plant.diaryapp.R;
import com.plant.diaryapp.utils.Utils;

public class CardFragment extends Fragment {

    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private int mYear, mMonth;

    private ImageView mBg;
    private TextView mMonthNum, mMonthText, mPbText;
    private ProgressBar mPb;
    private ImageButton mMore;

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_card, container, false);
        initViews(view);
        setupView(mYear,mMonth);
        return view;
    }

    private void initViews(View view) {
        mBg = view.findViewById(R.id.card_front_bg_iv);
        mMonthNum = view.findViewById(R.id.card_front_month_number_tv);
        mMonthText = view.findViewById(R.id.card_front_month_text_tv);
        mPbText = view.findViewById(R.id.card_front_pb_tv);
        mPb = view.findViewById(R.id.card_front_pb);
        mMore = view.findViewById(R.id.card_front_more_ibtn);
    }

    private void setupView(int year,int month){
        mMonthNum.setText(String.valueOf(month));
        mMonthText.setText(Utils.getMonthText(month));
        mPbText.setText("0/31");
    }
}
