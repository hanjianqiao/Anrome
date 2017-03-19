package com.huitao.lanchitour.anrome.pages.self;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.huitao.lanchitour.anrome.MainActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by hanji on 2016/11/6.
 */

public class SelfWebViewClient extends WebViewClient {

    private MainActivity m;
    public SelfWebViewClient(MainActivity m){
        this.m = m;
    }
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        try {
            Log.d("WebView", "Redirect to " + URLDecoder.decode(url, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(url.startsWith("ios:self:")) {
            Bundle args = new Bundle();
            try {
                args.putString(SelfSearch.ARG_KEY, URLEncoder.encode(url.substring(9), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            SelfSearch f = new SelfSearch();
            f.setArguments(args);
            m.showFragment(f);
            return true;
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
 //       final WebView tV = view;
        //view.loadUrl("javascript:"+ LanJS.getFromAssets(c));
        super.onPageFinished(view, url);
//        tV.post(new Runnable() {
//            @Override
//            public void run() {
//                tV.loadUrl("javascript:lan()");
//            }
//        });
        //c.tv_url.setText(url, TextView.BufferType.NORMAL);
        Log.d("WebView","onPageFinished:" + url);
    }
}
