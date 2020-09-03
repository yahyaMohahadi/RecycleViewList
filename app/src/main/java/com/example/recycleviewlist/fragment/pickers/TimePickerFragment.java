package com.example.recycleviewlist.fragment.pickers;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.recycleviewlist.R;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class TimePickerFragment extends Picker {
    public static final String KEY_RESULT_TIME_PICKER = "com.example.recycleviewlist.fragment.pickersTIMEPICKER";
    public static final String KEY_UUID = "com.example.recycleviewlist.fragment.pickersUUID";
    private View mViewTimePicker;
    private static UUID sUUID;
    private Date mDate;
    private TimePicker mTimePicker;


    public static Fragment newInstance(Date date, UUID uuid) {
        sUUID = uuid;
        return new TimePickerFragment(date);
    }

    public TimePickerFragment(Date date) {
        mDate = date;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    void setResult() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        calendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                mTimePicker.getHour(),
                mTimePicker.getMinute(),
                0
        );
        Intent data = new Intent();
        data.putExtra(KEY_RESULT_TIME_PICKER, calendar.getTime());
        data.putExtra(KEY_UUID, sUUID);
        getTargetFragment().onActivityResult(getTargetRequestCode(),
                Activity.RESULT_OK,
                data
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    void initPicker() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        mTimePicker.setHour(calendar.get(Calendar.HOUR));
        mTimePicker.setMinute(calendar.get(Calendar.MINUTE));
    }

    @Override
    View pickerView() {
        mViewTimePicker = LayoutInflater.from(getActivity()).inflate(R.layout.time_picker, null);
        mTimePicker = mViewTimePicker.findViewById(R.id.time_picker);
        return mViewTimePicker;
    }

    @Override
    int pickerIcon() {
        return R.drawable.ic_time_picker;
    }
}
