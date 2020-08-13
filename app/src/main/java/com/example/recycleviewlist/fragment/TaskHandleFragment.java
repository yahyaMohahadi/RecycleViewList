package com.example.recycleviewlist.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.recycleviewlist.R;
import com.example.recycleviewlist.model.State;
import com.example.recycleviewlist.model.StateHandler;
import com.example.recycleviewlist.model.Task;
import com.example.recycleviewlist.model.repository.taskRepository.TaskRepository;

import java.io.Serializable;

public class TaskHandleFragment extends DialogFragment {
    private EditText mEditTextName;
    private Button mButtonDatePicker;
    private Button mButtonTimePicker;
    private Task mTask;
    private State mState = State.DONE;
    private StateHandler mStateHandler;

    public static final String KEY_STATE_HANDLER = "com.example.recycleviewlist.activitystateForAdd";
    public static final String KEY_STATE_TASK = "com.example.recycleviewlist.actitaskKey";
    public static final int DATE_PICKER_REQUEST_CODE = 2;

    public static Intent getIntentHandel(StateHandler state, Task task) {
        Intent intent = new Intent();
        intent.putExtra(KEY_STATE_HANDLER, state);
        intent.putExtra(KEY_STATE_TASK, (Serializable) task);
        return intent;
    }

    public static TaskHandleFragment newInstance(Intent intent) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_STATE_HANDLER, intent.getSerializableExtra(KEY_STATE_HANDLER));
        args.putSerializable(KEY_STATE_TASK, intent.getSerializableExtra(KEY_STATE_TASK));
        TaskHandleFragment fragment = new TaskHandleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_start, null);
        findView(view);
        setOnClick();
        setArguments();
        initViews();
        return new AlertDialog.Builder(getActivity())
                .setTitle(mStateHandler == StateHandler.NEW ?
                        "new Task " + mState.toString() :
                        "editing task")
                .setIcon(R.mipmap.ic_launcher)
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        okClick();
                        setResult();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    private void initViews() {
        mButtonDatePicker.setText(mTask.getDateYMD());
        mButtonTimePicker.setText(mTask.getTimeHMS());
    }

    private void setOnClick() {
        mButtonDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*                DatePickerFragment datePickerFragment = (DatePickerFragment) DatePickerFragment.newInstance(mTask.getDate());

                datePickerFragment.setTargetFragment(TaskHandleFragment.this, DATE_PICKER_REQUEST_CODE);

                datePickerFragment.show(getFragmentManager(), "DIALOG_FRAGMENT_TAG");*/
            }
        });
        mButtonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void okClick() {
        mTask.setStringTitle(mEditTextName.getText().toString());
        mTask.setState(mState);
        if (mStateHandler == StateHandler.NEW) {
            TaskRepository.getInstance().addTask(mTask);
        } else {
            TaskRepository.getInstance().setTask(mTask);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        return view;
    }

    private void findView(View view) {
        mEditTextName = view.findViewById(R.id.editText_name);
        mButtonDatePicker = view.findViewById(R.id.button_date);
        mButtonTimePicker = view.findViewById(R.id.button_time);
    }

    private void setArguments() {
        mTask = (Task) getArguments().getSerializable(KEY_STATE_TASK);
        mState = mTask.getState();
        mStateHandler = (StateHandler) getArguments().getSerializable(KEY_STATE_HANDLER);
    }

    private void setResult() {
        Fragment fragment = getTargetFragment();
        fragment.onActivityResult(0, Activity.RESULT_OK, new Intent());
    }
}
