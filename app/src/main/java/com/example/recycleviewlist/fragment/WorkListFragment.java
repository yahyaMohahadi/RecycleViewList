package com.example.recycleviewlist.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleviewlist.R;
import com.example.recycleviewlist.model.State;
import com.example.recycleviewlist.model.StateHandler;
import com.example.recycleviewlist.model.Task;
import com.example.recycleviewlist.model.repository.taskRepository.TaskRepository;
import com.example.recycleviewlist.model.repository.userRepository.UserRepository;

import static com.example.recycleviewlist.R.id;
import static com.example.recycleviewlist.R.layout;


public class WorkListFragment extends Fragment {

    private ImageView mImageViewEmptyTask;
    private RecyclerView mRecyclerView;
    private AdapterTask mAdapterTask;
    private State mState;
    public final static String stateKey = "com.example.recycleviewlist.fragment.stateKey";
    public static final int REQUEST_COD_EDIT = 1;


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
        checkIsEmpty();
        return view;
    }

    public void checkIsEmpty() {

        if (TaskRepository.getInstance(UserRepository.getInstance().getUser(0).getStringName()).getNumberOfStats(mState) == 0) {
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
        mAdapterTask = new AdapterTask();
        mRecyclerView.setAdapter(mAdapterTask);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        checkIsEmpty();
    }

    private class AdapterTask extends RecyclerView.Adapter {

        private class HolderTask extends RecyclerView.ViewHolder {
            TextView mTextViewName;
            ImageView mImageViewState;
            ConstraintLayout mConstraintLayout;
            private Task mTask;

            public HolderTask(@NonNull View itemView) {
                super(itemView);
                mTextViewName = itemView.findViewById(id.textView_name);
                mConstraintLayout = itemView.findViewById(id.constrain_list);
                mImageViewState = itemView.findViewById(id.imageView_state);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = TaskHandleFragment.getIntentHandel(StateHandler.EDIT, mTask);
                        DialogFragment fragmentAdd = TaskHandleFragment.newInstance(intent);
                        fragmentAdd.setTargetFragment(WorkListFragment.this, REQUEST_COD_EDIT);
                        fragmentAdd.show(getActivity().getSupportFragmentManager(), "TAG");
                    }
                });

            }

            public void bind(Task task) {
                mTask = task;
                if (task != null) {
                    mTextViewName.setText(task.getStringTitle());
                }
                switch (mTask.getState()) {
                    case DOING: {
                        mImageViewState.setImageResource(R.drawable.ic_doing);
                        break;
                    }
                    case TODO: {
                        mImageViewState.setImageResource(R.drawable.ic_todo);
                        break;
                    }
                    case DONE: {
                        mImageViewState.setImageResource(R.drawable.ic_done);
                        break;
                    }
                }
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(layout.list, parent, false);
            return new HolderTask(view);

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            ((HolderTask) holder).bind(TaskRepository.getInstance(UserRepository.getInstance().getUser(0).getStringName()).gerNumberOfTaskWithState(position + 1, mState));
        }

        @Override
        public int getItemCount() {
            return TaskRepository.getInstance(UserRepository.getInstance().getUser(0).getStringName()).getNumberOfStats(mState);
        }
    }

}