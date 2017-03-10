package com.ckz.baisi.view;

import android.app.Fragment;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.ckz.baisi.R;
import com.lcodecore.tkrefreshlayout.IBottomView;

/**
 * Created by CKZ on 2017/3/7.
 */

public class BudejieLoadMore extends FrameLayout implements IBottomView {
    public BudejieLoadMore(Context context) {
        this(context,null);
    }

    public BudejieLoadMore(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BudejieLoadMore(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        View bottomView = View.inflate(context, R.layout.load_more_layout,null);
        addView(bottomView);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingUp(float fraction, float maxHeadHeight, float headHeight) {

    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {

    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void reset() {

    }
}
