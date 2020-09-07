package com.example.recycleviewlist.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleviewlist.R;
import com.example.recycleviewlist.model.Task;
import com.example.recycleviewlist.utils.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorkListAdapter extends RecyclerView.Adapter<WorkListAdapter.Holder> implements Filterable {


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

    @Override
    public Filter getFilter() {
        mList.clear();
        mCallbacks.getUpdateList();

        return  new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Task> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(mList);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Task item : mList) {
                        if (item.getStringSearch().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);

                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mList.clear();
                mList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };
    }


    public class Holder extends RecyclerView.ViewHolder {
        private TextView mTextViewName;
        private ImageView mImageViewTime;
        private ImageView mImageViewCalender;
        private ImageView mImageViewTaskDetail;
        private TextView mTextViewFirstStringName;
        private Task mTask;

        public Holder(@NonNull View itemView) {
            super(itemView);
            findView(itemView);
        }

        private void initView() {
            if (mTask.getHasImage()) {
                mTextViewFirstStringName.setVisibility(View.INVISIBLE);
                mImageViewTaskDetail.setBackground(null);
                mImageViewTaskDetail.setImageBitmap(
                        Image.loadBitMap(mTask, mContext)
                );
            } else {
                mTextViewFirstStringName.setVisibility(View.VISIBLE);
                String first = mTask.getStringTitle().equals("") || mTask.getStringTitle() == null ?
                        "T" :
                        String.valueOf(mTask.getStringTitle().charAt(0)).toUpperCase();
                mTextViewFirstStringName.setText(first);
            }
        }

        private void findView(@NonNull View itemView) {
            mTextViewName = itemView.findViewById(R.id.textView_name);
            mImageViewTime = itemView.findViewById(R.id.imageView_time);
            mImageViewCalender = itemView.findViewById(R.id.imageView_calandar);
            mImageViewTaskDetail = itemView.findViewById(R.id.imageView_detail_list);
            mTextViewFirstStringName = itemView.findViewById(R.id.textView_first_task_detail);

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
            initView();
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

        void getUpdateList();
    }
}



