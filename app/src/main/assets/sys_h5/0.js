function callBack(html, url){
	var obj = eval('('+html+')');
    var ja = obj.message;
    document.getElementById("title").innerHTML=ja.title;
    document.getElementById("date").innerHTML=ja.date;
    document.getElementById("body").innerHTML=ja.body;
}
function updateDisplay(userId, password, messageId){
	htmlText = LanJsBridge.getDataFromUrl("http://user.vsusvip.com:30000/detail?id="+messageId+"&userid="+userId+"&password="+password, "callBack");
	callBack(htmlText, "");
}
