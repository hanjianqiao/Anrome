package com.lanchitour.android.taobrokerage;

import com.lanchitour.android.taobrokerage.web.*;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public WebView mainView;
    private Button bt_go;
    private Button bt_tao;
    private Button bt_back;
    private Button bt_forward;
    private Button bt_person;
    private Button bt_home;
    public EditText tv_url;

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainView = (WebView)findViewById(R.id.mainwebview);
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
        mainView.getSettings().setSupportZoom(true);
        mainView.getSettings().setUseWideViewPort(true);
        mainView.getSettings().setLoadWithOverviewMode(true);
        mainView.setScaleX(1.0f);

        mainView.addJavascriptInterface(new LanWebAppInterface(this), "Android");
        mainView.setWebViewClient(new LanWebViewClient(this));
        mainView.setInitialScale(25);
        mainView.loadUrl("http://www.taobao.com");

        //button goto
        bt_go = (Button)findViewById(R.id.bt_go);
        tv_url = (EditText)findViewById(R.id.edit_url);
        bt_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainView.loadUrl(tv_url.getText().toString());
            }
        });
        //button home
        bt_home = (Button)findViewById(R.id.bt_home);
        bt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainView.loadUrl("http://www.taobao.com");
            }
        });
        //button tao
        bt_tao = (Button)findViewById(R.id.bt_tao);
        bt_tao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainView.loadUrl("http://www.alimama.com");
            }
        });
        //button forward
        bt_forward = (Button)findViewById(R.id.bt_forward);
        bt_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mainView.canGoForward()){
                    mainView.goForward();
                }
            }
        });
        //button back
        bt_back = (Button)findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mainView.canGoBack()){
                    mainView.goBack();
                }
            }
        });
        //button person
        bt_person = (Button)findViewById(R.id.bt_person);
        bt_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}
