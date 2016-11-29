package com.lanchitour.android.taobrokerage.web;

import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.lanchitour.android.taobrokerage.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by hanji on 2016/11/6.
 */

public class LanWebAppInterface {
    Context mContext;

    final String stringMaxBrokerage = "<p style=\"text-align: left; overflow:hidden\"><span style=\" vertical-align: middle;\">最高佣金比例：</span><span id=\"LanMaxBro\" style=\"color:#fe2641; \">####</span><button class=\"btn_01\">立即申请</button></p>";

    final String stringMaxFreeBro = "<p style=\"text-align: left; overflow:hidden\"><span style=\" display:inline-block; \">免审最高佣金比例：</span><span id=\"LanMaxFreeBro\" style=\"color:#fe2641; vertical-align: middle;\">####</span><button class=\"btn_01\">立即申请</button></p>";

    final String stringGenerateLink = "<div style=\"width: 3rem; height: 0.72rem; margin: 0.38rem auto 0; text-align: center; line-height: 0.72rem; background: #fe2641;color:#fff; \">生成推广链接</div>";

    final String string30Day = "<p style=\" margin:0.12rem auto 0.4rem; color:#999;\"><span>天推广：<span id=\"Lan30DayNum\"></span>件</span>&nbsp&nbsp<span >支出佣金：<span id=\"Lan30DayOut\"></span>元</span></p>";

    final String stringPlan = "<table width=\"100%\" border=\"1\"><caption>计划详情</caption><tr style=\"background: #fe2641; color:#fff; \"><th>计划名称</th><th>人工审核</th><th>佣金比例</th><th>申请计划</th></tr>";

    final String stringPlanItem = "<tr><td>通用计划</td><td>否</td><td style=\"color:#fe2641\">5.3%</td><td><button class=\"btn_02\">申请计划</button></td></tr></table>";

    final String stringQQ = "<table width=\"100%\" border=\"1\"><caption>高佣金推广活动（新鹊桥）</caption><tr style=\"background: #fe2641; color:#fff; \"><th>计划名称</th><th>剩余天数</th><th>实得佣金比例</th><th>操作</th></tr>";

    final String stringQQItem = "<tr><td>高佣金推广...</td><td>21天</td><td  style=\"color:#fe2641\">38.28%</td><td><button class=\"btn_02\">查看计划</button></td></tr></table>";

    final String stringTicket = "<table width=\"100%\" border=\"1\"><caption>店铺优惠券</caption><tr style=\"background: #fe2641; color:#fff;\"><th>优惠券</th><th>使用时间</th><th>手机券</th></tr>";

    final String stringTicketItem = "<tr><td align=\"middle\">满100减<span  style=\"color:#fe2641\">10</span>元</td><td align=\"middle\">10月20日 - 11月15日</td><td align=\"middle\"><button class=\"btn_02\">点击复制</button></td></tr></table>";

    /** Instantiate the interface and set the context */
    public LanWebAppInterface(Context c) {
        mContext = c;
    }

    @JavascriptInterface
    public String allHtml(String goodId) throws Exception {
        String ret = "";
        String brokerUrl = "http://pub.alimama.com/items/search.json?q=https://item.taobao.com/item.htm?id=\"+goodId+\"&perPageSize=50";
        String brokerListUrl = "http://pub.alimama.com/shopdetail/campaigns.json?oriMemberId=";
        String qqUrl = "";
        String qqListUrl = "";
        String ticketUrl = "";
        return ret;
    }

    /*
    * Specific Brokerage
    * */
    @JavascriptInterface
    public String readBrokerage(String goodId) throws Exception {
        String ret = "";
        final String start = "http://pub.alimama.com/items/search.json?q=https://item.taobao.com/item.htm?id=";
        final String end = "&perPageSize=50";
        String urlPath = start + goodId + end;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[10240];
        int len = 0;
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        InputStream inStream = conn.getInputStream();
        while ((len = inStream.read(data)) != -1) {
            outStream.write(data, 0, len);
        }
        inStream.close();
        String str =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        //Log.d("WebView", str);
        JSONObject jObject = new JSONObject(str);
        if(jObject.getJSONObject("data").getJSONObject("head").getString("status").equals("NORESULT")) return "<table width=\"100%\" border=\"1\"><caption>没有计划</caption><tr style=\"background: #fe2641; color:#fff; \"><th>计划名称</th><th>人工审核</th><th>佣金比例</th><th>申请计划</th></tr></table><br><br>";
        JSONObject jo = jObject.getJSONObject("data").getJSONArray("pageList").getJSONObject(0);
        ret = "<p><span>通用佣金比例：</span>&nbsp&nbsp<span id=\"LanGeneralBro\">";
        ret += jo.getString("tkRate") + "%</span>&nbsp&nbsp<a style=\"color:#fe2641\" href=http://pub.alimama.com/promo/search/index.htm?spm=a220o.1000855.0.0.faUrHt&q=https%3A%2F%2Fitem.taobao.com%2Fitem.htm%3Fid%3D"+ goodId + ">";
        ret += "生成推广链接</a></p>";
        ret += "<p><span>30天推广：<span id=\"Lan30DayNum\">"+jo.getString("totalNum")+"</span>件</span>&nbsp&nbsp<span >支出佣金：<span id=\"Lan30DayOut\">"+jo.getString("totalFee")+"</span>元</span></p>";
        ret += "<table width=\"100%\" border=\"1\"><caption>计划详情</caption><tr style=\"background: #fe2641; color:#fff; \"><th>计划名称</th><th>人工审核</th><th>佣金比例</th><th>申请计划</th></tr>";
        if(jo.getString("tkSpecialCampaignIdRateMap").equals("null")){
            ret += readBrokerageList(jo.getString("sellerId"), null, jo.getString("tkRate"));
        }
        else {
            ret += readBrokerageList(jo.getString("sellerId"), jo.getJSONObject("tkSpecialCampaignIdRateMap"), jo.getString("tkRate"));
        }
        ret += "</table><br><br>";
        return ret;
    }
    @JavascriptInterface
    public String readBrokerageList(String memberId, JSONObject spTkRates, String tkRate) throws Exception {
        String ret = "";
        final String start = "http://pub.alimama.com/shopdetail/campaigns.json?oriMemberId=";
        final String end = "";
        String urlPath = start + memberId + end;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[10240];
        int len = 0;
        CookieManager cookieManager = CookieManager.getInstance();
        String newCookie = cookieManager.getCookie("http://pub.alimama.com/");
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Cookie", newCookie);
        InputStream inStream = conn.getInputStream();
        while ((len = inStream.read(data)) != -1) {
            outStream.write(data, 0, len);
        }
        inStream.close();
        String str =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        //Log.d("WebView", str);
        if(str.startsWith("<!")) return "<tr>请登录</tr>";
        JSONObject jObject = new JSONObject(str);
        JSONArray ja = jObject.getJSONObject("data").getJSONArray("campaignList");
        for(int i = 0; i < ja.length(); i++) {
            JSONObject jo = ja.getJSONObject(i);
            ret += "<tr><td>" + jo.getString("campaignName") + "</td><td>";
            ret += jo.getString("properties").equals("3") ? "是" : "否";
            ret += "</td><td>" + (jo.getString("campaignId").equals("0") ? tkRate : spTkRates.getString(jo.getString("campaignId"))) + "%</td><td>";
            ret += "<a  style=\"color:#fe2641\" href=http://pub.alimama.com/myunion.htm?#!/promo/self/campaign?campaignId="
                    + jo.getString("campaignId") + "&shopkeeperId=" + jo.getString("shopKeeperId") + ">"
                    + "申请计划" + "</a></td></tr>";
        }
        return ret;
    }
    @JavascriptInterface
    public String readBrokerageDetail(String keeperId, String campaignId) {
        String ret = "";
        return ret;
    }
    /*
    * QueQiao brokerage
    * */
    @JavascriptInterface
    public String readQQ(String goodId) throws Exception {
        String ret = "";
        final String start = "http://pub.alimama.com/items/search.json?q=https://item.taobao.com/item.htm?id=";
        final String end = "&perPageSize=50";
        String urlPath = start + goodId + end;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[10240];
        int len = 0;
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        InputStream inStream = conn.getInputStream();
        while ((len = inStream.read(data)) != -1) {
            outStream.write(data, 0, len);
        }
        inStream.close();
        String str =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        //Log.d("WebView", str);
        JSONObject jObject = new JSONObject(str);
        if(jObject.getJSONObject("data").getJSONObject("head").getString("status").equals("NORESULT")) return "<table width=\"100%\" border=\"1\"><caption>没有鹊桥佣金</caption><tr style=\"background: #fe2641; color:#fff; \"><th>计划名称</th><th>剩余天数</th><th>实得佣金比例</th><th>操作</th></tr></table>";
        JSONObject jo = jObject.getJSONObject("data").getJSONArray("pageList").getJSONObject(0);


        ret = "<p><span>鹊桥佣金比例：</span>&nbsp&nbsp<span id=\"LanGeneralBro\">";
        ret += jo.getString("eventRate") + "%</span></p>";

        ret = "<table width=\"100%\" border=\"1\"><caption>高佣金推广活动（新鹊桥）</caption><tr style=\"background: #fe2641; color:#fff; \"><th>鹊桥ID</th><th>结束日期</th><th>实得佣金比例</th><th>操作</th></tr>";
        try {
            ret += readQQHistory(goodId);
        }catch (Exception e){
            ret += "<tr>没有鹊桥活动</tr>";
        }
        ret += "</table><br><br>";
        return ret;
    }
    @JavascriptInterface
    public String readQQHistory(String goodId) throws Exception {
        String ret = "";
        final String start = "http://zhushou.taokezhushou.com/api/v1/queqiaos/";
        final String end = "";
        String urlPath = start + goodId + end;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[10240];
        int len = 0;
        CookieManager cookieManager = CookieManager.getInstance();
        String newCookie = cookieManager.getCookie("http://pub.alimama.com/");
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Cookie", newCookie);
        InputStream inStream = conn.getInputStream();
        while ((len = inStream.read(data)) != -1) {
            outStream.write(data, 0, len);
        }
        inStream.close();
        String str =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        //Log.d("WebView", str);
        if(str.startsWith("{\"status\":404")) return "<tr>没有鹊桥活动</tr>";
        JSONObject jObject = new JSONObject(str);
        JSONArray ja = jObject.getJSONArray("data");
        for(int i = 0; i < ja.length(); i++) {
            JSONObject jo = ja.getJSONObject(i);
            ret += "<tr><td>" + jo.getString("event_id") + "</td><td>";
            ret += jo.getString("end_time") + "</td><td>";
            ret += jo.getString("final_rate") + "%</td><td>";
            ret += "<a href=https://temai.taobao.com/preview.htm?id=" + jo.getString("event_id") + ">" + "查看计划" + "</a></td></tr>";
        }
        return ret;
    }
    @JavascriptInterface
    public String previewQQ(String QQId) {
        String ret = "";
        return ret;
    }
    @JavascriptInterface
    public String applyQQ(String QQId) {
        String ret = "";
        return ret;
    }
    /*
    * Tickets
    * */
    @JavascriptInterface
    public String readSellerTicketList(String sellerId, String goodId) throws Exception {
        String ret = "";
        final String start = "http://zhushou3.taokezhushou.com/api/v1/coupons_base/";
        final String end = "?item_id=";
        String urlPath = start + sellerId + end + goodId;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[10240];
        int len = 0;
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Cookie", "taokezhushou_plugin=eyJpdiI6IkNteUtiQnlyV1NYNUdvTWx2Y3p4Z2c9PSIsInZhbHVlIjoiNmQyNEREdDZNXC8zcDBtWllwemxCS2RkNm1SdWlcLzk2SzRURTRRWlI2WDlVZVRsZFNpMVB0XC8rcFFMT2ltUXRZa1wvUWxoczRuZUVZUGlVdTU3VEV3Nm5BPT0iLCJtYWMiOiIyMDQxYTZkZDc3MmQ3OTUyZTY5Yjc1NDI1NTM5ODQ5ZTEwMjE4N2RhOGNkZjkwNmY4ZDk1Y2MwYTM4NzI0YmU0In0%3D;");
        InputStream inStream = conn.getInputStream();
        while ((len = inStream.read(data)) != -1) {
            outStream.write(data, 0, len);
        }
        inStream.close();
        String str =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        JSONObject jObject = new JSONObject(str);
        //Log.d("webview", urlPath);
        Log.d("WebView", str);
        if(jObject.getJSONArray("data").length() == 0) return "<table width=\"100%\" border=\"1\"><caption>没有优惠券</caption><tr style=\"background: #fe2641; color:#fff;\"><th>优惠券</th><th>使用时间</th><th>手机券</th></tr>";;
        JSONArray ja = jObject.getJSONArray("data");
        ret = "<table width=\"100%\" border=\"1\"><caption>店铺优惠券</caption><tr style=\"background: #fe2641; color:#fff;\"><th>优惠券</th><th>使用时间</th><th>手机券</th></tr>";
        for(int i = 0; i < ja.length(); i++) {
            JSONObject jo = ja.getJSONObject(i);
            String activityId = jo.getString("activity_id");
            ret += readSellerTicketDetailUrl(sellerId, activityId);
        }
        ret += "</table>";
        return ret;
    }
    @JavascriptInterface
    public String readSellerTicketDetailUrl(String sellerId, String activityId) throws Exception {
        String ret = "";
        final String start = "http://shop.m.taobao.com/shop/coupon.htm?seller_id=";
        final String middle = "&activity_id=";
        final String end = "";
        String urlPath = start + sellerId + middle + activityId + end;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[10240];
        int len = 0;
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        InputStream inStream = conn.getInputStream();
        while ((len = inStream.read(data)) != -1) {
            outStream.write(data, 0, len);
        }
        inStream.close();
        String str =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        //Log.d("webview", str);
        if(str.indexOf("立刻领用") > 0){
            ret += "<tr><td>";
            str = str.substring(str.indexOf("coupon-info"), str.indexOf("立刻领用"));
            ret += str.substring(str.indexOf("满"), str.indexOf("可用"));
            ret += "减";
            ret += str.substring(str.indexOf("<dt>")+4, str.indexOf("优惠券"));
            ret += "</td><td>";
            ret += str.substring(str.indexOf("有效期:")+4, str.indexOf("</dl>")-5);
            ret += "</td><td>";
            ret += "<button onclick=\"setClipboard(" + "http://shop.m.taobao.com/shop/coupon.htm?seller_id=" + sellerId +"&activity_id=" + activityId + ")\" class=\"btn_02\">点击复制</button>";
            ret += "</td></tr>";

        }
        return ret;
    }
    /*
    * Tickets
    * */
    @JavascriptInterface
    public String readTKZSTicketList(String sellerId) throws Exception {
        String ret = "";
        final String start = "https://cart.taobao.com/json/GetPriceVolume.do?sellerId=";
        final String end = "";
        String urlPath = start + sellerId + end;
        CookieManager cookieManager = CookieManager.getInstance();
        String newCookie = cookieManager.getCookie("http://m.taobao.com/");
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[10240];
        int len = 0;
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestProperty("Cookie", newCookie);

        InputStream inStream = conn.getInputStream();
        while ((len = inStream.read(data)) != -1) {
            outStream.write(data, 0, len);
        }
        inStream.close();
        String str =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        try {
            JSONObject jObject = new JSONObject(str);
            if(jObject.getJSONArray("priceVolumes").length() == 0) return "<table width=\"100%\" border=\"1\"><caption>没有优惠券</caption><tr style=\"background: #fe2641; color:#fff;\"><th>优惠券</th><th>使用时间</th><th>手机券</th></tr>";;
            JSONArray ja = jObject.getJSONArray("priceVolumes");
            ret = "<table width=\"100%\" border=\"1\"><caption>店铺优惠券</caption><tr style=\"background: #fe2641; color:#fff;\"><th>优惠券</th><th>使用时间</th><th>手机券</th></tr>";
            for(int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                String activityId = jo.getString("id");
                ret += readTKZSTicketDetailUrl(sellerId, activityId);
            }
            ret += "</table>";
            return ret;
        }catch (Exception e){
            //Log.d("WebView", str);
            return "<tr>请登录</tr>";
        }

    }
    @JavascriptInterface
    public String readTKZSTicketDetailUrl(String sellerId, String activityId) throws Exception {
        String ret = "";
        final String start = "http://shop.m.taobao.com/shop/coupon.htm?seller_id=";
        final String middle = "&activity_id=";
        final String end = "";
        String urlPath = start + sellerId + middle + activityId + end;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[10240];
        int len = 0;
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        InputStream inStream = conn.getInputStream();
        while ((len = inStream.read(data)) != -1) {
            outStream.write(data, 0, len);
        }
        inStream.close();
        String str =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        //Log.d("webview", str);
        if(str.indexOf("立刻领用") > 0){
            ret += "<tr><td>";
            str = str.substring(str.indexOf("coupon-info"), str.indexOf("立刻领用"));
            ret += str.substring(str.indexOf("满"), str.indexOf("可用"));
            ret += "减";
            ret += str.substring(str.indexOf("<dt>")+4, str.indexOf("优惠券"));
            ret += "</td><td>";
            ret += str.substring(str.indexOf("有效期:")+4, str.indexOf("</dl>")-5);
            ret += "</td><td>";
            ret += "<button onclick=setClipboard(\"http://shop.m.taobao.com/shop/coupon.htm?seller_id=" + sellerId +"&activity_id=" + activityId + "\") class=\"btn_02\">点击复制</button>";
            ret += "</td></tr>";
        }
        return ret;
    }
    /*
    * utils
    * */
    @JavascriptInterface
    public String getSellerId(String goodId) throws Exception {
        String ret = "";
        final String start = "http://pub.alimama.com/items/search.json?q=https://item.taobao.com/item.htm?id=";
        final String end = "&perPageSize=50";
        String urlPath = start + goodId + end;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[10240];
        int len = 0;
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        InputStream inStream = conn.getInputStream();
        while ((len = inStream.read(data)) != -1) {
            outStream.write(data, 0, len);
        }
        inStream.close();
        String str =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        //Log.d("webview", str);
        JSONObject jObject = new JSONObject(str);
        JSONObject jo = jObject.getJSONObject("data").getJSONArray("pageList").getJSONObject(0);
        ret = jo.getString("sellerId");
        return ret;
    }
    /*
    * clipbaord
    * */
    @JavascriptInterface
    public void setClipboard(String text) throws Exception {
        ClipboardManager cmb = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(text);
    }
}

