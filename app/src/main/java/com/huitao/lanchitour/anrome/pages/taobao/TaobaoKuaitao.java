package com.huitao.lanchitour.anrome.pages.taobao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.huitao.lanchitour.anrome.MainActivity;
import com.huitao.lanchitour.anrome.R;
import com.huitao.lanchitour.anrome.pages.supports.jsbridge.LanWebAppInterface;

/**
 * Created by hanji on 2017/3/9.
 */

public class TaobaoKuaitao extends Fragment {
    public static final String ARG_URL = "item";
    private WebView mainView;
    private String target = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        target = getArguments().getString(ARG_URL);
        Log.d("WebView", "TaobaoKuaitao load: " + target);
        View v = inflater.inflate(R.layout.taobao_layout_kuaitao, container, false);
        Button bt_back = (Button) v.findViewById(R.id.taobao_kuaitao_bt_back);
        Button bt_copy_token = (Button) v.findViewById(R.id.taobao_kuaitao_bt_copy_token);

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        bt_copy_token.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("WebView", "copytoken target: "+target);
                mainView.post(new Runnable() {
                    @Override
                    public void run() {
                        mainView.loadUrl("javascript:copyToken('" + target + "')");
                    }
                });
            }
        });

        mainView = (WebView) v.findViewById(R.id.webview_taobao_kuaitao);
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
        mainView.getSettings().setDisplayZoomControls(true);
        mainView.getSettings().setSupportZoom(true);
        mainView.setScaleX(1.0f);

        mainView.addJavascriptInterface(new LanWebAppInterface(this.getContext()), "LanJsBridge");
        mainView.setWebViewClient(new TaobaoKuaitaoWebViewClient((MainActivity) getActivity(), target));
        mainView.setInitialScale(25);
        mainView.loadUrl("file:///android_asset/sys_h5/kuaitao.html");

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void doAction(int action) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.getInstance().sync();
        }else{
            CookieManager.getInstance().flush();
        }
        mainView.reload();
    }
}
