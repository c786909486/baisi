package com.ckz.baisi.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.ckz.baisi.R;
import com.ckz.baisi.adapter.HotCommentAdapter;
import com.ckz.baisi.adapter.NormalCommentAdapter;
import com.ckz.baisi.appliction.MyAppliction;
import com.ckz.baisi.bean.BaisiData;
import com.ckz.baisi.bean.CommentBean;
import com.ckz.baisi.request.GsonRequest;
import com.ckz.baisi.unitls.GetGlest;
import com.ckz.baisi.unitls.LogUtils;
import com.ckz.baisi.unitls.MyToastUtils;
import com.ckz.baisi.unitls.SDFileHelper;
import com.ckz.baisi.unitls.ScreenUtils;
import com.ckz.baisi.view.BudejieLoadMore;
import com.ckz.baisi.view.MyListView;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

@RuntimePermissions
public class ShowBigImageActivity extends AppCompatActivity implements View.OnClickListener {
    private int max = GetGlest.getGLESTextureLimit();
    private String url;
    private int page = 0;
    private SDFileHelper helper;
    //
    private Context context;
    private BaisiData.ListBean listBean;
    //view控件
    private ImageView back;
    private TwinklingRefreshLayout refreshLayout;
    private ListView normalList;
    private TextView save,share,comment;
    private BudejieLoadMore loadView;
    //List
    private List<CommentBean.HotBean.ListBean> hotBeanList;
    private List<CommentBean.NormalBean.ListBeanX> normalBeenList;
    private NormalCommentAdapter nomalAdapter;

    //headView,显示大图
    private View view;
    //通用控件
   // private TextView commenTriangle;
    private MyListView hotList;
    private HotCommentAdapter hotAdapter;
    private LinearLayout commentGroup;
    private LinearLayout hotCommentView;
    //image
    private SubsamplingScaleImageView bigImage;
    //gif
    private PhotoView bigGif;
    private PhotoViewAttacher mAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_big_image);
        context = ShowBigImageActivity.this;
        getImageData();
        initView();
        setLoadMore();
        getData();

    }

    private void setHeaderView(){
        if (listBean.getType().equals("image")){
            view = LayoutInflater.from(context).inflate(R.layout.big_image,normalList,false);

            setCommandView(view);
            bigImage = (SubsamplingScaleImageView) view.findViewById(R.id.show_big_image);
            bigImage.setMinimumHeight(ScreenUtils.getScreenHeight(context));
            bigImage.setMinScale(1.0f);
            bigImage.setMaxScale(3.0f);
            if (listBean.getImage().getHeight()< max){
                Glide.with(context).load(listBean.getImage().getBig().get(0))
                        .asBitmap().placeholder(R.mipmap.baisibudejie).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .dontAnimate().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                        bigImage.setImage(ImageSource.bitmap(bitmap));
                    }
                });
            }else {
                Glide.with(context).load(listBean.getImage().getBig().get(0))
                        .downloadOnly(new SimpleTarget<File>() {
                            @Override
                            public void onResourceReady(File file, GlideAnimation<? super File> glideAnimation) {
                                bigImage.setImage(ImageSource.uri(Uri.fromFile(file)));
                            }
                        });
            }
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.big_gif,normalList,false);
            setCommandView(view);
            bigGif = (PhotoView) view.findViewById(R.id.show_big_gif);
            bigGif.setMinimumHeight(ScreenUtils.getScreenHeight(context));

            Glide.with(context).load(listBean.getGif().getImages().get(0))
                    .asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.mipmap.baisibudejie)
                    .listener(new RequestListener<String, GifDrawable>() {
                        @Override
                        public boolean onException(Exception e, String s, Target<GifDrawable> target, boolean b) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GifDrawable gifDrawable, String s, Target<GifDrawable> target, boolean b, boolean b1) {
                            mAttacher = new PhotoViewAttacher(bigGif);
                            return false;
                        }
                    }).into(bigGif);
        }
    }
    //设置通用控件
    private void setCommandView(View view){
       // commenTriangle = (TextView) view.findViewById(R.id.big_comment_triangle);
        hotList = (MyListView) view.findViewById(R.id.hot_comment_list);
        commentGroup = (LinearLayout) view.findViewById(R.id.big_comment_group);
        hotCommentView = (LinearLayout) view.findViewById(R.id.hot_comment_area);
        hotAdapter = new HotCommentAdapter(context,hotBeanList);
        hotList.setAdapter(hotAdapter);
    }

    //初始化控件
    private void initView() {
        helper = new SDFileHelper(context);
        back = (ImageView) findViewById(R.id.big_back);
        refreshLayout = (TwinklingRefreshLayout) findViewById(R.id.refresh_layout);
        normalList = (ListView) findViewById(R.id.big_comment_list);
        save = (TextView) findViewById(R.id.show_big_save);
        share = (TextView) findViewById(R.id.show_big_share);
        comment = (TextView) findViewById(R.id.show_big_comment);
        back.setOnClickListener(this);
        save.setOnClickListener(this);
        share.setOnClickListener(this);
        comment.setOnClickListener(this);
        refreshLayout.setEnableRefresh(false);
        nomalAdapter = new NormalCommentAdapter(context,normalBeenList);
        setHeaderView();
        normalList.addHeaderView(view);
        normalList.setAdapter(nomalAdapter);
        loadView = new BudejieLoadMore(context);
        refreshLayout.setBottomView(loadView);
    }

    //获取图片网站，类型，id
    private void getImageData(){
        listBean = (BaisiData.ListBean) getIntent().getBundleExtra("image").getSerializable("imageData");
        url = "http://c.api.budejie.com/topic/comment_list/"+listBean.getId()+"/0/budejie-android-6.6.2/0-20.json?";
        normalBeenList = new ArrayList<>();
        hotBeanList = new ArrayList<>();
    }

    private void setLoadMore(){
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       getData();
                        refreshLayout.finishLoadmore();
                    }
                },700);
            }
        });
    }
    private void getData(){
        String moreUrl = url.replace("0-20",page*20+"-20");
        GsonRequest<CommentBean> request = new GsonRequest<CommentBean>(moreUrl, CommentBean.class, new Response.Listener<CommentBean>() {
            @Override
            public void onResponse(CommentBean commentBean) {
                //热门评论
                if (commentBean.getHot().getInfo().getCount()!=0){
                    if (commentBean.getHot().getList()!=null){
                        List<CommentBean.HotBean.ListBean> hotBeen = commentBean.getHot().getList();
                        hotBeanList.removeAll(hotBeen);
                        hotBeanList.addAll(hotBeen);
                        hotCommentView.setVisibility(View.VISIBLE);
                        hotAdapter.notifyDataSetChanged();
                    }
                }else {
                    hotCommentView.setVisibility(View.GONE);
                }
                //最新评论
                if (commentBean.getNormal().getInfo().getCount()!=0){
                    List<CommentBean.NormalBean.ListBeanX> normalBean = commentBean.getNormal().getList();
                    normalBeenList.removeAll(normalBean);
                    normalBeenList.addAll(normalBean);
                    nomalAdapter.notifyDataSetChanged();
                    commentGroup.setVisibility(View.VISIBLE);
//                    commenTriangle.setVisibility(View.VISIBLE);
//                    commenTriangle.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                           commenTriangle.setVisibility(View.GONE);
//                        }
//                    },500);
                    MyToastUtils.ShowBigToast(context,"上滑显示"+String.valueOf(commentBean.getNormal().getInfo().getCount())+"条评论");
                    if (commentBean.getNormal().getInfo().getCount()>normalBeenList.size()){
                        refreshLayout.setEnableLoadmore(true);
                    }else {
                        refreshLayout.setEnableLoadmore(false);
                        Toast.makeText(context,"没有更多数据了",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    commentGroup.setVisibility(View.GONE);
//                    commenTriangle.setVisibility(View.GONE);
                }
                page++;
                LogUtils.d("DataSize",url);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context,"数据获取失败,请检查网络",Toast.LENGTH_SHORT).show();
            }
        });
        MyAppliction.getRequestQueue().add(request);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.big_back:
                finish();
                break;
            case R.id.show_big_save:
                //保存图片
                ShowBigImageActivityPermissionsDispatcher.saveImagePermissionWithCheck(this);

                if (listBean.getType().equals("image")){
                   saveImage(listBean.getImage().getBig().get(0));
                }else {
                    saveGif(listBean.getGif().getImages().get(0));
                }

                break;
            case R.id.show_big_share:
                //弹出分享窗口
                Toast.makeText(context,"弹出分享窗口",Toast.LENGTH_SHORT).show();
                break;
            case R.id.show_big_comment:
                //跳转到写评论界面

                break;
        }
    }

    private void saveImage(final String url){
        Glide.with(context).load(url).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
            @Override
            public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                String filename = url.replace("http://","").replace("/","-");
                try {
                    helper.savaFileToSD(filename,bytes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void saveGif(final String url){
        Glide.with(context).load(url).asGif().toBytes().into(new SimpleTarget<byte[]>() {
            @Override
            public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                String filename = url.replace("http://","").replace("/","-");
                try {
                    helper.savaFileToSD(filename,bytes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void saveImagePermission() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ShowBigImageActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showRationale(final PermissionRequest request) {
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void permissionDenied() {
    }

    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void neverAskAgain() {
    }
}
