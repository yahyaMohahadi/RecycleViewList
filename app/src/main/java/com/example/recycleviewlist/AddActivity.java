package com.example.recycleviewlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.recycleviewlist.fragment.AddFragment;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager manager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            manager.beginTransaction().add(R.id.fragment_container, new AddFragment()).commit();
        }
    }

    public static Intent getIntentAdd(Context context) {
        Intent instance = new Intent(context, AddActivity.class);
        return instance;
    }

    public static Fragment newInstance(Intent intent) {
        Bundle args = new Bundle();
        AddFragment fragment = new AddFragment();
        fragment.setArguments(args);
        return fragment;
    }
}