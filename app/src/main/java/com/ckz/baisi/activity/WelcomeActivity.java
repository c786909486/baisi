package com.ckz.baisi.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.ckz.baisi.R;
import com.ckz.baisi.unitls.ByteToInputStream;
import com.ckz.baisi.unitls.SPUtils;

import java.io.IOException;
import java.io.InputStream;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        SPUtils.putStringSp(this,"isFirst","no");
        VideoView adVideo = (VideoView) findViewById(R.id.show_welcome);
        ImageView adBtn = (ImageView) findViewById(R.id.ad_btn);
        adVideo.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.advideo));
        adVideo.start();
        adVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        adBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
