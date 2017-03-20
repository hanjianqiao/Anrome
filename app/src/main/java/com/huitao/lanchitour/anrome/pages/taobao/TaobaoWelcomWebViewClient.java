package com.huitao.lanchitour.anrome.pages.taobao;

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

public class TaobaoWelcomWebViewClient extends WebViewClient {
    private MainActivity m;

    public TaobaoWelcomWebViewClient(MainActivity m) {
        this.m = m;
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.d("WebView", "Redirect to " + URLDecoder.decode(url));
        url = URLDecoder.decode(url);
        if (url.startsWith("http")) {
            view.loadUrl(url);
            return false;
        } else if (url.startsWith("intent")) {
            String newUrl = url.substring(url.indexOf("http"), url.indexOf(";end"));
            view.loadUrl(newUrl);
            return false;
        } else if (url.startsWith("http://e22a.com/")) {
            String newUrl = "http" + url.substring(6, url.length());
            view.loadUrl(newUrl);
            return false;
        } else if (url.startsWith("ios:?q=")) {
            view.stopLoading();
            Log.d("WebView", url);
            String newUrl;
            if (url.startsWith("ios:?q=http://")) {
                newUrl = url.substring(7);
            } else if (url.startsWith("ios:?q=https://")) {
                newUrl = url.substring(7);
            } else {
                newUrl = "https://s.m.taobao.com/h5" + url.substring(4);
            }
            //view.loadUrl(newUrl);
            Bundle args = new Bundle();
            args.putString(TaobaoMain.ARG_ITEM, newUrl);
            TaobaoMain f = new TaobaoMain();
            f.setArguments(args);
            m.showFragment(f);
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
