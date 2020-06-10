package cn.beingyi.androidcore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import java.util.List;

import cn.beingyi.androidcore.R;
import cn.beingyi.androidcore.bean.PaymentInfo;

/**
 * created by zhengyu
 * on 2020/5/15
 **/
public class PaymentListAdapter extends BaseAdapter {
    Context context;
    List<PaymentInfo> paymentInfoList;
    ListView listView;

    int selectedIndex=0;

    public PaymentListAdapter(Context context, List<PaymentInfo> paymentInfoList, ListView listView) {
        this.context = context;
        this.paymentInfoList = paymentInfoList;
        this.listView = listView;

    }

    @Override
    public int getCount() {
        return paymentInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return paymentInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=null;

        if(convertView==null){
            holder=new Holder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_payment,null);
            holder.Icon=convertView.findViewById(R.id.ImageView_icon);
            holder.Name=convertView.findViewById(R.id.TextView_name);
            holder.isChecked=convertView.findViewById(R.id.ImageView_isChecked);

            convertView.setTag(holder);
        }else {
            holder= (Holder) convertView.getTag();
        }
        PaymentInfo info=paymentInfoList.get(position);
        holder.Icon.setImageResource(info.IconID);
        holder.Name.setText(info.Name);

        if(selectedIndex==position){
            holder.isChecked.setImageResource(R.drawable.ic_checked);
        }else {
            holder.isChecked.setImageResource(R.drawable.ic_unchecked);
        }


        return convertView;
    }

    public void setSelectedPosition(int position){
        this.selectedIndex=position;
        notifyDataSetChanged();
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    class Holder{

        ImageView Icon;
        TextView Name;
        ImageView isChecked;

    }

}
