package com.ckz.baisi.unitls;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.ckz.baisi.R;

/**
 * Created by CKZ on 2017/2/4.
 */

public class AnimatorUtilsss {
    public static Animation animator = null;

    public static void startRefreshAnim(Context context, ImageView imageView){
        animator = AnimationUtils.loadAnimation(context, R.anim.anim_rotate);
        imageView.startAnimation(animator);
    }
    public static void stopRefreshAnim(){
        if (animator.isInitialized()){
            animator.cancel();
        }
    }
}

