package com.example.recycleviewlist.fragment.pickers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

public abstract class Picker extends DialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initPicker();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    abstract View pickerView();
    //TODO RETURN View picker

    abstract int pickerIcon();
    //TODO RETURN icon picker


}
