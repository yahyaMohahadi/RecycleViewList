package com.example.recycleviewlist.fragment.pickers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.recycleviewlist.R;

import java.util.Date;

public abstract class Picker extends DialogFragment {
    private android.widget.DatePicker mDatePicker;
    private Date mDateCurentDate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.picker_dialog_alert_fragment, null);

        findView(view);
        initPicker();
        return new AlertDialog.Builder(getActivity())
                .setIcon(pickerIcon())
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                setResult();
                            }
                        }
                )
                .setNegativeButton(android.R.string.cancel, null)
                .setView(pickerView())
                .create();
    }

    abstract void setResult();
    //TODO get date frome date pickeer and save them in repository

    abstract void initPicker();
    //TODO init whith date

    abstract int pickerView();
    //TODO RETURN View picker

    abstract int pickerIcon();
    //TODO RETURN icon picker


    private void findView(View view) {
        mDatePicker = view.findViewById(pickerView());
    }
}
