package com.ckz.baisi.unitls;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.ckz.baisi.R;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadEvent;
import zlc.season.rxdownload2.entity.DownloadFlag;

/**
 * Created by CKZ on 2017/4/13.
 */

public class MyDownloadHelper {
    private Context mContext;
    public RxDownload mRxDownload;
    private String filePath;
    private MyNotification myNotification;
    private String progress;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x123:
                    myNotification.updateNotification(msg.arg1, (Long) msg.obj);
            }
        }
    };

    public MyDownloadHelper(Context mContext){
        this.mContext = mContext;
        mRxDownload = RxDownload.getInstance(mContext).maxThread(3).maxRetryCount(3).maxDownloadNumber(5);
        try {
            filePath = Environment.getExternalStorageDirectory().getCanonicalPath()+"/__不得视频";
        } catch (IOException e) {
            e.printStackTrace();
        }
        myNotification = new MyNotification(mContext);
    }
    public void startDowanload(final String url, final String name, final int notifyId){
        if (mRxDownload.getRealFiles(url)!=null){
            Toast.makeText(mContext,"该文件已下载", Toast.LENGTH_SHORT).show();
        }else {
            mRxDownload.serviceDownload(url, name, filePath)
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(@NonNull Object o) throws Exception {
//                            NotificationUtil.createProgressNotification(mContext, name, "开始下载", R.mipmap.ic_launcher, notifyId);
                            myNotification.startNotification(name, "正在下载...", R.mipmap.icon_new, notifyId);

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            Toast.makeText(mContext, "添加任务失败", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        
        mRxDownload.receiveDownloadStatus(url).subscribe(new Consumer<DownloadEvent>() {
            @Override
            public void accept(@NonNull final DownloadEvent downloadEvent) throws Exception {
                switch (downloadEvent.getFlag()){
                    case DownloadFlag.NORMAL:
                        Log.d("TAG","norml");

                        break;
                    case DownloadFlag.STARTED:
                        long progress = downloadEvent.getDownloadStatus().getPercentNumber();
                        Log.d("TAG", progress+"");
//                        NotificationUtil.updateNotification(notifyId, progress);
//
                        break;
                    case DownloadFlag.PAUSED:
                        Log.d("TAG","pause");
                        break;
                    case DownloadFlag.COMPLETED:
                        Log.d("TAG","complete");
                       if ( getFiles(url).length == (int) downloadEvent.getDownloadStatus().getTotalSize())
                        myNotification.completeNotification(name, "下载完成", R.mipmap.ic_launcher, notifyId);
//                            NotificationUtil.completeNotification(mContext,notifyId,R.mipmap.ic_launcher);
                }
            }
        });
    }

    public File[] getFiles(String url){
        File[] files = mRxDownload.getRealFiles(url);
        return files;
    }

    public void deleteFile(String url){
        mRxDownload.deleteServiceDownload(url,true).subscribe();
    }
}
