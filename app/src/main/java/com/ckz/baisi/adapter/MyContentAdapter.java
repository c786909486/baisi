package com.ckz.baisi.adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.ckz.baisi.R;
import com.ckz.baisi.activity.CommentActivity;
import com.ckz.baisi.activity.HtmlActivity;
import com.ckz.baisi.activity.ShowBigImageActivity;
import com.ckz.baisi.activity.UserDetilsActivity;
import com.ckz.baisi.bean.BaisiData;
import com.ckz.baisi.unitls.CalLinesUtils;
import com.ckz.baisi.unitls.GetGlest;
import com.ckz.baisi.unitls.LogUtils;
import com.ckz.baisi.unitls.MyIntegerUtils;
import com.ckz.baisi.unitls.ScreenUtils;
import com.ckz.baisi.unitls.TextUtils;
import com.ckz.baisi.view.CircleImageView;
import com.ckz.baisi.view.JCVideoCustom;
import java.util.List;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by CKZ on 2017/2/20.
 */

public class MyContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity context;
    private List<BaisiData.ListBean> mData;
    private static final int IMAGETYPE = 0;
    private static final int GIFTYPE = 1;
    private static final int VIDEOTYPE = 2;
    private static final int DUANZITYPE = 3;
    private static final int VOICETYPE = 4;
    private static final int HTMLTYPE = 5;
    private static final int REPONSETYPE = 6;
    private static final int QUANWEN = 0;
    private static final int SHOUQI = 1;
    private int type = QUANWEN;
    private TopCommentAdapter adapter;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);
    }
    public MyContentAdapter(Activity context, List<BaisiData.ListBean> mData){
        this.context = context;
        this.mData = mData;
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).getType().equals("image")){
            return IMAGETYPE;
        }else if (mData.get(position).getType().equals("gif")){
            return GIFTYPE;
        }else if (mData.get(position).getType().equals("video")){
            return VIDEOTYPE;
        }else if (mData.get(position).getType().equals("text")){
            return DUANZITYPE;
        }else if (mData.get(position).getType().equals("html")){
            return HTMLTYPE;
        } else if (mData.get(position).getType().equals("repost")){
            return REPONSETYPE;
        }else {
            return VOICETYPE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType){
            case IMAGETYPE:
                view = LayoutInflater.from(context).inflate(R.layout.image_item_layout,null);
                viewHolder = new ImageViewHolder(view);
                break;
            case GIFTYPE:
                view = LayoutInflater.from(context).inflate(R.layout.gif_item_layout,null);
                viewHolder = new GifViewHolder(view);
                break;
            case VIDEOTYPE:
                view = LayoutInflater.from(context).inflate(R.layout.video_item_layout,null);
                viewHolder = new VideoViewHolder(view);
                break;
            case DUANZITYPE:
                view = LayoutInflater.from(context).inflate(R.layout.duanzi_item_layout,null);
                viewHolder = new DuanziViewHolder(view);
                break;
            case HTMLTYPE:
                view = LayoutInflater.from(context).inflate(R.layout.html_item_layout,null);
                viewHolder = new HtmlViewHolder(view);
                break;
            case REPONSETYPE:
                view = LayoutInflater.from(context).inflate(R.layout.repost_layout,null);
                viewHolder = new RepostViewHolder(view);
                break;
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case IMAGETYPE:
                ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
                setImageData(imageViewHolder,position);
                break;
            case GIFTYPE:
                GifViewHolder gifViewHolder = (GifViewHolder) holder;
                setGifData(gifViewHolder,position);
                break;
            case DUANZITYPE:
                DuanziViewHolder duanziViewHolder = (DuanziViewHolder) holder;
                setDuanziData(duanziViewHolder,position);
                break;
            case VIDEOTYPE:
                VideoViewHolder videoViewHolder = (VideoViewHolder) holder;
                setVideoData(videoViewHolder,position);
                break;
            case HTMLTYPE:
                HtmlViewHolder htmlViewHolder = (HtmlViewHolder) holder;
                setHtmlData(htmlViewHolder,position);
                break;
            case REPONSETYPE:
                RepostViewHolder repostViewHolder = (RepostViewHolder) holder;
                setRepostData(repostViewHolder,position);
        }
    }
    /**
     * 设置转载信息
     */
    @SuppressLint("SetTextI18n")
    private void setRepostData(final RepostViewHolder holder, int position){
        holder.text_content.setText(mData.get(position).getText());
        String name = mData.get(position).getRepost().getU().getName();
        String content = mData.get(position).getRepost().getText();
        SpannableString string = new SpannableString(TextUtils.ToSBC(name+":"+content));
        MyClickableSpan span = new MyClickableSpan(position);
        string.setSpan(span,0,name.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.repost_name.setMovementMethod(LinkMovementMethod.getInstance());
        holder.repost_name.setHighlightColor(Color.parseColor("#ffffff"));
        holder.repost_name.setText(string);

        if (mData.get(position).getRepost().getType().equals("gif")){
            hideView(holder);
            holder.repost_image.setVisibility(View.VISIBLE);
            Glide.with(context).load(mData.get(position).getRepost().getGif().getImages().get(0))
                    .asGif().placeholder(R.mipmap.bg_activities_item_end_transparent)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE).dontAnimate().into(holder.repost_image);
        }else if (mData.get(position).getRepost().getType().equals("image")){
            hideView(holder);
            holder.repost_image.setVisibility(View.VISIBLE);
            if (mData.get(position).getRepost().getImage().getHeight()>600){
                Glide.with(context).load(mData.get(position).getImage().getBig().get(0)).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .placeholder(R.mipmap.bg_activities_item_end_transparent).dontAnimate().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                        Bitmap bitmap1 = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),600);
                        holder.repost_image.setImageBitmap(bitmap1);
                    }
                });
            }else {
                Glide.with(context).load(mData.get(position).getImage().getBig().get(0)).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .placeholder(R.mipmap.bg_activities_item_end_transparent).dontAnimate().into(holder.repost_image);
            }
        }else {
            hideView(holder);
            holder.repost_video.setVisibility(View.VISIBLE);
            holder.repost_video.play_counts.setText(mData.get(position).getRepost().getVideo().getPlaycount()+"播放");
            holder.repost_video.total_duration.setText(MyIntegerUtils.ss2mm(mData.get(position).getRepost().getVideo().getDuration()));
            holder.repost_video.setUp(mData.get(position).getRepost().getVideo().getVideo().get(0),JCVideoPlayer.SCREEN_LAYOUT_LIST,"");
            Glide.with(context).load(mData.get(position).getRepost().getVideo().getThumbnail().get(0))
                    .placeholder(R.mipmap.bg_activities_item_end_transparent).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .dontAnimate().into(holder.repost_video.thumbImageView);
        }
          /*
         * 设置用户信息以及发布时间
         */
        setUserData(context,holder.user_icon,holder.user_name,holder.pass_time,holder.user_layout,position);
        /*
         * 显示评论数
         */
        setBottomClick(holder.ding_btn,holder.cai_btn,holder.forward_btn,holder.commend_btn,holder.hot_commend,holder.list_top_icon,position);
    }
    //隐藏转载布局
    private void hideView(RepostViewHolder holder){
        holder.repost_video.setVisibility(View.GONE);
        holder.repost_image.setVisibility(View.GONE);
    }
    /**
     * 设置html数据
     */
    private void setHtmlData(final HtmlViewHolder holder, int position){
        holder.txt_content.setText(mData.get(position).getHtml().getTitle());
        MyClickListener listener = new MyClickListener(position);
        Glide.with(context).load(mData.get(position).getHtml().getThumbnail().get(0))
                .placeholder(R.mipmap.bg_activities_item_end_transparent).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate().into(holder.html_image);
        holder.html_image.setOnClickListener(listener);
          /*
         * 设置用户信息以及发布时间
         */
        setUserData(context,holder.user_icon,holder.user_name,holder.pass_time,holder.user_layout,position);
        /*
         * 显示评论数
         */
        setBottomClick(holder.ding_btn,holder.cai_btn,holder.forward_btn,holder.commend_btn,holder.hot_commend,holder.list_top_icon,position);
    }

    /**
     * 设置视频数据
     */
    private void setVideoData(final VideoViewHolder holder, final int position){
        /*
         *设置视频链接，播放次数，播放时长，视频正文,缩略图链接
         */
        MyClickListener listener = new MyClickListener(position);
        holder.video_txt.setText(mData.get(position).getText());
        holder.video_txt.setOnClickListener(listener);
        JCVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
        if (mData.get(position).getVideo()!=null){
            holder.show_video.play_counts.setText(String.valueOf(mData.get(position).getVideo().getPlaycount())+"播放");
            holder.show_video.total_duration.setText(MyIntegerUtils.ss2mm(mData.get(position).getVideo().getDuration()));
            holder.show_video.setUp(mData.get(position).getVideo().getVideo().get(1), JCVideoPlayer.SCREEN_LAYOUT_LIST,"");
            holder.show_video.widthRatio = mData.get(position).getVideo().getWidth();
            holder.show_video.heightRatio = mData.get(position).getVideo().getHeight();
            Glide.with(context).load(mData.get(position).getVideo().getThumbnail().get(0))
                    .placeholder(R.mipmap.bg_activities_item_end_transparent).dontAnimate()
                    .override(mData.get(position).getVideo().getWidth(),mData.get(position).getVideo().getHeight())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.show_video.thumbImageView);
        }

           /*
         * 设置用户信息以及发布时间
         */
        setUserData(context,holder.user_icon,holder.user_name,holder.pass_time,holder.user_layout,position);
        /*
         * 显示评论数
         */
        setBottomClick(holder.ding_btn,holder.cai_btn,holder.forward_btn,holder.commend_btn,holder.hot_commend,holder.list_top_icon,position);
    }

    /**
     * 设置段子数据
     */
    private void setDuanziData(final DuanziViewHolder holder, final int position){
        MyClickListener listener = new MyClickListener(position);
        String content = mData.get(position).getText();
        holder.duanzi_txt.setText(content);
        holder.duanzi_txt.setOnClickListener(listener);
        if (CalLinesUtils.calLines(context,content)){
            holder.quanwen_click.setVisibility(View.VISIBLE);
            holder.quanwen_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (type == QUANWEN){
                        holder.duanzi_txt.setMaxLines(Integer.MAX_VALUE);
                        holder.quanwen_click.setText("收起");
                        type = SHOUQI;
                    }else if (type == SHOUQI){
                        holder.quanwen_click.setText("全文");
                        holder.duanzi_txt.setMaxLines(7);
                        type = QUANWEN;
                    }
                }
            });
        }else {
            holder.quanwen_click.setVisibility(View.GONE);
        }

         /*
         * 设置用户信息以及发布时间
         */
        setUserData(context,holder.user_icon,holder.user_name,holder.pass_time,holder.user_layout,position);
        /*
         * 显示评论数
         */
        setBottomClick(holder.ding_btn,holder.cai_btn,holder.forward_btn,holder.commend_btn,holder.hot_commend,holder.list_top_icon,position);
    }

    /**
     * 设置gif数据
     */
    private void setGifData(final GifViewHolder holder, final int position){
        /*
         * 显示正文内容
         * 设置gif图片
         */
        MyClickListener listener = new MyClickListener(position);
        holder.gif_txt.setText(mData.get(position).getText());
        holder.gif_txt.setOnClickListener(listener);
        Glide.with(context)
                .load(mData.get(position).getGif().getImages().get(0))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.bg_activities_item_end_transparent)
                .dontAnimate()
                .into(holder.show_gif);
        holder.show_gif.setOnClickListener(listener);
         /*
         * 设置用户信息以及发布时间
         */
        setUserData(context,holder.user_icon,holder.user_name,holder.pass_time,holder.user_layout,position);
        /*
         * 显示评论数
         */
        setBottomClick(holder.ding_btn,holder.cai_btn,holder.forward_btn,holder.commend_btn,holder.hot_commend,holder.list_top_icon,position);
    }

    /**
     * 设置image数据
     */

    private void setImageData(final ImageViewHolder holder, int position){

        /*
         * 显示正文内容
         * 判断图片高度，显示图片
         */
        MyClickListener listener = new MyClickListener(position);
        holder.image_txt.setText(mData.get(position).getText());
        holder.image_txt.setOnClickListener(listener);
        if (mData.get(position).getImage().getHeight()>= GetGlest.getGLESTextureLimit()){
            Glide.with(context).load(mData.get(position).getImage().getBig().get(0))
                    .downloadOnly(mData.get(position).getImage().getWidth(),mData.get(position).getImage().getHeight());
        }
        if (mData.get(position).getImage().getHeight()> ScreenUtils.getScreenHeight(context)){
            holder.click_large.setVisibility(View.VISIBLE);
            Glide.with(context).load(mData.get(position).getImage().getBig().get(0))
                    .asBitmap()
                    .placeholder(R.mipmap.bg_activities_item_end_transparent)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                            Bitmap bitmap1 = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),600);
                            holder.image_show.setImageBitmap(bitmap1);
                        }
                    });
        }else {
            Glide.with(context).load(mData.get(position).getImage().getBig().get(0))
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.mipmap.bg_activities_item_end_transparent)
                    .dontAnimate().into(holder.image_show);
            holder.click_large.setVisibility(View.GONE);
        }
        holder.image_show.setOnClickListener(listener);

        /*
         * 设置用户信息以及发布时间
         */
        setUserData(context,holder.user_icon,holder.user_name,holder.pass_time,holder.user_layout,position);
        /*
         * 显示评论数
         */
        setBottomClick(holder.ding_btn,holder.cai_btn,holder.forward_btn,holder.commend_btn,holder.hot_commend,holder.list_top_icon,position);
    }
    //设置用户信息
    private void setUserData(Context context, final CircleImageView user_icon, TextView user_name, TextView pass_time, LinearLayout user_layout, int position){
        Glide.with(context).load(mData.get(position).getU().getHeader().get(0))
                .placeholder(R.mipmap.refresh_loading08)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                       user_icon.setImageDrawable(glideDrawable);
                    }
                });
        user_name.setText(mData.get(position).getU().getName());
        pass_time.setText(mData.get(position).getPasstime());
        user_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出用户主页选项窗口
            }
        });
    }

    //设置底部顶踩点击事件
    private void setBottomClick(final TextView ding, final TextView cai, TextView forward, TextView comment , RecyclerView hot_commend , ImageView list_icon, int position){
        MyClickListener listener = new MyClickListener(position);
        ding.setSelected(false);
        cai.setSelected(false);
        if (!mData.get(position).getUp().equals("0")) ding.setText(mData.get(position).getUp());
        if (mData.get(position).getDown()!=0)  cai.setText(String.valueOf(mData.get(position).getDown()));
        if (mData.get(position).getForward()!=0)  forward.setText(String.valueOf(mData.get(position).getForward()));
        if (!mData.get(position).getComment().equals("0")) comment.setText(mData.get(position).getComment());
        ding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ding.isClickable()){
                    ding.setSelected(true);
                    int num = Integer.valueOf(ding.getText().toString());
                    ding.setText(String.valueOf(num+1));
                    cai.setClickable(false);
                   ding.setClickable(false);
                }
            }
        });
        cai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cai.isClickable()){
                    cai.setSelected(true);
                    int num = Integer.valueOf(cai.getText().toString());
                    cai.setText(String.valueOf(num+1));
                    cai.setClickable(false);
                    ding.setClickable(false);
                }
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出底部分享窗口
            }
        });
        comment.setOnClickListener(listener);
        if (mData.get(position).getTop_comments()!=null){
            adapter = new TopCommentAdapter(context,mData.get(position).getTop_comments(),mData.get(position));
            hot_commend.setAdapter(adapter);
            list_icon.setVisibility(View.VISIBLE);
        }else {
            list_icon.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    /**
     *  转载布局
     */
    class RepostViewHolder extends RecyclerView.ViewHolder{
        //用户头像区
        CircleImageView user_icon;
        TextView user_name,pass_time;
        LinearLayout user_layout;
        TextView text_content;
        //repost_item
        TextView repost_name;
        ImageView repost_image;
        JCVideoCustom repost_video;
        //评论区
        TextView ding_btn,cai_btn,forward_btn,commend_btn;
        RecyclerView hot_commend;
        ImageView list_top_icon;
        public RepostViewHolder(View itemView) {
            super(itemView);
            user_icon = (CircleImageView) itemView.findViewById(R.id.image_user_icon);
            user_name = (TextView) itemView.findViewById(R.id.user_name);
            pass_time = (TextView) itemView.findViewById(R.id.pass_time);
            user_layout = (LinearLayout) itemView.findViewById(R.id.user_layout);
            text_content = (TextView) itemView.findViewById(R.id.text_content);
            //
           repost_image = (ImageView) itemView.findViewById(R.id.repost_image);
            repost_name = (TextView) itemView.findViewById(R.id.repost_name);
            repost_video = (JCVideoCustom) itemView.findViewById(R.id.repost_video);
            //
            ding_btn = (TextView) itemView.findViewById(R.id.ding_button);
            cai_btn = (TextView) itemView.findViewById(R.id.cai_button);
            forward_btn = (TextView) itemView.findViewById(R.id.forward_button);
            commend_btn = (TextView) itemView.findViewById(R.id.commend_button);
            hot_commend = (RecyclerView) itemView.findViewById(R.id.item_hot_commend);
            hot_commend.setLayoutManager(new LinearLayoutManager(context));
            list_top_icon = (ImageView) itemView.findViewById(R.id.list_top_icon);
        }
    }
    /**
     *  html布局
     */
    class HtmlViewHolder extends RecyclerView.ViewHolder{
        //用户头像区
        CircleImageView user_icon;
        TextView user_name,pass_time;
        LinearLayout user_layout;
        //html_item
        ImageView html_image;
        TextView txt_content;
        //评论区
        TextView ding_btn,cai_btn,forward_btn,commend_btn;
        RecyclerView hot_commend;
        ImageView list_top_icon;
        public HtmlViewHolder(View itemView) {
            super(itemView);
            user_icon = (CircleImageView) itemView.findViewById(R.id.image_user_icon);
            user_name = (TextView) itemView.findViewById(R.id.user_name);
            pass_time = (TextView) itemView.findViewById(R.id.pass_time);
            user_layout = (LinearLayout) itemView.findViewById(R.id.user_layout);
            //
            html_image = (ImageView) itemView.findViewById(R.id.html_image);
            txt_content = (TextView) itemView.findViewById(R.id.text_content);
            //
            ding_btn = (TextView) itemView.findViewById(R.id.ding_button);
            cai_btn = (TextView) itemView.findViewById(R.id.cai_button);
            forward_btn = (TextView) itemView.findViewById(R.id.forward_button);
            commend_btn = (TextView) itemView.findViewById(R.id.commend_button);
            hot_commend = (RecyclerView) itemView.findViewById(R.id.item_hot_commend);
            hot_commend.setLayoutManager(new LinearLayoutManager(context));
            list_top_icon = (ImageView) itemView.findViewById(R.id.list_top_icon);
        }
    }

    /**
     *  图片布局
     */
    class ImageViewHolder extends RecyclerView.ViewHolder{
        //用户头像区
        CircleImageView user_icon;
        TextView user_name,pass_time;
        LinearLayout user_layout;
        //image_item
        TextView image_txt;
        ImageView image_show;
        LinearLayout click_large;
        //评论区
        TextView ding_btn,cai_btn,forward_btn,commend_btn;
        RecyclerView hot_commend;
        ImageView list_top_icon;
        public ImageViewHolder(View itemView) {
            super(itemView);
            user_icon = (CircleImageView) itemView.findViewById(R.id.image_user_icon);
            user_name = (TextView) itemView.findViewById(R.id.user_name);
            pass_time = (TextView) itemView.findViewById(R.id.pass_time);
            user_layout = (LinearLayout) itemView.findViewById(R.id.user_layout);
            //
            image_txt = (TextView) itemView.findViewById(R.id.text_content);
            image_show = (ImageView) itemView.findViewById(R.id.show_image);
            click_large = (LinearLayout) itemView.findViewById(R.id.click_large);
            //
            ding_btn = (TextView) itemView.findViewById(R.id.ding_button);
            cai_btn = (TextView) itemView.findViewById(R.id.cai_button);
            forward_btn = (TextView) itemView.findViewById(R.id.forward_button);
            commend_btn = (TextView) itemView.findViewById(R.id.commend_button);
            hot_commend = (RecyclerView) itemView.findViewById(R.id.item_hot_commend);
            hot_commend.setLayoutManager(new LinearLayoutManager(context));
            list_top_icon = (ImageView) itemView.findViewById(R.id.list_top_icon);
        }
    }
    /**
     * gif布局
     */
    class GifViewHolder extends RecyclerView.ViewHolder{
        //用户头像区
        CircleImageView user_icon;
        TextView user_name,pass_time;
        LinearLayout user_layout;
        //gif item
        TextView gif_txt;
        ImageView show_gif;
        //评论区
        TextView ding_btn,cai_btn,forward_btn,commend_btn;
        RecyclerView hot_commend;
        ImageView list_top_icon;
        public GifViewHolder(View itemView) {
            super(itemView);
            user_icon = (CircleImageView) itemView.findViewById(R.id.image_user_icon);
            user_name = (TextView) itemView.findViewById(R.id.user_name);
            pass_time = (TextView) itemView.findViewById(R.id.pass_time);
            user_layout = (LinearLayout) itemView.findViewById(R.id.user_layout);
            //
            ding_btn = (TextView) itemView.findViewById(R.id.ding_button);
            cai_btn = (TextView) itemView.findViewById(R.id.cai_button);
            forward_btn = (TextView) itemView.findViewById(R.id.forward_button);
            commend_btn = (TextView) itemView.findViewById(R.id.commend_button);
            hot_commend = (RecyclerView) itemView.findViewById(R.id.item_hot_commend);
            hot_commend.setLayoutManager(new LinearLayoutManager(context));
            //
            gif_txt = (TextView) itemView.findViewById(R.id.text_content);
            show_gif = (ImageView) itemView.findViewById(R.id.show_gif);
            list_top_icon = (ImageView) itemView.findViewById(R.id.list_top_icon);
        }
    }
    /**
     * 段子布局
     */
    class DuanziViewHolder extends RecyclerView.ViewHolder{
        //用户头像区
        CircleImageView user_icon;
        TextView user_name,pass_time;
        LinearLayout user_layout;
        //duanzi item
        TextView duanzi_txt,quanwen_click;
        //评论区
        TextView ding_btn,cai_btn,forward_btn,commend_btn;
        RecyclerView hot_commend;
        ImageView list_top_icon;
        public DuanziViewHolder(View itemView) {
            super(itemView);
            user_icon = (CircleImageView) itemView.findViewById(R.id.image_user_icon);
            user_name = (TextView) itemView.findViewById(R.id.user_name);
            pass_time = (TextView) itemView.findViewById(R.id.pass_time);
            user_layout = (LinearLayout) itemView.findViewById(R.id.user_layout);
            //
            ding_btn = (TextView) itemView.findViewById(R.id.ding_button);
            cai_btn = (TextView) itemView.findViewById(R.id.cai_button);
            forward_btn = (TextView) itemView.findViewById(R.id.forward_button);
            commend_btn = (TextView) itemView.findViewById(R.id.commend_button);
            hot_commend = (RecyclerView) itemView.findViewById(R.id.item_hot_commend);
            hot_commend.setLayoutManager(new LinearLayoutManager(context));
            //
            duanzi_txt = (TextView) itemView.findViewById(R.id.text_content);
            quanwen_click = (TextView) itemView.findViewById(R.id.quanwen_click);
            list_top_icon = (ImageView) itemView.findViewById(R.id.list_top_icon);
        }
    }
    /**
     * 视频布局
     */
    class VideoViewHolder extends RecyclerView.ViewHolder{
        //用户头像区
        CircleImageView user_icon;
        TextView user_name,pass_time;
        LinearLayout user_layout;
       //video item
        TextView video_txt;
        JCVideoCustom show_video;
        //评论区
        TextView ding_btn,cai_btn,forward_btn,commend_btn;
        RecyclerView hot_commend;
        ImageView list_top_icon;
        public VideoViewHolder(View itemView) {
            super(itemView);
            user_icon = (CircleImageView) itemView.findViewById(R.id.image_user_icon);
            user_name = (TextView) itemView.findViewById(R.id.user_name);
            pass_time = (TextView) itemView.findViewById(R.id.pass_time);
            user_layout = (LinearLayout) itemView.findViewById(R.id.user_layout);
            //
            ding_btn = (TextView) itemView.findViewById(R.id.ding_button);
            cai_btn = (TextView) itemView.findViewById(R.id.cai_button);
            forward_btn = (TextView) itemView.findViewById(R.id.forward_button);
            commend_btn = (TextView) itemView.findViewById(R.id.commend_button);
            hot_commend = (RecyclerView) itemView.findViewById(R.id.item_hot_commend);
            hot_commend.setLayoutManager(new LinearLayoutManager(context));
            //
            video_txt = (TextView) itemView.findViewById(R.id.text_content);
            show_video = (JCVideoCustom) itemView.findViewById(R.id.show_video);
            show_video.thumbImageView.setBackgroundColor(Color.WHITE);
            list_top_icon = (ImageView) itemView.findViewById(R.id.list_top_icon);
        }
    }
    class MyClickListener implements View.OnClickListener{
        private int position;
        public MyClickListener(int position){
            this.position = position;
        }

        @Override
        public void onClick(View v) {
         switch (v.getId()){
             case R.id.html_image:
             case R.id.text_content:
             case R.id.commend_button:
                setIntent(position);
                 break;
             case R.id.show_gif:
             case R.id.show_image:
                 Intent intent = new Intent(context, ShowBigImageActivity.class);
                 Bundle bundle = new Bundle();
                 bundle.putSerializable("imageData",mData.get(position));
                 intent.putExtra("image",bundle);
                 context.startActivity(intent);
                 break;
         }
        }
    }
    class MyClickableSpan extends ClickableSpan {
        private int position;
        public MyClickableSpan(int position){
            this.position = position;
        }
        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
            ds.setColor(Color.parseColor("#4574AA"));

        }
        @Override
        public void onClick(View widget) {
            Intent intent = new Intent(context, UserDetilsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("userId",mData.get(position).getU().getUid());
            intent.putExtra("Id",bundle);
            context.startActivity(intent);
        }
    }
    private void setIntent(int position){
        if (mData.get(position).getType().equals("html")){
            Intent intent = new Intent(context,HtmlActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Data",mData.get(position).getHtml());
            intent.putExtra("Id",bundle);
            context.startActivity(intent);
        }else {
            Intent intent = new Intent(context,CommentActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Data",mData.get(position));
            intent.putExtra("Id",bundle);
            context.startActivity(intent);
            LogUtils.d("repostIntent",mData.get(position).getText());
        }

    }

}
