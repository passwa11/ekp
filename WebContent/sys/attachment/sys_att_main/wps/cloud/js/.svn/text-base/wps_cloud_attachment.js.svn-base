Com_RegisterFile("wps_cloud_attachment.js");
Com_IncludeFile("common.js");
Com_IncludeFile("data.js");


function WPSCloudOffice_AttachmentObject(fdId, fdKey,fdModelId,fdModelName, fdMode,hasHead) {
	this.fdId = fdId;
	this.fdKey = fdKey; // 附件标识
	this.fdModelName = fdModelName;
	this.fdModelId = fdModelId;
	this.optMode = Com_GetUrlParameter(location.href, "method");
	this.fdMode = fdMode;
	this.contentFlag = false;  //公文单子控制正文下载打印权限使用
	this.isTemplate = false; //是否是模板正文
	
	this.hasHead=hasHead;//wps菜单栏
	
	this.container = "WPSCloudOffice_" + fdKey;
	this.wpsObj = null;
	this.updateRelationUrl =  Com_Parameter.ContextPath + "sys/attachment/sys_att_main/sysAttMain.do?method=updateCloudRelation";
	
	this.hasLoad = false;
	this.isLoading = false;
	this.forceLoad = false;
	this.isSubmitByWps = true;
	this.load = WPSCloudOffice_Attachment_Load;
	this.unLoad = WPSCloudOffice_Attachment_UnLoad;
	this.submit = WPSCloudOffice_Attachment_Submit;
	this.print = WPSCloudOffice_Attachment_Print;
	
}

/***********************************************
功能 打印
***********************************************/
function WPSCloudOffice_Attachment_Print() {
   var _self = this;
   if(_self.wpsObj)
	   _self.wpsObj.WpsApplication().CommandBars('TabPrintPreview').Execute();
}

/***********************************************
功能 打开文档
***********************************************/
function WPSCloudOffice_Attachment_Load() {
   var _self = this;
   if(self.hasLoad && !self.isLoading){
		self.forceLoad = true;
	}
	if((!self.hasLoad && !self.isLoading) || self.forceLoad){
		_self.isLoading = true;
		var url = Com_Parameter.ContextPath+"sys/attachment/sys_att_main/sysAttMain.do?method=getWpsCloudViewUrl"; 
		   $.ajax({    
		 	     type:"post",     
		 	     url:url,     
		 	     data:{fdAttMainId:this.fdId,fdMode:this.fdMode,contentFlag:this.contentFlag},  
		 	     async:false,    //用同步方式 
		 	     success:function(data){
			 	    	 if(isJSON(data)){
			 	    		var results =  eval("("+data+")");				 	    
							if(results['url']){
						    	var wpsUrl = results['url'];
						    	if(results['apptoken']){
						    		wpsUrl += "&Apptoken=" +  results['apptoken'];
						    	}
						    	if(results['wps_sid']){
						    		wpsUrl += "&wps_sid=" +  results['wps_sid'];
						    	}	
						    	
						    	var mode = 'normal';
						    	if(_self.hasHead=="simple"){
						    		mode=_self.hasHead;
						    	}
						    	
						    	_self.wpsObj = WebOfficeSDK.config({
						    		mount:$("#"+_self.container)[0],
						    		url: wpsUrl,	
						    		mode:mode
						    	});	
						    	
					     	if(_self.optMode != "add" && _self.fdMode == "write" && !_self.isTemplate){
						    		 _self.wpsObj.ready().then(function(){
						    			 _self.wpsObj.WpsApplication().ActiveDocument.TrackRevisions = true;
						    		 });
						    	}else{
						    		 _self.wpsObj.ready().then(function(){
						    			 _self.wpsObj.WpsApplication().ActiveDocument.TrackRevisions = false;
						    		 });
						    	}
					     	
						    	//如果是编辑状态，提交表单的时候提交附件内容
					     	
						    	if(_self.fdMode == "write"){
						    		top.window.Com_Parameter.event["confirm"].unshift( function() {
										if ($("#"+_self.container).is(":visible") && typeof _self.wpsObj.WpsApplication != "undefined") {
											if(typeof _self.wpsObj.WpsApplication().ActiveDocument != "unknown"){
												_self.wpsObj.WpsApplication().ActiveDocument.TrackRevisions = false;
											}
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
		 	 			 	topic.publish('/sys/attachment/wpsCloud/loaded', _self.wpsObj, {});
		 	 			});
			 	    	}
		 	     }
		    });
	}
}


/***********************************************
功能 关闭文件，释放资源
***********************************************/
function WPSCloudOffice_Attachment_UnLoad() {

}

/***********************************************
功能 提交保存文件
***********************************************/
function WPSCloudOffice_Attachment_Submit() {
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
//		if(rtn.result=="nochange"){
//			console.log("file not change");
//			flag = true;
//			def.resolve(flag);
//		}
		if(rtn.result=="ok" || rtn.result=="nochange" || rtn == true){
			console.log("file save ok");
			$.ajax({   
		 	     type:"post",     
		 	     url:updateRelationUrl,    
		 	     data:{fdAttMainId:fdAttMainId,fdModelId:fdModelId,fdModelName:fdModelName,fdKey:fdKey},  
		 	     async:false,    //用同步方式 
		 	     success:function(data){
		 	    	console.log("file return:"+data);
		 	    	if (data){
		 	    		var results =  eval("("+data+")");
		 	    		if(results['status']){
		 	    			flag = true;
		 	    		}
		 	    		def.resolve(flag);
					}
		 		 }    
		    });
		}else{
			def.resolve(flag);
		}
	});
	return def

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