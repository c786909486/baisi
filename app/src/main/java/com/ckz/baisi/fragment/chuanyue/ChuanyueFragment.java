package com.ckz.baisi.fragment.chuanyue;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ckz.baisi.R;
import com.ckz.baisi.activity.ChuanyueActivity;
import com.ckz.baisi.adapter.MyContentAdapter;
import com.ckz.baisi.appliction.MyAppliction;
import com.ckz.baisi.bean.BaisiData;
import com.ckz.baisi.fragment.ZuiXinFragment;
import com.ckz.baisi.request.GsonRequest;
import com.ckz.baisi.unitls.ACache;
import com.ckz.baisi.unitls.MyToastUtils;
import com.ckz.baisi.unitls.SPUtils;
import com.ckz.baisi.view.BudejieRefresh;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by CKZ on 2017/3/20.
 */

public class ChuanyueFragment extends Fragment implements View.OnClickListener{
    private String content,loadMoreUrl;
    private XRecyclerView jinghuaRecycler;
    private List<BaisiData.ListBean> mData;
    private MyContentAdapter adapter;
    public TwinklingRefreshLayout refreshLayout;
    private BudejieRefresh headView;
    //刷新时间差
    private long timeCha;
    private TextView chuanyueAgain;
    private ACache mACache;
    private BaisiData baisi;
    private View bottomView;

    private Timer timer = new Timer();


    private static final String url[] = ChuanyueActivity.url;

    public String getContent(){
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chuanyue_content,container,false);
        init(view);
        return view;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 123:
                    headView.refreshTime.setText(setLastTime());
            }
        }
    };

    private void init(View view) {
        mACache = ACache.get(getContext());
        bottomView = LayoutInflater.from(getContext()).inflate(R.layout.chuanyue_bottom_view,null);
        chuanyueAgain = (TextView) bottomView.findViewById(R.id.chuanyue_again);
        chuanyueAgain.setOnClickListener(this);
        jinghuaRecycler = (XRecyclerView) view.findViewById(R.id.content_list_view);
        jinghuaRecycler.setEnabled(false);
        refreshLayout = (TwinklingRefreshLayout) view.findViewById(R.id.refresh_layout);
        mData = new ArrayList<>();
        adapter = new MyContentAdapter(getActivity(),mData);
        jinghuaRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        jinghuaRecycler.setAdapter(adapter);
        headView = new BudejieRefresh(getContext());
        refreshLayout.setHeaderView(headView);
        refreshData();
        headView.refreshTime.setText(setLastTime());
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                handler.sendEmptyMessage(123);
            }
        },1000,1000);
        getCacheData();
        jinghuaRecycler.setFootView(bottomView);
        bottomView.setVisibility(View.GONE);
        if (baisi!=null){
            mData.addAll(baisi.getList());
            adapter.notifyDataSetChanged();
            bottomView.setVisibility(View.VISIBLE);
        }else {
            refreshLayout.startRefresh();

        }

    }
    private String setLastTime(){
        String lastTime = null;
        if (timeCha == System.currentTimeMillis()){
            lastTime = "没有刷新过...";
        }else if (timeCha<3600*1000){
            if (timeCha/60/1000<1){
                lastTime = "最后刷新：刚刚刷新";
            }else {
                lastTime = "最后刷新："+timeCha/60/1000+"分钟前";
            }
        }else {
            lastTime = "最后刷新："+timeCha/60/60/1000+"小时前";
        }
        return lastTime;
    }


    public void refreshData(){
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setOverScrollBottomShow(false);
        refreshLayout.setEnableOverScroll(true);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                //刷新数据
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        timeCha = 0;
                        getData(content);
                        refreshLayout.finishRefreshing();
                    }
                },1000);
            }
        });
    }

    private void getData(String content){
        GsonRequest<BaisiData> gsonRequest = new GsonRequest<BaisiData>(content, BaisiData.class, new Response.Listener<BaisiData>() {
            @Override
            public void onResponse(BaisiData baisiData) {
               String time = baisiData.getList().get(0).getPasstime();
                String cyTime = time.substring(0,10);
                List<BaisiData.ListBean> listBeen = baisiData.getList();
                mData.clear();
                mData.addAll(0,listBeen);
                bottomView.setVisibility(View.VISIBLE);
                saveAllData(baisiData);
                if (getUserVisibleHint())
                    // Toast.makeText(getContext(),"更新了"+num+"条内容",Toast.LENGTH_SHORT).show();
                    MyToastUtils.showRefreshTosat(getContext(),ChuanyueActivity.showToast,"穿越到"+cyTime);
                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(),"请检查网络",Toast.LENGTH_SHORT).show();
            }
        });
        MyAppliction.getRequestQueue().add(gsonRequest);
    }

    //存储个页面data数据
    private void saveAllData(BaisiData baisiData){

        //最新
        if (content.equals(url[0]))  mACache.put("CYQB",baisiData);
        if (content.equals(url[1]))  mACache.put("CYSP",baisiData);
        if (content.equals(url[2]))  mACache.put("CYTP",baisiData);
        if (content.equals(url[3]))  mACache.put("CYDZ",baisiData);
    }
    //获取data数据
    private void getCacheData(){

        //最新
        if (content.equals(url[0]))   baisi = (BaisiData) mACache.getAsObject("CYQB");
        if (content.equals(url[1]))   baisi = (BaisiData) mACache.getAsObject("CYSP");
        if (content.equals(url[2]))   baisi = (BaisiData) mACache.getAsObject("CYTP");
        if (content.equals(url[3]))   baisi = (BaisiData) mACache.getAsObject("CYDZ");
    }
    private void removeAll(){
        mACache.remove("CYQB");
        mACache.remove("CYSP");
        mACache.remove("CYTP");
        mACache.remove("CYDZ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        mACache.remove("CYQB");
        mACache.remove("CYSP");
        mACache.remove("CYTP");
        mACache.remove("CYDZ");
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chuanyue_again:
                if (getUserVisibleHint()){
                    refreshLayout.startRefresh();
                    jinghuaRecycler.scrollToPosition(0);
                }
                break;
        }
    }
}
