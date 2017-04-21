package com.huitao.lanchitour.anrome.pages.taobao;

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

public class TaobaoAlimamaLogin extends Fragment {
    public WebView mainView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.taobao_layout_login_alimama, container, false);

        Button bt_home = (Button) v.findViewById(R.id.taobao_login_alimama_bt_back);

        bt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        mainView = (WebView) v.findViewById(R.id.taobao_login_alimama_webview);
        mainView.getSettings().setJavaScriptEnabled(true);
        // 开启DOM缓存。
        mainView.getSettings().setDomStorageEnabled(true);
        mainView.getSettings().setDatabaseEnabled(true);
        mainView.getSettings().setAppCacheEnabled(true);
        mainView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
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
        mainView.setWebViewClient(new TaobaoAlimamaLoginClient((MainActivity) getActivity()));
        mainView.setInitialScale(25);
        mainView.loadUrl("https://login.taobao.com/member/login.jhtml?style=mini&newMini2=true&css_style=alimama&from=alimama&redirectURL=http%253A%252F%252Fwww.alimama.com&full_redirect=true&disableQuickLogin=true");

        return v;
    }
}
