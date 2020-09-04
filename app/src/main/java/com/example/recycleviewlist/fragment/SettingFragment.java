package com.example.recycleviewlist.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleviewlist.OnlineUser;
import com.example.recycleviewlist.R;
import com.example.recycleviewlist.database.task.TaskRepository;
import com.example.recycleviewlist.database.user.UserRepository;
import com.example.recycleviewlist.model.User;

import java.util.ArrayList;

public class SettingFragment extends Fragment {

    private RecyclerView mRecyclerView;
    UserAdapter mUserAdapter;

    public SettingFragment() {
    }

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
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
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        mRecyclerView = view.findViewById(R.id.RecyclerView_setting);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        UserAdapter.Callbacks callbacks = new UserAdapter.Callbacks() {
            @Override
            public void deleteUser(User user) {
                UserRepository.getInstance(getActivity()).deleteUser(user);
                mUserAdapter.setUsers(UserRepository.getInstance(getActivity()).getUsers());
                mUserAdapter.notifyDataSetChanged();
            }
        };
        ArrayList<User> users = (ArrayList<User>) UserRepository.getInstance(getActivity()).getUsers();
        users.remove(OnlineUser.newInstance().getOnlineUser());
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getStringName()
                            .equals(OnlineUser
                                    .newInstance()
                                    .getOnlineUser()
                                    .getStringName()
                            )) {
                users.remove(i);
            }
        }
        mUserAdapter = UserAdapter.newInstance(
                getActivity(),
                users,
                TaskRepository.getInstance(getActivity()).getListTasksROOT(),
                callbacks
        );
        //Toast.makeText(getActivity(), UserRepository.getInstance(getActivity()).getUsers().size()+"", Toast.LENGTH_SHORT).show();
        mRecyclerView.setAdapter(mUserAdapter);
        return view;
    }
}