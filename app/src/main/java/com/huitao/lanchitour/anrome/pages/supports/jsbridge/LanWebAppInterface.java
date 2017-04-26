package com.huitao.lanchitour.anrome.pages.supports.jsbridge;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.SSLCertificateSocketFactory;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.huitao.lanchitour.anrome.Global;
import com.huitao.lanchitour.anrome.pages.supports.http.PostGetJson;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.UUID;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/**
 * Created by hanji on 2016/11/6.
 */

public class LanWebAppInterface {
    private static final String PREFS_NAME = "MyPrefsFile";
    private Context mContext;

    /**
     * Instantiate the interface and set the context
     */
    public LanWebAppInterface(Context c) {
        mContext = c;
    }

    private static HostnameVerifier getHostnameVerifier() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
    }

    @JavascriptInterface
    public String decodeURLIfBeginWithPercent(String url) {
        Log.d("WebView", "I will decode: " + url);
        try {
            if (url.startsWith("%")) {
                return URLDecoder.decode(url, "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

    @JavascriptInterface
    public String username() {
        return Global.username;
    }

    @JavascriptInterface
    public String password() {
        return Global.password;
    }

    @JavascriptInterface
    public String susername() {
        SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
        return settings.getString("username", "");
    }

    @JavascriptInterface
    public String spassword() {
        SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
        return settings.getString("password", "");
    }

    @JavascriptInterface
    public String sautologin() {
        SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
        return settings.getString("autologin", "no");
    }

    @JavascriptInterface
    public String isVip() {
        return Global.isVip() ? "yes" : "no";
    }

    @JavascriptInterface
    public String userLevel() {
        return Global.level;
    }

    @JavascriptInterface
    public String newMessage() {
        return "" + Global.newMessage;
    }

    @JavascriptInterface
    public String clearMessage() {
        Global.newMessage = 0;
        return "ok";
    }

    @JavascriptInterface
    public String setPara3(String s) {
        return Global.para3 = s;
    }

    @JavascriptInterface
    public String isLoggedIn() {
        return Global.isLoggedIn ? "yes" : "no";
    }

    @JavascriptInterface
    public void showAlert(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @JavascriptInterface
    public void showAgreement() {
        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setTitle("用户协议");
        alertDialog.setMessage("用户协议\n\n特别提示 \n\n    《小牛快淘用户协议》（以下简称“本协议”）适用于所有使用小牛快淘服务的用户（包括IOS版、安卓版app等，以下或称“您”）。本协议一经同意并接受，即形成您与小牛快淘所属的公司（以下简称“小牛快淘”）间有法律约束力之文件。您确认，您已详细阅读了本协议所有内容，您充分理解并同意接受本协议的全部内容。 \n    我们还需要提醒您的是，如本协议内容发生修改或更新，我们将会在小牛快淘提示修改内容并公布。您在使用小牛快淘公司提供的各项服务之前，应仔细阅读本协议，如您不同意变更后的协议内容，应立即停用小牛快淘及相关服务。 \n\n一、小牛快淘使用规则\n\n    小牛快淘是一个第三方淘宝客移动平台软件，通过模拟用户搜索行为，可以方便快捷的查询商品佣金、优惠券等信息，并整理和分享高价值推广产品，是您移动创业的好帮手。但小牛快淘本身不会对任何淘宝、阿里妈妈、阿里巴巴等平台账号和隐私信息、享有知识产权的信息等进行储存和修改，您可以放心使用。\n    · 小牛快淘特别提醒您：小牛快淘不会储存和修改任何用户所查询的产品佣金、优惠券等信息数据，也不会储存和修改任何第三方账号及隐私数据（如淘宝网、阿里妈妈、阿里巴巴账号等）；对于整理和分享的高价值淘宝客产品，小牛快淘不承担任何商品的质量及安全、产权等所有问题。\n    小牛快淘在正确安装后，您有权免费浏览小牛快淘，但不能使用完整的功能。如果您想要正常使用，则须通过合法渠道购买开通权限。您确认您明确知晓，您向小牛快淘购买的仅仅是小牛快淘软件本身的使用许可。同时，小牛快淘有权对上述试用政策作出调整，但无论如何，在您根据小牛快淘的销售政策购买小牛快淘之前，小牛快淘有权随时停止您使用小牛快淘的权限。 \n    · 您知晓并确认，就小牛快淘IOS版本，因为iOS 系统机制问题，目前小牛快淘iOS 版需要添加信任证书方式安装，如果因为IOS设备厂商对于系统修改的政策改变导致小牛快淘无法正常使用的，虽然小牛快淘会尽一切技术上的努力保持小牛快淘的正常使用，但小牛快淘对此不承担任何责任，且不会向您退款。您确认您在购买小牛快淘之前知晓该风险的存在并愿意承担该风险。 \n    · 您知晓并确认，就小牛快淘安卓版本，由于安卓设备种类繁多，如果因为安卓设备厂商对于安卓系统的修改等原因，导致小牛快淘无法正常使用的，虽然小牛快淘会尽一切技术上的努力保持小牛快淘的正常使用，但小牛快淘对此不承担任何责任，且不会向您退款。您确认您在购买小牛快淘之前知晓该风险的存在并愿意承担该风险。 \n    · 您知晓并确认，您自行使用软件后，如果在使用过程中造成了各种可能的不利影响，比如封号、帐户佣金扣除，或运行软件导致违反淘宝网、阿里妈妈、阿里巴巴的用户协议等,您须自行承担各种不利影响，您无权要求小牛快淘承担任何责任。 \n    · 您知晓并确认，您在购买小牛快淘开通和续费次数时，可以通过小牛快淘正规授权代理商购买。您的购买行为表示您已经同意并自愿遵守小牛快淘的相关授权规则（授权规则的修改权及最终解释权均归小牛快淘，具体内容以小牛快淘官方公布的授权规则为准）。 \n· 您知晓并确认，您仅可以通过上述官方代理渠道购买开通和续费次数，不得购买来源不明的开通和续费次数。小牛快淘有权对通过非正常途径获得的开通和续费次数进行冻结。小牛快淘保留向通过冒充、盗用他人身份或者帐号等非正常途径购买授权码的单位或个人追究法律责任的一切权力。 \n    · 您知晓并确认，小牛快淘开通和续费次数一旦购买成功，即确定完成，您不得要求退还已付金额。 \n    · 您知晓并确认，由于不可抗因素，如法律纠纷、产权纠纷等，导致小牛快淘公司终止运营或者运营权发生转移的，有权提前3日予以公告，您不得要求退还已付金额，也不得要求小牛快淘承担任何责任。 \n    · 您知晓并确认，小牛快淘提醒您妥善保管好您的账号隐私信息。非因小牛快淘原因造成的账号遗失或泄露造成开通和续费次数无法使用的，小牛快淘不承担任何责任。 \n\n二、帐号管理 \n\n    ·小牛快淘服务可能需要您注册帐号，如需要您注册的，您应保证其注册信息真实、准确、完整、合法。如上述信息发生任何变化，您应及时变更注册信息。若您提供给小牛快淘的注册信息不真实、不准确、不完整、不合法，含有违法或不良信息的，小牛快淘有权不予注册，并保留终止您使用小牛快淘各项服务的权利。您对于注册信息的不真实、不完整、不准确、未及时更新的，小牛快淘有权采取通知限期改正、暂停使用、注销登记等措施，因此所导致的一切不利后果及损失，应由您承担一切责任。 \n    · 您有义务妥善保管其在小牛快淘申请的账号及密码，不得泄漏给任何第三人。您必须为其账号及密码之全部行为负责，此账号及密码项下全部行为即视为您本身之行为。 \n    · 您账号及密码仅限于您个人使用，非经小牛快淘同意，您不得出借、移转或让与等任何方式给其他第三人使用，否则小牛快淘有权解除本协议并不退还您费用。小牛快淘不能也不会对因您未能遵守本款约定而发生的任何损失、损毁及其他不利后果负责。您理解小牛快淘对您的请求采取行动需要合理期限，在此之前，小牛快淘对已执行的指令及（或）所导致的您的损失不承担任何责任。\n    · 小牛快淘承诺对获得的您的个人信息主要为其正常的记录和管理客户信息及为本协议之目的使用。尽管小牛快淘对您的个人信息保护做了极大的努力，但是仍然不能保证现有的安全技术措施使您的隐私信息等不受任何形式的损失，您理解并同意，小牛快淘不对非因小牛快淘主观故意而导致的任何损害赔偿承担责任，包括但不限于商誉、隐私、使用、数据等方面的损失或其他无形损失的损害赔偿（无论小牛快淘是否已被告该等损害赔偿的可能性）。 \n\n三、知识产权 \n\n    ·小牛快淘软件及网站、微信公众号等所有的LOGO、商标、图形、技术、源代码与所有程序的知识产权及其他合法权益均属于小牛快淘，在此并未授权。 \n    · 未经小牛快淘许可，任何人不得擅自以任何方式（包括但不限于：以非法的方式复制、传播、展示、镜像、上传、下载）使用，或通过非常规方式（如：恶意干预数据）影响小牛快淘的正常服务,也不得对小牛快淘软件进行任何修改、反向编译，更不得在小牛快淘软件中放置或嵌入木马程序或后门等危害第三方数据安全性的程序。否则，小牛快淘将依法追究法律责任。 \n\n四、免责事由 \n\n    · 免责声明：小牛快淘不会储存和修改任何用户所查询的产品佣金、优惠券等信息数据，也不会储存和修改任何第三方账号及隐私数据（如淘宝网、阿里妈妈、阿里巴巴账号等）；对于整理和分享的高价值淘宝客产品，小牛快淘不承担任何商品的质量及安全、产权等所有问题，请您自行参考并决断。\n    您知悉并同意，小牛快淘不因下述任一情况而可能导致的任何损害赔偿承担责任，包括但不限于财产、收益、数据资料等方面的损失或其它无形损失： \n    · 因台风、地震、海啸、洪水、停电、战争、恐怖袭击等不可抗力之因素导致小牛快淘充值系统或服务不能正常运作。 \n    · 由于黑客攻击、电信部门技术调整或故障、系统维护等原因而造成的系统服务中断或者延迟。\n    · 由于政府命令、法律法规的变更、司法机关及行政机关的命令、裁定等原因而导致的系统服务中断、终止或延迟。 \n    · 由于您将授权码或小牛快淘帐户密码告知他人或未保管好自己的授权码密码或与他人共享小牛快淘帐户或任何其他非本公司的过错，导致您的个人资料泄露。 \n    · 由于与本公司链接或合作的其它网站（如网上银行等）所造成的银行帐户信息、身份信息泄露及由此而导致的任何法律争议和后果。\n    · 您（包括未成年人用户）向本公司提供错误、不完整、不实信息等，造成不能使用小牛快淘帐户或遭受任何其他损失。 \n· 如因系统维护或升级的需要而需暂停网络充值服务时，我们将尽可能事先进行通告。对于充值服务的中断或终止而给您造成的任何损失，我们无须对您或任何第三方承担任何责任。 \n    · 您已经充分阅读并同意小牛快淘刊登在软件上的《免责声明》 \n\n五、服务变更、中断或终止 \n\n    鉴于服务的特殊性，您同意小牛快淘有权随时变更、中断或终止部分或全部的服务（包括收费服务及免费服务）。\n    您明确知晓并理解，小牛快淘需要定期或不定期地对提供服务的平台进行检修或者升级维护，如因此类情况而造成收费服务在合理时间内的中断，小牛快淘无需为此承担任何责任，但小牛快淘应尽可能事先进行通告。\n\n六、违约责任 \n\n    如您违反本协议项下的任何规则、声明、承诺及保证，我们保留根据实际损失向您追究责任的权利。 \n\n七、法律适用与争议解决 \n\n    本协议的订立、效力、解释、履行、修改和终止以及争议的解决适用中国的法律。因解释和履行本协议而发生的任何争议，本协议双方应首先通过友好协商的方式加以解决。如果在一方向其他方发出要求协商解决的书面通知后30天之内争议仍然得不到解决，则任何一方均有权向法院提起诉讼。 \n\n八、隐私政策 \n\n    小牛快淘将严格保护您的隐私。为服务用户的目的，小牛快淘可能通过使用您的个人信息，向您提供服务，包括但不限于向您发出产品和服务信息，或者与小牛快淘合作伙伴共享信息以便他们向您发送有关其产品和服务的信息。除此之外，您的个人信息将仅用于收集时即已确定并且经过您同意的目的，如有除此之外的任何其他用途，我们都会提前征得您的同意。在任何时候，您的信息均依照小牛快淘的隐私政策处理，并可在小牛快淘软件上浏览。 \n\n九、其他 \n\n    · 小牛快淘不保证所有IOS设备、安卓设备均可正常运行小牛快淘，您应当在使用前先查询确认您的设备是否可以正常运行，小牛快淘不会因为您的设备无法正常运行小牛快淘向您退款。 \n    · 本协议项下所有的通知均可通过重要页面公告等方式进行，该等通知于发送之日视为已送达您。 \n    · 本协议构成双方对本协议之约定事项及其他有关事宜的完整协议，除本协议规定的，未赋予本协议各方其他权利。 \n    · 如本协议中的任何条款无论因何种原因完全或部分无效或不具有执行力，本协议的其余条款仍应有效并且有约束力。 \n    · 本协议中的标题仅为方便而设，不具法律或契约效果。");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @JavascriptInterface
    public String getDataFromUrl(String address, String callback) throws Exception {
        Log.d("WebView", " from " + address);
        //showAlert("fist get: ", address);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[10240];
        int len;
        URL url = new URL(address);
        Log.d("WebView", "URL address is " + url.toString());
        CookieManager cookieManager = CookieManager.getInstance();
        String newCookie = cookieManager.getCookie(address);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        String str = "";
        String encodingType = "UTF-8";
        try {
            conn.setConnectTimeout(4000);
            conn.setReadTimeout(10000);
            conn.setRequestProperty("Cookie", newCookie);
            InputStream inStream = conn.getInputStream();
            if (conn.getHeaderField("Content-Type").contains("GBK")) {
                encodingType = "GBK";
            }
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            str = new String(outStream.toByteArray(), encodingType);//通过out.Stream.toByteArray获取到写的数据
            Log.d("webview from" + address, str);
        } catch (SocketTimeoutException e) {
            if (e.toString().equals("java.net.SocketTimeoutException: connect timed out")) {
                Toast.makeText(mContext, "您的网络暂时不稳定，请稍后再试", Toast.LENGTH_SHORT).show();
            } else if (e.toString().equals("java.net.SocketTimeoutException: timeout")) {
                Toast.makeText(mContext, "您的网络质量低，请稍后再试", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "您的网络可能存在问题，请稍后再试", Toast.LENGTH_SHORT).show();
            }
            Log.d("WebView", e.toString());
        }
        //showAlert("then get: ", str);
        return str;
    }

    @SuppressLint("SSLCertificateSocketFactoryGetInsecure")
    @JavascriptInterface
    public String getDataFromUrls(String address, String callback) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[10240];
        int len;
        URL url = new URL(address);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setSSLSocketFactory(SSLCertificateSocketFactory.getInsecure(0, null));
        conn.setHostnameVerifier(getHostnameVerifier());
        InputStream inStream = conn.getInputStream();
        while ((len = inStream.read(data)) != -1) {
            outStream.write(data, 0, len);
        }
        inStream.close();
        String str = new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        Log.d("webview", str);
        return str;
    }

    @JavascriptInterface
    public void setRegNamePass(String username, String password) {
        Global.regUserName = username;
        Global.regPassword = password;
    }

    @JavascriptInterface
    public void setRegInfo(String code, String wechat, String qq) {
        Global.regCode = code;
        Global.regWechat = wechat;
        Global.regQQ = qq;
    }

    @JavascriptInterface
    public String register() throws Exception {
        JSONObject jsonData = new JSONObject();
        jsonData.put("user_id", Global.regUserName);
        jsonData.put("password", Global.regPassword);
        jsonData.put("code", Global.regCode);
        jsonData.put("wechat", Global.regWechat);
        jsonData.put("qq", Global.regQQ);
        jsonData.put("uuid", UUID.randomUUID().toString());
        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setTitle("正在注册...");
        alertDialog.setMessage("");
        alertDialog.show();
        JSONObject jo = PostGetJson.httpsPostGet("https://user.vsusvip.com:10000/register", jsonData.toString());
        alertDialog.dismiss();
        if (jo == null) {
            return "网络异常";
        }
        if (jo.getString("status").equals("ok")) {
            return "ok";
        }
        Log.d("WebView", jo.getString("status"));
        Log.d("WebView", jo.getString("message"));
        return jo.getString("message");
    }

    @JavascriptInterface
    public void save(String username, String password, String autologin) {
        SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putString("autologin", autologin);
        editor.apply();
    }

    @JavascriptInterface
    public String login(String username, String password) throws Exception {
        JSONObject jsonData = new JSONObject();
        jsonData.put("user_id", username);
        jsonData.put("password", password);
        jsonData.put("uuid", UUID.randomUUID().toString());
        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setTitle("正在登录...");
        alertDialog.setMessage("");
        alertDialog.show();
        JSONObject jo = PostGetJson.httpsPostGet("https://user.vsusvip.com:10000/login0", jsonData.toString());
        alertDialog.dismiss();
        if (jo == null) {
            return "网络异常";
        }
        if (jo.getString("status").equals("ok")) {
            JSONObject userInfo = new JSONObject(jo.getString("data"));
            Global.parent = userInfo.getString("parent");
            Global.username = userInfo.getString("user_id");
            Global.password = password;
            Global.isLoggedIn = true;
            Global.level = userInfo.getString("level");
            Global.endYear = Integer.valueOf(userInfo.getString("expire_year"));
            Global.endMonth = Integer.valueOf(userInfo.getString("expire_month"));
            Global.endDay = Integer.valueOf(userInfo.getString("expire_day"));
            Global.uendYear = Integer.valueOf(userInfo.getString("uexpire_year"));
            Global.uendMonth = Integer.valueOf(userInfo.getString("uexpire_month"));
            Global.uendDay = Integer.valueOf(userInfo.getString("uexpire_day"));
            return "ok";
        }
        Log.d("WebView", jo.getString("status"));
        Log.d("WebView", jo.getString("message"));
        return jo.getString("message");
    }

    @JavascriptInterface
    public String buyVip() throws Exception {
        JSONObject jsonData = new JSONObject();
        jsonData.put("user_id", Global.username);
        jsonData.put("password", Global.password);
        jsonData.put("uuid", UUID.randomUUID().toString());
        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setTitle("正在购买VIP...");
        alertDialog.setMessage("");
        alertDialog.show();
        JSONObject jo = PostGetJson.httpsPostGet("https://user.vsusvip.com:10000/up2vip", jsonData.toString());
        alertDialog.dismiss();
        if (jo == null) {
            return "网络异常";
        }
        if (jo.getString("status").equals("ok")) {
            login(Global.username, Global.password);
            return "ok";
        }
        Log.d("WebView", jo.getString("status"));
        Log.d("WebView", jo.getString("message"));
        return jo.getString("message");
    }

    @JavascriptInterface
    public String extendVip(String month) throws Exception {
        JSONObject jsonData = new JSONObject();
        jsonData.put("user_id", Global.username);
        jsonData.put("password", Global.password);
        jsonData.put("month", month);
        jsonData.put("uuid", UUID.randomUUID().toString());
        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setTitle("正在续费VIP...");
        alertDialog.setMessage("");
        alertDialog.show();
        JSONObject jo = PostGetJson.httpsPostGet("https://user.vsusvip.com:10000/extendvip", jsonData.toString());
        alertDialog.dismiss();
        if (jo == null) {
            return "网络异常";
        }
        if (jo.getString("status").equals("ok")) {
            login(Global.username, Global.password);
            return "ok";
        }
        Log.d("WebView", jo.getString("status"));
        Log.d("WebView", jo.getString("message"));
        return jo.getString("message");
    }

    @JavascriptInterface
    public String extendAgent(String combo) throws Exception {
        JSONObject jsonData = new JSONObject();
        jsonData.put("user_id", Global.username);
        jsonData.put("password", Global.password);
        jsonData.put("combo", combo);
        jsonData.put("uuid", UUID.randomUUID().toString());
        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setTitle("正在购买套餐...");
        alertDialog.setMessage("");
        alertDialog.show();
        JSONObject jo = PostGetJson.httpsPostGet("https://user.vsusvip.com:10000/extendagent", jsonData.toString());
        alertDialog.dismiss();
        if (jo == null) {
            return "网络异常";
        }
        if (jo.getString("status").equals("ok")) {
            return "ok";
        }
        Log.d("WebView", jo.getString("status"));
        Log.d("WebView", jo.getString("message"));
        return jo.getString("message");
    }

    @JavascriptInterface
    public String logout() {
        Global.username = "";
        Global.password = "";
        Global.isLoggedIn = false;

        SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("autologin", "no");
        editor.apply();
        return "ok";
    }

    @JavascriptInterface
    public String username(String address, String callback) throws Exception {
        return Global.username;
    }

    @JavascriptInterface
    public String password(String address, String callback) throws Exception {
        return Global.password;
    }

    /*
    * clipbaord
    * */
    @JavascriptInterface
    public void setClipboard(String text) throws Exception {
        ClipboardManager cmb = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(text);
        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        if (text.equals("")) {
            alertDialog.setTitle("复制失败");
        } else {
            alertDialog.setTitle("复制成功");
        }
        alertDialog.setMessage(text);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}

