package cn.beingyi.androidcore.utils;

import android.graphics.Point;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * author: zhengyu
 * date: 2020/12/4 13:56
 */
public class RichText {

    List<TextBean> stringList =new ArrayList<>();

    public RichText() {
    }

    public RichText append(String text,int color){
        stringList.add(new TextBean(text,color));
        return this;
    }

    public SpannableString build(){
        HashMap<Point, Integer> colorMap = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        int position = 0;
        for (TextBean bean: stringList) {
            int color = bean.color;
            String text=bean.text;
            sb.append(text);
            Point point = new Point(position, position + text.length());
            position += text.length();
            colorMap.put(point, color);
        }
        SpannableString ss = new SpannableString(sb.toString());
        for (Point point : colorMap.keySet()) {
            ss.setSpan(new ForegroundColorSpan(colorMap.get(point)), point.x, point.y, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }

    class TextBean{
        private String text;
        private int color;

        public TextBean(String text, int color) {
            this.text = text;
            this.color = color;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }
    }

}
