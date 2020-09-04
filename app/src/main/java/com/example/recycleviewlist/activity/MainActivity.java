package com.example.recycleviewlist.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.recycleviewlist.R;
import com.example.recycleviewlist.fragment.MainFragment;

public class MainActivity extends SingleFragmentActivity {
    public static final int REQUEST_CODE_SETTING = 5;

    @Override
    MainFragment newFragment() {
        return MainFragment.newInstance(new MainFragment.Callbacks() {

            @Override
            public void startSeting() {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SETTING);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_fragment);
    }
}