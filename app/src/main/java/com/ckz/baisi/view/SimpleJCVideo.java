package com.ckz.baisi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.ckz.baisi.R;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by CKZ on 2017/3/8.
 */

public class SimpleJCVideo extends JCVideoPlayerStandard {


    public SimpleJCVideo(Context context) {
        super(context);
    }

    public SimpleJCVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.simple_video_layout;
    }
}
