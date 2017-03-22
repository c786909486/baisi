package com.ckz.baisi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ckz.baisi.R;
import com.ckz.baisi.view.SimpleJCVideo;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class PlayVideoActivity extends AppCompatActivity {
    private SimpleJCVideo simpleJCVideo;
    private ImageView close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        simpleJCVideo = (SimpleJCVideo) findViewById(R.id.play_voideo);
        close = (ImageView) findViewById(R.id.close_video);
        String url = getIntent().getBundleExtra("video").getString("videoUrl");
        int size[] = getIntent().getBundleExtra("video").getIntArray("size");
        simpleJCVideo.setUp(url, JCVideoPlayer.SCREEN_LAYOUT_NORMAL,"");
        simpleJCVideo.startButton.performClick();
        assert size != null;
        simpleJCVideo.widthRatio = size[0];
        simpleJCVideo.heightRatio = size[1];
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
