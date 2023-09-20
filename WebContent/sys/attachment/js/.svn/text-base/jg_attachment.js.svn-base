/*******************************************************************************
 * JS文件说明： 该文件提供了金格在线编辑的操作函数
 * 
 * 作者：缪贵荣
 * 版本：1.0 2010-11-25
 ******************************************************************************/

//定义全局变量 控件key
window.jgOCX_Name = "";
window.userOpSysType = null;

Com_RegisterFile("jg_attachment.js");
Com_IncludeFile("common.js");
Com_IncludeFile("data.js");
Com_IncludeFile("upload.css",Com_Parameter.ContextPath	+ "sys/attachment/view/img/","css",true);
Com_IncludeFile("session.jsp",Com_Parameter.ContextPath	+ "sys/attachment/js/session.jsp?_="+new Date().getTime(),"js",true);
Com_IncludeFile("sysAttMain_MessageInfo.jsp?locale="+ Com_Parameter.__sysAttMainlocale__,Com_Parameter.ContextPath + "sys/attachment/sys_att_main/", 'js',true);
Com_IncludeFile("sysAttMain_showWindow.jsp?locale="+ Com_Parameter.__sysAttMainlocale__,Com_Parameter.ContextPath + "sys/attachment/sys_att_main/jg/", 'js',true);
Com_IncludeFile("sysAttMain_jg_version.jsp?locale="+ Com_Parameter.__sysAttMainlocale__,Com_Parameter.ContextPath + "sys/attachment/sys_att_main/jg/", 'js',true);
if (typeof Attachment_ObjectInfo == "undefined") {
	Attachment_ObjectInfo = new Array();
}

/***********************************************
 功能  附件对象的构造函数
 参数：
	 fdId:
	 可选，附件的fdId
	 fdKey：
	 必选，附件标识名称
	 fdModelName：
	 必选，域模型名称
	 fdModelId:
	 可选，当前文档的fdId
	 fdAttType:
	 可选，文件类型，office ，默认为office
 ***********************************************/
function JG_AttachmentObject(fdId, fdKey, fdModelName, fdModelId, fdAttType,
		editMode, divName) {
	this.fdId = fdId;
	this.fdKey = fdKey; // 附件标识
	window.jgOCX_Name = fdKey;
	this.fdModelName = fdModelName;
	this.fdModelId = fdModelId;
	if (fdAttType == null)
		fdAttType = "office";
	this.attType = fdAttType;
	this.currentMode = Com_GetUrlParameter(location.href, "method");
	if (editMode == null)
		editMode = this.currentMode;
	this.editMode = editMode;
	this.protectstatus = true;
	if (editMode == "edit" || editMode == "add") {
		this.canDelete = true;
		this.canRead = true;
		this.canDownload = true;
		this.canEdit = true;
		this.canPrint = true;
		this.canAdd = true;
		this.canCopy = true;
		this.protectstatus = false;
	} else {
		this.canDelete = false;
		this.canRead = false;
		this.canDownload = false;
		this.canEdit = false;
		this.canPrint = false;
		this.canAdd = false;
		this.canCopy = false;
	} 
	this.canSaveDraft=true; 
	this.showOpenDraft = false;
	if (divName == null)
		divName = "JGWebOffice_" + fdKey;
	this.navName = divName;
	this.userName = "";
	this.userId = "";
	this.ocxObj = null;
	this.activeObj = null;
	this.disabled = false;
	this.hasLoad = false;
	this.hasAddSubmitFun = false;
	this.hasShowButton = false;
	this.showRevisions = false;
	this.trackRevisions = true;
	this.forceRevisions = true;  //是否强制不留痕
	this.clearDownloadRevisions = false; //下载是否清除留痕
	this.hiddenRevisions=true;
	this.bindSubmit = true;   //是否绑定提交方法
	this.isReadOnly = false;   //是否只读打开
	this.showToolBar = true;
	
	this.isFirst = false;//是否第一个进入编辑模式的用户
	this.updateTimer = null;
	
	//操作系统类型
	window.userOpSysType = jg_detectOS();
	
	this.buttonDiv = null;
	this.isTemplate = false;
	this.fdTemplateModelId = null;
	this.fdTemplateModelName = null;
	this.fdTemplateKey = "";
	this.bookMarks = "";

	Attachment_ObjectInfo[fdKey] = this;

	this.getOcxObj = function(divName) {
		if (this.ocxObj == null) {
			if (divName == null)
				divName = this.navName;
			if (typeof (divName) == "string") {
				var OSType = jg_detectOS();
				if (null != jgBigVersionParam && jgBigVersionParam == "2015" && OSType.indexOf("Win") > -1) {
					document.getElementById(divName).HookEnabled = false;
					this.ocxObj = document.getElementById(divName).FuncExtModule;
				} else {
					this.ocxObj = document.getElementById(divName);
				}
			} else {
				this.ocxObj = divName;
			}
		}
		
		if (this.activeObj == null) {
			if (divName == null)
				divName = this.navName;
			if (typeof (divName) == "string") {
				this.activeObj = document.getElementById(divName);
			} else {
				this.activeObj = divName;
			}
		}
		
		return (this.ocxObj == null ? false : true);
	};
	//新增打印日志
	this.addPrintLog = function() {
		var url = Com_Parameter.ContextPath+"sys/print/sys_print_log/sysPrintLog.do?method=addAttPrintLog&fdId="+this.fdId;
		var dataFun = new KMSSData();
		dataFun.SendToUrl(url,function(){},false);
	};
	//新增附件下载日志
	this.addDownloadLog = function() {
		var url = Com_Parameter.ContextPath+"sys/attachment/sys_att_download_log/sysAttDownloadLog.do?method=addDownlodLog&downloadType=manual&downloadFlag="+(new Date()).getTime()+"&fdId="+this.fdId;
		var dataFun = new KMSSData();
		dataFun.SendToUrl(url,function(){},false);
	};

	this.isActive = JG_Attachment_IsActive;
	this.load = JG_Attachment_Load;
	this.unLoad = JG_Attachment_UnLoad;
	this.show = JG_Attachment_Show;
	this.setBookmark = JG_Attachment_SetBookmark;
	this.setFileName = JG_Attachment_SetFileName;
	this.isProtect = JG_Attachment_IsProtect;
	this.setProtect = JG_Attachment_SetProtect;
	this.disabledObject = JG_Attachment_Disabled;
	this.setStatusMsg = JG_Attachment_SetStatusMsg;
	this.getUrl = JG_Attachment_GetUrl;
	this.saveAsImage = JG_Attachment_saveAsImage;

	this._showButton = _JG_Attachment_ShowButton;
	this._hideButton = _JG_Attachment_HideButton;
	this._setParamter = _JG_Attachment_SetParamter;
	this._setBookmark = _JG_Attachment_SetBookmark;
	this._submit = _JG_Attachment_Submit;
	this._writeAttachmentInfo = _JG_Attachment_WriteAttachmentInfo;
	this._isWord = _JG_Attachment_IsWord;
	this._isExcel = _JG_Attachment_IsExcel;
	this._isAfter2003 = _JG_Attachment_IsAfter2003;
	this._getWpsVersion = _JG_Attachment_getWpsVersion;
	/* 区域保护 (根据书签名设置可以修改的位置，多个用,分割)*/
	this.protectKName = WebAreaProtect;
	this.protectUnName = WebAreaUnprotect;
	/* 是否格式合同 */
	this.fdIssample = false;
	this._getConfigPluginOfficeType = _JG_Attachment_getConfigPluginOfficeType;
}

/***********************************************
 功能 判断插件是否安装成功
 ***********************************************/
function JG_Attachment_IsActive() {
	try { // 根据控件注册时的ProgID来判断
		var obj = new ActiveXObject("iWebOffice2009.HandWriteCtrl"); // 2009控件
		return true;
	} catch (e) {
		return false;
	}
}

//获取当前操作系统是Windows还是Linux
//合同模块选择金格，附件机制使用wps，此方法名与wps.js方法重名冲突
function jg_detectOS() 
	{
		var sUserAgent = navigator.userAgent;
		
		var isWin = (navigator.platform == "Win32") || (navigator.platform == "Windows");
		var isMac = (navigator.platform == "Mac68K") || (navigator.platform == "MacPPC") || (navigator.platform == "Macintosh") || (navigator.platform == "MacIntel");
		
		if (isMac) 
			return "Mac";
		
		var isUnix = (navigator.platform == "X11") && !isWin && !isMac;
		
		if (isUnix) 
			return "Unix";
		
		var isLinux = (String(navigator.platform).indexOf("Linux") > -1);
		
		if (isLinux) 
			return "Linux";
		
		if (isWin) 
		{
			
			return "isWin";
			
			var isWin2K = sUserAgent.indexOf("Windows NT 5.0") > -1 || sUserAgent.indexOf("Windows 2000") > -1;
			if (isWin2K) 
				return "Win2000";
			
			var isWinXP = sUserAgent.indexOf("Windows NT 5.1") > -1 || sUserAgent.indexOf("Windows XP") > -1;
			if (isWinXP) 
				return "WinXP";
			
			var isWin2003 = sUserAgent.indexOf("Windows NT 5.2") > -1 || sUserAgent.indexOf("Windows 2003") > -1;
			if (isWin2003) 
				return "Win2003";
			
			var isWinVista= sUserAgent.indexOf("Windows NT 6.0") > -1 || sUserAgent.indexOf("Windows Vista") > -1;
			if (isWinVista) 
				return "WinVista";
			
			var isWin7 = sUserAgent.indexOf("Windows NT 6.1") > -1 || sUserAgent.indexOf("Windows 7") > -1;
			if (isWin7) 
				return "Win7";
			
			var isWin10 = sUserAgent.indexOf("Windows NT 10.0") > -1 || sUserAgent.indexOf("Windows 10") > -1;
			if (isWin10) 
				return "isWin10";
		}
		
		return "other";
	}

/***********************************************
 功能 打开文档
 参数：
	 fileName：
	 可选，文件名，默认fdKey
	 fileType:
	 可选，文件类型，默认word文档
 ***********************************************/
function JG_Attachment_Load(fileName, fileType) {
	var self = this;
	//隐藏不支持控件的提示信息
	var obj = document.getElementById("noSupport_" + this.fdKey);
	if(obj){
		obj.style.display="none";
	}
	if (!this.hasLoad && this.getOcxObj()) {
		try {
			var fType="";
			var fName="";
			var defaultFileType = ".doc";
			if (this._getConfigPluginOfficeType() == 'wps') {
				defaultFileType = ".wps";
			}

			if(fileName){
				//88256替换文件名中的英文冒号
				fileName = fileName.replace(/:/g, "-");
				fileName = fileName.replace(/%3A/g, "-");
				if(fileName.lastIndexOf(".")>-1){
					fType = fileName.substring(fileName.lastIndexOf("."));
					fName = fileName;
				}else{
					fType = fileType ? fileType : defaultFileType;
					fName = fileName + fType;
				}
			}else{
				fType = fileType ? fileType : defaultFileType;
				fName = (this.fdKey + fType);
			}
			fType = fType.toLowerCase();
			var OSType = jg_detectOS();
			if(OSType.indexOf("Win") == -1){
				var  strFileType = fType;
				if (strFileType == ".doc" || strFileType == ".docx")
				{
					nType = 1;
				}else if(strFileType == ".xls" || strFileType == ".xlsx")
				{
					nType = 12;
				}
				else if(strFileType == ".wps")
				{
					nType = 1;
				}
				else if(strFileType == ".et")
				{
					nType = 12;
				}
				else if (strFileType == ".ofd")
				{
					nType = 3;            //数科ofd阅读器
					//nType = 4;            //福昕ofd阅读器
				}
				else if(strFileType == ".ppt" || strFileType == ".pptx")
				{
					nType = 13;
				}
				
				if(nType == 1){
					if(!this.ocxObj.setPluginType(1))     //自主可控默认加载WPS插件
						{
							alert("setPluginType fail 1");
							alert(this.ocxObj.GetErrMessage());
							return false;
						}
				}else if(nType == 12){
					if(!this.ocxObj.setPluginType(12))     //自主可控默认加载ET插件
						{
							alert("setPluginType fail 1");
							alert(this.ocxObj.GetErrMessage());
							return false;
						}
					
				}
				else if(nType == 13){
					if(!this.ocxObj.setPluginType(13))     //自主可控默认加载ppt插件
						{
							alert("setPluginType fail 1");
							alert(this.ocxObj.GetErrMessage());
							return false;
						}
					
				}
			}
			
			var urlPrefix = Com_Parameter.ContextPath;
			if(urlPrefix.substring(0,4) != 'http')
				urlPrefix = Com_GetCurDnsHost() + urlPrefix;
			
			
			this.ocxObj.WebUrl = urlPrefix + "sys/attachment/sys_att_main/jg_service.jsp";
			if ((null != jgBigVersionParam && jgBigVersionParam != "2015") || OSType.indexOf("Win") == -1){
				this.ocxObj.WebUrl = urlPrefix + "sys/attachment/sys_att_main/jg_service.jsp?jgBigVersion=2009";
			}
			this.ocxObj.RecordID = this.fdModelId; // RecordID为文档的fdModelId
			this.ocxObj.Compatible = false;			//控件兼容模式
			
			this.ocxObj.FileType = fType;
			this.ocxObj.FileName = fName;
			
			this.ocxObj.UserName = this.userName;
			this.ocxObj.MaxFileSize = 100 * 1024;
			if(OSType.indexOf("Win") > -1){
				this.ocxObj.ProgressXY(100,100);
				//jg_showWindow全局参数，在/sys/attachment/sys_att_main/jg/sysAttMain_showWindow.jsp中引入
				this.ocxObj.ShowWindow = jg_showWindow;
				this.ocxObj.ToolsSpace = 0;
			}
			this.ocxObj.Language = Attachment_MessageInfo["info.JG.lang"];
			// this.ocxObj.Language="CH";
			// this.ocxObj.Language="EN";
			if(this.showRevisions)
				this.ocxObj.EditType = "-1,0,1,1,0,1,1,1";
			else
				this.ocxObj.EditType = "-1,0,0,1,0,1,1,1";
			this.ocxObj.ShowMenu = "0"; // 设置是否显示整个菜单
			if (this.canPrint) {
				this.ocxObj.EnablePrint = "1"; // 设置是否允许打印
			} else {
				this.ocxObj.EnablePrint = "0";
			}
			
			//解决火狐浏览器打开控件标题部分文字被遮挡问题
			if (navigator.userAgent.indexOf("Firefox") >= 0){
				this.ocxObj.VersionFontColor="FF0000";
		    }

			this.ocxObj.MaxFileSize = 1*1024*1024;
			
			this._setParamter();
			
			//this.ocxObj.RMMode = true;
			//#78998 修复 谷歌下控件请求sessionId没带导致显示匿名问题
			//if(typeof(eval(this.ocxObj.INetSetCookie)) == 'function'){
				//非ie浏览器
				if (navigator.userAgent.toUpperCase().indexOf("MSIE") < 0 && navigator.userAgent.toUpperCase().indexOf("TRIDENT") < 0) {
					var session = getJGSession();
					if(session['JSESSIONID']){
						if(OSType.indexOf("Win") > -1 && typeof(eval(this.ocxObj.INetSetCookie)) == 'function'){
							//#114950 修复 admin.do中配置不同的身份认证方式，在谷歌浏览器下金格生成痕迹稿显示匿名问题
							for(var key in session){
								self.ocxObj.INetSetCookie(self.ocxObj.WebUrl,key+"="+session[key]);
							}
						} else {
							if (typeof(eval(self.ocxObj.WebSetCookies)) == 'function') {
								// 如果开启了SSO，仅有sessionId还不行，还需要LRToken等
								var __cookies_array = [];
								for(var i in session) {
									__cookies_array.push(i + "=" + session[i]);
								}
								self.ocxObj.WebSetCookies(__cookies_array.join("; "));
							}
						}
					}
				}
			//}
			
			if (this.editMode == "edit" || this.editMode == "add") {
				this.ocxObj.ShowToolBar = true; //控件工具栏
			} else {
				this.ocxObj.ShowToolBar = false; //office工具栏
			}
			
			this.ocxObj.WebOpen();
			
			this.setStatusMsg(this.ocxObj.Status);
			this.attType = "office";
			this._setBookmark();
			
			if (this.editMode != "edit" && this.editMode != "add") {
				this.setProtect();
			}
			this.hasLoad = true;
			//var jgObj = document.getElementById("JGWebOffice_" + this.fdKey);
			//jgObj.style.display="block";
			this.ocxObj.Active(true);//控件获取焦点
			
			//非新建状态下，控制当前只能一个用户在线编辑
			if (this.editMode != "add" && this.editMode != "print" && this.editMode != "view" ) {//#127018 打印模式不做控制
				this.isFirst = isFirst(fdId,fdModelId,fdModelName,fdKey)
				if(this.isFirst){
					this.ocxObj.EditType = "1";
					
					this.updateTimer = setInterval(function(){
						 updateTime(fdId,fdModelId,fdModelName,fdKey);
					},6000);
				}else{
					var copyValue;
					//国产化系统，金格控件的CopyType类型为boolean，非国产化为string
					if (window.userOpSysType.indexOf("Win") == -1 
					  		&& navigator.userAgent.indexOf("Firefox") >= 0) {
						copyValue = true;
					} else {
						copyValue = "1";
					}

					if(!this.canCopy){
				    	this.ocxObj.CopyType = copyValue;
				    	this.ocxObj.EditType = "0,1";
					}else{
						this.ocxObj.CopyType = copyValue;
						this.ocxObj.EditType = "4,1";
					}
				}
			}
		} catch (e) {
			if(window.console){
				window.console.log("jg_load error: " + e.message);
			}
		}
	}else{
		if(!this.getOcxObj()){
			//显示不支持控件的提示信息
			var	showDiv = this.buttonDiv;
			if (showDiv == null)
				showDiv = "_button_" + this.fdKey + "_JG_Attachment_TD";
			var buttonContainer = document.getElementById(showDiv);
			if(buttonContainer){
				buttonContainer.innerHTML="";
			}
			
			if (navigator.userAgent.indexOf("Firefox") == -1){
				var jgObj = document.getElementById("JGWebOffice_" + this.fdKey);
				jgObj.style.display="none";
				
				var obj = document.getElementById("noSupport_" + this.fdKey);
				obj.style.display="block";
			}
		}
	}
}

/***********************************************
 功能 关闭文件，释放资源
 ***********************************************/
function JG_Attachment_UnLoad() {
	if (this.hasLoad) {
		try {
			this.hasLoad = false;
			/*start 编辑保存附件时，在unload下火狐不兼容webclose方法 By朱国荣 2015-12-30*/
			 //if (navigator.userAgent.indexOf("Firefox") >= 0 || navigator.userAgent.indexOf("Chrome") >= 0){ 
			  //  return;
			 //}
			/*end*/
			/*  WebClose参数为TRUE 清理本地临时文件 */
			if (!this.ocxObj.WebClose(true)) {
				this.setStatusMsg(this.ocxObj.Status);
			}
		} catch (e) {
			if(window.console){
				window.console.log("jg_unLoad error: " + e.description);
			}
		}
	}
}

/***********************************************
 功能  显示附件列表
 ***********************************************/
function JG_Attachment_Show() {
	this.disabledObject(false);
}

 
/***********************************************
 功能  在线编辑时设置书签值
 参数：
	 name：必需 书签名称
	 value：必需，书签的值
 ***********************************************/
function JG_Attachment_SetBookmark(name, value) {
	if (name == null || value == null)
		return false;
	if (name == "")
		return false;
	if (this.attType == "office") {
		if (this.getOcxObj() && this.hasLoad) {
			if (!this.isProtect()) {
				if (value == this.ocxObj.WebGetBookMarks(name)) { // 如果内容一样不再设置书签
					return true;
				}
				if (this._isWord()) {
					return this.ocxObj.WebSetBookMarks(name, value);
				}
				var editType = this.ocxObj.EditType;
				try {
					//wps下设置书签不稳定临时处理方式
					this.ocxObj.EditType = "1,1";
					return this.ocxObj.WebSetBookMarks(name, value);
				} catch (err) {
					//alert("set bookmark error: " + err);
				} finally {
					this.ocxObj.EditType = editType;
				}
			}
		} else {
			setTimeout("Attachment_ObjectInfo[\"" + this.fdKey
					+ "\"].setBookmark(\"" + name.replace("\"", "\\\"")
					+ "\",\"" + value.replace("\"", "\\\"") + "\")", 1000);
			return true;
		}
	}
	return false;
}

/***********************************************
 功能  设置附件文件名
 参数：
 fileName：必需， 文件名
 fileType：可选，文件类型
 注：附件提交前设置，附件是第一个提交的。
 ***********************************************/
function JG_Attachment_SetFileName(fileName, fileType) {
	if (fileName) {
		this.ocxObj.FileName = encodeURIComponent(fileName);
	}
	if (fileType) {
		this.ocxObj.FileType = fileType;
	}
}

/***********************************************
 功能  判断文档是否保护模式
 ***********************************************/
function JG_Attachment_IsProtect() {
	try {
		if (this.ocxObj.FileType.toLowerCase().lastIndexOf(".doc") > -1 || this.ocxObj.FileType.toLowerCase().lastIndexOf(".wps") > -1) {
			var r = this.ocxObj.WebObject.ProtectionType;
			if ( r > -1 && r!=1 && r != 3) {
				return true;
			}
		} else if (this.ocxObj.FileType.toLowerCase().lastIndexOf(".xls") > -1 || this.ocxObj.FileType.toLowerCase().lastIndexOf(".et") > -1) {
			var mpc = this.ocxObj.WebObject.Application.ActiveSheet.ProtectContents;
			var mpdo = this.ocxObj.WebObject.Application.ActiveSheet.ProtectDrawingObjects;
			var mps = this.ocxObj.WebObject.Application.ActiveSheet.ProtectScenarios;
			if (mpc || mpdo || mps) {
				return true;
			}
		}
	} catch (e) {
		return false;
	}
	return false;
}

/***********************************************
 功能  设置附件是否保护
 参数：
 	isProtect：可选， 是否保护
 ***********************************************/
function JG_Attachment_SetProtect(isProtect) {
	 try {
    	if (isProtect == true || isProtect == null) {
			if (this.canCopy) {
				// 修改签章锁定后，下载公文报错的问题
				this.ocxObj.WebSetProtect(false, ""); // 可复制的保护
			} else {
				//不可复制的保护,保护分窗体保护和非窗体保护，excel没有非窗体保护模式，所以这里做类型判断
				//if(this._isWord()){
				//  this.ocxObj.WebObject.Protect(2, false, ""); 
				//}else{
				//  this.ocxObj.WebSetProtect(true, "");
				//}
				this.ocxObj.WebSetProtect(true, ""); 
			}
		} else {
			this.ocxObj.WebSetProtect(false, ""); // ""表示密码为空
		}
		this.ocxObj.DisableKey("CTRL+P,F12"); // 禁止快捷键
	}catch (e) {
		//alert("jg_load error: " + e.description);
	}
}


/***********************************************
 功能  隐藏控件
 参数：
 	disabled：可选， 是否隐藏
 ***********************************************/
function JG_Attachment_Disabled(disabled) {
	if (disabled == null)
		disabled = !this.disabled;
	if (disabled) {
		//隐藏
		this._hideButton();
		if (this.getOcxObj()) {
			//this.activeObj.style.display = "none";
			$(this.navName).hide();
		}
	} else {
		//显示
		if(this.showToolBar){
			if (this.getOcxObj()) {
				this._showButton();
			}
		}
		if (this.getOcxObj()) {
			//this.activeObj.style.display = "";
			$(this.navName).show();
		}
	}
	this.disabled = disabled;
}

/***********************************************
 功能 显示操作状态
 ***********************************************/
function JG_Attachment_SetStatusMsg(mString) {
	window.status = mString;
}

/***********************************************
 功能  生成附件的链接
 参数：
	 method:链接中的method参数值，
	 fdId:附件的ID
	 needHost:是否需要显示绝对路径
 ***********************************************/
function JG_Attachment_GetUrl(method, fdId, needHost) {
	var host = "";
	if (needHost)
		host = location.protocol.toLowerCase() + "//" + location.hostname + ":" + location.port;
	return host + Com_Parameter.ContextPath
			+ "sys/attachment/sys_att_main/sysAttMain.do?method=" + method
			+ "&fdId=" + fdId;
}

/***********************************************
 功能 设置后台交互的参数
 ***********************************************/
function _JG_Attachment_SetParamter() {
	this.ocxObj.WebSetMsgByName("_attType", this.attType);
	this.ocxObj.WebSetMsgByName("_fdId", this.fdId);
	this.ocxObj.WebSetMsgByName("_userId", this.userId);
	this.ocxObj.WebSetMsgByName("_userName", this.userName);
	this.ocxObj.WebSetMsgByName("_fdModelId", this.fdModelId);
	this.ocxObj.WebSetMsgByName("_fdModelName", this.fdModelName);
	this.ocxObj.WebSetMsgByName("_fdKey", this.fdKey);
	if (!this.isTemplate && this.fdTemplateModelId && this.fdTemplateModelName) {
		this.ocxObj.WebSetMsgByName("_fdTemplateModelId",
				this.fdTemplateModelId);
		this.ocxObj.WebSetMsgByName("_fdTemplateModelName",
				this.fdTemplateModelName);
		this.ocxObj.WebSetMsgByName("_fdTemplateKey", this.fdTemplateKey);
	}
}

/***********************************************
 功能  在线编辑时设置书签值（内部方法）
 ***********************************************/
function _JG_Attachment_SetBookmark() {
	if (this.bookMarks) {
		var isWord = this._isWord();
		var editType = this.ocxObj.EditType;
		try {
			var json = (new Function("return (" + this.bookMarks + ");"))();
			if(!isWord) {
				//wps下设置书签不稳定临时处理方式
				this.ocxObj.EditType = "1,1";
			}
			for (var o in json) {
				if (json[o] == this.ocxObj.WebGetBookMarks(o)) { // 如果内容一样不再设置书签
					continue;
				}else{
					var markVal = json[o];
					if(markVal){
						markVal = markVal.replace(/&#039;/g, "'").replace(/&#034;/g, "\"");
					}
					 this.ocxObj.WebSetBookMarks(o, markVal);
				}
			}
		} catch (err) {
			//兼容原来的逻辑
			var bmsArr = this.bookMarks.split(",");
			if(!isWord) {
				//wps下设置书签不稳定临时处理方式
				this.ocxObj.EditType = "1,1"
			}
			for ( var i = 0, l = bmsArr.length; i < l; i++) {
				if (bmsArr[i]) {
					var bmArr = bmsArr[i].split(":");
					if (bmArr && bmArr[0] && bmArr[1]) {
						if (bmArr[1] == this.ocxObj.WebGetBookMarks(bmArr[0])) { // 如果内容一样不再设置书签
							continue;
						}
						//冒号处理，防止时间等带有冒号的值给截断了
						if(bmArr.length>2){
							this.ocxObj.WebSetBookMarks(bmArr[0], bmsArr[i].substring(bmsArr[i].indexOf(":")+1));
						}else{
							this.ocxObj.WebSetBookMarks(bmArr[0], bmArr[1]);
						}
					}
				}
			}
		} finally {
			this.ocxObj.EditType = editType;
		}
	}
}

/***********************************************
 功能  显示操作按钮
 ***********************************************/
function _JG_Attachment_ShowButton() {
	if (this.hasShowButton)
		return true;
	var _self = this;
	if (this.attType == "office") {
		var obj = null;
		var showDiv = this.buttonDiv;
		if (showDiv == null) {
			obj = document.getElementById("_button_" + this.fdKey
					+ "_JG_Attachment_TD");
			if(obj){
				obj.style.textAlign = "right";
				obj.parentElement.parentElement.parentElement.style.width = "100%";
			}
		} else {
			obj = document.getElementById(showDiv);
		}
		if(obj){
		var _createTempInput = function(name,value,flag){
			  var inputDom = document.createElement("a");
			  var a_name = document.createAttribute("name");
			    a_name.nodeValue = name;
			  var a_href = document.createAttribute("href");
			    a_href.nodeValue = "javascript:void(0);";
			  var a_class = document.createAttribute("class");
			     a_class.nodeValue = flag;
			    var a_Text = document.createTextNode(value);
			    inputDom.setAttributeNode(a_href);
			    inputDom.setAttributeNode(a_name);
			    inputDom.setAttributeNode(a_class);
			    inputDom.appendChild(a_Text);
			    return inputDom;
		};
		var _insertDomBefore = function(insertTo,insertObj){
			if(insertTo.firstChild!=null){
				insertTo.insertBefore(insertObj,insertTo.firstChild); 
			}else{
				insertTo.appendChild(insertObj);
			}
		};
		var _createInput = function(name,value){
			var inputDom = document.createElement("input");
			inputDom.setAttribute("type","button");
			inputDom.className="btnopt";
			inputDom.value = value;
			inputDom.setAttribute("name",name);
			return inputDom;
		};
		var _insertBefore = function(insertTo,insertObj){
			if(insertTo.firstChild!=null){
				insertTo.insertBefore(insertObj,insertTo.firstChild); 
			}else{
				insertTo.appendChild(insertObj);
			}
		};
		var attachmentObject = this;
		if (this.ocxObj.FileType.toLowerCase().lastIndexOf(".doc") > -1||this.ocxObj.FileType.toLowerCase().lastIndexOf(".wps") > -1) {
			//暂存
			if (this.canSaveDraft&&this.editMode=='edit'&&!this.isReadOnly) {
				if(!this.isTemplate && (Com_GetUrlParameter(location.href, "method") == 'view'||Com_GetUrlParameter(location.href, "method") == 'edit')) {//暂存按钮是否显示的判断条件
					var _saveDraft;
					if(showDiv == null){
						_saveDraft= _createInput(this.fdKey + "_saveDraft",Attachment_MessageInfo["button.saveDraft"]);
						_saveDraft.onclick = function() { 
							attachmentObject.ocxObj.WebSetMsgByName("_saveDraft", "true");
							attachmentObject._submit();
						};
						_insertBefore(obj,_saveDraft); 
						blank = document.createElement("SPAN");
						blank.innerHTML = "&nbsp;&nbsp;";
						_insertBefore(obj,blank); 
						
					}else{
					 _saveDraft= _createTempInput(this.fdKey + "_saveDraft",Attachment_MessageInfo["button.saveDraft"],"attsaveDraft");
					_saveDraft.onclick = function() { 
						attachmentObject.ocxObj.WebSetMsgByName("_saveDraft", "true");
						attachmentObject._submit();
					};
					_insertDomBefore(obj,_saveDraft); 
					blank = document.createElement("SPAN");
					blank.innerHTML = "&nbsp;&nbsp;";
					_insertDomBefore(obj,blank); 
				  }
				}
			}	
			if (this.canPrint) {
				if (!this.isTemplate && (this.currentMode != 'add' || this.fdIssample)) {
					var _print;
					if(showDiv == null){
						//打印
						_print =  _createInput(this.fdKey + "_print",Attachment_MessageInfo["button.print"]);
						_print.onclick = function() {
							//attachmentObject.ocxObj.WebOpenPrint();
							jgObjPrint(attachmentObject);
							//_self.addPrintLog();
						};
						_insertBefore(obj,_print);  
						blank = document.createElement("SPAN");
						blank.innerHTML = "&nbsp;&nbsp;";
						_insertBefore(obj,blank); 
						
					}else{
					//打印
					 _print =  _createTempInput(this.fdKey + "_print",Attachment_MessageInfo["button.print"],"attprint");
					_print.onclick = function() {
						//attachmentObject.ocxObj.WebOpenPrint();
						jgObjPrint(attachmentObject);
						//_self.addPrintLog();
					};
					_insertDomBefore(obj,_print);  
					blank = document.createElement("SPAN");
					blank.innerHTML = "&nbsp;&nbsp;";
					_insertDomBefore(obj,blank); 
					}
				}
			}
			if (this.canDownload) {
				if (!this.isTemplate && (this.currentMode != 'add' || this.fdIssample)) {
					//下载（相当于另存为）
						var _download;
						if(showDiv == null){
							_download = _createInput(this.fdKey + "_download", Attachment_MessageInfo["button.download"]);
							_download.onclick = function() {
								//合同正文下载需要留痕
								var isAgreement = false;
								var curRevisions = attachmentObject.showRevisions;
								if (attachmentObject.fdKey == 'mainOnline' 
									&& attachmentObject.fdModelName == 'com.landray.kmss.km.agreement.model.KmAgreementApply') {
									isAgreement = true;
								}
								if (attachmentObject.isProtect()) {
									attachmentObject.setProtect(false);
									if(attachmentObject.editMode != 'edit'){
										if (!isAgreement) {
											attachmentObject.ocxObj.ClearRevisions(); // 去除痕迹
										} else {
											if (curRevisions ==  false) {
												attachmentObject.ocxObj.ClearRevisions(); // 去除痕迹
											}
										}
									}	
								   if(_self.protectstatus){
									   attachmentObject.setProtect();
								   }
							   }else{
								   if(attachmentObject.editMode != 'edit'){
										if (!isAgreement) {
											attachmentObject.ocxObj.ClearRevisions(); // 去除痕迹
										} else {
											if (curRevisions == false) {
												attachmentObject.ocxObj.ClearRevisions(); // 去除痕迹
											}
										}
									}
								   if(!_self.protectstatus){
									   attachmentObject.ocxObj.WebSetProtect(false, "");
								   }else{
									   attachmentObject.ocxObj.WebSetProtect(true, "");
								   }
							   }
								//解码，避免下载时文件名乱码
								attachmentObject.ocxObj.FileName = decodeURIComponent(attachmentObject.ocxObj.FileName);
								if (attachmentObject.clearDownloadRevisions) {
									attachmentObject.ocxObj.ClearRevisions(); // 去除痕迹
									attachmentObject.ocxObj.WebSetRevision(false,false,false,false);
									attachmentObject.ocxObj.WebShow(false);
								}
								if (attachmentObject.ocxObj.WebSaveLocal()) { //保存为本地
									_JG_Attachment_chromeHideJG_2015(0);
									alert(Attachment_MessageInfo["msg.downloadSucess"]);
									_JG_Attachment_chromeHideJG_2015(1);
								}
								//编码，避免提交后文件名乱码
								attachmentObject.ocxObj.FileName = encodeURIComponent(attachmentObject.ocxObj.FileName);
								//修复下载后控件可以输入内容
								var copyValue;
								//国产化系统，金格控件的CopyType类型为boolean，非国产化为string
								if (window.userOpSysType.indexOf("Win") == -1 
								  		&& navigator.userAgent.indexOf("Firefox") >= 0) {
									copyValue = true;
								} else {
									copyValue = "1";
								}

								if(attachmentObject.editMode != 'edit'){
									 if(!attachmentObject.canCopy){
								    	attachmentObject.ocxObj.CopyType = copyValue;
								    	attachmentObject.ocxObj.EditType = "0,1";
									}else{
										attachmentObject.ocxObj.CopyType = copyValue;
										attachmentObject.ocxObj.EditType = "4,1";
									}
									if (isAgreement) {
										//合同查看页下载完成后，原文档需要保留痕迹
										if (curRevisions == true) {
											attachmentObject.showRevisions=true;
											attachmentObject.ocxObj.WebSetRevision(true,true,true,true);	
										} else {
											attachmentObject.showRevisions=false;
											attachmentObject.ocxObj.WebSetRevision(false,true,true,true);	
										}
									}
								}
								_self.addDownloadLog(); //记录下载日志
							};
							_insertBefore(obj,_download);
							blank = document.createElement("SPAN");
							blank.innerHTML = "&nbsp;&nbsp;";
							_insertBefore(obj,blank);
						}else{
							_download = _createTempInput(this.fdKey + "_download", Attachment_MessageInfo["button.download"],"attdownload");
							_download.onclick = function() {
							//合同正文下载需要留痕
							var isAgreement = false;
							var curRevisions = attachmentObject.showRevisions;
							if (attachmentObject.fdKey == 'mainOnline' 
								&& attachmentObject.fdModelName == 'com.landray.kmss.km.agreement.model.KmAgreementApply') {
								isAgreement = true;
							}
							if (attachmentObject.isProtect()) {
								attachmentObject.setProtect(false);
							   if(attachmentObject.editMode != 'edit'){
									if (!isAgreement) {
										attachmentObject.ocxObj.ClearRevisions(); // 去除痕迹
									} else {
										if (curRevisions == false) {
											attachmentObject.ocxObj.ClearRevisions(); // 去除痕迹
										}
									}
								}
								if(_self.protectstatus){
								   attachmentObject.setProtect();
								}
						    }else{
							   if(attachmentObject.editMode != 'edit'){
									if (!isAgreement) {
										attachmentObject.setProtect(false);
										attachmentObject.ocxObj.ClearRevisions(); // 去除痕迹
									} else {
										if (curRevisions == false) {
											attachmentObject.setProtect(false);
											attachmentObject.ocxObj.ClearRevisions(); // 去除痕迹
										}
									}
								}
							   if(!_self.protectstatus){
								    attachmentObject.ocxObj.WebSetProtect(false, "");
							   }else{
								    attachmentObject.ocxObj.WebSetProtect(true, "");
							   }
						    }
							//解码，避免下载时文件名乱码
							attachmentObject.ocxObj.FileName = decodeURIComponent(attachmentObject.ocxObj.FileName);
							if (attachmentObject.clearDownloadRevisions) {
								attachmentObject.ocxObj.ClearRevisions(); // 去除痕迹
								attachmentObject.ocxObj.WebSetRevision(false,false,false,false);
								attachmentObject.ocxObj.WebShow(false);
							}
							if (attachmentObject.ocxObj.WebSaveLocal()) { //保存为本地
								_JG_Attachment_chromeHideJG_2015(0);
								alert(Attachment_MessageInfo["msg.downloadSucess"]);
								_JG_Attachment_chromeHideJG_2015(1);
							}
							//编码，避免提交后文件名乱码
							attachmentObject.ocxObj.FileName = encodeURIComponent(attachmentObject.ocxObj.FileName);
							//修复下载后控件可以输入内容
							var copyValue;
							//国产化系统，金格控件的CopyType类型为boolean，非国产化为string
							if (window.userOpSysType.indexOf("Win") == -1 
							  		&& navigator.userAgent.indexOf("Firefox") >= 0) {
								copyValue = true;
							} else {
								copyValue = "1";
							}
							if(attachmentObject.editMode != 'edit'){
								 if(!attachmentObject.canCopy){
							    	attachmentObject.ocxObj.CopyType = copyValue;
							    	attachmentObject.ocxObj.EditType = "0,1";
								}else{
									attachmentObject.ocxObj.CopyType = copyValue;
									attachmentObject.ocxObj.EditType = "4,1";
								}
								if (isAgreement) {
									//合同查看页下载完成后，原文档需要保留痕迹
									if (curRevisions == true) {
										attachmentObject.showRevisions=true;
										attachmentObject.ocxObj.WebSetRevision(true,true,true,true);	
									} else {
										attachmentObject.showRevisions=false;
										attachmentObject.ocxObj.WebSetRevision(false,true,true,true);	
									}
								}
							}
							_self.addDownloadLog();  //记录下载日志
						};
						_insertDomBefore(obj,_download);
						blank = document.createElement("SPAN");
						blank.innerHTML = "&nbsp;&nbsp;";
						_insertDomBefore(obj,blank);
					  }
				}
			}
			
			if ((this.editMode == "edit" || this.editMode == "add")&&!this.isReadOnly) {
				var _page;
				var _bookmark;
				if(showDiv == null){
					//编辑页眉页脚
					_page = _createInput(this.fdKey + "_page",Attachment_MessageInfo["button.page"]);
					_page.onclick = function() {
						attachmentObject.ocxObj.WebObject.ActiveWindow.ActivePane.View.SeekView = 9;
					};
					_insertDomBefore(obj,_page); 
					blank = document.createElement("SPAN");
					blank.innerHTML = "&nbsp;&nbsp;";
					_insertDomBefore(obj,blank);
					// 书签管理
					_bookmark =  _createInput(this.fdKey + "_bookmark", Attachment_MessageInfo["button.bookmark"]);
					_bookmark.onclick = function() {
						try {
							attachmentObject.ocxObj.Active(true);//控件获取焦点
							attachmentObject.ocxObj.WebObject.Application.Dialogs(168).Show(); // office
						} catch (e) {
							//attachmentObject.ocxObj.WebObject.Application.Dialogs(16394).Show(); // wps
							try {
								attachmentObject.ocxObj.Active(true);//控件获取焦点
								attachmentObject.ocxObj.WebObject.Application.Dialogs.Item(168).Show(); // office
							} catch (e) {
								//attachmentObject.ocxObj.WebObject.Application.Dialogs(16394).Show(); // wps
								_JG_Attachment_chromeHideJG_2015(0);
								alert('文档只读状态下，书签管理功能不可用');
								_JG_Attachment_chromeHideJG_2015(1);
							}
						}
					};
					_insertBefore(obj,_bookmark); 
					blank = document.createElement("SPAN");
					blank.innerHTML = "&nbsp;&nbsp;";
					_insertBefore(obj,blank); 
				}else{
					//编辑页眉页脚
					 _page = _createTempInput(this.fdKey + "_page",Attachment_MessageInfo["button.page"],"attpage");
					_page.onclick = function() {
						attachmentObject.ocxObj.WebObject.ActiveWindow.ActivePane.View.SeekView = 9;
					};
					_insertDomBefore(obj,_page); 
					blank = document.createElement("SPAN");
					blank.innerHTML = "&nbsp;&nbsp;";
					_insertDomBefore(obj,blank); 
					// 书签管理
					 _bookmark =  _createTempInput(this.fdKey + "_bookmark", Attachment_MessageInfo["button.bookmark"],"attbook");
					_bookmark.onclick = function() {
						try {
							attachmentObject.ocxObj.Active(true);//控件获取焦点
							attachmentObject.ocxObj.WebObject.Application.Dialogs(168).Show(); // office
						} catch (e) {
							//attachmentObject.ocxObj.WebObject.Application.Dialogs(16394).Show(); // wps
							try {
								attachmentObject.ocxObj.Active(true);//控件获取焦点
								attachmentObject.ocxObj.WebObject.Application.Dialogs.Item(168).Show(); // office
							} catch (e) {
								//attachmentObject.ocxObj.WebObject.Application.Dialogs(16394).Show(); // wps
								_JG_Attachment_chromeHideJG_2015(0);
								alert('文档只读状态下，书签管理功能不可用');
								_JG_Attachment_chromeHideJG_2015(1);
							}
						}
					};
					_insertDomBefore(obj,_bookmark); 
					blank = document.createElement("SPAN");
					blank.innerHTML = "&nbsp;&nbsp;";
					_insertDomBefore(obj,blank); 
				}
			}
			if (!this.isTemplate && this.currentMode != 'add'&&this.hiddenRevisions) {
				var _showRevisions;
				//隐藏显示留痕按钮
				if (showDiv == null){
					if(!attachmentObject.forceRevisions){
						//如果设置了强制不留痕，则不保存痕迹
						setTimeout(function(){
							attachmentObject.ocxObj.WebShow(attachmentObject.forceRevisions);
						 },500);
					}else{
						if(attachmentObject.canCopy || this.editMode == "edit"){
							_showRevisions = _createInput(this.fdKey + "_showRevisions",
									this.showRevisions ? Attachment_MessageInfo["button.hideRevisions"]: Attachment_MessageInfo["button.showRevisions"]);
							//在线编辑打开，默认显示留痕
							setTimeout(function(){
								if(attachmentObject.showRevisions == true){
									attachmentObject.ocxObj.WebSetRevision(true,true,true,true);
								}else{
									attachmentObject.ocxObj.WebSetRevision(false,true,true,true);
								}
							 },500);
							_showRevisions.onclick = function() {
								attachmentObject.ocxObj.WebSetRevision(!attachmentObject.showRevisions,true,true,true);
								attachmentObject.showRevisions = !attachmentObject.showRevisions;
								_showRevisions.value = attachmentObject.showRevisions ? Attachment_MessageInfo["button.hideRevisions"]
										: Attachment_MessageInfo["button.showRevisions"];
								var beforeOptBtnLength = $(".optInnerContainerTab").find("tr .innerTdBtn2").length;
								OptBar_Refresh();
								//如果操作栏上的按钮是通过扩展点方式引入，此时刷新后会丢失扩展点对应的按钮，应该加上
								var afterOptBtnLength = OptBar_ButtonList.length;
								if (afterOptBtnLength < beforeOptBtnLength) {
									//扩展点方式加入按钮，使用addBtn方法
									if (window.addBtn) {
										window.addBtn();
									}
									if (window.loadClauseBtn) {
										window.loadClauseBtn();
									}
								}
							};
							_insertBefore(obj,_showRevisions); 
							var blank = document.createElement("SPAN");
							blank.innerHTML = "&nbsp;&nbsp;";
							_insertBefore(obj,blank);
						}
					}
				}else{
					if(!attachmentObject.forceRevisions){
						//如果设置了强制不留痕，则不保存痕迹
						setTimeout(function(){
							attachmentObject.ocxObj.WebShow(attachmentObject.forceRevisions);
						 },500);
					}else{
					  if(attachmentObject.canCopy || this.editMode == "edit"){
						_showRevisions= _createTempInput(this.fdKey + "_showRevisions",
								this.showRevisions ? Attachment_MessageInfo["button.hideRevisions"]: Attachment_MessageInfo["button.showRevisions"],"attshowRevisions");
						//在线编辑打开，默认显示留痕
						
						setTimeout(function(){
							if(attachmentObject.showRevisions == true){
								attachmentObject.ocxObj.WebSetRevision(true,true,true,true);
							}else{
								attachmentObject.ocxObj.WebSetRevision(false,true,true,true);
							}
						 },500);
						
						_showRevisions.onclick = function() {
						attachmentObject.ocxObj.WebSetRevision(!attachmentObject.showRevisions,true,true,true);
						attachmentObject.showRevisions = !attachmentObject.showRevisions;
						_showRevisions.innerHTML = attachmentObject.showRevisions ? Attachment_MessageInfo["button.hideRevisions"]
								: Attachment_MessageInfo["button.showRevisions"];
						//OptBar_Refresh();
					  };
					  _insertDomBefore(obj,_showRevisions); 
						var blank = document.createElement("SPAN");
						blank.innerHTML = "&nbsp;&nbsp;";
						_insertDomBefore(obj,blank);
					}
				 }
			  }
			}
		} 
		if(this.ocxObj.FileType.toLowerCase().lastIndexOf(".xls") > -1 || this.ocxObj.FileType.toLowerCase().lastIndexOf(".et") > -1){
			if (this.canPrint) {
				if (!this.isTemplate && this.currentMode != 'add') {
					var _print;
					if(showDiv == null){
						//打印
						_print  = _createInput( this.fdKey + "_print", Attachment_MessageInfo["button.print"]);
						_print.onclick = function() {
							//attachmentObject.ocxObj.WebOpenPrint();
							jgObjPrint(attachmentObject);
							//_self.addPrintLog();
						};
						_insertBefore(obj,_print);
						blank = document.createElement("SPAN");
						blank.innerHTML = "&nbsp;&nbsp;";
						_insertBefore(obj,blank);
						// 打印预览
					}else{
					//打印
					_print  = _createTempInput( this.fdKey + "_print", Attachment_MessageInfo["button.print"],"_print");
					_print.onclick = function() {
						//attachmentObject.ocxObj.WebOpenPrint();
						jgObjPrint(attachmentObject);
						//_self.addPrintLog();
					};
					_insertDomBefore(obj,_print);
					blank = document.createElement("SPAN");
					blank.innerHTML = "&nbsp;&nbsp;";
					_insertDomBefore(obj,blank);
					// 打印预览
				  }
				}
			}
			//暂存
			if (this.canSaveDraft&&this.editMode=='edit'&&!this.isReadOnly) {
				if(!this.isTemplate && (Com_GetUrlParameter(location.href, "method") == 'view'||Com_GetUrlParameter(location.href, "method") == 'edit')) {//暂存按钮是否显示的判断条件
					var _saveDraft;
					if(showDiv == null){
						_saveDraft= _createInput(this.fdKey + "_saveDraft",Attachment_MessageInfo["button.saveDraft"]);
						_saveDraft.onclick = function() { 
							attachmentObject.ocxObj.WebSetMsgByName("_saveDraft", "true");
							attachmentObject._submit(); 
						};
						_insertBefore(obj,_saveDraft);
						blank = document.createElement("SPAN");
						blank.innerHTML = "&nbsp;&nbsp;";
						_insertBefore(obj,blank);
					}else{
					_saveDraft= _createTempInput(this.fdKey + "_saveDraft",Attachment_MessageInfo["button.saveDraft"],"attsaveDraft");
					_saveDraft.onclick = function() { 
						attachmentObject.ocxObj.WebSetMsgByName("_saveDraft", "true");
						attachmentObject._submit(); 
					};
					_insertDomBefore(obj,_saveDraft);
					blank = document.createElement("SPAN");
					blank.innerHTML = "&nbsp;&nbsp;";
					_insertDomBefore(obj,blank);
				  }
				}
			}
		}
		 
		if (this.showOpenDraft||(this.currentMode == 'add'&&this.editMode=='edit'&&!this.isTemplate&&!this.isReadOnly)) { 
			// 打开本地初稿
			if(showDiv == null){
				// 打开本地初稿
				var _openLocal = _createInput(this.fdKey + "_openLocal",Attachment_MessageInfo["button.openLocal"]);
				_openLocal.onclick = function() {
					attachmentObject.ocxObj.FileName = decodeURIComponent(attachmentObject.ocxObj.FileName);
					attachmentObject.ocxObj.WebOpenLocal();
					var  fileName = attachmentObject.ocxObj.FileName;
					if(attachmentObject.ocxObj.FileType == ".docx" && fileName.indexOf(".docx") > -1){
						attachmentObject.ocxObj.FileType = ".doc";
						if (attachmentObject._getConfigPluginOfficeType() == 'wps') {
							attachmentObject.ocxObj.FileType = ".wps";
						}
						attachmentObject.ocxObj.FileName = fileName.substring(0,fileName.lastIndexOf(".")) + attachmentObject.ocxObj.FileType;
					}
				};
				_insertBefore(obj,_openLocal);
				blank = document.createElement("SPAN");
				blank.innerHTML = "&nbsp;&nbsp;";
				_insertBefore(obj,blank); 
			}else{
			var _openLocal = _createTempInput(this.fdKey + "_openLocal",Attachment_MessageInfo["button.openLocal"],"attopenLocal");
			_openLocal.onclick = function() {
				var retLocalFile = attachmentObject.ocxObj.WebOpenLocal();
				var fileName = attachmentObject.ocxObj.FileName;
				var isAgreementModel = false;
				//判断是否范本起草打开本地初稿
				if ('mainOnline' == attachmentObject.fdKey
						&& ('com.landray.kmss.km.agreement.model.KmAgreementModel' == attachmentObject.fdModelName
						|| 'com.landray.kmss.km.sample.model.KmSampleModel' == attachmentObject.fdModelName
						|| 'com.landray.kmss.km.agreement.model.KmAgreementApply' == attachmentObject.fdModelName)) {
					isAgreementModel = true;
				}
				if(attachmentObject.ocxObj.FileType == ".docx" && fileName.indexOf(".docx") > -1){
					if (!isAgreementModel) {
						attachmentObject.ocxObj.FileType = ".doc";
					}
					if (attachmentObject._getConfigPluginOfficeType() == 'wps') {
						attachmentObject.ocxObj.FileType = ".wps";
					}
					attachmentObject.ocxObj.FileName = fileName.substring(0,fileName.lastIndexOf(".")) + attachmentObject.ocxObj.FileType;
					//范本正文打开初稿需要暂存
					if (isAgreementModel && retLocalFile) {
						attachmentObject.ocxObj.WebSetMsgByName("_saveDraft", "true");
						attachmentObject._submit();
					}
					
				}
			};
			_insertDomBefore(obj,_openLocal);
			blank = document.createElement("SPAN");
			blank.innerHTML = "&nbsp;&nbsp;";
			_insertDomBefore(obj,blank); 
		  }
		}
		if(showDiv == null){
			if (jgPluginType != '2003') {
				var _openLocal = _createInput(this.fdKey + "_fullSize",Attachment_MessageInfo["button.fullsize"]);
				_openLocal.onclick = function() {
					attachmentObject.ocxObj.FullSize();
				};
				_insertBefore(obj,_openLocal);
			}

			blank = document.createElement("SPAN");
			blank.innerHTML = "&nbsp;&nbsp;";
			_insertBefore(obj,blank); 
			OptBar_Refresh(true);
			this.hasShowButton = true;
		}else{
			if (jgPluginType != '2003') {
				var _fullSize = _createTempInput(this.fdKey + "_fullSize",Attachment_MessageInfo["button.fullsize"],"attfullSize");
				_fullSize.onclick = function() {
					attachmentObject.ocxObj.FullSize();
				};
				_insertDomBefore(obj,_fullSize); 
			}
			var blank = document.createElement("SPAN");
			blank.innerHTML = "&nbsp;&nbsp;";
			_insertDomBefore(obj,blank); 
			this.hasShowButton = true;
		}		
		if(this.editMode == "edit"){
			if (!this.hasAddSubmitFun && !this.disabled &&this.bindSubmit) {
				Com_Parameter.event["confirm"].unshift( function() {
					return attachmentObject._submit();
				});
				this.hasAddSubmitFun = true;
			}
		}
		}
	}
}

/**
 * 解决火狐浏览器下打印崩溃问题
 * @param attachmentObject
 * @returns
 */
function jgObjPrint(attachmentObject) {
	attachmentObject.ocxObj.Active(true);//控件获取焦点
	if (null != userOsTypeParam && userOsTypeParam == "windows") {
		if (navigator.userAgent.indexOf("Firefox") >= 0 || navigator.userAgent.indexOf("Chrome") >= 0){
			attachmentObject.activeObj.ExecuteScript("OpenOCXPrint","ActiveObject.FuncExtModule.WebOpenPrint()");
		} else {
			attachmentObject.ocxObj.WebOpenPrint();
		}
	} else {
		if (null != isEnabledParam && isEnabledParam == "true") {
			attachmentObject.ocxObj.WebOpenPrint();
		}
	}
}

/***********************************************
 功能  隐藏操作按钮
 ***********************************************/
function _JG_Attachment_HideButton(showDiv) {
	if (!this.hasShowButton)
		return true;
	if (showDiv == null)
		showDiv = this.buttonDiv;
	if (this.attType == "office") {
		if (showDiv == null)
			showDiv = "_button_" + this.fdKey + "_JG_Attachment_TD";
		var obj = document.getElementById(showDiv);
		var nodes = obj.childNodes;
		for ( var i = 0; i < nodes.length; i++) {
			if (nodes[i].name != null
					&& nodes[i].name.indexOf(this.fdKey + "_") == 0) {
				obj.removeChild(nodes[i]);
			}
		}
		OptBar_Refresh(true);
		this.hasShowButton = false;
	}
	return true;
}

/***********************************************
 功能  提交保存附件，该操作调用控件的上传方法上传附件
 ***********************************************/
function _JG_Attachment_Submit() {
	//合同正文未上传时，进行暂存操作不需要提交保存
	if ('mainOnline' == this.fdKey && 
			'com.landray.kmss.km.agreement.model.KmAgreementApply' == this.fdModelName) {
		var pageUrl = window.location.href;
		if (typeof formInitData != "undefined" && !formInitData.fdAttMainId) {
			return true;
		} else if (pageUrl.indexOf('method=add&fdApplyId') < 0 && window.opener != null && typeof window.opener.formInitData != "undefined"  && !window.opener.formInitData.fdAttMainId) {
			return true;
		}
	}
	
	var OSType = jg_detectOS();
	if(OSType.indexOf("Win") == -1 && navigator.userAgent.indexOf("Firefox") >= 0
			&& null != this.ocxObj.FileType
			&& this.ocxObj.FileType.indexOf("xls") == -1){
		//在国产系统zzkk版本下的火狐浏览器需要先获取控件焦点再提交保存
		this.ocxObj.setScreenFocus();
	}
	this._setParamter();
	if (this.currentMode == 'add' || !this.trackRevisions || this.isTemplate) {
		//流程管理后台设置需要注释掉此行才能保存
		this.ocxObj.ClearRevisions();
	}
	var originFileName = this.ocxObj.FileName;
	var newFileName = this.ocxObj.FileName;
	/*if(this._isAfter2003()){
		newFileName = newFileName.substring(0,newFileName.length - 1);
		this.ocxObj.FileName = newFileName;
	}*/
	try{
		//this.ocxObj.FileName = encodeURIComponent(this.ocxObj.FileName);
		this.ocxObj.AllowEmpty = false;//是否允许保存空白文件
		if (!this.ocxObj.WebSave()) {
			this.setStatusMsg(this.ocxObj.Status);
			//alert(this.ocxObj.Status);
			return false;
		}
		//刷新opener的附件列表
		var fdKeyFields ;
		try{
			fdKeyFields = window.opener.document.getElementsByName("attachmentKeyParam");
		}catch(e){
			fdKeyFields = document.getElementsByName("attachmentKeyParam");
		}
		if(fdKeyFields && originFileName!=newFileName){
			for(var i=0;i<fdKeyFields.length;i++){
				var value = fdKeyFields[i].value;
				if(window.opener["attachmentObject_"+value].isOnlineEdit){
					window.opener["attachmentObject_"+value].isOnlineEdit = false;
					window.opener["attachmentObject_"+value].refreshList(decodeURIComponent(originFileName),decodeURIComponent(newFileName));
				}
			}
		}
		this._writeAttachmentInfo();
		this.setStatusMsg(this.ocxObj.Status);
		return true;
	} catch (e) {
		alert("jg_submit error: " + e.description);
	}
	
}

/***********************************************
 功能  附件保存后调用该方法写入文档信息
 ***********************************************/
function _JG_Attachment_WriteAttachmentInfo() {
	var addIds = this.ocxObj.WebGetMsgByName("_fdAttMainId");
	if (addIds) {
		var addAttIds = document.getElementsByName("attachmentForms."
				+ this.fdKey + ".attachmentIds")[0];
		if (addAttIds != null) {
			addAttIds.value = addIds;
		}
	}
}

/***********************************************
 功能 判断是否office word
 ***********************************************/
function _JG_Attachment_IsWord() {
	try {
		if (this.ocxObj.WebObject.Application.Name.indexOf("Microsoft Word") >= 0) {
			return true;
		}
	} catch (e) {
		return false;
	}
	return false;
}
 
 /***********************************************
 功能 判断是否office Excel
 ***********************************************/
function _JG_Attachment_IsExcel() {
	try {
		if (this.ocxObj.WebObject.Application.Name.indexOf("Microsoft Excel") >= 0) {
			return true;
		}
	} catch (e) {
		return false;
	}
	return false;
}

/***********************************************
功能 判断是否office  2003以上版本
***********************************************/
function _JG_Attachment_IsAfter2003() {
		if (this.ocxObj.FileType.toLowerCase().lastIndexOf(".xlsx") > -1) {
			return true;
		}
		if (this.ocxObj.FileType.toLowerCase().lastIndexOf(".docx") > -1) {
			return true;
		}
	
	return false;
}

/***********************************************
功能 判断是否wps版本
***********************************************/
function _JG_Attachment_getWpsVersion() {
	var version = this.ocxObj.WebObject.Application.Build ;
	    if(version.substring(0,1)=='8'){
	    	alert(Attachment_MessageInfo["error.jgsupport"].replace("{0}","WPS2012"));
	    	return "2012";
	    }else if(version.substring(0,1)=='6'){
	    	return "2009";
	    }
}

/*******************************************************************************
 * 功能 保存为图片文档
 ******************************************************************************/
function JG_Attachment_saveAsImage() {
	//this.ocxObj.ClearRevisions(); // 清除所有留痕
	try {
		if (!this.ocxObj.WebSaveImage()) {
			this.setStatusMsg(this.ocxObj.Status);
			alert(this.ocxObj.Status);
			return false;
		}
		this.setStatusMsg(this.ocxObj.Status);
		return true;
	} catch (e) {
		alert("JG_Attachment_saveAsImage error: " + e.description);
	}
}

/***********************************************
功能 使用2003金格控件时，判断用户选择的办公软件类型
返回 office/wps
***********************************************/
function _JG_Attachment_getConfigPluginOfficeType() {
	var selType = 'office';
	if ('2003' == jgPluginType && 'wps' == jgOfficeType) {
		selType = 'wps';
	}
	return selType;
}


 function loadingChange()   
 {   
	 if(document.readyState == "complete"){ 
    	 //当页面加载状态为完全结束时进入
    	 $("input[type='text']").mousedown(function(){
    		 $(this).blur();
    		 $(this).focus();
    	 });
    	 
    	/* .each(function(node,_obj){
 			$(_obj).mousedown(function(e){
 				var fireFoxObj = document.getElementById("JGWebOffice_"+window.jgOCX_Name);
 				if (null != jgBigVersionParam && jgBigVersionParam == "2015") {
 					fireFoxObj = fireFoxObj.FuncExtModule;
				}
 				if(fireFoxObj){
 					fireFoxObj.Active(false);
 				}
				setTimeout(function(){
					$(this).focus();
				 },500);
 			});
 		});*/
	 }
 }  
 if (navigator.userAgent.indexOf("Firefox") >= 0){
	 document.onreadystatechange = loadingChange;//当页面加载状态改变的时候执行这个方法.	 
 }
 
 
 /***********************************************
 功能：显示标签表格的第几个标签
 参数：
 	tableName：
 		必选，字符串，表格ID
 	index：
 		必选，整数，标签索引号
 ***********************************************/
 function Doc_SetCurrentLabel(tableName, index, noEvent){
 	var tbObj = document.getElementById(tableName);
 	var curLabel = tbObj.getAttribute("LKS_CurrentLabel");
 	if(curLabel==index)
 		return;
 	var styleInfo = Doc_GetStyleInfo(tbObj);
 	var imgId = tableName + "_Label_Img_";
 	var btnId = tableName + "_Label_Btn_";
 	
 	var switchFunc = tbObj.getAttribute("LKS_OnLabelSwitch");
 	if(!noEvent && switchFunc!=null){
 		var switchFuncArr = switchFunc.split(";");
 		for(var i=0; i<switchFuncArr.length; i++){
 			if(window[switchFuncArr[i]]!=null && window[switchFuncArr[i]](tableName, index)==false)
 				return;
 		}
 	}
 	var btnObj;
 	if(curLabel!=null){
 		document.getElementById(imgId+curLabel+"_1").src = styleInfo.imagePath + "graylabbg1.gif";
 		btnObj = document.getElementById(btnId+curLabel);
 		btnObj.style.backgroundImage = "url(" + styleInfo.imagePath + "graylabbg2.gif)";
 		btnObj.className = styleInfo.classPrefix+"btnlabel2";
 		document.getElementById(imgId+curLabel+"_2").src = styleInfo.imagePath + "graylabbg3.gif";
 	}
 	document.getElementById(imgId+index+"_1").src = styleInfo.imagePath + "curlabbg1.gif";
 	btnObj = document.getElementById(btnId+index);
 	btnObj.style.backgroundImage = "url(" + styleInfo.imagePath + "curlabbg2.gif)";
 	btnObj.className = styleInfo.classPrefix+"btnlabel1";
 	document.getElementById(imgId+index+"_2").src = styleInfo.imagePath + "curlabbg3.gif";
 	//先显示，后隐藏，避免页面滚动
 	var trRows = $("#" + tableName + " > tbody > tr");
 	if(trRows.length<1){
 		trRows = $("#" + tableName + " > tr");
 	}
 	var trObj = $(trRows[index]);
 	trObj.show();
 	var resizeArr =[];
 	//防止选项卡中带附件是 IE下 find("*[KMSS_OnShow]:visible") 这个方法出现异常
 	try{
 		resizeArr=trObj.find("*[KMSS_OnShow]:visible");
 	}catch(e){
 		resizeArr =[];
 	}
 	var resizeAttr = "";
 	if(resizeArr.length>0){
 		resizeAttr = "KMSS_OnShow";
 	}else{
 		if(!Com_Parameter.IE ){
 			resizeArr =[];
 			try{
 				resizeArr=trObj.find("*[onresize]:visible");
 			}catch(e){
 				resizeArr=[];
 			}
 			resizeAttr = "onresize";
 		}
 	}
 	if(resizeArr.length>0){
 		resizeArr.each(function(){
 			var funStr = this.getAttribute(resizeAttr);
 			if(funStr!=null && funStr!=""){
 				var tmpFunc = new Function(funStr);
 				tmpFunc.call(this, Com_GetEventObject());
 			}
 		});
 	}
 	
 	if (navigator.userAgent.indexOf("Firefox") >= 0 || navigator.userAgent.indexOf("Chrome") >= 0){
 		for(var i=1; i<trRows.length; i++){
 			//将原来hide模式改成CSS样式来控件tab页签，否则金格控件在切换tab时会被销毁
 			if(i!=index){
 				$(trRows[i]).css({'position': 'absolute','top':'-99999999em'});
 			} else {
 				$(trRows[i]).removeAttr("style");
 				$(trRows[i]).show();
 			}
 		}
 	} else {
 		for(var i=1; i<trRows.length; i++){
 			if(i!=index){
 				$(trRows[i]).hide();
 			}
 		}
 	}
 	
 	tbObj.setAttribute("LKS_CurrentLabel", index);
 }

 	/* 区域保护 (根据书签名设置可以修改的位置，多个用,分割)*/
 	/* tableMode 针对书签在表格中情形，为空或者0,原有保护逻辑，tableMode = 1,书签所在的单元格被保护，但是可以进行表格行扩展 */
 	/* bookMappingJson 书签名称与表单字段的对应关系配置 */
	function WebAreaProtect(BMarksName, pwd, tableMode, bookMappingJson) {
		if(!this.ocxObj.WebObject) {return;}
		var IEBrowerFlag = true;
		if (navigator.userAgent.indexOf("Chrome") >= 0) {
			IEBrowerFlag = false;
    	}
		if (this.ocxObj.FileType == ".doc" || this.ocxObj.FileType == ".docx"){
			if(this.ocxObj.WebObject.ProtectionType == "-1") { // 在没有保护的情况下才能进行保护
				if(!BMarksName)
					BMarksName="";
				var mMarksName = BMarksName.split(",");
				var tag = "";
				var allBookMarks = this.ocxObj.WebObject.Application.ActiveDocument.Bookmarks;
				for(var i=0;i<mMarksName.length;i++){
					if (!allBookMarks.Exists(mMarksName[i])) {
						tag += mMarksName[i];
	                    continue;
					}
					var range = allBookMarks.Item(mMarksName[i]).Range;
					if (tableMode == 1) {
						var bookmarkObj = allBookMarks.Item(mMarksName[i]);
						//判断书签是否在表格中,office软件VBA支持Information，wps 软件不支持，所以用替代方法
						//var markPosTable = range.Information(12);
						var markPosTable = range.Tables.Count;
						var mappingField = bookMappingJson[mMarksName[i]];
						var detailFlag = true;
						if(mappingField.indexOf('.')>-1){
							var prefix = mappingField.split('.')[0];
							if ('00' == prefix || '01' == prefix || '02' == prefix || '03' == prefix) {
								//当做普通书签字段处理，非表格书签
								detailFlag = false;
							}
						}
						if (markPosTable > 0 && detailFlag) {
							var bookmarkCell = range.Cells.Item(1);
							//bookmarkObj.Select();
							var table = bookmarkCell.Range.Tables.Item(1);
							//表格行及书签对应的列可编辑
							bookmarkCell.Range.Editors.Add(-1);
							var curColIndex = bookmarkCell.ColumnIndex;
							var curRowIndex = bookmarkCell.RowIndex;
							//是否存在纵向合并的单元格，IE浏览器与谷歌浏览器判断方式不一样
							var rowSpanFlag = true;
							if (IEBrowerFlag && table.rows.Cells) {
								rowSpanFlag = false;
							} else if (!IEBrowerFlag && table.rows.Item(1).Cells) {
								rowSpanFlag = false;
							}
							
							if (!rowSpanFlag) {
								//不存在纵向合并的单元格，可以按照以前行列方式处理
								for (var m = curRowIndex;m <= table.Rows.Count; m ++) {
									table.Rows.Item(m).Range.Editors.Add(-1);
								}
							} else {
								//存在纵向合并的单元格，不能按照行列方式处理
								for (var m = curRowIndex;m <= table.Rows.Count; m ++) {
									var realCells = table.Range.Cells;
									for(var j=1;j<=realCells.Count;j++){
										var tableCell = realCells.Item(j);
										if (tableCell.RowIndex == m) {
											//tableCell.Range.Editors.Add(-1);
										}
									}
								}
							}
						} else {
							range.Editors.Add(-1); 	// 常量：wdeditoreveryone=-1
							this.ocxObj.WebObject.Application.Selection.Editors.Add(-1);
						}
					} else {
						//range.Select();
						range.Editors.Add(-1); 	// 常量：wdeditoreveryone=-1
						this.ocxObj.WebObject.Application.Selection.Editors.Add(-1);
					}
				}
				this.ocxObj.WebObject.Protect(3, false, pwd, false, false);				// 常量：wdAllowOnlyReading=3
				this.ocxObj.WebObject.Application.Selection.MoveLeft(Unit = 1,
						Count = 1);
				//this.ocxObj.WebObject.ActiveWindow.View.ShadeEditableRanges = false;		// 取消"突出显示可编辑区域"
			}
		} else {
			//this.ocxObj.Alert("非Office文档，无法执行区域保护操作!");
			//alert("非Office文档，无法执行区域保护操作!");
		}
	}
	
	/* 取消区域保护 */
	function WebAreaUnprotect(BMarksName, pwd) {
		if(!this.ocxObj) {return;}
		if(!this.ocxObj.WebObject) {return;}
		if (this.ocxObj.FileType == ".doc" || this.ocxObj.FileType == ".docx"){
			if(!BMarksName) {
				BMarksName="";
			}
			if (BMarksName == '') {
				return;
			};
			var mMarksName = BMarksName.split(",");
			var allBookMarks = this.ocxObj.WebObject.Application.ActiveDocument.Bookmarks;
			for(var i=0;i<mMarksName.length;i++){
				if (!mMarksName[i]||allBookMarks.Exists(mMarksName[i])) 		// 判断是否存在该书签
				{
					try {
						if(this.ocxObj.WebObject.ProtectionType != -1) { // 在保护的情况下进行解除保护
							this.ocxObj.WebObject.Unprotect(pwd);				// 解保护
						}
						if (!allBookMarks.Exists(mMarksName[i])) {
		                    continue;
						}
						var range = allBookMarks.Item(mMarksName[i]).Range;
						range.Select();											// 选定书签内容
						this.ocxObj.WebObject.DeleteAllEditableRanges(-1); 	// 去掉突出显示
					} catch (e) {
						//this.ocxObj.Alert("执行取消区域保护时出现错误，错误原因：" + e.description);
						alert("执行取消区域保护时出现错误，错误原因：" + e.description);
					}
				}
			}
		} else {
			//this.ocxObj.Alert("非Office文档，无法执行取消区域保护操作!");
			//alert("非Office文档，无法执行取消区域保护操作!");
		}
	}
	
	//隐藏显示金格控件，调用common.js中方法不生效，这里重写一个
	function _JG_Attachment_chromeHideJG_2015(value) {	
		try{
			if (navigator.userAgent.indexOf("Chrome") >= 0) {
				if (null != jgBigVersionParam && jgBigVersionParam == "2015") {
					$("object[id*='JGWebOffice_']").each(function(i,_obj){
							_obj.HidePlugin(value);
					});	
				}
			}
		}catch(e){}
	}
