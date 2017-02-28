package com.ckz.baisi.unitls;

/**
 * Created by CKZ on 2017/2/21.
 */

public class MyTimeUtils {

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
}
