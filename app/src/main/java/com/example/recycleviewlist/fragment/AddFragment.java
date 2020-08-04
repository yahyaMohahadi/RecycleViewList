package com.example.recycleviewlist.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import com.example.recycleviewlist.R;
import com.example.recycleviewlist.model.State;

public class AddFragment extends Fragment {
    private EditText mEditTextNumber;
    private EditText mEditTextName;
    private Button mButtonStart;
    private RadioGroup mRadioGroupState;
    private State mState = State.DONE;

    public static final String KEY_NAME = "com.example.recycleviewlist.fragmentresultName";
    public static final String KEY_NUMBER = "com.example.recycleviewlist.fragmentresultNumber";
    public static final String KEY_STATE = "com.example.recycleviewlist.fragmentresultState";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        findView(view);
        setOnClick();
        return view;
    }

    private void setOnClick() {
        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mEditTextName.getText().toString();
                int number = Integer.parseInt(mEditTextNumber.getText().toString());
                //startActivity(WorkListActivity.newIntent(getActivity(),number,name));

                Intent returnIntent = new Intent();
                returnIntent.putExtra(KEY_NAME, name);
                returnIntent.putExtra(KEY_NUMBER, number);
                returnIntent.putExtra(KEY_STATE, mState);
                getActivity().setResult(Activity.RESULT_OK, returnIntent);
                getActivity().finish();
            }
        });

        mRadioGroupState.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton_done:
                        mState = State.DONE;
                        break;
                    case R.id.radioButton_doing:
                        mState = State.DOING;
                        break;
                    case R.id.radioButton_todo:
                        mState = State.TODO;
                        break;
                }
            }
        });
    }

    private void findView(View view) {
        mEditTextName = view.findViewById(R.id.editText_name);
        mEditTextNumber = view.findViewById(R.id.editText_number);
        mButtonStart = view.findViewById(R.id.button_start);
        mRadioGroupState = view.findViewById(R.id.RadioGroup_state);

    }
}