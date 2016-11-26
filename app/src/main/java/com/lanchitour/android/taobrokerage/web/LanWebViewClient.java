package com.lanchitour.android.taobrokerage.web;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.lanchitour.android.taobrokerage.MainActivity;
import com.lanchitour.android.taobrokerage.javascritp.LanJS;

import java.io.IOException;
import java.net.URLDecoder;

/**
 * Created by hanji on 2016/11/6.
 */

public class LanWebViewClient extends WebViewClient {

    private MainActivity c;
    public LanWebViewClient(MainActivity mContext) {
        super();
        this.c = mContext;
    }
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.d("WebView", "Redirect to " + URLDecoder.decode(url));
        if(url.startsWith("http")) {
            //view.loadUrl(url);
        }else if(url.startsWith("intent")){
            String decodedUrl = URLDecoder.decode(url);
            String newUrl = decodedUrl.substring(decodedUrl.indexOf("http"), decodedUrl.indexOf(";end"));
            view.loadUrl(newUrl);
        }else if(view.getUrl().startsWith("http://e22a.com/")){
            String newUrl = "http" + url.substring(6, url.length());
            view.loadUrl(newUrl);
            return true;
        }
        return false;
    }
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        Log.d("WebView","onPageStarted:" + url);
    }
    public void onPageFinished(WebView view, String url) {
        final WebView tV = view;
        view.loadUrl("javascript:"+ LanJS.getFromAssets(c));
        super.onPageFinished(view, url);
        tV.post(new Runnable() {
            @Override
            public void run() {
                tV.loadUrl("javascript:lan()");
            }
        });
        c.tv_url.setText(url, TextView.BufferType.NORMAL);
        Log.d("WebView","onPageFinished:" + url);
    }
}
