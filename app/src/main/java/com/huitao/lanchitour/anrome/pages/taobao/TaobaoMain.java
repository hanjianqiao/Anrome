package com.huitao.lanchitour.anrome.pages.taobao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.huitao.lanchitour.anrome.MainActivity;
import com.huitao.lanchitour.anrome.R;
import com.huitao.lanchitour.anrome.pages.supports.jsbridge.LanWebAppInterface;

/**
 * Created by hanji on 2017/3/9.
 */

public class TaobaoMain extends Fragment {
    public static final String ARG_ITEM = "item";
    private WebView mainView;
    private String target = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        target = getArguments().getString(ARG_ITEM);
        View v = inflater.inflate(R.layout.taobao_layout_main, container, false);
        ImageButton bt_home = (ImageButton) v.findViewById(R.id.taobao_bt_home);
        ImageButton bt_back = (ImageButton) v.findViewById(R.id.taobao_bt_back);
        ImageButton bt_forward = (ImageButton) v.findViewById(R.id.taobao_bt_forward);
        ImageButton bt_kuaitao = (ImageButton) v.findViewById(R.id.taobao_bt_kuaitao);

        bt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainView.loadUrl("http://www.alimama.com");
            }
        });
        bt_kuaitao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString(TaobaoKuaitao.ARG_URL, mainView.getUrl());
                Log.d("WebView", "Will kuaitao: " + mainView.getUrl());
                TaobaoKuaitao f = new TaobaoKuaitao();
                f.setArguments(args);
                ((MainActivity) getActivity()).showFragment(f);
            }
        });
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mainView.canGoBack()) {
                    mainView.goBack();
                }
            }
        });
        bt_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mainView.canGoForward()) {
                    mainView.goForward();
                }
            }
        });

        mainView = (WebView) v.findViewById(R.id.webview_taobao);
        mainView.getSettings().setJavaScriptEnabled(true);
        // 开启DOM缓存。
        mainView.getSettings().setDomStorageEnabled(true);
        mainView.getSettings().setDatabaseEnabled(true);
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
        mainView.setWebViewClient(new TaobaoMainWebViewClient());
        mainView.setInitialScale(25);
        mainView.loadUrl(target);

        return v;
    }
}
