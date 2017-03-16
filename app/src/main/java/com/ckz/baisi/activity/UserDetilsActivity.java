package com.ckz.baisi.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.ckz.baisi.R;
import com.ckz.baisi.adapter.MyPagerAdapter;
import com.ckz.baisi.appliction.MyAppliction;
import com.ckz.baisi.bean.UserBean;
import com.ckz.baisi.fragment.userSend.PinglunFragment;
import com.ckz.baisi.fragment.userSend.TieziFragment;
import com.ckz.baisi.request.GsonRequest;
import com.ckz.baisi.unitls.LogUtils;
import com.ckz.baisi.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class UserDetilsActivity extends AppCompatActivity implements View.OnClickListener{
    private Context context = UserDetilsActivity.this;
    //控件
    private ImageView userBackground;
    private TextView fansCount;
    private TextView guanzhuCount;
    private TextView userLever;
    private TextView addFocus;
    private CircleImageView userIcon;
    private TextView topBack;
    private TextView userName;
    private Toolbar toolBar;
    private AppBarLayout appBars;
    private TabLayout userTab;
    private TextView showCounts;
    public static ProgressBar userLoading;
    private ImageView userSex;
    private TextView userIntroduction;
    private TextView totalLike;
    private String userURL;
    private String tieziURL;
    private String shareURL;
    private String commentURL;

    private ViewPager userPager;
    private MyPagerAdapter adapter;

    private List<String> titleList = new ArrayList<>(); //页卡标题集合
    private ArrayList<Fragment> viewList = new ArrayList<>(); //页卡视图集合

    private TieziFragment tieziFragment,shareFragment;
    private PinglunFragment pinglunFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detils);
        String userId = getIntent().getBundleExtra("Id").getString("userId");
        userURL = "http://d.api.budejie.com/user/profile?userid="+userId;
        tieziURL = "http://s.budejie.com/topic/user-topic/"+userId+"/1/desc/budejie-android-6.6.5/0-20.json?";
        shareURL = "http://s.budejie.com/topic/share-topic/"+userId+"/budejie-android-6.6.5/0-20.json?";
        commentURL = "http://s.budejie.com/comment/user-comment/"+userId+"/budejie-android-6.6.5/0-20.json?";
        LogUtils.d("userId",userURL);
        initView();
    }
    //初始化控件
    private void initView(){
        showCounts = (TextView) findViewById(R.id.show_counts);
        userTab = (TabLayout) findViewById(R.id.user_tab);
        appBars = (AppBarLayout) findViewById(R.id.app_bars);
        toolBar = (Toolbar) findViewById(R.id.toolbar);
        userName = (TextView) findViewById(R.id.user_name);
        topBack = (TextView) findViewById(R.id.top_back);
        userIcon = (CircleImageView) findViewById(R.id.user_icon);
        addFocus = (TextView) findViewById(R.id.add_focus);
        userLever = (TextView) findViewById(R.id.user_level);
        guanzhuCount = (TextView) findViewById(R.id.guanzhu_count);
        fansCount = (TextView) findViewById(R.id.fans_count);
        userBackground = (ImageView) findViewById(R.id.user_bg);
        userLoading = (ProgressBar) findViewById(R.id.user_loading);
        userSex = (ImageView) findViewById(R.id.user_sex);
        userIntroduction = (TextView) findViewById(R.id.user_introduction);
        totalLike = (TextView) findViewById(R.id.user_total_like);
        userPager = (ViewPager) findViewById(R.id.user_pager);
        userLoading.setVisibility(View.VISIBLE);
        topBack.setOnClickListener(this);
        bindViewPager();
        getUserData();

    }
    //绑定tabLayout和viewPager
    private void bindViewPager(){
        titleList.add("帖子");
        titleList.add("分享");
        titleList.add("评论");

        tieziFragment = new TieziFragment();
        tieziFragment.setContent(tieziURL);
        shareFragment = new TieziFragment();
        shareFragment.setContent(shareURL);
        pinglunFragment = new PinglunFragment();
        pinglunFragment.setContent(commentURL);

        viewList.add(tieziFragment);
        viewList.add(shareFragment);
        viewList.add(pinglunFragment);
        userTab.setTabMode(TabLayout.MODE_FIXED);
        userTab.addTab(userTab.newTab().setText(titleList.get(0)));
        userTab.addTab(userTab.newTab().setText(titleList.get(1)));
        userTab.addTab(userTab.newTab().setText(titleList.get(2)));
        adapter = new MyPagerAdapter(getSupportFragmentManager(),viewList,titleList);
        userTab.setupWithViewPager(userPager);
        userPager.setAdapter(adapter);
    }

    //获取用户信息
    private void getUserData(){
        GsonRequest<UserBean> request = new GsonRequest<UserBean>(userURL, UserBean.class, new Response.Listener<UserBean>() {
            @Override
            public void onResponse(UserBean userBean) {

                //设置展开折叠颜色变化
                setExpanded(userBean);
                setUser(userBean);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context,"获取信息失败，请检查网络",Toast.LENGTH_SHORT).show();
            }
        });
        MyAppliction.getRequestQueue().add(request);
    }

    //获取用户头像、背景图、关注数等
    @SuppressLint("SetTextI18n")
    private void setUser(final UserBean user){
        //用户名
        userName.setText(user.getData().getUsername());
        //介绍
        userIntroduction.setText(user.getData().getIntroduction());
        //粉丝数
        fansCount.setText(user.getData().getFans_count()+" 粉丝");
        //关注数
        guanzhuCount.setText(user.getData().getFollow_count()+" 关注");
        //用户等级
        userLever.setText("等级：LV"+user.getData().getLevel());
        //点赞数
        totalLike.setText(user.getData().getTotal_cmt_like_count()+"");
        //用户背景
        Glide.with(context).load(user.getData().getBackground_image()).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate().into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                userBackground.setImageDrawable(glideDrawable);
            }
        });
        //用户头像
        Glide.with(context).load(user.getData().getProfile_image()).diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate().into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                userIcon.setImageDrawable(glideDrawable);
            }
        });
        if (userPager.getCurrentItem() == 0){
            showCounts.setText(user.getData().getTiezi_count()+"条帖子");
        }
        if (userPager.getCurrentItem() == 1){
            showCounts.setText(user.getData().getShare_count()+"条分享");
        }
        if (userPager.getCurrentItem() == 2){
            showCounts.setText(user.getData().getComment_count()+"条评论");
        }

        userPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    showCounts.setText(user.getData().getTiezi_count()+"条帖子");
                }
                if (position == 1){
                    showCounts.setText(user.getData().getShare_count()+"条分享");
                }
                if (position == 2){
                    showCounts.setText(user.getData().getComment_count()+"条评论");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    //设置展开折叠颜色变化
    private void setExpanded(final UserBean userBean){
        state = CollapsingToolbarLayoutState.EXPANDED;
        topBack.setShadowLayer(10,5,5, Color.BLACK);
        final Drawable drawableExpanded;
        final Drawable drawableCollasped;
        final int color;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            color = getResources().getColor(R.drawable.title_txt_press,null);
        }else {
            color = getResources().getColor(R.drawable.title_txt_press);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawableExpanded = getResources().getDrawable(R.drawable.collect_left_button_w,null);
            drawableCollasped = getResources().getDrawable(R.drawable.collect_left_button_press,null);
        }else {
            drawableExpanded = getResources().getDrawable(R.drawable.collect_left_button_w);
            drawableCollasped = getResources().getDrawable(R.drawable.collect_left_button_press);
        }
        drawableExpanded.setBounds(0,0,drawableExpanded.getMinimumWidth(),drawableExpanded.getMinimumHeight());
        drawableCollasped.setBounds(0,0,drawableCollasped.getMinimumWidth(),drawableCollasped.getMinimumHeight());
        topBack.setCompoundDrawables(drawableExpanded,null,null,null);

        topBack.setTextColor(Color.parseColor("#ffffff"));
        userName.setShadowLayer(10,5,5,Color.BLACK);
        appBars.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    if (state != CollapsingToolbarLayoutState.EXPANDED) {
                        userName.setShadowLayer(10,5,5,Color.BLACK);//展开设置阴影
                        topBack.setShadowLayer(10,5,5,Color.BLACK);//展开设置阴影
                        topBack.setCompoundDrawables(drawableExpanded,null,null,null);
                        topBack.setTextColor(Color.parseColor("#ffffff"));
                        state = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
                        if (userBean.getData().getSex().equals("m")){
                            userSex.setImageResource(R.mipmap.personal_sex_men);
                        }else {
                            userSex.setImageResource(R.mipmap.personal_sex_women);
                        }

                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                        userName.setShadowLayer(0,0,0,Color.BLACK);//折叠取消阴影
                        topBack.setShadowLayer(0,0,0,Color.BLACK);//折叠取消阴影
                        topBack.setCompoundDrawables(drawableCollasped,null,null,null);
                        topBack.setTextColor(color);
                        if (userBean.getData().getSex().equals("m")){
                            userSex.setImageResource(R.mipmap.personal_sex_men_w);
                        }else {
                            userSex.setImageResource(R.mipmap.personal_sex_women_w);
                        }
                        state = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
                    }
                } else {
                    if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                        if(state == CollapsingToolbarLayoutState.COLLAPSED){
                            userName.setShadowLayer(0,0,0,Color.BLACK);//中间到展开取消阴影
                            topBack.setShadowLayer(0,0,0,Color.BLACK);//中间到展开取消阴影
                            topBack.setCompoundDrawables(drawableCollasped,null,null,null);
                            topBack.setTextColor(color);
                            if (userBean.getData().getSex().equals("m")){
                                userSex.setImageResource(R.mipmap.personal_sex_men_w);
                            }else {
                                userSex.setImageResource(R.mipmap.personal_sex_women_w);
                            }
                        }else {
                            userName.setShadowLayer(10,5,5,Color.BLACK);//折叠到中间设置阴影
                            topBack.setShadowLayer(10,5,5,Color.BLACK);//折叠到中间设置阴影
                            topBack.setCompoundDrawables(drawableExpanded,null,null,null);
                            topBack.setTextColor(Color.parseColor("#ffffff"));
                            if (userBean.getData().getSex().equals("m")){
                                userSex.setImageResource(R.mipmap.personal_sex_men);
                            }else {
                                userSex.setImageResource(R.mipmap.personal_sex_women);
                            }
                        }

                        state = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
                    }
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
           case R.id.top_back:
               finish();
               break;
        }
    }

    private CollapsingToolbarLayoutState state;

    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }

    @Override
    protected void onPause() {
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
}
