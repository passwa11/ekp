/**
 * 此方法是根据wps_sdk.js做的调用方法封装
 * 可参照此定义
 * @param {*} funcs     这是在WPS加载项内部定义的方法，采用JSON格式（先方法名，再参数）
 */

var bUseHttps = false;

var isCloseWps=true;

function _WpsInvoke(funcs, cb) {
	
	installWpsAddin();
	
    // console.log(111111111111111111111111111111);
    var info = {};
    info.funcs = funcs;
    var func = bUseHttps ? WpsInvoke.InvokeAsHttps : WpsInvoke.InvokeAsHttp
    func(WpsInvoke.ClientType.wps, // 组件类型
        "WpsOAAssist", // 插件名，与wps客户端加载的加载的插件名对应
        "dispatcher", // 插件方法入口，与wps客户端加载的加载的插件代码对应，详细见插件代码
        info, // 传递给插件的数据
        function (result) { // 调用回调，status为0为成功，其他是错误
            if (result.status) {
                if (bUseHttps && result.status == 100) {
                    attWpsAssitHandleEditor(funcs,'unlock');
                    WpsInvoke.AuthHttpesCert('请在稍后打开的网页中，点击"高级" => "继续前往"，完成授权。')
                    return;
                }
            } else {
            	isCloseWps=false;
                console.log("打开:"+result.response)
                
                WpsInvoke.RegWebNotify(WpsInvoke.ClientType.wps, "WpsOAAssist", function (messageText) {
                    attWpsAssitHandleEditor(funcs,'unlock');
					 console.log("关闭wps");
					 console.log(messageText);
					 let json = eval('(' + messageText + ')');
					 if(json.hasOwnProperty("closeWps"))
						 isCloseWps=json.closeWps;
                    if (funcs[0].OnlineEditDoc) {
                        var appModel = funcs[0].OnlineEditDoc.wpsExtAppModel;
                        if(appModel == "kmImissive"){
                            refreshPage();
                        }
                    }
				})
                
                showresult(result.response)
            }
            if (cb) {
                cb(result);
            }
        })
}
/**
 * 该方法封装了发送给WPS客户端的请求，不需要用户去实现
 * 接收消息：WpsInvoke.RegWebNotify（type，name,callback）
 * WPS客户端返回消息： wps.OAAssist.WebNotify（message）
 * @param {*} type 加载项对应的插件类型
 * @param {*} name 加载项对应的名字
 * @param {func} callback 接收到WPS客户端的消息后的回调函数
 
WpsInvoke.RegWebNotify(WpsInvoke.ClientType.wps, "WpsOAAssist", function (messageText) {
	 console.log("关闭wps");
	 console.log(messageText);
	 let json = eval('(' + messageText + ')');
	 if(json.hasOwnProperty("closeWps"))
		 isCloseWps=json.closeWps;
    //var span = window.parent.document.getElementById("webnotifyspan")
    //span.innerHTML = messageText
})*/


/**
 * 处理WPS加载项的方法返回值
 *
 * @param {*} resultData
 */


function showresult(resultData) {
	if(resultData=="")
		return;
    let json = eval('(' + resultData + ')')
    switch (json.message) {
        case "GetDocStatus": {
            let docstatus = json.docstatus
            if (typeof docstatus != "undefined") {
                let str = "文档保存状态：" +
                    docstatus.saved +
                    "\n文档字数：" +
                    docstatus.words +
                    "\n文档页数：" + docstatus.pages
                alert(str)
            }
        }
    }
}
/**
 * 这是页面中针对代码显示的变量定义，开发者无需关心
 */
var _wps = {}

/**
 * 判断当前OS是否是Linux系统
 *
 * @returns
 */
function checkOSisLinux() {
    if (detectOS() == "Linux") {
        return true
    } else {
        alert("此方法仅在WPS Linux特定版本支持")
    }
}

/**
 * 检查操作系统
 *
 * @returns Win10 | Win7 | WinVista | Win2003 | WinXP | Win2000 | Linux | Unix | Mac
 */
function detectOS() {
    var sUserAgent = navigator.userAgent;
    var isWin = (navigator.platform == "Win32") || (navigator.platform == "Windows");
    var isMac = (navigator.platform == "Mac68K") || (navigator.platform == "MacPPC") || (navigator.platform == "Macintosh") || (navigator.platform == "MacIntel");
    if (isMac) return "Mac";
    var isUnix = (navigator.platform == "X11") && !isWin && !isMac;
    if (isUnix) return "Unix";
    var isLinux = (String(navigator.platform).indexOf("Linux") > -1);
    if (isLinux) return "Linux";
    if (isWin) {
        var isWin2K = sUserAgent.indexOf("Windows NT 5.0") > -1 || sUserAgent.indexOf("Windows 2000") > -1;
        if (isWin2K) return "Win2000";
        var isWinXP = sUserAgent.indexOf("Windows NT 5.1") > -1 || sUserAgent.indexOf("Windows XP") > -1;
        if (isWinXP) return "WinXP";
        var isWin2003 = sUserAgent.indexOf("Windows NT 5.2") > -1 || sUserAgent.indexOf("Windows 2003") > -1;
        if (isWin2003) return "Win2003";
        var isWinVista = sUserAgent.indexOf("Windows NT 6.0") > -1 || sUserAgent.indexOf("Windows Vista") > -1;
        if (isWinVista) return "WinVista";
        var isWin7 = sUserAgent.indexOf("Windows NT 6.1") > -1 || sUserAgent.indexOf("Windows 7") > -1;
        if (isWin7) return "Win7";
        var isWin10 = sUserAgent.indexOf("Windows NT 6.1") > -1 || sUserAgent.indexOf("Windows 10") > -1;
        if (isWin10) return "Win10";
    }
    return "other";
}

function CloseWpsClient(docId) {
	if (!isCloseWps) {
		console.log("调用关闭CloseWpsClient:" + docId);
		
		/*if (sessionStorage.getItem("isReload")) {
			console.log("页面被刷新");
		} else {
			console.log("首次被加载");
			sessionStorage.setItem("isReload", true)
			
		}*/
		
		_WpsInvoke([{
			"ExitWPS" : {
				"ekpSysAttMainId" : docId,
				"closeFileNoTip" : true
			}
		}]);

	}

}