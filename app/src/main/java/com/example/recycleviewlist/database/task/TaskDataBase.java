package com.example.recycleviewlist.database.task;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.recycleviewlist.utils.Convertors;
import com.example.recycleviewlist.model.Task;

@Database(entities = Task.class, version = Task.COLS.VERSION_DB_USER)
@TypeConverters({Convertors.class})
public abstract class TaskDataBase extends RoomDatabase {
    public abstract TaskDAO getTaskDAO();
}
