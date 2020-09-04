package com.example.recycleviewlist.utils;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.recycleviewlist.model.Task;

import java.io.File;

public class Image {
    public static File getImagesDir(@NonNull Context context){
        File directory = context.getDir("taskIMG", Context.MODE_PRIVATE);
        // Create imageDir
        if (!directory.exists()){
            directory.mkdir();
        }
        return directory;
    }
    public  static String getTaskImageName(@NonNull Task task){
       return task.getUUID().toString()+".jpg";
    }


    public  static File getTaskImagePath(@NonNull Task task,@NonNull Context context){
        return new File(getImagesDir(context),getTaskImageName(task));
    }
}
