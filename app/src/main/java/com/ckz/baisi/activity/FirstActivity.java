package com.ckz.baisi.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.ckz.baisi.R;
import com.ckz.baisi.unitls.SPUtils;

public class FirstActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_first);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SPUtils.getStringSp(FirstActivity.this,"isFirst").equals("no")){
                    Intent intent = new Intent(FirstActivity.this,MainActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(FirstActivity.this,WelcomeActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        },2000);
    }
}
