package com.huitao.lanchitour.anrome;

import android.os.Message;
import android.util.Log;

import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.huitao.lanchitour.anrome.pages.supports.http.PostGetJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by hanji on 2017/3/10.
 */

public class Global {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    public static int version = 7;
    public static MainActivity m;
    public static String username = "";
    public static String password = "";
    public static boolean isLoggedIn = false;
    public static String level = "user";
    public static int endYear = 0;
    public static int endMonth = 0;
    public static int endDay = 0;
    public static int newMessage = 0;
    public static ScheduledFuture<?> beeperHandle;
    public static String regUserName = "";
    public static String regPassword = "";
    public static String regCode = "";
    public static String regWechat = "";
    public static String regQQ = "";
    public static String para3 = "";
    public static BottomNavigationItem taobao;
    public static BottomNavigationItem shop;
    public static BottomNavigationItem self;
    public static BottomNavigationItem user;

    public static boolean isVip() {
        Calendar calendar = Calendar.getInstance();
        boolean Y = (endYear < calendar.get(Calendar.YEAR));
        boolean YE = (endYear == (calendar.get(Calendar.YEAR)));
        boolean M = (endMonth < calendar.get(Calendar.MONTH));
        boolean ME = (endMonth == (calendar.get(Calendar.MONTH)));
        boolean D = (endDay < calendar.get(Calendar.DAY_OF_MONTH));
        Log.d("WebView", "" + Y + YE + M + ME + D);
        return !(Y || (YE && M) || (YE && ME && D));
    }

    public static void start(final MainActivity m) {
        Global.m = m;
        final Runnable beeper = new Runnable() {
            public void run() {
                if (!Global.isLoggedIn) {
                    return;
                }
                JSONObject jsonData = new JSONObject();
                try {
                    jsonData.put("user_id", username);
                    jsonData.put("password", password);
                    jsonData.put("uuid", UUID.randomUUID().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject jo = PostGetJson.httpPostGet("http://user.vsusvip.com:30000/check", jsonData.toString());
                try {
                    if (jo != null && jo.getString("status").equals("ok"))
                        if (jo.getString("message").equals("yes")) {
                            Global.newMessage = 1;
                            Message message = m.mHandler.obtainMessage(0);
                            message.sendToTarget();
                        }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Calendar calendar = Calendar.getInstance();
                Log.d("WebView", "" + calendar.get(Calendar.DAY_OF_MONTH));
            }
        };
        beeperHandle = scheduler.scheduleAtFixedRate(beeper, 10, 60, SECONDS);
//        scheduler.schedule(new Runnable() {
//            public void run() { beeperHandle.cancel(true); }
//        }, 60 * 60, SECONDS);
    }

    public static void stop() {
        beeperHandle.cancel(true);
    }
}
