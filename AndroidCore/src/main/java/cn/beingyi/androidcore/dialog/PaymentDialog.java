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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.beingyi.androidcore.R;
import cn.beingyi.androidcore.adapter.PaymentListAdapter;
import cn.beingyi.androidcore.bean.PaymentInfo;

/**
 * created by zhengyu
 * on 2020/5/15
 **/
public class PaymentDialog {

    Context context;
    Dialog dialog;

    ImageView img_close;
    ListView listView;
    List<PaymentInfo> paymentInfoList=new ArrayList<>();
    PaymentListAdapter adapter;
    Button btn_pay;
    PaymentResultListener listener;
    boolean selected=false;

    private void init(){
        img_close=dialog.findViewById(R.id.ImageView_close);
        listView=dialog.findViewById(R.id.ListView_payment);
        btn_pay=dialog.findViewById(R.id.Button_pay);
        adapter=new PaymentListAdapter(context,paymentInfoList,listView);
        listView.setAdapter(adapter);

    }

    public PaymentDialog(Context mContext,PaymentResultListener listener) {
        this.context = mContext;
        this.dialog=new Dialog(context);
        this.listener=listener;

        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialog_payment);

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
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setAttributes(p);
        dialog.getWindow().setWindowAnimations(R.style.ActionSheetDialogAnimation);

        initList();

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=adapter.getSelectedIndex();
                PaymentInfo info=paymentInfoList.get(position);
                listener.onSure(info.ID);
                selected=true;
                dialog.dismiss();
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(!selected){
                    listener.onCancel();
                }
            }
        });

    }

    private void initList(){
        addPayment(1,"支付宝",R.drawable.ic_alipay);
        addPayment(2,"微信支付",R.drawable.ic_wechat);


        //listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                adapter.setSelectedPosition(position);
            }
        });

        adapter.notifyDataSetChanged();

    }

    public void show(){
        dialog.show();
    }

    private void addPayment(int ID,String Nmae,int imgID){
        PaymentInfo info=new PaymentInfo();
        info.ID=ID;
        info.Name=Nmae;
        info.IconID=imgID;

        paymentInfoList.add(info);
        adapter.notifyDataSetChanged();
    }



    public interface PaymentResultListener {
        void onSure(int id);
        void onCancel();
    }


}
