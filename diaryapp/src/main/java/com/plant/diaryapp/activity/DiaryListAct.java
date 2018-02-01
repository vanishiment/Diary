package com.plant.diaryapp.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.plant.diaryapp.R;
import com.plant.diaryapp.app.DiaryApp;
import com.plant.diaryapp.data.datasource.DiaryDataSource;
import com.plant.diaryapp.data.local.LocalDiaryDataSource;
import com.plant.diaryapp.data.model.Diary;
import com.plant.diaryapp.data.remote.RemoteDiaryDataSource;
import com.plant.diaryapp.data.repo.DiaryRepo;
import com.plant.diaryapp.ui.StateLayout;
import com.plant.diaryapp.ui.StateRecyclerView;
import com.plant.diaryapp.utils.AppExecutors;

import java.util.ArrayList;
import java.util.List;

public class DiaryListAct extends ToolbarAct {

    public static final String YEAR = "year";
    public static final String MONTH = "month";
    private int mYear,mMonth;

    private StateRecyclerView mStateRecyclerView;
    private StateLayout mStateLayout;
    private DiaryListAdapter mDiaryListAdapter;
    private List<Diary> mDiaryList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_diary_list);
        handleIntent();
        initView();
        loadData();
    }

    private void handleIntent(){
        Intent intent = getIntent();
        if (intent != null){
            mYear = intent.getIntExtra(YEAR,-1);
            mMonth = intent.getIntExtra(MONTH,-1);
        }
    }

    private void initView(){
        setDisplayShowTitleEnabled(true);
        if (mYear != -1 && mMonth != -1){
            setDiaryListTitle(mMonth + "·" + mYear);
        }
        mStateRecyclerView = findViewById(R.id.diary_list_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mStateRecyclerView.setLayoutManager(linearLayoutManager);
        mStateLayout = findViewById(R.id.diary_list_state_layout);
        mStateRecyclerView.setStateLayout(mStateLayout);
        if (!mDiaryList.isEmpty()){
            mDiaryList.clear();
        }
        mDiaryListAdapter = new DiaryListAdapter(this,mDiaryList);
        mStateRecyclerView.setAdapter(mDiaryListAdapter);
    }

    private void setDiaryListTitle(String title){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null && !TextUtils.isEmpty(title)){
            actionBar.setTitle(title);
        }
    }

    @Override
    public boolean canBack() {
        return true;
    }

    private void loadData(){
        LocalDiaryDataSource local = LocalDiaryDataSource.getSource(DiaryApp.getDiaryDb().mDiaryDao(), AppExecutors.get());
        DiaryRepo repo = DiaryRepo.getRepo(local,new RemoteDiaryDataSource());
        repo.getMonthDiary(mYear, mMonth, new DiaryDataSource.LoadDiaryListCallback() {
            @Override
            public void onDiaryListLoaded(List<Diary> diaryList) {
                if (mDiaryListAdapter != null){
                    mDiaryListAdapter.replaceData(diaryList,false);
                }
            }

            @Override
            public void onDataNotAvailable() {
                if (mDiaryListAdapter != null){
                    mDiaryList.clear();
                    mDiaryListAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public static class DiaryListAdapter extends RecyclerView.Adapter<DiaryListAdapter.VH>{

        private Context mContext;
        private List<Diary> mDiaryList;

        DiaryListAdapter(Context context, List<Diary> diaryList) {
            mContext = context;
            mDiaryList = diaryList;
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diary_list_item,parent,false);
            return new VH(view);
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            Diary diary = mDiaryList.get(position);
            if (diary.getDay() != 0){
                holder.day.setText(String.valueOf(diary.getDay()));
            }
            if (diary.getWeek() != 0){
                holder.week.setText(String.valueOf(diary.getWeek()));
            }
//            if (!TextUtils.isEmpty(diary.getPic())){
                Glide.with(holder.background).load(R.drawable.item_bg).into(holder.background);
//            }
            int year = diary.getYear();
            int month = diary.getMonth();
            int day = diary.getDay();
            holder.mCardView.setOnClickListener(v -> {
                Intent intent = new Intent(mContext,DiaryAct.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(DiaryAct.DIARY,diary);
                intent.putExtras(bundle);
                intent.putExtra(DiaryAct.YEAR,year);
                intent.putExtra(DiaryAct.MONTH,month);
                intent.putExtra(DiaryAct.DAY,day);
                mContext.startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return mDiaryList == null ? 0 : mDiaryList.size();
        }

        void replaceData(List<Diary> list,boolean forceRefresh){
            if (forceRefresh){
                if (list == null || list.isEmpty()){
                    mDiaryList.clear();
                    notifyDataSetChanged();
                }
            }
            if (list == null || list.isEmpty()) {
                return;
            }
            mDiaryList.clear();
            mDiaryList.addAll(list);
            notifyDataSetChanged();
        }

        static class VH extends RecyclerView.ViewHolder{

            CardView mCardView;
            TextView day,week;
            ImageView background;

            VH(View itemView) {
                super(itemView);
                mCardView = itemView.findViewById(R.id.diary_list_item_cl);
                day = itemView.findViewById(R.id.diary_list_day);
                week = itemView.findViewById(R.id.diary_list_week);
                background = itemView.findViewById(R.id.diary_list_bg);
            }
        }
    }
}
