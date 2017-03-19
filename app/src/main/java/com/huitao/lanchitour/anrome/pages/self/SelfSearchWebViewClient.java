package com.huitao.lanchitour.anrome.pages.self;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.huitao.lanchitour.anrome.MainActivity;

import java.net.URLDecoder;

/**
 * Created by hanji on 2016/11/6.
 */

public class SelfSearchWebViewClient extends WebViewClient {

    private String key = "";
    private MainActivity m;

    public SelfSearchWebViewClient(MainActivity m, String s) {
        this.m = m;
        this.key = s;
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.d("WebView", "Redirect to " + URLDecoder.decode(url));
        if (url.startsWith("http")) {
            //view.loadUrl(url);
        } else if (url.startsWith("ios:showDetail:")) {
            Log.d("WebView", "" + url.substring(15));
            Bundle args = new Bundle();
            args.putString(SelfDetail.ARG_GOOD_ID, url.substring(15));
            SelfDetail f = new SelfDetail();
            f.setArguments(args);
            m.showFragment(f);
            return true;
        } else if (url.startsWith("intent")) {
            String decodedUrl = URLDecoder.decode(url);
            String newUrl = decodedUrl.substring(decodedUrl.indexOf("http"), decodedUrl.indexOf(";end"));
            view.loadUrl(newUrl);
        } else if (view.getUrl().startsWith("http://e22a.com/")) {
            String newUrl = "http" + url.substring(6, url.length());
            view.loadUrl(newUrl);
            return true;
        }
        return false;
    }

    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        Log.d("WebView", "onPageStarted:" + url);
    }

    public void onPageFinished(WebView view, String url) {
        final WebView tV = view;
        //view.loadUrl("javascript:"+ LanJS.getFromAssets(c));
        super.onPageFinished(view, url);
        tV.post(new Runnable() {
            @Override
            public void run() {
                tV.loadUrl("javascript:doWork('" + key + "')");
            }
        });
        //c.tv_url.setText(url, TextView.BufferType.NORMAL);
        Log.d("WebView", "onPageFinished:" + url);
    }
}
