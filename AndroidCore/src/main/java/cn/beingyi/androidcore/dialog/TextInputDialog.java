package cn.beingyi.androidcore.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import cn.beingyi.androidcore.R;
import cn.beingyi.androidcore.ui.ToastUtils;


/**
 *
 * 输入文字类
 *
 * by zhengyu
 *
 */

public class TextInputDialog {

    Context context;
    Activity activity;
    Dialog dialog;
    EditText ed_msg;
    ImageView img_send;
    ProgressBar progressBar;
    CallBack callBack;

    private void init(){
        ed_msg=dialog.findViewById(R.id.EditText_msg);
        img_send=dialog.findViewById(R.id.ImageView_send);
        progressBar=dialog.findViewById(R.id.ProgressBar_loading_send);
    }

    public TextInputDialog(final Context context, final CallBack callBack) {
        this.context = context;
        this.activity = (Activity) context;
        this.dialog=new Dialog(context);
        this.callBack=callBack;


        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().clearFlags( WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.setContentView(R.layout.view_input_text);

        init();

        DisplayMetrics outMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        int heightPixels = outMetrics.heightPixels;


        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.gravity = Gravity.TOP;
        p.height= ViewGroup.LayoutParams.WRAP_CONTENT;
        p.width= widthPixels;
        //p.alpha = 0.9f;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setAttributes(p);
        dialog.getWindow().setWindowAnimations(R.style.ActionSheetDialogAnimation);

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                ed_msg.requestFocus(View.FOCUS_UP);
                hideSoftInput(activity,ed_msg);
            }
        });

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                ed_msg.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(ed_msg, InputMethodManager.SHOW_IMPLICIT);
                    }
                },200);

            }
        });
        img_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=ed_msg.getText().toString();
                if(!msg.isEmpty()) {
                    if(msg.length()>50){
                        ToastUtils.show("评论过长，请保持在50字符以内");
                        return;
                    }
                    callBack.onSend(msg,TextInputDialog.this);
                }
            }
        });

    }


    public void setSending(){
        img_send.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void setStopSend(){
        img_send.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    public void dismiss(){
        dialog.dismiss();
    }


    /**
     * 隐藏键盘
     * @param bas 上下文
     */
    public static void hideSoftInput(Activity bas, EditText et) {
        try {
            ((InputMethodManager) bas.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(et.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void show(){
        dialog.show();
        ed_msg.post(() -> {
//            ed_msg.requestFocus();
//            InputTools.ShowKeyboard(ed_msg);
        });
    }

    public interface CallBack{

        void onSend(String msg, TextInputDialog dialog);

    }


}
