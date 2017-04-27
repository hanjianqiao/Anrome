package com.huitao.lanchitour.anrome.pages.taobao;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.huitao.lanchitour.anrome.MainActivity;

import java.net.URLDecoder;

/**
 * Created by Lanchitour on 20170/1/01 modifu at 2016/11/6 15:35
 */

public class TaobaoMainWebViewClient extends WebViewClient {
    private MainActivity m;

    public TaobaoMainWebViewClient(MainActivity m) {
        this.m = m;
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        url = URLDecoder.decode(url);
        Log.d("WebView", "" + view.getUrl() + ", TaobaoMainWebViewClient Redirect to " + url);
        if (url.startsWith("taobao://")) {
            if (view.getUrl() != null) {
                if (view.getUrl().startsWith("http://h5.m.taobao.com/awp/core/detail.htm?") || view.getUrl().startsWith("https://item.taobao.com/item.htm?id=")) {
                    return true;
                }
            }
            if (url.startsWith("taobao://h5.m.taobao.com/awp/core/detail.htm?")) {
                String newUrl = "http://h5.m.taobao.com/awp/core/detail.htm?" + url.substring(url.indexOf("detail.htm?") + 11, url.length());
                if (!view.getUrl().startsWith("http://h5.m.taobao.com/awp/core/detail.htm?")) {
                    view.loadUrl(newUrl);
                } else {

                }
            } else if (url.startsWith("taobao://a.m.taobao.com/i")) {
                String newUrl = "https://item.taobao.com/item.htm?id=" + url.substring(25, url.indexOf(".htm?"));
                if (!view.getUrl().startsWith("https://item.taobao.com/item.htm?id=")) {
                    view.loadUrl(newUrl);
                } else {

                }
            } else {
//                AlertDialog alertDialog = new AlertDialog.Builder(m).create();
//                alertDialog.setTitle("请截图发给客服");
//                alertDialog.setMessage("From Webview url: " + view.getUrl() + "\nLoad url: " + url);
//                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                alertDialog.show();
                String newUrl = "http" + url.substring(6, url.length());
                view.loadUrl(newUrl);
            }
        } else if (view.getUrl() != null && view.getUrl().startsWith("http://c.b1wt.com/")) {
            if (url.startsWith("https://a.m.taobao.com/i")) {
                Log.d("WebView", "a.m.taobao load " + url.substring(24));

                if (!view.getUrl().startsWith("http")) {
                    String newUrl = "https://item.taobao.com/item.htm?id=" + url.substring(24, url.indexOf(".htm?"));
                    view.loadUrl(newUrl);
                }
            } else if (url.startsWith("taobao://")) {
                Log.d("WebView", "taobao: a.m.taobao load " + url);
                if (url.startsWith("taobao://h5.m.taobao.com/awp/core/detail.htm?")) {
                    String newUrl = "http://h5.m.taobao.com/awp/core/detail.htm?" + url.substring(url.indexOf("detail.htm?") + 11, url.length());
                    if (!view.getUrl().startsWith("http://h5.m.taobao.com/awp/core/detail.htm?")) {
                        view.loadUrl(newUrl);
                    } else {

                    }
                } else if (url.startsWith("taobao://a.m.taobao.com/i")) {
                    String newUrl = "https://item.taobao.com/item.htm?id=" + url.substring(25, url.indexOf(".htm?"));
                    if (!view.getUrl().startsWith("https://item.taobao.com/item.htm?id=")) {
                        view.loadUrl(newUrl);
                    } else {

                    }
                } else {
                    String newUrl = "http" + url.substring(6, url.length());
                    view.loadUrl(newUrl);
                }
            } else {
                String newUrl = "https://item.taobao.com/item.htm?id=";
                if (url.indexOf("itemId=") > 0) {
                    newUrl += url.substring(url.indexOf("itemId=") + 7, url.length());
                } else {
                    newUrl = url;
                }
                if (!view.getUrl().startsWith("https://item.taobao.com/item.htm?id=")) {
                    view.loadUrl(newUrl);
                } else {

                }
            }
        } else if (view.getUrl() != null && view.getUrl().startsWith("http://c.b6wq.com/")) {
            if (url.startsWith("https://a.m.taobao.com/i")) {
                Log.d("WebView", "a.m.taobao load " + url.substring(24));

                if (!view.getUrl().startsWith("http")) {
                    String newUrl = "https://item.taobao.com/item.htm?id=" + url.substring(24, url.indexOf(".htm?"));
                    view.loadUrl(newUrl);
                }
            } else if (url.startsWith("taobao://")) {
                Log.d("WebView", "taobao: a.m.taobao load " + url);
                if (url.startsWith("taobao://h5.m.taobao.com/awp/core/detail.htm?")) {
                    String newUrl = "http://h5.m.taobao.com/awp/core/detail.htm?" + url.substring(url.indexOf("detail.htm?") + 11, url.length());
                    if (!view.getUrl().startsWith("http://h5.m.taobao.com/awp/core/detail.htm?")) {
                        view.loadUrl(newUrl);
                    } else {

                    }
                } else if (url.startsWith("taobao://a.m.taobao.com/i")) {
                    String newUrl = "https://item.taobao.com/item.htm?id=" + url.substring(25, url.indexOf(".htm?"));
                    if (!view.getUrl().startsWith("https://item.taobao.com/item.htm?id=")) {
                        view.loadUrl(newUrl);
                    } else {

                    }
                } else {
                    String newUrl = "http" + url.substring(6, url.length());
                    view.loadUrl(newUrl);
                }
            } else {
                String newUrl = "https://item.taobao.com/item.htm?id=";
                if (url.indexOf("itemId=") > 0) {
                    newUrl += url.substring(url.indexOf("itemId=") + 7, url.length());
                } else {
                    newUrl = url;
                }
                if (!view.getUrl().startsWith("https://item.taobao.com/item.htm?id=")) {
                    view.loadUrl(newUrl);
                } else {

                }
            }
        } else if ((view.getUrl() != null && view.getUrl().toLowerCase().startsWith("http")) || url.toLowerCase().startsWith("http")) {
            if (url.toLowerCase().startsWith("http")) {
                return false;
            }
        }
        return true;
    }

    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        Log.d("WebView", "onPageStarted:" + url);
    }

    public void onPageFinished(WebView view, String url) {
//        final WebView tV = view;
        //view.loadUrl("javascript:"+ LanJS.getFromAssets(c));
        super.onPageFinished(view, url);
//        tV.post(new Runnable() {
//            @Override
//            public void run() {
//                tV.loadUrl("javascript:lan()");
//            }
//        });
        //c.tv_url.setText(url, TextView.BufferType.NORMAL);
        Log.d("WebView", "onPageFinished:" + url);
    }
}
