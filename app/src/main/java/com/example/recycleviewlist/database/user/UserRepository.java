package com.example.recycleviewlist.database.user;

import android.content.Context;

import androidx.room.Room;

import com.example.recycleviewlist.model.User;

import java.util.Date;
import java.util.List;

interface UserReposible {
    void deleteUser(String userNmae);

    //return null if it not exist return a user for online user
    public User isUserExist(String name, String password);

    public void deleteUser(User user);

    public Boolean addUser(User user);


    List<User> getUsers();
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
            mUserRepository = new UserRepository();
            mDatabase = Room.databaseBuilder(
                    mContext,
                    UserDataBase.class,
                    User.COLS.NAME_DB_FILE
            )
                    .allowMainThreadQueries()
                    .build();
            if (mDatabase.getUserDio().getUserCount("root", "root") == 0) {
                User.Builder builder = new User.Builder();
                mDatabase.getUserDio().addUser(builder
                        .setName("root")
                        .setDate(new Date())
                        .setPassword("root")
                        .creat());
            }
        }
        return mUserRepository;
    }

    @Override
    public Boolean addUser(User user) {
        if (mDatabase.getUserDio().getUserCount(user.getStringName(), user.getStringPassword()) == 0) {
            if (user.getDate() == null) {
                user.setDate(new Date());
            }
            mDatabase.getUserDio().addUser(user);
        }
        return mDatabase.getUserDio().getUserCount("root", "root") != 0;
    }

    @Override
    public void deleteUser(User user) {
        mDatabase.getUserDio().deleteUser(user);
    }

    @Override
    public void deleteUser(String userNmae) {
        mDatabase.getUserDio().deleteUser(userNmae);
    }

    @Override
    public User isUserExist(String name, String password) {
        if (mDatabase.getUserDio().getUser(name, password) != null) {
            return mDatabase.getUserDio().getUser(name, password);
                   /* new User.Builder()
                            .setName(name)
                            .setPassword(password)
                            .setUUID(UUID.randomUUID())
                            .creat();*/
        }
        return null;
    }

    @Override
    public List<User> getUsers() {
        return mDatabase.getUserDio().getUsers();
    }
}
