/*
 * Copyright (C) 2016 JetRadar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huitao.lanchitour.anrome;

import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.huitao.lanchitour.anrome.pages.self.Self;
import com.huitao.lanchitour.anrome.pages.shop.Shop;
import com.huitao.lanchitour.anrome.pages.taobao.TaobaoWelcome;
import com.huitao.lanchitour.anrome.pages.user.UserCenter;
import com.jetradar.multibackstack.BackStackActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.Deque;

public class MainActivity extends BackStackActivity implements BottomNavigationBar.OnTabSelectedListener {
    private static final String STATE_CURRENT_TAB_ID = "current_tab_id";
    private static final int MAIN_TAB_ID = 3;
    // An object that manages Messages in a Thread
    public Handler mHandler;
    boolean doubleBackToExitPressedOnce = false;
    private Menu menu;
    private BottomNavigationBar bottomNavBar;
    private Fragment curFragment;
    private int curTabId;
    private Deque<Fragment> stack0 = new ArrayDeque<>();
    private Deque<Fragment> stack1 = new ArrayDeque<>();
    private Deque<Fragment> stack2 = new ArrayDeque<>();
    private Deque<Fragment> stack3 = new ArrayDeque<>();

    public void doRefresh() {
        if (Global.isLoggedIn && curTabId == 3) {
            WebView mainView = (WebView) findViewById(R.id.webview_user);
            if (mainView.getUrl().endsWith("my.html")) {
                Log.d("Static refresh", "Hello");
                mainView.reload();
            }
        }
    }

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            Global.version = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage) {
                Log.d("WebView", "Main handler: " + inputMessage.what);
                switch (inputMessage.what) {
                    case 0:
                        doRefresh();
                        break;
                    case 1:
                        AlertDialog alertDialog = new AlertDialog.Builder(Global.m).create();
                        alertDialog.setTitle("更新提示");
                        alertDialog.setMessage("为了更好的使用体验，请重新下载安装最新版本，下载地址请关注“小牛快淘”微信公众号，回复“最新版本”即可。");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                        break;
                    default:
                        super.handleMessage(inputMessage);
                }
            }
        };
        setContentView(R.layout.activity_main);
        setUpBottomNavBar();
        if (state == null) {
            Log.d("Debugs:", "7");
            bottomNavBar.selectTab(MAIN_TAB_ID, false);
            curTabId = MAIN_TAB_ID;
            showFragment(rootTabFragment(MAIN_TAB_ID));
        }
        Global.start(this);
        final Runnable beeper = new Runnable() {
            public void run() {
                try {
                    URL url = new URL("http://version.vsusvip.com:13420/android");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        throw new RuntimeException("Failed : HTTP error code : "
                                + conn.getResponseCode());
                    }
                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            (conn.getInputStream())));
                    String output;
                    String stringToParse = "";
                    while ((output = br.readLine()) != null) {
                        stringToParse += output;
                        System.out.println(output);
                    }
                    conn.disconnect();
                    JSONObject json = new JSONObject(stringToParse);

                    Log.d("WebView", "Current version is " + Global.version + " New version is " + json.getString("message"));

                    if (Global.version < Integer.valueOf(json.getString("message"))) {
                        Message message = Global.m.mHandler.obtainMessage(1);
                        message.sendToTarget();
                    }

                } catch (
                        Exception e)

                {
                    e.printStackTrace();
                }
            }
        };
        AsyncTask.execute(beeper);
    }

    private void setUpBottomNavBar() {
        bottomNavBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation);
        Global.taobao = new BottomNavigationItem(R.drawable.ic_taobao, R.string.taobao);
        Global.shop = new BottomNavigationItem(R.drawable.ic_shop, R.string.shop);
        Global.self = new BottomNavigationItem(R.drawable.ic_self, R.string.self);
        Global.user = new BottomNavigationItem(R.drawable.ic_user, R.string.user);
        bottomNavBar
                .addItem(Global.taobao)
                .addItem(Global.shop)
                .addItem(Global.self)
                .addItem(Global.user)
                .initialise();
        bottomNavBar.setTabSelectedListener(this);
    }

    @NonNull
    private Fragment rootTabFragment(int tabId) {
        switch (tabId) {
            case 0:
                return new TaobaoWelcome();
            case 1:
                return new Shop();
            case 2:
                return new Self();
            case 3:
                return new UserCenter();
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        curFragment = getSupportFragmentManager().findFragmentById(R.id.content);
        curTabId = savedInstanceState.getInt(STATE_CURRENT_TAB_ID);
        bottomNavBar.selectTab(curTabId, false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_CURRENT_TAB_ID, curTabId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        //Pair<Integer, Fragment> pair = popFragmentFromBackStack();
        Fragment f = null;
        switch (curTabId) {
            case 0:
                if (!stack0.isEmpty())
                    f = stack0.pop();
                break;
            case 1:
                if (!stack1.isEmpty())
                    f = stack1.pop();
                break;
            case 2:
                if (!stack2.isEmpty())
                    f = stack2.pop();
                break;
            case 3:
                if (!stack3.isEmpty())
                    f = stack3.pop();
                break;
        }
        if (f != null) {
            Log.d("Debugs:", "8");
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction tr = fm.beginTransaction();
            tr.remove(curFragment).commit();
            backTo(f);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 1000);
        }
    }

    @Override
    public void onTabSelected(int position) {
        Log.d("TabF", "Tab Selected:" + position + " Cur:" + curTabId);
        if (!Global.isLoggedIn) {
            bottomNavBar.selectTab(3, false);
            if (curTabId != 3) {
                if (curFragment != null) {
                    switch (curTabId) {
                        case 0:
                            stack0.push(curFragment);
                            break;
                        case 1:
                            stack1.push(curFragment);
                            break;
                        case 2:
                            stack2.push(curFragment);
                            break;
                    }
                    Log.d("TabF", "Tab cur is none:" + position);
                }
                curTabId = 3;
                Fragment f = null;
                if (!stack3.isEmpty()) {
                    f = stack3.pop();
                }

                if (f == null) {
                    f = rootTabFragment(curTabId);
                    Log.d("TabF", "Tab frag is none:" + position);
                }
                replaceFragment(f);
            }
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("请登录后使用");
            alertDialog.setMessage("");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            return;
        }
        if (curFragment != null) {
            switch (curTabId) {
                case 0:
                    stack0.push(curFragment);
                    break;
                case 1:
                    stack1.push(curFragment);
                    break;
                case 2:
                    stack2.push(curFragment);
                    break;
                case 3:
                    stack3.push(curFragment);
                    break;
            }
            Log.d("TabF", "Tab cur is none:" + position);
        }
        curTabId = position;
        Fragment f = null;
        switch (curTabId) {
            case 0:
                if (!stack0.isEmpty())
                    f = stack0.pop();
                break;
            case 1:
                if (!stack1.isEmpty())
                    f = stack1.pop();
                break;
            case 2:
                if (!stack2.isEmpty())
                    f = stack2.pop();
                break;
            case 3:
                if (!stack3.isEmpty())
                    f = stack3.pop();
                break;
        }
        if (f == null) {
            f = rootTabFragment(curTabId);
            Log.d("TabF", "Tab frag is none:" + position);
        }
        replaceFragment(f);
    }

    @Override
    public void onTabReselected(int position) {
        Log.d("TabF", "Tab Reselected:" + position);
        if (position == 0) {
            WebView wv = (WebView) findViewById(R.id.webview_taobao_welcome);
            wv.loadUrl("file:///android_asset/sys_h5/index.html");
            Fragment f;
            if (stack0.isEmpty()) {
                return;
            }
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction tr = fm.beginTransaction();
            f = curFragment;
            while (!stack0.isEmpty()) {
                tr.remove(f);
                f = stack0.pop();
            }
            tr.commit();
            backTo(f);
        }
    }

    @Override
    public void onTabUnselected(int position) {
    }

    public void showFragment(@NonNull Fragment fragment) {
        showFragment(fragment, true);
    }

    public void showFragment(@NonNull Fragment fragment, boolean addToBackStack) {
        Log.d("Debugs:", "6");
        if (curFragment != null && addToBackStack) {
            switch (curTabId) {
                case 0:
                    stack0.push(curFragment);
                    break;
                case 1:
                    stack1.push(curFragment);
                    break;
                case 2:
                    stack2.push(curFragment);
                    break;
                case 3:
                    stack3.push(curFragment);
                    break;
            }
        }
        replaceFragment(fragment);
    }

    private void backTo(@NonNull Fragment fragment) {
        Log.d("Debugs:", "5" + fragment.isAdded());
        replaceFragment(fragment);
        getSupportFragmentManager().executePendingTransactions();
    }


    private void replaceFragment(@NonNull Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tr = fm.beginTransaction();
        if (curFragment != null && fragment.isAdded()) {
            Log.d("Debugs:", "1");
            tr.hide(curFragment).show(fragment);
        } else if (curFragment != null) {
            Log.d("Debugs:", "2" + fragment);
            tr.hide(curFragment).add(R.id.content, fragment, "");
        } else {
            Log.d("Debugs:", "9");
            tr.add(R.id.content, fragment);
        }
        Log.d("Debugs:", "3");
        curFragment = fragment;
        tr.commit();
        fm.executePendingTransactions();
        Log.d("Debugs:", "4" + fragment.isAdded());
    }

    @Override
    public void onDestroy() {
        Global.stop();
        super.onDestroy();
    }
}