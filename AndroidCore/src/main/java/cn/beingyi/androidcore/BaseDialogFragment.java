package cn.beingyi.androidcore;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * @author zhengyu
 * @date : 2020/7/20 9:40
 */
public abstract class BaseDialogFragment extends DialogFragment {

    View rootView;

    public abstract int getContentView();
    public abstract void initView();
    public abstract void onCreate();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=super.onCreateView(inflater, container, savedInstanceState);
        if(rootView==null){
            rootView=inflater.inflate(getContentView(),container,false);
        }
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView();
        onCreate();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        DisplayMetrics outMetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        int heightPixels = outMetrics.heightPixels;


        WindowManager.LayoutParams p = getDialog().getWindow().getAttributes();
        p.gravity = Gravity.TOP;
        p.height= ViewGroup.LayoutParams.WRAP_CONTENT;
        p.width= widthPixels;
        //p.alpha = 0.9f;
        getDialog().getWindow().setAttributes(p);
    }


    protected <T extends View> T find(int viewId) {
        return (T) rootView.findViewById(viewId);
    }

}
