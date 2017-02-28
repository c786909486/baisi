package com.ckz.baisi.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ckz.baisi.R;
import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.OnAnimEndListener;

/**
 * Created by CKZ on 2017/2/5.
 */

public class BudejieRefresh extends FrameLayout implements IHeaderView {
    private ImageView pullIcon,refreshIcon;
    private TextView refreshTextView;
    public TextView refreshTime;
    public BudejieRefresh(Context context) {
        this(context, null);
    }

    public BudejieRefresh(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BudejieRefresh(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View rootView = View.inflate(getContext(), R.layout.refresh_layout, null);
        pullIcon = (ImageView) rootView.findViewById(R.id.pull_icon);
        refreshIcon = (ImageView) rootView.findViewById(R.id.refreshing_icon);
        refreshTextView = (TextView) rootView.findViewById(R.id.refresh_txt);
        refreshTime = (TextView) rootView.findViewById(R.id.refresh_time);
        addView(rootView);
    }


    public void setPullDownStr(String pullDownStr1) {
        pullDownStr = pullDownStr1;
    }

    public void setReleaseRefreshStr(String releaseRefreshStr1) {
        releaseRefreshStr = releaseRefreshStr1;
    }

    public void setRefreshingStr(String refreshingStr1) {
        refreshingStr = refreshingStr1;
    }

    private String pullDownStr = "下拉可以刷新";
    private String releaseRefreshStr = "松开立即刷新";
    private String refreshingStr = "正在刷新数据中...";

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingDown(float fraction, float maxHeadHeight, float headHeight) {
        if (fraction < 0.5f){
            refreshTextView.setText(pullDownStr);
            pullIcon.setImageResource(R.drawable.pull_animation);
        }
        if (fraction > 0.5f) {refreshTextView.setText(releaseRefreshStr);}
           pullIcon.setImageResource(R.drawable.release_ani);
    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {
        if (fraction < 0.5f) {
            refreshTextView.setText(pullDownStr);
            if (pullIcon.getVisibility() == GONE) {
                pullIcon.setVisibility(VISIBLE);
                refreshIcon.setVisibility(GONE);
            }
        }
    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {
        refreshTextView.setText(refreshingStr);
        pullIcon.setVisibility(GONE);
        refreshIcon.setVisibility(VISIBLE);
        ((AnimationDrawable) refreshIcon.getDrawable()).start();
    }

    @Override
    public void onFinish(OnAnimEndListener listener) {
        listener.onAnimEnd();
    }

    @Override
    public void reset() {
        pullIcon.setVisibility(VISIBLE);
        refreshIcon.setVisibility(GONE);
        refreshTextView.setText(pullDownStr);
    }
}