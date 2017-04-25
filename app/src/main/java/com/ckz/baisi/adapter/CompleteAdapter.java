package com.ckz.baisi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ckz.baisi.R;
import com.ckz.baisi.unitls.SPUtils;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadEvent;
import zlc.season.rxdownload2.entity.DownloadRecord;

/**
 * Created by CKZ on 2017/4/23.
 */

public class CompleteAdapter extends BaseAdapter {
    private Context mContext;
    private List<DownloadRecord> mData;
    private RxDownload mRxDownload;

    public CompleteAdapter(Context context,List<DownloadRecord> mData){
        this.mContext = context;
        this.mData = mData;
        mRxDownload = RxDownload.getInstance(mContext);
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
        CompleteViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.complete_item,parent,false);
            holder = new CompleteViewHolder();
            holder.videoIcon = (ImageView) convertView.findViewById(R.id.video_icon);
            holder.videoTitle = (TextView) convertView.findViewById(R.id.video_title);
            holder.videoSize = (TextView) convertView.findViewById(R.id.video_size);
            convertView.setTag(holder);
        }else {
            holder = (CompleteViewHolder) convertView.getTag();
        }
        setVideoImage(holder,position);
        setTitle(holder,position);
        setSize(holder,position);
        return convertView;
    }
    private void setVideoImage(CompleteViewHolder holder, int position){
        String imageUrl = SPUtils.getStringSp(mContext,mData.get(position).getUrl()+"_image");
        Glide.with(mContext).load(imageUrl).placeholder(R.mipmap.bg_activities_item_end_transparent)
                .diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate().into(holder.videoIcon);
    }
    private void setTitle(CompleteViewHolder holder,int position){
        String title = SPUtils.getStringSp(mContext,mData.get(position).getUrl()+"_title");
        holder.videoTitle.setText(title);
    }
    private void setSize(final CompleteViewHolder holder, int position){
        mRxDownload.receiveDownloadStatus(mData.get(position).getUrl()).subscribe(new Consumer<DownloadEvent>() {
            @Override
            public void accept(@NonNull DownloadEvent downloadEvent) throws Exception {
                holder.videoSize.setText(downloadEvent.getDownloadStatus().getFormatTotalSize());
            }
        });
    }
    class CompleteViewHolder{
        ImageView videoIcon;
        TextView videoTitle,videoSize;
    }
}
