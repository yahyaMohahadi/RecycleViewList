package com.example.recycleviewlist.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleviewlist.R;
import com.example.recycleviewlist.model.Task;
import com.example.recycleviewlist.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHoldeler> {

    List<User> mUsers;
    ArrayList<Task> mTasks;
    Context mContext;
    HashMap<UUID, Integer> mMapTaskNumbers;
    private Callbacks mCallbacks;

    public static UserAdapter newInstance(Context context,
                                          List<User> users,
                                          List<Task> allTasks,
                                          UserAdapter.Callbacks callbacks

    ) {
        UserAdapter adapter = new UserAdapter();
        adapter.setUsers(users);
        adapter.setContext(context);
        adapter.setTasks((ArrayList<Task>) allTasks);
        adapter.setCallbacks(callbacks);

        return adapter;
    }

    private UserAdapter() {
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void setCallbacks(Callbacks callbacks) {
        mCallbacks = callbacks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        mTasks = tasks;
    }

    public void setUsers(List<User> users) {
        mUsers = users;
    }

    @NonNull
    @Override
    public UserHoldeler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.lise_user, parent, false);
        return new UserAdapter.UserHoldeler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHoldeler holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class UserHoldeler extends RecyclerView.ViewHolder {
        private TextView mTextViewUserName;
        private TextView mTextViewUserPassword;
        private TextView mTextViewUserTasks;
        private TextView mTextViewUserFirstTimeLogin;
        private ImageButton mImageButtonDeleteUser;

        public UserHoldeler(@NonNull View itemView) {
            super(itemView);
            findView(itemView);
        }

        private void findView(View view) {
            mTextViewUserName = view.findViewById(R.id.textView_seting_user);
            mTextViewUserPassword = view.findViewById(R.id.textView_setting_password);
            mTextViewUserTasks = view.findViewById(R.id.textView_setting_tasks);
            mTextViewUserFirstTimeLogin = view.findViewById(R.id.textView_setting_date);
            mImageButtonDeleteUser = view.findViewById(R.id.imageButton_setting_delete_user);
        }


        public void bind(final int position) {
            mTextViewUserName.setText(mUsers.get(position).getStringName());
            mTextViewUserPassword.setText(mUsers.get(position).getStringPassword());
            mTextViewUserTasks.setText(String.valueOf(getNumberOfTasks(position)));
            mTextViewUserFirstTimeLogin.setText(mUsers.get(position).getDate().toString());
            mImageButtonDeleteUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallbacks.deleteUser(mUsers.get(position));
                }
            });
        }

        private int getNumberOfTasks(int position) {
            int numberOfTasks = 0;
            for (int i = 0; i < mTasks.size(); i++) {
                if (mTasks.get(i).getIDUser().equals(mUsers.get(position).getUUID())) {
                    numberOfTasks++;
                    mTasks.remove(mTasks.get(i));
                }
            }
            return numberOfTasks;
        }
    }

    public interface Callbacks {
        void deleteUser(User user);
    }
}
