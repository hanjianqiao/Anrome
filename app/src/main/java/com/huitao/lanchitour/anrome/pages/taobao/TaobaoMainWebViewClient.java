package com.huitao.lanchitour.anrome.pages.taobao;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URLDecoder;

/**
 * Created by Lanchitour on 20170/1/01 modifu at 2016/11/6 15:35
 */

public class TaobaoMainWebViewClient extends WebViewClient {
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.d("WebView", "TaobaoMainWebViewClient Redirect to " + url);
        if (url.startsWith("http")) {
            view.loadUrl(url);
            return false;
        } else if (url.startsWith("intent")) {
            String decodedUrl = URLDecoder.decode(url);
            String newUrl = decodedUrl.substring(decodedUrl.indexOf("http"), decodedUrl.indexOf(";end"));
            view.loadUrl(newUrl);
            return false;
        } else if (view.getUrl().startsWith("http://e22a.com/")) {
            String newUrl = "http" + url.substring(6, url.length());
            view.loadUrl(newUrl);
            return false;
        } else if (url.startsWith("taobao://")) {
            String newUrl = "http" + url.substring(6, url.length());
            view.loadUrl(newUrl);
            return false;
        } else if (view.getUrl().startsWith("http://c.b1wt.com/")) {
            String newUrl = "https://item.taobao.com/item.htm?id=" + url.substring(url.indexOf("itemId=") + 7, url.length());
            Log.d("WebView", "TaobaoMainWebViewClient load to " + newUrl);
            view.loadUrl(newUrl);
            return false;
        }
        return true;
    }

    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        Log.d("WebView", "onPageStarted:" + url);
    }

    public void onPageFinished(WebView view, String url) {
//        final WebView tV = view;
        //view.loadUrl("javascript:"+ LanJS.getFromAssets(c));
        super.onPageFinished(view, url);
//        tV.post(new Runnable() {
//            @Override
//            public void run() {
//                tV.loadUrl("javascript:lan()");
//            }
//        });
        //c.tv_url.setText(url, TextView.BufferType.NORMAL);
        Log.d("WebView", "onPageFinished:" + url);
    }
}
