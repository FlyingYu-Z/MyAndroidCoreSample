package cn.beingyi.androidcore.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;

import cn.beingyi.androidcore.AndroidCore;

public class ServiceUtils {

    /**
     * 判断服务是否开启
     *
     * @return
     */
    public static boolean isServiceRunning(Context context, String ServiceName) {
        try {
            if (TextUtils.isEmpty(ServiceName)) {
                return false;
            }
            ActivityManager myManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager.getRunningServices(1000);
            for (int i = 0; i < runningService.size(); i++) {
                Log.i("服务运行：", "" + runningService.get(i).service.getClassName().toString());
                if (runningService.get(i).service.getClassName().toString().equals(ServiceName)) {
                    return true;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }


    public static void startService(Class<?> cls){
        try {
            Context context = AndroidCore.getContext();

            if (!isServiceRunning(context, cls.getName())) {
                Intent intent = new Intent(context, cls);
                context.startService(intent);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
