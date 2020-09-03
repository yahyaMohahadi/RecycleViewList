package com.example.recycleviewlist.fragment.pickers;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import androidx.fragment.app.Fragment;

import com.example.recycleviewlist.R;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class DatePickerFragment extends Picker {
    public static final String KEY_DATE_PICKER = "com.example.recycleviewlist.fragment.pickersate";
    public static final String KEY_UUID = "com.example.recycleviewlist.fragment.pickersateuuuiid";
    private Date mDate;
    private View mViewDatePicker;
    private DatePicker mDatePicker;
    private static UUID sUUID;

    public static Fragment newInstance(Date date,UUID uuid) {
        DatePickerFragment datePicker = new DatePickerFragment(date);
        sUUID =uuid;
        return datePicker;
    }


    private DatePickerFragment(Date mDate) {
        this.mDate = mDate;
    }

    @Override
    void setResult() {
        Calendar calender = Calendar.getInstance();
        calender.setTime(mDate);
        calender.set(mDatePicker.getYear(), mDatePicker.getMonth(), mDatePicker.getDayOfMonth());
        Intent data = new Intent();
        data.putExtra(KEY_DATE_PICKER,calender.getTime());
        data.putExtra(KEY_UUID, sUUID);
        getTargetFragment().onActivityResult(getTargetRequestCode(),
                Activity.RESULT_OK,
                data
        );
    }

    @Override
    void initPicker() {
        Calendar calender = Calendar.getInstance();
        calender.setTime(mDate);
        mDatePicker.init(
                calender.get(Calendar.YEAR),
                calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH),
                null);
    }

    @Override
    View pickerView() {
        mViewDatePicker = LayoutInflater.from(getActivity()).inflate(R.layout.date_picker, null);
        mDatePicker = mViewDatePicker.findViewById(R.id.date_picker);
        return mViewDatePicker;
    }

    @Override
    int pickerIcon() {
        return R.drawable.ic_date_picker;
    }


}
