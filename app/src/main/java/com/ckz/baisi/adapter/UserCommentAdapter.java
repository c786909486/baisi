package com.ckz.baisi.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.ckz.baisi.R;
import com.ckz.baisi.activity.CommentActivity;
import com.ckz.baisi.activity.CommentFromUser;
import com.ckz.baisi.activity.PlayVideoActivity;
import com.ckz.baisi.activity.ShowCommentImage;
import com.ckz.baisi.bean.UserCommentBean;
import com.ckz.baisi.unitls.ACache;
import com.ckz.baisi.unitls.LogUtils;
import com.ckz.baisi.view.CircleImageView;

import java.io.IOException;
import java.util.List;


public class UserCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TEXT = 0;
    private static final int IMAGE = 1;
    private static final int GIF = 2;
    private static final int VIDEO = 3;
    private static final int VOICE = 4;
    private Context context;
    private ACache mACache;
    private List<UserCommentBean.ListBean> mData;


    private static final int CURRENT_STATE_RELEASE = 0;
    private static final int CURRENT_STATE_PLAYING = 1;
    private int currentState = CURRENT_STATE_RELEASE;
    private MediaPlayer mPlayer;
    private AnimationDrawable animationDrawable;
    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).getCmt_type().equals("text")){
            return TEXT;
        }else if (mData.get(position).getCmt_type().equals("image")){
            return IMAGE;
        }else if (mData.get(position).getCmt_type().equals("gif")){
            return GIF;
        }else if (mData.get(position).getCmt_type().equals("video")){
            return VIDEO;
        }else {
            return VOICE;
        }
    }


    public UserCommentAdapter(Context context, List<UserCommentBean.ListBean> mData){
        this.context = context;
        this.mData = mData;
        mACache = ACache.get(context);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case TEXT:
                view = LayoutInflater.from(context).inflate(R.layout.user_comment_text,null);
                holder = new TextHolder(view);
                break;
            case IMAGE:
                view = LayoutInflater.from(context).inflate(R.layout.user_comment_image,null);
                holder = new ImageHolder(view);
                break;
            case GIF:
                view = LayoutInflater.from(context).inflate(R.layout.user_comment_gif,null);
                holder = new GifHolder(view);
                break;
            case VIDEO:
                view = LayoutInflater.from(context).inflate(R.layout.user_comment_video,null);
                holder = new VideoHolder(view);
                break;
            case VOICE:
                view = LayoutInflater.from(context).inflate(R.layout.user_comment_voice,null);
                holder = new VoiceHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case TEXT:
                TextHolder textHolder = (TextHolder) holder;
                setCommandView(textHolder.userIcon,textHolder.userName,textHolder.passTime,textHolder.ding,textHolder.cai,textHolder.conentText,textHolder.yuanWen,position);
                break;
            case IMAGE:
                ImageHolder imageHolder = (ImageHolder) holder;
                setCommandView(imageHolder.userIcon,imageHolder.userName,imageHolder.passTime,imageHolder.ding,imageHolder.cai,imageHolder.conentText,imageHolder.yuanWen,position);
                //设置图片
                setImage(imageHolder.userImage, position);
                break;
            case GIF:
                GifHolder gifHolder = (GifHolder) holder;
                setCommandView(gifHolder.userIcon,gifHolder.userName,gifHolder.passTime,gifHolder.ding,gifHolder.cai,gifHolder.conentText,gifHolder.yuanWen,position);
                //设置gif
                setGif(gifHolder.userGif,position);
                break;
            case VIDEO:
                VideoHolder videoHolder = (VideoHolder) holder;
                setCommandView(videoHolder.userIcon,videoHolder.userName,videoHolder.passTime,videoHolder.ding,videoHolder.cai,videoHolder.conentText,videoHolder.yuanWen,position);
                //设置video
                setVideo(videoHolder.playIcon,videoHolder.videoThumbnail,position);
                break;
            case VOICE:
                VoiceHolder voiceHolder = (VoiceHolder) holder;
                setCommandView(voiceHolder.userIcon,voiceHolder.userName,voiceHolder.passTime,voiceHolder.ding,voiceHolder.cai,voiceHolder.conentText,voiceHolder.yuanWen,position);

        }
    }

    private void setVideo(ImageView playIcon, ImageView videoThumbnail, int position) {
        MyClickListener listener = new MyClickListener(position);
        playIcon.setOnClickListener(listener);
        videoThumbnail.setOnClickListener(listener);
        Glide.with(context).load(mData.get(position).getVideo().getThumbnail().get(0)).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate().into(videoThumbnail);
    }

    private void setGif(ImageView userGif, int position) {
        MyClickListener listener = new MyClickListener(position);
        Glide.with(context).load(mData.get(position).getImage().getThumbnail().get(0)).asGif().placeholder(R.mipmap.bg_activities_item_end_transparent)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).dontAnimate().into(userGif);
        userGif.setOnClickListener(listener);
    }

    private void setImage(ImageView userImage, int position) {
        MyClickListener listener = new MyClickListener(position);
        Glide.with(context).load(mData.get(position).getImage().getThumbnail().get(0)).asBitmap().placeholder(R.mipmap.bg_activities_item_end_transparent)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).dontAnimate().into(userImage);
        userImage.setOnClickListener(listener);
    }

    //设置通用数据
    @SuppressLint("SetTextI18n")
    private void setCommandView(final CircleImageView userIcon, TextView userName, TextView passTime, TextView ding,
                                TextView cai, TextView contentText, TextView yuanWen, final int position){
        //设置头像
        Glide.with(context).load(mData.get(position).getU().getHeader().get(0)).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate().into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                userIcon.setImageDrawable(glideDrawable);
            }
        });
        //设置用户名
        userName.setText(mData.get(position).getU().getName());
        //passtime
        passTime.setText(mData.get(position).getPasstime());
        //正文内容
        contentText.setText(mData.get(position).getContent());
        //原文
        yuanWen.setText("[原文]"+mData.get(position).getTopic().getText());
        yuanWen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,CommentFromUser.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Data",mData.get(position).getTopic());
                intent.putExtra("Id",bundle);
                context.startActivity(intent);
                LogUtils.d("repostIntent",mData.get(position).getContent());
            }
        });
        //设置顶踩
        setClick(ding,cai,position);

    }
    //顶踩点击
    private void setClick(final TextView ding, final TextView cai, final int position){
        ding.setSelected(false);
        cai.setSelected(false);
        //右边点赞数
        if (mACache.getAsString(String.valueOf(mData.get(position).getId()))!=null){

            if (mACache.getAsString(String.valueOf(mData.get(position).getId())).equals("1")){
                ding.setSelected(true);
                cai.setSelected(false);
                ding.setEnabled(false);
                cai.setEnabled(false);
                int num = mData.get(position).getLike_count()+1;
                ding.setText(String.valueOf(num));
                notifyDataSetChanged();
            }
            if (mACache.getAsString(String.valueOf(mData.get(position).getId())).equals("2")){

                ding.setSelected(false);
                cai.setSelected(true);
                ding.setEnabled(false);
                cai.setEnabled(false);
                int num = mData.get(position).getLike_count()+1;
                cai.setText(String.valueOf(num));
                notifyDataSetChanged();
            }
        }else {
            if (mData.get(position).getLike_count()!=0){
                ding.setText(String.valueOf(mData.get(position).getLike_count()));
            }
            if (mData.get(position).getHate_count()!=0){
                cai.setText(String.valueOf(mData.get(position).getHate_count()));
            }
        }
        ding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ding.isClickable()){
                    ding.setSelected(true);
                    int num = mData.get(position).getLike_count()+1;
                    mACache.put(String.valueOf(mData.get(position).getId()),"1");
                    ding.setText(String.valueOf(num));
                    cai.setEnabled(false);
                    ding.setEnabled(false);
                }
            }
        });
        cai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cai.isClickable()){
                    cai.setSelected(true);
                    int num = mData.get(position).getHate_count();
                    mACache.put(String.valueOf(mData.get(position).getId()),"2");
                    cai.setText(String.valueOf(num+1));
                    cai.setEnabled(false);
                    ding.setEnabled(false);
                }
            }
        });
    }

    //播放功能
    private void setPlayer(final VoiceHolder holder, final int position){
        holder.voiceDuration.setText(String.valueOf(mData.get(position).getAudio().getDuration())+"''");
        if (mPlayer==null){
            animationDrawable = (AnimationDrawable) holder.voice_play_ani.getDrawable();
            hideVoiceViews(holder);
            animationDrawable.stop();
            holder.voice_play_icon.setVisibility(View.VISIBLE);
        }
        holder.voice_play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationDrawable = (AnimationDrawable) holder.voice_play_ani.getDrawable();
                if (currentState == CURRENT_STATE_RELEASE){
                    try {
                        if (mPlayer!=null){
                            mPlayer.release();
                        }
                        mPlayer = new MediaPlayer();
                        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mPlayer.setDataSource(mData.get(position).getAudio().getAudio().get(0));
                        mPlayer.prepare();
                        mPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                        @Override
                        public void onBufferingUpdate(MediaPlayer mp, int percent) {
                            if (percent<100){
                                hideVoiceViews(holder);
                                mp.pause();
                                holder.loading.setVisibility(View.VISIBLE);

                            }else {
                                hideVoiceViews(holder);
                                holder.voice_play_ani.setVisibility(View.VISIBLE);
                                mp.start();
                                animationDrawable.start();
                            }
                        }
                    });
                    mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            hideVoiceViews(holder);
                            animationDrawable.stop();
                            holder.voice_play_icon.setVisibility(View.VISIBLE);
                            currentState = CURRENT_STATE_RELEASE;
                        }
                    });

                    currentState = CURRENT_STATE_PLAYING;
                }else {
                    if (!mPlayer.isPlaying()){
                        currentState = CURRENT_STATE_RELEASE;
                    }
                    mPlayer.reset();
                    mPlayer.release();
                    hideVoiceViews(holder);
                    animationDrawable.stop();
                    holder.voice_play_icon.setVisibility(View.VISIBLE);
                    currentState = CURRENT_STATE_RELEASE;
                }
            }
        });
    }
    private void hideVoiceViews(VoiceHolder holder){
        holder.loading.setVisibility(View.GONE);
        holder.voice_play_icon.setVisibility(View.GONE);
        holder.voice_play_ani.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * 文字布局
     */
    private class TextHolder extends RecyclerView.ViewHolder{
        //通用部分
        CircleImageView userIcon;
        TextView userName,passTime,ding,cai,conentText,yuanWen;

        public TextHolder(View itemView) {
            super(itemView);
            userIcon = (CircleImageView) itemView.findViewById(R.id.image_user_icon);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            passTime = (TextView) itemView.findViewById(R.id.pass_time);
            ding = (TextView) itemView.findViewById(R.id.comment_right_ding);
            cai = (TextView) itemView.findViewById(R.id.comment_right_cai);
            conentText = (TextView) itemView.findViewById(R.id.uset_txt_content);
            yuanWen = (TextView) itemView.findViewById(R.id.yuan_wen);
        }
    }
    /**
     * image布局
     */
    private class ImageHolder extends RecyclerView.ViewHolder{
        //通用部分
        CircleImageView userIcon;
        TextView userName,passTime,ding,cai,conentText,yuanWen;
        //image
        ImageView userImage;
        public ImageHolder(View itemView) {
            super(itemView);
            userIcon = (CircleImageView) itemView.findViewById(R.id.image_user_icon);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            passTime = (TextView) itemView.findViewById(R.id.pass_time);
            ding = (TextView) itemView.findViewById(R.id.comment_right_ding);
            cai = (TextView) itemView.findViewById(R.id.comment_right_cai);
            conentText = (TextView) itemView.findViewById(R.id.uset_txt_content);
            yuanWen = (TextView) itemView.findViewById(R.id.yuan_wen);
            //
            userImage = (ImageView) itemView.findViewById(R.id.user_image);
        }
    }
    /**
     * gif布局
     */
    private class GifHolder extends RecyclerView.ViewHolder{
        //通用部分
        CircleImageView userIcon;
        TextView userName,passTime,ding,cai,conentText,yuanWen;
        //gif
        ImageView userGif;
        public GifHolder(View itemView) {
            super(itemView);
            userIcon = (CircleImageView) itemView.findViewById(R.id.image_user_icon);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            passTime = (TextView) itemView.findViewById(R.id.pass_time);
            ding = (TextView) itemView.findViewById(R.id.comment_right_ding);
            cai = (TextView) itemView.findViewById(R.id.comment_right_cai);
            conentText = (TextView) itemView.findViewById(R.id.uset_txt_content);
            yuanWen = (TextView) itemView.findViewById(R.id.yuan_wen);
            //
            userGif = (ImageView) itemView.findViewById(R.id.user_gif);
        }
    }
    /**
     * 视频布局
     */
    private class VideoHolder extends RecyclerView.ViewHolder{
        //通用部分
        CircleImageView userIcon;
        TextView userName,passTime,ding,cai,conentText,yuanWen;
        //video
        ImageView videoThumbnail,playIcon;

        public VideoHolder(View itemView) {
            super(itemView);
            userIcon = (CircleImageView) itemView.findViewById(R.id.image_user_icon);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            passTime = (TextView) itemView.findViewById(R.id.pass_time);
            ding = (TextView) itemView.findViewById(R.id.comment_right_ding);
            cai = (TextView) itemView.findViewById(R.id.comment_right_cai);
            conentText = (TextView) itemView.findViewById(R.id.uset_txt_content);
            yuanWen = (TextView) itemView.findViewById(R.id.yuan_wen);
            //
            videoThumbnail = (ImageView) itemView.findViewById(R.id.show_video_thumbnail);
            playIcon = (ImageView) itemView.findViewById(R.id.video_play_icon);
        }
    }
    /**
     * voice布局
     */
    private class VoiceHolder extends RecyclerView.ViewHolder{
        //通用部分
        CircleImageView userIcon;
        TextView userName,passTime,ding,cai,conentText,yuanWen;
        //voice
        ImageView voice_play_btn,voice_play_ani,voice_play_icon;
        ProgressBar loading;
        TextView voiceDuration;
        public VoiceHolder(View itemView) {
            super(itemView);
            userIcon = (CircleImageView) itemView.findViewById(R.id.image_user_icon);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            passTime = (TextView) itemView.findViewById(R.id.pass_time);
            ding = (TextView) itemView.findViewById(R.id.comment_right_ding);
            cai = (TextView) itemView.findViewById(R.id.comment_right_cai);
            conentText = (TextView) itemView.findViewById(R.id.uset_txt_content);
            yuanWen = (TextView) itemView.findViewById(R.id.yuan_wen);
            conentText.setVisibility(View.GONE);
            //
            voice_play_btn = (ImageView) itemView.findViewById(R.id.voice_play_btn);
            voice_play_icon = (ImageView) itemView.findViewById(R.id.voide_play_icon);
            voice_play_ani = (ImageView) itemView.findViewById(R.id.voide_play_ani);
            loading = (ProgressBar) itemView.findViewById(R.id.voice_loading);
            voiceDuration = (TextView) itemView.findViewById(R.id.voice_duration);
        }
    }

    private class MyClickListener implements View.OnClickListener{
        private int position;
        MyClickListener(int position){
            this.position = position;
        }
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()){
                case R.id.video_play_icon:
                case R.id.show_video_thumbnail :
                    intent = new Intent(context, PlayVideoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("videoUrl",mData.get(position).getVideo().getVideo().get(0));
                    int size[] = new int[]{mData.get(position).getVideo().getWidth(),mData.get(position).getVideo().getHeight()};
                    bundle.putIntArray("size",size);
                    intent.putExtra("video",bundle);
                    break;
                case R.id.user_gif:
                    intent =new Intent(context, ShowCommentImage.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("imageUrl",mData.get(position).getGif().getImages().get(0));
                    int gifSize[] = new int[]{mData.get(position).getGif().getWidth(),mData.get(position).getGif().getHeight()};
                    bundle1.putIntArray("size",gifSize);
                    intent.putExtra("image",bundle1);
                    break;
                case R.id.user_image:
                    intent =new Intent(context, ShowCommentImage.class);
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("imageUrl",mData.get(position).getImage().getImages().get(0));
                    int imageSize[] = new int[]{mData.get(position).getImage().getWidth(),mData.get(position).getImage().getHeight()};
                    bundle2.putIntArray("size",imageSize);
                    intent.putExtra("image",bundle2);
                    break;
            }
            context.startActivity(intent);
        }
    }
}
