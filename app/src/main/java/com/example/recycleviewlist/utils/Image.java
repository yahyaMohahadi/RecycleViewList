package com.example.recycleviewlist.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import com.example.recycleviewlist.model.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Image {
    public static File getImagesDir(@NonNull Context context) {
        File directory = context.getDir("taskIMG", Context.MODE_PRIVATE);
        // Create imageDir
        if (!directory.exists()) {
            directory.mkdir();
        }
        return directory;
    }

    public static String getTaskImageName(@NonNull Task task) {
        return task.getUUID().toString() + ".jpg";
    }


    public static File getTaskImagePath(@NonNull Task task, @NonNull Context context) {
        return new File(getImagesDir(context), getTaskImageName(task));
    }

    public static Bitmap loadBitMap(Task task, Context context) {
        if (task.getHasImage()) {
            try {
                Bitmap img = BitmapFactory.decodeStream(
                        new FileInputStream(getTaskImagePath(task, context))
                );
                return img;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static boolean saveImageTask(Task task, Bitmap selectedImage, Context context) {
        if (!task.getHasImage()) {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(getTaskImagePath(task, context));
                selectedImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
                task.setHasImage(true);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            return false;
        }
    }
}
