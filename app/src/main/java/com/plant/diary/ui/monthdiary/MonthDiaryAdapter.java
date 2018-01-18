package com.plant.diary.ui.monthdiary;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.plant.diary.R;
import com.plant.diary.data.model.Diary;
import com.plant.diary.ui.constants.Constant;
import com.plant.diary.ui.detaildiary.DetailDiaryActivity;
import java.util.List;

public class MonthDiaryAdapter extends RecyclerView.Adapter<MonthDiaryAdapter.VH> {

  private List<Diary> mDays;
  private Context mContext;

  MonthDiaryAdapter(Context context,List<Diary> days) {
    this.mContext = context;
    this.mDays = days;
  }

  @Override public VH onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.month_diary_item,parent,false);
    return new VH(view);
  }

  @Override public void onBindViewHolder(VH holder, int position) {
    Diary diary = mDays.get(position);
    holder.day.setText(String.valueOf(diary.getDay()));
    //int week = diary.getWeek();
    holder.week.setText("");
    if (!TextUtils.isEmpty(diary.getPic()))
    Glide.with(holder.background.getContext()).load(diary.getPic()).into(holder.background);
    holder.mCardView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent = new Intent(mContext, DetailDiaryActivity.class);
        intent.putExtra(Constant.MONTH_COVER_YEAR,diary.getYear());
        intent.putExtra(Constant.MONTH_COVER_MONTH,diary.getMonth());
        intent.putExtra(Constant.MONTH_COVER_DAY,diary.getDay());
        mContext.startActivity(intent);
      }
    });
  }

  @Override public int getItemCount() {
    return mDays == null ? 0 : mDays.size();
  }

  void replaceData(List<Diary> diaryList){
    mDays.clear();
    mDays.addAll(diaryList);
    notifyDataSetChanged();
  }

  static class VH extends RecyclerView.ViewHolder{

    CardView mCardView;
    TextView day,week;
    ImageView background;

    VH(View itemView) {
      super(itemView);
      mCardView = itemView.findViewById(R.id.month_diary_item_cl);
      day = itemView.findViewById(R.id.month_diary_day);
      week = itemView.findViewById(R.id.month_diary_week);
      background = itemView.findViewById(R.id.month_diary_bg);
    }
  }
}
