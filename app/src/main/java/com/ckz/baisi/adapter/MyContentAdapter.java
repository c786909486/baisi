package com.ckz.baisi.adapter;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.ckz.baisi.bean.BaisiData;
import com.ckz.baisi.unitls.DisplayUtils;
import com.ckz.baisi.unitls.MyTimeUtils;
import com.ckz.baisi.unitls.ScreenUtils;
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
        }else {
            return VIDEOTYPE;
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
        }
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
        JCVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
        if (mData.get(position).getVideo()!=null){
            holder.show_video.play_counts.setText(String.valueOf(mData.get(position).getVideo().getPlaycount())+"播放");
            holder.show_video.total_duration.setText(MyTimeUtils.ss2mm(mData.get(position).getVideo().getDuration()));
            holder.show_video.setUp(mData.get(position).getVideo().getVideo().get(1), JCVideoPlayer.SCREEN_LAYOUT_NORMAL,"");
            Glide.with(context).load(mData.get(position).getVideo().getThumbnail().get(0))
                    .placeholder(R.mipmap.bg_activities_item_end_transparent).dontAnimate()
                    .override(mData.get(position).getVideo().getWidth(),mData.get(position).getVideo().getHeight())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.show_video.thumbImageView);
        }

           /*
         * 设置用户信息以及发布时间
         */
        Glide.with(context).load(mData.get(position).getU().getHeader().get(0))
                .placeholder(R.mipmap.refresh_loading08)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        holder.user_icon.setImageDrawable(glideDrawable);
                    }
                });
        holder.user_name.setText(mData.get(position).getU().getName());
        holder.pass_time.setText(mData.get(position).getPasstime());
        holder.user_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出用户主页选项窗口
            }
        });

        /*
         * 显示评论数
         */
        if (mData.get(position).getUp()!=null)  holder.ding_btn.setText(mData.get(position).getUp());
        if (mData.get(position).getDown()!=0)  holder.cai_btn.setText(String.valueOf(mData.get(position).getDown()));
        if (mData.get(position).getForward()!=0)  holder.forward_btn.setText(String.valueOf(mData.get(position).getForward()));
        if (mData.get(position).getComment()!=null)  holder.commend_btn.setText(mData.get(position).getComment());
        holder.ding_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.ding_btn.isClickable()){
                    holder.ding_btn.setSelected(true);
                    int num = Integer.valueOf(holder.ding_btn.getText().toString());
                    holder.ding_btn.setText(String.valueOf(num+1));
                    holder.cai_btn.setClickable(false);
                    holder.ding_btn.setClickable(false);
                }
            }
        });
        holder.cai_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.cai_btn.isClickable()){
                    holder.cai_btn.setSelected(true);
                    int num = Integer.valueOf(holder.cai_btn.getText().toString());
                    holder.cai_btn.setText(String.valueOf(num+1));
                    holder.cai_btn.setClickable(false);
                    holder.ding_btn.setClickable(false);
                }
            }
        });

        holder.forward_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出底部分享窗口
            }
        });


        holder.commend_btn.setOnClickListener(listener);

        holder.user_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出底部访问用户主页窗口
            }
        });
        if (mData.get(position).getTop_comments()!=null){
            adapter = new TopCommentAdapter(context,mData.get(position).getTop_comments());
            holder.hot_commend.setAdapter(adapter);

        }
    }

    /**
     * 设置段子数据
     */
    private void setDuanziData(final DuanziViewHolder holder, int position){
        MyClickListener listener = new MyClickListener(position);
        String content = mData.get(position).getText();
        holder.duanzi_txt.setText(content);
        if (calLines(content)){
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
        Glide.with(context).load(mData.get(position).getU().getHeader().get(0))
                .placeholder(R.mipmap.refresh_loading08)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        holder.user_icon.setImageDrawable(glideDrawable);
                    }
                });
        holder.user_name.setText(mData.get(position).getU().getName());
        holder.pass_time.setText(mData.get(position).getPasstime());
        holder.user_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出用户主页选项窗口
            }
        });

        /*
         * 显示评论数
         */
        if (mData.get(position).getUp()!=null)  holder.ding_btn.setText(mData.get(position).getUp());
        if (mData.get(position).getDown()!=0)  holder.cai_btn.setText(String.valueOf(mData.get(position).getDown()));
        if (mData.get(position).getForward()!=0)  holder.forward_btn.setText(String.valueOf(mData.get(position).getForward()));
        if (mData.get(position).getComment()!=null)  holder.commend_btn.setText(mData.get(position).getComment());
        holder.ding_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.ding_btn.isClickable()){
                    holder.ding_btn.setSelected(true);
                    int num = Integer.valueOf(holder.ding_btn.getText().toString());
                    holder.ding_btn.setText(String.valueOf(num+1));
                    holder.cai_btn.setClickable(false);
                    holder.ding_btn.setClickable(false);
                }
            }
        });
        holder.cai_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.cai_btn.isClickable()){
                    holder.cai_btn.setSelected(true);
                    int num = Integer.valueOf(holder.cai_btn.getText().toString());
                    holder.cai_btn.setText(String.valueOf(num+1));
                    holder.cai_btn.setClickable(false);
                    holder.ding_btn.setClickable(false);
                }
            }
        });

        holder.forward_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出底部分享窗口
            }
        });
        holder.commend_btn.setOnClickListener(listener);
        holder.user_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出底部访问用户主页窗口
            }
        });
        if (mData.get(position).getTop_comments()!=null){
            adapter = new TopCommentAdapter(context,mData.get(position).getTop_comments());
            holder.hot_commend.setAdapter(adapter);

        }

    }

    /**
     * 设置gif数据
     */
    private void setGifData(final GifViewHolder holder,int position){
        /*
         * 显示正文内容
         * 设置gif图片
         */
        MyClickListener listener = new MyClickListener(position);
        holder.gif_txt.setText(mData.get(position).getText());
        Glide.with(context)
                .load(mData.get(position).getGif().getImages().get(0))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.bg_activities_item_end_transparent)
                .dontAnimate()
                .into(holder.show_gif);
        /*
         * 设置用户信息以及发布时间
         */
        Glide.with(context).load(mData.get(position).getU().getHeader().get(0))
                .placeholder(R.mipmap.refresh_loading08)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .placeholder(R.mipmap.bg_activities_item_end_transparent)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        holder.user_icon.setImageDrawable(glideDrawable);
                    }
                });
        holder.user_name.setText(mData.get(position).getU().getName());
        holder.pass_time.setText(mData.get(position).getPasstime());
        holder.user_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出用户主页选项窗口
            }
        });

        /*
         * 显示评论数
         */
        if (mData.get(position).getUp()!=null)  holder.ding_btn.setText(mData.get(position).getUp());
        if (mData.get(position).getDown()!=0)  holder.cai_btn.setText(String.valueOf(mData.get(position).getDown()));
        if (mData.get(position).getForward()!=0)  holder.forward_btn.setText(String.valueOf(mData.get(position).getForward()));
        if (mData.get(position).getComment()!=null)  holder.commend_btn.setText(mData.get(position).getComment());
        holder.ding_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.ding_btn.isClickable()){
                    holder.ding_btn.setSelected(true);
                    int num = Integer.valueOf(holder.ding_btn.getText().toString());
                    holder.ding_btn.setText(String.valueOf(num+1));
                    holder.cai_btn.setClickable(false);
                    holder.ding_btn.setClickable(false);
                }
            }
        });
        holder.cai_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.cai_btn.isClickable()){
                    holder.cai_btn.setSelected(true);
                    int num = Integer.valueOf(holder.cai_btn.getText().toString());
                    holder.cai_btn.setText(String.valueOf(num+1));
                    holder.ding_btn.setClickable(false);
                    holder.cai_btn.setClickable(false);
                }
            }
        });

        holder.forward_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出底部分享窗口
            }
        });
        holder.commend_btn.setOnClickListener(listener);
        holder.user_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出底部访问用户主页窗口
            }
        });
        if (mData.get(position).getTop_comments()!=null){
            adapter = new TopCommentAdapter(context,mData.get(position).getTop_comments());
            holder.hot_commend.setAdapter(adapter);

        }
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

        /*
         * 设置用户信息以及发布时间
         */
        Glide.with(context).load(mData.get(position).getU().getHeader().get(0))
                .placeholder(R.mipmap.refresh_loading08)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        holder.user_icon.setImageDrawable(glideDrawable);
                    }
                });
        holder.user_name.setText(mData.get(position).getU().getName());
        holder.pass_time.setText(mData.get(position).getPasstime());
        holder.user_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出用户主页选项窗口
            }
        });

        /*
         * 显示评论数
         */
        if (mData.get(position).getUp()!=null)  holder.ding_btn.setText(mData.get(position).getUp());
        if (mData.get(position).getDown()!=0)  holder.cai_btn.setText(String.valueOf(mData.get(position).getDown()));
        if (mData.get(position).getForward()!=0)  holder.forward_btn.setText(String.valueOf(mData.get(position).getForward()));
        if (mData.get(position).getComment()!=null)  holder.commend_btn.setText(mData.get(position).getComment());
        holder.ding_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.ding_btn.isClickable()){
                    holder.ding_btn.setSelected(true);
                    int num = Integer.valueOf(holder.ding_btn.getText().toString());
                    holder.ding_btn.setText(String.valueOf(num+1));
                    holder.cai_btn.setClickable(false);
                    holder.ding_btn.setClickable(false);
                }
            }
        });
        holder.cai_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.cai_btn.isClickable()){
                    holder.cai_btn.setSelected(true);
                    int num = Integer.valueOf(holder.cai_btn.getText().toString());
                    holder.cai_btn.setText(String.valueOf(num+1));
                    holder.cai_btn.setClickable(false);
                    holder.ding_btn.setClickable(false);
                }
            }
        });

        holder.forward_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出底部分享窗口
            }
        });
        holder.commend_btn.setOnClickListener(listener);
        holder.user_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出底部访问用户主页窗口
            }
        });
        if (mData.get(position).getTop_comments()!=null){
            adapter = new TopCommentAdapter(context,mData.get(position).getTop_comments());
            holder.hot_commend.setAdapter(adapter);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    /**
     * 计算文本的长度是否超过最大行
     *
     * @param
     * @param string
     * @return
     */
    private boolean calLines(String string) {
        // 获得字体的宽度，sp转px的方法，网上很多，19为textview中所设定的textSize属性值
        int txtWidth = DisplayUtils.sp2px(context, 17);
        // 获得屏幕的宽度
        int winWidth = DisplayUtils
                .getWindowWidth(context);
        // 获得textView控件的宽度，15为xml中所设定marginleft 和 marginright的值，这里都是15，所以直接乘以2了。
        int viewWidth = winWidth
                - DisplayUtils.dip2px(context, 10) * 2;
        // 获得单行最多显示字数
        int maxWords = viewWidth / txtWidth;
        // 计算字符串长度，
        int stringLen = string.length();
        // 字符串长度除以单行最多显示字数为行数
        int lines = stringLen / maxWords;

        if (lines > 7) {
            // 如果大于指定行数，则直接返回
            return true;
        } else if (lines == 7) {
            // 否则需要判断下是否等于最大行，但是有余数
            if (stringLen % maxWords > 0) {
                return true;
            }
        }
        return false;
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
             case R.id.commend_button:
                 Intent intent = new Intent(context, CommentActivity.class);
                 Bundle bundle = new Bundle();
                 bundle.putString("commentId",mData.get(position).getId());
                 intent.putExtra("Id",bundle);
                 context.startActivity(intent);
                 break;
         }
        }
    }
}
