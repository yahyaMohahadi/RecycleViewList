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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.recycleviewlist.R;
import com.example.recycleviewlist.database.task.TaskRepository;
import com.example.recycleviewlist.model.State;
import com.example.recycleviewlist.model.StateHandler;
import com.example.recycleviewlist.model.Task;

import java.util.UUID;

public class TaskHandleDialog extends DialogFragment {
    public static final String KEY_STATE_HANDLER = "com.example.recycleviewlist.activitystateForAdd";
    public static final String KEY_UUID_TASK = "com.example.recycleviewlist.actitaskKey";
    public static final String KEY_UUID = "com.example.recycleviewlist.fragmentRESULTUUUID";
    public static final String KEY_STATE = "com.example.recycleviewlist.fragmentSTATE";
    private EditText mEditTextName;
    private EditText mEditTextDiscreption;
    private UUID mTaskUUID;
    private Task mTask;
    private TextView mTextViewTimeShow;
    private State mState = State.DONE;
    private StateHandler mStateHandler;
    private RadioGroup mRadioGroupStete;
    private RadioButton mRadioButtonDone;
    private RadioButton mRadioButtonDoing;
    private RadioButton mRadioButtonTodo;

    public static TaskHandleDialog newInstance(StateHandler state, UUID taskUUID) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_STATE_HANDLER, state);
        args.putSerializable(KEY_UUID_TASK, taskUUID);
        TaskHandleDialog fragment = new TaskHandleDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.handler_dialog_fragment, null);
        findView(view);
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
                        okClickOk();
                        setResult();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .setNeutralButton("delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        deleteTask();
                        setResult();
                    }
                })
                .create();
    }

    private void setArguments() {
        mTaskUUID = (UUID) getArguments().getSerializable(KEY_UUID_TASK);
        mTask = TaskRepository.getInstance(getActivity()).getTask(mTaskUUID);
        mState = mTask.getState();
        mStateHandler = (StateHandler) getArguments().getSerializable(KEY_STATE_HANDLER);
    }

    private void deleteTask() {
        TaskRepository.getInstance(getActivity()).removeTask(mTask);
    }

    private void initViews() {
        setStateRadioGroup(mTask.getState());
        mTextViewTimeShow.setText(mTask.getDate().toString());
            mEditTextDiscreption.setText(mTask.getStringDescription());
            mEditTextName.setText(mTask.getStringTitle());
    }


    private void okClickOk() {
        mTask.setStringTitle(mEditTextName.getText().toString());
        mTask.setStringDescription(mEditTextDiscreption.getText().toString());
        mTask.setState(getSatateRadioGroup());
/*        if (mStateHandler == StateHandler.NEW) {
            TaskRepository.getInstance(getActivity()).addTask(mTask);
        } else if (mStateHandler == StateHandler.EDIT) {*/
        TaskRepository.getInstance(getActivity()).setTask(mTask);
        //  }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.handler_dialog_fragment, container, false);
        return view;
    }

    private void findView(View view) {
        mEditTextName = view.findViewById(R.id.editText_name);
        mTextViewTimeShow = view.findViewById(R.id.textView_time);
        mRadioGroupStete = view.findViewById(R.id.RadioGroup_state);
        mEditTextDiscreption = view.findViewById(R.id.editText_discription);
        mRadioButtonDone = view.findViewById(R.id.radioButton_done);
        mRadioButtonDoing = view.findViewById(R.id.radioButton_doing);
        mRadioButtonTodo = view.findViewById(R.id.radioButton_todo);

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

    private void setResult() {
        Fragment fragment = getTargetFragment();
        Intent data = new Intent();
        data.putExtra(KEY_STATE,mTask.getState());
        data.putExtra(KEY_UUID,mTaskUUID);
        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
    }
}
