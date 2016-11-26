var srcurl = window.location.href;
var goodid, userid, shopid, tbtoken, pub_cid;
function setClipboard(text){
    Android.setClipboard(text);
}
function lan(){
    if(isDetailPage(srcurl) && !document.getElementById("LanJPanel")){
        prepareLanJPanel(srcurl);
        goodid = getGoodID(srcurl);
        var innerText = taoBrokerList(srcurl);
        setLanJPanel(innerText);
    }
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
function prepareLanJPanel(url){
    var JP = document.createElement("section");
    var att = document.createAttribute("id");
    att.value = "LanJPanel";
    JP.setAttributeNode(att);
    insertAfter(JP, document.getElementById("s-price"));
}
function setLanJPanel(text){
    document.getElementById("LanJPanel").innerHTML=text;
}
function isDetailPage(url){
    return url.indexOf("h5.m.taobao.com/awp/core/detail.htm") > 0 || url.indexOf("detail.m.tmall.com/item.htm") > 0;
}
function insertAfter(newNode, referenceNode) {
    referenceNode.parentNode.insertBefore(newNode, referenceNode.nextSibling);
}
function taoBrokerList(url){
    var ret = "";
    ret += Android.readBrokerage(goodid);
    ret += Android.readQQ(goodid);
    ret += Android.readSellerTicketList(Android.getSellerId(goodid), goodid);
    ret += Android.readTKZSTicketList(Android.getSellerId(goodid));
    return ret;
}