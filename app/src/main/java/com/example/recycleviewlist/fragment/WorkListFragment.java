package com.example.recycleviewlist.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleviewlist.model.State;
import com.example.recycleviewlist.model.Task;
import com.example.recycleviewlist.model.TaskRepository;

import static com.example.recycleviewlist.R.drawable.ic_action_doing;
import static com.example.recycleviewlist.R.drawable.ic_action_done;
import static com.example.recycleviewlist.R.drawable.ic_action_todo;
import static com.example.recycleviewlist.R.id;
import static com.example.recycleviewlist.R.layout;


public class WorkListFragment extends Fragment {


    private RecyclerView mRecyclerView;
    //private Random mRandom = new Random();
    private State mState;
    public final static String stateKey = "com.example.recycleviewlist.fragment.stateKey";


    public static Intent newIntent(Context context, State state) {
        Intent instance = new Intent(context, WorkListFragment.class);
        instance.putExtra(stateKey, state);
        return instance;
    }


    public static Fragment newInstance(Intent intent) {
        Bundle args = new Bundle();
     /*   args.putString(nameKey, intent.getStringExtra(nameKey));
        args.putInt(numberKey, intent.getIntExtra(numberKey, 0));*/
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
        addAdapter();

        return view;
    }

    public void addAdapter() {
        mRecyclerView.setAdapter(new AdapterTask());
    }

    private class AdapterTask extends RecyclerView.Adapter {
        int mIntAll = 0;
        boolean mBooleanIsEmpty = true;

        {
            int position = 0;
            for (Task task : TaskRepository.getInstance().getTasks()) {
                if (task.getState() == mState) {
                    position++;
                }
            }
            mIntAll = position;
        }

        private class HolderTask extends RecyclerView.ViewHolder {
            TextView mTextViewState;
            TextView mTextViewName;
            LinearLayout mLinearLayoutList;

            public HolderTask(@NonNull View itemView) {
                super(itemView);
                mTextViewState = itemView.findViewById(id.textView_state);
                mTextViewName = itemView.findViewById(id.textView_name);
                mLinearLayoutList = itemView.findViewById(id.linear_list);

            }

            public void bind() {
                String textStr;
                int position = 0;
                for (@NonNull Task task : TaskRepository.getInstance().getTasks()) {
                    if (task.getState() == mState) {
                        position++;
                        if (position == getAdapterPosition()) {
                            mTextViewName.setText(task.getStringName());
                            mTextViewState.setText(String.valueOf(mState));
                            return;
                        }
                    }
                }
            }
        }

        private class HolderEmptyTask extends RecyclerView.ViewHolder {
            ImageView mImageViewEmptyTask;

            public HolderEmptyTask(@NonNull View itemView) {
                super(itemView);
                mImageViewEmptyTask = itemView.findViewById(id.imageView_empty_task);
            }

            public void bind() {
                switch (mState) {
                    case DONE: {
                        mImageViewEmptyTask.setImageResource(ic_action_done);
                        break;
                    }
                    case TODO: {
                        mImageViewEmptyTask.setImageResource(ic_action_todo);
                        break;
                    }
                    case DOING: {
                        mImageViewEmptyTask.setImageResource(ic_action_doing);
                        break;
                    }
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (mIntAll == 0) {
                return 0;
            } else {
                mBooleanIsEmpty = false;
                return 1;
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            if (viewType == 1) {
                View view = inflater.inflate(layout.list, parent, false);
                return new HolderTask(view);
            } else {
                View view = inflater.inflate(layout.image_list_empty_show, parent, false);
                return new HolderEmptyTask(view);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (mBooleanIsEmpty == true) {
                ((HolderEmptyTask) holder).bind();
            } else {
                ((HolderTask) holder).bind();
            }
        }

        @Override
        public int getItemCount() {
/*            if (mBooleanIsEmpty) {
                return 1;
            }*/
            return mIntAll+1;
        }
    }

}