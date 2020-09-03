package com.example.recycleviewlist.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.recycleviewlist.R;

import java.io.Serializable;



public class AlertDialog extends DialogFragment {

    private static final String sKeyTextAlert = "com.example.recycleviewlist.fragmentALERTDIALOGSTRING";
    private static final String sKeyContex = "com.example.recycleviewlist.fragmentCONTEX";
    private static final String sKeyOrder = "com.example.recycleviewlist.order";
    private static Context mContext;
    private String mStringTextAlert;
    private StateOrder mOrder;

    private AlertDialog() {
    }

    public static AlertDialog newInstance(Context context, StateOrder order, String text) {
        Bundle args = new Bundle();
        AlertDialog alertDialog = new AlertDialog();
        args.putString(sKeyTextAlert, text);
        args.putSerializable(sKeyOrder, order);
        mContext = context;
        alertDialog.setArguments(args);
        return alertDialog;
    }

    private void getArgument() {
        mOrder = (StateOrder) getArguments().getSerializable(sKeyOrder);
        mStringTextAlert = getArguments().getString(sKeyTextAlert);
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_alert, null);
        getArgument();
        TextView alertDialog = view.findViewById(R.id.textView_alert_dialog);
        alertDialog.setText(mStringTextAlert);
        return new android.app.AlertDialog.Builder(mContext)
                .setTitle("Warning")
                .setIcon(R.drawable.ic_dialog_alert)
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        doOrder();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    private void doOrder() {
        switch (mOrder) {
            case DELETE_TASK: {

                getTargetFragment().onActivityResult(getTargetRequestCode(),Activity.RESULT_OK,new Intent());
                return;
            }
            case DELETE_ACOUNT: {
                getTargetFragment().onActivityResult(getTargetRequestCode(),Activity.RESULT_OK,new Intent());
                return;
            }
        }
    }

}
enum StateOrder implements Serializable {
    DELETE_TASK, DELETE_ACOUNT
}