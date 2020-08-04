package com.example.recycleviewlist;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
    TextView mTextViewDone;
    TextView mTextViewToDo;
    TextView mTextViewDoing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_list_not_fragment);

        mTextViewDoing = findViewById(R.id.textView_doing);
        mTextViewDone = findViewById(R.id.textView_done);
        mTextViewToDo = findViewById(R.id.textView_todo);

        mViewPagerTask = findViewById(R.id.ViewPager_task);
        mViewPagerTask.setAdapter(new ViewPagerAdapter(this));
        mViewPagerTask.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                SetTableColors(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

    }

    private void SetTableColors(int position) {
        mTextViewToDo.setBackgroundResource(R.color.color_bachground_table_of);
        mTextViewDone.setBackgroundResource(R.color.color_bachground_table_of);
        mTextViewDoing.setBackgroundResource(R.color.color_bachground_table_of);
        switch (position) {
            case 0:
                mTextViewDone.setBackgroundResource(R.color.color_bachground_table_on);
                break;
            case 1:
                mTextViewDoing.setBackgroundResource(R.color.color_bachground_table_on);
                break;
            case 2:
                mTextViewToDo.setBackgroundResource(R.color.color_bachground_table_on);
                break;
        }
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