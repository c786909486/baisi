package com.ckz.baisi.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ckz.baisi.R;

import java.io.File;

public class GongxianActivity extends AppCompatActivity {
    private TextView title_back,title_name;
    private WebView displayWebview;
    private ProgressBar pb;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gongxian);
        url = getIntent().getStringExtra("url");
        title_back = (TextView) findViewById(R.id.gongxian_back);
        title_name = (TextView) findViewById(R.id.credit_title_name);
        displayWebview = (WebView) findViewById(R.id.gongxian_web);
        pb = (ProgressBar) findViewById(R.id.wb_progressBar);
        pb.setMax(100);
        title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (displayWebview.canGoBack()){
                    displayWebview.goBack();
                }else {
                    finish();
                }
            }
        });
        displayWebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        displayWebview.getSettings().setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        displayWebview.getSettings().setSupportZoom(true);//是否可以缩放，默认true
        displayWebview.getSettings().setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        displayWebview.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        displayWebview.getSettings().setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        displayWebview.getSettings().setAppCacheEnabled(true);//是否使用缓存
        displayWebview.getSettings().setDomStorageEnabled(true);//DOM Storage
        displayWebview.loadUrl(url);
        displayWebview.setWebViewClient(new WebViewClient(){
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        displayWebview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                pb.setProgress(newProgress);
                if(newProgress==100){
                    pb.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (title.contains(".com")){
                    title_name.setText("百思不得姐");
                }else {
                    title_name.setText(title);
                }

            }
        });
    }

    @Override
    public void onBackPressed() {

        if (displayWebview.canGoBack()){
            displayWebview.goBack();
        }else {
            super.onBackPressed();
        }

    }
}
