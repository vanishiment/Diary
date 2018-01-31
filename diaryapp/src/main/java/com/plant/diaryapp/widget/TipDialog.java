package com.plant.diaryapp.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.plant.diaryapp.R;


public class TipDialog extends DialogFragment {


    private OnButtonClickListener mListener;

    public interface OnButtonClickListener {

        void onDone();

        void onCancel();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnButtonClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnButtonClickListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.tip_dialog_title)
                .setMessage(R.string.tip_dialog_msg)
                .setPositiveButton(R.string.tip_dialog_done, (dialog, which) -> {
                    if (mListener != null) {
                        mListener.onDone();
                    }
                })
                .setNegativeButton(R.string.tip_dialog_cancel, (dialog, which) -> {
                    if (mListener != null) {
                        mListener.onCancel();
                    }
                });
        return builder.create();
    }
}
