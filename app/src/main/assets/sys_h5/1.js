var end = '<a><div class="fl mr20"><img src="icon03.png" alt=""></div><div class="fl"><p>'
var end1 = '</p><span>立即查看</span></div></a>';
function showDetail(){
    LanJsBridge.setPara3(this.id);
    window.location.href = 'news.html';
}
function callBack(html, url){
	var target = document.getElementsByClassName('bill')[0];
	var obj = eval('('+html+')');
	var message = obj.message;
	for(var i = 0; i < message.length; i++){
	    var item = document.createElement('li');
	    var att = document.createAttribute('class');
	    att.value = 'bill01';
	    item.id = message[i].id;
	    item.onclick = showDetail;
	    item.setAttributeNode(att);
	    item.innerHTML = end+message[i].title+end1;
	    target.insertBefore(item, target.childNodes[0]);
	}
}
function updateDisplay(userId, password, messageId){
	htmlText = LanJsBridge.getDataFromUrl("http://user.vsusvip.com:30000/list?userid="+userId+"&password="+password, "callBack");
	callBack(htmlText, '');
}
