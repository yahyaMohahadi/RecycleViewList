package com.example.recycleviewlist.model.repository.userRepository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.recycleviewlist.database.UserDBRepository;
import com.example.recycleviewlist.model.User;

public class UserRepository implements UserReposible {
    private static UserRepository mUserRepository;
    private static Context mContext;

    private static SQLiteDatabase mDatabase;

    public static UserRepository getInstance(Context context) {
        mContext = context;
        if (mDatabase == null) {
            UserDBRepository mUserDBRepository = new UserDBRepository(mContext);
            mUserRepository = new UserRepository();
            mDatabase = mUserDBRepository.getWritableDatabase();
            mUserRepository.addUser(new User("root", "root"));
         /*   for (int i = 0; i < 100; i++) {
                User user = new User(i + "", new Random().nextInt(10000000) + "");

                mUserRepository.addUser(user);
            }*/
        }
        return mUserRepository;
    }

    private UserRepository() {
    }

    @Override
    public void addUser(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserDBRepository.COLS.CUL_NAME, user.getStringName());
        contentValues.put(UserDBRepository.COLS.CUL_PASSWORD, user.getStringPassword());
        contentValues.put(UserDBRepository.COLS.CUL_UUID, user.getUUID().toString());
        mDatabase.insert(UserDBRepository.COLS.TABLE_NAME, null, contentValues);
    }

    @Override
    public Boolean deleteUser(User user) {
        return mDatabase.delete(UserDBRepository.COLS.TABLE_NAME,
                UserDBRepository.COLS.CUL_UUID + " = ? ",
                new String[]{user.getUUID().toString()})
                == 1 ? true : false;
    }

    //return null if it not exist return a user for online user
    @Override
    public User isUserExist(String name, String password) {
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + UserDBRepository.COLS.TABLE_NAME
                        + " WHERE " + UserDBRepository.COLS.CUL_NAME +
                        " = ? " + "and " + UserDBRepository.COLS.CUL_PASSWORD + " =? ",
                new String[]{name, password});
        boolean isExist = cursor.getCount() == 1 ? true : false;
        cursor.moveToFirst();
        if (isExist) {
            String id = cursor.getString(cursor.getColumnIndex(UserDBRepository.COLS.CUL_UUID));
            cursor.close();
            return new User(name, password, id);

        } else {
            cursor.close();
            return null;
        }
    }
}
interface UserReposible{
    //return null if it not exist return a user for online user
    public User isUserExist(String name, String password);
    public Boolean deleteUser(User user);
    public void addUser(User user);


}
