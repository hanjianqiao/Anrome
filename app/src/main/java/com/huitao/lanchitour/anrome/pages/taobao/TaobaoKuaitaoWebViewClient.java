package com.huitao.lanchitour.anrome.pages.taobao;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.huitao.lanchitour.anrome.Global;
import com.huitao.lanchitour.anrome.MainActivity;
import com.huitao.lanchitour.anrome.R;
import com.huitao.lanchitour.anrome.pages.supports.file.LanJSReader;

import java.net.URLDecoder;

/**
 * Created by hanji on 2016/11/6.
 */

public class TaobaoKuaitaoWebViewClient extends WebViewClient {
    private String target = "";
    private MainActivity m;

    public TaobaoKuaitaoWebViewClient(MainActivity m, String s) {
        target = s;
        this.m = m;
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.d("WebView", "Redirect to " + URLDecoder.decode(url));
        if (url.startsWith("alimama")) {
            view.stopLoading();
            Bundle args = new Bundle();
            args.putString(TaobaoAlimamaLogin.ARG_ITEM, "https://login.taobao.com/member/login.jhtml?style=mini&newMini2=true&css_style=alimama&from=alimama&redirectURL=http%253A%252F%252Fwww.alimama.com&full_redirect=true&disableQuickLogin=true");
            TaobaoAlimamaLogin f = new TaobaoAlimamaLogin();
            f.setArguments(args);
            m.showFragment(f);
            return false;
            //view.loadUrl(url);
        } else if (url.startsWith("logintaobao")) {
            view.stopLoading();
            Bundle args = new Bundle();
            args.putString(TaobaoAlimamaLogin.ARG_ITEM, "https://login.m.taobao.com/login.htm");
            TaobaoAlimamaLogin f = new TaobaoAlimamaLogin();
            f.setArguments(args);
            m.showFragment(f);
            return false;
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
            String newUrl;
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
        super.onPageFinished(view, url);
        TextView title = (TextView) m.findViewById(R.id.taobao_kuaitao_title);
        if (url.endsWith("kuaitao.html")) {
            if ((target.indexOf("h5.m.taobao.com/awp/core/detail.htm") > 0) || (target.indexOf("detail.m.tmall.com/item.htm") > 0) || (target.indexOf("detail.m.liangxinyao.com/item.htm") > 0)) {
                if (Global.isVip()) {
                    title.setText(R.string.kuaitaoyixia);
                } else {
                    title.setText(R.string.buy_vip_to_check);
                }
                final WebView tV = view;
                tV.post(new Runnable() {
                    @Override
                    public void run() {
                        tV.loadUrl("javascript:doWork('" + target + "'," + Global.isVip() + ")");
                    }
                });
            } else {
                title.setText(R.string.use_in_detail_page);

                final WebView tV = view;
                tV.post(new Runnable() {
                    @Override
                    public void run() {
                        tV.loadUrl("javascript:doWork('" + target + "'," + Global.isVip() + ")");
                    }
                });
            }
        } else if (url.indexOf("alimama.com") > 0) {
            final WebView tV = view;
            view.loadUrl("javascript:" + LanJSReader.getFromAssets(m));
            tV.post(new Runnable() {
                @Override
                public void run() {
                    tV.loadUrl("javascript:" + "var element = document.createElement('meta');  element.name = \"viewport\";  element.content = \"width=device-width,initial-scale=0.6,minimum-scale=0.6,maximum-scale=0.6,user-scalable=0.6\"; var head = document.getElementsByTagName('head')[0]; head.appendChild(element);");
                }
            });
        }
        //c.tv_url.setText(url, TextView.BufferType.NORMAL);
        Log.d("WebView", "onPageFinished:" + url);
    }
}
