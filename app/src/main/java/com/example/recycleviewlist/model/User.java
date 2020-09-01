package com.example.recycleviewlist.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.UUID;


@Entity(tableName = User.COLS.TABLE_NAME)
public class User implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Long primery;

    @ColumnInfo(name = COLS.CUL_NAME)
    private String mStringName;

    @ColumnInfo(name = COLS.CUL_PASSWORD)
    private String mStringPassword;

    @ColumnInfo(name = COLS.CUL_UUID)
    private UUID mUUID;

    public User() {
    }

    public static class Builder {
        public Builder() {
        }

        private String mStringName;
        private String mStringPassword;
        private UUID mUUID = UUID.randomUUID();

        public Builder setName(String stringName) {
            this.mStringName = stringName;
            return this;
        }

        public Builder setPassword(String stringPassword) {
            this.mStringPassword = stringPassword;
            return this;
        }

        public Builder setUUID(UUID uuid) {
            this.mUUID = uuid;
            return this;
        }

        public User creat() {
            User user = new User();
            user.mUUID = this.mUUID;
            user.mStringPassword = this.mStringPassword;
            user.mStringName = this.mStringName;
            return user;
        }

    }

    public Long getPrimery() {
        return primery;
    }

    public void setPrimery(Long primery) {
        this.primery = primery;
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

    public void setUUID(UUID UUID) {
        mUUID = UUID;
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
