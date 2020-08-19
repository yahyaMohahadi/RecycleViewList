package com.example.recycleviewlist.activity;

import android.os.Bundle;

import com.example.recycleviewlist.OnlineUser;
import com.example.recycleviewlist.R;
import com.example.recycleviewlist.fragment.MainFragment;
import com.example.recycleviewlist.model.repository.taskRepository.TaskRepository;

public class MainActivity extends SingleFragmentActivity {

    @Override
    MainFragment newFragment() {
        return new MainFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_fragment);
        //for get tasks on db at first
        TaskRepository.getInstance(getApplicationContext(),
                OnlineUser.newInstance().getOnlineUser().getUUID());
    }
}