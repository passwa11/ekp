/*压缩类型：标准*/
/***********************************************
JS文件说明：
该文件是系统中最基础的JS文件，所有页面必须首先载入该文件。
该文件提供了一些常用的方法，并提供了跨浏览器的JS替代方案。

作者：叶中奇
版本：1.0 2006-4-3
***********************************************/

Com_RegisterFile("common.js");


//默认的静态资源类型
var staticResourceRegexes = [/\.html(?:\?|$)/i,/\.js(?:\?|$)/i,/\.css(?:\?|$)/i,
    /\.jpg(?:\?|$)/i,/\.png(?:\?|$)/i,/\.gif(?:\?|$)/i,/\.jpg(?:\?|$)/i,/\.tmpl(?:\?|$)/i,
    /\.eot(?:\?|$)/i,/\.svg(?:\?|$)/i,/\.ttf(?:\?|$)/i,/\.woff(?:\?|$)/i];

function isStaticResource(url){
    for(var i = 0; i<staticResourceRegexes.length; i++){
        var isYes = staticResourceRegexes[i].test(url);
        if(isYes){
            return true;
        }
    }
    return false;
}
var pollIndex = 0;
function siteSelect(url){
    if(Com_Parameter
        && Com_Parameter.possibleResourceSites
        && Com_Parameter.possibleResourceSites.length>1){
        var length = Com_Parameter.possibleResourceSites.length;
        var masterSite = Com_Parameter.possibleResourceSites[0];
        if( isStaticResource(url) ){
            if(pollIndex++<0){
                pollIndex = 0;//reset
            }
            var candidateSite = Com_Parameter.possibleResourceSites[pollIndex % length];
            var _url;
            if(url.startsWith(masterSite)){
                //如果是访问主站，替换成轮询的站点
                _url = url.replace(masterSite,candidateSite);
            }else if(url.startsWith('/')){
                //如果以/开头，表示网站根路径，追加备选站点前缀即可
                _url = candidateSite+url;
            }
            /*
            if(window.console && console.log){
                console.log('poll choose site from '+url+' to '+_url);
            }
            */
            return _url?_url:url;
        }
    }
    return url;
}
/***********************************************
功能：引入JS文件
参数
	fileList：文件名列表，用|分隔多值
	contextPath：文件路径，默认值为"js/"
	extendName：文件扩展名，默认从文件名中获取
***********************************************/
function Com_IncludeFile(fileList, contextPath, extendName, isOuter){
	Com_Parameter.JsFileMapping = Com_Parameter.JsFileMapping || {};
	var i, j, fileType;
	contextPath = contextPath || "js/";
	isOuter = isOuter || false;
	var topList = ["jquery.js"];
	function isInTopList(f) {
		for (var i = 0; i < topList.length; i ++) {
			if (f.indexOf(topList[i]) > -1) {
				return true;
			}
		}
		return false;
	}
	
	fileList = fileList.split("|");
	var outFiles = [],
		jsFiles = [],
		cssFiles = [];
	for(i=0; i<fileList.length; i++){
		var cleanKey = fileList[i];
		fileList[i] = contextPath + fileList[i];
		if(Com_ArrayGetIndex(Com_Parameter.JsFileList, fileList[i])==-1){
			Com_Parameter.JsFileList[Com_Parameter.JsFileList.length] = fileList[i];
			if(extendName==null){
				j = fileList[i].lastIndexOf(".");
				if(j>-1)
					fileType = fileList[i].substring(j+1);
				else
					fileType = "js";
			}else{
				fileType = extendName;
			}
			//缓存处理
			if(fileList[i].indexOf('s_cache=')<0){
				if(fileList[i].indexOf("?")>=0){
					fileList[i] = fileList[i] + "&s_cache=" + Com_Parameter.Cache;
				}else{
					fileList[i] = fileList[i] + "?s_cache=" + Com_Parameter.Cache;
				}
			}
			switch(fileType){
				case "js":
				case "jsp":
					var fileId = (function(fileName){
						if(fileName.indexOf("/")>-1){
							fileName = fileName.substring(fileName.lastIndexOf("/")+1); 
						}
						if(fileName.indexOf("?")>-1){
							fileName = fileName.substring(0,fileName.lastIndexOf("?")); 
						}
						fileName = fileName.replace(/[\.\=]/g,"_");
						return fileName;
					})(fileList[i]);
					if(isOuter){
						jsFiles.push(fileList[i]);
						outFiles.push("<script id=" + fileId + " src="+siteSelect(fileList[i])+"></script>");
						Com_Parameter.JsFileMapping[fileList[i]]=fileList[i];
					}else{
						if (isInTopList(fileList[i])) {
							jsFiles.unshift(Com_Parameter.ResPath+fileList[i]);
							outFiles.unshift("<script id=" + fileId + " src="+siteSelect(Com_Parameter.ResPath+fileList[i])+"></script>");
							Com_Parameter.JsFileMapping[fileList[i]]=Com_Parameter.ResPath+fileList[i];
						} else {
							jsFiles.push(Com_Parameter.ResPath+fileList[i]);
							outFiles.push("<script id=" + fileId + " src="+siteSelect(Com_Parameter.ResPath+fileList[i])+"></script>");
							Com_Parameter.JsFileMapping[fileList[i]]=Com_Parameter.ResPath+fileList[i];
						}
					}
					break;
				case "css":
					if(isOuter){
						cssFiles.push(fileList[i]);
						outFiles.push("<link rel=stylesheet href="+siteSelect(fileList[i])+">");
					}else{
						cssFiles.push(Com_Parameter.ResPath+fileList[i]);
						outFiles.push("<link rel=stylesheet href="+siteSelect(Com_Parameter.ResPath+fileList[i])+">");
					}
			}
		}
	}
	var dojoConfig = window.dojoConfig || {};
	if(dojoConfig.offline){
		//避免影响范围过大,暂时只在离线页面中使用新的Com_IncludeFile逻辑
		Com_IncludeJSFiles(jsFiles);
		Com_IncludeCSSFiles(cssFiles);
	}else{
		if(outFiles.length>0){
		    var _string = outFiles.join("\r\n");
			document.writeln(_string);
		}
		
	}
}

function Com_IncludeJSFiles(srcArray){
	for(var i = 0;i < srcArray.length;i++){
		_create(srcArray[i]);
	}
	function _create(src){
		var xmlhttp = {};
		if (window.XMLHttpRequest) {// 所有浏览器
			xmlhttp = new XMLHttpRequest();
		} else if (window.ActiveXObject) {// IE5 和 IE6
			xmlhttp = new ActiveXObject(
					"Microsoft.XMLHTTP");
		}
		xmlhttp.open("GET", siteSelect(src), false);
		xmlhttp.onreadystatechange = function(){
			 if (xmlhttp.readyState == 4) { // Complete
			     if (xmlhttp.status == 200 || xmlhttp.status == 0) { // OK response
			    	 var responseText = xmlhttp.responseText;
			    	 if(window.execScript){
			    		 window.execScript(responseText);
			    	 }else if(window.eval){
			    		 window.eval(responseText);
			    	 }
			     }
			 }     
		};
		xmlhttp.send(null);
	}
}

function Com_IncludeCSSFiles(cssArray){
	for(var i = 0;i < cssArray.length;i++){
		_create(cssArray[i]);
	}
	function _create(url){
		var head = document.getElementsByTagName('head')[0],
			css = document.createElement('link');
		css.type = 'text/css';
		css.rel = 'stylesheet';
		css.href = siteSelect(url);
		head.appendChild(css);
	}
}

/***********************************************
功能：新建文档函数
参数：
	modelName：模板域模型
	strUrl:必选，创建的url，其中id参数用参数!{id},显示名参数用!{name}
***********************************************/
function Com_NewFile(modelName,strUrl){
	if(modelName==null || strUrl==null) return;
	var href = location.href;	
	var method = Com_GetUrlParameter(href,"method");
	if(Com_GetUrlParameter(href,"method")=="add"){
		var i=strUrl.indexOf("?");
		if(i>0) strUrl = strUrl.substring(i);
		var paraList = strUrl.split("&");
		var hasValue = false;
		for(i=0;i<paraList.length;i++){
			var j=paraList[i].indexOf("=");
			if(j<0) continue;
			if(paraList[i].indexOf("=!{")>0){
				var paramName = paraList[i].substring(0,j);
				var paramValue = Com_GetUrlParameter(href,paramName);
				if(paramValue!=null && paramValue!="") {
					hasValue = true;
					break;
				}
			}
		}
		if(!hasValue) {
			var strUrl = Dialog_Template(modelName,strUrl,false,true);
			if(strUrl==null) {Com_Parameter.CloseInfo=null;Com_CloseWindow();}
			else window.open(strUrl,"_self");
				
		}		
	}else if(method.indexOf("list")>0){
		Dialog_Template(modelName,strUrl);
	}
}

/***********************************************
功能：新建文档函数，选择简单分类
参数：
	modelName：分类域模型
	strUrl:必选，创建的url，其中id参数用参数!{id},显示名参数用!{name}
add by wubing date:2009-07-30
***********************************************/
function Com_NewFileFromSimpleCateory(modelName,strUrl){
	if(modelName==null || strUrl==null) return;
	var href = location.href;	
	var method = Com_GetUrlParameter(href,"method");
	if(Com_GetUrlParameter(href,"method")=="add"){
		var i=strUrl.indexOf("?");
		if(i>0) strUrl = strUrl.substring(i);
		var paraList = strUrl.split("&");
		var hasValue = false;
		for(i=0;i<paraList.length;i++){
			var j=paraList[i].indexOf("=");
			if(j<0) continue;
			if(paraList[i].indexOf("=!{")>0){
				var paramName = paraList[i].substring(0,j);
				var paramValue = Com_GetUrlParameter(href,paramName);
				if(paramValue!=null && paramValue!="") {
					hasValue = true;
					break;
				}
			}
		}
		if(!hasValue) {
			var strUrl = Dialog_SimpleCategoryForNewFile(modelName,strUrl,false,true);
			if(strUrl==null) {Com_Parameter.CloseInfo=null;Com_CloseWindow();}
			else window.open(strUrl,"_self");
				
		}		
	}else if(method.indexOf("list")>0){
		Dialog_SimpleCategoryForNewFile(modelName,strUrl);
	}
}

/***********************************************
功能：注册js文件
参数：
	fileName：文件名
***********************************************/
function Com_RegisterFile(fileName){
	fileName = "js/" + fileName;
	if(Com_ArrayGetIndex(Com_Parameter.JsFileList, fileName)==-1)
		Com_Parameter.JsFileList[Com_Parameter.JsFileList.length] = fileName;
}

/***********************************************
 功能：注册js文件，该方法只在合并js中自动调用， 参见java类 com.landray.kmss.sys.ui.util.PcJsOptimizeUtil
 参数：
 fileName：文件名
 与Com_RegisterFile的区别在于不会添加前置的js/，因为在合并JS的地方，业务已经指定了前缀
 ***********************************************/
function New_Com_RegisterFile(fileName, ctx){
	if(fileName){
		fileName = ctx?ctx+fileName:fileName;
		if(Com_ArrayGetIndex(Com_Parameter.JsFileList, fileName)==-1){
			Com_Parameter.JsFileList[Com_Parameter.JsFileList.length] = fileName;
		}
	}
}

/***********************************************
功能：获取数组中指定关键字的位置
参数：
	arr：数组
	key：关键字
返回：关键字所在的位置，找不到则返回-1
***********************************************/
function Com_ArrayGetIndex(arr, key){
	for(var i=0; i<arr.length; i++)
		if(arr[i]==key)
			return i;
	return -1;
}

/***********************************************
功能：关闭窗口
***********************************************/
function Com_CloseWindow() {
	if (Com_Parameter.CloseInfo != null) {
		// 判断是否有修改内容
		if(Com_Parameter.checkFormModify && Com_Parameter.checkFormModify()) {
			____Com_CloseWindow();
			return;
		}
		if (typeof(seajs) != 'undefined') {
			seajs.use('lui/dialog', function(dialog) {
						dialog.confirm(Com_Parameter.CloseInfo,
								function(value) {
									if (value) {
										____Com_CloseWindow();
									} else
										return;
								});
					});
		} else {
			if (!confirm(Com_Parameter.CloseInfo))
				return;
			____Com_CloseWindow();
		}
	} else {
		____Com_CloseWindow();
	}
}

function ____Com_CloseWindow() {
	try{
		// 遍历所有父窗口判断是否存在$dialog
		var parent = window;
		while (parent) {
			if (typeof(parent.$dialog) != 'undefined') {
				parent.$dialog.hide();
				return;
			}
			if (parent == parent.parent)
				break;
			parent = parent.parent;
		}
	}catch(e){
		if(window.console) {
			window.console.warn("父窗口信息采集异常：" + e);
		}
	}
	try {
		var win = window;
		for (var frameWin = win.parent; frameWin != null && frameWin != win; frameWin = win.parent) {
			if (frameWin["Frame_CloseWindow"] != null) {
				frameWin["Frame_CloseWindow"](win);
				return;
			}
			win = frameWin;
		}
	} catch (e) {
		if(window.console) {
			window.console.warn("父窗口关闭接口调用异常：" + e);
		}
	}
	try{
		if( Com_Parameter.top !== window.top  && window.postMessage){
			var messageData = {
				functionName: 'fireEvent',
				args: [{ name: 'close' }]
			};
			parent.postMessage(JSON.stringify(messageData), '*');
		}
	}catch (e){
		if(window.console) {
			window.console.warn("父窗口信息通信异常：" + e);
		}
	}
	try {
		top.opener = top;
		top.open("", "_self");
		top.close();
		if(isInWelink()){
//			HWH5.close().catch(function (error) {
//				  aelrt(error);
//				  console.log('关闭webview异常', error);
//			});
			HWH5.close();
		}
	} catch (e) {
		if(window.console) {
			window.console.error("窗口关闭异常：" + e);
		}
	}
}

function isInWelink(){
	var ua = window.navigator.userAgent;
	ua = ua.toLowerCase();
	if(ua.indexOf('welink')>-1){
		return true;
	}
	return false;
}

function isInDingTalk(){
	var ua = window.navigator.userAgent;
	ua = ua.toLowerCase();
	if(ua.indexOf('dingtalk-win')>-1){
		return true;
	}
	return false;
}

/***********************************************
功能：设置窗口的标题
参数：
	Title：标题文本
***********************************************/
function Com_SetWindowTitle(Title){
	try{
		document.title = Title;
	}catch(err){
	}
}

Com_Parameter.IE = (typeof window.ActiveXObject!="undefined");		//全局变量，判断当前浏览器是否为IE浏览器

/***********************************************
功能：往某个对象中添加一个事件
参数：
	obj：对象，如：window、document等
	eventType：事件名称，不以on开始，比如："load"、"mouseover"
	func：需要执行的函数
***********************************************/
function Com_AddEventListener(obj, eventType, func){
	if(Com_Parameter.IE)
		obj.attachEvent("on"+eventType, func);
	else
		obj.addEventListener(eventType, func, false);
}

/***********************************************
功能：往某个对象中删除一个事件
参数：
	obj：对象，如：window、document等
	eventType：事件名称，不以on开始，比如："load"、"mouseover"
	func：已经添加的函数
***********************************************/
function Com_RemoveEventListener(obj, eventType, func){
	if(Com_Parameter.IE)
		obj.detachEvent("on"+eventType, func);
	else
		obj.removeEventListener(eventType, func, false);
}

/***********************************************
功能：打开一个新窗口
参数：
	url：窗口路径，若Com_Parameter.IsAutoTransferPara设置为True，则自动往URL中添加当前窗口的参数
	target：
		null/""：在下一个帧中打开，若在第一帧调用则打开第三帧页面
		1/2/3/4：在指定的帧打开（见首页界面说明）
		string：目标帧名称
	winStyle：
		当在首页帧结构集中打开，仅当打开第4帧时该参数有效，其值可为：
			"max"：新开窗口最大化
			"mid"：新开窗口跟视图窗口各分一半的空间
			"min"：新开窗口最小化
		若不是在首页真结构集打开时，跟window.open的参数一样
	keepUrl:
		是否保留原有url，默认值为false（不保留）
返回：新窗口对象
***********************************************/
function Com_OpenWindow(url, target, winStyle, keepUrl){
	//这个判断会影响钉钉工作台的数据导出模块iframe加载问题，所以暂时注释-by王雄峰
	/*if(isInDingTalk()){
		//修复 #129594
		target="_self";
	}*/
	if(!keepUrl){
		if(Com_Parameter.IsAutoTransferPara)
			url = Com_CopyParameter(url, new Array("forward", "s_path"));
		if(!(url.indexOf("https://")==0 || url.indexOf("http://")==0)){
			url = Com_SetUrlParameter(url, "s_css", Com_Parameter.Style);
		}
	}
	//tomcat7对于大括号的支持问题
	url = url.replace("{", "%7B").replace("}", "%7D");
	var eventObj = Com_GetEventObject();
	if(eventObj!=null && eventObj.shiftKey==true){
		target = "_blank";
		document.selection.empty();
	}
	if(target=="1" || target=="2" || target=="3" || target=="4")
		target = parseInt(target,10);
	if(target==null || target=="" || target==1 || target==2 || target==3 || target==4){
		var win = Com_RunMainFrameFunc("Frame_OpenWindow", url, target, winStyle);
		if(win==null) {
			target = Com_GetUrlParameter(location.href, "s_target");
			if(target==null)
				target = "_blank";
			win = window.open(url, target);
		}
		return win;
	}else{
		if(winStyle==null || winStyle=="")
			return window.open(url, target);
		else
			return window.open(url, target, winStyle);
	}
}

/***********************************************
功能：拷贝当前URL的参数拷贝到指定的URL中
参数：
	url：目标URL
返回：拷贝后的新的URL
***********************************************/
function Com_CopyParameter(url, except){
	if(location.search=="")
		return url;
	var paraList = location.search.substring(1).split("&");
	var i, j, k, para, value;
	copyParameterOutLoop:
	for(i=0; i<paraList.length; i++){
		j = paraList[i].indexOf("=");
		if(j==-1)
			continue;
		para = paraList[i].substring(0, j);
		if(except!=null){
			if(except[0]!=null){
				for(k=0; k<except.length; k++)
					if(para==except[k])
						continue copyParameterOutLoop;
			}else if(para==except){
				continue;
			}
		}
		value = Com_GetUrlParameter(url, para);
		if(value==null)
			url = Com_SetUrlParameter(url, para, decodeURIComponent(paraList[i].substring(j+1)));
	}
	return url;
}

/***********************************************
功能：提交表单，并保持提交后的URL参数不进行改变
参数：
	formObj：表单对象
	method：提交方法
***********************************************/
Com_Parameter.isSubmit = false;//防止重复提交
Com_Parameter.preOldSubmit = null;
function Com_Submit(formObj, method, clearParameter, moreOptions){
	var top = Com_Parameter.top || window.top;
	moreOptions = moreOptions || {};
	if(Com_Parameter.isSubmit){
		return;
	}
	Com_Parameter.isSubmit = true;
	// 处理提交请求前的逻辑
	if (Com_Submit.ajaxBeforeSubmit) {
		Com_Submit.ajaxBeforeSubmit(formObj);
	}
	if (!bCancel){
		if(formObj.onsubmit!=null && !formObj.onsubmit()){
			if (Com_Submit.ajaxCancelSubmit) {
				Com_Submit.ajaxCancelSubmit(formObj);
			}
			Com_Parameter.isSubmit = false;
			return false;
		}
		//提交表单校验
		for(var i=0; i<Com_Parameter.event["submit"].length; i++){
			if(!Com_Parameter.event["submit"][i](formObj, method, clearParameter, moreOptions)){
				if (Com_Submit.ajaxCancelSubmit) {
					Com_Submit.ajaxCancelSubmit(formObj);
				}
				Com_Parameter.isSubmit = false;
				return false;
			}
		}
		//提交表单消息确认
		var promises = []
		for(var i=0; i<Com_Parameter.event["confirm"].length; i++){
			var promise = Com_Parameter.event["confirm"][i](formObj, method, clearParameter, moreOptions)
			if(typeof promise === 'boolean' && !promise){
				if (Com_Submit.ajaxCancelSubmit) {
					Com_Submit.ajaxCancelSubmit(formObj);
				}
				Com_Parameter.isSubmit = false;
				return false;
			} else if (promise.then) {
				promises.push(promise)
			}
		}
		if (promises.length > 0) {
			var promiseAllCount = 0;
			var def = $.Deferred();
			for (var i = 0; i < promises.length; i++) {
				promises[i].then(function (result) {
					if (!result) {
						def.resolve(false);
					}
					promiseAllCount++;
					if (promiseAllCount === promises.length) {
						def.resolve(true);
					}
				})
			}
			def.then(function (result) {
				if (result) {
					return __Submit();
				}else{
					if (Com_Submit.ajaxCancelSubmit) {
						Com_Submit.ajaxCancelSubmit(formObj);
					}
					Com_Parameter.isSubmit = false;
					return false;
				}
			})
			return
		}
	}
	
	function __Submit(){
		// 预提交处理
		var url = Com_Parameter.ContextPath + 'sys/ui/resources/user.jsp';
		try {
			Com_Parameter.preOldSubmit = Com_GetEventObject();//校验会话的异步操作导致事件被改变，保存提交之前的事件，其他业务可能需要用到
		} catch (e) {
			// TODO: handle exception
		}

		//校验会话
		if (typeof (seajs) != 'undefined' && typeof (top['seajs']) != 'undefined') {
			try {
				//Com_EventPreventDefault();
				//var validate_result = false;
				// 获取RTF域内容，处理提交时是否有包含非法值
				if (typeof (CKEDITOR) != 'undefined') {
					for (instance in CKEDITOR.instances) {
						CKEDITOR.instances[instance].updateElement();
					}
				}
				seajs.use('lui/dialog', function (dialog) {
					$.ajax({
						url: url,
						type: 'POST',
						dataType: 'text',
						data: $(formObj).serializeArray(),
						async: true,
						error: function (data) {
							//dialog.alert('会话校验失败，'+data);
							alert(Com_Parameter.ServiceNotAvailTip + data.statusText);
							// 如果校验失败，执行回调方法
							for (var i = 0; i < Com_Parameter.event["submit_failure_callback"].length; i++) {
								Com_Parameter.event["submit_failure_callback"][i]();
							}
							Com_Parameter.isSubmit = false;
						},
						success: function (data) {
							if (data) {
								data = data.replace(/[\r\n]/g, "");
							}
							if (data.indexOf("invalid_value") == 0) { // 校验非法值
								if (data.substr("invalid_value".length).indexOf("<script>") != -1 || data.substr("invalid_value".length).indexOf("<link>") != -1 || data.substr("invalid_value".length).indexOf("<iframe>") != -1
									|| data.substr("invalid_value".length).indexOf("<input>") != -1 || data.substr("invalid_value".length).indexOf("<select>") != -1 || data.substr("invalid_value".length).indexOf("<option>") != -1
									|| data.substr("invalid_value".length).indexOf("<form>") != -1)
									alert(Data_GetResourceString("sys-config:sysConfig.rtf.illegal"));
								else
									alert(data.substr("invalid_value".length));
								// 如果校验失败，执行回调方法
								for (var i = 0; i < Com_Parameter.event["submit_failure_callback"].length; i++) {
									Com_Parameter.event["submit_failure_callback"][i]();
								}
								Com_Parameter.isSubmit = false;
								return false;
							} else if(data.indexOf("locker_version_error") > -1) {
								lockerErrorTip(data,method);
								// 如果校验失败，执行回调方法
								for (var i = 0; i < Com_Parameter.event["submit_failure_callback"].length; i++) {
									Com_Parameter.event["submit_failure_callback"][i]();
								}
								return false;
							} else if (data != "" && data != "anonymous") {
								return Com_SubmitForm(formObj, method, clearParameter);
							} else {
								dialog.confirm(Com_Parameter.SessionExpireTip, function (value) {
									if (value == true) {
										window.open(Com_Parameter.ContextPath);
									}
								});
								Com_Parameter.isSubmit = false;
								// 如果校验失败，执行回调方法
								for (var i = 0; i < Com_Parameter.event["submit_failure_callback"].length; i++) {
									Com_Parameter.event["submit_failure_callback"][i]();
								}
								return false;
							}
						}
					});

				});
			} catch (e) {
				if (window.console) {
					window.console.log(e.message);
				}
				return Com_SubmitForm(formObj, method, clearParameter);
			}

		} else {
			// admin.do提交存在问题，先去除校验
			//var validate_result = sendToUrl(url,formObj, method, clearParameter);
			//if(!validate_result){//
			//	return false;
			//}
			return Com_SubmitForm(formObj, method, clearParameter);
		}
	}

	return __Submit()
}

//版本锁不相同提示
function lockerErrorTip(data,method){
	seajs.use(['lui/dialog', 'lang!'], function (dialog, lang) {
		var data_arr = data.split("##");
		var errorTips = data_arr[1]; //错误提示
		var buttons = [
			{
				name : lang["button.continue.submit"],
				value : true,
				focus : true,
				fn : function(value, dialog) {
					var fdVersionObj = $('input[name="componentLockerVersionForm.fdVersion"]');
					var value = fdVersionObj.val();
					if(value==''){
						value = "1";
					}else{
						var valueInt = parseInt(value);
						valueInt = valueInt+1;
						fdVersionObj.val(valueInt);
					}
					Com_Parameter.isSubmit = false;
					if(window.versionOverwrite){
						versionOverwrite();
					}else{
						Com_Submit(document.forms[0],method);
					}
				}
			},
			{
				name : lang["button.cancel.refresh"],
				value : true,
				focus : true,
				fn : function(value, dialog) {
					Com_Parameter.isSubmit = false;
					if(window.versionCancel){
						versionCancel();
					}else{
						var href = window.location.href;
						var method = Com_GetUrlParameter(href, "method");
						href = href.replace('method='+method,'method=edit');
						window.location.href = href;
					}
				}
			}
		];

		var content = {
			"html":errorTips,
			"title": lang["sys.common.already.update"],
			"width": "500px", "height": "200px", "buttons": buttons
		};
		Com_Parameter.isSubmit = false;
		dialog.confirm(content, function (value) {
		}, null);
	});
}


function Com_SubmitForm(formObj, method, clearParameter){
	var i;
	var url = Com_CopyParameter(formObj.action);
	if(clearParameter!=null){
		clearParameter = clearParameter.split(":");
		for(i=0; i<clearParameter.length; i++)
			url = Com_SetUrlParameter(url, clearParameter[i], null);
	}
	if(method!=null)
		url = Com_SetUrlParameter(url, "method", method);
	var seq = parseInt(Com_GetUrlParameter(url, "s_seq"),10);
	seq = isNaN(seq)?1:seq+1;
	url = Com_SetUrlParameter(url, "s_seq", seq);
	formObj.action = url;
	if (Com_Submit.ajaxSubmit) {
		Com_Submit.ajaxSubmit(formObj);
		if (Com_Submit.ajaxAfterSubmit) {
			Com_Submit.ajaxAfterSubmit(formObj);
		}
	} else {
		Com_DisableFormOpts();
		formObj.submit();
//		if(!Com_Parameter.isSubmit){
//			Com_Parameter.isSubmit = true;
//			formObj.submit();
//		}
	}
	return true;
}

function Com_DisableFormOpts(){
	var btns = document.getElementsByTagName("INPUT");
	for(i=0; i<btns.length; i++)
		if(btns[i].type=="button" || btns[i].type=="image")
			btns[i].disabled = true;
	btns = document.getElementsByTagName("A");
	for(i=0; i<btns.length; i++){
		btns[i].disabled = true;
		btns[i].removeAttribute("href");
		btns[i].onclick = null;
		for (var j=0; j<btns[i].childNodes.length; j++){
			if(btns[i].childNodes[j].nodeType == 1) {
				btns[i].childNodes[j].disabled = true;
			}
		}
	}
	if(window.LUI){
		window.LUI.fire({'type':'topic','name':'btn_disabled','data':true} , window );
	}
	
}

/***********************************************
功能：提交表单，并保持提交后的URL参数不进行改变,所有按钮不变灰
参数：
	formObj：表单对象
	method：提交方法
***********************************************/
function Com_SubmitNoEnabled(formObj, method){
	if (!bCancel){
		if(formObj.onsubmit!=null && !formObj.onsubmit()){
			return false;
		}
		//提交表单校验
		for(var i=0; i<Com_Parameter.event["submit"].length; i++){
			if(!Com_Parameter.event["submit"][i]()){
				return false;
			}
		}
		//提交表单消息确认
		for(var i=0; i<Com_Parameter.event["confirm"].length; i++){
			if(!Com_Parameter.event["confirm"][i]()){
				return false;
			}
		}
	}
	var url = Com_CopyParameter(formObj.action);
	if(method!=null)
		url = Com_SetUrlParameter(url, "method", method);
	var seq = parseInt(Com_GetUrlParameter(url, "s_seq"),10);
	seq = isNaN(seq)?1:seq+1;
	url = Com_SetUrlParameter(url, "s_seq", seq);
	formObj.action = url;
	formObj.submit();
	return true;
}

/***********************************************
功能：获取URL中的参数（调用该函数不需要考虑编码的问题）
参数：
	url：URL
	param：参数名
返回：参数值
***********************************************/
function Com_GetUrlParameter(url, param){
	var re = new RegExp();
	re.compile("[\\?&]"+param+"=([^(&|#)]*)", "i");
	var arr = re.exec(url);
	if(arr==null)
		return null;
	else
		return decodeURIComponent(arr[1]);
}

/***********************************************
功能：设置URL参数，若参数不存在则添加一个，否则覆盖原有参数
参数：
	url：URL
	param：参数名
	value：参数值
返回：URL
***********************************************/
function Com_SetUrlParameter(url, param, value){
	var hash = null;
	if(url.indexOf('#') > -1){
		hash = url.substring(url.indexOf('#'));
		url = url.substring(0,url.indexOf('#'));
	}
	var re = new RegExp();
	re.compile("([\\?&]"+param+"=)[^&]*", "i");
	if(value==null){
		if(re.test(url)){
			url = url.replace(re, "");
		}
	}else{
		value = encodeURIComponent(value);
		if(re.test(url)){
			url = url.replace(re, "$1"+value);
		}else{
			url += (url.indexOf("?")==-1?"?":"&") + param + "=" + value;
		}
	}
	if(url.charAt(url.length-1)=="?")
		url = url.substring(0, url.length-1);
	if(hash){
		url += hash;
	}
	return url;
}
/********************************************
 功能：获取当前DNS
 ********************************************/
function Com_GetCurDnsHost(){
	var host = location.protocol.toLowerCase()+"//" + location.hostname;
	if(location.port!='' && location.port!='80'){
		host = host+ ":" + location.port;
	}
	return host;
}
/***********************************************
功能：替换HTML代码中的敏感字符
***********************************************/
function Com_HtmlEscape(s){
	if(s==null || s=="")
		return "";
	var re = /&/g;
	s = s.replace(re, "&amp;");
	re = /\"/g;
	s = s.replace(re, "&quot;");
	re = /'/g;
	s = s.replace(re, '&#39;');
	re = /</g;
	s = s.replace(re, "&lt;");
	re = />/g;
	return s.replace(re, "&gt;");
}

function Com_Trim(s){
	return s.replace(/(^\s*)|(\s*$)/g, "");
}

function Com_GetCurrentStyle(obj,property){
	if(Com_Parameter.IE){
		return obj.currentStyle[property];
	}else{
		return window.getComputedStyle(obj,null).getPropertyValue(property);
	}
}

/***********************************************
功能：获取Event对象，必须在事件触发中调用
返回：Event对象
***********************************************/
function Com_GetEventObject(){
	if(Com_Parameter.IE) 
 		return window.event;
  	var func=Com_GetEventObject.caller;
    var funcArray = [];
    var isFuncInArray = function(funcArray, item) {
  	  for(var i = 0; i<funcArray.length; i++ ) {
  	    if(item === funcArray[i]) {
  	      return true;
  	    }
  	  }
  	  return false;
  	};
  	var equalCount = 0;
    while(func!=null){
    	
    	// 避免递归造成死循环，重复调用超过10次强制退出
        if(isFuncInArray(funcArray, func)) {
        	if(equalCount > 10){
        		return null;
        	}
        	equalCount++;
        }
		
		if(func.arguments&&func.arguments.length>0){
			var arg0=func.arguments[0];
			if(arg0){
				if(	(arg0.constructor == Event || arg0.constructor == MouseEvent || arg0.constructor == KeyboardEvent) 
						|| (typeof(arg0)=="object" && arg0.preventDefault && arg0.stopPropagation)
						){
						return arg0;
					}
			}
		}
		
		func=func.caller;
		funcArray.push(func);
	}
	return null;
}

/***********************************************
功能：设置对象的OuterHTML
参数：
	obj：对象
	htmlCode：html代码
返回：html代码
***********************************************/
function Com_SetOuterHTML(obj, htmlCode){
	if(Com_Parameter.IE){
		obj.outerHTML = htmlCode;
	}else{
		if(htmlCode==""){
			try{
				obj.parentNode.removeChild(obj);
			}catch(e){}
		}else{
			var r = obj.ownerDocument.createRange();
			r.setStartBefore(obj);
			var df = r.createContextualFragment(htmlCode);
			obj.parentNode.replaceChild(df, obj);
		}
	}
	return htmlCode;
}

/***********************************************
功能：设置对象的innerText
参数：
	obj：对象
	text：显示文字
***********************************************/
function Com_SetInnerText(obj, text){
	if("textContent" in obj)
		obj.textContent = text;
	else
		obj.innerText = text;
}

/***********************************************
功能：将某个对象中的属性值替换字符串中的“!{属性名}”变量
注意：若对象中没有该属性，此方法会自动认为该属性为""
参数：
	str：原字符串
	obj：查找属性值的对象
返回：替换后的字符串
***********************************************/
function Com_ReplaceParameter(str, obj){
	var re = new RegExp("!\\{([^\(\)]+?)\\}");
	for(var arr=re.exec(str); arr!=null; arr=re.exec(str)){
		var value = eval("obj."+arr[1]);
		str = RegExp.leftContext+(value==null?"":encodeURIComponent(value))+RegExp.rightContext;
	}
	return str;
}

/***********************************************
功能：禁止当前事件的默认行为
***********************************************/
function Com_EventPreventDefault(){
	var eventObj = Com_GetEventObject();
	if(eventObj!=null){
		if(Com_Parameter.IE)
			eventObj.returnValue = false;
		else
			eventObj.preventDefault();
	}
}

/***********************************************
功能：禁止当前事件的冒泡行为
***********************************************/
function Com_EventStopPropagation(){
	var eventObj = Com_GetEventObject();
	if(eventObj!=null){
		if(Com_Parameter.IE)
			eventObj.cancelBubble = true;
		else
			eventObj.stopPropagation(); 
	}
}

//=============================以下函数为内部函数，普通模块请勿调用==============================

/***********************************************
功能：运行主帧的一个函数
参数：
	funcName：函数名（字符串）
	arg1、arg2、arg3、arg4：参数
返回：函数的返回值
***********************************************/
function Com_RunMainFrameFunc(funcName, arg1, arg2, arg3, arg4){
	try{
		var win = window;
		for(var frameWin = win.parent; frameWin!=null && frameWin!=win; frameWin=win.parent){
			if(frameWin[funcName]!=null)
				return frameWin[funcName](win, arg1, arg2, arg3, arg4);
			win = frameWin;
		}
	}catch(e){}
	return null;
}

var bCancel = false; 
Com_Parameter.event = new Array;
Com_Parameter.event["submit"] = new Array;
Com_Parameter.event["confirm"] = new Array;
Com_Parameter.event["privileged_submit"] = new Array;

// 预提交校验失败的回调
Com_Parameter.event["submit_failure_callback"] = new Array;

Com_Parameter.Loaded = false;
Com_AddEventListener(window, "load", function(){
	Com_Parameter.Loaded = true;
});

function Com_FireLKSEvent(eventName, parameter){
	eventName = "LKS_"+eventName;
	if(Com_Parameter.event[eventName]!=null){
		for(var i=0; i<Com_Parameter.event[eventName].length; i++)
			Com_Parameter.event[eventName][i](parameter);
	}
}

function Com_AttachLKSEvent(eventName, func){
	eventName = "LKS_"+eventName;
	if(Com_Parameter.event[eventName]==null)
		Com_Parameter.event[eventName] = new Array;
	Com_Parameter.event[eventName][Com_Parameter.event[eventName].length] = func;
}

Com_AddEventListener(document, "keydown", function(){
	var eventObj = Com_GetEventObject();
	if(eventObj.keyCode==8){
		var eleObj = eventObj.srcElement || eventObj.target;
		var tagName = eleObj.tagName;
		switch(tagName){
		case "INPUT":
		case "TEXTAREA":
			if (eleObj.readOnly) //防止历史返回
				return false;
			break;
		default:
			return true;
		}
	}
	return true;
});
if(Com_Parameter.IE && !window.__Com_WinOpenFunc){
	var __Com_CookieInfo = function(path){
		if(path=='about:blank'){
			return '';
		}
		var index = path.indexOf("?");
		if(index>-1){
			path = path.substring(0, index);
		}
		index = path.indexOf("://");
		if(index>-1){
			path = path.substring(index+3);
			index = path.indexOf("/");
			if(index>-1){
				path = path.substring(index);
			}else{
				return '';
			}
		}
		var domain = location.hostname;
		index = domain.indexOf('.');
		if(index>-1){
			domain = '; domain=' + domain.substring(index+1);
		}else{
			domain = '';
		}
		return domain + ';path='+path;
	};
	document.cookie = 'docReferer=; expires=Fri, 31 Dec 1999 23:59:59 GMT;' + __Com_CookieInfo(location.href);
	var __Com_WinOpenFunc = window.open;
	window.open = function(){
		var cookieInfo = __Com_CookieInfo(arguments[0]);
		if(cookieInfo!=''){
			document.cookie = 'docReferer=' + encodeURIComponent(location.href)
				+ ';expires=' + (new Date(new Date().getTime()+5000).toGMTString())
				+ cookieInfo;
		}
		if(__Com_WinOpenFunc.apply){
			return __Com_WinOpenFunc.apply(window, arguments);
		}else{
			switch(arguments.length){
			case 0:
				return null;
			case 1:
				return __Com_WinOpenFunc(arguments[0]);
			case 2:
				return __Com_WinOpenFunc(arguments[0],arguments[1]);
			default:
				return __Com_WinOpenFunc(arguments[0],arguments[1],arguments[2]);
			}
		}
	};
}

function sendToUrl(url,formObj, method, clearParameter){
	var http_request = null;
	var sessinValidate = false;
	
	if(window.CustomHttpRequest){
		http_request = new CustomHttpRequest();
	}else if (window.XMLHttpRequest) {
		http_request = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		var requestArray = ["Msxml2.XMLHTTP", "Microsoft.XMLHTTP"];
		for (var i = requestArray.length - 1; http_request == null && i >= 0; i--) {
			try	{
				http_request = new ActiveXObject(requestArray[i]);
			} catch (e) {
				//
			}
		}
	}
	if (http_request==null){
		return true;
	}
	
	http_request.onreadystatechange = function(){
		if (http_request.readyState == 4) {
			if (http_request.status == 200) {
				var res = http_request.responseText;
				//alert(res);
				if('true'==res){
					//return toSubmit(formObj, method, clearParameter);
					sessinValidate = true;
				}else if('false'==res){
					alert(Com_Parameter.SessionExpireTip);
					window.open(Com_Parameter.ContextPath);
				}else{
					alert(Com_Parameter.SessionExpireTip);
					window.open(Com_Parameter.ContextPath);
				}
			} else {
				alert(Com_Parameter.ServiceNotAvailTip);
				//return false;
			}
		}else{
			//alert(http_request.readyState);
			//alert('3与服务器连接不通，请联系管理员或等会再试');
			//return false;
		}
	}
	try{
		http_request.open("GET", url, false);
		http_request.send();
		return sessinValidate;
	}catch(e){
		alert(Com_Parameter.ServiceNotAvailTip+e);
		return false;
	}
	
}

//=============================以上函数为内部函数，普通模块请勿调用==============================

/***********************************************
功能：将日期格式的字符串转为JS的Date对象，日期格式取系统资源文件（根据当前语言获取）
	由于各种语言的差异，日期的格式会不同，为了适应各种不同的格式，所以提供该方法
参数
	date：日期字符串，如：1984-03-17, 1984-03-17 12:54
	type：类型，如：date, datetime （如果为空，则默认date）
	format：日期格式，默认是根据type来取值，如果有传入，则以传入的为准（传入的格式必须是标准格式，如：“年份”必须是4位，“月份/日期/小时/分钟/秒”必须是2位，“毫秒”必须是3位），当传入格式时，必须指定type的类型，否则将以日期转换
注意：
	1. 日期字符串必须包含有：年月日（如：1984-03-17）
	2. 日期时间字符串必须包含有：年月日时分（如：1984-03-17 12:54），另外的秒和毫秒可选
***********************************************/
function Com_GetDate(date, type, format) {
	if (!date)
		return new Date();
	if (!type) {
		// 如果没有type，则分析一下日期数据，有空格属于日期时间，否则为日期
		if (date.indexOf(' ') > -1) 
			type = 'datetime';
		else
			type = 'date';
	}
	// yyyy-MM-dd HH:mm:ss.SSS
	if ('date' == type) {
		format = format || Com_Parameter.Date_format;
	} else {
		format = format || Com_Parameter.DateTime_format;
	}
	
	// 定义：位置，年份，月份，日期
	var index, year, month, day;
	// 取年份的位置，必须是4位
	index = format.indexOf("yyyy");
	year = parseInt(date.substr(index, 4), 10);
	// 取月份，必须2位
	index = format.indexOf("MM");
	month = parseInt(date.substr(index, 2), 10);
	// 取日期，必须2位
	index = format.indexOf("dd");
	day = parseInt(date.substr(index, 2), 10);
	
	if ('datetime' == type) {
		// 定义：小时，分钟，秒，毫秒
		var hour, minute, second, millisecond;
		// 取小时，必须2位
		index = format.indexOf("HH");
		hour = parseInt(date.substr(index, 2), 10);
		// 取分钟，必须2位
		index = format.indexOf("mm");
		minute = parseInt(date.substr(index, 2), 10);
		// 取秒，必须2位。如无数据则取0
		index = format.indexOf("ss");
		second = (index > -1) ? parseInt(date.substr(index, 2), 10) : 0;
		// 取毫秒，必须3位。如无数据则取0
		index = format.indexOf("SSS");
		millisecond = (index > -1) ? parseInt(date.substr(index, 3), 10) : 0;
		return new Date(year, month - 1, day, hour, minute, second, millisecond);
	}
	
	return new Date(year, month - 1, day);
} 

/***********************************************
功能：列表删除数据
参数
	config：封装好的数据，包含以下属性
		url：删除数据的URL
		type：提交方式，默认POST
		data：删除的数据
		modelName：要删除数据的主文档全名称（如：com.landray.kmss.km.review.model.KmReviewMain），主要用来判断是否有开启软删除
	callback：删除成功的回调
***********************************************/
function Com_Delete(config, callback) { 
	var top = Com_Parameter.top || window.top;
	var comfirmMsg = Com_Parameter.ComfirmDelete;
	// 判断是否开启软删除
	if (Com_Parameter.SoftDeleteEnableModules.length > 0 && config.modelName && config.modelName.length > 0) {
		if(Com_Parameter.SoftDeleteEnableModules.indexOf(config.modelName) > -1) {
			comfirmMsg = Com_Parameter.ComfirmSoftDelete;
		}
	}
	config.comfirmMsg = comfirmMsg;
	// 提交类型
	config.type = config.type || 'POST';
	if (typeof (seajs) != 'undefined' && typeof (top['seajs']) != 'undefined') {
		try {
			// 新UI方式，主要用于弹窗
			seajs.use('lui/dialog', function(dialog) {
				dialog.confirm(config.comfirmMsg, function(value) {
					if (value == true) {
						if(config.type.toUpperCase() == 'POST') {
							__Com_Delete_Ajax(config, callback, dialog);
						} else {
							Com_OpenWindow(config.url, '_self');
						}
					}
				});
			});
		} catch (e) {
			__Com_Delete_Old(config, callback);
		}
	} else {
		__Com_Delete_Old(config, callback);
	}
}

// 传统方式执行删除操作，主要是不使用seajs的弹窗
function __Com_Delete_Old(config, callback) {
	if (confirm(config.comfirmMsg)) {
		if(config.type.toUpperCase() == 'POST') {
			__Com_Delete_Ajax(config, callback);
		} else {
			Com_OpenWindow(config.url, '_self');
		}
	}
}

//判断谷歌浏览器下隐藏控件 value=1代表显示 0代表隐藏
function chromeHideJG_2015(value) {	
	try{
		if (navigator.userAgent.indexOf("Chrome") >= 0) {
			if (null != jgBigVersionParam && jgBigVersionParam == "2015") {
				$("object[id*='JGWebOffice_']").each(function(i,_obj){
					_obj.setAttribute("value", value);
					_obj.HidePlugin(value);
				});	
			}
		}
	}catch(e){}
}

//判断谷歌浏览器下根据控件ID隐藏控件 value=1代表显示 0代表隐藏 objId控件名称
function chromeHideJGByObjId_2015(value, objId) {
	try{
		if (navigator.userAgent.indexOf("Chrome") >= 0) {
			if (null != jgBigVersionParam && jgBigVersionParam == "2015") {
				$("object[id*='JGWebOffice_']").each(function(i,_obj){
					if (value == 1) {
						var objIdParam = _obj.id;
						if (objIdParam == objId) {
							_obj.HidePlugin(1);
						} else {
							_obj.HidePlugin(0);
						}
					} else {
						_obj.HidePlugin(0);
					}
				});	
			}
		}
	}catch(e){}
}

//判断谷歌浏览器下隐藏控件 value=1代表显示 0代表隐藏
function chromeHideJG_2015ByKey(key,value) {	
	try{
		if (navigator.userAgent.indexOf("Chrome") >= 0) {
			if (null != jgBigVersionParam && jgBigVersionParam == "2015") {
				document.getElementById("JGWebOffice_"+key).HidePlugin(value);
			}
		}
	}catch(e){}
}

function __Com_Delete_Ajax(config, callback, dialog) {
	config.type = config.type || 'POST';
	if(dialog)
		window.del_load = dialog.loading();
	$.ajax({
		url : config.url,
		type : config.type,
		dataType : 'json',
		data : config.data,
		error : function(data) {
			if(typeof (callback) != 'undefined' && typeof (callback) == 'function')
				callback.call(window, data);
			else {
				if(dialog)
					dialog.failure(Com_Parameter.OptFailureInfo);
				else
					alert(Com_Parameter.OptFailureInfo);
			}
		},
		success : function(data) {
			if(typeof (callback) != 'undefined' && typeof (callback) == 'function')
				callback.call(window, data);
			else {
				if(dialog)
					dialog.result(data);
				else
					alert(Com_Parameter.OptSuccessInfo);
			}
		},
		complete : function() {
			if (window.del_load != null) {
				window.del_load.hide(); 
			}
		}
	});
}

// 为适合View页面的删除操作
function Com_Delete_Get(url, modelName) {
	var config = {
			type : 'GET',
			url : url,
			modelName : modelName || ''
		};
	Com_Delete(config);
}

function Com_PostMessage(winObj,message,origin){
	winObj && winObj.postMessage(message,origin);
} 

// 标示当前页面是否EKP页面
Com_Parameter.EKP = true
// 获取top，为了解决EKP被第三方应用以IFRAME形式嵌入后无法正常运行问题，增加此参数
// 开发需要使用top时应该根据Com_Parameter.top获取而非window.top
// 注: 将整个EKP嵌入到另一个应用，此需求的风险完全不可控，但是需求方一定要做，特此说明。
Com_Parameter.top = (function(){
	var _top = window
	var curWindow = window
	try{
		while(curWindow.parent && curWindow.parent !== curWindow){
			curWindow = curWindow.parent
			if(curWindow && curWindow.Com_Parameter && curWindow.Com_Parameter.EKP){
				_top = curWindow
			}
		}
	}catch(e){
	}
	return _top
})()

// EKP被别的应用嵌套，可能是MKPAAS，加载mkpaas.js
if(Com_Parameter.top !== window.top){
	Com_IncludeFile("mkpaas.js");
}

// 判断IE版本
function Com_IEVersion() {
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
    var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1; //判断是否IE<11浏览器
    var isEdge = userAgent.indexOf("Edge") > -1 && !isIE; //判断是否IE的Edge浏览器
    var isIE11 = userAgent.indexOf('Trident') > -1 && userAgent.indexOf("rv:11.0") > -1;
    if (isIE) {
        var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
        reIE.test(userAgent);
        var fIEVersion = parseFloat(RegExp["$1"]);
        if (fIEVersion == 7) {
            return 'ie7';
        } else if (fIEVersion == 8) {
            return 'ie8';
        } else if (fIEVersion == 9) {
            return 'ie9';
        } else if (fIEVersion == 10) {
            return 'ie10';
        } else {
            return 'ie6'; //IE版本<=7
        }
    } else if (isEdge) {
        return 'edge'; //edge
    } else if (isIE11) {
        return 11; //IE11
    } else {
        return -1; //不是ie浏览器
    }
}

/***********************************************
功能：新开页面（主要解决新版chrome新开页面提交后无法自动关闭窗口的问题）
	 解决的思路是新开页面使用window.open()方法来打开，这样才能用window.close()来关闭窗口
参数
	elem：允许是URL字符串，或<a>标签元素
	prop：获取<a>标签url的属性，默认是：href
使用样例
	<a data-href="/ekp/xxx" onclick="Com_OpenNewWindow(this)">文档名称</a>
	<a href="javascript:void(0);" onclick="Com_OpenNewWindow('/ekp/xxx')">文档名称</a>
***********************************************/
function Com_OpenNewWindow(elem, prop) {
	var url, target;
	if(typeof(elem) === 'string') {
		url = elem;
	} else {
		url = $(elem).data(prop || "href");
		if(!url) {
			url = $(elem).attr(prop);
		}
		target = $(elem).attr("target");
	}
	if(url) {
		Com_OpenWindow(url, target);
	} else {
		// 没有找到URL
		alert("URL not found !");
	}
}

// 附件渲染时，添加到此队列中，页面加载完统一调整附件样式
function Com_AddAttrMain(attr) {
	if (attr && attr.fdKey) {
		var AttMains = Com_Parameter['AttrMains'];
		if (!AttMains) {
			AttMains = {};
		}
		AttMains[attr.fdKey] = attr;
		Com_Parameter['AttrMains'] = AttMains;
	}
}

// 统一渲染附件样式
function Com_ResizeAttrMains() {
	if(Com_Parameter['AttrMains']) {
		for(var key in Com_Parameter['AttrMains']) {
			Com_Parameter['AttrMains'][key].resizeAllUploader2();
		}
	}
}

//一键置灰，写这可尽量兼容所有页面（包括不引入seajs的页面）
(function() {
	// 多语言判断 & IE版本
	var html = document.getElementsByTagName('html');
	var cls = html[0].getAttribute('class');
	var IeVersion = Com_IEVersion();
	var lan_classname = 'lui-mulit-'+Com_Parameter.Lang+'-html ';
	var ie_classname = IeVersion==-1 ? '' : 'lui-'+IeVersion+'-html';
	cls = (cls ? cls + ' ' : '') +  lan_classname + ie_classname;

	if (Com_Parameter.isSysMouring == "true") {
		cls = (cls ? cls + ' ' : '') +  'mourning';
		if((window.ActiveXObject || "ActiveXObject" in window)
				&& document.documentMode && document.documentMode >= 10) {
			Com_AddEventListener(window, "load", function() {
				var scriptNode = document.createElement("script");
		        scriptNode.type = 'text/javascript';
		        scriptNode.src = Com_Parameter.ResPath + 'js/grayscale.js';
		        document.body.appendChild(scriptNode);

		        scriptNode.onload = scriptNode.onreadystatechange = function() {
		            if(!this.readyState || this.readyState=='loaded' || this.readyState=='complete'){
		                grayscale(document.getElementsByTagName('body'));
		            }
		        }
			});
		}
	}
	html[0].setAttribute('class', cls);
	// 页面加载完后，重新调整附件宽度
	var interval, intervalCount = 0;
	Com_AddEventListener(window, "load", function() {
		interval = setInterval(function() {
			Com_ResizeAttrMains();
			if(intervalCount >= 3) {
				clearInterval(interval);
			}
			intervalCount += 1;
		}, 500);
	});
})();

// 增加某些浏览器不支持数组判断的函数
if(!Array.isArray) {
	Array.isArray = function(obj) {
		return Object.prototype.toString.call(obj) == "[object Array]";
	};
}

//兼容ie8 Array indexOf方法
if (!Array.indexOf) {
	Array.prototype.indexOf = function (obj) {
		for(var i = 0;i < this.length;i++){
			if (this[i] == obj) {
				return i;
			}
		}
		return -1;
	}
}
//兼容ie8 array indexOf方法

/* 数据权限管理相关脚本 */
(function () {
	// 初始化IFrame修改事件
	initIframeChange = function (iframe) {
		if (window.MutationObserver || window.webkitMutationObserver) {
			// chrome
			var callback = function (mutations) {
				mutations.forEach(function (mutation) {
					iframeSrcChanged(mutation.oldValue, mutation.target.src, mutation.target);
				});
			};
			if (window.MutationObserver) {
				var observer = new MutationObserver(callback);
			} else {
				var observer = new webkitMutationObserver(callback);
			}
			observer.observe(iframe, {
				attributes: true,
				attributeOldValue: true
			});
		} else if (iframe.addEventListener) {
			// Firefox, Opera and Safari
			iframe.addEventListener("DOMAttrModified", function (event) {
				iframeSrcChanged(event.prevValue, event.newValue, event.target);
			}, false);
		} else if (ifram.attachEvent) {
			// Internet Explorer
			iframe.attachEvent("onpropertychange", function (event) {
				iframeSrcChanged(event.prevValue, event.newValue, event.target);
			});
		}
	}

	// IFrame路径修改
	iframeSrcChanged = function (oldValue, newValue, iframeObj) {
		if (newValue.indexOf('datamngToken') < 0) {
			iframeObj.src = appendDatamngToken(newValue);
		}
	}

	// 拼接数据权限token
	window.appendDatamngToken = function (url) {
		try {
			if (url.indexOf('datamngToken') < 0 && top.datamngToken && top.datamngToken != 'undefined') {
				// 如果没有token，增加相关token
				if (url.indexOf('?') > -1) {
					url += '&';
				} else {
					url += '?';
				}
				url += 'datamngToken=' + top.datamngToken + '&__iframe=1';
			}
		} catch (e) {
			// 当出现异常时，不处理，按原逻辑返回
		}
		return url;
	}

	Com_AddEventListener(window, "load", function () {
		try {
			if (top.datamngToken) {
				var iframList = document.getElementsByTagName('iframe');
				for (var i = 0; i < iframList.length; i++) {
					initIframeChange(iframList[i]);
				}
			}
		} catch (e) {
			// 当出现异常时，不处理，按原逻辑返回
		}
	});
})();
