package com.example.recycleviewlist.database.user;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.recycleviewlist.model.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    void addUser(User user);

    @Query("DELETE FROM users WHERE UserName = :userName ")
    void deleteUser(String userName);

    @Delete
    void deleteUser(User user);

    @Query("SELECT COUNT(*) FROM USERS WHERE Password = :password AND UserName = :name")
    int getUserCount(String name, String password);

    @Query("SELECT * FROM USERS WHERE Password = :password AND UserName = :name limit 1")
    User getUser(String name, String password);

    @Query("SELECT * FROM USERS")
    List<User> getUsers();
}
