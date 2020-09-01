package com.example.recycleviewlist.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.recycleviewlist.OnlineUser;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;


@Entity(tableName = Task.COLS.TABLE_NAME)
public class Task implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUUID(UUID UUID) {
        mUUID = UUID;
    }

    public void setIDUser(UUID IDUser) {
        mIDUser = IDUser;
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

    public Task() {
    }

    public static class Bulder {
        private UUID mUUID = UUID.randomUUID();
        private UUID mIDUser = OnlineUser.newInstance().getOnlineUser().getUUID();
        private State mState = State.DONE;
        private String mStringTitle;
        private String mStringDescription;
        private Date mDate ;

        public Bulder() {
        }

        public Bulder setUUID(UUID uuid) {
            this.mUUID = uuid;
            return this;
        }

        public Bulder setUUIDUser(UUID uuid) {
            this.mIDUser = uuid;
            return this;
        }

        public Bulder setState(State state) {
            this.mState = state;
            return this;
        }

        public Bulder setTitle(String title) {
            this.mStringTitle = title;
            return this;
        }

        public Bulder setDescription(String description) {
            this.mStringDescription = description;
            return this;
        }

        public Bulder setDate(Date date) {
            this.mDate = date;
            return this;
        }

        public Task creat() {
            Task task = new Task();
            task.mIDUser = this.mIDUser;
            task.mUUID = this.mUUID;
            task.mState = this.mState;
            task.mStringTitle = this.mStringTitle;
            task.mStringDescription = this.mStringDescription;
            task.mDate = this.mDate;
            return task;
        }
    }


    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
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
