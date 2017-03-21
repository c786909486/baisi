package com.ckz.baisi.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ckz.baisi.R;
import com.ckz.baisi.activity.GongxianActivity;
import com.ckz.baisi.adapter.GridViewAdapter;
import com.ckz.baisi.adapter.TuiJianAdapter;
import com.ckz.baisi.adapter.ViewPagerAdapter;
import com.ckz.baisi.appliction.MyAppliction;
import com.ckz.baisi.bean.GrideBean;
import com.ckz.baisi.bean.TJDYBean;
import com.ckz.baisi.dialog.DialogUtils;
import com.ckz.baisi.request.GsonRequest;
import com.ckz.baisi.unitls.ACache;
import com.ckz.baisi.unitls.LogUtils;
import com.ckz.baisi.unitls.NetWorkUtils;
import com.ckz.baisi.view.CircleImageView;
import com.ckz.baisi.view.MyListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CKZ on 2017/3/14.
 */

public class WodeFragment extends Fragment implements View.OnClickListener{
    private static final String grideURL = "http://s.budejie.com/op/square2/budejie-android-6.6.3/xiaomi/0-100.json?";
    private static final String TJDYURL = "http://d.api.budejie.com/tag/subscribe/budejie-android-6.6.3.json?";
    private CircleImageView userIcon;
    private TextView shentieBtn;
    private TextView collectBtn;
    private TextView searchBtn;
    private TextView feedBtn;
    private TextView myVideoBtn;
    private LinearLayout noNetGroup;
    private ViewPager viewPager;
    private LinearLayout llDot;
    private MyListView tuijianList;
    /**
     * 总的页数
     */
    private int pageCount=2;
    /**
     * 每一页显示的个数
     */
    private int pageSize = 10;
    /**
     * 当前显示的是第几页
     */
    private int curIndex = 0;

    private List<View> mPagerList;
    private List<GrideBean.SquareListBean> mDatas;
    private List<TJDYBean.RecTagsBean> tjData;
    private GridViewAdapter adapter;
    private TuiJianAdapter tAdapter;
    private ACache mACache;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wode_fragment, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tuijianList = (MyListView) view.findViewById(R.id.tuijian_list);
        llDot = (LinearLayout) view.findViewById(R.id.ll_dot);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        noNetGroup = (LinearLayout) view.findViewById(R.id.no_net_group);
        myVideoBtn = (TextView) view.findViewById(R.id.my_video_btn);
        feedBtn = (TextView) view.findViewById(R.id.feed_btn);
        searchBtn = (TextView) view.findViewById(R.id.search_btn);
        collectBtn = (TextView) view.findViewById(R.id.collect_btn);
        shentieBtn = (TextView) view.findViewById(R.id.shentie_btn);
        userIcon = (CircleImageView) view.findViewById(R.id.user_icon);
        mACache = ACache.get(getContext());
        setViewPager();
        setListView();
    }

    /**
     * 设置listview
     */
    private void setListView(){
        tjData = new ArrayList<>();
        tAdapter = new TuiJianAdapter(getContext(),tjData);
        tuijianList.setAdapter(tAdapter);
        TJDYBean tjdyBean = (TJDYBean) mACache.getAsObject("dingyueData");
        if (tjdyBean!=null){
            tjData.addAll(tjdyBean.getRec_tags());
            tAdapter.notifyDataSetChanged();
        }else {
            getTuiJianData();
        }
    }

    private void getTuiJianData(){
        GsonRequest<TJDYBean> request = new GsonRequest<TJDYBean>(TJDYURL, TJDYBean.class, new Response.Listener<TJDYBean>() {
            @Override
            public void onResponse(TJDYBean tjdyBean) {
                List<TJDYBean.RecTagsBean> list = tjdyBean.getRec_tags();
                tjData.removeAll(list);
                tjData.addAll(list);
                tAdapter.notifyDataSetChanged();
                mACache.put("dingyueData",tjdyBean);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        MyAppliction.getRequestQueue().add(request);
    }

    private void setViewPager(){

        mDatas = new ArrayList<>();
        mPagerList = new ArrayList<>();
        for (int i = 0; i < pageCount; i++) {
            // 每个页面都是inflate出一个新实例
            GridView gridView = (GridView) LayoutInflater.from(getContext()).inflate(R.layout.gridview, viewPager, false);
            adapter = new GridViewAdapter(getContext(), mDatas, i, pageSize);
            gridView.setAdapter(adapter);
            mPagerList.add(gridView);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int pos = position + curIndex * pageSize;
                    switch (mDatas.get(pos).getId()){
                        case 28:
                        case 46:
                        case 48:
                        case 55:
                        case 141:
                        case 83:
                            DialogUtils.setZBDialog(getContext());
                            break;
                        case 151:
                        case 154:
                        case 87:
                        case 116:
                        case 82:
                        case 113:
                        case 102:
                        case 85:
                        case 160:
                        case 153:
                        case 130:
                            Intent intent = new Intent(getContext(), GongxianActivity.class);
                            intent.putExtra("url",mDatas.get(pos).getUrl());
                            getContext().startActivity(intent);
                            break;
                    }
                }
            });
        }
        GrideBean grideBean = (GrideBean) mACache.getAsObject("GrideData");
        if (grideBean!=null){
            mDatas.addAll(grideBean.getSquare_list());
            adapter.notifyDataSetChanged();
            noNetGroup.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
        }else {
            if (NetWorkUtils.isNetworkConnected(getContext())){
                noNetGroup.setVisibility(View.GONE);
                viewPager.setVisibility(View.VISIBLE);
                getGrideData();
                adapter.notifyDataSetChanged();
            }else {
                noNetGroup.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.GONE);
                setClick();
            }
        }
        //设置适配器
        viewPager.setAdapter(new ViewPagerAdapter(mPagerList));
//设置圆点
        setOvalLayout();
    }
    /**
     * 获取grideview数据
     */
    private void getGrideData(){
        GsonRequest<GrideBean> request = new GsonRequest<GrideBean>(grideURL, GrideBean.class, new Response.Listener<GrideBean>() {
            @Override
            public void onResponse(GrideBean grideBean) {
                List<GrideBean.SquareListBean> listBeen = grideBean.getSquare_list();
                mDatas.addAll(listBeen);
                adapter.notifyDataSetChanged();
                mACache.put("GrideData",grideBean);
                //总的页数=总数/每页数量，并取整

                LogUtils.d("pageCount",pageCount+"");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(),"当前网络不可用，请检查网络连接",Toast.LENGTH_SHORT).show();
            }
        });
        MyAppliction.getRequestQueue().add(request);
    }
    /**
     * 设置圆点
     */
    public void setOvalLayout() {
        for (int i = 0; i < pageCount; i++) {
            llDot.addView(LayoutInflater.from(getContext()).inflate(R.layout.dot, null));
        }
        // 默认显示第一页
        llDot.getChildAt(0).findViewById(R.id.v_dot)
                .setBackgroundResource(R.drawable.dot_selected);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                // 取消圆点选中
                llDot.getChildAt(curIndex)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_normal);
                // 圆点选中
                llDot.getChildAt(position)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_selected);
                curIndex = position;
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    /**
     * 设置无网络点击事件
     */
    private void setClick(){
        shentieBtn.setOnClickListener(this);
        collectBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        feedBtn.setOnClickListener(this);
        myVideoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shentie_btn:
            case R.id.collect_btn:
            case R.id.search_btn:
            case R.id.feed_btn:
            case R.id.my_video_btn:
                Toast.makeText(getContext(),"当前网络不可用，请检查网络连接",Toast.LENGTH_SHORT).show();
                break;
        }

    }


}
