package com.example.recycleviewlist.database.user;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.recycleviewlist.model.User;

import java.util.UUID;

@Dao
public interface UserDAO {

    @Insert
    void addUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM USERS WHERE Password = :password AND UserName = :name")
    boolean isUserExist(String name, String password);

    @Query("SELECT * FROM USERS WHERE Password = :password AND UserName = :name")
    UUID getUserUUID(String name, String password);
}
