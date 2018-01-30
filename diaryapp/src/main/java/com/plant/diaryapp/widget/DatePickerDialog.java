package com.plant.diaryapp.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.plant.diaryapp.R;


public class DatePickerDialog extends DialogFragment {

    private static final String TAG = "DatePickerDialog";

    private OnDateClickListener mListener;

    public interface OnDateClickListener {

        void onItemClick(String item);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnDateClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnDateClickListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] items = getResources().getStringArray(R.array.dialog_year);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_title)
                .setItems(R.array.dialog_year, (dialog, which) -> {
                    mListener.onItemClick(items[which]);
                });
        return builder.create();
    }

}
