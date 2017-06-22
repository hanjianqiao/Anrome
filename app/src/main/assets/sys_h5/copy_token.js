function copyToken(any){
    var ret = "";
    if(location.href.substring(0, 34) == 'http://pub.alimama.com/myunion.htm'){
        var holder = document.getElementsByClassName('tab-content')[0].children;
        for(var i = 0; i < holder.length; i++){
            var item = holder[i];
            if(item.style.display === 'block'){
                ret += item.getElementsByClassName('getcode-box')[0].getElementsByClassName('textarea')[0].innerHTML;
                break;
            }
        }
        LanJsBridge.setClipboard(ret);
    }else if(location.href.substring(0, 28) == 'http://pub.alimama.com/promo'){
        var tokens = document.getElementsByClassName("code-wrap-s");
        var tokenl = document.getElementsByClassName("code-wrap-l");
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
    }else{
        LanJsBridge.setClipboard(ret);
    }
}