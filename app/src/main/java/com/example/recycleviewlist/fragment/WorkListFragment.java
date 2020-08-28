package com.example.recycleviewlist.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleviewlist.R;
import com.example.recycleviewlist.database.task.TaskRepository;
import com.example.recycleviewlist.model.State;

import static com.example.recycleviewlist.R.id;
import static com.example.recycleviewlist.R.layout;


public class WorkListFragment extends Fragment {

    public final static String stateKey = "com.example.recycleviewlist.fragment.stateKey";
    private ImageView mImageViewEmptyTask;
    private RecyclerView mRecyclerView;
    private WorkListAdapter mAdapterTask;
    private State mState;

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
                mAdapterTask.notifyDataSetChanged();
            }
        }

    }

    public void addAdapter() {
        mAdapterTask = WorkListAdapter.newInstance(
                getActivity().getApplicationContext(),
                this,
                mState
        );
        mRecyclerView.setAdapter(mAdapterTask);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        checkTasksInDataBase();
        mAdapterTask.notifyDataSetChanged();
    }
}