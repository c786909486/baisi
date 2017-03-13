package com.ckz.baisi.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ckz.baisi.R;
import com.ckz.baisi.adapter.MyPagerAdapter;
import com.ckz.baisi.fragment.jinghuaContent.QuanbuContent;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by CKZ on 2017/2/19.
 */

public class ZuiXinFragment extends Fragment implements View.OnClickListener {

    private ImageView textView;
    public static ImageView imageView;
    public static Animation animator = null;
    public static TextView showToast;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private MyPagerAdapter adapter;
    private ImageButton shenhe_btn, search_btn;
    private List<String> titleList = new ArrayList<>(); //页卡标题集合
    private ArrayList<Fragment> viewList = new ArrayList<>(); //页卡视图集合
    private QuanbuContent zx_fragment_tuijian, zx_fragment_shiping, zx_fragment_tupian, zx_fragment_duanzi, zx_fragment_yuanchuang,
            zx_fragment_paihang, zx_fragment_wanghong, zx_fragment_shehui, zx_fragment_meinv;
    //json网址
    public static final String url[] = new String[]{
            //全部
            "http://s.budejie.com/topic/list/zuixin/1/budejie-android-6.6.2/0-20.json?",
            //视频
            "http://s.budejie.com/topic/list/zuixin/41/budejie-android-6.6.2/0-20.json?",
            //图片
            "http://s.budejie.com/topic/list/zuixin/10/budejie-android-6.6.2/0-20.json?",
            //段子
            "http://s.budejie.com/topic/list/zuixin/29/budejie-android-6.6.2/0-20.json?",
            //原创
            "http://s.budejie.com/topic/tag-topic/44289/new/budejie-android-6.6.2/0-20.json?",
            //网红
            "http://s.budejie.com/topic/tag-topic/3096/new/budejie-android-6.6.2/0-20.json?",
            //美女
            "http://s.budejie.com/topic/tag-topic/117/new/budejie-android-6.6.2/0-20.json?",
            //冷知识
            "http://s.budejie.com/topic/tag-topic/3176/new/budejie-android-6.6.2/0-20.json?",
            //游戏
            "http://s.budejie.com/topic/tag-topic/164/new/budejie-android-6.6.2/0-20.json?"

    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zuixin_fragment_layout, null);
        initView(view);
        setData();
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(2)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(3)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(4)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(5)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(6)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(7)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(8)));

        adapter = new MyPagerAdapter(getChildFragmentManager(), viewList, titleList);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        return view;
    }

    private void setData() {
        titleList.add("全部");
        titleList.add("视频");
        titleList.add("图片");
        titleList.add("段子");
        titleList.add("原创");
        titleList.add("网红");
        titleList.add("美女");
        titleList.add("冷知识");
        titleList.add("游戏");


        zx_fragment_tuijian = new QuanbuContent();
        zx_fragment_shiping = new QuanbuContent();
        zx_fragment_tupian = new QuanbuContent();
        zx_fragment_duanzi = new QuanbuContent();
        zx_fragment_yuanchuang = new QuanbuContent();
        zx_fragment_paihang = new QuanbuContent();
        zx_fragment_wanghong = new QuanbuContent();
        zx_fragment_shehui = new QuanbuContent();
        zx_fragment_meinv = new QuanbuContent();

        zx_fragment_tuijian.setContent(url[0]);
        zx_fragment_shiping.setContent(url[1]);
        zx_fragment_tupian.setContent(url[2]);
        zx_fragment_duanzi.setContent(url[3]);
        zx_fragment_yuanchuang.setContent(url[4]);
        zx_fragment_paihang.setContent(url[5]);
        zx_fragment_wanghong.setContent(url[6]);
        zx_fragment_shehui.setContent(url[7]);
        zx_fragment_meinv.setContent(url[8]);

        viewList.add(zx_fragment_tuijian);
        viewList.add(zx_fragment_shiping);
        viewList.add(zx_fragment_tupian);
        viewList.add(zx_fragment_duanzi);
        viewList.add(zx_fragment_yuanchuang);
        viewList.add(zx_fragment_paihang);
        viewList.add(zx_fragment_wanghong);
        viewList.add(zx_fragment_shehui);
        viewList.add(zx_fragment_meinv);
    }

    private void initView(View view) {
        animator = AnimationUtils.loadAnimation(getContext().getApplicationContext(), R.anim.anim_rotate);
        textView = (ImageView) view.findViewById(R.id.tool_bar_title);
        textView.setOnClickListener(this);
        imageView = (ImageView) view.findViewById(R.id.tool_bar_refresh);
        viewPager = (ViewPager) view.findViewById(R.id.zuixin_view_pager);
        tabLayout = (TabLayout) view.findViewById(R.id.zuixin_tablayout);
        shenhe_btn = (ImageButton) view.findViewById(R.id.top_shenhe_btn);
        search_btn = (ImageButton) view.findViewById(R.id.top_search_btn);
        showToast = (TextView) view.findViewById(R.id.zuixin_show_toast);
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tool_bar_title:
                ZuiXinFragment.setAni();
                setClickRefresh();
                break;
        }
    }
    public static void setAni() {
        imageView.startAnimation(animator);
    }

    public void setClickRefresh() {
        if (zx_fragment_tuijian.isVisible())
           zx_fragment_tuijian.refreshLayout.startRefresh();
        if (zx_fragment_shiping.isVisible())
           zx_fragment_shiping.refreshLayout.startRefresh();
        if (zx_fragment_tupian.isVisible())
            zx_fragment_tupian.refreshLayout.startRefresh();
        if (zx_fragment_duanzi.isVisible())
            zx_fragment_duanzi.refreshLayout.startRefresh();
        if (zx_fragment_yuanchuang.isVisible())
            zx_fragment_yuanchuang.refreshLayout.startRefresh();
        if (zx_fragment_paihang.isVisible())
            zx_fragment_paihang.refreshLayout.startRefresh();
        if (zx_fragment_wanghong.isVisible())
            zx_fragment_wanghong.refreshLayout.startRefresh();
        if (zx_fragment_shehui.isVisible())
            zx_fragment_shehui.refreshLayout.startRefresh();
        if (zx_fragment_meinv.isVisible())
            zx_fragment_meinv.refreshLayout.startRefresh();

    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
