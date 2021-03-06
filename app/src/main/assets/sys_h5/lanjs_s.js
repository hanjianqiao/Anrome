var goodid, userid, shopid, tbtoken;
var showIt;

function decodeHtml(html) {
    var txt = document.createElement("textarea");
    txt.innerHTML = html;
    return txt.value;
}

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
    return url.indexOf("h5.m.taobao.com/awp/core/detail.htm") > 0 || url.indexOf("detail.m.tmall.com/item.htm") > 0
    || url.indexOf("detail.m.liangxinyao.com/item.htm") > 0 || url.indexOf("?id=") > 0;
}

// general brokerage
var spTkRates;
var tkRate;

function updateBrokerageAcrateItemCallback(htmlText, url){
    var obj = JSON.parse(htmlText);
}

function updateBrokerageAcrateItem(campaignId, shopKeeperId, sellerId){
    htmlText = LanJsBridge.getDataFromUrl("https://pub.alimama.com/campaign/merchandiseDetail.json?campaignId="+campaignId+"&shopkeeperId="+shopKeeperId+"&userNumberId="+sellerId+"&tab=2&omid="+sellerId+"&toPage=1&perPagesize=10&_input_charset=utf-8", "updateBrokerageItemCallback");
    updateBrokerageAcrateItemCallback(htmlText, "");
}

function updateGeneralBrokerageItemCallBack(htmlText, url){
    console.log("lanchitour");
    try{
        var obj = JSON.parse(htmlText);
        try{
            var ja = obj.data.campaignList;
            for(var i = 0; i < ja.length; i++){
                var innerText = "";
                var jo = ja[i];
                innerText += "<td>" + jo.campaignName + "</td><td>" + (jo.properties == "3" ? "是" : "否");
                if(jo.campaignId == '0'){
                    innerText += "</td><td style=\"color:#fe2641\">" + tkRate + "%</td><td>";
                }else{
                    innerText += "</td><td id=\"camp_"+jo.campaignId+"\" style=\"color:#fe2641\">" + jo.avgCommissionToString + "</td><td>";
                }
                //innerText += "<a href=\"https://pub.alimama.com/promo/search/index.htm?q=https%3A%2F%2Fitem.taobao.com%2Fitem.htm%3Fid%3D"+ goodid+"&yxjh=-1\"";
                innerText += "<a href=\"https://pub.alimama.com/myunion.htm?#!/promo/self/campaign?campaignId="+jo.campaignId+"&shopkeeperId="+jo.shopKeeperId+"&userNumberId="+userid+"\"";
                innerText += "<button class=\"btn_02\">申请计划</button></a></td>";
                var item = document.createElement("tr");
                item.innerHTML = innerText;
                document.getElementById("plantable").appendChild(item);
            }
        }catch(err){}
    }catch(err){
        document.getElementById("plantitle").innerHTML = "请<a href=\"alimama:login\" style=\"color:red\"><b>登陆</b></a>后查看详情<br>若您已登陆，则访问受限，请稍后再试。";
    }
}

function updateGeneralBrokerageItem(memberId){
    htmlText = LanJsBridge.getDataFromUrl("https://pub.alimama.com/shopdetail/campaigns.json?oriMemberId="+memberId, "updateGeneralBrokerageItemCallBack");
    updateGeneralBrokerageItemCallBack(htmlText, "");
}


function updateGeneralBrokerageItem2CallBack(htmlText, url){
    try{
        var obj = eval('('+htmlText+')');
        try{
            var ja = obj.data;
            for(var i = 0; i < ja.length; i++){
                var innerText = "";
                var jo = ja[i];
                innerText += "<td>" + jo.CampaignName+ "</td><td>" + jo.Properties;
                    innerText += "</td><td id=\"camp_"+jo.CampaignID+"\" style=\"color:#fe2641\">" + jo.commissionRate + "%</td><td>";
                //innerText += "<a href=\"https://pub.alimama.com/promo/search/index.htm?q=https%3A%2F%2Fitem.taobao.com%2Fitem.htm%3Fid%3D"+ goodid+"&yxjh=-1\"";
                innerText += "<a href=\"https://pub.alimama.com/myunion.htm?#!/promo/self/campaign?campaignId="+jo.CampaignID+"&shopkeeperId="+jo.ShopKeeperID+"&userNumberId="+userid+"\"";
                innerText += "<button class=\"btn_02\">申请计划</button></a></td>";
                var item = document.createElement("tr");
                item.innerHTML = innerText;
                document.getElementById("plantable").appendChild(item);
            }
        }catch(err){}
    }catch(err){
        document.getElementById("plantitle").innerHTML = "请<a href=alimama:login style=\"color:red\"><b>登陆</b></a>后查看详情<br>若您已登陆，则访问受限，请稍后再试。";
    }
}

function updateGeneralBrokerageItem2(goodId){
    htmlText = LanJsBridge.getDataFromUrl("https://pub.alimama.com/pubauc/getCommonCampaignByItemId.json?itemId="+goodId, "updateGeneralBrokerageItem2CallBack");
    updateGeneralBrokerageItem2CallBack(htmlText, "");
}

function updateGeneralBrokerageCallBack(htmlText, url){
    try{
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
                try{
                    var innerText = "";
                    innerText += "<td>";
                    innerText += dataList.couponInfo;
                    innerText += "</td><td>";
                    innerText += dataList.couponEffectiveEndTime;
                    innerText += "</td><td>";
                    innerText += "后台推广可见";
                    innerText += "</td>";
                    var item = document.createElement("tr");
                    item.innerHTML = innerText;
                    document.getElementById("coupontable").appendChild(item);
                }catch(err){
                }
                updateGeneralBrokerageItem(userid);
            }
        }
    }catch(e){
            document.getElementById("genbrorate").innerHTML="0%";
            document.getElementById("days30sell").innerHTML="0";
            document.getElementById("givebro").innerHTML = "0";
            document.getElementById("plantitle").innerHTML=e;
    }
}

function updateGeneralBrokerage(){
    htmlText = LanJsBridge.getDataFromUrl("https://pub.alimama.com/items/search.json?q=https://item.taobao.com/item.htm?id="+goodid+"&perPageSize=50", "updateGeneralBrokerageCallBack");
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
    document.getElementById("genlick").href = (showIt ? ((jo.eventRate || jo.eventRate == '0') ? ("https://pub.alimama.com/promo/item/channel/index.htm?q=https%3A%2F%2Fitem.taobao.com%2Fitem.htm%3Fid%3D"+ goodid+"&channel=qqhd&yxjh=-1") : ("https://pub.alimama.com/promo/search/index.htm?q=https%3A%2F%2Fitem.taobao.com%2Fitem.htm%3Fid%3D"+ goodid+"&yxjh=-1")) : "");
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
    htmlText = LanJsBridge.getDataFromUrl("https://pub.alimama.com/items/search.json?q=https://item.taobao.com/item.htm?id="+goodid+"&perPageSize=50", "updateQueqiaoBrokerageCallBack");
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
            var innerText = "";
            innerText += "<td>"+ja[i].condition+"</td><td>";
            innerText += ja[i].timeRange;
            innerText += "</td><td>";
            if(showIt){
                innerText += "<button class=\"btn_02\" onclick=setClipboard(\"http://shop.m.taobao.com/shop/coupon.htm?seller_id=" + userid +"&activity_id=" + activityId + "\")>点击复制</button>";
            }else{
                innerText += "<button class=\"btn_02\">VIP可复制</button>";
            }
            innerText += "</td>";
            var item = document.createElement("tr");
            item.innerHTML = innerText;
            document.getElementById("coupontable").appendChild(item);
            //updateTaobaoCouponItem(userid, activityId);
        }
    }
    else{
        document.getElementById("coupontitle").innerHTML="<a href=logintaobao:show style=\"color:red\"><b>登陆淘宝</b></a>获取更多优惠信息";
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


function updateTKZSCoupon2ItemCallBack(htmlText, url){
    var obj = eval('('+htmlText+')');
    var ja = obj.result;
    var start = ja.startFee;
    var amount = ja.amount;
    var status = ja.retStatus;
    if(status != 0){
        return;
    }
    var innerText = "";
    innerText += "<td>";
    innerText += "满"+start+"减"+amount;
    innerText += "</td><td>";
    innerText += ja.effectiveEndTime;
    innerText += "</td><td>";
    if(showIt){
        innerText += "<button class=\"btn_02\" onclick=setClipboard(\"http://shop.m.taobao.com/shop/coupon.htm?seller_id=" + userid +"&activity_id=" + url.substring(url.indexOf("activityId=")+11, url.length) + "\")>点击复制</button>";
    }else{
        innerText += "<button class=\"btn_02\">VIP可复制</button>";
    }
    innerText += "</td>";
    var item = document.createElement("tr");
    item.innerHTML = innerText;
    document.getElementById("coupontable").appendChild(item);
}

function updateTKZSCoupon2Item(sellerId, activityId){
    htmlText = LanJsBridge.getDataFromUrl("https://uland.taobao.com/cp/coupon?"+"itemId="+goodid+"&activityId="+activityId, "updateTKZSCoupon2ItemCallBack");
    updateTKZSCoupon2ItemCallBack(htmlText, "https://uland.taobao.com/cp/coupon?"+"itemId="+goodid+"&activityId="+activityId);
}


function updateTKZSCouponCallBack(htmlText, url){
    var obj = eval('('+htmlText+')');
    var ja = obj.dataList;
    if(Object.keys(ja).length == 0){
        //document.getElementById("lanTKZtic").innerHTML="<caption>没有优惠券</caption><tr style=\"background: #fe2641; color:#fff;\"><th>优惠券</th><th>使用时间</th><th>手机券</th></tr>";
        return;
    }
    for(var i = 0; i < ja.length; i++){
        var activityId = ja[i].activityId;
        updateTKZSCoupon2Item(userid, activityId);
    }
}

function updateTKZSCoupon(){
    try{
        LanJsBridge.getDataFromUrl("http://zhushou4.taokezhushou.com/api/v1/tip?version=6.0.0.0", "callBackNull");
        htmlText = LanJsBridge.getDataFromUrl("http://zhushou4.taokezhushou.com/api/v1/coupon?sellerId="+userid+"&itemId="+goodid, "updateTKZSCouponCallBack");
        //htmlText = LanJsBridge.getDataFromUrl("http://zhushou4.taokezhushou.com/api/v1/coupons_base/"+userid+"?item_id="+goodid, "updateTKZSCouponCallBack");
        if(htmlText != ''){
            updateTKZSCouponCallBack(htmlText, "");
        }
    }catch(e){}
}

// Dataoke coupon
function callBackgetDataokeInfo(htmlText, url){
    str0 = htmlText.substring(htmlText.indexOf('activity_id=')+12, htmlText.length);
    str0 = str0.substring(0, str0.indexOf('"'));
    htmlText = LanJsBridge.getDataFromUrl("https://uland.taobao.com/cp/coupon?"+"itemId="+goodid+"&activityId="+str0, "updateTKZSCoupon2ItemCallBack");
    updateTKZSCoupon2ItemCallBack(htmlText, "https://uland.taobao.com/cp/coupon?"+"itemId="+goodid+"&activityId="+str0);
}

function getDataokeInfo(dtkid){
    htmlText = LanJsBridge.getDataFromUrl("http://www.dataoke.com/gettpl?gid="+dtkid, "callBackgetDataokeInfo");
    callBackgetDataokeInfo(htmlText, '');
}

function callBackDataoke(htmlText, url){
    if(htmlText.indexOf('goods-items_') > 0 && htmlText.indexOf('data_goodsid') > 0){
        dataokeid = htmlText.substring(htmlText.indexOf('goods-items_')+12, htmlText.indexOf('data_goodsid')-2);
        getDataokeInfo(dataokeid);
    }
}

function updateDataokeCouponTwice(){
    htmlText = LanJsBridge.getDataFromUrlWithRefer("http://www.dataoke.com/search/?keywords="+goodid+"&xuan=spid", "http://www.dataoke.com/search/?keywords="+goodid+"&xuan=spid", "callBackDataoke");
    callBackDataoke(htmlText, '');
}

function updateDataokeCoupon(){
    htmlText = LanJsBridge.getDataFromUrlWithRefer("http://www.dataoke.com/search/?keywords="+goodid+"&xuan=spid", "http://www.dataoke.com/search/?keywords="+goodid+"&xuan=spid", "updateDataokeCouponTwice");
    updateDataokeCouponTwice();
}

// Qingtaoke coupon
function updateQingtaokeCouponCallback(htmlText, url){
    try{
        var obj = eval('('+htmlText+')');
        var ja = obj.data;
        for(var i = 0; i < ja.length; i++){
            var jo = ja[i];
            var innerText = "";
            innerText += "<td>";
            if(jo.applyAmount == '0' && jo.amount == '0'){
                innerText += "优惠券";
            }else{
                innerText += "满"+jo.applyAmount+"可减"+jo.amount;
            }
            innerText += "</td><td>";
            if(showIt){
                innerText += "<a href=webpage:"+"https://market.m.taobao.com/apps/aliyx/coupon/detail.html?wh_weex=true&activityId=" + jo.activityId +"&sellerId=" + jo.sellerId+" style=\"color:red\"><b>请点击查看优惠券详情</b></a>";
            }else{
                innerText += "优惠券详情";
            }
            innerText += "</td><td>";
            if(showIt){
                innerText += "<button class=\"btn_02\" onclick=setClipboard(\"https://market.m.taobao.com/apps/aliyx/coupon/detail.html?wh_weex=true&activityId=" + jo.activityId +"&sellerId=" + jo.sellerId + "\")>点击复制</button>";
            }else{
                innerText += "<button class=\"btn_02\">VIP可复制</button>";
            }
            innerText += "</td>";
            var item = document.createElement("tr");
            item.innerHTML = innerText;
            document.getElementById("coupontable").appendChild(item);
        }
    }catch(e){
    }

}

function updateQingtaokeCoupon(){
    htmlText = LanJsBridge.getDataFromUrl("http://www.qingtaoke.com/api/UserPlan/UserCouponList?sid="+userid+"&gid="+goodid, "updateQingtaokeCouponCallback")
    updateQingtaokeCouponCallback(htmlText, '');
}

function callBackNothing(htmlString, url){
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

    //tbtoken = LanJsBridge.getCookie("_tb_token_", "https://pub.alimama.com/")
    try{
        updateGeneralBrokerage();
    }catch(e){}

    try{
        updateQueqiaoBrokerage();
    }catch(e){}

    try{
        updateTaobaoCoupon();
    }catch(e){}

    try{
        updateTKZSCoupon();
    }catch(e){}

    try{
        updateDataokeCoupon();
    }catch(e){}

    try{
        updateQingtaokeCoupon();
    }catch(e){}
}



