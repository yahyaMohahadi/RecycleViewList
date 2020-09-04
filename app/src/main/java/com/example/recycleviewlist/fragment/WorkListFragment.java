package com.example.recycleviewlist.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleviewlist.R;
import com.example.recycleviewlist.database.task.TaskRepository;
import com.example.recycleviewlist.fragment.pickers.DatePickerFragment;
import com.example.recycleviewlist.fragment.pickers.Picker;
import com.example.recycleviewlist.fragment.pickers.TimePickerFragment;
import com.example.recycleviewlist.model.State;
import com.example.recycleviewlist.model.StateHandler;
import com.example.recycleviewlist.model.Task;

import java.util.Date;
import java.util.UUID;

import static com.example.recycleviewlist.R.id;
import static com.example.recycleviewlist.R.layout;


public class WorkListFragment extends Fragment {

    public final static String stateKey = "com.example.recycleviewlist.fragment.stateKey";
    private ImageView mImageViewEmptyTask;
    private RecyclerView mRecyclerView;
    private WorkListAdapter mAdapterTask;
    private State mState;
    public static final int REQUEST_COD_EDIT = 1;
    public static final int REQUEST_CODE_TIME_PICKER = 2;
    public static final int REQUEST_CODE_DATEPICKER = 4;


    public static Intent newIntent(Context context, State state) {
        Intent instance = new Intent(context, WorkListFragment.class);
        instance.putExtra(stateKey, state);
        return instance;
    }


    public static Fragment newInstance(Intent intent) {
        Bundle args = new Bundle();
        args.putSerializable(stateKey, intent.getSerializableExtra(stateKey));
        WorkListFragment fragment = new WorkListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(layout.fragment_work_list, container, false);
        mState = (State) getArguments().getSerializable(stateKey);
        mRecyclerView = view.findViewById(id.recucleView_work);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mImageViewEmptyTask = view.findViewById(id.imageView_empty_task);
        checkTasksInDataBase();
        return view;
    }

    public void checkTasksInDataBase() {
        if (TaskRepository.getInstance(
                getActivity()).getListTasks(mState).size() == 0) {
            if (mRecyclerView != null) {
                mRecyclerView.setVisibility(View.GONE);
            }
            if (mImageViewEmptyTask != null) {
                mImageViewEmptyTask.setVisibility(View.VISIBLE);
                mImageViewEmptyTask.setImageResource(R.drawable.empty_pic);
            }
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mImageViewEmptyTask.setVisibility(View.GONE);
            if (mAdapterTask == null) {
                addAdapter();
            } else {
                //todo it not good performance make notify change for one task
                mAdapterTask.setList(
                        TaskRepository.getInstance(getActivity()).getListTasks(mState)
                );
                mAdapterTask.notifyDataSetChanged();
            }
        }

    }

    public void addAdapter() {
  /*      if (OnlineUser.newInstance().getOnlineUser().equals(OnlineUser.mUserRoot)) {
            mAdapterTask = WorkListAdapter.newInstance(
                    getActivity(),
                    TaskRepository.getInstance(getActivity()).getT(mState)
        } else {*/
        mAdapterTask = WorkListAdapter.newInstance(
                getActivity(),
                TaskRepository.getInstance(getActivity()).getListTasks(mState)
        );
        //}
        mAdapterTask.setCallbacks(new WorkListAdapter.Callbacks() {
            @Override
            public void itemCall(UUID uuid) {
                DialogFragment fragmentAdd = TaskHandleDialog.newInstance(
                        StateHandler.EDIT,
                        uuid
                );
                //todo get result
                fragmentAdd.setTargetFragment(WorkListFragment.this, REQUEST_COD_EDIT);
                fragmentAdd.show(getFragmentManager(), "TAG");
            }

            @Override
            public void calenderCall(UUID uuid) {
                Picker calander = (Picker) DatePickerFragment.newInstance(
                        TaskRepository.getInstance(getActivity())
                                .getTask(uuid)
                                .getDate(), uuid

                );

                calander.setTargetFragment(WorkListFragment.this, REQUEST_CODE_DATEPICKER);
                calander.show(getFragmentManager(), "tag");
            }

            @Override
            public void timeCall(UUID uuid) {
                Picker time = (Picker) TimePickerFragment.newInstance(
                        TaskRepository.getInstance(getActivity())
                                .getTask(uuid)
                                .getDate(), uuid

                );

                time.setTargetFragment(WorkListFragment.this, REQUEST_CODE_TIME_PICKER);
                time.show(getFragmentManager(), "taG");
            }
        });
        mRecyclerView.setAdapter(mAdapterTask);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || data == null) {
            return;
        } else if (requestCode == REQUEST_CODE_DATEPICKER) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.KEY_DATE_PICKER);
            UUID uuid = (UUID) data.getSerializableExtra(DatePickerFragment.KEY_UUID);
            Task task = TaskRepository.getInstance(getActivity()).getTask(uuid);
            task.setDate(date);
            TaskRepository.getInstance(getActivity()).setTask(task);

        } else if (requestCode == REQUEST_CODE_TIME_PICKER) {
            Date date = (Date) data.getSerializableExtra(TimePickerFragment.KEY_RESULT_TIME_PICKER);
            UUID uuid = (UUID) data.getSerializableExtra(TimePickerFragment.KEY_UUID);
            Task task = TaskRepository.getInstance(getActivity()).getTask(uuid);
            task.setDate(date);
            TaskRepository.getInstance(getActivity()).setTask(task);

        } else if (requestCode == REQUEST_COD_EDIT) {
            UUID uuid = (UUID) data.getSerializableExtra(TaskHandleDialog.KEY_UUID);
        }
        //todo hard prosees make ckange just onew item
        checkTasksInDataBase();
    }
}