package com.example.recycleviewlist.database.user;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.recycleviewlist.utils.Convertors;
import com.example.recycleviewlist.model.User;

@Database(entities = User.class, version = User.COLS.VERSION_DB_USER)
@TypeConverters({Convertors.class})
public abstract class UserDataBase extends RoomDatabase {
    public abstract UserDAO getUserDio();
}
