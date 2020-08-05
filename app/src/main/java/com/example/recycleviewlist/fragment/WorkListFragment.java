package com.example.recycleviewlist.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleviewlist.R;
import com.example.recycleviewlist.model.State;
import com.example.recycleviewlist.model.Task;
import com.example.recycleviewlist.model.TaskRepository;


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
        View view = inflater.inflate(R.layout.fragment_work_list, container, false);
        mState = (State) getArguments().getSerializable(stateKey);
        mRecyclerView = view.findViewById(R.id.recucleView_work);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        addAdapter();

        return view;
    }

    public void addAdapter() {
        mRecyclerView.setAdapter(new AdapterTask());
    }

    private class AdapterTask extends RecyclerView.Adapter {
        int mIntAll = 0;

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
                mTextViewState = itemView.findViewById(R.id.textView_state);
                mTextViewName = itemView.findViewById(R.id.textView_name);
                mLinearLayoutList = itemView.findViewById(R.id.linear_list);

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

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list, parent, false);
            return new HolderTask(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((HolderTask) holder).bind();
        }

        @Override
        public int getItemCount() {
            return mIntAll;
        }
    }

}