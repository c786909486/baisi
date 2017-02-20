package com.ckz.baisi.fragment;

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

import com.ckz.baisi.R;
import com.ckz.baisi.adapter.MyPagerAdapter;
import com.ckz.baisi.fragment.jinghuaContent.TuijianFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CKZ on 2017/2/19.
 */

public class ZuiXinFragment extends Fragment implements View.OnClickListener{

    private ImageView textView;
    public static ImageView imageView;
    public static Animation animator = null;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private MyPagerAdapter adapter;
    private ImageButton shenhe_btn,search_btn;
    private List<String> titleList = new ArrayList<>(); //页卡标题集合
    private ArrayList<Fragment> viewList = new ArrayList<>(); //页卡视图集合

    //json网址
    private String url[] = new String[]{

    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zuixin_fragment_layout,null);
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

        adapter = new MyPagerAdapter(getChildFragmentManager(),viewList,titleList);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(3);
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


        TuijianFragment fragment_tuijian = new TuijianFragment();
        TuijianFragment fragment_shiping = new TuijianFragment();
        TuijianFragment fragment_tupian = new TuijianFragment();
        TuijianFragment fragment_duanzi = new TuijianFragment();
        TuijianFragment fragment_yuanchuang = new TuijianFragment();
        TuijianFragment fragment_paihang = new TuijianFragment();
        TuijianFragment fragment_wanghong = new TuijianFragment();
        TuijianFragment fragment_shehui = new TuijianFragment();
        TuijianFragment fragment_meinv = new TuijianFragment();

        viewList.add(fragment_tuijian);
        viewList.add(fragment_shiping);
        viewList.add(fragment_tupian);
        viewList.add(fragment_duanzi);
        viewList.add(fragment_yuanchuang);
        viewList.add(fragment_paihang);
        viewList.add(fragment_wanghong);
        viewList.add(fragment_shehui);
        viewList.add(fragment_meinv);
    }

    private void initView(View view) {
        animator = AnimationUtils.loadAnimation(getContext().getApplicationContext(),R.anim.anim_rotate);
        textView = (ImageView) view.findViewById(R.id.tool_bar_title);
        textView.setOnClickListener(this);
        imageView = (ImageView) view.findViewById(R.id.tool_bar_refresh);
        viewPager = (ViewPager) view.findViewById(R.id.zuixin_view_pager);
        tabLayout = (TabLayout) view.findViewById(R.id.zuixin_tablayout);
        shenhe_btn = (ImageButton) view.findViewById(R.id.top_shenhe_btn);
        search_btn = (ImageButton) view.findViewById(R.id.top_search_btn);
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
                break;
        }
    }
    public static void setAni(){
        imageView.startAnimation(animator);
    }
}
