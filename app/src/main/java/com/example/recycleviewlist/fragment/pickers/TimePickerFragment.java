package com.example.recycleviewlist.fragment.pickers;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.recycleviewlist.OnlineUser;
import com.example.recycleviewlist.R;
import com.example.recycleviewlist.model.Task;
import com.example.recycleviewlist.model.repository.taskRepository.TaskRepository;

import java.util.Calendar;

public class TimePickerFragment extends Picker {
    private static Task mTask;
    private View mViewTimePicker;
    private TimePicker mTimePicker;

    public static Fragment newInstance(Task task) {
        mTask = task;
        return new TimePickerFragment();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    void setResult() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mTask.getDate());
        calendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                mTimePicker.getHour(),
                mTimePicker.getMinute(),
                0
        );
        mTask.setDate(calendar.getTime());
        TaskRepository.getInstance(getActivity(), OnlineUser.newInstance().getOnlineUser().getUUID()).
                setTask(mTask);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    void initPicker() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mTask.getDate());
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
