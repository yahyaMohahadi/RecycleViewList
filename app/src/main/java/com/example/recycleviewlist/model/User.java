package com.example.recycleviewlist.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import java.io.Serializable;
import java.util.UUID;


@Entity(tableName = User.COLS.TABLE_NAME)
public class User implements Serializable {

    @ColumnInfo(name = COLS.CUL_NAME)
    private String mStringName;

    @ColumnInfo(name = COLS.CUL_PASSWORD)
    private String mStringPassword;

    @ColumnInfo(name = COLS.CUL_UUID)
    private UUID mUUID;


    public void setUUID(UUID UUID) {
        mUUID = UUID;
    }

    public User(@NonNull String stringName, @NonNull String stringUserName) {
        this(stringName, stringUserName, UUID.randomUUID());
    }

    public User(@NonNull String stringName, @NonNull String stringUserName, @NonNull UUID uuid) {
        mStringName = stringName;
        mStringPassword = stringUserName;
        mUUID =uuid;
    }

    public String getStringName() {
        return mStringName;
    }

    public void setStringName(String stringName) {
        mStringName = stringName;
    }

    public String getStringPassword() {
        return mStringPassword;
    }

    public void setStringPassword(String stringPassword) {
        mStringPassword = stringPassword;
    }

    public UUID getUUID() {
        return mUUID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return mUUID.equals(user.mUUID);
    }

    public static class COLS {
        public static final int VERSION_DB_USER = 1;
        public static final String NAME_DB_FILE = "database_user.db";
        public static final String TABLE_NAME = "users";
        public static final String CUL_NAME = "UserName";
        public static final String CUL_PASSWORD = "Password";
        public static final String CUL_UUID = "uuid";
    }
}
