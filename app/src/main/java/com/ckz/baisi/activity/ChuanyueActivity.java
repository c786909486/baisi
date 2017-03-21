package com.ckz.baisi.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ckz.baisi.R;
import com.ckz.baisi.adapter.MyPagerAdapter;
import com.ckz.baisi.fragment.chuanyue.ChuanyueFragment;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class ChuanyueActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView backbtn;
    private ImageButton topsuijibtn;
    private TabLayout tabLayout;
    private ViewPager chuanyueViewPager;
    public static TextView showToast;

    private MyPagerAdapter adapter;
    private List<String> titleList = new ArrayList<>(); //页卡标题集合
    private ArrayList<Fragment> viewList = new ArrayList<>(); //页卡视图集合
    private ChuanyueFragment quanbu,shiping,tupian,duanzi;

    private int position = 0;
    //json网址
    public static final String url[] = new String[]{
            //全部
            "http://d.api.budejie.com/topic/list/chuanyue/1/budejie-android-6.6.3/0-20.json?",
            //视频
            "http://d.api.budejie.com/topic/list/chuanyue/41/budejie-android-6.6.3/0-20.json?",
            //图片
            "http://d.api.budejie.com/topic/list/chuanyue/10/budejie-android-6.6.3/0-20.json?",
            //段子
            "http://d.api.budejie.com/topic/list/chuanyue/29/budejie-android-6.6.3/0-20.json?"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuanyue);
        initView();
    }

    private void initView() {
        showToast = (TextView) findViewById(R.id.chuanyue_show_toast);
        chuanyueViewPager = (ViewPager) findViewById(R.id.chuanyue_view_pager);
        tabLayout = (TabLayout) findViewById(R.id.chuanyue_tablayout);
        topsuijibtn = (ImageButton) findViewById(R.id.top_suiji_btn);
        backbtn = (TextView) findViewById(R.id.back_btn);
        setData();
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(2)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(3)));

        adapter = new MyPagerAdapter(getSupportFragmentManager(),viewList,titleList);
        tabLayout.setupWithViewPager(chuanyueViewPager);
        chuanyueViewPager.setAdapter(adapter);
        chuanyueViewPager.setCurrentItem(position);
        backbtn.setOnClickListener(this);
        topsuijibtn.setOnClickListener(this);
    }

    private void setData() {
        titleList.add("全部");
        titleList.add("视频");
        titleList.add("图片");
        titleList.add("段子");

        quanbu = new ChuanyueFragment();
        shiping= new ChuanyueFragment();
        tupian = new ChuanyueFragment();
        duanzi = new ChuanyueFragment();

        quanbu.setContent(url[0]);
        shiping.setContent(url[1]);
        tupian.setContent(url[2]);
        duanzi.setContent(url[3]);

        viewList.add(quanbu);
        viewList.add(shiping);
        viewList.add(tupian);
        viewList.add(duanzi);
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()){
            return;
        }
        super.onBackPressed();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.top_suiji_btn:
                if (quanbu.isVisible()) quanbu.refreshLayout.startRefresh();
                if (shiping.isVisible()) shiping.refreshLayout.startRefresh();
                if (tupian.isVisible()) tupian.refreshLayout.startRefresh();
                if (duanzi.isVisible()) duanzi.refreshLayout.startRefresh();
                break;

        }
    }
}
