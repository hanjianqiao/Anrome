package com.huitao.lanchitour.anrome.pages.taobao;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.huitao.lanchitour.anrome.MainActivity;

import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            Log.d("WebView", url);
            String newUrl;
            if (url.startsWith("ios:?q=http")) {
                Pattern pattern1 = Pattern.compile("http.+\\?");
                Pattern pattern2 = Pattern.compile("id=\\d+");
                Matcher matcher1 = pattern1.matcher(url);
                Matcher matcher2 = pattern2.matcher(url);
                if (!matcher1.find() || !matcher2.find()) {
                    AlertDialog alertDialog = new AlertDialog.Builder(m).create();
                    alertDialog.setTitle("不正确的商品地址");
                    alertDialog.setMessage("");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    return true;
                }
                newUrl = matcher1.group(0) + matcher2.group(0);
                Log.d("Webview", "taobao new url: " + newUrl);
            } else {
                newUrl = "https://s.m.taobao.com/h5" + url.substring(4);
            }
            //view.loadUrl(newUrl);
            Bundle args = new Bundle();
            args.putString(TaobaoMain.ARG_ITEM, newUrl);
            TaobaoMain f = new TaobaoMain();
            f.setArguments(args);
            m.showFragment(f);
            return true;
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
