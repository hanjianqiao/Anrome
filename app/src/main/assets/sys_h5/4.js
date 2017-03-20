function callBack(html, url){
	var obj = eval('('+html+')');
    var ja = obj.data;
    document.getElementById("balance").innerHTML=ja.balance;
}
function updateDisplay(userId, password){
	htmlText = LanJsBridge.getDataFromUrls("https://user.vsusvip.com:10000/query?id="+userId+"&pwd="+password, "callBack");
    callBack(htmlText, '');
}