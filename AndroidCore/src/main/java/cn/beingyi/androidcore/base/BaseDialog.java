package cn.beingyi.androidcore.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import cn.beingyi.androidcore.R;


/**
 * created by zhengyu
 * on 2020/5/23
 * 用于快速创建自定义对话框
 **/
public abstract class BaseDialog {


    public Context context;
    Dialog dialog;

    public BaseDialog(Context context) {
        this.context = context;
        this.dialog = new Dialog(context);

        Config config=config();

        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        if(!config.isHasBgShadow()) {
            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        dialog.setContentView(getContentView());
        initView();

        DisplayMetrics outMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        int heightPixels = outMetrics.heightPixels;


        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.gravity = Gravity.TOP;
        p.height= ViewGroup.LayoutParams.WRAP_CONTENT;
        p.width= widthPixels;
        //p.alpha = 0.9f;
        dialog.getWindow().setGravity(config.getGravity());
        dialog.getWindow().setAttributes(p);
        dialog.getWindow().setWindowAnimations(R.style.ActionSheetDialogAnimation);

        onCreate();

    }


    public abstract int getContentView();

    public abstract void initView();

    public abstract void onCreate();

    public  abstract Config config();

    public void show(){
        dialog.show();
    }
    public void dismiss(){
        dialog.dismiss();
    }

    protected <T extends View> T find(int viewId) {
        return (T) dialog.findViewById(viewId);
    }


    public class Config{
        boolean hasBgShadow;
        int gravity;

        public boolean isHasBgShadow() {
            return hasBgShadow;
        }

        public void setHasBgShadow(boolean hasBgShadow) {
            this.hasBgShadow = hasBgShadow;
        }

        public int getGravity() {
            return gravity;
        }

        public void setGravity(int gravity) {
            this.gravity = gravity;
        }
    }

}
