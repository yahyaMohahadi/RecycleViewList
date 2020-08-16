package com.example.recycleviewlist.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.recycleviewlist.R;
import com.example.recycleviewlist.fragment.TaskHandleFragment;
import com.example.recycleviewlist.fragment.WorkListFragment;
import com.example.recycleviewlist.model.State;
import com.example.recycleviewlist.model.StateHandler;
import com.example.recycleviewlist.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    public static final int REQUEST_COD_ADD = 0;
    ViewPager2 mViewPagerTask;
    List<State> mStates = new ArrayList<>(Arrays.asList(State.DONE, State.DOING, State.TODO));
    TextView mTextViewDone;
    TextView mTextViewToDo;
    TextView mTextViewDoing;
    int mIntCurrent = 0;
    private FloatingActionButton mActionButton;
    private Fragment[] mFragments = new Fragment[3];
/*    private static Fragment mFragmentCurent;
    private static FragmentManager sFragmentManager;*/


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_view_pager);
        setFragments();
        findView();
        setOnClick();
        setUI();
    }

    private void setData() {
        for (Fragment fragment :
                mFragments) {
            ((WorkListFragment) fragment).addAdapter();
        }
    }

    private void setUI() {
        mViewPagerTask.setAdapter(new ViewPagerAdapter(this));
        setRegisterForViewPager();
    }

/*    public static Fragment getCurentFragment(){
        return mFragmentCurent;
    }
    public static FragmentManager getFragmentManagere(){
        return sFragmentManager;
    }*/
    private void setOnClick() {
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = TaskHandleFragment.getIntentHandel(StateHandler.NEW,new Task(mStates.get(mIntCurrent),""));
                DialogFragment fragmentAdd = TaskHandleFragment.newInstance(intent);
         /*       mFragmentCurent = mFragments[mIntCurrent];
                sFragmentManager = getSupportFragmentManager();*/
                fragmentAdd.setTargetFragment(mFragments[mIntCurrent], REQUEST_COD_ADD);
                fragmentAdd.show(getSupportFragmentManager(),"TAG");
            }
        });
        mTextViewDoing.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mViewPagerTask.setCurrentItem(1);
                    }
                }
        );
        mTextViewDone.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mViewPagerTask.setCurrentItem(0);
                    }
                }
        );
        mTextViewToDo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mViewPagerTask.setCurrentItem(2);
                    }
                }
        );
    }

    private void setRegisterForViewPager() {
        mViewPagerTask.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mIntCurrent = position;
                setTableColors();
                ((WorkListFragment)mFragments[position]).checkIsEmpty();

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);

            }
        });
    }

    private void findView() {
        mTextViewDoing = findViewById(R.id.textView_doing);
        mTextViewDone = findViewById(R.id.textView_done);
        mTextViewToDo = findViewById(R.id.textView_todo);
        mViewPagerTask = findViewById(R.id.ViewPager_task);
        mActionButton = findViewById(R.id.floatingActionButton_add);
    }

    private void setFragments() {
        for (int i = 0; i < 3; i++) {
            Fragment fragment = WorkListFragment.newInstance(
                    WorkListFragment.newIntent(
                            MainActivity.this, mStates.get(i)
                    ));
            mFragments[i] = fragment;

        }
    }

    private void setTableColors() {
        mTextViewToDo.setBackgroundResource(R.color.color_bachground_table_of);
        mTextViewDone.setBackgroundResource(R.color.color_bachground_table_of);
        mTextViewDoing.setBackgroundResource(R.color.color_bachground_table_of);
        switch (mIntCurrent) {
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
        public WorkListFragment createFragment(int position) {

            return (WorkListFragment) mFragments[position];
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}