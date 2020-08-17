package com.example.recycleviewlist.model;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Objects;
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
        mStringName = stringName;
        mStringPassword = stringUserName;
        mUUID = UUID.randomUUID();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(mUUID, user.mUUID);
    }
}
