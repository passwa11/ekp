// --------------------------  通用方法  ---------------------------
var isNeedClose;//定义一个是否关闭客户端的变量  用来区分点击窗口关闭时保存和其它保存
//扩展js string endwith,startwith方法
String.prototype.endWith = function (str) {
    if (str == null || str == "" || this.length == 0 || str.length > this.length)
        return false;
    if (this.substring(this.length - str.length) == str)
        return true;
    else
        return false;
}

String.prototype.startWith = function (str) {
    if (str == null || str == "" || this.length == 0 || str.length > this.length)
        return false;
    if (this.substr(0, str.length) == str)
        return true;
    else
        return false;
}

//UTF-16转UTF-8
function utf16ToUtf8(s) {
    if (!s) {
        return;
    }
    var i, code, ret = [],
        len = s.length;
    for (i = 0; i < len; i++) {
        code = s.charCodeAt(i);
        if (code > 0x0 && code <= 0x7f) {
            //单字节
            //UTF-16 0000 - 007F
            //UTF-8  0xxxxxxx
            ret.push(s.charAt(i));
        } else if (code >= 0x80 && code <= 0x7ff) {
            //双字节
            //UTF-16 0080 - 07FF
            //UTF-8  110xxxxx 10xxxxxx
            ret.push(
                //110xxxxx
                String.fromCharCode(0xc0 | ((code >> 6) & 0x1f)),
                //10xxxxxx
                String.fromCharCode(0x80 | (code & 0x3f))
            );
        } else if (code >= 0x800 && code <= 0xffff) {
            //三字节
            //UTF-16 0800 - FFFF
            //UTF-8  1110xxxx 10xxxxxx 10xxxxxx
            ret.push(
                //1110xxxx
                String.fromCharCode(0xe0 | ((code >> 12) & 0xf)),
                //10xxxxxx
                String.fromCharCode(0x80 | ((code >> 6) & 0x3f)),
                //10xxxxxx
                String.fromCharCode(0x80 | (code & 0x3f))
            );
        }
    }
    return ret.join('');
}

//若要显示:当前日期加时间(如:200906121200)
function currentTime() {
    var now = new Date();

    var year = now.getFullYear(); //年
    var month = now.getMonth() + 1; //月
    var day = now.getDate(); //日

    var hh = now.getHours(); //时
    var mm = now.getMinutes(); //分

    var clock = year + "";

    if (month < 10)
        clock += "0";

    clock += month + "";

    if (day < 10)
        clock += "0";

    clock += day + "";

    if (hh < 10)
        clock += "0";

    clock += hh + "";
    if (mm < 10) clock += '0';
    clock += mm;
    return (clock);
}

/**
 * 判断文件个数是否为0，若为0则关闭
 * @param {*} name 
 */
function closeEtIfNoDocument() {
    var etApp = wps.EtApplication();
    var docs = etApp.Workbooks;
     var time=3000;
     var timer=setInterval(function (){//使用一个定时器 在三秒内持续进行文档数目的监听   在三秒内文档数目更新后就关闭客户端
        time--;
        if (docs && docs.Count == 0) {
            clearInterval(timer);
            let params={
				closeEt:true
			}
			etApp.WindowState=-4140;//最小化
			wps.ApiEvent.Cancel = true;
			etApp.Quit();
        }
        if(time<=0){
            clearInterval(timer);
        }
    },100);
 if (docs && docs.Count == 0) {
    
    	let params={
                closeEt:true
            }
    	etApp.WindowState=-4140;//最小化
    	wps.ApiEvent.Cancel = true;
    	etApp.Quit();
        wps.OAAssist.WebNotify(JSON.stringify(params),true);
    	
    }
}

function activeTab() {
    wps.ribbonUI.ActivateTab('WPSWorkExtTab');
}

function showOATab() {
    wps.PluginStorage.setItem("ShowOATabDocActive", pCheckIfOADoc()); //根据文件是否为OA文件来显示OA菜单
    wps.ribbonUI.Invalidate(); // 刷新Ribbon自定义按钮的状态
}

function pGetParamName(data, attr) {
    var start = data.indexOf(attr);
    data = data.substring(start + attr.length);
    return data;
}

function pGetFileName(request, url) {
    var disposition = request.getResponseHeader("Content-Disposition");
    var filename = "";
    if (disposition) {
        var matchs = pGetParamName(disposition, "filename=");
        if (matchs) {
            filename = decodeURIComponent(matchs);
        } else {
            filename = "petro" + Date.getTime();
        }
    } else {
        var filename = url.substring(url.lastIndexOf("/") + 1);
    }
    return filename;
}

function StringToUint8Array(string) {
    var binLen, buffer, chars, i, _i;
    binLen = string.length;
    buffer = new ArrayBuffer(binLen);
    chars = new Uint8Array(buffer);
    for (var i = 0; i < binLen; ++i) {
        chars[i] = String.prototype.charCodeAt.call(string, i);
    }
    return buffer;
}

function DownloadFile(url, callback) {
    // 需要根据业务实现一套
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var path = wps.Env.GetTempPath() + "/" + pGetFileName(xhr, url);
            var reader = new FileReader();
            reader.onload = function () {
                wps.FileSystem.writeAsBinaryString(path, reader.result);
                callback(path);
            };
            reader.readAsBinaryString(xhr.response);
        }
    }
    xhr.open('GET', url);
    xhr.responseType = 'blob';
    xhr.send();
}

function UploadFile(strFileName, strPath, uploadPath, strFieldName, OnSuccess, OnFail) {
    var xhr = new XMLHttpRequest();
    xhr.open('POST', uploadPath);

    function KFormData() {
        this.fake = true;
        this.boundary = "--------FormData" + Math.random();
        this._fields = [];
    }
    KFormData.prototype.append = function (key, value) {
        this._fields.push([key, value]);
    }
    KFormData.prototype.toString = function () {
        var boundary = this.boundary;
        var body = "";
        this._fields.forEach(function (field) {
            body += "--" + boundary + "\r\n";
            if (field[1].name) {
                var file = field[1];
                body += "Content-Disposition: form-data; name=\"" + field[0] + "\"; filename=\"" + file.name + "\"\r\n";
                body += "Content-Type: " + file.type + "\r\n\r\n";
                body += file.getAsBinary() + "\r\n";
            } else {
                body += "Content-Disposition: form-data; name=\"" + field[0] + "\";\r\n\r\n";
                body += field[1] + "\r\n";
            }
        });
        body += "--" + boundary + "--";
        return body;
    }
    var fileData = wps.FileSystem.readAsBinaryString(strPath);
    var data = new KFormData();
    data.append('file', {
        name: strFileName,
        type: "application/octet-stream",
        getAsBinary: function () {
            return fileData;
        }
    });
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            if (xhr.status == 200)
                OnSuccess(xhr.response)
            else
                OnFail(xhr.response);
        }
    };
    xhr.setRequestHeader("Cache-Control", "no-cache");
    xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
    if (data.fake) {
        xhr.setRequestHeader("Content-Type", "multipart/form-data; boundary=" + data.boundary);
        var arr = StringToUint8Array(data.toString());
        xhr.send(arr);
    } else {
        xhr.send(data);
    }
}

function CloseEtClientByEkp(doc){
	var userId=GetDocParamsValue(doc, "ekpUserId");
	var docId=GetDocParamsValue(doc, "ekpAttMainId");
	var ekpServerPrefix=GetDocParamsValue(doc, "ekpServerPrefix");
	var wpsoaassistToken=GetDocParamsValue(doc, "wpsoaassistToken");
	$.ajax({
        url: ekpServerPrefix+"/sys/attachment/sys_att_main/sysAttMain.do?method=checkWpsOaassitEditMark&useId="+userId+"&fdId="+docId+"&wpsOasisstToken="+wpsoaassistToken,
        async: false,
        method: "post",
        dataType: 'json',
        success: function (response) {
            if (response.status == "close") {
            	var etApp = wps.EtApplication();
            	etApp.WindowState=-4140;//最小化
            	doc.Close(false);
            	
            }
        },
        error: function (response) {
            console.log(response);
        }
    });
}

/**
 * 获取文件路径
 * @param {*} html 文件全称
 */
function getHtmlURL(html) {
    //弹出辅助窗格框
    var GetUrlPath = ()=> {
        var e = document.location.toString();
        return -1 != (e = decodeURI(e)).indexOf("/") && (e = e.substring(0, e.lastIndexOf("/"))), e
    }

    var url = GetUrlPath();

    if (url.length != 0) {
        url = url.concat("/" + html);
    } else {
        url = url.concat("./" + html);
    }
    return url;
}

/**
 * 打开弹窗（打开文档前调用）
 * @returns
 */
function showLoading(){
	    wps.PluginStorage.setItem("commonShowDialog",true)
	    var url = getHtmlURL("loading.html");
	    wps.ShowDialog(url,"loading...",200,100,true);
}

/**
 * 关闭弹窗（打开文档后调用）
 * @returns
 */
function hideLoading(){
	wps.PluginStorage.setItem("commonShowDialog",false)
}


