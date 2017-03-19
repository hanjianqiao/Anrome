package com.huitao.lanchitour.anrome.pages.shop;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.huitao.lanchitour.anrome.MainActivity;
import com.huitao.lanchitour.anrome.pages.supports.file.LanJSReader;

import java.net.URLDecoder;

/**
 * Created by hanji on 2016/11/6.
 */

public class ShopKuaitaoWebViewClient extends WebViewClient {
    private String target = "";
    private MainActivity m;

    public ShopKuaitaoWebViewClient(MainActivity m, String s) {
        target = s;
        this.m = m;
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.d("WebView", "Redirect to " + URLDecoder.decode(url));
        if (url.startsWith("http")) {
            //view.loadUrl(url);
        } else if (url.startsWith("intent")) {
            String decodedUrl = URLDecoder.decode(url);
            String newUrl = decodedUrl.substring(decodedUrl.indexOf("http"), decodedUrl.indexOf(";end"));
            view.loadUrl(newUrl);
        } else if (url.startsWith("http://e22a.com/")) {
            String newUrl = "http" + url.substring(6, url.length());
            view.loadUrl(newUrl);
            return true;
        } else if (url.startsWith("ios:")) {
            String newUrl = "";
            if (url.startsWith("ios:http://")) {
                newUrl = url.substring(4);
            } else if (url.startsWith("ios:https://")) {
                newUrl = url.substring(4);
            } else {
                newUrl = "https://s.m.taobao.com/h5" + url.substring(4);
            }
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
        view.loadUrl("javascript:" + LanJSReader.getFromAssets(m));
        super.onPageFinished(view, url);
        tV.post(new Runnable() {
            @Override
            public void run() {
                tV.loadUrl("javascript:doWork('" + target + "','true')");
            }
        });
        //c.tv_url.setText(url, TextView.BufferType.NORMAL);
        Log.d("WebView", "onPageFinished:" + url);
    }
}
