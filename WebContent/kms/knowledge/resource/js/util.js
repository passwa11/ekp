// 截取字段
function getByLength(words, length) {
    if(!words) return "";
    words = htmlDecodeByRegExp(words);
    if(length < words.length)
        words = words.substring(0, length-2) + "...";
    return words;
}

function getSubLength(val,length) {
    var str = new String(val);
    var bytesCount = 0;
    for (var i = 0 ,n = str.length; i < n; i++) {
        if (bytesCount>length){
            return str.substring(0,i)+"...";
        }
        var c = str.charCodeAt(i);
        if ((c >= 0 && c <= 128)) {
            bytesCount += 1;
        } else {
            bytesCount += 2;
        }
    }
    return str;
}


function getByLengthWithEscape(words, length) {
    words = getByLength(words, length);
    return Com_HtmlEscape(words.replace(/&/g, escape("&")));
}

//计算日期相减天数
function DateMinus(sDate, eDate) {
    if (sDate == "" || eDate == "") {
        return -1;
    }
    var sdate = new Date(sDate.replace(/-/g, "/"));
    var edate = new Date(eDate.replace(/-/g, "/"));
    var days = edate.getTime() - sdate.getTime();
    // 如果不足一天，返回1天
    if(days == 0){
        return 1;
    }
    var day = parseInt(days / (1000 * 60 * 60 * 24));
    return day;
}

/**
 * 阻止事件冒泡，兼容IE
 */
function stopBubble(e) {
    //如果提供了事件对象，则这是一个非IE浏览器
    if (e && e.stopPropagation)
        //因此它支持W3C的stopPropagation()方法
        e.stopPropagation();
    else
        //否则，我们需要使用IE的方式来取消事件冒泡
        window.event.cancelBubble = true;
}

// 获取现在日期
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate;
    return currentdate;
}

function HTMLDecode(text) {
    var temp = document.createElement("div");
    temp.innerHTML = text;
    var output = temp.innerText || temp.textContent;
    temp = null;
    return output;
}

function htmlDecodeByRegExp(str){
    var s = "";
    if(str.length == 0) return "";
    s = str.replace(/&amp;/g,"&");
    s = s.replace(/&lt;/g,"<");
    s = s.replace(/&gt;/g,">");
    s = s.replace(/&nbsp;/g," ");
    s = s.replace(/&#39;/g,"\'");
    s = s.replace(/&quot;/g,"\"");
    return s;
}