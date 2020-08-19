package com.example.recycleviewlist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TaskDBReposytory extends SQLiteOpenHelper {
    public static final int VERSION_DB_USER = 1;
    public static final String NAME_DB_USER = "tasks.db";
    public static final String QUERY = "CREATE TABLE " + TaskDBReposytory.COLS.TABLE_NAME + "( " +
            TaskDBReposytory.COLS.CUL_ID + " INTEGER PRIMARY KEY, " +
            TaskDBReposytory.COLS.CUL_TITLE + " TEXT NOT NULL, " +
            TaskDBReposytory.COLS.CUL_STATE + " TEXT NOT NULL,"+
            TaskDBReposytory.COLS.CUL_DESCRIPTION + " TEXT ,"+
            TaskDBReposytory.COLS.CUL_DATE+ " TEXT ,"+
            TaskDBReposytory.COLS.CUL_UUID+ " TEXT NOT NULL,"+
            TaskDBReposytory.COLS.CUL_UUID_USER + " TEXT NOT NULL )";

    public static class COLS {
        public static final String TABLE_NAME = "tasks";
        public static final String CUL_ID = "ID";
        public static final String CUL_STATE = "state";
        public static final String CUL_TITLE = "title";
        public static final String CUL_DESCRIPTION = "description";
        public static final String CUL_DATE = "date";
        public static final String CUL_UUID = "uuid";
        public static final String CUL_UUID_USER = "uuidUser";
    }

    public TaskDBReposytory(@Nullable Context context) {
        super(context, NAME_DB_USER, null, VERSION_DB_USER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
