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

        dialog.setCancelable(config.isCanCancelable());
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

    }

    public void setHeight(int height){
        DisplayMetrics outMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        int heightPixels = outMetrics.heightPixels;

        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.gravity = Gravity.TOP;
        p.height= height;
        p.width= widthPixels;
        dialog.getWindow().setAttributes(p);
    }


    public void setHeightX(int split){
        DisplayMetrics outMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        int heightPixels = outMetrics.heightPixels;

        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.gravity = Gravity.TOP;
        p.height= heightPixels/split;
        p.width= widthPixels;
        dialog.getWindow().setAttributes(p);
    }


    public abstract int getContentView();

    public abstract void initView();

    public abstract void onCreate();

    public  abstract Config config();

    public void show(){
        dialog.show();
        onCreate();
    }
    public void dismiss(){
        dialog.dismiss();
    }

    protected <T extends View> T find(int viewId) {
        return (T) dialog.findViewById(viewId);
    }


    public class Config{
        private boolean hasBgShadow;
        private int gravity;
        private boolean canCancelable;

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

        public boolean isCanCancelable() {
            return canCancelable;
        }

        public void setCanCancelable(boolean canCancelable) {
            this.canCancelable = canCancelable;
        }
    }

}
