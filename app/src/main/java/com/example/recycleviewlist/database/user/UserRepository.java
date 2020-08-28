package com.example.recycleviewlist.database.user;

import android.content.Context;

import androidx.room.Room;

import com.example.recycleviewlist.model.User;

import java.util.UUID;

interface UserReposible {
    //return null if it not exist return a user for online user
    public User isUserExist(String name, String password);

    public void deleteUser(User user);

    public Boolean addUser(User user);


}

public class UserRepository implements UserReposible {
    private static UserRepository mUserRepository;
    private static Context mContext;

    private static UserDataBase mDatabase;

    private UserRepository() {
    }

    public static UserRepository getInstance(Context context) {
        mContext = context;
        if (mDatabase == null) {
            mDatabase = Room.databaseBuilder(
                    mContext,
                    UserDataBase.class,
                    User.COLS.NAME_DB_FILE
            ).build();
            if (!mDatabase.getUserDio().isUserExist("root", "root"))
                mDatabase.getUserDio().addUser(new User("root", "root"));
        }
        return mUserRepository;
    }

    @Override
    public Boolean addUser(User user) {
        if (!mDatabase.getUserDio().isUserExist("root", "root"))
            mDatabase.getUserDio().addUser(user);
        return !mDatabase.getUserDio().isUserExist("root", "root");
    }

    @Override
    public void deleteUser(User user) {
        mDatabase.getUserDio().deleteUser(user);
    }

    @Override
    public User isUserExist(String name, String password) {
        UUID uuid = mDatabase.getUserDio().getUserUUID(name, password);
        if (uuid != null) {
            return new User(name, password, uuid);
        }
        return null;
    }
}
