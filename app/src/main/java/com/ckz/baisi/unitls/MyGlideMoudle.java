package com.ckz.baisi.unitls;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.request.target.ViewTarget;
import com.ckz.baisi.R;

/**
 * Created by CKZ on 2017/2/8.
 */

public class MyGlideMoudle implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder glideBuilder) {
        ViewTarget.setTagId(R.id.user_icon_loader);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
