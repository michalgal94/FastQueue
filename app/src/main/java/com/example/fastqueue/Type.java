package com.example.fastqueue;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Type {

    public static String[] TYPES = {"צבע","פן","תספורת"};
    public static int[] TYPE_COLOR = {Color.YELLOW,Color.RED,Color.GREEN};

    private String desc;
    private String min;


    public Type() {
    }


    public Type(String desc , String min) {
       setDesc(desc);
       setMin(min);

    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }



//    public class Type extends TableLayout {

//    private TextView desc;
//    private TextView min;
//
//
//    public Type(Context context, String description , String minutes) {
//        super(context);
//        desc = new TextView(context);
//        min = new TextView(context);
//        addView(desc,0);
//        addView(min,1);
//    }

}
