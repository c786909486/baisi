package com.ckz.baisi.adapter;

import android.content.Context;
import android.util.Log;
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
import zlc.season.rxdownload2.entity.DownloadFlag;
import zlc.season.rxdownload2.entity.DownloadRecord;

/**
 * Created by CKZ on 2017/4/21.
 */

public class DownloadingAdapter extends BaseAdapter {
    private Context mContext;
    private List<DownloadRecord> mData;
    private RxDownload mRxDownload;

    public DownloadingAdapter(Context context,List<DownloadRecord> mData){
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
        DownloadViewHolder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.downloading_item,parent,false);
            holder = new DownloadViewHolder();
            holder.videoIcon = (ImageView) convertView.findViewById(R.id.video_icon);
            holder.videoTitle = (TextView) convertView.findViewById(R.id.video_title);
            holder.videoSize = (TextView) convertView.findViewById(R.id.video_size);
            holder.downloadProgress = (ProgressBar) convertView.findViewById(R.id.download_progress);
            convertView.setTag(holder);
        }else {
            holder = (DownloadViewHolder) convertView.getTag();
        }
        setVideoImage(holder,position);
        setVideoText(holder,position);
        setProgress(holder,position);
        return convertView;
    }

    /**
     * 设置视频缩略图
     * @param holder
     * @param position
     */
    private void setVideoImage(DownloadViewHolder holder,int position){
        String imageUrl = SPUtils.getStringSp(mContext,mData.get(position).getUrl()+"_image");
        Glide.with(mContext).load(imageUrl).placeholder(R.mipmap.bg_activities_item_end_transparent)
                .diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate().into(holder.videoIcon);
    }

    private void setVideoText(DownloadViewHolder holder,int position){
        String title = SPUtils.getStringSp(mContext,mData.get(position).getUrl()+"_title");
        holder.videoTitle.setText(title);
    }

    private void setProgress(final DownloadViewHolder holder, final int position){
        mRxDownload.receiveDownloadStatus(mData.get(position).getUrl()).subscribe(new Consumer<DownloadEvent>() {
            @Override
            public void accept(@NonNull DownloadEvent downloadEvent) throws Exception {
               switch (downloadEvent.getFlag()){
                   case DownloadFlag.STARTED:
                   case DownloadFlag.PAUSED:
                   case DownloadFlag.WAITING:
                       holder.downloadProgress.setProgress((int) downloadEvent.getDownloadStatus().getPercentNumber());
                       holder.videoSize.setText(downloadEvent.getDownloadStatus().getFormatDownloadSize()+"/"+downloadEvent.getDownloadStatus().getFormatTotalSize());
                       break;
                   case DownloadFlag.COMPLETED:
                       mData.remove(position);
                       notifyDataSetChanged();
                       break;
                   case DownloadFlag.DELETED:
                       mData.remove(position);
                       notifyDataSetChanged();
                       mRxDownload.deleteServiceDownload(mData.get(position).getUrl(),true);
                       break;

               }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {

            }
        });
    }

    class DownloadViewHolder{
        ImageView videoIcon;
        TextView videoTitle,videoSize;
        ProgressBar downloadProgress;
    }
}
