package cn.beingyi.androidcore.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.Nullable;

import org.xutils.common.util.DensityUtil;

import cn.beingyi.androidcore.R;

/**
 * @author zhengyu
 * @date : 2020/7/22 19:88
 */
public class RippleMenuView extends LinearLayout {

    public RippleMenuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initAttr(attrs);
    }

    public RippleMenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initAttr(attrs);
    }

    Context context;
    MaterialRippleLayout parent;
    LinearLayout ln_parent;
    ImageView img_icon;
    TextView tv_label;
    TextView tv_value;
    ImageView img_arrow;

    private void init() {
        context = getContext();
        setOrientation(VERTICAL);

        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(lp);
        setClickable(true);

        View view = LayoutInflater.from(context).inflate(R.layout.item_ripple_menu, null);
        parent = view.findViewById(R.id.MaterialRippleLayout_parent);
        ln_parent = view.findViewById(R.id.LinearLayout_parent);
        img_icon = view.findViewById(R.id.ImageView_icon);
        tv_label = view.findViewById(R.id.TextView_label);
        tv_value = view.findViewById(R.id.TextView_value);
        img_arrow = view.findViewById(R.id.ImageView_arrow);

        addView(view);
    }


    private void initAttr(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.RippleMenuView);
        int icon = array.getResourceId(R.styleable.RippleMenuView_menuIcon, 0);
        String label = array.getString(R.styleable.RippleMenuView_menuLabel);
        String value = array.getString(R.styleable.RippleMenuView_menuValue);
        boolean hasArrow = array.getBoolean(R.styleable.RippleMenuView_hasArrow, false);

        if (icon == 0) {
            img_icon.setVisibility(GONE);
        } else {
            img_icon.setImageResource(icon);
            img_icon.setVisibility(VISIBLE);
        }

        tv_label.setText(label);
        tv_value.setText(value);

        if (value.isEmpty()) {
            tv_value.setVisibility(GONE);
        }

        if (hasArrow) {
            img_arrow.setVisibility(VISIBLE);
        } else {
            img_arrow.setVisibility(INVISIBLE);
        }

        float iconWidth = array.getLayoutDimension(R.styleable.RippleMenuView_menuIconWidth, 0);
        float iconHeight = array.getLayoutDimension(R.styleable.RippleMenuView_menuIconHeight, 0);

        {
            ViewGroup.LayoutParams lp = img_icon.getLayoutParams();
            lp.width = (int) iconWidth;
            lp.height = (int) iconHeight;
            img_icon.setLayoutParams(lp);
        }
        int labelTextColor = array.getColor(R.styleable.RippleMenuView_labelTextColor, 0);
        tv_label.setTextColor(labelTextColor);

        {
            float labelTextSize = array.getDimension(R.styleable.RippleMenuView_labelTextSize, 0);
            int sp = DensityUtil.px2dip(labelTextSize);
            tv_label.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp);
        }

        {
            float paddingLeft = array.getDimension(R.styleable.RippleMenuView_parentPaddingLeft, 0);
            float paddingTop = array.getDimension(R.styleable.RippleMenuView_parentPaddingTop, 0);
            float paddingRight = array.getDimension(R.styleable.RippleMenuView_parentPaddingRight, 0);
            float paddingBottom = array.getDimension(R.styleable.RippleMenuView_parentPaddingBottom, 0);
            ln_parent.setPadding((int) paddingLeft, (int) paddingTop, (int) paddingRight, (int) paddingBottom);
        }


        array.recycle();
    }


    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        parent.setOnClickListener(onClickListener);
    }

    public void setIcon(int resid) {
        img_icon.setImageResource(resid);
    }

    public void setLabel(String str) {
        tv_label.setText(str);
    }

    public void setValue(String str) {
        tv_value.setText(str);
        tv_value.setVisibility(VISIBLE);
    }


}
