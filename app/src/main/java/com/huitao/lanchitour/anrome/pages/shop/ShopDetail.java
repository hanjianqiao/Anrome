package com.huitao.lanchitour.anrome.pages.shop;

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

public class ShopDetail extends Fragment {
    public static final String ARG_GOOD_ID = "item";
    public WebView mainView;
    private String goodId = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        goodId = getArguments().getString(ARG_GOOD_ID);
        View v = inflater.inflate(R.layout.shop_layout_detail, container, false);
        Button bt_back = (Button) v.findViewById(R.id.shop_detail_bt_back);

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        mainView = (WebView) v.findViewById(R.id.webview_shop_detail);
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

        mainView.addJavascriptInterface(new LanWebAppInterface(this.getContext()), "LanJsBridge");
        mainView.setWebViewClient(new ShopDetailWebViewClient((MainActivity) getActivity(), goodId));
        mainView.setInitialScale(25);
        mainView.loadUrl("file:///android_asset/sys_h5/detail_shop.html");

        return v;
    }
}
