function get(name){
   if(name=(new RegExp('[?&]'+encodeURIComponent(name)+'=([^&]*)')).exec(location.search))
      return decodeURIComponent(name[1]);
}

function callBack(html, url){
	var obj = eval('('+html+')');
	var ja = obj.message;
	var jo = ja[0];
	document.getElementById("intro").innerHTML = jo.title;
	document.getElementById("price").innerHTML = jo.price;
	document.getElementById("sells").innerHTML = jo.sell;
	document.getElementById("introimg").src = jo.image;
	document.getElementById("copytitle").href = "clipboard:"+jo.title;
    document.getElementById("copylink").href = "clipboard:"+jo.url;
	document.getElementById("taodetail").href = "ios:showTaobaoDetail:"+jo.url;
}
function doWork(q){
	htmlText = LanJsBridge.getDataFromUrl("http://self.vsusvip.com:7080/query"+q, "callBack");
	callBack(htmlText, "");
}
