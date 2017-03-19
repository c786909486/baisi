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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.ckz.baisi.R;
import com.ckz.baisi.activity.PlayVideoActivity;
import com.ckz.baisi.activity.ShowCommentImage;
import com.ckz.baisi.bean.CommentBean;
import com.ckz.baisi.unitls.ACache;
import com.ckz.baisi.unitls.MyIntegerUtils;
import com.ckz.baisi.view.CircleImageView;

import java.io.IOException;
import java.util.List;



public class HotCommentAdapter extends BaseAdapter {

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
    private Context context;
    private LayoutInflater inflater;
    private List<CommentBean.HotBean.ListBean> mData;
    private ACache mACache;
    private InnerHotCommentAdapter adapter;
    public HotCommentAdapter(Context context, List<CommentBean.HotBean.ListBean> mData){
        this.context = context;
        this.mData = mData;
        inflater = LayoutInflater.from(context);
        mACache = ACache.get(context);
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
    public int getItemViewType(int position) {
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
    }

    @Override
    public int getViewTypeCount() {
        return 5;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextHolder textHolder = null;
        ImageHolder imageHolder = null;
        GifHolder gifHolder = null;
        VideoHolder videoHolder = null;
        VoiceHolder voiceHolder = null;
        MyClickListener listener = new MyClickListener(position);
        if (convertView == null){
            switch (getItemViewType(position)){
                case TXTCOMMENTTYPE:
                    textHolder = new TextHolder();
                    convertView = inflater.inflate(R.layout.normal_comment_item_layout,parent,false);
                    textHolder.userIcon = (CircleImageView) convertView.findViewById(R.id.comment_user_icon);
                    textHolder.userSex = (ImageView) convertView.findViewById(R.id.comment_user_sex);
                    textHolder.userName = (TextView) convertView.findViewById(R.id.comment_user_name);
                    textHolder.dingCount = (TextView) convertView.findViewById(R.id.comment_right_ding);
                    textHolder.caiCount = (TextView) convertView.findViewById(R.id.comment_right_cai);
                    textHolder.userLikeCount = (TextView) convertView.findViewById(R.id.user_like_count);
                    textHolder.txt_comment = (TextView) convertView.findViewById(R.id.txt_comment);
                    textHolder.floorList = (ListView) convertView.findViewById(R.id.floor_list);
                    convertView.setTag(textHolder);
                    break;
                case IMAGECOMMENTTYPE:
                    imageHolder = new ImageHolder();
                    convertView = inflater.inflate(R.layout.normal_comment_image_layout,parent,false);
                    imageHolder.userIcon = (CircleImageView) convertView.findViewById(R.id.comment_user_icon);
                    imageHolder.userSex = (ImageView) convertView.findViewById(R.id.comment_user_sex);
                    imageHolder.userName = (TextView) convertView.findViewById(R.id.comment_user_name);
                    imageHolder.dingCount = (TextView) convertView.findViewById(R.id.comment_right_ding);
                    imageHolder.caiCount = (TextView) convertView.findViewById(R.id.comment_right_cai);
                    imageHolder.userLikeCount = (TextView) convertView.findViewById(R.id.user_like_count);
                    imageHolder.show_image = (ImageView) convertView.findViewById(R.id.show_image);
                    imageHolder.txt_comment = (TextView) convertView.findViewById(R.id.txt_comment);
                    imageHolder.floorList = (ListView) convertView.findViewById(R.id.floor_list);
                    convertView.setTag(imageHolder);
                    break;
                case GIFCOMMENTTYPE:
                    gifHolder = new GifHolder();
                    convertView = inflater.inflate(R.layout.normal_comment_gif_layout,parent,false);
                    gifHolder.userIcon = (CircleImageView) convertView.findViewById(R.id.comment_user_icon);
                    gifHolder.userSex = (ImageView) convertView.findViewById(R.id.comment_user_sex);
                    gifHolder.userName = (TextView) convertView.findViewById(R.id.comment_user_name);
                    gifHolder.dingCount = (TextView) convertView.findViewById(R.id.comment_right_ding);
                    gifHolder.caiCount = (TextView) convertView.findViewById(R.id.comment_right_cai);
                    gifHolder.userLikeCount = (TextView) convertView.findViewById(R.id.user_like_count);
                    gifHolder.show_gif = (ImageView) convertView.findViewById(R.id.show_gif);
                    gifHolder.txt_comment = (TextView) convertView.findViewById(R.id.txt_comment);
                    gifHolder.floorList = (ListView) convertView.findViewById(R.id.floor_list);
                    convertView.setTag(gifHolder);
                    break;
                case VIDEOCOMMENTTYPE:
                    videoHolder = new VideoHolder();
                    convertView = inflater.inflate(R.layout.normal_comment_video_layout,parent,false);
                    videoHolder.userIcon = (CircleImageView) convertView.findViewById(R.id.comment_user_icon);
                    videoHolder.userSex = (ImageView) convertView.findViewById(R.id.comment_user_sex);
                    videoHolder.userName = (TextView) convertView.findViewById(R.id.comment_user_name);
                    videoHolder.dingCount = (TextView) convertView.findViewById(R.id.comment_right_ding);
                    videoHolder.caiCount = (TextView) convertView.findViewById(R.id.comment_right_cai);
                    videoHolder.userLikeCount = (TextView) convertView.findViewById(R.id.user_like_count);
                    videoHolder.show_video_thumbnail = (ImageView) convertView.findViewById(R.id.show_video_thumbnail);
                    videoHolder.txt_comment = (TextView) convertView.findViewById(R.id.txt_comment);
                    videoHolder.videoPlay = (ImageView) convertView.findViewById(R.id.video_play_icon);
                    videoHolder.floorList = (ListView) convertView.findViewById(R.id.floor_list);
                    convertView.setTag(videoHolder);
                    break;
                case VOICECOMMENTTYPE:
                    voiceHolder = new VoiceHolder();
                    convertView = inflater.inflate(R.layout.normal_comment_voice_layout,parent,false);
                    voiceHolder.userIcon = (CircleImageView) convertView.findViewById(R.id.comment_user_icon);
                    voiceHolder.userSex = (ImageView) convertView.findViewById(R.id.comment_user_sex);
                    voiceHolder.userName = (TextView) convertView.findViewById(R.id.comment_user_name);
                    voiceHolder.dingCount = (TextView) convertView.findViewById(R.id.comment_right_ding);
                    voiceHolder.caiCount = (TextView) convertView.findViewById(R.id.comment_right_cai);
                    voiceHolder.userLikeCount = (TextView) convertView.findViewById(R.id.user_like_count);
                    voiceHolder.voice_play_icon = (ImageView) convertView.findViewById(R.id.voide_play_icon);
                    voiceHolder.voice_play_ani = (ImageView) convertView.findViewById(R.id.voide_play_ani);
                    voiceHolder.voice_play_btn = (ImageView) convertView.findViewById(R.id.voice_play_btn);
                    voiceHolder.loading = (ProgressBar) convertView.findViewById(R.id.voice_loading);
                    voiceHolder.floorList = (ListView) convertView.findViewById(R.id.floor_list);
                    voiceHolder.voiceDuration = (TextView) convertView.findViewById(R.id.voice_duration);
                    convertView.setTag(voiceHolder);
                    break;
            }
        }else {
            switch (getItemViewType(position)){
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
        switch (getItemViewType(position)){
            case TXTCOMMENTTYPE:
                assert textHolder != null;
                setCommandView(textHolder.userIcon,textHolder.userSex,textHolder.userName,textHolder.dingCount,textHolder.caiCount,textHolder.userLikeCount,textHolder.floorList,position);
                textHolder.txt_comment.setText(mData.get(position).getContent());
                break;
            case IMAGECOMMENTTYPE:
                assert imageHolder != null;
                setCommandView(imageHolder.userIcon,imageHolder.userSex,imageHolder.userName,imageHolder.dingCount,imageHolder.caiCount,imageHolder.userLikeCount,imageHolder.floorList,position);
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
                setCommandView(gifHolder.userIcon,gifHolder.userSex,gifHolder.userName,gifHolder.dingCount,gifHolder.caiCount,gifHolder.userLikeCount,gifHolder.floorList,position);
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
                setCommandView(videoHolder.userIcon,videoHolder.userSex,videoHolder.userName,videoHolder.dingCount,videoHolder.caiCount,videoHolder.userLikeCount,videoHolder.floorList,position);
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
                setCommandView(voiceHolder.userIcon,voiceHolder.userSex,voiceHolder.userName,voiceHolder.dingCount,voiceHolder.caiCount,voiceHolder.userLikeCount,voiceHolder.floorList,position);
                setPlayer(voiceHolder,position);
        }
        return convertView;
    }

    private void setCommandView(final CircleImageView userIcon, ImageView userSex, TextView userName, final TextView dingCount ,TextView caiCount, TextView userLikeCount,ListView floorList, final int position){
        //用户头像
        Glide.with(context).load(mData.get(position).getUser().getProfile_image())
                .placeholder(R.mipmap.list_view_release).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate().into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                userIcon.setImageDrawable(glideDrawable);
            }
        });
        //用户性别
        if (mData.get(position).getUser().getSex().equals("m")){
            userSex.setImageResource(R.mipmap.sex_men);
        }else if (mData.get(position).getUser().getSex().equals("f")){
            userSex.setImageResource(R.mipmap.sex_women);
        }
        //用户名
        userName.setText(mData.get(position).getUser().getUsername());
        dingCount.setSelected(false);
        caiCount.setSelected(false);


        setClick(dingCount,caiCount,position);
        //用户关注数
        userLikeCount.setText(MyIntegerUtils.int2k(Integer.valueOf(mData.get(position).getUser().getTotal_cmt_like_count())));
        int count = Integer.valueOf(mData.get(position).getUser().getTotal_cmt_like_count());
        if (count<1000){
            userLikeCount.setBackgroundResource(R.drawable.bg_gz);
        }else if (count>=1000 && count<10000){
            userLikeCount.setBackgroundResource(R.drawable.bg_gz_k);
        }
        //设置回复列表
        if (mData.get(position).getPrecmts()!=null){
            adapter = new InnerHotCommentAdapter(context,mData.get(position).getPrecmts());
            floorList.setVisibility(View.VISIBLE);
            floorList.setAdapter(adapter);
            floorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(context,"弹出inner点赞窗口",Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            floorList.setVisibility(View.GONE);
        }
    }

    //顶踩点击
    private void setClick(final TextView ding, final TextView cai, final int position){
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

    private class TextHolder{
        //command
        CircleImageView userIcon;
        TextView userName;
        ImageView userSex;
        TextView dingCount;
        TextView caiCount;
        TextView userLikeCount;
        //文字评论
        TextView txt_comment;
        //楼层列表
        ListView floorList;
    }
    private class ImageHolder{
        //command
        CircleImageView userIcon;
        TextView userName;
        ImageView userSex;
        TextView dingCount;
        TextView caiCount;
        TextView userLikeCount;
        //图片
        ImageView show_image;
        //文字评论
        TextView txt_comment;
        //楼层列表
        ListView floorList;
    }
    private class GifHolder{
        //command
        CircleImageView userIcon;
        TextView userName;
        ImageView userSex;
        TextView dingCount;
        TextView caiCount;
        TextView userLikeCount;
        //图片
        ImageView show_gif;
        //文字评论
        TextView txt_comment;
        //楼层列表
        ListView floorList;
    }
    private class VideoHolder{
        //command
        CircleImageView userIcon;
        TextView userName;
        ImageView userSex;
        TextView dingCount;
        TextView caiCount;
        TextView userLikeCount;
        //图片
        ImageView show_video_thumbnail;
        ImageView videoPlay;
        //文字评论
        TextView txt_comment;
        //楼层列表
        ListView floorList;
    }
    private class VoiceHolder{
        //command
        CircleImageView userIcon;
        TextView userName;
        ImageView userSex;
        TextView dingCount;
        TextView caiCount;
        TextView userLikeCount;
        //楼层列表
        ListView floorList;
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
