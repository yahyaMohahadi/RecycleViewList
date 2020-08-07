package com.example.recycleviewlist.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.recycleviewlist.R;
import com.example.recycleviewlist.fragment.AddFragment;
import com.example.recycleviewlist.model.State;

public class AddActivity extends AppCompatActivity {

    public static final  String  KEY_STATE_ADD_FRAG = "com.example.recycleviewlist.activitystateForAdd";
    private  static AddFragment mAddFragment ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager manager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            manager.beginTransaction().add(R.id.fragment_container, mAddFragment).commit();
        }
    }

    public static Intent getIntentAdd(Context context, State state) {
        Intent intent = new Intent(context, AddActivity.class);
        intent.putExtra(KEY_STATE_ADD_FRAG,state);
        return intent;
    }

    public static AddFragment newInstance(Intent intent) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_STATE_ADD_FRAG,intent.getSerializableExtra(KEY_STATE_ADD_FRAG));
        AddFragment fragment = new AddFragment();
        fragment.setArguments(args);
        mAddFragment = fragment;
        return fragment;
    }
}