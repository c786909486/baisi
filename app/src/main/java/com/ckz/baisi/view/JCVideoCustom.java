package com.ckz.baisi.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ckz.baisi.R;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by CKZ on 2017/2/25.
 */

public class JCVideoCustom extends JCVideoPlayerStandard {

    public TextView play_counts;
    public TextView total_duration;
    private RelativeLayout show_counts_layout;
    public JCVideoCustom(Context context) {
        super(context);
    }

    public JCVideoCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        super.init(context);
        play_counts = (TextView) this.findViewById(R.id.play_counts);
        total_duration = (TextView) this.findViewById(R.id.total_duration);
        show_counts_layout = (RelativeLayout) this.findViewById(R.id.show_counts_layout);
    }

    @Override
    public void setUp(String url, int screen, Object... objects) {
        super.setUp(url, screen, objects);
        FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
    }
    @Override
    public int getLayoutId() {
        return R.layout.baisi_video_layout;
    }


    @Override
    public void setUiWitStateAndScreen(int state) {
        super.setUiWitStateAndScreen(state);
        if (state == 0){
//            play_counts.setVisibility(VISIBLE);
//            total_duration.setVisibility(VISIBLE);
            show_counts_layout.setVisibility(VISIBLE);
        }else {
//            play_counts.setVisibility(GONE);
//            total_duration.setVisibility(GONE);
            show_counts_layout.setVisibility(GONE);
        }
        if (this.currentScreen == 0){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                this.topContainer.setBackground(getResources().getDrawable(R.drawable.white_bg,null));
            }else {
                this.topContainer.setBackground(getResources().getDrawable(R.drawable.white_bg));
            }
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                this.topContainer.setBackground(getResources().getDrawable(R.drawable.jc_title_bg,null));
            }else {
                this.topContainer.setBackground(getResources().getDrawable(R.drawable.jc_title_bg));
            }
        }
    }
}
