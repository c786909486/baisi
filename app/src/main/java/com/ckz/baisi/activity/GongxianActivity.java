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
    private WebView webView;
    private ProgressBar pb;
    private static final String url = "http://m.budejie.com/user/credit.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gongxian);
        title_back = (TextView) findViewById(R.id.gongxian_back);
        title_name = (TextView) findViewById(R.id.credit_title_name);
        webView = (WebView) findViewById(R.id.gongxian_web);
        pb = (ProgressBar) findViewById(R.id.wb_progressBar);
        pb.setMax(100);
        title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient(){
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
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
                title_name.setText(title);
            }
        });
    }
}
