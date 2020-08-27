package com.example.recycleviewlist.fragment.pickers;

import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.recycleviewlist.R;
import com.example.recycleviewlist.model.Task;

import java.util.Date;

public class TimePickerFragment extends Picker {
    private static Date mDate;
    private static Task mTask;

    public static Fragment newInstance(Task task) {
        mDate = task.getDate();
        mTask = task;
        return new TimePickerFragment();
    }

    @Override
    void setResult() {

    }

    @Override
    void initPicker() {

    }

    @Override
    View pickerView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.time_picker,null);
        return view;
    }

    @Override
    int pickerIcon() {
        return R.drawable.ic_time_picker;
    }
}
