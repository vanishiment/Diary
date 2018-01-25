package com.plant.diaryapp.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;


public class StateRecyclerView extends RecyclerView {

    private StateLayout mStateLayout;

    private AdapterDataObserver mObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            if (mStateLayout != null) {
                Adapter adapter = getAdapter();
                if (adapter.getItemCount() == 0) {
                    mStateLayout.showEmpty(null);
                    StateRecyclerView.this.setVisibility(GONE);
                } else {
                    mStateLayout.hideEveryThing();
                    StateRecyclerView.this.setVisibility(VISIBLE);
                }
            }

        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            onChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            onChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            onChanged();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            onChanged();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            onChanged();
        }
    };

    public StateRecyclerView(Context context) {
        super(context);
    }

    public StateRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StateRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setStateLayout(StateLayout stateLayout) {
        mStateLayout = stateLayout;
    }

    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        adapter.registerAdapterDataObserver(mObserver);
        mObserver.onChanged();
    }
}
