package com.plant.diaryapp.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.plant.diaryapp.R;


public class DialogFragExtend extends DialogFragment implements View.OnClickListener{

    private Context mContext;

    private TextView mTitle,mTip;
    private ProgressBar mProgressBar;
    private Button mDone,mCancel;

    private OnCancelClickListener mCancelListener;
    private OnDoneClickListener mDoneListener;



    public interface OnCancelClickListener {

        void onCancel();

    }

    public interface OnDoneClickListener {

        void onDone();

    }

    public void setCancelListener(OnCancelClickListener cancelListener) {
        mCancelListener = cancelListener;
    }

    public void setDoneListener(OnDoneClickListener doneListener) {
        mDoneListener = doneListener;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mDoneListener != null){
            mDoneListener = null;
        }
        if (mCancelListener != null){
            mCancelListener = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Window win = getDialog().getWindow();
        // 一定要设置Background，如果不设置，window属性设置无效
        if (win != null)
        win.setBackgroundDrawable( new ColorDrawable(getResources().getColor(R.color.blue_window_background)));

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );

        WindowManager.LayoutParams params = win.getAttributes();
        params.gravity = Gravity.CENTER;
        // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
        params.width =  ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        win.setAttributes(params);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_file_export,container,false);
        mTitle = view.findViewById(R.id.file_export_title);
        mTip = view.findViewById(R.id.file_export_success);
        mProgressBar = view.findViewById(R.id.file_export_pb);
        mDone = view.findViewById(R.id.file_export_done);
        mCancel = view.findViewById(R.id.file_export_cancel);
        mDone.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.file_export_done){
            if (mDoneListener != null){
                mDoneListener.onDone();
            }
            dismiss();
        }else if (v.getId() == R.id.file_export_cancel){
            if (mCancelListener != null){
                mCancelListener.onCancel();
            }
            dismiss();
        }
    }

    public void showExportSuccess(){
        mProgressBar.setVisibility(View.GONE);
        mTip.setVisibility(View.VISIBLE);
    }
}
