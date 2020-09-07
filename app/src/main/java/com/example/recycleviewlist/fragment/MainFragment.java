package com.example.recycleviewlist.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.recycleviewlist.OnlineUser;
import com.example.recycleviewlist.R;
import com.example.recycleviewlist.database.task.TaskRepository;
import com.example.recycleviewlist.database.user.UserRepository;
import com.example.recycleviewlist.model.State;
import com.example.recycleviewlist.model.StateHandler;
import com.example.recycleviewlist.model.Task;
import com.example.recycleviewlist.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainFragment extends Fragment {

    public static final int REQUEST_COD_ADD = 0;
    public static final int REQUEST_COD_ALERT_DELETE = 3;
    public static final int REQUEST_COD_ALERT_DELETE_ACUNT = 4;


    ViewPager2 mViewPagerTask;
    List<State> mStates = new ArrayList<>(Arrays.asList(State.TODO, State.DOING, State.DONE));
    TextView mTextViewDone;
    TextView mTextViewToDo;
    TextView mTextViewDoing;
    int mIntCurrent = 0;
    private FloatingActionButton mActionButton;
    private Fragment[] mFragments = new Fragment[3];
    private User mUserOnline = OnlineUser.newInstance().getOnlineUser();
    private Callbacks mCallbacks;
    SearchView mSearchView;

    public static MainFragment newInstance(Callbacks callbacks) {
        MainFragment mainFragment = new MainFragment();
        mainFragment.setCallbacks(callbacks);
        return mainFragment;
    }

    public void setCallbacks(Callbacks callbacks) {
        mCallbacks = callbacks;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        setFragments();
        findView(view);
        setOnClick();
        setUI();
        setHasOptionsMenu(true);
        return view;
    }

    private void setFragments() {
        for (int i = 0; i < 3; i++) {
            Fragment fragment = WorkListFragment.newInstance(
                    WorkListFragment.newIntent(
                            getActivity(), mStates.get(i)
                    ));
            mFragments[i] = fragment;
        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
        if (OnlineUser.newInstance().getOnlineUser().equals(OnlineUser.mUserRoot)) {
            menu.findItem(R.id.actionbar_main_seting).setVisible(true);
        }
        initSearchView(menu);
    }

    private void initSearchView(@NonNull Menu menu) {
        mSearchView = (SearchView) menu.findItem(R.id.action_bar_search).getActionView();
        mSearchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //Toast.makeText(getContext(), query, Toast.LENGTH_SHORT).show();
                ((WorkListFragment) mFragments[mIntCurrent]).onSearch(query);
                return false;

            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            default: {
                return super.onOptionsItemSelected(item);
            }
            case R.id.actionbar_main_delete_acount: {
                AlertDialog alertDialog = AlertDialog.newInstance(getActivity(),
                        StateOrder.DELETE_ACOUNT
                        , "Are you sure to delete your acount?\nit means delete all task and settings"
                );

                alertDialog.setTargetFragment(this, REQUEST_COD_ALERT_DELETE_ACUNT);
                alertDialog.show(getActivity().getSupportFragmentManager(), "eee");
                return true;
            }
            case R.id.actionbar_main_delete_tasks: {
                AlertDialog alertDialog = AlertDialog.newInstance(getActivity(),
                        StateOrder.DELETE_TASK,
                        "Are you sure to clear all of your tasks?");
                alertDialog.setTargetFragment(this, REQUEST_COD_ALERT_DELETE);
                alertDialog.show(getActivity().getSupportFragmentManager(), "ttt");
                return true;
            }
            case R.id.actionbar_main_logout: {
                logout();
                getActivity().finish();
                return true;
            }
            case R.id.actionbar_main_seting: {
                mCallbacks.startSeting();
                return true;
            }
        }

    }

    private void logout() {
        startActivity(new Intent(getActivity(), com.example.recycleviewlist.activity.LoginActivity.class));
    }


    private void setMenuView() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(mUserOnline.getStringName());
        activity.getSupportActionBar().setTitle("Task Manager");
    }


    private void findView(View view) {
        mTextViewDoing = view.findViewById(R.id.textView_doing);
        mTextViewDone = view.findViewById(R.id.textView_done);
        mTextViewToDo = view.findViewById(R.id.textView_todo);
        mViewPagerTask = view.findViewById(R.id.ViewPager_task);
        mActionButton = view.findViewById(R.id.floatingActionButton_add);
    }

    private void setOnClick() {
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task add = new Task.Bulder()
                        .setTitle("")
                        .setDate(new Date())
                        .setState(mStates.get(mIntCurrent))
                        .creat();
                TaskRepository.getInstance(getActivity()).addTask(add);
                DialogFragment fragmentAdd = TaskHandleDialog.newInstance(StateHandler.NEW,
                        add.getUUID()
                );
                fragmentAdd.setTargetFragment(MainFragment.this, REQUEST_COD_ADD);
                fragmentAdd.show(getActivity().getSupportFragmentManager(), "TAG");
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

    private void setUI() {
        mViewPagerTask.setAdapter(new ViewPagerAdapter(getActivity()));
        setRegisterForViewPager();
    }

    private void setRegisterForViewPager() {
        mViewPagerTask.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (mSearchView != null)
                    mSearchView.setIconified(true);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mIntCurrent = position;
                setTableColors();
                ((WorkListFragment) mFragments[position]).checkTasksInDataBase();

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);

            }
        });
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || data == null) {
            return;
        } else if (requestCode == REQUEST_COD_ADD) {
            State stateChange = (State) data.getSerializableExtra(TaskHandleDialog.KEY_STATE);
            ((WorkListFragment) mFragments[(mStates.indexOf(stateChange))]).checkTasksInDataBase();
        } else if (requestCode == REQUEST_COD_ALERT_DELETE) {
            deleteAllTasks();
        } else if (requestCode == REQUEST_COD_ALERT_DELETE_ACUNT) {
            deleteAcount();
        }
    }

    private void deleteAcount() {
        deleteAllTasks();
        UserRepository.getInstance(getActivity())
                .deleteUser(OnlineUser.newInstance().getOnlineUser().getStringName());

        getActivity().finish();
    }

    private void deleteAllTasks() {
        for (Fragment fragment : mFragments) {
            if (OnlineUser.newInstance().getRoot()) {
                TaskRepository.getInstance(getActivity())
                        .removeTasksAllUsers();
            }
            TaskRepository.getInstance(getActivity())
                    .removeTasksOnlineUser();
            ((WorkListFragment) fragment).checkTasksInDataBase();
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

    public interface Callbacks {
        void startSeting();
    }
}
