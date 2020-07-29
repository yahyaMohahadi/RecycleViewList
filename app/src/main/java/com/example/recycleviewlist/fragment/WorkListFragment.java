package com.example.recycleviewlist.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
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
import com.example.recycleviewlist.WorkListActivity;


public class WorkListFragment extends Fragment {


    private int mIntNumber;
    private String mStringName;
    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work_list, container, false);
        mStringName = (String) getArguments().getString(WorkListActivity.nameKey);
        mIntNumber = (int) getArguments().getInt(WorkListActivity.numberKey);
        mRecyclerView = view.findViewById(R.id.recucleView_work);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new AdapterTask());

        return view;
    }

    private class AdapterTask extends RecyclerView.Adapter {

        private class HolderTask extends RecyclerView.ViewHolder {
            TextView mTextViewNumbre;
            TextView mTextViewName;
            LinearLayout mLinearLayoutList;

            @SuppressLint("ResourceAsColor")
            public HolderTask(@NonNull View itemView) {
                super(itemView);
                mTextViewNumbre = itemView.findViewById(R.id.textView_state);
                mTextViewName = itemView.findViewById(R.id.textView_name);
                mLinearLayoutList = itemView.findViewById(R.id.linear_list);

            }

            @SuppressLint("ResourceAsColor")
            public void bind() {
                mTextViewName.setText(mStringName);
                mTextViewNumbre.setText(1 + getAdapterPosition() + "");
                Log.d("salam", getAdapterPosition() + "");
                if ((getAdapterPosition() % 2) == 0) {
                    itemView.setBackgroundColor(R.color.color_bachground1t);
                } else {
                    itemView.setBackgroundColor(R.color.color_bachground2t);
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
            return mIntNumber;
        }
    }
}