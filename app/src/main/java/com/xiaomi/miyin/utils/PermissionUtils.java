package com.xiaomi.miyin.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class PermissionUtils {

    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 41;

    public static boolean checkPermissionForReadExternalStorage(Context context){
        return context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermissionForReadExternalStorage(Context context) throws Exception{
        try {
            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_STORAGE_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
