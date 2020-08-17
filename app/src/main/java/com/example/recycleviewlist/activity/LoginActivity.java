package com.example.recycleviewlist.activity;

import androidx.fragment.app.Fragment;

import com.example.recycleviewlist.fragment.LoginFragment;

public class LoginActivity extends SingleFragmentActivity {
    @Override
    Fragment newFragment() {
        return new LoginFragment();
    }
}
