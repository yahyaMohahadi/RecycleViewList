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

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.recycleviewlist.R;
import com.example.recycleviewlist.model.State;
import com.example.recycleviewlist.model.Task;
import com.example.recycleviewlist.model.repository.taskRepository.TaskRepository;

public class AddFragment extends DialogFragment {
    private EditText mEditTextNumber;
    private EditText mEditTextName;
    private State mState = State.DONE;

    public static final  String  KEY_STATE_ADD_FRAG = "com.example.recycleviewlist.activitystateForAdd";
    private  static AddFragment mAddFragment ;

    public static Intent getIntentAdd( State state) {
        Intent intent = new Intent();
        intent.putExtra(KEY_STATE_ADD_FRAG,state);
        return intent;
    }

    public static AddFragment newInstance(Intent intent) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_STATE_ADD_FRAG,intent.getSerializableExtra(KEY_STATE_ADD_FRAG));
        AddFragment fragment = new AddFragment();
        fragment.setArguments(args);
        mAddFragment = fragment;
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_start, null);
        findView(view);
        setArguments();
        return new AlertDialog.Builder(getActivity())
                .setTitle("new Task " + mState.toString())
                .setIcon(R.mipmap.ic_launcher)
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        addClick();
                        setResult();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    private void addClick() {
        String name = mEditTextName.getText().toString();
        int number = Integer.parseInt(mEditTextNumber.getText().toString());
        for (int i = 0; i < number; i++) {
            TaskRepository.getInstance().addTask(new Task(
                    mState, name
            ));
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
        mEditTextNumber = view.findViewById(R.id.editText_number);
    }

    private void setArguments() {
        mState = (State) getArguments().getSerializable(KEY_STATE_ADD_FRAG);
    }

    private void setResult() {
        Fragment fragment = getTargetFragment();
        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, new Intent());
        //getActivity().finish();
    }
}