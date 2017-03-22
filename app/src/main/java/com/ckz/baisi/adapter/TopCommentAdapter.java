package com.ckz.baisi.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ckz.baisi.R;
import com.ckz.baisi.activity.CommentActivity;
import com.ckz.baisi.activity.UserDetilsActivity;
import com.ckz.baisi.bean.BaisiData;
import com.ckz.baisi.unitls.TextUtils;
import java.io.IOException;
import java.util.List;

/**
 * Created by CKZ on 2017/3/1.
 */

public class TopCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TXTCOMMENT = 0;
    private static final int VOICECOMMENT = 1;
    private static final int CURRENT_STATE_RELEASE = 0;
    private static final int CURRENT_STATE_PLAYING = 1;
    private int currentState = CURRENT_STATE_RELEASE;
    private List<BaisiData.ListBean.TopCommentsBean> mData;
    private Context context;
    private MediaPlayer mPlayer;
    private AnimationDrawable animationDrawable;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }
    private BaisiData.ListBean listBean;

    public TopCommentAdapter(Context context, List<BaisiData.ListBean.TopCommentsBean> mData, BaisiData.ListBean listBean){
        this.context = context;
        this.mData = mData;
        this.listBean = listBean;
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).getCmt_type().equals("audio")){
            return VOICECOMMENT;
        }else {
            return TXTCOMMENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType){
            case VOICECOMMENT:
                view = LayoutInflater.from(context).inflate(R.layout.top_voice_comment_item,null);
                viewHolder = new VoiceCommentViewHolder(view);
                break;
            case TXTCOMMENT:
                view = LayoutInflater.from(context).inflate(R.layout.top_txt_comment_item,null);
                viewHolder = new TxtCommentViewHolder(view);
                break;
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
      switch (getItemViewType(position)){
          case VOICECOMMENT:
              VoiceCommentViewHolder viewHolder = (VoiceCommentViewHolder) holder;
              setVoicePlay(viewHolder,position);
              break;
          case TXTCOMMENT:
              TxtCommentViewHolder txtCommentViewHolder = (TxtCommentViewHolder) holder;
              setText(txtCommentViewHolder,position);
              break;
      }
    }

    private void setText(TxtCommentViewHolder holder,int position){
        String name = mData.get(position).getU().getName();
        String comment = mData.get(position).getContent();
        SpannableString string = new SpannableString(TextUtils.ToSBC(name+":"+comment));
        MyClickableSpan span = new MyClickableSpan(position);
        string.setSpan(span,0,name.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.txt_comment.setMovementMethod(LinkMovementMethod.getInstance());
        holder.txt_comment.setHighlightColor(Color.parseColor("#ffffff"));
        holder.txt_comment.setText(string);
    }

    private void setVoicePlay(final VoiceCommentViewHolder holder, final int position){
        String name = mData.get(position).getU().getName();
        SpannableString string = new SpannableString(TextUtils.ToSBC(name+":"));
        MyClickableSpan span = new MyClickableSpan(position);
        string.setSpan(span,0,name.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.userName.setMovementMethod(LinkMovementMethod.getInstance());
        holder.userName.setHighlightColor(Color.parseColor("#ffffff"));
        holder.userName.setText(string);
        holder.voice_duration.setText(mData.get(position).getAudio().getDuration()+"''");
        setPlayer(holder,position);
    }

    //播放功能
    private void setPlayer(final VoiceCommentViewHolder holder, final int position){
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
                            if (!mPlayer.isPlaying()){
                                currentState = CURRENT_STATE_RELEASE;
                                hideVoiceViews(holder);
                                holder.voice_play_icon.setVisibility(View.VISIBLE);
                            }
                        }
                    });

                    currentState = CURRENT_STATE_PLAYING;
                }else {

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

    private void hideVoiceViews(VoiceCommentViewHolder holder){
        holder.loading.setVisibility(View.GONE);
        holder.voice_play_icon.setVisibility(View.GONE);
        holder.voice_play_ani.setVisibility(View.GONE);
    }

    public int getItemCount() {
        return mData.size();
    }

    //设置点击事件
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    //设置长按
    public interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);
    }

    class MyClickableSpan extends ClickableSpan{
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

    class TxtCommentViewHolder extends RecyclerView.ViewHolder{
        TextView txt_comment;
        public TxtCommentViewHolder(View itemView) {
            super(itemView);
            txt_comment = (TextView) itemView.findViewById(R.id.top_text_comment);
            txt_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setIntent();
                }
            });
        }
    }

    class VoiceCommentViewHolder extends RecyclerView.ViewHolder{
        ImageView voice_play_btn,voice_play_ani,voice_play_icon;
        ProgressBar loading;
        TextView userName;
        RelativeLayout voice_play_area;
        TextView voice_duration;
        MediaPlayer player;
        public VoiceCommentViewHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.top_voice_user_name);
            voice_play_btn = (ImageView) itemView.findViewById(R.id.voice_play_btn);
            voice_play_ani = (ImageView) itemView.findViewById(R.id.voide_play_ani);
            voice_play_icon = (ImageView) itemView.findViewById(R.id.voide_play_icon);
            loading = (ProgressBar) itemView.findViewById(R.id.voice_loading);
            voice_duration = (TextView) itemView.findViewById(R.id.voice_duration);
            voice_play_area = (RelativeLayout) itemView.findViewById(R.id.top_voice_play_area);
            voice_play_area.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setIntent();
                }
            });
            player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
    }
    private void setIntent(){
        Intent intent =  new Intent(context,CommentActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("Data",listBean);
        intent.putExtra("Id",bundle);
        context.startActivity(intent);
    }
}
