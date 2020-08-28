package com.example.recycleviewlist.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleviewlist.R;
import com.example.recycleviewlist.database.task.TaskRepository;
import com.example.recycleviewlist.fragment.pickers.DatePickerFragment;
import com.example.recycleviewlist.fragment.pickers.Picker;
import com.example.recycleviewlist.fragment.pickers.TimePickerFragment;
import com.example.recycleviewlist.model.State;
import com.example.recycleviewlist.model.StateHandler;
import com.example.recycleviewlist.model.Task;

public class WorkListAdapter extends RecyclerView.Adapter<WorkListAdapter.Holder> {

    public static final int REQUEST_COD_EDIT = 1;
    public static final int REQUEST_CODE_DATEPICKER = 3;
    public static final int REQUEST_CODE_TIME_PICKER = 4;
    private Context mContext;
    private State mState;
    private Fragment mFragment;

    private WorkListAdapter() {
    }

    public static WorkListAdapter newInstance(Context context, Fragment fragment, State state) {
        WorkListAdapter adapter = new WorkListAdapter();
        adapter.setContext(context);
        adapter.setState(state);
        adapter.setFragment(fragment);
        return new WorkListAdapter();
    }

    public void setState(State state) {
        mState = state;
    }

    public void setFragment(Fragment fragment) {
        mFragment = fragment;
    }

    public void setContext(Context sContext) {
        this.mContext = sContext;
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView mTextViewName;
        private ConstraintLayout mConstraintLayout;
        private ImageView mImageViewTime;
        private ImageView mImageViewCalender;

        public Holder(@NonNull View itemView) {
            super(itemView);
            findView(itemView);
            setOnCklick(itemView);
        }

        private void findView(@NonNull View itemView) {
            mTextViewName = itemView.findViewById(R.id.textView_name);
            mConstraintLayout = itemView.findViewById(R.id.constrain_list);
            mImageViewTime = itemView.findViewById(R.id.imageView_time);
            mImageViewCalender = itemView.findViewById(R.id.imageView_calandar);
        }

        private void setOnCklick(View itemView) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment fragmentAdd = TaskHandleDialog.newInstance(StateHandler.EDIT,
                            TaskRepository.getInstance(mContext).getListTasks(mState).get(getItemCount()));
                    fragmentAdd.setTargetFragment(mFragment, REQUEST_COD_EDIT);
                    fragmentAdd.show(mFragment.getFragmentManager(), "TAG");
                }
            });
            mImageViewTime.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Picker time = (Picker) TimePickerFragment.newInstance(
                                    TaskRepository.getInstance(mContext).getListTasks(mState).get(getItemCount())
                            );
                            time.setTargetFragment(mFragment, REQUEST_CODE_TIME_PICKER);
                            time.show(mFragment.getFragmentManager(), "tag");
                        }
                    }
            );
            mImageViewCalender.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Picker calander = (Picker) DatePickerFragment.newInstance(
                                    TaskRepository.getInstance(mContext).getListTasks(mState).get(getItemCount())
                            );
                            calander.setTargetFragment(mFragment, REQUEST_CODE_DATEPICKER);
                            calander.show(mFragment.getFragmentManager(), "tag");
                        }
                    }
            );
        }

        public void bind(int position) {
            Task task = TaskRepository.getInstance(mContext).getListTasks(mState).get(position);
            if (task != null) {
                mTextViewName.setText(task.getStringTitle());
            }
        }
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return TaskRepository.getInstance(mContext).getListTasks(mState).size();
    }
}
