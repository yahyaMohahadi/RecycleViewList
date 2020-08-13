package com.example.recycleviewlist.fragment.pickers;

import androidx.fragment.app.Fragment;

import com.example.recycleviewlist.R;

import java.util.Date;

public class TimePickerFragment extends Picker {
    private static Date mDate;

    @Override
    void setResult() {

    }

    @Override
    void initPicker() {

    }

    @Override
    int pickerView() {
        return R.id.time_picker;
    }

    @Override
    int pickerIcon() {
        return R.drawable.ic_time_picker;
    }

    public  static  Fragment newInstance(Date date) {
        mDate = date;
        return new TimePickerFragment();
    }
}
