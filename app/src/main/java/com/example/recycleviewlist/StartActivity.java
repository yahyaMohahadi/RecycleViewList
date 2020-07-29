package com.example.recycleviewlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.recycleviewlist.fragment.StartFragment;

public class StartActivity extends SingleFragment {
    @Override
    protected Fragment setFragment() {
        return new StartFragment();
    }
}