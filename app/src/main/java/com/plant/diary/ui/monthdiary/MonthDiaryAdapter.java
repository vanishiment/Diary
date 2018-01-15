package com.plant.diary.ui.monthdiary;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.plant.diary.R;
import com.plant.diary.ui.detaildiary.DetailDiaryActivity;
import java.util.List;

public class MonthDiaryAdapter extends RecyclerView.Adapter<MonthDiaryAdapter.VH> {

  private List<String> mDays;
  private Context mContext;

  MonthDiaryAdapter(Context context,List<String> days) {
    this.mContext = context;
    this.mDays = days;
  }

  @Override public VH onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.month_diary_item,parent,false);
    return new VH(view);
  }

  @Override public void onBindViewHolder(VH holder, int position) {
    holder.mCardView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        mContext.startActivity(new Intent(mContext, DetailDiaryActivity.class));
      }
    });
  }

  @Override public int getItemCount() {
    return mDays == null ? 0 : mDays.size();
  }

  static class VH extends RecyclerView.ViewHolder{

    CardView mCardView;

    VH(View itemView) {
      super(itemView);
      mCardView = itemView.findViewById(R.id.month_diary_item_cl);
    }
  }
}
