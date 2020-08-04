package com.example.recycleviewlist.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.recycleviewlist.R;
import com.example.recycleviewlist.WorkListActivity;

public class StartFragment extends Fragment {
    private EditText mEditTextNumber;
    private EditText mEditTextName;
    private Button mButtonStart;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_start, container, false);
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
            }
        });
    }

    private void findView(View view) {
        mEditTextName = view.findViewById(R.id.editText_name);
        mEditTextNumber = view.findViewById(R.id.editText_number);
        mButtonStart = view.findViewById(R.id.button_start);
    }
}