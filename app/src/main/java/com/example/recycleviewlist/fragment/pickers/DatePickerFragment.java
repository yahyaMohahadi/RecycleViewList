package com.example.recycleviewlist.fragment.pickers;

import androidx.fragment.app.Fragment;

import com.example.recycleviewlist.R;

import java.util.Date;

public class DatePickerFragment extends Picker {
    private static Date mDate;

    @Override
    void setResult() {

    }

    @Override
    void initPicker() {

    }

    @Override
    int pickerView() {
        return R.id.date_picker;
    }

    @Override
    int pickerIcon() {
        return R.drawable.ic_date_picker;
    }

    public  static Fragment newInstance(Date date) {
        mDate = date;
        return new DatePickerFragment();
    }
}
