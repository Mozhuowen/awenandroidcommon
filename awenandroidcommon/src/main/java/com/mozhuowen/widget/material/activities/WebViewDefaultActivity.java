package com.mozhuowen.widget.material.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.blunderer.materialdesignlibrary.handlers.ActionBarHandler;
import com.mozhuowen.R;

/**
 * Created by Awen on 16/3/31.
 * Email:mozhuowen@gmail.com
 */
public class WebViewDefaultActivity extends ActivityDefault {

    WebView webView;
    static String url;

    @Override
    public int getContentResId() {
        return R.layout.webview_default;
    }

    @Override
    public String getTitleString() {
        return "";
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl(url);
        WebSettings wSet = webView.getSettings();
        wSet.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String urlx)
            { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(urlx);
                return true;
            }

        });
//        webView.setWebViewClient(new WebViewClient());
//        webView.setWebChromeClient(new WebChromeClient());

    }

    @Override
    public boolean isActionbarVisible() {
        return true;
    }

    @Override
    public boolean isDisplayHomeAsUpEnabled() {
        return true;
    }

    @Override
    protected boolean enableActionBarShadow() {
        return false;
    }

    @Override
    protected ActionBarHandler getActionBarHandler() {
        return null;
    }

    public static void startAction(Context context,String targetUrl) {
        url = targetUrl;
        context.startActivity(new Intent(context,WebViewDefaultActivity.class));
    }
}
