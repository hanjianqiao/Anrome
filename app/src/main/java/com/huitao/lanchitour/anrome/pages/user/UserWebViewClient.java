package com.huitao.lanchitour.anrome.pages.user;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.huitao.lanchitour.anrome.Global;
import com.huitao.lanchitour.anrome.MainActivity;
import com.huitao.lanchitour.anrome.R;

import java.net.URLDecoder;

/**
 * Created by hanji on 2016/11/6.
 */

public class UserWebViewClient extends WebViewClient {
    private MainActivity m;
    private UserCenter wv;
    private String para3 = "";
    public UserWebViewClient(MainActivity m, UserCenter wv){
        this.m = m;
        this.wv = wv;
    }
    private void doSwipe(String back_title, String backUrl, String title, Boolean hideTitle){
        Button bt_back = (Button)m.findViewById(R.id.user_toolbar_back);
        TextView tv_title = (TextView)m.findViewById(R.id.user_textview_title);
        tv_title.setText(title);
        if(hideTitle){
            bt_back.setVisibility(View.INVISIBLE);
        }else{
            bt_back.setText(back_title);
            bt_back.setVisibility(View.VISIBLE);
        }
        wv.backToUrl = backUrl;
    }
    private void doSwipe(String back_title, String backUrl, String title){
        doSwipe(back_title, backUrl, title, false);
    }
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.d("WebView", "User Redirect to " + URLDecoder.decode(url));
        if(url.endsWith("notloggedin")) {
            AlertDialog alertDialog = new AlertDialog.Builder(m).create();
            alertDialog.setTitle("未登录");
            alertDialog.setMessage("请登录后继续使用");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            return true;
            //view.loadUrl(url);
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

        Button bt_register = (Button)m.findViewById(R.id.user_bt_register);
        bt_register.setVisibility(View.INVISIBLE);
        if(url.endsWith("about.html")){
            doSwipe("个人中心", "file:///android_asset/sys_h5/my.html", "关于我们");
        }else if(url.endsWith("agent.html")){
            doSwipe("个人中心", "file:///android_asset/sys_h5/my.html", "代理中心");
        }else if(url.endsWith("agent-before.html")){
            doSwipe("个人中心", "file:///android_asset/sys_h5/my.html", "代理中心");
        }else if(url.endsWith("agent-pay.html")){
            doSwipe("个人中心", "file:///android_asset/sys_h5/my.html", "代理中心");
        }else if(url.endsWith("login.html")){
            bt_register.setVisibility(View.VISIBLE);
            doSwipe("个人中心", "file:///android_asset/sys_h5/my.html", "用户登陆");
        }else if(url.endsWith("login-success.html")){
            doSwipe("个人中心", "file:///android_asset/sys_h5/my.html", "登陆成功");
        }else if(url.endsWith("message.html")){
            Global.newMessage = 0;
            doSwipe("个人中心", "file:///android_asset/sys_h5/my.html", "系统消息");
        }else if(url.endsWith("money.html")){
            doSwipe("个人中心", "file:///android_asset/sys_h5/my.html", "财富中心");
        }else if(url.endsWith("my.html")) {
            doSwipe("", "", "用户中心", true);
        }else if(url.endsWith("mybill-list.html")){
            doSwipe("财富中心", "file:///android_asset/sys_h5/money.html", "帐单列表");
        }else if(url.endsWith("newcomer.html")){
            doSwipe("个人中心", "file:///android_asset/sys_h5/my.html", "新手引导");
        }else if(url.endsWith("news.html")){
            doSwipe("系统消息", "file:///android_asset/sys_h5/message.html", "消息详情");
        }else if(url.endsWith("perfect.html")){
            doSwipe("用户注册", "file:///android_asset/sys_h5/register.html", "完善信息");
        }else if(url.endsWith("register.html")){
            doSwipe("用户登录", "file:///android_asset/sys_h5/login.html", "用户注册");
        }else if(url.endsWith("register-success.html")){
            doSwipe("用户登录", "file:///android_asset/sys_h5/login.html", "登陆成功");
        }else if(url.endsWith("vip01.html")){
            doSwipe("个人中心", "file:///android_asset/sys_h5/my.html", "VIP中心");
        }else if(url.endsWith("vip02.html")){
            doSwipe("会员服务", "file:///android_asset/sys_h5/vip03.html", "VIP中心");
        }else if(url.endsWith("vip03.html")){
            doSwipe("个人中心", "file:///android_asset/sys_h5/my.html", "VIP中心");
        }else if(url.endsWith("vipbuy.html")){
            doSwipe("购买会员", "file:///android_asset/sys_h5/vip01.html", "VIP中心");
        }else if(url.endsWith("vipextend.html")){
            doSwipe("会员服务", "file:///android_asset/sys_h5/vip03.html", "VIP中心");
        }
        Log.d("WebView","onPageStarted:" + url);
    }
    public void onPageFinished(WebView view, String url) {
        final WebView tV = view;
        //view.loadUrl("javascript:"+ LanJS.getFromAssets(c));
        super.onPageFinished(view, url);
        tV.post(new Runnable() {
            @Override
            public void run() {
                tV.loadUrl("javascript:updateDisplay('"+Global.username+"','"+ Global.password+"','"+Global.para3+"')");
            }
        });
        //c.tv_url.setText(url, TextView.BufferType.NORMAL);
        Log.d("WebView","onPageFinished:" + url);
    }
}
