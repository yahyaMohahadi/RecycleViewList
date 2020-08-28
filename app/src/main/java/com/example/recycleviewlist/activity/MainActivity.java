package com.example.recycleviewlist.activity;

import android.os.Bundle;

import com.example.recycleviewlist.R;
import com.example.recycleviewlist.fragment.MainFragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    MainFragment newFragment() {
        return new MainFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_fragment);
    }
}