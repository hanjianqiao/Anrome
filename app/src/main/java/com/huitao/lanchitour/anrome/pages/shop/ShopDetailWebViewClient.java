package com.huitao.lanchitour.anrome.pages.shop;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.huitao.lanchitour.anrome.Global;
import com.huitao.lanchitour.anrome.MainActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by hanji on 2016/11/6.
 */

public class ShopDetailWebViewClient extends WebViewClient {
    private String goodId = "";
    private MainActivity m;

    public ShopDetailWebViewClient(MainActivity m, String s) {
        this.m = m;
        this.goodId = s;
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        try {
            Log.d("WebView", "Redirect to " + URLDecoder.decode(url, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (url.startsWith("http")) {
            //view.loadUrl(url);
        } else if (url.startsWith("huitao:")) {
            Log.d("WebView", "" + url.substring(7));
            Bundle args = new Bundle();
            args.putString(ShopTaobaoDetail.ARG_URL, url.substring(7));
            ShopKuaitao f = new ShopKuaitao();
            f.setArguments(args);
            m.showFragment(f);
            return true;
        } else if (url.startsWith("ios:showTaobaoDetail:")) {
            Log.d("WebView", "" + url.substring(21));
            Bundle args = new Bundle();
            args.putString(ShopTaobaoDetail.ARG_URL, url.substring(21));
            ShopTaobaoDetail f = new ShopTaobaoDetail();
            f.setArguments(args);
            m.showFragment(f);
            return true;
        } else if (url.startsWith("intent")) {
            try {
                String decodedUrl = null;
                decodedUrl = URLDecoder.decode(url, "utf-8");
                String newUrl = decodedUrl.substring(decodedUrl.indexOf("http"), decodedUrl.indexOf(";end"));
                view.loadUrl(newUrl);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return false;
        } else if (view.getUrl().startsWith("http://e22a.com/")) {
            String newUrl = "http" + url.substring(6, url.length());
            view.loadUrl(newUrl);
            return false;
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
                tV.loadUrl("javascript:doWork('" + goodId + "'," + Global.isVip() + ")");
            }
        });
        //c.tv_url.setText(url, TextView.BufferType.NORMAL);
        Log.d("WebView", "onPageFinished:" + url);
    }
}
