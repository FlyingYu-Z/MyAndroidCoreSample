package cn.beingyi.androidcore.model;

import android.app.Activity;
import android.content.Context;

import com.hbv.app.dialog.LoadingDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * author: zhengyu
 * date: 2020/12/30 20:50
 */
public class FileDownloader {
    Activity activity;
    String destPath;
    String url;
    OnDownloadResultListener onDownloadResultListener;

    public FileDownloader(Context context, String destPath, String url, OnDownloadResultListener onDownloadResultListener) {
        this.activity = (Activity) context;
        this.destPath = destPath;
        this.url = url;
        this.onDownloadResultListener = onDownloadResultListener;

        LoadingDialog loadingDialog=new LoadingDialog(activity);
        loadingDialog.show();
        new Thread(){
            @Override
            public void run() {
                super.run();
                download(url,new File(destPath));
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dismiss();
                    }
                });
            }
        }.start();
    }


    private void download(String urlString, File destFile) {
        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream(destFile);
            byte[] buffer = new byte[1204];
            int length;
            int byteread = 0;
            while ((byteread = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteread);
            }
            inStream.close();
            fs.close();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onDownloadResultListener.onSuccess(destFile);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onDownloadResultListener.onFailure();
                }
            });
        }
    }

    public interface OnDownloadResultListener{
        void onSuccess(File file);
        void onFailure();
    }

}
