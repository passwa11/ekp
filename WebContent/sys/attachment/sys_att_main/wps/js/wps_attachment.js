Com_RegisterFile("wps_attachment.js");
Com_IncludeFile("common.js");
Com_IncludeFile("data.js");


function WPS_AttachmentObject(fdId, fdKey,fdModelId,fdModelName, fdMode) {
	this.fdId = fdId;
	this.fdKey = fdKey; // 附件标识
	this.fdModelName = fdModelName;
	this.fdModelId = fdModelId;
	this.fdMode = fdMode;
	this.bindSubmit = true;
	
	this.container = "WPSWebOffice_" + fdKey;
	this.wpsObj = null;
	
	this.bookMarks = null;

	this.updateRelationUrl =  Com_Parameter.ContextPath + "sys/attachment/sys_att_main/sysAttMain.do?method=updateRelation";
	
	this.load = WPS_Attachment_Load;
	this.unLoad = WPS_Attachment_UnLoad;
	this.submit = WPS_Attachment_Submit;
}

/***********************************************
功能 打开文档
***********************************************/
function WPS_Attachment_Load() {
   var _self = this;
   //var json = (new Function("return (" + _self.bookMarks + ");"))();
  // console.log("_self.bookMarks",_self.bookMarks);
  // console.log("bookMarks",json);
   var url=Com_Parameter.ContextPath+"sys/attachment/sys_att_main/sysAttMain.do?method=getWpsUrlAndToken"; 
   $.ajax({    
 	     type:"post",     
 	     url:url,     
 	     data:{fdAttMainId:this.fdId,fdMode:this.fdMode},  
 	     async:false,    //用同步方式 
 	     success:function(data){
 	    	if (data){
 	    		var results =  eval("("+data+")");
			    if(results['wpsUrl'] && results['token']!=null){
			    	var mode = 'simple';
			    	if(_self.fdMode == 'write'){
			    		mode = 'normal';
			    	}
			    	_self.wpsObj = WebOfficeSDK.config({
			    		mount:$("#"+_self.container)[0],
			    		url: results['wpsUrl'],
			    		mode:mode
			    	});
			    	if(_self.wpsObj){
			    		_self.wpsObj.setToken({token: results['token']}); 
		    			//书签
			    		/*
				    	if(_self.bookMarks != "" && _self.bookMarks != null){
				    		_self.wpsObj.WordApplication().ActiveDocument.ReplaceBookmark(_self.bookMarks);
				    	}
				    	_self.wpsObj.WordApplication().ActiveDocument.TrackRevisions = false;
				    	*/
			    		
			    		var rtnPromise = _self.wpsObj.ready();
			    		rtnPromise.then(function(rtn){
			    			_self.wpsObj.WordApplication().ActiveDocument.ReplaceBookmark([{name:'haha', type:'text', value:'234'}]);
			    		});
			    	}
			    	if (typeof(seajs) != 'undefined') {
	 	 			seajs.use( ['lui/topic' ], function(topic) {
	 	 			 	topic.publish('/sys/attachment/wpsWebOffice/loaded', _self.wpsObj, {});
	 	 			});
		 	    	}
			    	//如果是编辑状态，提交表单的时候提交附件内容
			    	if(_self.fdMode == "write" && _self.bindSubmit){
						Com_Parameter.event["confirm"].unshift( function() {
							return _self.submit();
						});
					}
				}
			}
 		 }    
    });
}

/***********************************************
 功能 关闭文件，释放资源
 ***********************************************/
function WPS_Attachment_UnLoad() {
	
}

/***********************************************
功能 提交保存文件
***********************************************/
function WPS_Attachment_Submit() {
	var flag = false; 
	var rtnPromise = this.wpsObj.save();
	rtnPromise.then(function(rtn){
		console.log(rtn);
		if(rtn.result=="nochange" || rtn.result=="ok"){
			console.log("2222222222222222222");
		}
	});
	$.ajax({   
 	     type:"post",     
 	     url:this.updateRelationUrl,    
 	     data:{fdAttMainId:this.fdId,fdModelId:this.fdModelId,fdModelName:this.fdModelName,fdKey:this.fdKey},  
 	     async:false,    //用同步方式 
 	     success:function(data){
 	    	if (data){
 	    		var results =  eval("("+data+")");
 	    		if(results['status']){
 	    			flag = true;
 	    		}
			}
 		 }    
    });
	return flag;
}


