package com.example.recycleviewlist.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recycleviewlist.R;
import com.example.recycleviewlist.fragment.SettingFragment;

public class SettingActivity extends SingleFragmentActivity {

    @Override
    Fragment newFragment() {
        return SettingFragment.newInstance();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_fragment);
    }
}