package com.ckz.baisi.fragment.userSend;

import android.os.Bundle;
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
import com.ckz.baisi.activity.UserDetilsActivity;
import com.ckz.baisi.adapter.MyContentAdapter;
import com.ckz.baisi.adapter.UserCommentAdapter;
import com.ckz.baisi.appliction.MyAppliction;
import com.ckz.baisi.bean.BaisiData;
import com.ckz.baisi.bean.UserCommentBean;
import com.ckz.baisi.request.GsonRequest;
import com.ckz.baisi.unitls.LogUtils;
import com.ckz.baisi.view.BudejieRefresh;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by CKZ on 2017/3/16.
 */

public class PinglunFragment extends Fragment {
    private String content,loadMoreUrl;
    private RecyclerView jinghuaRecycler;
    private List<UserCommentBean.ListBean> mData;
    private UserCommentAdapter adapter;
    public TwinklingRefreshLayout refreshLayout;
    private BudejieRefresh headView;
    //加载更多的页数
    private int pageCount = 0;




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
        adapter = new UserCommentAdapter(getActivity(),mData);
        jinghuaRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        jinghuaRecycler.setAdapter(adapter);
        headView = new BudejieRefresh(getContext());
        refreshLayout.setHeaderView(headView);
        UserDetilsActivity.userLoading.setVisibility(View.VISIBLE);
        refreshData();
        getData(content);
    }


    public void refreshData(){
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
//                long loadTime = System.currentTimeMillis()/1000-(pageCount)*4000*3600;
                loadMoreUrl = content.replace("0-20",pageCount+"-20");
                //加载更多
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData(loadMoreUrl);
                        refreshLayout.finishLoadmore();
                        LogUtils.d("Pinglun",loadMoreUrl);
                        LogUtils.d("page",pageCount+"");

                    }
                },1000);
            }
        });
    }

    private void getData(final String content){
        GsonRequest<UserCommentBean> gsonRequest = new GsonRequest<UserCommentBean>(content, UserCommentBean.class, new Response.Listener<UserCommentBean>() {
            @Override
            public void onResponse(UserCommentBean userCommentBean) {
                pageCount = userCommentBean.getInfo().getNp();
                UserDetilsActivity.userLoading.setVisibility(View.GONE);
                List<UserCommentBean.ListBean> listBeen = userCommentBean.getList();
                if (listBeen!=null){
                    mData.removeAll(listBeen);
                    mData.addAll(listBeen);
                    adapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

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
