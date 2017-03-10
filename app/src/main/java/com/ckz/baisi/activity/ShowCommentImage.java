package com.ckz.baisi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ckz.baisi.R;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ShowCommentImage extends AppCompatActivity {
    private ImageView commentImage,commentAd,back;
    private PhotoViewAttacher mAttacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_comment_image);
        commentImage = (ImageView) findViewById(R.id.show_comment_image);
        commentAd = (ImageView) findViewById(R.id.show_comment_ad);
        back = (ImageView) findViewById(R.id.show_big_back);
        String url = getIntent().getBundleExtra("image").getString("imageUrl");
        int size[] = getIntent().getBundleExtra("image").getIntArray("size");
        assert size != null;
        Glide.with(this).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.mipmap.baisibudejie)
                .fitCenter().override(size[0],size[1]).dontAnimate().listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String s, Target<GlideDrawable> target, boolean b) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable glideDrawable, String s, Target<GlideDrawable> target, boolean b, boolean b1) {
                mAttacher = new PhotoViewAttacher(commentImage);
                mAttacher.update();
                return false;
            }
        }).into(commentImage);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
