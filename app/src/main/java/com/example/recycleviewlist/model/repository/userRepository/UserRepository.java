package com.example.recycleviewlist.model.repository.userRepository;

import com.example.recycleviewlist.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static UserRepository mUserRepository;
    List<User> mUsers = new ArrayList<>();

    public static UserRepository getInstance() {
        if (mUserRepository == null) {
            mUserRepository = new UserRepository();
            mUserRepository.addUser(new User("root", "root"));
        }
        return mUserRepository;
    }

    private UserRepository() {
    }

    public void addUser(User user) {
        mUsers.add(user);
    }

    public User getUser(int index) {
        return mUsers.get(index);
    }

    public void deleteUser(User user) {
        mUsers.remove(user);
    }

    public List<User> getList() {
        return mUsers;
    }

    public boolean isUserExist(String userName,String password){
        for (User user: mUsers) {
            if (user.getStringName().equals(userName)&& user.getStringPassword().equals(password)){
                return true;
            }
        }
        return false;
    }
}
