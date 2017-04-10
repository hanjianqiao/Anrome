var end = '<a><div class="fl mr20"><img src="icon03.png" alt=""></div><div class="fl"><p>'
var end1 = '</p><span>'
var end2 = '</span></div><span style="float:right;color:#00c30e">立即查看</span></a>';
var nowLoad = 0;
var uid;
var passwd;
var target;
function showDetail(){
    LanJsBridge.setPara3(this.id);
    window.location.href = 'news.html';
}
function lastStage0(para){
    sel = document.getElementById('pull2up');
    sel.innerHTML = '点击加载更多';
}
function lastStage1(para){
    sel = document.getElementById('pull2up');
    sel.innerHTML = '已显示全部消息';
    sel.onclick = '';
}
function callBack(html, url){
	var obj = eval('('+html+')');
	var message = obj.message;
	if(message.length > 0){
        for(var i = message.length-1; i >= 0; i--){
            var item = document.createElement('li');
            var att = document.createAttribute('class');
            att.value = 'bill01';
            item.id = message[i].id;
            item.onclick = showDetail;
            item.setAttributeNode(att);
            item.innerHTML = end+message[i].title+end1+message[i].date+end2;
            target.appendChild(item);
        }
	        //LanJsBridge.callInMain("lastStage0", "");
            lastStage0('');
        }else{
            //LanJsBridge.callInMain("lastStage1", "");
            lastStage1('');
        }
}
function updateDisplay(userId, password, messageId){
    target = document.getElementsByClassName('bill')[0];
    uid = userId;
    passwd = password;
    var limit = 20;
	htmlText = LanJsBridge.getDataFromUrl("http://user.vsusvip.com:30000/list?userid="+userId+"&password="+password+"&offset="+nowLoad+"&limit="+limit, "callBack");
	callBack(htmlText, '');
}
function loadMore(){
    var limit = 20;
    nowLoad = nowLoad+limit;
	htmlText = LanJsBridge.getDataFromUrl("http://user.vsusvip.com:30000/list?userid="+uid+"&password="+passwd+"&offset="+nowLoad+"&limit="+limit, "callBack");
    callBack(htmlText, "");
}