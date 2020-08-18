package com.example.recycleviewlist.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    String mStringName;
    String mStringPassword;
    UUID mUUID;

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

    public User(@NonNull String stringName, @NonNull String stringUserName) {
        this(stringName, stringUserName, UUID.randomUUID().toString());
    }

    public User(@NonNull String stringName, @NonNull String stringUserName, @NonNull String uuid) {
        mStringName = stringName;
        mStringPassword = stringUserName;
        mUUID = UUID.fromString(uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return mUUID.equals(user.mUUID);
    }
}
