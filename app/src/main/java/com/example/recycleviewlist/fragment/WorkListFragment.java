package com.example.recycleviewlist.fragment;

import android.annotation.SuppressLint;
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

import java.util.Random;


public class WorkListFragment extends Fragment {


    private int mIntNumber;
    private String mStringName;
    private RecyclerView mRecyclerView;
    private Random mRandom = new Random();
    private State mState ;
    public final static String nameKey = "com.example.recycleviewlist.fragment.nameKey";
    public final static String numberKey = "com.example.recycleviewlist.fragment.numberKey";
    public final static String stateKey = "com.example.recycleviewlist.fragment.stateKey";


    public static Intent newIntent(Context context, int number, String name , State state) {
        Intent instance = new Intent(context, WorkListFragment.class);
        instance.putExtra(nameKey, name);
        instance.putExtra(numberKey, number);
        instance.putExtra(stateKey, state);
        return instance;

    }


    public static Fragment newInstance(Intent intent) {
        Bundle args = new Bundle();
        args.putString(nameKey, intent.getStringExtra(nameKey));
        args.putInt(numberKey, intent.getIntExtra(numberKey, 0));
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
        mStringName = (String) getArguments().getString(nameKey);
        mIntNumber = (int) getArguments().getInt(numberKey);
        mState = (State) getArguments().getSerializable(stateKey);
        mRecyclerView = view.findViewById(R.id.recucleView_work);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new AdapterTask());

        return view;
    }

    private class AdapterTask extends RecyclerView.Adapter {

        private class HolderTask extends RecyclerView.ViewHolder {
            TextView mTextViewState;
            TextView mTextViewName;
            LinearLayout mLinearLayoutList;

            @SuppressLint("ResourceAsColor")
            public HolderTask(@NonNull View itemView) {
                super(itemView);
                mTextViewState = itemView.findViewById(R.id.textView_state);
                mTextViewName = itemView.findViewById(R.id.textView_name);
                mLinearLayoutList = itemView.findViewById(R.id.linear_list);

            }

            public void bind() {
                mTextViewName.setText(mStringName);
                mTextViewState.setText(String.valueOf(mState));
                //color
          /*      if ((getAdapterPosition() % 2) == 0) {
                    itemView.setBackgroundResource(R.color.color_bachground1);
                } else {
                    itemView.setBackgroundResource(R.color.color_bachground2);
                }*/
            }

          /*  private void setRandomState() {

                String state = "";
                switch (mRandom.nextInt(3)) {
                    case 0:
                        state = String.valueOf(State.DOING);

                        break;
                    case 1:
                        state = String.valueOf(State.DONE);
                        break;
                    case 2:
                        state = String.valueOf(State.TODO);
                        break;
                }
                mTextViewState.setText(state);
            }*/
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