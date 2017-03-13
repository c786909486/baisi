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

public class TuijianFragment extends Fragment {
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

    private static final String jingXuanUrl[] = JingHuaFragment.url;
//    private static final String zuiXinUrl[] = ZuiXinFragment.url;

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
        },1000,1000);
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
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                JingHuaFragment.setAni();
                //刷新数据
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData(content,true);
                        saveRefreshTime();
                       // getTimeCha();
                        refreshLayout.finishRefreshing();
                        JingHuaFragment.animator.cancel();
                    }
                },1000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                long loadTime = System.currentTimeMillis()/1000-(pageCount+1)*10*3600;
                loadMoreUrl = content.replace("0-20",loadTime+"-20");
                //加载更多
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        savePageCount();
                        getPageCount();
                        getData(loadMoreUrl,false);
                        pageCount++;
                        savePageCount();
                        refreshLayout.finishLoadmore();
                    }
                },1000);
            }
        });
    }

    private void getData(final String content, final boolean isRefresh){
        GsonRequest<BaisiData> gsonRequest = new GsonRequest<BaisiData>(content, BaisiData.class, new Response.Listener<BaisiData>() {
            @Override
            public void onResponse(BaisiData baisiData) {
                List<BaisiData.ListBean> listBeen = baisiData.getList();
                int lastNum = mData.size();
                mData.removeAll(listBeen);
                if (isRefresh){
                    mData.addAll(0,listBeen);
                    int nowNum = mData.size();
                    int num = nowNum - lastNum;
                    if (getUserVisibleHint())
                       // Toast.makeText(getContext(),"更新了"+num+"条内容",Toast.LENGTH_SHORT).show();
                    MyToastUtils.showRefreshTosat(getContext(),JingHuaFragment.showToast,"更新了"+num+"条内容");
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
        //精选
        if (content.equals(jingXuanUrl[0])) SPUtils.putlongSp(getActivity(),"JXTJ",System.currentTimeMillis());
        if (content.equals(jingXuanUrl[1])) SPUtils.putlongSp(getActivity(),"JXSP",System.currentTimeMillis());
        if (content.equals(jingXuanUrl[2])) SPUtils.putlongSp(getActivity(),"JXTP",System.currentTimeMillis());
        if (content.equals(jingXuanUrl[3])) SPUtils.putlongSp(getActivity(),"JXDZ",System.currentTimeMillis());
        if (content.equals(jingXuanUrl[4])) SPUtils.putlongSp(getActivity(),"JXYC",System.currentTimeMillis());
        if (content.equals(jingXuanUrl[5])) SPUtils.putlongSp(getActivity(),"JXWH",System.currentTimeMillis());
        if (content.equals(jingXuanUrl[6])) SPUtils.putlongSp(getActivity(),"JXPH",System.currentTimeMillis());
        if (content.equals(jingXuanUrl[7])) SPUtils.putlongSp(getActivity(),"JXSH",System.currentTimeMillis());
        if (content.equals(jingXuanUrl[8])) SPUtils.putlongSp(getActivity(),"JXMN",System.currentTimeMillis());
        if (content.equals(jingXuanUrl[9])) SPUtils.putlongSp(getActivity(),"JXLZS",System.currentTimeMillis());
        if (content.equals(jingXuanUrl[10]))SPUtils.putlongSp(getActivity(),"JXYX",System.currentTimeMillis());
    }

    //不同界面获取不同时间差
    private void getTimeCha(){
        //精选
        if (content.equals(jingXuanUrl[0]))  timeCha = System.currentTimeMillis()- SPUtils.getlongSp(getActivity(),"JXTJ");
        if (content.equals(jingXuanUrl[1]))  timeCha = System.currentTimeMillis()- SPUtils.getlongSp(getActivity(),"JXSP");
        if (content.equals(jingXuanUrl[2]))  timeCha = System.currentTimeMillis()- SPUtils.getlongSp(getActivity(),"JXTP");
        if (content.equals(jingXuanUrl[3]))  timeCha = System.currentTimeMillis()- SPUtils.getlongSp(getActivity(),"JXDZ");
        if (content.equals(jingXuanUrl[4]))  timeCha = System.currentTimeMillis()- SPUtils.getlongSp(getActivity(),"JXYC");
        if (content.equals(jingXuanUrl[5]))  timeCha = System.currentTimeMillis()- SPUtils.getlongSp(getActivity(),"JXWH");
        if (content.equals(jingXuanUrl[6]))  timeCha = System.currentTimeMillis()- SPUtils.getlongSp(getActivity(),"JXPH");
        if (content.equals(jingXuanUrl[7]))  timeCha = System.currentTimeMillis()- SPUtils.getlongSp(getActivity(),"JXSH");
        if (content.equals(jingXuanUrl[8]))  timeCha = System.currentTimeMillis()- SPUtils.getlongSp(getActivity(),"JXMN");
        if (content.equals(jingXuanUrl[9]))  timeCha = System.currentTimeMillis()- SPUtils.getlongSp(getActivity(),"JXLZS");
        if (content.equals(jingXuanUrl[10])) timeCha = System.currentTimeMillis()- SPUtils.getlongSp(getActivity(),"JXYX");
    }
    //存储个页面data数据
    private void saveAllData(){
        BaisiData baisiData = new BaisiData();
        baisiData.setList(mData);
        //精选
        if (content.equals(jingXuanUrl[0]))  cache.put("JXTJD",baisiData,ACache.TIME_DAY);
        if (content.equals(jingXuanUrl[1]))  cache.put("JXSPD",baisiData,ACache.TIME_DAY);
        if (content.equals(jingXuanUrl[2]))  cache.put("JXTPD",baisiData,ACache.TIME_DAY);
        if (content.equals(jingXuanUrl[3]))  cache.put("JXDZD",baisiData,ACache.TIME_DAY);
        if (content.equals(jingXuanUrl[4]))  cache.put("JXYCD",baisiData,ACache.TIME_DAY);
        if (content.equals(jingXuanUrl[5]))  cache.put("JXWHD",baisiData,ACache.TIME_DAY);
        if (content.equals(jingXuanUrl[6]))  cache.put("JXPHD",baisiData,ACache.TIME_DAY);
        if (content.equals(jingXuanUrl[7]))  cache.put("JXSHD",baisiData,ACache.TIME_DAY);
        if (content.equals(jingXuanUrl[8]))  cache.put("JXMND",baisiData,ACache.TIME_DAY);
        if (content.equals(jingXuanUrl[9]))  cache.put("JXLZSD",baisiData,ACache.TIME_DAY);
        if (content.equals(jingXuanUrl[10]))  cache.put("JXYXD",baisiData,ACache.TIME_DAY);
    }
    //获取data数据
    private void getCacheData(){
        //精选
        if (content.equals(jingXuanUrl[0]))   baisi = (BaisiData) cache.getAsObject("JXTJD");
        if (content.equals(jingXuanUrl[1]))   baisi = (BaisiData) cache.getAsObject("JXSPD");
        if (content.equals(jingXuanUrl[2]))   baisi = (BaisiData) cache.getAsObject("JXTPD");
        if (content.equals(jingXuanUrl[3]))   baisi = (BaisiData) cache.getAsObject("JXDZD");
        if (content.equals(jingXuanUrl[4]))   baisi = (BaisiData) cache.getAsObject("JXYCD");
        if (content.equals(jingXuanUrl[5]))   baisi = (BaisiData) cache.getAsObject("JXWHD");
        if (content.equals(jingXuanUrl[6]))   baisi = (BaisiData) cache.getAsObject("JXPHD");
        if (content.equals(jingXuanUrl[7]))   baisi = (BaisiData) cache.getAsObject("JXSHD");
        if (content.equals(jingXuanUrl[8]))   baisi = (BaisiData) cache.getAsObject("JXMND");
        if (content.equals(jingXuanUrl[9]))   baisi = (BaisiData) cache.getAsObject("JXLZSD");
        if (content.equals(jingXuanUrl[10]))   baisi = (BaisiData) cache.getAsObject("JXYXD");
    }
    //保存加载更多的页数
    private void savePageCount(){
        //精选
        if (content.equals(jingXuanUrl[0]))  cache.put("JXTJP",pageCount,ACache.TIME_DAY);
        if (content.equals(jingXuanUrl[1]))  cache.put("JXSPP",pageCount,ACache.TIME_DAY);
        if (content.equals(jingXuanUrl[2]))  cache.put("JXTPP",pageCount,ACache.TIME_DAY);
        if (content.equals(jingXuanUrl[3]))  cache.put("JXDZP",pageCount,ACache.TIME_DAY);
        if (content.equals(jingXuanUrl[4]))  cache.put("JXYCP",pageCount,ACache.TIME_DAY);
        if (content.equals(jingXuanUrl[5]))  cache.put("JXWHP",pageCount,ACache.TIME_DAY);
        if (content.equals(jingXuanUrl[6]))  cache.put("JXPHP",pageCount,ACache.TIME_DAY);
        if (content.equals(jingXuanUrl[7]))  cache.put("JXSHP",pageCount,ACache.TIME_DAY);
        if (content.equals(jingXuanUrl[8]))  cache.put("JXMNP",pageCount,ACache.TIME_DAY);
        if (content.equals(jingXuanUrl[9]))  cache.put("JXLZSP",pageCount,ACache.TIME_DAY);
        if (content.equals(jingXuanUrl[10]))  cache.put("JXYXP",pageCount,ACache.TIME_DAY);

    }
    //获取加载更多页数
    private void getPageCount(){
        if (content.equals(jingXuanUrl[0]))   pageCount = (int) cache.getAsObject("JXTJP");
        if (content.equals(jingXuanUrl[1]))   pageCount = (int) cache.getAsObject("JXSPP");
        if (content.equals(jingXuanUrl[2]))   pageCount = (int) cache.getAsObject("JXTPP");
        if (content.equals(jingXuanUrl[3]))   pageCount = (int) cache.getAsObject("JXDZP");
        if (content.equals(jingXuanUrl[4]))   pageCount = (int) cache.getAsObject("JXYCP");
        if (content.equals(jingXuanUrl[5]))   pageCount = (int) cache.getAsObject("JXWHP");
        if (content.equals(jingXuanUrl[6]))   pageCount = (int) cache.getAsObject("JXPHP");
        if (content.equals(jingXuanUrl[7]))   pageCount = (int) cache.getAsObject("JXSHP");
        if (content.equals(jingXuanUrl[8]))   pageCount = (int) cache.getAsObject("JXMNP");
        if (content.equals(jingXuanUrl[9]))   pageCount = (int) cache.getAsObject("JXLZSP");
        if (content.equals(jingXuanUrl[10]))   pageCount = (int) cache.getAsObject("JXYXP");
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
