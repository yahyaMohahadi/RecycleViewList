package com.example.recycleviewlist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.recycleviewlist.fragment.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorkListActivityNotFragment extends AppCompatActivity {


    ViewPager2 mViewPagerTask;
    List<State> mStates = new ArrayList<>(Arrays.asList(State.DONE, State.DOING, State.TODO));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_list_not_fragment);
        mViewPagerTask = findViewById(R.id.ViewPager_task);
        mViewPagerTask.setAdapter(new ViewPagerAdapter(this));

    }

    private class ViewPagerAdapter extends FragmentStateAdapter {


        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return WorkListActivity.newInstance(
                    WorkListActivity.newIntent(
                            WorkListActivityNotFragment.this,
                            10,
                            "maktab",
                            mStates.get(position)
                    ));
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}