package com.example.recycleviewlist.fragment.pickers;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import androidx.fragment.app.Fragment;

import com.example.recycleviewlist.R;
import com.example.recycleviewlist.database.task.TaskRepository;
import com.example.recycleviewlist.model.Task;

import java.util.Calendar;

public class DatePickerFragment extends Picker {
    private static Task sTask;
    private View mViewDatePicker;
    private DatePicker mDatePicker;

    public static Fragment newInstance(Task task) {
        sTask = task;
        return new DatePickerFragment();
    }

    @Override
    void setResult() {
        Calendar calender = Calendar.getInstance();
        calender.setTime(sTask.getDate());
        calender.set(mDatePicker.getYear(), mDatePicker.getMonth(), mDatePicker.getDayOfMonth());
        sTask.setDate(calender.getTime());
        TaskRepository.getInstance(getActivity()).
                setTask(sTask);
    }

    @Override
    void initPicker() {
        Calendar calender = Calendar.getInstance();
        calender.setTime(sTask.getDate());
        mDatePicker.init(
                        calender.get(Calendar.YEAR),
                        calender.get(Calendar.MONTH),
                        calender.get(Calendar.DAY_OF_MONTH),
                        null);

//                new DatePicker.OnDateChangedListener(){
//                    @Override
//                    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
//
//                    }
//                }
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
