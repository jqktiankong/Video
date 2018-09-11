package com.jqk.video.view.home;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

public class MyWebView extends FrameLayout {

    private WebView webView;
    private ProgressBar progressBar;
    private float density;
    private Activity activity;
    private String firstUrl = "", url = "";
    private boolean first = true;

    public MyWebView(@NonNull Activity context) {
        super(context);
        init();
    }

    public MyWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyWebView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init() {

        DisplayMetrics dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();

        density = dm.density;

        webView = new WebView(getContext());
        ViewGroup.LayoutParams webViewLp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(webView, webViewLp);

        progressBar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);
        ViewGroup.LayoutParams progressBarLp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (3 * density));
        progressBar.setMax(100);
        addView(progressBar, progressBarLp);

        webView.setWebViewClient(new WebViewClient() {
            //覆写shouldOverrideUrlLoading实现内部显示网页
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings seting = webView.getSettings();
        seting.setJavaScriptEnabled(true);//设置webview支持javascript脚本
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);//加载完网页进度条消失
                    if (first) {
                        firstUrl = getUrl();
                        first = false;
                    }
                    url = getUrl();
                } else {
                    progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar.setProgress(newProgress);//设置进度值
                }
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            //覆盖shouldOverrideUrlLoading 方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void loadUrl(String url) {
        webView.loadUrl(url);
    }

    public String getUrl() {
        return webView.getUrl();
    }

    public boolean canGoBack() {
        if (webView.canGoBack() && !firstUrl.equals(url)) {
            return true;
        } else {
            return false;
        }
    }

    public void goBack() {
        webView.goBack();
    }
}
