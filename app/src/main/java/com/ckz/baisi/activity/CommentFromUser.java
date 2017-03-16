package com.ckz.baisi.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.ckz.baisi.R;
import com.ckz.baisi.adapter.HotCommentAdapter;
import com.ckz.baisi.adapter.NormalCommentAdapter;
import com.ckz.baisi.appliction.MyAppliction;
import com.ckz.baisi.bean.BaisiData;
import com.ckz.baisi.bean.CommentBean;
import com.ckz.baisi.bean.UserCommentBean;
import com.ckz.baisi.request.GsonRequest;
import com.ckz.baisi.unitls.CalLinesUtils;
import com.ckz.baisi.unitls.ListViewUtil;
import com.ckz.baisi.unitls.LogUtils;
import com.ckz.baisi.unitls.MyIntegerUtils;
import com.ckz.baisi.unitls.ScreenUtils;
import com.ckz.baisi.view.BudejieLoadMore;
import com.ckz.baisi.view.BudejieRefresh;
import com.ckz.baisi.view.CircleImageView;
import com.ckz.baisi.view.JCVideoCustom;
import com.ckz.baisi.view.MyListView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class CommentFromUser extends AppCompatActivity implements View.OnClickListener{
    private static final int QUANWEN = 0;
    private static final int SHOUQI = 1;
    private int type = QUANWEN;
    private int index = 1;
    private UserCommentBean.ListBean.TopicBean listBean;
    private TextView commentLeft;
    private ImageView commentRight;
    private ListView newCommentList;
    private MyListView hotCommentList;
    private TwinklingRefreshLayout refreshLayout;
    private String url;
    private List<CommentBean.HotBean.ListBean> hotBeanList;
    private List<CommentBean.NormalBean.ListBeanX> normalBeenList;
    private NormalCommentAdapter nomalAdapter;
    private HotCommentAdapter hotAdapter;
    private Context context = CommentFromUser.this;
    private Timer timer = new Timer();
    private BudejieRefresh headView;
    private BudejieLoadMore loadView;
    private long timeCha = 0;
    private View contentView;
    private RelativeLayout emptyView;
    private LinearLayout dataView;
    private LinearLayout hotCommentView;
    //用户头像区
    private CircleImageView user_icon;
    private TextView user_name,pass_time,content_txt;
    private LinearLayout user_layout;
    //评论区
    private TextView ding_btn,cai_btn,forward_btn,commend_btn;
    //段子
    private TextView quanwen_btn;
    //视频
    private JCVideoCustom jcVideoCustom;
    //gif
    private ImageView show_gif;
    //image
    private ImageView show_image;
    private LinearLayout click_large;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 123:
                    headView.refreshTime.setText(setLastTime());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initData();
        init();
        getRefreshData();
        setRefreshData();
        refreshLayout.setEnableLoadmore(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeCha++;
                handler.sendEmptyMessage(123);
            }
        },1000,1000);

    }

    private String setLastTime(){
        String lastTime = null;
        if (timeCha == System.currentTimeMillis()){
            lastTime = "没有刷新过...";
        }else if (timeCha<3600){
            if (timeCha/60<1){
                lastTime = "最后刷新：刚刚刷新";
            }else {
                lastTime = "最后刷新："+timeCha/60+"分钟前";
            }
        }else {
            lastTime = "最后刷新："+timeCha/60/60+"小时前";
        }
        return lastTime;
    }
    //初始化数据
    private void initData(){
        listBean = (UserCommentBean.ListBean.TopicBean) getIntent().getBundleExtra("Id").getSerializable("Data");
        url = "http://c.api.budejie.com/topic/comment_list/"+listBean.getId()+"/0/budejie-android-6.6.2/0-20.json?";
        LogUtils.d("URL",url);
        hotBeanList = new ArrayList<>();
        normalBeenList = new ArrayList<>();


    }
    //初始化控件
    private void init(){
        commentLeft = (TextView) findViewById(R.id.comment_left_btn);
        commentRight = (ImageView) findViewById(R.id.comment_right_btn);
        newCommentList = (ListView) findViewById(R.id.new_comment_list);
        refreshLayout = (TwinklingRefreshLayout) findViewById(R.id.comment_refresh_layout);
        commentLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        commentRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出分享窗口
            }
        });
        nomalAdapter = new NormalCommentAdapter(this,normalBeenList);
        setHeadView();
        newCommentList.addHeaderView(contentView,null,false);
        newCommentList.setAdapter(nomalAdapter);
        newCommentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context,"弹出点赞窗口,ID为："+normalBeenList.get((int) parent.getAdapter().getItemId(position)).getUser().getUsername(),Toast.LENGTH_SHORT).show();

            }
        });

    }

    //设置headview
    private void setHeadView(){
        if (listBean.getType().equals("image")){
            //image布局
            contentView = LayoutInflater.from(context).inflate(R.layout.comment_image_layout,null);
            setCommendView(contentView);
            setImage(contentView);
        }else if (listBean.getType().equals("gif")){
            //gif布局
            contentView = LayoutInflater.from(context).inflate(R.layout.comment_gif_layout,null);
            setCommendView(contentView);
            setGif(contentView);
        }else if (listBean.getType().equals("video")){
            //video布局
            contentView = LayoutInflater.from(context).inflate(R.layout.comment_video_layout,null);
            setCommendView(contentView);
            setVideo(contentView);
        }else if (listBean.getType().equals("text")){
            //duanzi布局
            contentView = LayoutInflater.from(context).inflate(R.layout.comment_duanzi_layout,null);
            setCommendView(contentView);
            setDuanzi(contentView);
        }
    }
    //设置image
    private void setImage(View view){
        show_image = (ImageView) view.findViewById(R.id.show_image);
        click_large = (LinearLayout) view.findViewById(R.id.click_large);
        if (listBean.getImage().getHeight()> ScreenUtils.getScreenHeight(context)){
            click_large.setVisibility(View.VISIBLE);
            Glide.with(context).load(listBean.getImage().getBig().get(0))
                    .asBitmap().placeholder(R.mipmap.bg_activities_item_end_transparent)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .override(listBean.getImage().getWidth(),600)
                    .dontAnimate()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                            Bitmap bitmap1 = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),600);
                            show_image.setImageBitmap(bitmap1);
                        }
                    });
        }else {
            click_large.setVisibility(View.GONE);
            Glide.with(context).load(listBean.getImage().getBig().get(0))
                    .asBitmap().placeholder(R.mipmap.bg_activities_item_end_transparent)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .override(listBean.getImage().getWidth(),listBean.getImage().getHeight())
                    .dontAnimate()
                    .into(show_image);
        }
        show_image.setOnClickListener(this);
    }

    //设置gif
    private void setGif(View view){
        show_gif = (ImageView) view.findViewById(R.id.show_gif);
        Glide.with(context).load(listBean.getGif().getImages().get(0))
                .asGif()
                .placeholder(R.mipmap.bg_activities_item_end_transparent).dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(show_gif);
        show_gif.setOnClickListener(this);
    }

    //设置视频内容
    private void setVideo(View view){
        jcVideoCustom = (JCVideoCustom) view.findViewById(R.id.show_video);
        JCVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
        jcVideoCustom.play_counts.setText(String.valueOf(listBean.getVideo().getPlaycount())+"播放");
        jcVideoCustom.total_duration.setText(MyIntegerUtils.ss2mm(listBean.getVideo().getDuration()));
        jcVideoCustom.widthRatio = listBean.getVideo().getWidth();
        jcVideoCustom.heightRatio = listBean.getVideo().getHeight();
        jcVideoCustom.setUp(listBean.getVideo().getVideo().get(0),JCVideoPlayer.SCREEN_LAYOUT_NORMAL,"");
        Glide.with(context).load(listBean.getVideo().getThumbnail().get(0))
                .placeholder(R.mipmap.bg_activities_item_end_transparent).dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(jcVideoCustom.thumbImageView);
    }
    //设置段子内容
    private void setDuanzi(View view){
        quanwen_btn = (TextView) view.findViewById(R.id.quanwen_click);
        if (CalLinesUtils.calLines(this,listBean.getText())){
            quanwen_btn.setVisibility(View.VISIBLE);
            quanwen_btn.setOnClickListener(this);
        }else {
            quanwen_btn.setVisibility(View.GONE);
        }
    }

    //设置headview中通用控件
    private void setCommendView(View view){
        //用户头像区域
        user_icon = (CircleImageView) view.findViewById(R.id.image_user_icon);
        user_name = (TextView) view.findViewById(R.id.user_name);
        pass_time = (TextView) view.findViewById(R.id.pass_time);
        content_txt = (TextView) view.findViewById(R.id.text_content);
        user_layout = (LinearLayout) view.findViewById(R.id.user_layout);
        user_layout.setOnClickListener(this);
        content_txt.setText(listBean.getText());
        Glide.with(context).load(listBean.getU().getHeader().get(0))
                .placeholder(R.mipmap.refresh_loading08)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        user_icon.setImageDrawable(glideDrawable);
                    }
                });
        user_name.setText(listBean.getU().getName());
        pass_time.setText(listBean.getPasstime());
        //底部ding
        ding_btn = (TextView) view.findViewById(R.id.comment_ding_button);
        cai_btn = (TextView) view.findViewById(R.id.comment_cai_button);
        forward_btn = (TextView) view.findViewById(R.id.comment_forward_button);
        commend_btn = (TextView) view.findViewById(R.id.comment_commend_button);
        if (!listBean.getUp().equals("0"))  ding_btn.setText(listBean.getUp());
        if (listBean.getDown()!=0) cai_btn.setText(String.valueOf(listBean.getDown()));
        if (listBean.getForward()!=0) forward_btn.setText(String.valueOf(listBean.getForward()));
        if (!listBean.getComment().equals("0")) commend_btn.setText(listBean.getComment());
        ding_btn.setOnClickListener(this);
        cai_btn.setOnClickListener(this);
        forward_btn.setOnClickListener(this);
        commend_btn.setOnClickListener(this);
        //热门评论、最新评论、无数据
        emptyView = (RelativeLayout) view.findViewById(R.id.no_data_view);
        dataView = (LinearLayout) view.findViewById(R.id.has_data_view);
        hotCommentView = (LinearLayout) view.findViewById(R.id.hot_comment_area);
        hotCommentList = (MyListView) view.findViewById(R.id.hot_comment_list);
        hotAdapter = new HotCommentAdapter(context,hotBeanList);
        hotCommentList.setAdapter(hotAdapter);
        hotCommentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context,"弹出点赞窗口,ID为："+hotBeanList.get((int) parent.getAdapter().getItemId(position)).getUser().getUsername(),Toast.LENGTH_SHORT).show();

            }
        });
    }
    //刷新数据
    private void setRefreshData(){
        headView = new BudejieRefresh(this);
        refreshLayout.setHeaderView(headView);
        loadView = new BudejieLoadMore(this);
        refreshLayout.setBottomView(loadView);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getRefreshData();
                        refreshLayout.finishRefreshing();
                    }
                },1000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getLoadMoreData();
                        refreshLayout.finishLoadmore();
                    }
                },1000);
            }
        });
    }

    private void getLoadMoreData(){
        String moreUrl = url.replace("0-20",index*20+"-20");
        GsonRequest<CommentBean> request = new GsonRequest<CommentBean>(moreUrl, CommentBean.class, new Response.Listener<CommentBean>() {
            @Override
            public void onResponse(CommentBean commentBean) {

                //最新评论
                List<CommentBean.NormalBean.ListBeanX> normalBean = commentBean.getNormal().getList();
                normalBean.removeAll(normalBeenList);
                normalBeenList.addAll(normalBean);
                nomalAdapter.notifyDataSetChanged();
                dataView.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
                if (commentBean.getNormal().getInfo().getCount()>normalBeenList.size()){
                    refreshLayout.setEnableLoadmore(true);
                }else {
                    refreshLayout.setEnableLoadmore(false);
                    Toast.makeText(context,"没有更多数据了",Toast.LENGTH_SHORT).show();
                }
                index++;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        MyAppliction.getRequestQueue().add(request);
    }

    private void getRefreshData(){
        GsonRequest<CommentBean> request = new GsonRequest<CommentBean>(url, CommentBean.class, new Response.Listener<CommentBean>() {
            @Override
            public void onResponse(CommentBean commentBean) {

                timeCha = 0;
                //热门评论
                if (commentBean.getHot().getInfo().getCount()!=0){
                    List<CommentBean.HotBean.ListBean> hotBeen = commentBean.getHot().getList();
                    hotBeanList.removeAll(hotBeen);
                    hotBeanList.addAll(0,hotBeen);
                    hotCommentView.setVisibility(View.VISIBLE);
                    hotAdapter.notifyDataSetChanged();
                }else {
                    hotCommentView.setVisibility(View.GONE);
                }
                //最新评论
                if (commentBean.getNormal().getInfo().getCount()!=0){
                    List<CommentBean.NormalBean.ListBeanX> normalBean = commentBean.getNormal().getList();
                    normalBeenList.removeAll(normalBean);
                    normalBeenList.addAll(normalBean);
                    nomalAdapter.notifyDataSetChanged();
                    dataView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                    if (commentBean.getNormal().getInfo().getCount()>normalBeenList.size()){
                        refreshLayout.setEnableLoadmore(true);
                    }else {
                        refreshLayout.setEnableLoadmore(false);
                    }
                }else {
                    dataView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        MyAppliction.getRequestQueue().add(request);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //userLayout
            case R.id.user_layout:
                //弹出访问用户窗口

                break;
            //comment_ding_layout
            case R.id.comment_ding_button:
                if (ding_btn.isClickable()){
                    ding_btn.setSelected(true);
                    int num = Integer.valueOf(ding_btn.getText().toString());
                    ding_btn.setText(String.valueOf(num+1));
                    cai_btn.setClickable(false);
                    ding_btn.setClickable(false);
                }
                break;
            case R.id.comment_cai_button:
                if (cai_btn.isClickable()){
                    cai_btn.setSelected(true);
                    int num = Integer.valueOf(cai_btn.getText().toString());
                    cai_btn.setText(String.valueOf(num+1));
                    cai_btn.setClickable(false);
                    ding_btn.setClickable(false);
                }
                break;
            case R.id.comment_forward_button:
                //弹出底部分享窗口

                break;
            case R.id.comment_commend_button:
                break;
            //全文-收起
            case R.id.quanwen_click:
                if (type == QUANWEN){
                    content_txt.setMaxLines(Integer.MAX_VALUE);
                    quanwen_btn.setText("收起");
                    type = SHOUQI;
                }else {
                    content_txt.setMaxLines(7);
                    quanwen_btn.setText("全文");
                    type = QUANWEN;
                }
                break;
            case R.id.show_gif:
            case R.id.show_image:
                Intent intent = new Intent(context, ShowBigImageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("imageData",listBean);
                intent.putExtra("image",bundle);
                startActivity(intent);
                break;
        }
    }
}
