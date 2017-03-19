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