package com.example.recycleviewlist.database.task;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.example.recycleviewlist.OnlineUser;
import com.example.recycleviewlist.model.State;
import com.example.recycleviewlist.model.Task;

import java.util.List;
import java.util.UUID;

public class TaskRepository implements Reposible {
    private static TaskRepository getInstance;
    private static TaskDataBase mDatabase;
    private static Context mContext;

    private TaskRepository() {
    }


    public static TaskRepository getInstance(Context context) {
        mContext = context;
        if (getInstance == null) {
            getInstance = new TaskRepository();
            mDatabase = Room.databaseBuilder(mContext, TaskDataBase.class, Task.COLS.NAME_DB_FILE)
                    .allowMainThreadQueries()
                    .build();
        }
        return getInstance;
    }

    @Override
    public void addTask(Task task) {
        mDatabase.getTaskDAO().addTask(task);
    }

    @Override
    public List<Task> getListTasks() {
        return mDatabase.getTaskDAO().getTaskListROOT(
                OnlineUser.newInstance().getOnlineUser().getUUID()
        );
    }

    @Override
    public List<Task> getListTasksROOT() {
        return mDatabase.getTaskDAO().getTaskListROOT();
    }

    @Override
    public List<Task> getListTasks(UUID userUUID) {
        return mDatabase.getTaskDAO().getTaskListROOT(userUUID);
    }

    @Override
    public Task getTask(UUID uuid) {
        return mDatabase.getTaskDAO().getTask(uuid);

    }

    @Override
    public List<Task> getListTasks(@NonNull State state) {
        UUID onlineUser = OnlineUser.newInstance().getOnlineUser().getUUID();
        if (OnlineUser.newInstance().isRoot()) {
            switch (state) {
                case DONE: {
                    return mDatabase.getTaskDAO().getTaskListROOT(State.DONE);
                }
                case TODO: {
                    return mDatabase.getTaskDAO().getTaskListROOT(State.TODO);
                }
                case DOING: {
                    return mDatabase.getTaskDAO().getTaskListROOT(State.DOING);
                }
            }
        } else {
            switch (state) {
                case DONE: {
                    return mDatabase.getTaskDAO().getTaskListROOT(onlineUser, State.DONE);
                }
                case TODO: {
                    return mDatabase.getTaskDAO().getTaskListROOT(onlineUser, State.TODO);
                }
                case DOING: {
                    return mDatabase.getTaskDAO().getTaskListROOT(onlineUser, State.DOING);
                }
            }
        }
        return null;
    }

    @Override
    public void setTask(Task task) {
        mDatabase.getTaskDAO().setTask(task);
    }

    @Override
    public void removeTask(UUID uuid) {
        mDatabase.getTaskDAO().removeTask(
                mDatabase.getTaskDAO().getTask(uuid)
        );
    }

    @Override
    public void removeTask(Task task) {
        mDatabase.getTaskDAO().removeTask(
                task
        );
    }

    @Override
    public void removeTasksOnlineUser() {
        mDatabase.getTaskDAO().removeTask(
                mDatabase.getTaskDAO().getTaskListROOT(
                        OnlineUser.newInstance().getOnlineUser().getUUID()
                )
        );
    }

    @Override
    public void removeTasksAllUsers() {
        mDatabase.getTaskDAO().removeTasks();
    }
}

interface Reposible {
    /**
     * CREAT
     * READ
     * Upgrade
     * DELETE
     */

    //delete
    void removeTasksAllUsers();

    void removeTasksOnlineUser();

    void removeTask(Task task);

    void removeTask(UUID uuid);

    //update
    void setTask(Task task);

    //read
    List<Task> getListTasks();

    List<Task> getListTasksROOT();

    List<Task> getListTasks(UUID user);

    Task getTask(UUID uuid);

    public List<Task> getListTasks(State state);

    //creat
    void addTask(Task task);

}
