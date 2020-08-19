package com.example.recycleviewlist.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.amitshekhar.DebugDB;
import com.example.recycleviewlist.R;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    abstract Fragment newFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_fragment);
        if (savedInstanceState == null){
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().add(R.id.sigle_fragment_container,newFragment()).commit();
        }
        Log.d("QQQ", DebugDB.getAddressLog());
/*        Class<?> debugDB = null;
        try {
            debugDB.getMethod("getAddressLog");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            debugDB = Class.forName("com.amitshekhar.DebugDB");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
    }
}
