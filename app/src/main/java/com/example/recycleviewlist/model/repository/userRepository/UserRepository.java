package com.example.recycleviewlist.model.repository.userRepository;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.recycleviewlist.database.UserDBRepository;
import com.example.recycleviewlist.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserRepository {
    private static UserRepository mUserRepository;
    public static List<User> mUsers = new ArrayList<>();
    private static Context mContext;
    private static UserDBRepository mUserDBRepository = new UserDBRepository(mContext);
    private static SQLiteDatabase mDatabase;

    public static SQLiteDatabase getInstance(Context context) {
        mContext = context;
        if (mDatabase == null) {
            mDatabase = mUserDBRepository.getWritableDatabase();
            mUsers.add(new User("root", "root"));
        }
        return mDatabase;
    }

    private UserRepository() {
        for (int i = 0; i < 100; i++) {
            User user = new User(i + "", new Random().nextInt(10000000) + "");
            addUser(user);
        }
    }

    public void addUser(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserDBRepository.COLS.CUL_NAME, user.getStringName());
        contentValues.put(UserDBRepository.COLS.CUL_PASSWORD, user.getStringPassword());
        contentValues.put(UserDBRepository.COLS.CUL_UUID, user.getUUID().toString());
        mDatabase.insert(UserDBRepository.COLS.TABLE_NAME, null, contentValues);
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

    public boolean isUserExist(String userName, String password) {
        for (User user : mUsers) {
            if (user.getStringName().equals(userName) && user.getStringPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}
