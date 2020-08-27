package com.example.recycleviewlist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDBRepository extends SQLiteOpenHelper {
    public static final int VERSION_DB_USER = 1;
    public static final String NAME_DB_USER = "database_user.db";
    public static final String QUERY = "CREATE TABLE " + COLS.TABLE_NAME + "( " +
            COLS.CUL_ID + " INTEGER PRIMARY KEY, " +
            COLS.CUL_NAME + " TEXT NOT NULL, " +
            COLS.CUL_PASSWORD + " TEXT NOT NULL," +
            COLS.CUL_UUID + " INTEGER NOT NULL )";

    public UserDBRepository(@Nullable Context context) {
        super(context, NAME_DB_USER, null, VERSION_DB_USER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static class COLS {
        public static final String TABLE_NAME = "users";
        public static final String CUL_ID = "ID";
        public static final String CUL_NAME = "UserName";
        public static final String CUL_PASSWORD = "Password";
        public static final String CUL_UUID = "uuid";
    }
}
