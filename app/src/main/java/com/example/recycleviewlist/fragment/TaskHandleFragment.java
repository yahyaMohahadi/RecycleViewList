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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.recycleviewlist.OnlineUser;
import com.example.recycleviewlist.R;
import com.example.recycleviewlist.model.State;
import com.example.recycleviewlist.model.StateHandler;
import com.example.recycleviewlist.model.Task;
import com.example.recycleviewlist.model.repository.taskRepository.TaskRepository;

public class TaskHandleFragment extends DialogFragment {
    private EditText mEditTextName;
    private EditText mEditTextDiscreption;
    private Button mButtonDatePicker;
    private Button mButtonTimePicker;
    private Task mTask;
    private State mState = State.DONE;
    private StateHandler mStateHandler;
    private RadioGroup mRadioGroupStete;
    private RadioButton mRadioButtonDone;
    private RadioButton mRadioButtonDoing;
    private RadioButton mRadioButtonTodo;
    public static final String KEY_STATE_HANDLER = "com.example.recycleviewlist.activitystateForAdd";
    public static final String KEY_STATE_TASK = "com.example.recycleviewlist.actitaskKey";
    public static final int DATE_PICKER_REQUEST_CODE = 2;

    public static Intent getIntentHandel(StateHandler state, Task task) {
        Intent intent = new Intent();
        intent.putExtra(KEY_STATE_HANDLER, state);
        intent.putExtra(KEY_STATE_TASK, task);
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
        View view = inflater.inflate(R.layout.handler_dialog_fragment, null);
        findView(view);
        setOnClick();
        setArguments();
        initViews();

        return new AlertDialog.Builder(getActivity())
                .setTitle(mStateHandler == StateHandler.NEW ?
                        "new Task " + mState.toString() :
                        "editing task")
                .setIcon(R.drawable.ic_task_handeler)
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        okClick();
                        setResult(Activity.RESULT_OK);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .setNeutralButton("delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        deleteTask();
                        setResult(Activity.RESULT_CANCELED);
                    }
                })
                .create();
    }

    private void deleteTask() {
        TaskRepository.getInstance(getActivity().getApplicationContext(),
                OnlineUser.newInstance().getOnlineUser().getUUID()).removeTask(mTask.getUUID());
    }

    private void initViews() {
        setStateRadioGroup(mTask.getState());
        if (mStateHandler == StateHandler.NEW) {
            mButtonDatePicker.setText(mTask.getDateYMD());
            mButtonTimePicker.setText(mTask.getTimeHMS());
        } else {
            mEditTextDiscreption.setText(mTask.getStringDescription());
            mEditTextName.setText(mTask.getStringTitle());
        }
    }

    private void setOnClick() {
        mButtonDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO FIND fRAGMENT
                //DatePickerFragment datePickerFragment = (DatePickerFragment) DatePickerFragment.newInstance(mTask.getDate());

                //datePickerFragment.setTargetFragment(MainActivity.getCurentFragment(), DATE_PICKER_REQUEST_CODE);

                //getDialog().dismiss();
                //FragmentManager fragmentManager = getParentFragment().getFragmentManager();
                //datePickerFragment.show(MainActivity.getFragmentManagere(), "DIALOG_FRAGMENT_TAG");
            }
        });
        mButtonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO FIND fRAGMENT
            }
        });
    }

    private void okClick() {
        mTask.setStringTitle(mEditTextName.getText().toString());
        mTask.setState(mState);
        mTask.setStringDescription(mEditTextDiscreption.getText().toString());
        mTask.setState(getSatateRadioGroup());
        if (mStateHandler == StateHandler.NEW) {
            TaskRepository.getInstance(getActivity().getApplicationContext(),
                    OnlineUser.newInstance().getOnlineUser().getUUID()).addTask(mTask);
        } else {
            TaskRepository.getInstance(getActivity().getApplicationContext(),
                    OnlineUser.newInstance().getOnlineUser().getUUID()).setTask(mTask);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.handler_dialog_fragment, container, false);
        return view;
    }

    private void findView(View view) {
        mEditTextName = view.findViewById(R.id.editText_name);
        mButtonDatePicker = view.findViewById(R.id.button_date);
        mButtonTimePicker = view.findViewById(R.id.button_time);
        mRadioGroupStete = view.findViewById(R.id.RadioGroup_state);
        mEditTextDiscreption = view.findViewById(R.id.editText_discription);
        mRadioButtonDone = view.findViewById(R.id.radioButton_done);
        mRadioButtonDoing = view.findViewById(R.id.radioButton_doing);
        mRadioButtonTodo = view.findViewById(R.id.radioButton_todo);

    }

    private void setArguments() {
        mTask = (Task) getArguments().getSerializable(KEY_STATE_TASK);
        mState = mTask.getState();
        mStateHandler = (StateHandler) getArguments().getSerializable(KEY_STATE_HANDLER);
    }

    private State getSatateRadioGroup() {
        switch (mRadioGroupStete.getCheckedRadioButtonId()) {
            case R.id.radioButton_done: {
                return State.DONE;
            }
            case R.id.radioButton_doing: {
                return State.DOING;
            }
            case R.id.radioButton_todo: {
                return State.TODO;
            }
        }
        return null;

    }

    private void setStateRadioGroup(State state) {

        switch (state) {
            case DONE: {
                mRadioButtonDone.setChecked(true);
                break;
            }
            case TODO: {
                mRadioButtonTodo.setChecked(true);
                break;
            }
            case DOING: {
                mRadioButtonDoing.setChecked(true);
                break;
            }
        }
    }

    private void setResult(int result) {
        Fragment fragment = getTargetFragment();
        fragment.onActivityResult(0, result, new Intent());
    }
}
