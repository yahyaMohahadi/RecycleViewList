package com.example.recycleviewlist.fragment.pickers;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.recycleviewlist.R;
import com.example.recycleviewlist.model.Task;

import java.util.Date;

public class DatePickerFragment extends Picker {
    private static Date mDate;
    private static Task sTask;

    public static Fragment newInstance(Task task) {
        mDate = task.getDate();
        sTask =task;
        return new DatePickerFragment();
    }

    @Override
    void setResult() {
        getTargetFragment().onActivityResult(getTargetRequestCode(),
                Activity.RESULT_OK,
                new Intent().putExtra("data",mDate)
        );
    }

    @Override
    void initPicker() {

    }

    @Override
    View pickerView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.date_picker,null);
        return view;
    }

    @Override
    int pickerIcon() {
        return R.drawable.ic_date_picker;
    }
}
