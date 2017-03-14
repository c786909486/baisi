package com.ckz.baisi.animator;

import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by CKZ on 2017/3/13.
 */

public class SendAnimator {

    public static void aniIn(View view){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"translationY",-1000, 300,0);
        animator.setDuration(600);
        animator.start();
    }
    public static void aniOut(View view){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"translationY",0,-300, 1300);
        animator.setDuration(600);
        animator.start();
    }
}
