package cn.beingyi.androidcore.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import java.io.File;

import cn.beingyi.androidcore.AndroidCore;

/**
 * created by zhengyu
 * on 2020/6/1
 **/
public class UriUtils {

    public static Uri getUriFromFile(Context context, File file){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri contentUri = FileProvider.getUriForFile(context, AndroidCore.getFileProvider(), file);
            return contentUri;
        } else {
            return Uri.fromFile(file);
        }
    }

}
