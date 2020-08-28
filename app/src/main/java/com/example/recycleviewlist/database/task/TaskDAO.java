package com.example.recycleviewlist.database.task;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.recycleviewlist.model.State;
import com.example.recycleviewlist.model.Task;

import java.util.List;
import java.util.UUID;

@Dao
public interface TaskDAO {

    @Insert
    void addTask(Task task);

    @Query("SELECT * FROM TASKS WHERE uuid = :uuid")
    Task getTask(UUID uuid);


    @Update
    void setTask(Task task);


    @Delete
    void removeTask(Task task);

    @Delete
    void removeTask(List<Task> tasks);


    @Query("DELETE FROM TASKS")
    void removeTasks();

    @Query("SELECT * FROM TASKS ")
    List<Task> getTaskList();

    @Query("SELECT * FROM TASKS WHERE uuidUser = :uuidUser")
    List<Task> getTaskList(UUID uuidUser);

    @Query("SELECT * FROM TASKS WHERE uuidUser = :uuidUser AND state = :state ")
    List<Task> getTaskList(UUID uuidUser, State state);


}
