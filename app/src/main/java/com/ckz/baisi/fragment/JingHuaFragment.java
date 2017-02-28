package com.ckz.baisi.fragment;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ckz.baisi.R;
import com.ckz.baisi.activity.GongxianActivity;
import com.ckz.baisi.adapter.MyPagerAdapter;
import com.ckz.baisi.fragment.jinghuaContent.TuijianFragment;
import com.ckz.baisi.unitls.AnimatorUtilsss;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by CKZ on 2017/2/4.
 */

public class JingHuaFragment extends Fragment implements View.OnClickListener{

    private ImageView textView;
    public static ImageView imageView;
    public static Animation animator = null;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private MyPagerAdapter adapter;
    private ImageButton game_btn,suiji_btn;
    private List<String> titleList = new ArrayList<>(); //页卡标题集合
    private ArrayList<Fragment> viewList = new ArrayList<>(); //页卡视图集合
    private int jinghua_position = 0;
    //json网址
    private String url[] = new String[]{
            //推荐
            "http://s.budejie.com/topic/list/jingxuan/1/budejie-android-6.6.2/0-20.json?",
            //视频
            "http://s.budejie.com/topic/list/jingxuan/41/budejie-android-6.6.2/0-20.json?",
            //图片
            "http://s.budejie.com/topic/list/jingxuan/10/budejie-android-6.6.2/0-20.json?",
            //段子
            "http://s.budejie.com/topic/tag-topic/64/hot/budejie-android-6.6.2/0-20.json?",
            //原创
            "http://s.budejie.com/topic/tag-topic/44289/hot/budejie-android-6.6.2/0-20.json?",
            //网红
            "http://s.budejie.com/topic/tag-topic/3096/hot/budejie-android-6.6.2/0-20.json?",
            //排行
            "http://s.budejie.com/topic/list/remen/1/budejie-android-6.6.2/0-20.json?",
            //社会
            "http://s.budejie.com/topic/tag-topic/473/hot/budejie-android-6.6.2/0-20.json?",
            //美女
            "http://s.budejie.com/topic/tag-topic/117/hot/budejie-android-6.6.2/0-20.json?",
            //冷知识
            "http://s.budejie.com/topic/tag-topic/3176/hot/budejie-android-6.6.2/0-20.json?",
            //游戏
            "http://s.budejie.com/topic/tag-topic/164/hot/budejie-android-6.6.2/0-20.json?"
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jinghua_fragment_layout,null);
        animator = AnimationUtils.loadAnimation(getContext().getApplicationContext(),R.anim.anim_rotate);
        textView = (ImageView) view.findViewById(R.id.tool_bar_title);
        textView.setOnClickListener(this);
        imageView = (ImageView) view.findViewById(R.id.tool_bar_refresh);
        viewPager = (ViewPager) view.findViewById(R.id.jinghua_view_pager);
        tabLayout = (TabLayout) view.findViewById(R.id.jinghua_tablayout);
        game_btn = (ImageButton) view.findViewById(R.id.top_games_btn);
        suiji_btn = (ImageButton) view.findViewById(R.id.top_suiji_btn);
        game_btn.setOnClickListener(this);
        suiji_btn.setOnClickListener(this);
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
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(9)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(10)));
        adapter = new MyPagerAdapter(getChildFragmentManager(),viewList,titleList);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(jinghua_position);
       //Log.d("Tag",viewList.get(0).getContent());

        return view;
    }

    private void setData(){
        titleList.add("推荐");
        titleList.add("视频");
        titleList.add("图片");
        titleList.add("段子");
        titleList.add("原创");
        titleList.add("网红");
        titleList.add("排行");
        titleList.add("社会");
        titleList.add("美女");
        titleList.add("冷知识");
        titleList.add("游戏");

        TuijianFragment fragment_tuijian = new TuijianFragment();
        TuijianFragment fragment_shiping = new TuijianFragment();
        TuijianFragment fragment_tupian = new TuijianFragment();
        TuijianFragment fragment_duanzi = new TuijianFragment();
        TuijianFragment fragment_yuanchuang = new TuijianFragment();
        TuijianFragment fragment_paihang = new TuijianFragment();
        TuijianFragment fragment_wanghong = new TuijianFragment();
        TuijianFragment fragment_shehui = new TuijianFragment();
        TuijianFragment fragment_meinv = new TuijianFragment();
        TuijianFragment fragment_lengzhishi = new TuijianFragment();
        TuijianFragment fragment_youxi = new TuijianFragment();
        fragment_tuijian.setContent(url[0]);
        fragment_shiping.setContent(url[1]);
        fragment_tupian.setContent(url[2]);
        fragment_duanzi.setContent(url[3]);
        fragment_yuanchuang.setContent(url[4]);
        fragment_paihang.setContent(url[5]);
        fragment_wanghong.setContent(url[6]);
        fragment_shehui.setContent(url[7]);
        fragment_meinv.setContent(url[8]);
        fragment_lengzhishi.setContent(url[9]);
        fragment_youxi.setContent(url[10]);

        viewList.add(fragment_tuijian);
        viewList.add(fragment_shiping);
        viewList.add(fragment_tupian);
        viewList.add(fragment_duanzi);
        viewList.add(fragment_yuanchuang);
        viewList.add(fragment_paihang);
        viewList.add(fragment_wanghong);
        viewList.add(fragment_shehui);
        viewList.add(fragment_meinv);
        viewList.add(fragment_lengzhishi);
        viewList.add(fragment_youxi);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.tool_bar_title:
              JingHuaFragment.setAni();
              break;
          case R.id.top_games_btn:
              Intent intent = new Intent(getContext(), GongxianActivity.class);
              startActivity(intent);
              break;
      }
    }
    public static void setAni(){
        imageView.startAnimation(animator);
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
