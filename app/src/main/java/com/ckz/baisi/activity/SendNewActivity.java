package com.ckz.baisi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ckz.baisi.R;
import com.ckz.baisi.animator.SendAnimator;

public class SendNewActivity extends AppCompatActivity {
    private TextView sendVideo,sendPicture,sendText,sendVoice,sendLink;
    private ImageView iconPost;
    private Button cancleBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_new);
        initView();
        setAni();
    }
    private void initView() {
        sendVideo = (TextView) findViewById(R.id.send_video);
        sendPicture = (TextView) findViewById(R.id.send_picture);
        sendText = (TextView) findViewById(R.id.send_text);
        sendVoice = (TextView) findViewById(R.id.send_voice);
        sendLink = (TextView) findViewById(R.id.send_link);
        iconPost = (ImageView) findViewById(R.id.icon_post);
        cancleBtn = (Button) findViewById(R.id.send_cancle);
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVideo.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SendAnimator.aniOut(sendVideo);
                    }
                },100);

                sendLink.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SendAnimator.aniOut(sendLink);
                    }
                },150);

                sendPicture.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SendAnimator.aniOut(sendPicture);
                    }
                },200);
                iconPost.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SendAnimator.aniOut(iconPost);
                    }
                },250);
                sendVoice.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SendAnimator.aniOut(sendVoice);
                    }
                },300);
                sendText.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SendAnimator.aniOut(sendText);
                    }
                },250);
                cancleBtn.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                },850);
            }
        });
    }
    private void setAni(){


        sendVideo.postDelayed(new Runnable() {
            @Override
            public void run() {
                sendVideo.setVisibility(View.VISIBLE);
                SendAnimator.aniIn(sendVideo);
            }
        },100);

        sendLink.postDelayed(new Runnable() {
            @Override
            public void run() {
                sendLink.setVisibility(View.VISIBLE);
                SendAnimator.aniIn(sendLink);
            }
        },150);

        sendPicture.postDelayed(new Runnable() {
            @Override
            public void run() {
                sendPicture.setVisibility(View.VISIBLE);
                SendAnimator.aniIn(sendPicture);
            }
        },200);
        iconPost.postDelayed(new Runnable() {
            @Override
            public void run() {
                iconPost.setVisibility(View.VISIBLE);
                SendAnimator.aniIn(iconPost);
            }
        },250);
        sendVoice.postDelayed(new Runnable() {
            @Override
            public void run() {
                sendVoice.setVisibility(View.VISIBLE);
                SendAnimator.aniIn(sendVoice);
            }
        },300);
        sendText.postDelayed(new Runnable() {
            @Override
            public void run() {
                sendText.setVisibility(View.VISIBLE);
                SendAnimator.aniIn(sendText);
            }
        },350);
    }
}
