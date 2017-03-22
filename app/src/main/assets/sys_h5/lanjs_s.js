var goodid, userid, shopid, tbtoken;
var showIt;

function RegexItem(f, c, b) {
    try {
        var a = c.exec(f);
        return a[b]
    } catch (d) {}
    return null
}

function getGoodID(a) {
    var b = /[&|?]id=(\d+)/g;
    return RegexItem(a, b, 1)
}

function setLanJPanel(text){
    document.getElementById("lanSec").innerHTML=text;
}

function isDetailPage(url){
    return url.indexOf("h5.m.taobao.com/awp/core/detail.htm") > 0 || url.indexOf("detail.m.tmall.com/item.htm") > 0 || url.indexOf("detail.m.liangxinyao.com/item.htm") > 0;
}

// general brokerage
var spTkRates;
var tkRate;

function updateGeneralBrokerageItemCallBack(htmlText, url){
    try{
        var obj = JSON.parse(htmlText);
        var ja = obj.data.campaignList;
    //LanJsBridge.showAlert('', htmlText);
        for(var i = 0; i < ja.length; i++){
            var innerText = "";
            var jo = ja[i];
            innerText += "<td>" + jo.campaignName + "</td><td>" + (jo.properties == "3" ? "是" : "否");
            try{
                innerText += "</td><td style=\"color:#fe2641\">" + (jo.campaignId == "0" ? tkRate : spTkRates[jo.campaignId]) + "%</td><td>";
            }catch(e){
                break;
            }
            innerText += "<a href=http://pub.alimama.com/myunion.htm?#!/promo/self/campaign?campaignId=";
            innerText += jo.campaignId + "&shopkeeperId=" + jo.shopKeeperId + ">";
            innerText += "<button class=\"btn_02\">申请计划</button></a></td>";
            var item = document.createElement("tr");
            item.innerHTML = innerText;
            document.getElementById("plantable").appendChild(item);
        }
    }catch(err){
        document.getElementById("plantitle").innerHTML = "请<a href=\"http://www.alimama.com/\" style=\"color:red\"><b>登陆</b></a>后查看详情";
    }
}

function updateGeneralBrokerageItem(memberId){
    htmlText = LanJsBridge.getDataFromUrl("http://pub.alimama.com/shopdetail/campaigns.json?oriMemberId="+memberId, "updateGeneralBrokerageItemCallBack");
    updateGeneralBrokerageItemCallBack(htmlText, "");
}

function updateGeneralBrokerageCallBack(htmlText, url){
    var obj = eval('('+htmlText+')');
    if(obj.data.head.status == "NORESULT"){
        document.getElementById("genbrorate").innerHTML="0%";
        document.getElementById("days30sell").innerHTML="0";
        document.getElementById("givebro").innerHTML = "0";
        document.getElementById("plantitle").innerHTML="没有计划";
    }else{
        var dataList = obj.data.pageList[0];
        document.getElementById("genbrorate").innerHTML = (showIt ? dataList.tkRate : "??")+"%";
        document.getElementById("days30sell").innerHTML = (showIt ? dataList.totalNum : "??");
        document.getElementById("givebro").innerHTML = (showIt ? dataList.totalFee : "??");
        userid = dataList.sellerId;
        spTkRates = dataList.tkSpecialCampaignIdRateMap;
        tkRate = dataList.tkRate;
        if(showIt){
            updateGeneralBrokerageItem(dataList.sellerId);
        }
    }
}

function updateGeneralBrokerage(){
    htmlText = LanJsBridge.getDataFromUrl("http://pub.alimama.com/items/search.json?q=https://item.taobao.com/item.htm?id="+goodid+"&perPageSize=50", "updateGeneralBrokerageCallBack");
    updateGeneralBrokerageCallBack(htmlText, "")
}

// queqiao brokerage

function updateQueqiaoBrokerageItemCallBack(htmlText, url){
    if(htmlText.startsWith("{\"status\":404")){
        document.getElementById("queqiaotitle").innerHTML = "没有鹊桥活动";
        return;
    }
    var jObject = eval('('+htmlText+')');
    var ja = jObject.data;
    for(var i = 0; i < ja.length; i++) {
        var innerText = "";
        var jo = ja[i];
        innerText += "<td>" + jo.event_id + "</td><td>";
        innerText += jo.end_time + "</td><td style=\"color:#fe2641\">";
        innerText += jo.final_rate + "%</td><td>";
        innerText += "<a href=https://temai.taobao.com/preview.htm?id=" + jo.event_id + ">" + "<button class=\"btn_02\" >查看计划</button>" + "</a></td>";
        var item = document.createElement("tr");
        item.innerHTML = innerText;
        document.getElementById("queqiaotable").appendChild(item);
    }
}

function updateQueqiaoBrokerageItem(goodId){
    //htmlText = LanJsBridge.getDataFromUrl("http://zhushou.taokezhushou.com/api/v1/queqiaos/"+goodId, "updateQueqiaoBrokerageItemCallBack");
    //updateQueqiaoBrokerageItemCallBack(htmlText, "");
}

function updateQueqiaoBrokerageCallBack(htmlText, url){
    var obj = eval('('+htmlText+')');
    if(obj.data.head.status == "NORESULT"){
        //document.getElementById("queqiaotitle").innerHTML="没有鹊桥佣金";
        document.getElementById('queqiaorate').innerHTML = "0%";
        return;
    }
    var jo = obj.data.pageList[0];
    try{
        document.getElementById("queqiaorate").innerHTML = (showIt ? (jo.eventRate ? jo.eventRate : "0") : "??")+"%";
    }catch(e){
        document.getElementById("queqiaorate").innerHTML = (showIt ? '0%' : "??%");
    }
    document.getElementById("genlick").href = (showIt ? ((jo.eventRate || jo.eventRate == '0') ? ("http://pub.alimama.com/promo/item/channel/index.htm?q=https%3A%2F%2Fitem.taobao.com%2Fitem.htm%3Fid%3D"+ goodid+"&channel=qqhd") : ("http://pub.alimama.com/promo/search/index.htm?q=https%3A%2F%2Fitem.taobao.com%2Fitem.htm%3Fid%3D"+ goodid)) : "");
    try {
        if(showIt){
            updateQueqiaoBrokerageItem(goodid);
        }
    }catch (err){
        //alert(err.message);
        document.getElementById("queqiaotitle").innerHTML="没有鹊桥佣金";
    }
}

function updateQueqiaoBrokerage(){
    htmlText = LanJsBridge.getDataFromUrl("http://pub.alimama.com/items/search.json?q=https://item.taobao.com/item.htm?id="+goodid+"&perPageSize=50", "updateQueqiaoBrokerageCallBack");
    updateQueqiaoBrokerageCallBack(htmlText, "");
}

// Taobao Coupon

function setClipboard(string){
    //alert("set clipboard")
    LanJsBridge.setClipboard(string);
}

function updateTaobaoCouponItemCallBack(htmlText, url){
    if(htmlText.indexOf("立刻领用") > 0){
        var innerText = "";
        innerText += "<td>";
        htmlText = htmlText.substring(htmlText.indexOf("coupon-info"), htmlText.indexOf("立刻领用"));
        innerText += htmlText.substring(htmlText.indexOf("满"), htmlText.indexOf("可用")) + "减";
        innerText += htmlText.substring(htmlText.indexOf("<dt>")+4, htmlText.indexOf("优惠券"));
        innerText += "</td><td>";
        innerText += htmlText.substring(htmlText.indexOf("有效期:")+4, htmlText.indexOf("</dl>")-5);
        innerText += "</td><td>";
        if(showIt){
            innerText += "<button class=\"btn_02\" onclick=setClipboard(\""+url+ "\")>点击复制</button>";
        }else{
            innerText += "<button class=\"btn_02\">VIP可复制</button>";
        }
        innerText += "</td>";
        var item = document.createElement("tr");
        item.innerHTML = innerText;
        document.getElementById("coupontable").appendChild(item);
    }
}

function updateTaobaoCouponItem(sellerId, activityId){
    htmlText = LanJsBridge.getDataFromUrl("http://shop.m.taobao.com/shop/coupon.htm?seller_id="+sellerId+"&activity_id="+activityId, "updateTaobaoCouponItemCallBack");
    updateTaobaoCouponItemCallBack(htmlText, "http://shop.m.taobao.com/shop/coupon.htm?seller_id="+sellerId+"&activity_id="+activityId);
}

function updateTaobaoCouponCallBack(htmlText, url){
    if(htmlText[2] == "{"){
        var obj = eval('('+htmlText+')');
        var ja = obj.priceVolumes;
        if(Object.keys(ja).length == 0){
            //document.getElementById("lantaotic").innerHTML="<caption>没有优惠券</caption><tr style=\"background: #fe2641; color:#fff;\"><th>优惠券</th><th>使用时间</th><th>手机券</th></tr>";
            return;
        }
        for(var i = 0; i < ja.length; i++){
            var activityId = ja[i].id;
            updateTaobaoCouponItem(userid, activityId);
        }
    }
    else{
        //document.getElementById("lantaotic").innerHTML="<caption>登陆查看优惠券</caption><tr style=\"background: #fe2641; color:#fff;\"><th>优惠券</th><th>使用时间</th><th>手机券</th></tr>";
        //var item = document.createElement("tr");
        //item.innerHTML = "请登陆";
        //document.getElementById("coupontable").appendChild(item);
    }
}

function updateTaobaoCoupon(){
    htmlText = LanJsBridge.getDataFromUrl("https://cart.taobao.com/json/GetPriceVolume.do?sellerId="+userid, "updateTaobaoCouponCallBack");
    updateTaobaoCouponCallBack(htmlText, "");
}

// Taokezhushou Coupon

function updateTKZSCouponItemCallBack(htmlText, url){
    if(htmlText.indexOf("立刻领用") > 0){
        var innerText = "";
        innerText += "<td>";
        htmlText = htmlText.substring(htmlText.indexOf("coupon-info"), htmlText.indexOf("立刻领用"));
        innerText += htmlText.substring(htmlText.indexOf("满"), htmlText.indexOf("可用")) + "减";
        innerText += htmlText.substring(htmlText.indexOf("<dt>")+4, htmlText.indexOf("优惠券"));
        innerText += "</td><td>";
        innerText += htmlText.substring(htmlText.indexOf("有效期:")+4, htmlText.indexOf("</dl>")-5);
        innerText += "</td><td>";
        if(showIt){
            innerText += "<button class=\"btn_02\" onclick=setClipboard(\""+url+ "\")>点击复制</button>";
        }else{
            innerText += "<button class=\"btn_02\">VIP可复制</button>";
        }
        innerText += "</td>";
        var item = document.createElement("tr");
        item.innerHTML = innerText;
        document.getElementById("coupontable").appendChild(item);
    }
}

function updateTKZSCouponItem(sellerId, activityId){
    htmlText = LanJsBridge.getDataFromUrl("http://shop.m.taobao.com/shop/coupon.htm?seller_id="+sellerId+"&activity_id="+activityId, "updateTKZSCouponItemCallBack");
    updateTKZSCouponItemCallBack(htmlText, "http://shop.m.taobao.com/shop/coupon.htm?seller_id="+sellerId+"&activity_id="+activityId);
}

function updateTKZSCouponCallBack(htmlText, url){
    var obj = eval('('+htmlText+')');
    var ja = obj.data;
    if(Object.keys(ja).length == 0){
        //document.getElementById("lanTKZtic").innerHTML="<caption>没有优惠券</caption><tr style=\"background: #fe2641; color:#fff;\"><th>优惠券</th><th>使用时间</th><th>手机券</th></tr>";
        return;
    }
    for(var i = 0; i < ja.length; i++){
        var activityId = ja[i].activity_id;
        updateTKZSCouponItem(userid, activityId);
    }
}

function updateTKZSCoupon(){
    LanJsBridge.getDataFromUrl("http://zhushou3.taokezhushou.com/api/v1/getdata?itemid="+goodid+"&version=3.5.2", "callBackNull");
    htmlText = LanJsBridge.getDataFromUrl("http://zhushou3.taokezhushou.com/api/v1/coupons_base/"+userid+"?item_id="+goodid, "updateTKZSCouponCallBack");
    updateTKZSCouponCallBack(htmlText, "");
}


function imgAutoFit(a, b){
    WebViewJavaScriptBridge1.test0();
    WebViewJavaScriptBridge1.test1('a');
    WebViewJavaScriptBridge1.have('a','b');
    return b;
}

function callBackNull(htmlString, url){
}

function callBackShowAlert(htmlString, url){
    alert(htmlString);
    return ''
}

function doWork(srcUrl, showit){
    showIt = showit;
    if(!isDetailPage(srcUrl)){
        document.getElementById("genbrorate").innerHTML="0";
        document.getElementById("days30sell").innerHTML="0";
        document.getElementById("givebro").innerHTML = "0";
        document.getElementById("queqiaorate").innerHTML = "0";
        return;
    }
    goodid = getGoodID(srcUrl);
    
    //tbtoken = LanJsBridge.getCookie("_tb_token_", "http://pub.alimama.com/")

    updateGeneralBrokerage();
    updateQueqiaoBrokerage();
    updateTaobaoCoupon();
    updateTKZSCoupon();
}

function copyToken(any){
    var tokens = document.getElementsByClassName("code-wrap-s");
    var tokenl = document.getElementsByClassName("code-wrap-l");
    var ret = "";
    for(var i = 0; i < tokens.length; i++){
        var htmlText = tokens[i].innerHTML;
        var startIndex = htmlText.indexOf("class=\"label\"");
        while(htmlText[startIndex] != ">"){startIndex++;}
        startIndex++;
        var endIndex = startIndex;
        while(htmlText[endIndex] != "<"){endIndex++;}
        ret += htmlText.substring(startIndex, endIndex);

        startIndex = htmlText.indexOf("value=");
        while(htmlText[startIndex] != "\""){startIndex++;}
        startIndex++;
        endIndex = startIndex;
        while(htmlText[endIndex] != "\""){endIndex++;}

        ret += htmlText.substring(startIndex, endIndex);
        ret += "\n"
    }
    for(var i = 0; i < tokenl.length; i++){
        var htmlText = tokenl[i].innerHTML;
        var startIndex = htmlText.indexOf("\"clipboard-target\"");
        while(htmlText[startIndex] != ">"){startIndex++;}
        startIndex++;
        var endIndex = startIndex;
        while(htmlText[endIndex] != "<"){endIndex++;}
        ret += htmlText.substring(startIndex, endIndex);
        ret += "\n"
    }
    LanJsBridge.setClipboard(ret);
}


