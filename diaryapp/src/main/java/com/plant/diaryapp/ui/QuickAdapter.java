package com.plant.diaryapp.ui;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plant.diaryapp.utils.AppExecutors;

import java.util.ArrayList;
import java.util.List;


public abstract class QuickAdapter<T> extends RecyclerView.Adapter<QuickAdapter.VH> {

    private List<T> mList;

    public QuickAdapter(List<T> list) {
        mList = list;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return VH.get(parent, getLayoutId());
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        bindViewHolder(holder, mList, position);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public abstract @LayoutRes
    int getLayoutId();

    public abstract void bindViewHolder(VH holder, List<T> list, int position);

    public abstract boolean areItemsTheSame(T oldT,T newT);
    public abstract boolean areContentsTheSame(T oldT,T newT);

    public void replaceData(List<T> list){
        if (list == null || list.isEmpty()){
            return;
        }else {
            if (mList == null){
                mList = new ArrayList<>();
            }
            if (mList.isEmpty()){
                mList.addAll(list);
                notifyItemRangeChanged(0,mList.size());
            }else {
                AppExecutors.get().diskIO().execute(() -> {
                    DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                        @Override
                        public int getOldListSize() {
                            return mList.size();
                        }

                        @Override
                        public int getNewListSize() {
                            return list.size();
                        }

                        @Override
                        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                            return QuickAdapter.this.areItemsTheSame(mList.get(oldItemPosition),list.get(newItemPosition));
                        }

                        @Override
                        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                            return QuickAdapter.this.areContentsTheSame(mList.get(oldItemPosition),list.get(newItemPosition));
                        }
                    });
                    mList = list;
                    AppExecutors.get().mainThread().execute(()->{
                        diffResult.dispatchUpdatesTo(QuickAdapter.this);
                    });
                });
            }
        }
    }

    static class VH extends RecyclerView.ViewHolder {

        private SparseArray<View> mViews;
        private View mContentView;

        VH(View itemView) {
            super(itemView);
            mContentView = itemView;
            mViews = new SparseArray<>();
        }

        static VH get(ViewGroup parent, @LayoutRes int layoutId) {
            View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
            return new VH(view);
        }

        public View getView(@IdRes int id) {
            View v = mViews.get(id);
            if (v == null) {
                v = mContentView.findViewById(id);
                mViews.put(id, v);
            }
            return v;
        }
    }
}
