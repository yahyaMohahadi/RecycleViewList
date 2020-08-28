package com.example.recycleviewlist.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.example.recycleviewlist.OnlineUser;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


@Entity(tableName = Task.COLS.TABLE_NAME)
public class Task implements Serializable {

    @ColumnInfo(name = COLS.CUL_UUID)
    private UUID mUUID;

    @ColumnInfo(name = COLS.CUL_UUID_USER)
    private UUID mIDUser;

    @ColumnInfo(name = COLS.CUL_STATE)
    private State mState;

    @ColumnInfo(name = COLS.CUL_TITLE)
    private String mStringTitle;

    @ColumnInfo(name = COLS.CUL_DESCRIPTION)
    private String mStringDescription;

    @ColumnInfo(name = COLS.CUL_DATE)
    private Date mDate;

    public void setUUID(UUID UUID) {
        mUUID = UUID;
    }

    public void setIDUser(UUID IDUser) {
        mIDUser = IDUser;
    }

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

    public Task() {
        this(State.DONE, "maktab default");
    }

    public UUID getIDUser() {
        return mIDUser;
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

    public void setDate(Date date) {
        mDate = date;
    }

    public String getDateYMD() {
        String ymd = new SimpleDateFormat("yyyy-MM-dd").format(mDate);
        return ymd;
    }

    public String getTimeHMS() {
        String hms = new SimpleDateFormat("hh-mm-ss").format(mDate);
        return hms;
    }

    public static class COLS {
        public static final int VERSION_DB_USER = 1;
        public static final String NAME_DB_FILE = "tasks.db";
        public static final String TABLE_NAME = "tasks";
        public static final String CUL_ID = "ID";
        public static final String CUL_STATE = "state";
        public static final String CUL_TITLE = "title";
        public static final String CUL_DESCRIPTION = "description";
        public static final String CUL_DATE = "date";
        public static final String CUL_UUID = "uuid";
        public static final String CUL_UUID_USER = "uuidUser";
    }
}
