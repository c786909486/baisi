package com.ckz.baisi.fragment.download;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ckz.baisi.R;
import com.ckz.baisi.adapter.DownloadingAdapter;
import com.ckz.baisi.adapter.MyContentAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadEvent;
import zlc.season.rxdownload2.entity.DownloadFlag;
import zlc.season.rxdownload2.entity.DownloadRecord;

/**
 * Created by CKZ on 2017/4/20.
 */

public class DownloadingFragment extends Fragment implements AdapterView.OnItemClickListener {
    private RxDownload mRxDownload;
    private ListView downloanging;
    private DownloadingAdapter mAdapter;
    private List<DownloadRecord> mData;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.downloadl_fragment,null);
        downloanging = (ListView) view.findViewById(R.id.download_list);
        downloanging.setOnItemClickListener(this);
        mRxDownload = RxDownload.getInstance(getContext());
        mData = new ArrayList<>();
        mAdapter = new DownloadingAdapter(getContext(),mData);
        downloanging.setAdapter(mAdapter);
        getData();
        return view;
    }

    private void getData(){
        mRxDownload.getTotalDownloadRecords().subscribe(new Consumer<List<DownloadRecord>>() {
            @Override
            public void accept(@NonNull List<DownloadRecord> downloadRecords) throws Exception {
                for (DownloadRecord each : downloadRecords){
                    if (each.getFlag() == DownloadFlag.STARTED){
                        mData.add(each);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view,final int position, long id) {
        mRxDownload.receiveDownloadStatus(mData.get(position).getUrl()).subscribe(new Consumer<DownloadEvent>() {
            @Override
            public void accept(@NonNull DownloadEvent downloadEvent) throws Exception {
                switch (downloadEvent.getFlag()){
                    case DownloadFlag.WAITING:
                    case DownloadFlag.STARTED:
                        mRxDownload.pauseServiceDownload(mData.get(position).getUrl());
                        break;
                    case DownloadFlag.PAUSED:
                        mRxDownload.serviceDownload(mData.get(position).getUrl()).subscribe();
                        break;

                }
            }
        });
    }
}
