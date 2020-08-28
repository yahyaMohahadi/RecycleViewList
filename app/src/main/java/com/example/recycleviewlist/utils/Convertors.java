package com.example.recycleviewlist.utils;

import androidx.room.TypeConverter;

import java.util.UUID;

public class Convertors {

    @TypeConverter
    public static String StringFromUUID(UUID uuid) {
        return uuid.toString();
    }

    @TypeConverter
    public static UUID StringFromUUID(String uuid) {
        return UUID.fromString(uuid);
    }
}
