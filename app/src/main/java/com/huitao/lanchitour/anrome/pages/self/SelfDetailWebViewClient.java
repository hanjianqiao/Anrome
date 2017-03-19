package com.huitao.lanchitour.anrome.pages.self;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.huitao.lanchitour.anrome.MainActivity;

import java.net.URLDecoder;

/**
 * Created by hanji on 2016/11/6.
 */

public class SelfDetailWebViewClient extends WebViewClient {

    private String goodId = "";
    private MainActivity m;
    public SelfDetailWebViewClient(MainActivity m, String s){
        this.m = m;
        goodId = s;
    }
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.d("WebView", "Redirect to " + URLDecoder.decode(url));
        if(url.startsWith("http")) {
            //view.loadUrl(url);
        }else if(url.startsWith("clipboard:")){
            ClipboardManager cmb = (ClipboardManager) m.getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setText(url.substring(10));
            AlertDialog alertDialog = new AlertDialog.Builder(m).create();
            alertDialog.setTitle("复制成功");
            alertDialog.setMessage(url.substring(10));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            return true;
        }else if(url.startsWith("ios:showTaobaoDetail:")){
            Log.d("WebView", ""+url.substring(21));
            Bundle args = new Bundle();
            args.putString(SelfTaobaoDetail.ARG_URL, url.substring(21));
            SelfTaobaoDetail f = new SelfTaobaoDetail();
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
        final WebView tV = view;
        //view.loadUrl("javascript:"+ LanJS.getFromAssets(c));
        super.onPageFinished(view, url);
        tV.post(new Runnable() {
            @Override
            public void run() {
                tV.loadUrl("javascript:doWork('?id="+goodId+"')");
            }
        });
        //c.tv_url.setText(url, TextView.BufferType.NORMAL);
        Log.d("WebView","onPageFinished:" + url);
    }
}
