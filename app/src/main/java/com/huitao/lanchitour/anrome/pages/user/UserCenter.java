package com.huitao.lanchitour.anrome.pages.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.huitao.lanchitour.anrome.MainActivity;
import com.huitao.lanchitour.anrome.R;
import com.huitao.lanchitour.anrome.pages.supports.jsbridge.LanWebAppInterface;

/**
 * Created by hanji on 2017/3/9.
 */

public class UserCenter extends Fragment {
    public WebView mainView;
    public String backToUrl = "";
    private MainActivity m;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.user_layout_main, container, false);
        Button bt_back = (Button) v.findViewById(R.id.user_toolbar_back);
        Button bt_register = (Button) v.findViewById(R.id.user_bt_register);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!backToUrl.equals("")) {
                    mainView.loadUrl(backToUrl);
                }
            }
        });
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainView.loadUrl("file:///android_asset/sys_h5/register.html");
            }
        });
        mainView = (WebView) v.findViewById(R.id.webview_user);
        mainView.getSettings().setJavaScriptEnabled(true);
        // 开启DOM缓存。
        mainView.getSettings().setDomStorageEnabled(true);
        mainView.getSettings().setDatabaseEnabled(true);
        mainView.getSettings().setAppCacheEnabled(false);
        mainView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //页面大小
        mainView.getSettings().setUseWideViewPort(true);
        mainView.getSettings().setLoadWithOverviewMode(true);
        //zoom
        mainView.getSettings().setBuiltInZoomControls(true);
        mainView.getSettings().setDisplayZoomControls(false);
        mainView.getSettings().setSupportZoom(false);
        mainView.getSettings().setUseWideViewPort(true);
        mainView.getSettings().setLoadWithOverviewMode(true);
        mainView.setScaleX(1.0f);

        mainView.addJavascriptInterface(new LanWebAppInterface(getActivity()), "LanJsBridge");
        mainView.setWebViewClient(new UserWebViewClient((MainActivity) getActivity(), this));
        mainView.setInitialScale(25);
        mainView.loadUrl("file:///android_asset/sys_h5/my.html");

        return v;
    }
}
