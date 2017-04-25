package com.ckz.baisi.fragment.download;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ckz.baisi.R;
import com.ckz.baisi.adapter.CompleteAdapter;
import com.ckz.baisi.adapter.DownloadingAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadFlag;
import zlc.season.rxdownload2.entity.DownloadRecord;

/**
 * Created by CKZ on 2017/4/20.
 */

public class CompleteFragment extends Fragment implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{
    private RxDownload mRxDownload;
    private ListView downloanging;
    private CompleteAdapter mAdapter;
    private List<DownloadRecord> mData;

    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.downloadl_fragment,null);
        builder = new AlertDialog.Builder(getContext());
        downloanging = (ListView) view.findViewById(R.id.download_list);
        downloanging.setOnItemClickListener(this);
        downloanging.setOnItemLongClickListener(this);
        mRxDownload = RxDownload.getInstance(getContext());
        mData = new ArrayList<>();
        mAdapter = new CompleteAdapter(getContext(),mData);
        downloanging.setAdapter(mAdapter);
        getData();
        return view;
    }

    private void getData(){
        mRxDownload.getTotalDownloadRecords().subscribe(new Consumer<List<DownloadRecord>>() {
            @Override
            public void accept(@NonNull List<DownloadRecord> downloadRecords) throws Exception {
                mData.clear();
                for (DownloadRecord each : downloadRecords){
                    if (each.getFlag() == DownloadFlag.COMPLETED){
                        mData.add(0,each);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
    public void resetData(){
        mData.clear();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        File file = mRxDownload.getRealFiles(mData.get(position).getUrl())[0];
        if (file!=null){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file),"video/mp4");
            startActivity(intent);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        String[] lists = new String[]{"删除视频文件"};
       alertDialog =  builder.setItems(lists, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        mRxDownload.deleteServiceDownload(mData.get(position).getUrl(),true).subscribe();
                        mData.remove(position);
                        mAdapter.notifyDataSetChanged();
                        break;
                }
            }
        }).create();
        alertDialog.show();
        return true;
    }
}
