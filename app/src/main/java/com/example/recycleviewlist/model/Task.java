package com.example.recycleviewlist.model;

import com.example.recycleviewlist.OnlineUser;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Task implements Serializable {
    private UUID mUUID;
    private UUID mIDUser;
    private State mState;
    private String mStringTitle;
    private String mStringDescription;
    private Date mDate;

    public Task(State state, String stringTitle) {
        mUUID = UUID.randomUUID();
        mState = state;
        mStringTitle = stringTitle;
        mDate = new Date();
        mIDUser = OnlineUser.newInstance().getOnlineUser().getUUID();
    }

    public Task(UUID UUID, UUID IDUser, State state, String stringTitle, String stringDescription) {
        mUUID = UUID;
        mIDUser = IDUser;
        mState = state;
        mStringTitle = stringTitle;
        mStringDescription = stringDescription;
        mDate = new Date();
    }

    public UUID getIDUser() {
        return mIDUser;
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

    public String getStringTitle() {
        return mStringTitle;
    }

    public void setStringTitle(String stringTitle) {
        mStringTitle = stringTitle;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public String getStringDescription() {
        return mStringDescription;
    }

    public void setStringDescription(String stringDescription) {
        mStringDescription = stringDescription;
    }

    public Date getDate() {
        return mDate;
    }

    public String getDateYMD(){
        String ymd = new SimpleDateFormat("yyyy-MM-dd").format(mDate);
        return ymd;
    }

    public String getTimeHMS(){
        String hms = new SimpleDateFormat("hh-mm-ss").format(mDate);
        return hms;
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
