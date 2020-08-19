package com.example.recycleviewlist.model.repository.taskRepository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.recycleviewlist.OnlineUser;
import com.example.recycleviewlist.database.TaskDBReposytory;
import com.example.recycleviewlist.model.State;
import com.example.recycleviewlist.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskRepository implements Reposible {
    private static TaskRepository getInstance;
    private static SQLiteDatabase mDatabase;
    private static Context mContext;
    private static UUID mIDUser;
    private List<Task> mTasks = new ArrayList<>();

    public static TaskRepository getInstance(Context context, UUID uuidUser) {
        mContext = context;
        mIDUser = uuidUser;
        if (getInstance == null) {
            getInstance = new TaskRepository();
            mDatabase = new TaskDBReposytory(context).getWritableDatabase();
            getInstance.getTasks();
        }
        return getInstance;
    }

    private TaskRepository() {
    }

    @Override
    public void addTask(Task task) {
        ContentValues contentValues = getContentValuesTask(task);
        mDatabase.insert(TaskDBReposytory.COLS.TABLE_NAME, null, contentValues);
        updateListTask();
    }

    @Override
    public Task getTask(UUID uuid) {
        for (Task task : mTasks) {
            if (task.getUUID().equals(uuid)) {
                return task;
            }
        }
        return null;
    }

    @Override
    public int getPosition(UUID uuid) {
        return mTasks.indexOf(getTask(uuid));
    }


    private void getTasks() {
        List<Task> tasks = new ArrayList<>();
        Cursor cursor;
        if (!OnlineUser.newInstance().getOnlineUser().equals(OnlineUser.mUserRoot)) {
            cursor = mDatabase.rawQuery("SELECT * FROM " + TaskDBReposytory.COLS.TABLE_NAME +
                            " WHERE " + TaskDBReposytory.COLS.CUL_UUID_USER + " =? ",
                    new String[]{OnlineUser.newInstance().getOnlineUser().getUUID().toString()});
        } else {
            cursor = mDatabase.rawQuery("SELECT * FROM " + TaskDBReposytory.COLS.TABLE_NAME,
                    new String[]{});
        }
        Cursor cursor1 = mDatabase.rawQuery("SELECT * FROM " + TaskDBReposytory.COLS.TABLE_NAME,
                new String[]{});
//        Log.d("QQQ",cursor1.getCount()+"");
//        Log.d("QQQ",OnlineUser.newInstance().getOnlineUser().getUUID().toString());
//        Log.d("QQQ",cursor.getCount()+"whre curcer");
        cursor.moveToFirst();
        if (cursor.getCount()!=0) {
            while (!cursor.isAfterLast()) {
                Task task = getTaskFromCursor(cursor);
                tasks.add(task);
                cursor.moveToNext();
            }
        }
        cursor.close();
//        Log.d("QQQ",tasks.toString());
        mTasks = tasks;
    }

    private Task getTaskFromCursor(Cursor cursor) {
        UUID uuid = UUID.fromString(
                cursor.getString(cursor.getColumnIndex(TaskDBReposytory.COLS.CUL_UUID)));
        UUID userUuid = UUID.fromString(
                cursor.getString(cursor.getColumnIndex(TaskDBReposytory.COLS.CUL_UUID_USER)));
        State state = State.valueOf(
                cursor.getString(cursor.getColumnIndex(TaskDBReposytory.COLS.CUL_STATE)));
        String title = cursor.getString(cursor.getColumnIndex(TaskDBReposytory.COLS.CUL_TITLE));
        String description = cursor.getString(cursor.getColumnIndex(TaskDBReposytory.COLS.CUL_DESCRIPTION));
        return new Task(uuid, userUuid, state, title, description);
    }

    @Override
    public List<Task> getListTasks() {
        if (mTasks == null) {
            getTasks();
        }
        return mTasks;
    }

    private void updateListTask() {
        getTasks();
    }

    @Override
    public void setTask(Task task) {
        ContentValues contentValues = getContentValuesTask(task);
        mDatabase.update(TaskDBReposytory.COLS.TABLE_NAME, contentValues,
                TaskDBReposytory.COLS.CUL_UUID_USER +
                        " =? ", new String[]{task.getIDUser().toString()});
        updateListTask();
    }


    private ContentValues getContentValuesTask(Task task) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskDBReposytory.COLS.CUL_UUID, task.getUUID().toString());
        contentValues.put(TaskDBReposytory.COLS.CUL_STATE, task.getState().toString());
        contentValues.put(TaskDBReposytory.COLS.CUL_TITLE, task.getStringTitle());
        contentValues.put(TaskDBReposytory.COLS.CUL_DATE, task.getDate().toString());
        contentValues.put(TaskDBReposytory.COLS.CUL_DESCRIPTION, task.getStringDescription());
        contentValues.put(TaskDBReposytory.COLS.CUL_UUID_USER, task.getIDUser().toString());
        return contentValues;
    }

    @Override
    public void removeTask(UUID uuid) {
        mDatabase.delete(TaskDBReposytory.COLS.TABLE_NAME, TaskDBReposytory.COLS.CUL_UUID +
                " =? ", new String[]{uuid.toString()
        });
        updateListTask();
    }

    @Override
    public void removeTasks() {
        mDatabase.delete(TaskDBReposytory.COLS.TABLE_NAME, null, null);
        updateListTask();
    }

    @Override
    public int getNumberOfStats(State state) {
        int position = 0;
        for (Task task : mTasks) {
            if (task.getState() == state) {
                position++;
            }
        }
        return position;
    }

    @Override
    public Task gerNumberOfTaskWithState(int number, State state) {
        int position = 0;
        for (Task task : mTasks) {
            if (task.getState() == state) {
                position++;
            }
            if (position == number) {
                return task;
            }
        }
        return null;
    }

}

interface Reposible {
    /**
     * CREAT
     * READ
     * Upgrade
     * DELETE
     */

    void addTask(Task task);

    Task getTask(UUID uuid);

    int getPosition(UUID uuid);

    void setTask(Task task);

    void removeTask(UUID uuid);

    void removeTasks();

    int getNumberOfStats(State state);

    Task gerNumberOfTaskWithState(int number, State state);

    public List<Task> getListTasks();

    // void setDate(LocaleData localeData);
}