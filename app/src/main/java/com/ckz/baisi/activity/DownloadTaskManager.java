package com.ckz.baisi.activity;


import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ckz.baisi.R;
import com.ckz.baisi.fragment.download.CompleteFragment;
import com.ckz.baisi.fragment.download.DownloadingFragment;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadFlag;
import zlc.season.rxdownload2.entity.DownloadRecord;

public class DownloadTaskManager extends AppCompatActivity implements View.OnClickListener{
    private TextView managerback;
    private TextView btndownload;
    private TextView btncomplete;
    private ImageView downloaddelete;
    private FragmentManager manager;

    private DownloadingFragment DLFragment;
    private CompleteFragment CPFragment;

    private RxDownload mRxDownload;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_manager);
        mRxDownload = RxDownload.getInstance(this);
        initView();
    }

        private void initView() {

            downloaddelete = (ImageView) findViewById(R.id.download_delete);
            btncomplete = (TextView) findViewById(R.id.btn_complete);
            btndownload = (TextView) findViewById(R.id.btn_download);
            managerback = (TextView) findViewById(R.id.manager_back);
            manager = getSupportFragmentManager();
            btndownload.setOnClickListener(this);
            btncomplete.setOnClickListener(this);
            managerback.setOnClickListener(this);
            downloaddelete.setOnClickListener(this);
            btndownload.performClick();

            builder = new AlertDialog.Builder(this);
        }
    private void hideAllFragment(FragmentTransaction transaction) {
        if (DLFragment !=null) transaction.hide(DLFragment);
        if (CPFragment !=null) transaction.hide(CPFragment);
    }

    private void resetSeclect(){
        btncomplete.setSelected(false);
        btndownload.setSelected(false);
        btncomplete.setBackgroundColor(Color.parseColor("#00000000"));
        btndownload.setBackgroundColor(Color.parseColor("#00000000"));
    }
    @Override
    public void onClick(View v) {
        final FragmentTransaction transaction = manager.beginTransaction();
        hideAllFragment(transaction);
        switch (v.getId()){
            case R.id.manager_back:
                finish();
                break;
            case R.id.btn_download:
                if (!btndownload.isSelected()) {
                    downloaddelete.setVisibility(View.GONE);
                    resetSeclect();
                    btndownload.setSelected(true);
                    btndownload.setBackgroundResource(R.color.white);

                    DLFragment = new DownloadingFragment();
                    transaction.replace(R.id.download_fragment, DLFragment);
                }else {
                    transaction.show(DLFragment);
                }
                break;
            case R.id.btn_complete:
                if (!btncomplete.isSelected()) {
                    downloaddelete.setVisibility(View.VISIBLE);
                    resetSeclect();
                    btncomplete.setSelected(true);
                    btncomplete.setBackgroundResource(R.color.white);

                    CPFragment = new CompleteFragment();
                    transaction.replace(R.id.download_fragment, CPFragment);
                }else {
                    transaction.show(CPFragment);
                }
                break;
            case R.id.download_delete:
                transaction.show(CPFragment);

                dialog = builder.setTitle("删除文件")
                        .setMessage("是否删除全部视频文件")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               mRxDownload.getTotalDownloadRecords().subscribe(new Consumer<List<DownloadRecord>>() {
                                   @Override
                                   public void accept(@NonNull List<DownloadRecord> downloadRecords) throws Exception {
                                       for (DownloadRecord each : downloadRecords){
                                           if (each.getFlag() == DownloadFlag.COMPLETED){
                                               mRxDownload.deleteServiceDownload(each.getUrl(),true).subscribe();
                                               CPFragment.resetData();
                                           }
                                       }
                                   }
                               });
                            }
                        }).create();
                dialog.show();
                break;

                
        }
        transaction.commit();
    }
}
