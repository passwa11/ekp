Com_RegisterFile("wps_center_attachment.js");
Com_IncludeFile("common.js");
Com_IncludeFile("data.js");


function WPSCenterOffice_AttachmentObject(fdId, fdKey,fdModelId,fdModelName, fdMode,hasHead,canPrint,canExport, directPreview) {
	this.fdId = fdId;
	this.fdKey = fdKey; // 附件标识
	this.fdModelName = fdModelName;
	this.fdModelId = fdModelId;
	this.optMode = Com_GetUrlParameter(location.href, "method");
	this.fdMode = fdMode;
	this.contentFlag = false;  //公文单子控制正文下载打印权限使用
	this.isTemplate = false; //是否是模板正文
	this.wpsPreview = '';
	this.canPrint = '';
	this.canExport = '';
	this.directPreview = directPreview;

	this.hasHead=hasHead;//wps菜单栏
	
	this.container = "WPSCenterOffice_" + fdKey;
	this.wpsObj = null;
	this.wpsRevisionsObj = null;
	this.updateRelationUrl =  Com_Parameter.ContextPath + "sys/attachment/sys_att_main/sysAttMain.do?method=updateCloudRelation";
	this.isSubmitByWps = true;
	this.hasLoad = false;
	this.isLoading = false;
	this.forceLoad = false;
	
	this.load = WPSCenterOffice_Attachment_Load;
	this.unLoad = WPSCenterOffice_Attachment_UnLoad;
	this.submit = WPSCenterOffice_Attachment_Submit;
	this.print = WPSCenterOffice_Attachment_Print;
	this.accent = WPSCenterOffice_Attachment_accent;
	this.reject = WPSCenterOffice_Attachment_reject;
	this.handleRevision = WPSCenterOffice_Attachment_handleRevision;

}

/***********************************************
功能 打印
***********************************************/
function WPSCenterOffice_Attachment_Print() {
   var _self = this;
   if(_self.wpsObj && typeof _self.wpsObj.WpsApplication != "undefined") {
	   _self.wpsObj.WpsApplication().CommandBars('TabPrintPreview').Execute();
   }
}

function refreshToken() {
	var token = null;
	$.ajax({
		type: "post",
		url: Com_Parameter.ContextPath + "sys/attachment/sys_att_main/sysAttMain.do?method=getLongToken4WpsCenter",
		async: false,
		dataType: "json",
		success:function(data){
			if (data && data.success) {
				token = {
					'token': data.token,
					'timeout': data.timeout
				};
			}
		}
	});
	return token;
}

/***********************************************
功能 打开文档
***********************************************/
function WPSCenterOffice_Attachment_Load() {
   var _self = this;
   if(self.hasLoad && !self.isLoading){
		self.forceLoad = true;
	}
	if((!self.hasLoad && !self.isLoading) || self.forceLoad){
		_self.isLoading = true;
		var url = Com_Parameter.ContextPath+"sys/attachment/sys_att_main/sysAttMain.do?method=getWpsCenterViewAndEditUrl";
		   $.ajax({    
		 	     type:"post",     
		 	     url:url,     
		 	     data:{fdAttMainId:this.fdId,fdMode:this.fdMode,contentFlag:this.contentFlag,canPrint:this.canPrint,canExport:this.canExport},
		 	     async:false,    //用同步方式 
		 	     success:function(data){
			 	    	 if(isJSON(data)){
			 	    		var results =  eval("("+data+")");				 	    
							if(results['url']){
						    	var wpsUrl = results['url'];
						    	var mode = 'normal';
						    	if (_self.wpsPreview != '' && _self.fdMode == 'read') { // 预览是否要显示评论 修订等状态
									wpsUrl += "&wpsPreview=" + _self.wpsPreview;
								} else if (_self.wpsPreview == '' && _self.fdMode == 'read'
									&& _self.directPreview != undefined && _self.directPreview != '') {
									wpsUrl += "&wpsPreview=" + _self.directPreview;
								} else if (_self.fdMode == 'read') {  // 预览默认不显示评论 修订等状态
									wpsUrl += "&wpsPreview=0010000";
								}
						    	if(_self.hasHead=="simple"){
						    		mode=_self.hasHead;
						    	}
								if ('write' === _self.fdMode) {
									var token = refreshToken();
									//编辑模式使用中台sdk传递token鉴权
									_self.wpsObj = WebOfficeSDK.config({
										mount: $("#" + _self.container)[0],
										url: wpsUrl,
										mode: mode,
										refreshToken
									});
									// 设置 token
									_self.wpsObj.setToken(token);
								} else {
									_self.wpsObj = WebOfficeSDK.config({
										mount:$("#"+_self.container)[0],
										url: wpsUrl,
										mode:mode
									});
								}

								//_self.wpsObj.iframe.style["pointer-events"]="none";
								//_self.wpsObj.setToken({token:results['wpsCenterToken'],timeout: 10 * 60 * 1000});
								//_self.wpsObj.iframe.style.width='100%';
								//_self.wpsObj.iframe.style.height='556px';
								//document.getElementById(_self.container).appendChild(_self.wpsObj.iframe)

					     		if(_self.optMode != "add" && _self.fdMode == "write" && !_self.isTemplate){
						    		 _self.wpsObj.ready().then(function(){
						    		 	if (typeof _self.wpsObj.WpsApplication != "undefined") {
											_self.wpsObj.WpsApplication().ActiveDocument.TrackRevisions = true;
											_self.wpsRevisionsObj=_self.wpsObj.WpsApplication().ActiveDocument.Revisions;
										}
						    		 });
						    	}else{
						    		 _self.wpsObj.ready().then(function(){
										 if (typeof _self.wpsObj.WpsApplication != "undefined") {
											 _self.wpsObj.WpsApplication().ActiveDocument.TrackRevisions = false;
										 }

						    		 });
						    	}

						    	//如果是编辑状态，提交表单的时候提交附件内容

						    	if(_self.fdMode == "write"){
						    		top.window.Com_Parameter.event["confirm"].unshift( function() {
										if ($("#"+_self.container).is(":visible") && typeof _self.wpsObj.WpsApplication != "undefined") {
											_self.wpsObj.WpsApplication().ActiveDocument.TrackRevisions = false;
										}
						    			return _self.submit();
						    		});
						    	}
							}else{
								if(results['error']){
									seajs.use(["lui/dialog", "lui/jquery"], function(dialog, $) {
										dialog.alert(results['error']);
									});
								}
							}
							_self.hasLoad = true;
							_self.isLoading = false;
			 	    	 }else{
			 	    		$("#"+_self.container).html(data);
			 	    	 }
			 	    	if (typeof(seajs) != 'undefined') {
		 	 			seajs.use( ['lui/topic' ], function(topic) {
		 	 			 	topic.publish('/sys/attachment/wpsCenter/loaded', _self.wpsObj, {});
		 	 			});
			 	    	}
						 setTimeout(function(){
							 if(_self.wpsObj)
								 _self.wpsObj.iframe.style.width='100%';
						 }, 500);
		 	     }
		    });
	}
}


/***********************************************
功能 关闭文件，释放资源
***********************************************/
function WPSCenterOffice_Attachment_UnLoad() {

}

/***********************************************
功能 提交保存文件
***********************************************/
function WPSCenterOffice_Attachment_Submit() {
	if(!this.isSubmitByWps){
		return true;
	}

	var flag = false; 
	var rtnPromise = this.wpsObj.save();
	var updateRelationUrl=this.updateRelationUrl;
	var fdAttMainId=this.fdId;
	var fdModelId=this.fdModelId;
	var fdModelName=this.fdModelName;
	var fdKey=this.fdKey;
	var def = $.Deferred();
	rtnPromise.then(function(rtn){
		console.log("wps_save_result", rtn);
		if(rtn.result=="ok"||rtn.result=="nochange"){
			console.log("file save ok");
			flag = true;
			def.resolve(flag);
		}else{
			def.resolve(flag);
		}
	});
	return def

}

/***********************************************
 功能 接受所有修订
 ***********************************************/
function WPSCenterOffice_Attachment_accent() {
	var _self = this;
	if(_self.wpsRevisionsObj)
		_self.wpsRevisionsObj.AcceptAll();
}

/***********************************************
 功能 拒绝所有修订
 ***********************************************/
function WPSCenterOffice_Attachment_reject() {
	var _self = this;
	if(_self.wpsRevisionsObj)
		_self.wpsRevisionsObj.RejectAll();
}
/***********************************************
 功能 操作修订
 true:打开修订
 false:关闭修订
 ***********************************************/
function WPSCenterOffice_Attachment_handleRevision(isRevision) {
	var _self = this;
	if(_self.wpsObj && typeof _self.wpsObj.WpsApplication != "undefined") {
		_self.wpsObj.WpsApplication().ActiveDocument.TrackRevisions =isRevision;
	}
}

function isJSON(str) {
    if (typeof str == 'string') {
        try {
            JSON.parse(str);
            return true;
        } catch(e) {
            console.log(e);
            return false;
        }
    }
    return false;
}