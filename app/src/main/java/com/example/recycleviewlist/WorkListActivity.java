package com.example.recycleviewlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.recycleviewlist.fragment.State;
import com.example.recycleviewlist.fragment.WorkListFragment;

public class WorkListActivity extends SingleFragment {
    public final static String nameKey = "com.example.recycleviewlist.fragment.nameKey";
    public final static String numberKey = "com.example.recycleviewlist.fragment.numberKey";
    public final static String stateKey = "com.example.recycleviewlist.fragment.stateKey";


    public static Intent newIntent(Context context, int number, String name , State state) {
        Intent instance = new Intent(context, WorkListActivity.class);
        Bundle bundle = new Bundle();
        instance.putExtra(nameKey, name);
        instance.putExtra(numberKey, number);
        instance.putExtra(stateKey, state);
        return instance;

    }

    @Override
    protected Fragment setFragment() {
        return newInstance(getIntent());
    }

    public static Fragment newInstance(Intent intent) {
        Bundle args = new Bundle();
        args.putString(nameKey, intent.getStringExtra(nameKey));
        args.putInt(numberKey, intent.getIntExtra(numberKey, 0));
        args.putSerializable(stateKey, intent.getSerializableExtra(stateKey));
        WorkListFragment fragment = new WorkListFragment();
        fragment.setArguments(args);
        return fragment;
    }
}