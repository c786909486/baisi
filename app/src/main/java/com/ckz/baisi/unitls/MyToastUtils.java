package com.ckz.baisi.unitls;

import android.animation.Animator;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.ckz.baisi.R;

/**
 * Created by CKZ on 2017/3/12.
 */

public class MyToastUtils {

    public static void ShowBigToast(Context context,String content){
        View view = LayoutInflater.from(context).inflate(R.layout.show_big_toast,null);
        TextView txtToast = (TextView) view.findViewById(R.id.txt_toast);
        txtToast.setText(content);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM , 0, 150);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
    public static void showRefreshTosat(Context context, final TextView showToast, String content){
        Animation animationIn = AnimationUtils.loadAnimation(context,R.anim.toast_translate_in);
        final Animation animationOut = AnimationUtils.loadAnimation(context,R.anim.toast_translate_out);
        showToast.setText(content);
        showToast.setVisibility(View.VISIBLE);
        showToast.startAnimation(animationIn);
        showToast.postDelayed(new Runnable() {
            @Override
            public void run() {
                showToast.startAnimation(animationOut);
                showToast.setVisibility(View.GONE);
            }
        },2000);
    }
}
