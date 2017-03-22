package com.ckz.baisi.fragment.jinghuaContent;

import android.content.Intent;
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
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ckz.baisi.R;
import com.ckz.baisi.activity.CommentActivity;
import com.ckz.baisi.adapter.MyContentAdapter;
import com.ckz.baisi.adapter.TopCommentAdapter;
import com.ckz.baisi.appliction.MyAppliction;
import com.ckz.baisi.bean.BaisiData;
import com.ckz.baisi.fragment.JingHuaFragment;
import com.ckz.baisi.fragment.ZuiXinFragment;
import com.ckz.baisi.request.GsonRequest;
import com.ckz.baisi.unitls.ACache;
import com.ckz.baisi.unitls.MyToastUtils;
import com.ckz.baisi.unitls.SPUtils;
import com.ckz.baisi.view.BudejieRefresh;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by CKZ on 2017/2/7.
 */

public class QuanbuContent extends Fragment {
    private String content,loadMoreUrl;
    private RecyclerView jinghuaRecycler;
    private List<BaisiData.ListBean> mData;
    private MyContentAdapter adapter;
    public TwinklingRefreshLayout refreshLayout;
    private BudejieRefresh headView;
    //刷新时间差
    private long timeCha;
    //加载更多的页数
    private int pageCount;

    private ACache cache;
    private BaisiData baisi;

    private Timer timer = new Timer();

//    private static final String jingXuanUrl[] = JingHuaFragment.url;
    private static final String zuiXinUrl[] = ZuiXinFragment.url;

    public String getContent(){
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_content_layout,container,false);
        cache = ACache.get(getContext());
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
        jinghuaRecycler = (RecyclerView) view.findViewById(R.id.content_list_view);
        refreshLayout = (TwinklingRefreshLayout) view.findViewById(R.id.refresh_layout);
        mData = new ArrayList<>();
        adapter = new MyContentAdapter(getActivity(),mData);
        jinghuaRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        jinghuaRecycler.setAdapter(adapter);
        headView = new BudejieRefresh(getContext());
        refreshLayout.setHeaderView(headView);
//        getTimeCha();
        refreshData();
        headView.refreshTime.setText(setLastTime());
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getTimeCha();
                handler.sendEmptyMessage(123);
            }
        },2000,2000);
        getCacheData();
        if (baisi!=null){
            mData.addAll(0,baisi.getList());
            adapter.notifyDataSetChanged();
        }else {
            refreshLayout.startRefresh();
        }


    }
    private String setLastTime(){
        String lastTime = null;
        if (timeCha == 0){
            lastTime = "没有刷新过...";
        }else if (timeCha<3600*1000){
            if (timeCha/60/1000<1){
                lastTime = "最后刷新：刚刚刷新";
            }else {
                lastTime = "最后刷新："+timeCha/60/1000+"分钟前";
            }
        }else {
            if (timeCha>=24*3600*1000 && timeCha<48*3600*1000){
                lastTime = "最后刷新： 昨天";
            }else {
                lastTime = "最后刷新："+timeCha/60/60/1000+"小时前";
            }
        }
        return lastTime;
    }


    public void refreshData(){
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                ZuiXinFragment.setAni();
                //刷新数据
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData(content,true);
                        saveRefreshTime();
                        getTimeCha();
                        refreshLayout.finishRefreshing();
                        ZuiXinFragment.animator.cancel();
                    }
                },1000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                loadMoreUrl = content.replace("0-20",pageCount+"-20");
                //加载更多
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        savePageCount();
                        getPageCount();
                        getData(loadMoreUrl,false);
                        savePageCount();
                        refreshLayout.finishLoadmore();
                    }
                },1000);
            }
        });
    }

    private void getData(String content, final boolean isRefresh){
        GsonRequest<BaisiData> gsonRequest = new GsonRequest<BaisiData>(content, BaisiData.class, new Response.Listener<BaisiData>() {
            @Override
            public void onResponse(BaisiData baisiData) {
                pageCount = baisiData.getInfo().getNp();
                List<BaisiData.ListBean> listBeen = baisiData.getList();
                int lastNum = mData.size();
                mData.removeAll(listBeen);
                if (isRefresh){
                    mData.addAll(0,listBeen);
                    int nowNum = mData.size();
                    int num = nowNum - lastNum;
                    if (getUserVisibleHint())
                        // Toast.makeText(getContext(),"更新了"+num+"条内容",Toast.LENGTH_SHORT).show();
                        MyToastUtils.showRefreshTosat(getContext(),ZuiXinFragment.showToast,"获取到最新"+num+"条内容");
                }else {
                    mData.addAll(listBeen);
                }
                adapter.notifyDataSetChanged();
                saveAllData();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(),"请检查网络",Toast.LENGTH_SHORT).show();
            }
        });
        MyAppliction.getRequestQueue().add(gsonRequest);
    }

    //根据不同界面存储各自的刷新时间
    private void saveRefreshTime(){
        //最新
        if (content.equals(zuiXinUrl[0]))  SPUtils.putlongSp(getActivity(),"ZXQB",System.currentTimeMillis());
        if (content.equals(zuiXinUrl[1]))  SPUtils.putlongSp(getActivity(),"ZXSP",System.currentTimeMillis());
        if (content.equals(zuiXinUrl[2]))  SPUtils.putlongSp(getActivity(),"ZXTP",System.currentTimeMillis());
        if (content.equals(zuiXinUrl[3]))  SPUtils.putlongSp(getActivity(),"ZXDZ",System.currentTimeMillis());
        if (content.equals(zuiXinUrl[4]))  SPUtils.putlongSp(getActivity(),"ZXYC",System.currentTimeMillis());
        if (content.equals(zuiXinUrl[5]))  SPUtils.putlongSp(getActivity(),"ZXWH",System.currentTimeMillis());
        if (content.equals(zuiXinUrl[6]))  SPUtils.putlongSp(getActivity(),"ZXMN",System.currentTimeMillis());
        if (content.equals(zuiXinUrl[7]))  SPUtils.putlongSp(getActivity(),"ZXLZS",System.currentTimeMillis());
        if (content.equals(zuiXinUrl[8]))  SPUtils.putlongSp(getActivity(),"ZXYX",System.currentTimeMillis());

    }

    //不同界面获取不同时间差
    private void getTimeCha(){
        //最新
        if (content.equals(zuiXinUrl[0]))  timeCha = System.currentTimeMillis()- SPUtils.getlongSp(getActivity(),"ZXQB");
        if (content.equals(zuiXinUrl[1]))  timeCha = System.currentTimeMillis()- SPUtils.getlongSp(getActivity(),"ZXSP");
        if (content.equals(zuiXinUrl[2]))  timeCha = System.currentTimeMillis()- SPUtils.getlongSp(getActivity(),"ZXTP");
        if (content.equals(zuiXinUrl[3]))  timeCha = System.currentTimeMillis()- SPUtils.getlongSp(getActivity(),"ZXDZ");
        if (content.equals(zuiXinUrl[4]))  timeCha = System.currentTimeMillis()- SPUtils.getlongSp(getActivity(),"ZXYC");
        if (content.equals(zuiXinUrl[5]))  timeCha = System.currentTimeMillis()- SPUtils.getlongSp(getActivity(),"ZXWH");
        if (content.equals(zuiXinUrl[6]))  timeCha = System.currentTimeMillis()- SPUtils.getlongSp(getActivity(),"ZXMN");
        if (content.equals(zuiXinUrl[7]))  timeCha = System.currentTimeMillis()- SPUtils.getlongSp(getActivity(),"ZXLZS");
        if (content.equals(zuiXinUrl[8]))  timeCha = System.currentTimeMillis()- SPUtils.getlongSp(getActivity(),"ZXYX");
    }
    //存储个页面data数据
    private void saveAllData(){
        BaisiData baisiData = new BaisiData();
        baisiData.setList(mData);
        //最新
        if (content.equals(zuiXinUrl[0]))  cache.put("ZXQBD",baisiData,ACache.TIME_DAY);
        if (content.equals(zuiXinUrl[1]))  cache.put("ZXSPD",baisiData,ACache.TIME_DAY);
        if (content.equals(zuiXinUrl[2]))  cache.put("ZXTPD",baisiData,ACache.TIME_DAY);
        if (content.equals(zuiXinUrl[3]))  cache.put("ZXDZD",baisiData,ACache.TIME_DAY);
        if (content.equals(zuiXinUrl[4]))  cache.put("ZXYCD",baisiData,ACache.TIME_DAY);
        if (content.equals(zuiXinUrl[5]))  cache.put("ZXWHD",baisiData,ACache.TIME_DAY);
        if (content.equals(zuiXinUrl[6]))  cache.put("ZXMND",baisiData,ACache.TIME_DAY);
        if (content.equals(zuiXinUrl[7]))  cache.put("ZXLZSD",baisiData,ACache.TIME_DAY);
        if (content.equals(zuiXinUrl[8]))  cache.put("ZXYXD",baisiData,ACache.TIME_DAY);
    }
    //获取data数据
    private void getCacheData(){

        //最新
        if (content.equals(zuiXinUrl[0]))   baisi = (BaisiData) cache.getAsObject("ZXQBD");
        if (content.equals(zuiXinUrl[1]))   baisi = (BaisiData) cache.getAsObject("ZXSPD");
        if (content.equals(zuiXinUrl[2]))   baisi = (BaisiData) cache.getAsObject("ZXTPD");
        if (content.equals(zuiXinUrl[3]))   baisi = (BaisiData) cache.getAsObject("ZXDZD");
        if (content.equals(zuiXinUrl[4]))   baisi = (BaisiData) cache.getAsObject("ZXYCD");
        if (content.equals(zuiXinUrl[5]))   baisi = (BaisiData) cache.getAsObject("ZXWHD");
        if (content.equals(zuiXinUrl[6]))   baisi = (BaisiData) cache.getAsObject("ZXMND");
        if (content.equals(zuiXinUrl[7]))   baisi = (BaisiData) cache.getAsObject("ZXLZSD");
        if (content.equals(zuiXinUrl[8]))   baisi = (BaisiData) cache.getAsObject("ZXYXD");
    }
    //保存加载更多的页数
    private void savePageCount(){

        //最新
        if (content.equals(zuiXinUrl[0]))   cache.put("ZXQBP",pageCount,ACache.TIME_DAY);
        if (content.equals(zuiXinUrl[1]))   cache.put("ZXSPP",pageCount,ACache.TIME_DAY);
        if (content.equals(zuiXinUrl[2]))   cache.put("ZXTPP",pageCount,ACache.TIME_DAY);
        if (content.equals(zuiXinUrl[3]))   cache.put("ZXDZP",pageCount,ACache.TIME_DAY);
        if (content.equals(zuiXinUrl[4]))   cache.put("ZXYCP",pageCount,ACache.TIME_DAY);
        if (content.equals(zuiXinUrl[5]))   cache.put("ZXWHP",pageCount,ACache.TIME_DAY);
        if (content.equals(zuiXinUrl[6]))   cache.put("ZXMNP",pageCount,ACache.TIME_DAY);
        if (content.equals(zuiXinUrl[7]))   cache.put("ZXLZSP",pageCount,ACache.TIME_DAY);
        if (content.equals(zuiXinUrl[8]))   cache.put("ZXYXP",pageCount,ACache.TIME_DAY);

    }
    //获取加载更多页数
    private void getPageCount(){

        //最新
        if (content.equals(zuiXinUrl[0]))   pageCount = (int) cache.getAsObject("ZXQBP");
        if (content.equals(zuiXinUrl[1]))   pageCount = (int) cache.getAsObject("ZXSPP");
        if (content.equals(zuiXinUrl[2]))   pageCount = (int) cache.getAsObject("ZXTPP");
        if (content.equals(zuiXinUrl[3]))   pageCount = (int) cache.getAsObject("ZXDZP");
        if (content.equals(zuiXinUrl[4]))   pageCount = (int) cache.getAsObject("ZXYCP");
        if (content.equals(zuiXinUrl[5]))   pageCount = (int) cache.getAsObject("ZXWHP");
        if (content.equals(zuiXinUrl[6]))   pageCount = (int) cache.getAsObject("ZXMNP");
        if (content.equals(zuiXinUrl[7]))   pageCount = (int) cache.getAsObject("ZXLZSP");
        if (content.equals(zuiXinUrl[8]))   pageCount = (int) cache.getAsObject("ZXYXP");
    }


    @Override
    public void onStart() {
        super.onStart();
        if (timeCha>=30*60*1000){
            refreshLayout.startRefresh();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
