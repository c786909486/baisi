package com.ckz.baisi.unitls;

import java.text.DecimalFormat;

/**
 * Created by CKZ on 2017/2/21.
 */

public class MyIntegerUtils {

    public static String ss2mm(int duration){
        String time = "";
        if (duration<=3600){
            String mm = String.valueOf(duration/60);
            String ss = String.valueOf(duration%60);
            if (mm.length()==1) mm = "0"+mm;
            if (ss.length()==1) ss = "0"+ss;

            time = mm+":"+ss;
        }
        return time;
    }

    public static String int2k(String index){
        String count = "";
        if (Integer.valueOf(index)<1000){
            count = index;
        }else {
            float i = Integer.valueOf(index)/1000;
            DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
            count = df.format(i)+"K";
        }
        return count;
    }
}
