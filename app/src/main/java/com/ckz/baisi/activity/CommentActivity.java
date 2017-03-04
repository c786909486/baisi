package com.ckz.baisi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ckz.baisi.R;
import com.ckz.baisi.bean.BaisiData;
import com.ckz.baisi.unitls.LogUtils;

public class CommentActivity extends AppCompatActivity {
    private BaisiData.ListBean mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        mData = (BaisiData.ListBean) getIntent().getBundleExtra("Id").getSerializable("Data");
        String id = mData.getId();
        LogUtils.d("CommentId",id);
    }
}
