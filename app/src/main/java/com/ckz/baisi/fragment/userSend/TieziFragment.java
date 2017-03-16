package com.ckz.baisi.fragment.userSend;

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
import com.ckz.baisi.activity.UserDetilsActivity;
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

public class TieziFragment extends Fragment {
    private String content,loadMoreUrl;
    private RecyclerView jinghuaRecycler;
    private List<BaisiData.ListBean> mData;
    private MyContentAdapter adapter;
    public TwinklingRefreshLayout refreshLayout;
    private BudejieRefresh headView;
    //加载更多的页数
    private int pageCount;




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
        init(view);
        return view;
    }


    private void init(View view) {
        jinghuaRecycler = (RecyclerView) view.findViewById(R.id.content_list_view);
        refreshLayout = (TwinklingRefreshLayout) view.findViewById(R.id.refresh_layout);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOverScrollTopShow(false);
        mData = new ArrayList<>();
        adapter = new MyContentAdapter(getActivity(),mData);
        jinghuaRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        jinghuaRecycler.setAdapter(adapter);
        headView = new BudejieRefresh(getContext());
        refreshLayout.setHeaderView(headView);
        refreshData();
        UserDetilsActivity.userLoading.setVisibility(View.VISIBLE);
        refreshLayout.startLoadMore();
    }


    public void refreshData(){
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                long loadTime = System.currentTimeMillis()/1000-(pageCount+1)*100*3600;
                loadMoreUrl = content.replace("0-20",loadTime+"-20");
                //加载更多
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData(loadMoreUrl);
                        pageCount++;
                        refreshLayout.finishLoadmore();
                    }
                },1000);
            }
        });
    }

    private void getData(final String content){
        GsonRequest<BaisiData> gsonRequest = new GsonRequest<BaisiData>(content, BaisiData.class, new Response.Listener<BaisiData>() {
            @Override
            public void onResponse(BaisiData baisiData) {
                UserDetilsActivity.userLoading.setVisibility(View.GONE);
                List<BaisiData.ListBean> listBeen = baisiData.getList();
                if (listBeen!=null){
                    mData.removeAll(listBeen);
                    mData.addAll(listBeen);
                    adapter.notifyDataSetChanged();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(),"请检查网络",Toast.LENGTH_SHORT).show();
            }
        });
        MyAppliction.getRequestQueue().add(gsonRequest);
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
