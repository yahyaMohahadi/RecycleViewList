package com.example.recycleviewlist.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleviewlist.R;
import com.example.recycleviewlist.model.Task;

import java.util.List;
import java.util.UUID;

public class WorkListAdapter extends RecyclerView.Adapter<WorkListAdapter.Holder> {


    private Callbacks mCallbacks;
    private List<Task> mList;
    private Context mContext;

    private WorkListAdapter() {
    }

    public static WorkListAdapter newInstance(@NonNull Context context, @NonNull List<Task> tasks) {
        WorkListAdapter adapter = new WorkListAdapter();
        adapter.setList(tasks);
        adapter.setContext(context);
        return adapter;
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
        return mList.size();
    }

    public List<Task> getList() {
        return mList;
    }

    public void setList(List<Task> list) {
        mList = list;
    }

    public void setCallbacks(Callbacks callbacks) {
        mCallbacks = callbacks;
    }

    public void setContext(Context context) {
        mContext = context;
    }


    public class Holder extends RecyclerView.ViewHolder {
        private TextView mTextViewName;
        private ImageView mImageViewTime;
        private ImageView mImageViewCalender;
        private Task mTask;

        public Holder(@NonNull View itemView) {
            super(itemView);
            findView(itemView);

        }

        private void findView(@NonNull View itemView) {
            mTextViewName = itemView.findViewById(R.id.textView_name);
            mImageViewTime = itemView.findViewById(R.id.imageView_time);
            mImageViewCalender = itemView.findViewById(R.id.imageView_calandar);

        }

        private void setOnCklick(View itemView) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallbacks.itemCall(mTask.getUUID());

                }
            });
            mImageViewTime.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mCallbacks.timeCall(mTask.getUUID());

                        }
                    }
            );
            mImageViewCalender.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mCallbacks.calenderCall(mTask.getUUID());

                        }
                    }
            );

        }

        public void bind(int position) {
            mTask = mList.get(position);
            setOnCklick(itemView);
            if (mTask != null) {
                mTextViewName.setText(mTask.getStringTitle());
            }
        }
    }


    public interface Callbacks {

        void itemCall(UUID uuid);

        void calenderCall(UUID uuid);

        void timeCall(UUID uuid);
    }
}


/*
    private ImageButton mImageButtonShare;
    private ImageButton mImageButtonFlder;
    private ImageButton mImageButtonCamera;

    private ImageButton mImageButtonShare;
    private ImageButton mImageButtonFlder;
    private ImageButton mImageButtonCamera;*/

        /*    mImageButtonShare = itemView.findViewById(R.id.button_share);
                    mImageButtonFlder= itemView.findViewById(R.id.button_folder);
                    mImageButtonCamera= itemView.findViewById(R.id.button_camera);*/


/*
            mImageButtonShare.setOnClickListener(
                    new View.OnClickListener() {
@Override
public void onClick(View view) {
        //todo make manage
        }
        }
        );
        mImageButtonFlder.setOnClickListener(
        new View.OnClickListener() {
@Override
public void onClick(View view) {
        //todo make manage
        }
        }
        );
        mImageButtonCamera.setOnClickListener(
        new View.OnClickListener() {
@Override
public void onClick(View view) {
        //todo make manage
        }
        }
        );*/
