package com.example.recycleviewlist;

import com.example.recycleviewlist.model.User;

public class OnlineUser {
    private  static OnlineUser mOnlineUser ;
    private static User onlineUser;
    private OnlineUser(User user) {
        onlineUser = user;
    }
    public static OnlineUser newInstance(User user){
        if (mOnlineUser == null){
            mOnlineUser = new OnlineUser(user);
        }
        return mOnlineUser;
    }

    public static User getOnlineUser() {
        return onlineUser;
    }

    public static void setOnlineUser(User onlineUser) {
        OnlineUser.onlineUser = onlineUser;
    }
}
