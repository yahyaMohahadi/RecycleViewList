package com.example.recycleviewlist.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recycleviewlist.fragment.LoginFragment;

public class LoginActivity extends SingleFragmentActivity {
    @Override
    Fragment newFragment() {
        return new LoginFragment();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
    }
}
