package com.ckz.baisi.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ckz.baisi.R;
import com.ckz.baisi.activity.PlayVideoActivity;
import com.ckz.baisi.activity.ShowCommentImage;
import com.ckz.baisi.bean.CommentBean;
import com.ckz.baisi.unitls.ACache;

import java.io.EOFException;
import java.io.IOException;
import java.util.List;

/**
 * Created by CKZ on 2017/3/9.
 */

public class InnerNormalCommentAdapter extends BaseAdapter {
    private static final int TXTCOMMENTTYPE = 0; //text
    private static final int IMAGECOMMENTTYPE = 1; //image
    private static final int VOICECOMMENTTYPE = 2; //audio
    private static final int VIDEOCOMMENTTYPE = 3; //video
    private static final int GIFCOMMENTTYPE = 4; //gif

    private static final int CURRENT_STATE_RELEASE = 0;
    private static final int CURRENT_STATE_PLAYING = 1;
    private int currentState = CURRENT_STATE_RELEASE;
    private MediaPlayer mPlayer;
    private AnimationDrawable animationDrawable;
    private LayoutInflater inflater;
    private ACache mACache;
    private List<CommentBean.NormalBean.ListBeanX.PrecmtsBeanX> mData;
    private Context context;

    public InnerNormalCommentAdapter (Context context,List<CommentBean.NormalBean.ListBeanX.PrecmtsBeanX> mData){
        this.context =context;
        this.mData = mData;
        inflater = LayoutInflater.from(context);
        mACache = ACache.get(context);
    }

    @Override
    public int getItemViewType(int position) {
       try {
           if (mData.get(position).getType().equals("text")){
               return TXTCOMMENTTYPE;
           }else if (mData.get(position).getType().equals("image")){
               return IMAGECOMMENTTYPE;
           }else if (mData.get(position).getType().equals("audio")){
               return VOICECOMMENTTYPE;
           }else if (mData.get(position).getType().equals("video")){
               return VIDEOCOMMENTTYPE;
           }else if (mData.get(position).getType().equals("gif")){
               return GIFCOMMENTTYPE;
           }else {
               return super.getItemViewType(position);
           }
       }catch (Exception e){
           return super.getItemViewType(position);
       }
    }

    @Override
    public int getViewTypeCount() {
        return 5;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        MyClickListener listener = new MyClickListener(position);
        TextHolder textHolder = null;
        ImageHolder imageHolder = null;
        GifHolder gifHolder = null;
        VideoHolder videoHolder = null;
        VoiceHolder voiceHolder = null;
        if (convertView == null){
            switch (getItemViewType(position)){
                case TXTCOMMENTTYPE:
                    textHolder = new TextHolder();
                    convertView = inflater.inflate(R.layout.inner_text,parent,false);
                    textHolder.floorCount = (TextView) convertView.findViewById(R.id.inner_floor);
                    textHolder.userName = (TextView) convertView.findViewById(R.id.inner_user_name);
                    textHolder.dingCount = (TextView) convertView.findViewById(R.id.inner_ding);
                    textHolder.caiCount = (TextView) convertView.findViewById(R.id.inner_cai);
                    textHolder.txt_comment = (TextView) convertView.findViewById(R.id.inner_text);
                    convertView.setTag(textHolder);
                    break;
                case IMAGECOMMENTTYPE:
                    imageHolder = new ImageHolder();
                    convertView = inflater.inflate(R.layout.inner_image,parent,false);
                    imageHolder.floorCount = (TextView) convertView.findViewById(R.id.inner_floor);
                    imageHolder.userName = (TextView) convertView.findViewById(R.id.inner_user_name);
                    imageHolder.dingCount = (TextView) convertView.findViewById(R.id.inner_ding);
                    imageHolder.caiCount = (TextView) convertView.findViewById(R.id.inner_cai);
                    imageHolder.txt_comment = (TextView) convertView.findViewById(R.id.inner_text);
                    imageHolder.show_image = (ImageView) convertView.findViewById(R.id.inner_image);
                    convertView.setTag(imageHolder);
                    break;
                case GIFCOMMENTTYPE:
                    gifHolder = new GifHolder();
                    convertView = inflater.inflate(R.layout.inner_gif,parent,false);
                    gifHolder.floorCount = (TextView) convertView.findViewById(R.id.inner_floor);
                    gifHolder.userName = (TextView) convertView.findViewById(R.id.inner_user_name);
                    gifHolder.dingCount = (TextView) convertView.findViewById(R.id.inner_ding);
                    gifHolder.caiCount = (TextView) convertView.findViewById(R.id.inner_cai);
                    gifHolder.txt_comment = (TextView) convertView.findViewById(R.id.inner_text);
                    gifHolder.show_gif = (ImageView) convertView.findViewById(R.id.inner_gif);
                    convertView.setTag(gifHolder);
                    break;
                case VIDEOCOMMENTTYPE:
                    videoHolder = new VideoHolder();
                    convertView = inflater.inflate(R.layout.inner_video,parent,false);
                    videoHolder.floorCount = (TextView) convertView.findViewById(R.id.inner_floor);
                    videoHolder.userName = (TextView) convertView.findViewById(R.id.inner_user_name);
                    videoHolder.dingCount = (TextView) convertView.findViewById(R.id.inner_ding);
                    videoHolder.caiCount = (TextView) convertView.findViewById(R.id.inner_cai);
                    videoHolder.txt_comment = (TextView) convertView.findViewById(R.id.inner_text);
                    videoHolder.show_video_thumbnail = (ImageView) convertView.findViewById(R.id.show_video_thumbnail);
                    videoHolder.videoPlay = (ImageView) convertView.findViewById(R.id.video_play_icon);
                    convertView.setTag(videoHolder);
                    break;
                case VOICECOMMENTTYPE:
                    voiceHolder = new VoiceHolder();
                    convertView = inflater.inflate(R.layout.inner_voice,parent,false);
                    voiceHolder.floorCount = (TextView) convertView.findViewById(R.id.inner_floor);
                    voiceHolder.userName = (TextView) convertView.findViewById(R.id.inner_user_name);
                    voiceHolder.dingCount = (TextView) convertView.findViewById(R.id.inner_ding);
                    voiceHolder.caiCount = (TextView) convertView.findViewById(R.id.inner_cai);
                    voiceHolder.voice_play_icon = (ImageView) convertView.findViewById(R.id.voide_play_icon);
                    voiceHolder.voice_play_ani = (ImageView) convertView.findViewById(R.id.voide_play_ani);
                    voiceHolder.voice_play_btn = (ImageView) convertView.findViewById(R.id.voice_play_btn);
                    voiceHolder.loading = (ProgressBar) convertView.findViewById(R.id.voice_loading);
                    voiceHolder.voiceDuration = (TextView) convertView.findViewById(R.id.voice_duration);
                    convertView.setTag(voiceHolder);
                    break;
            }
        }else {
            switch (type){
                case TXTCOMMENTTYPE:
                    textHolder = (TextHolder) convertView.getTag();
                    break;
                case IMAGECOMMENTTYPE:
                    imageHolder = (ImageHolder) convertView.getTag();
                    break;
                case GIFCOMMENTTYPE:
                    gifHolder = (GifHolder) convertView.getTag();
                    break;
                case VIDEOCOMMENTTYPE:
                    videoHolder = (VideoHolder) convertView.getTag();
                    break;
                case VOICECOMMENTTYPE:
                    voiceHolder = (VoiceHolder) convertView.getTag();
                    break;
            }
        }
        switch (type){
            case TXTCOMMENTTYPE:
                assert textHolder != null;
                setCommandView(textHolder.floorCount,textHolder.userName,textHolder.dingCount,textHolder.caiCount,position);
                if (mData.get(position).getContent().equals("楼主已将此评论删除")) textHolder.txt_comment.setTextColor(R.color.baisiColor);
                textHolder.txt_comment.setText(mData.get(position).getContent());

                break;
            case IMAGECOMMENTTYPE:
                assert imageHolder != null;
                setCommandView(imageHolder.floorCount,imageHolder.userName,imageHolder.dingCount,imageHolder.caiCount,position);
                if (mData.get(position).getContent()!=null && !mData.get(position).getContent().equals("")){
                    imageHolder.txt_comment.setText(mData.get(position).getContent());
                    imageHolder.txt_comment.setVisibility(View.VISIBLE);
                }else {
                    imageHolder.txt_comment.setVisibility(View.GONE);
                }
                Glide.with(context).load(mData.get(position).getImage().getThumbnail().get(0))
                        .asBitmap().placeholder(R.mipmap.bg_activities_item_end_transparent)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE).dontAnimate()
                        .into(imageHolder.show_image);

                imageHolder.show_image.setOnClickListener(listener);
                break;
            case GIFCOMMENTTYPE:
                assert gifHolder != null;
                setCommandView(gifHolder.floorCount,gifHolder.userName,gifHolder.dingCount,gifHolder.caiCount,position);
                if (mData.get(position).getContent()!=null && !mData.get(position).getContent().equals("")){
                    gifHolder.txt_comment.setText(mData.get(position).getContent());
                    gifHolder.txt_comment.setVisibility(View.VISIBLE);
                }else {
                    gifHolder.txt_comment.setVisibility(View.GONE);
                }
                Glide.with(context).load(mData.get(position).getGif().getImages().get(0))
                        .asGif().placeholder(R.mipmap.bg_activities_item_end_transparent)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE).dontAnimate()
                        .into(gifHolder.show_gif);

                gifHolder.show_gif.setOnClickListener(listener);
                break;
            case VIDEOCOMMENTTYPE:
                assert videoHolder != null;
                setCommandView(videoHolder.floorCount,videoHolder.userName,videoHolder.dingCount,videoHolder.caiCount,position);
                if (mData.get(position).getContent()!=null && !mData.get(position).getContent().equals("")){
                    videoHolder.txt_comment.setText(mData.get(position).getContent());
                    videoHolder.txt_comment.setVisibility(View.VISIBLE);
                }else {
                    videoHolder.txt_comment.setVisibility(View.GONE);
                }
                Glide.with(context).load(mData.get(position).getVideo().getThumbnail().get(0))
                        .placeholder(R.mipmap.bg_activities_item_end_transparent)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE).dontAnimate()
                        .into(videoHolder.show_video_thumbnail);
                videoHolder.videoPlay.setOnClickListener(listener);
                videoHolder.show_video_thumbnail.setOnClickListener(listener);
                break;
            case VOICECOMMENTTYPE:
                assert voiceHolder != null;
                setCommandView(voiceHolder.floorCount,voiceHolder.userName,voiceHolder.dingCount,voiceHolder.caiCount,position);
                setPlayer(voiceHolder,position);
                break;
        }
        return convertView;
    }

    //设置通用控件
    private void setCommandView(TextView floorCount,TextView userName,TextView dingCount,TextView caiCount,int position){
            floorCount.setText(String.valueOf(mData.get(position).getFloor()));
            userName.setText(mData.get(position).getUser().getUsername());
            userName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"弹出访问用户窗口",Toast.LENGTH_SHORT).show();
                }
            });
            setClick(dingCount,caiCount,position);
    }

    //顶踩点击
    private void setClick(final TextView ding, final TextView cai, final int position){
        //右边点赞数
        if (mACache.getAsString(String.valueOf(mData.get(position).getId()))!=null){

            if (mACache.getAsString(String.valueOf(mData.get(position).getId())).equals("1")){
                Log.d("tag",position+"");
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
                cai.setText(String.valueOf(mData.get(position).getLike_count()));
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

    private void hideVoiceViews(VoiceHolder holder) {
        holder.loading.setVisibility(View.GONE);
        holder.voice_play_icon.setVisibility(View.GONE);
        holder.voice_play_ani.setVisibility(View.GONE);
    }
    private class TextHolder{
        //command
        TextView floorCount;
        TextView userName;
        TextView dingCount;
        TextView caiCount;
        //文字评论
        TextView txt_comment;

    }
    private class ImageHolder{
        //command
        TextView floorCount;
        TextView userName;
        TextView dingCount;
        TextView caiCount;
        //图片
        ImageView show_image;
        //文字评论
        TextView txt_comment;

    }
    private class GifHolder{
        //command
        TextView floorCount;
        TextView userName;
        TextView dingCount;
        TextView caiCount;
        //图片
        ImageView show_gif;
        //文字评论
        TextView txt_comment;
    }
    private class VideoHolder{
        //command
        TextView floorCount;
        TextView userName;
        TextView dingCount;
        TextView caiCount;
        //图片
        ImageView show_video_thumbnail;
        ImageView videoPlay;
        //文字评论
        TextView txt_comment;

    }
    private class VoiceHolder{
        //command
        TextView floorCount;
        TextView userName;
        TextView dingCount;
        TextView caiCount;
        //voicePlay
        ImageView voice_play_btn,voice_play_ani,voice_play_icon;
        ProgressBar loading;
        TextView voiceDuration;
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
                case R.id.show_gif:
                    intent =new Intent(context, ShowCommentImage.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("imageUrl",mData.get(position).getGif().getImages().get(0));
                    int gifSize[] = new int[]{mData.get(position).getGif().getWidth(),mData.get(position).getGif().getHeight()};
                    bundle1.putIntArray("size",gifSize);
                    intent.putExtra("image",bundle1);
                    break;
                case R.id.show_image:
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
