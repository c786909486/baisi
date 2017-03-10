package com.ckz.baisi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ckz.baisi.R;
import com.ckz.baisi.view.SimpleJCVideo;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class PlayVideoActivity extends AppCompatActivity {
    private SimpleJCVideo simpleJCVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        simpleJCVideo = (SimpleJCVideo) findViewById(R.id.play_voideo);
        String url = getIntent().getBundleExtra("video").getString("videoUrl");
        int size[] = getIntent().getBundleExtra("video").getIntArray("size");
        simpleJCVideo.setUp(url, JCVideoPlayer.SCREEN_LAYOUT_NORMAL,"");
        simpleJCVideo.startButton.performClick();
        assert size != null;
        simpleJCVideo.widthRatio = size[0];
        simpleJCVideo.heightRatio = size[1];

    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
