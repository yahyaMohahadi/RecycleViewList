package com.example.recycleviewlist.model;

import java.util.UUID;

public class Task {
    private UUID mUUID;
    private State mState;
    private String mStringName;

    public Task(State state, String stringName) {
        mUUID = UUID.randomUUID();
        mState = state;
        mStringName = stringName;
    }

    public Task() {
        this(State.DONE,"maktab default");
    }

    public State getState() {
        return mState;
    }

    public void setState(State state) {
        mState = state;
    }

    public String getStringName() {
        return mStringName;
    }

    public void setStringName(String stringName) {
        mStringName = stringName;
    }

    public UUID getUUID() {
        return mUUID;
    }
}
