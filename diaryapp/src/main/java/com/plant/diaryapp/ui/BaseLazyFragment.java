package com.plant.diaryapp.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseLazyFragment extends Fragment {

    Context mContext;
    private OnUpdateListener mListener;

    private boolean mIsUIPrepared = false;
    private boolean mIsDataInited = false;

    public void updateActivityTitle(String title) {
        if (mListener != null) {
            mListener.onUpdate(title);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUpdateListener) {
            mListener = (OnUpdateListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnUpdateListener");
        }
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(getLayoutId(), container, false);
        mIsUIPrepared = true;
        initView(mRootView);
        lazyLoad();
        return mRootView;
    }

    @Override public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            lazyLoad();
        }
    }

    private void lazyLoad() {
        if (getUserVisibleHint() && mIsUIPrepared && !mIsDataInited){
            onFragmentVisible();
            loadData();
        }
    }

    public abstract int getLayoutId();

    public abstract void initView(View view);

    public abstract void onFragmentVisible();

    /**
     * 数据加载完成调用 loadDataFinished
     */
    public abstract void loadData();

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mContext = null;
    }

    public interface OnUpdateListener {
        void onUpdate(String title);
    }
}
